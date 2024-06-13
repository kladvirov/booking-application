package by.kladvirov.dto.payment.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Info {

    private Integer hours;

    private Service service;

}
