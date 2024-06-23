package by.kladvirov.repository;

import by.kladvirov.dto.payment.PaymentDto;
import by.kladvirov.entity.Payment;
import by.kladvirov.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<PaymentDto> findByExpiresAtBeforeAndStatusIs(ZonedDateTime dateTime, PaymentStatus status);

}


