package by.kladvirov.controller;


import by.kladvirov.dto.PasswordChangingDto;
import by.kladvirov.dto.TokenDto;
import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.dto.UserInfoDto;
import by.kladvirov.dto.AuthenticationRequest;
import by.kladvirov.service.AuthenticationService;
import by.kladvirov.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final UserServiceImpl service;

    @GetMapping("/get-info")
    public ResponseEntity<UserInfoDto> fetchUser(@AuthenticationPrincipal UserDetails userDetails) {
        UserInfoDto userInfoDto = new UserInfoDto(userDetails.getUsername(), userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList(),
                service.findUserRoles(userDetails.getUsername()));
        return ResponseEntity.ok(userInfoDto);
    }

    @PostMapping("/register")
    public ResponseEntity<TokenDto> register(@RequestBody @Valid UserCreationDto request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        return ResponseEntity.ok(authenticationService.refreshToken(header));
    }

    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String header, @AuthenticationPrincipal UserDetails userDetails) {
        authenticationService.logout(header, userDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<HttpStatus> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PasswordChangingDto passwordChangingDto
    ) {
        authenticationService.changePassword(userDetails, passwordChangingDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<HttpStatus> verify(@RequestParam(name = "token") String token) {
        authenticationService.verifyUser(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/send-verification-message")
    public ResponseEntity<HttpStatus> sendVerificationMessage(@AuthenticationPrincipal UserDetails userDetails) {
        authenticationService.sendVerificationMessage(userDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/self-delete")
    public ResponseEntity<HttpStatus> deleteUserByToken(@AuthenticationPrincipal UserDetails userDetails) {
        authenticationService.deleteUserByToken(userDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

