package by.andron.controller;

import by.andron.dto.ServiceCreationDto;
import by.andron.service.ServiceService;
import by.kladvirov.dto.core.ServiceDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(value = "/services")
public class ServiceController {

    private final ServiceService service;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_SERVICES')")
    public ResponseEntity<ServiceDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_SERVICES')")
    public ResponseEntity<List<ServiceDto>> findAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SAVE_SERVICES')")
    public ResponseEntity<ServiceDto> save(
            @RequestBody
            @Valid ServiceCreationDto dto
    ) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_SERVICES')")
    public ResponseEntity<HttpStatus> update(
            @PathVariable("id") Long id,
            @RequestBody
            @Valid ServiceCreationDto dto
    ) {
        service.update(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_SERVICES')")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
