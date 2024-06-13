package by.andron.controller;

import by.andron.dto.ReservationCreationDto;
import by.andron.service.ReservationService;
import by.kladvirov.dto.core.ReservationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService service;

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping("/find-by-login-and-id")
    public ResponseEntity<ReservationDto> findByLoginAndId(
            @RequestParam("login") String login,
            @RequestParam("id") Long id
    ) {
        return new ResponseEntity<>(service.findByLoginAndId(login, id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReservationDto> save(
            @RequestBody
            @Valid ReservationCreationDto dto,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String header
    ) {
        return new ResponseEntity<>(service.save(dto, header), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(
            @PathVariable("id") Long id,
            @RequestBody
            @Valid ReservationCreationDto dto
    ) {
        service.update(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(
            @PathVariable Long id
    ) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
