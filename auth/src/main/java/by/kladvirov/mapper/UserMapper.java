package by.kladvirov.mapper;

import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.dto.UserDto;
import by.kladvirov.entity.Role;
import by.kladvirov.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "roles", source = "userCreationDto.roleIds", qualifiedByName = "mapRoleIdsToRoles")
    User toEntity(UserCreationDto userCreationDto);

    User toEntity(UserDto userDto);

    UserDto toDto(User user);

    @Mapping(target = "roleIds", source = "user.roles", qualifiedByName = "mapRolesToRoleIds")
    UserCreationDto toCreationDto(User user);

    List<UserDto> toDto(List<User> users);

    @Named("mapRoleIdsToRoles")
    default Set<Role> mapRoleIdsToRoles(Set<Long> roleIds) {
        return roleIds.stream()
                .map(roleId -> {
                    Role role = new Role();
                    role.setId(roleId);
                    return role;
                })
                .collect(Collectors.toSet());
    }

    @Named("mapRolesToRoleIds")
    default Set<Long> mapRolesToRoleIds(Set<Role> roles) {
        return roles.stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
    }

}
