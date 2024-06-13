package by.kladvirov.dto.payment.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Service {

    private Float pricePerHour;

    private Provider provider;

}
