package by.kladvirov.security.auth;


import by.kladvirov.dto.PasswordChangingDto;
import by.kladvirov.dto.TokenDto;
import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.dto.UserInfoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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

    @GetMapping("/get-info")
    public ResponseEntity<UserInfoDto> fetchUser(@AuthenticationPrincipal UserDetails userDetails) {
        UserInfoDto userInfoDto = new UserInfoDto(userDetails.getUsername(), userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());
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
    public TokenDto refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        return authenticationService.refreshToken(header);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String header, @AuthenticationPrincipal UserDetails userDetails) {
        authenticationService.logout(header, userDetails);
    }

    @PostMapping("/change-password")
    public void changePassword(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String header,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PasswordChangingDto passwordChangingDto
    ) {
        authenticationService.changePassword(header, userDetails, passwordChangingDto);
    }

    @GetMapping("/verify")
    public void verify(@RequestParam(name = "token") String token) {
        authenticationService.verifyUser(token);
    }
    @GetMapping("/send-message")
    public void sendMessage(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        authenticationService.sendMessage(header);
    }

    @PostMapping("/delete-by-token")
    public void deleteUserByToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String header, @AuthenticationPrincipal UserDetails userDetails) {
        authenticationService.deleteUserByToken(header, userDetails);
    }

}

