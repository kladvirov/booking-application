package by.kladvirov.mapper;

import by.kladvirov.dto.RoleCreationDto;
import by.kladvirov.dto.RoleDto;
import by.kladvirov.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    Role toEntity(RoleCreationDto roleDto);

    RoleDto toDto(Role role);

    List<RoleDto> toDto(List<Role> roles);

}
