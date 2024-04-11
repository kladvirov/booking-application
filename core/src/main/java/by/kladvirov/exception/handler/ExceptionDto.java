package by.kladvirov.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExceptionDto {

    private String message;

    private Integer status;

    private LocalDateTime time;

    private String uri;

}
