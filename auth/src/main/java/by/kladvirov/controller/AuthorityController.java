package by.kladvirov.controller;

import by.kladvirov.dto.AuthorityCreationDto;
import by.kladvirov.dto.AuthorityDto;
import by.kladvirov.service.AuthorityService;
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
@RequestMapping("/authorities")
public class AuthorityController {

    private final AuthorityService authorityService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_AUTHORITIES')")
    public ResponseEntity<AuthorityDto> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(authorityService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_AUTHORITIES')")
    public ResponseEntity<List<AuthorityDto>> getAllAuthorities(Pageable pageable) {
        return new ResponseEntity<>(authorityService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_AUTHORITIES')")
    public ResponseEntity<AuthorityDto> createAuthority(@RequestBody @Valid AuthorityCreationDto authorityCreationDto) {
        return new ResponseEntity<>(authorityService.save(authorityCreationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_AUTHORITIES')")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") Long id, @RequestBody @Valid AuthorityCreationDto authorityCreationDto) {
        authorityService.update(id, authorityCreationDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_AUTHORITIES')")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        authorityService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
