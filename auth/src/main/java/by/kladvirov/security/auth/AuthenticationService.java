package by.kladvirov.security.auth;

import by.kladvirov.dto.ConfirmationMessageDto;
import by.kladvirov.dto.Message;
import by.kladvirov.dto.PasswordChangingDto;
import by.kladvirov.dto.TokenDto;
import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.dto.UserDto;
import by.kladvirov.entity.User;
import by.kladvirov.entity.redis.Token;
import by.kladvirov.entity.redis.Verification;
import by.kladvirov.enums.UserStatus;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.TokenMapper;
import by.kladvirov.mapper.UserMapper;
import by.kladvirov.rabbitmq.RabbitMqSender;
import by.kladvirov.security.JwtService;
import by.kladvirov.service.TokenService;
import by.kladvirov.service.UserService;
import by.kladvirov.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

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

    private final RabbitMqSender sender;

    @Value("${duration}")
    private Long duration;

    @Transactional
    public TokenDto login(AuthenticationRequest request) {
        User user = userService.findByLogin(request.getLogin());
        if (user.getStatus() == UserStatus.DELETED) {
            throw new RuntimeException("The user is deleted");
        }
        TokenDto tokenDto = jwtService.generateTokenDto(user);
        tokenService.save(tokenMapper.toEntity(tokenDto, user.getId()));
        manager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        return tokenDto;
    }

    @Transactional
    public TokenDto register(UserCreationDto request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new ServiceException("The following email is already taken");
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        UserDto user = userService.save(request);
        TokenDto tokenDto = jwtService.generateTokenDto(userMapper.toEntity(request));
        tokenService.save(tokenMapper.toEntity(tokenDto, user.getId()));
        String token = UUID.randomUUID().toString();
        verificationService.save(buildVerification(user, token));
        Message message = new ConfirmationMessageDto(request.getName(), request.getSurname(), request.getEmail(), token);
        sender.send(message);
        return tokenDto;
    }

    @Transactional
    public TokenDto refreshToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid format");
        }

        String refreshToken = header.substring(7);
        String userLogin = jwtService.extractUserLogin(refreshToken);

        User user = userService.findByLogin(userLogin);

        if (jwtService.isTokenValid(refreshToken, user)) {
            TokenDto tokenDto = jwtService.generateTokenDto(user);
            Token foundToken = tokenService.findByRefreshToken(refreshToken);
            tokenService.delete(foundToken);
            tokenService.save(tokenMapper.toEntity(tokenDto, user.getId()));
            return tokenDto;
        }

        throw new RuntimeException();
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
    public void changePassword(String header, UserDetails userDetails, PasswordChangingDto passwordChangingDto) {
        String token = extractToken(header);
        validateToken(token, userDetails);
        validateCurrentPassword(passwordChangingDto.getCurrentPassword(), userDetails.getPassword());
        updatePassword(userDetails.getUsername(), passwordChangingDto.getConfirmationPassword());
    }

    @Transactional
    public void verifyUser(String token) {
        Verification verification = verificationService.findByToken(token);
        updateStatus(verification.getUserId());
        updateIsCompleted(verification);
    }

    @Transactional
    public void deleteUserByToken(String header, UserDetails userDetails) {
        String token = extractToken(header);
        validateToken(token, userDetails);
        userService.deleteByLogin(userDetails.getUsername());
    }

    @Transactional
    public void reVerifyUser(String header, UserDetails userDetails) {
        String hash = extractToken(header);
        validateToken(hash, userDetails);
        Token hashToken = tokenService.findByAccessToken(hash);
        UserDto user = userService.findById(hashToken.getUserId());
        Verification verificationToken = verificationService.findByUserId(user.getId());
        if (ChronoUnit.SECONDS.between(ZonedDateTime.now(), ZonedDateTime.parse(verificationToken.getCreatedAt())) > duration) {
            verificationService.deleteByUserId(user.getId());
            String token = UUID.randomUUID().toString();
            verificationService.save(buildVerification(user, token));
            Message message = new ConfirmationMessageDto(user.getName(), user.getSurname(), user.getEmail(), token);
            sender.send(message);
        } else {
            throw new ServiceException("It's too early");
        }
    }

    private String extractToken(String header) {
        return header.substring(7);
    }

    private void validateToken(String token, UserDetails userDetails) {
        if (!jwtService.isTokenValid(token, userDetails)) {
            throw new RuntimeException("Token is invalid");
        }
    }

    private void validateCurrentPassword(String currentPassword, String storedPassword) {
        if (!passwordEncoder.matches(currentPassword, storedPassword)) {
            throw new RuntimeException("Current passwords don't match");
        }
    }

    private void updatePassword(String username, String newPassword) {
        userService.updatePassword(username, passwordEncoder.encode(newPassword));
    }

    private Verification buildVerification(UserDto user, String token) {
        return Verification.builder()
                .token(token)
                .isCompleted(false)
                .userId(user.getId())
                .createdAt(ZonedDateTime.now().toString())
                .build();
    }

    private void updateIsCompleted(Verification verification) {
        verification.setIsCompleted(true);
        verificationService.save(verification);
    }

    private void updateStatus(Long id) {
        userService.updateStatus(id, UserStatus.VERIFIED);
    }

}
