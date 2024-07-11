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
public class ReservationCreationDto {

    @NotNull(message = "Status cannot be empty")
    private Status status;

    @NotNull(message = "Creation date cannot be empty")
    private ZonedDateTime dateFrom;

    @NotNull(message = "Date to cannot be empty")
    @FutureOrPresent
    private ZonedDateTime dateTo;

    @NotNull(message = "Username cannot be empty")
    private String username;

    @NotNull(message = "Service id cannot be empty")
    private Long serviceId;

}
