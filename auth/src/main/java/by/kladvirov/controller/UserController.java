package by.kladvirov.controller;

import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.dto.UserDto;
import by.kladvirov.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_USERS')")
    public ResponseEntity<UserDto> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_USERS')")
    public ResponseEntity<List<UserDto>> getAllUsers(Pageable pageable) {
        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/find-by-role")
    public ResponseEntity<List<UserDto>> findUsersByRoleName(@RequestParam(value = "roleName") String roleName) {
        List<UserDto> users = userService.findUsersByRoleName(roleName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/find-roles")
    public ResponseEntity<List<String>> findUserRoles(@RequestParam(value = "login") String login) {
        List<String> userRoles = userService.findUserRoles(login);
        return ResponseEntity.ok(userRoles);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_USERS')")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserCreationDto userCreationDto) {
        return new ResponseEntity<>(userService.save(userCreationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_USERS')")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") Long id, @RequestBody @Valid UserCreationDto userCreationDto) {
        userService.update(id, userCreationDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_USERS')")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update-roles/{id}")
    @PreAuthorize("hasAuthority('UPDATE_USERS')")
    public ResponseEntity<HttpStatus> updateUserRoles(@PathVariable("id") Long id, @RequestBody List<Long> roleIds) {
        userService.updateUserRoles(id, roleIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-roles/{id}")
    @PreAuthorize("hasAuthority('DELETE_USERS')")
    public ResponseEntity<HttpStatus> deleteUserRoles(@PathVariable("id") Long id, @RequestBody List<Long> roleIds) {
        userService.deleteUserRoles(id, roleIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
