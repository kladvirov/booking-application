package by.kladvirov.dto;

import by.kladvirov.enums.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {

    @NotNull(message = "Status cannot be empty")
    private Status status;

    @NotNull(message = "Date from cannot be empty")
    private ZonedDateTime dateFrom;

    @NotNull(message = "Date to cannot be empty")
    @FutureOrPresent
    private ZonedDateTime dateTo;

    @NotNull(message = "Order date cannot be empty")
    private ZonedDateTime orderedAt;

    @NotNull(message = "Expiration date cannot be null, but can be empty")
    private ZonedDateTime expiresAt;

    @NotNull(message = "User id cannot be empty")
    private Long userId;

    @NotNull(message = "Service id cannot be empty")
    private Long serviceId;

}
