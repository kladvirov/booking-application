package by.kladvirov.controller;

import by.kladvirov.dto.ReservationCreationDto;
import by.kladvirov.service.ReservationService;
import by.kladvirov.dto.core.ReservationDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PreAuthorize("hasAuthority('READ_RESERVATIONS') && @reservationServiceImpl.isAllowedToRead(principal.username, #id) || @reservationServiceImpl.isAdmin(#httpServletRequest.getHeader('Authorization'))")
    public ResponseEntity<ReservationDto> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping("/find-by-login-and-id")
    public ResponseEntity<ReservationDto> findByLoginAndId(
            @RequestParam("login") String login,
            @RequestParam("id") Long id
    ) {
        return new ResponseEntity<>(service.findByLoginAndId(login, id), HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('READ_RESERVATIONS') && @reservationServiceImpl.isAdmin(#httpServletRequest.getHeader('Authorization'))")
    public ResponseEntity<List<ReservationDto>> findAll(HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_RESERVATIONS')")
    public ResponseEntity<List<ReservationDto>> findAllByUsername() {
        return new ResponseEntity<>(service.findAllByUsername(SecurityContextHolder.getContext().getAuthentication().getName()), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SAVE_RESERVATIONS') && @reservationServiceImpl.isAllowedToCreate(principal.username, #dto.username) || @reservationServiceImpl.isAdmin(#httpServletRequest.getHeader('Authorization'))")
    public ResponseEntity<ReservationDto> save(
            @RequestBody
            @Valid ReservationCreationDto dto,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String header,
            HttpServletRequest httpServletRequest
    ) {
        return new ResponseEntity<>(service.save(dto, header), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_RESERVATIONS') && @reservationServiceImpl.isAllowedToUpdate(#dto.username, principal.username) || @reservationServiceImpl.isAdmin(#httpServletRequest.getHeader('Authorization'))")
    public ResponseEntity<HttpStatus> update(
            @PathVariable("id") Long id,
            @RequestBody
            @Valid ReservationCreationDto dto,
            HttpServletRequest httpServletRequest
    ) {
        service.update(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_RESERVATIONS') && @reservationServiceImpl.isAllowedToDelete(principal.username, #id) || @reservationServiceImpl.isAdmin(#httpServletRequest.getHeader('Authorization'))")
    public ResponseEntity<HttpStatus> delete(
            @PathVariable Long id,
            HttpServletRequest httpServletRequest
    ) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
