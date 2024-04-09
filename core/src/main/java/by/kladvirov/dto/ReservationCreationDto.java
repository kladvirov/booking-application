package by.kladvirov.dto;

import by.kladvirov.enums.Status;
import jakarta.validation.constraints.NotEmpty;
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
    // change on local date

    @NotNull(message = "Date to cannot be empty")
    private ZonedDateTime dateTo;

    @NotNull(message = "User id cannot be empty")
    private Long userId;

    @NotNull(message = "Service id cannot be empty")
    private Long serviceId;

}
