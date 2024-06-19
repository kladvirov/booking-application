package by.kladvirov.service;

import by.kladvirov.dto.payment.PaymentDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {

    PaymentDto findById(Long id);

    List<PaymentDto> findAll(Pageable pageable);

    PaymentDto save(String header, Long reservationId);

    void delete(Long id);

    void pay(Long id, Long reservationId, String header);

    void cancel(Long id);

}
