package by.kladvirov.controller;

import by.kladvirov.dto.payment.PaymentDto;
import by.kladvirov.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(paymentService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PaymentDto>> findAll(Pageable pageable) {
        return new ResponseEntity<>(paymentService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String header,
            @RequestParam("reservationId") Long id
    ) {
        return new ResponseEntity<>(paymentService.save(header, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        paymentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/pay")
    public ResponseEntity<HttpStatus> pay(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String header,
            @RequestParam("id") Long id,
            @RequestParam("reservationId") Long reservationId
    ) {
        paymentService.pay(id, reservationId, header);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cancel")
    public ResponseEntity<HttpStatus> cancel(@RequestHeader(HttpHeaders.AUTHORIZATION) String header, @RequestParam("id") Long id) {
        paymentService.cancel(header, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
