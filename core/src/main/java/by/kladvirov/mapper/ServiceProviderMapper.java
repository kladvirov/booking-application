package by.kladvirov.mapper;

import by.kladvirov.dto.ServiceProviderCreationDto;
import by.kladvirov.dto.ServiceProviderDto;
import by.kladvirov.model.Service;
import by.kladvirov.model.ServiceProvider;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServiceProviderMapper {

    ServiceProvider toEntity(ServiceProviderCreationDto serviceProviderCreationDto);

    ServiceProviderDto toDto(ServiceProvider serviceProvider);

    List<ServiceProviderDto> toDto(List<ServiceProvider> serviceProviders);

}
