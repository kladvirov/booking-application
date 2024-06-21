package by.kladvirov.mapper;

import by.kladvirov.dto.ServiceCreationDto;
import by.kladvirov.dto.ServiceDto;
import by.kladvirov.model.Service;
import by.kladvirov.model.ServiceProvider;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServiceMapper {

    @Mapping(source = "serviceProviderId", target = "serviceProvider", qualifiedByName = "mapServiceProviderIdToServiceProvider")
    Service toEntity(ServiceCreationDto dto);

    @Mapping(target = "serviceProviderId", source = "serviceProvider", qualifiedByName = "mapServiceProviderToServiceProviderId")
    ServiceDto toDto(Service service);

    @Mapping(target = "serviceProviderId", source = "serviceProvider")
    List<ServiceDto> toDto(List<Service> services);

    @Named("mapServiceProviderToServiceProviderId")
    default Long mapServiceProviderToServiceProviderId(ServiceProvider serviceProvider) {
        return serviceProvider.getId();
    }

    @Named("mapServiceProviderIdToServiceProvider")
    default ServiceProvider mapServiceProviderIdToServiceProvider(Long id) {
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(id);
        return serviceProvider;
    }

}
