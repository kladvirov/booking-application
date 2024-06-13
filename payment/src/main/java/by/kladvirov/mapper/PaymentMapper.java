package by.kladvirov.mapper;

import by.kladvirov.dto.payment.PaymentDto;
import by.kladvirov.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {

    Payment toEntity(PaymentDto paymentDto);

    PaymentDto toDto(Payment payment);

    List<PaymentDto> toDto(List<Payment> payments);

}
