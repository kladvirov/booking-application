package by.kladvirov.controller;

import by.kladvirov.dto.RoleCreationDto;
import by.kladvirov.dto.RoleDto;
import by.kladvirov.service.RoleService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ROLES')")
    public ResponseEntity<RoleDto> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(roleService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ROLES')")
    public ResponseEntity<List<RoleDto>> getAllRoles(Pageable pageable) {
        return new ResponseEntity<>(roleService.findAll(pageable), HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ROLES')")
    public ResponseEntity<RoleDto> createRole(@RequestBody @Valid RoleCreationDto roleCreationDto) {
        return new ResponseEntity<>(roleService.save(roleCreationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ROLES')")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") Long id, @RequestBody @Valid RoleCreationDto roleCreationDto) {
        roleService.update(id, roleCreationDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ROLES')")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
