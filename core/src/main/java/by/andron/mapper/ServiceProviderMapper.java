package by.andron.mapper;

import by.andron.dto.ServiceProviderCreationDto;
import by.kladvirov.dto.core.ServiceProviderDto;
import by.andron.model.ServiceProvider;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServiceProviderMapper {

    ServiceProvider toEntity(ServiceProviderCreationDto serviceProviderCreationDto);

    ServiceProviderDto toDto(ServiceProvider serviceProvider);

    List<ServiceProviderDto> toDto(List<ServiceProvider> serviceProviders);

}
