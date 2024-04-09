package by.kladvirov.security.auth;

import by.kladvirov.dto.PasswordChangingDto;
import by.kladvirov.dto.TokenDto;
import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.dto.UserDto;
import by.kladvirov.entity.User;
import by.kladvirov.enums.UserStatus;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.TokenMapper;
import by.kladvirov.mapper.UserMapper;
import by.kladvirov.security.JwtService;
import by.kladvirov.service.TokenService;
import by.kladvirov.service.UserService;
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

    private final TokenService tokenService;

    private final TokenMapper tokenMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    private final AuthenticationManager manager;

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
            throw new RuntimeException("The following email is already taken");
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        UserDto user = userService.save(request);
        TokenDto tokenDto = jwtService.generateTokenDto(userMapper.toEntity(request));
        tokenService.save(tokenMapper.toEntity(tokenDto, user.getId()));
        return tokenDto;
    }

    @Transactional
    public TokenDto refreshToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("change to custom exception");
        }

        String refreshToken = header.substring(7);
        String userLogin = jwtService.extractUserLogin(refreshToken);

        User user = userService.findByLogin(userLogin);

        if (jwtService.isTokenValid(refreshToken, user)) {
            TokenDto tokenDto = jwtService.generateTokenDto(user);
            if (!tokenService.existsByRefreshToken(refreshToken)) {
                throw new ServiceException("Invalid token", HttpStatus.FORBIDDEN);
            }
            tokenService.deleteByRefreshToken(refreshToken);
            tokenService.save(tokenMapper.toEntity(tokenDto, user.getId()));
            return tokenDto;
        }

        throw new RuntimeException();
    }

    @Transactional
    public void logout(String header, UserDetails userDetails) {
        String token = header.substring(7);
        if (jwtService.isTokenValid(token, userDetails)) {
            tokenService.deleteByToken(token);
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
    public void deleteUserByToken(String header, UserDetails userDetails) {
        String token = extractToken(header);
        validateToken(token, userDetails);
        userService.deleteByLogin(userDetails.getUsername());
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

}
