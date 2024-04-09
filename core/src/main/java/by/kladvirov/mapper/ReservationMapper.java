package by.kladvirov.mapper;

import by.kladvirov.dto.ReservationCreationDto;
import by.kladvirov.dto.ReservationDto;
import by.kladvirov.model.Reservation;
import by.kladvirov.model.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

    @Mapping(target = "service", source = "serviceId", qualifiedByName = "mapServiceIdToService")
    Reservation toEntity(ReservationCreationDto dto);

    @Mapping(source = "service", target = "serviceId", qualifiedByName = "mapServiceToServiceId")
    ReservationDto toDto(Reservation reservation);

    @Mapping(source = "service", target = "serviceId")
    List<ReservationDto> toDto(List<Reservation> reservations);

    @Named("mapServiceToServiceId")
    default Long mapServiceToServiceId(Service service) {
        return service.getId();
    }

    @Named("mapServiceIdToService")
    default Service mapServiceIdToService(Long id) {
        Service service = new Service();
        service.setId(id);
        return service;
    }

}
