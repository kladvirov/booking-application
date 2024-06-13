package by.kladvirov.dto.payment;

import by.kladvirov.dto.payment.json.Info;
import by.kladvirov.enums.PaymentStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Data
@Builder
public class PaymentDto {

    private Long id;

    private Info info;

    @NotNull
    private PaymentStatus status;

    @NotNull
    @PastOrPresent
    private ZonedDateTime createdAt;

    @NotNull
    @FutureOrPresent
    private ZonedDateTime expiresAt;

    @NotNull
    @Positive
    private Long userId;

    @NotNull
    @Positive
    private Long reservationId;

}
