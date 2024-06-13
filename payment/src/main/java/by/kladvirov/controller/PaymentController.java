package by.kladvirov.controller;

import by.kladvirov.dto.payment.PaymentDto;
import by.kladvirov.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public Mono<ResponseEntity<PaymentDto>> createPayment(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String header,
            @RequestParam("reservationId") Long id
    ) {
        return paymentService.save(header, id).map(ResponseEntity::ok);
    }

}
