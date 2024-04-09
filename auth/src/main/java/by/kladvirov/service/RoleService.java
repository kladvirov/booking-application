package by.kladvirov.service;

import by.kladvirov.dto.RoleCreationDto;
import by.kladvirov.dto.RoleDto;
import by.kladvirov.entity.Role;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {

    RoleDto findById(Long id);

    List<RoleDto> findAll(Pageable pageable);

    List<Role> findAllByIdIn(List<Long> roleIds);

    RoleDto save(RoleCreationDto roleDto);

    void update(Long id, RoleCreationDto roleDto);

    void delete(Long id);

}
