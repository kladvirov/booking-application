package by.kladvirov.mapper;

import by.kladvirov.dto.ServiceProviderCreationDto;
import by.kladvirov.dto.ServiceProviderDto;
import by.kladvirov.model.ServiceProvider;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServiceProviderMapper {

    ServiceProvider toEntity(ServiceProviderCreationDto serviceProviderCreationDto);

    ServiceProviderDto toDto(ServiceProvider serviceProvider);

    List<ServiceProviderDto> toDto(List<ServiceProvider> serviceProviders);

}
