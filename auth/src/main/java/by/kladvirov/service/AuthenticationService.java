package by.kladvirov.service;

import by.kladvirov.dto.AuthenticationRequest;
import by.kladvirov.dto.ChangePasswordMessageDto;
import by.kladvirov.dto.ConfirmationMessageDto;
import by.kladvirov.dto.EmailDto;
import by.kladvirov.dto.ForgotPasswordMessageDto;
import by.kladvirov.dto.Message;
import by.kladvirov.dto.PasswordChangingDto;
import by.kladvirov.dto.TokenDto;
import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.dto.UserDto;
import by.kladvirov.entity.User;
import by.kladvirov.entity.redis.Token;
import by.kladvirov.entity.redis.Verification;
import by.kladvirov.enums.UserStatus;
import by.kladvirov.exception.PasswordException;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.exception.TokenException;
import by.kladvirov.mapper.TokenMapper;
import by.kladvirov.mapper.UserMapper;
import by.kladvirov.rabbitmq.RabbitMqPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;

    private final VerificationService verificationService;

    private final TokenService tokenService;

    private final TokenMapper tokenMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    private final AuthenticationManager manager;

    private final RabbitMqPublisher publisher;

    @Transactional
    public TokenDto login(AuthenticationRequest request) {
        User user = userService.getByLogin(request.getLogin());
        if (user.getStatus() == UserStatus.DELETED) {
            throw new ServiceException("The user is deleted", HttpStatus.NO_CONTENT);
        }
        TokenDto tokenDto = jwtService.generateTokenDto(user);
        tokenService.save(tokenMapper.toEntity(tokenDto, user.getId()));
        manager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        return tokenDto;
    }

    @Transactional
    public TokenDto register(UserCreationDto request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new ServiceException("The following email is already taken", HttpStatus.BAD_REQUEST);
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        UserDto user = userService.save(request);
        TokenDto tokenDto = jwtService.generateTokenDto(userMapper.toEntity(request));
        tokenService.save(tokenMapper.toEntity(tokenDto, user.getId()));
        Verification verification = verificationService.save(user.getId());
        Message message = new ConfirmationMessageDto(request.getName(), request.getSurname(), request.getEmail(), verification.getToken());
        publisher.send(message);
        return tokenDto;
    }

    @Transactional
    public TokenDto refreshToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new TokenException("Invalid format", HttpStatus.BAD_REQUEST);
        }

        String refreshToken = header.substring(7);
        String userLogin = jwtService.extractUserLogin(refreshToken);
        User user = userService.getByLogin(userLogin);

        if (jwtService.isTokenValid(refreshToken, user)) {
            TokenDto tokenDto = jwtService.generateTokenDto(user);
            Token foundToken = tokenService.findByRefreshToken(refreshToken);
            tokenService.delete(foundToken);
            tokenService.save(tokenMapper.toEntity(tokenDto, user.getId()));
            return tokenDto;
        } else {
            throw new TokenException("Token is invalid", HttpStatus.FORBIDDEN);
        }
    }

    @Transactional
    public void logout(String header, UserDetails userDetails) {
        String token = header.substring(7);
        if (jwtService.isTokenValid(token, userDetails)) {
            Token foundToken = tokenService.findByAccessToken(token);
            tokenService.delete(foundToken);
        }
    }

    @Transactional
    public void changePassword(UserDetails userDetails, PasswordChangingDto passwordChangingDto) {
        if (!passwordEncoder.matches(passwordChangingDto.getCurrentPassword(), userDetails.getPassword())) {
            throw new PasswordException("Current passwords don't match", HttpStatus.BAD_REQUEST);
        }
        userService.updatePassword(userDetails.getUsername(), passwordEncoder.encode(passwordChangingDto.getConfirmationPassword()));
        sendChangePasswordMessage(userDetails.getUsername());
    }

    @Transactional
    public void restorePassword(String email, PasswordChangingDto passwordChangingDto) {
        userService.updatePassword(email, passwordEncoder.encode(passwordChangingDto.getConfirmationPassword()));
    }

    @Transactional
    public void verifyUser(String token) {
        verificationService.verifyUser(token);
    }

    @Transactional
    public void deleteUserByToken(UserDetails userDetails) {
        userService.deleteByLogin(userDetails.getUsername());
    }

    @Transactional
    public void sendVerificationMessage(UserDetails userDetails) {
        verificationService.sendVerificationMessage(userDetails.getUsername());
    }

    @Transactional
    public void sendChangePasswordMessage(String username) {
        User user = userService.getByLogin(username);
        if (user != null) {
            Message message = new ChangePasswordMessageDto(user.getName(), user.getSurname(), user.getEmail(), "Пароль был успешно сменен на новый");
            publisher.send(message);
        } else {
            throw new ServiceException("There was an exception during sending message", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void sendForgotPasswordMessage(EmailDto email) {
        User user = userService.findByEmail(email);
        if (user != null) {
            Message message = new ForgotPasswordMessageDto(user.getName(), user.getSurname(), user.getEmail());
            publisher.send(message);
        } else {
            throw new ServiceException("There was an exception during sending message in auth service", HttpStatus.BAD_REQUEST);
        }
    }

}
