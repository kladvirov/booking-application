package by.kladvirov.dto.payment.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ServiceDto {

    private Float pricePerHour;

    private ProviderDto provider;

}
