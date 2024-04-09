package by.kladvirov.mapper;

import by.kladvirov.dto.TokenDto;
import by.kladvirov.entity.redis.Token;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TokenMapper {

    TokenDto toDto(Token token);

    Token toEntity(TokenDto tokenDto, Long userId);

}
