package by.kladvirov.controller;

import by.kladvirov.dto.ReservationCreationDto;
import by.kladvirov.dto.ReservationDto;
import by.kladvirov.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/reservations")
public class ReservationController {

    private final ReservationService service;

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> findById(
            @PathVariable("id") Long id
    ) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReservationDto> save(
            @RequestBody
            @Valid ReservationCreationDto dto
    ) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
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
