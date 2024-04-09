package by.kladvirov.mapper;

import by.kladvirov.dto.AuthorityCreationDto;
import by.kladvirov.dto.AuthorityDto;
import by.kladvirov.entity.Authority;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorityMapper {

    Authority toEntity(AuthorityCreationDto authorityCreationDto);

    AuthorityDto toDto(Authority authority);

    List<AuthorityDto> toDto(List<Authority> authorities);

}
