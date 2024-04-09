package by.kladvirov.service.impl;

import by.kladvirov.dto.RoleCreationDto;
import by.kladvirov.dto.RoleDto;
import by.kladvirov.entity.Role;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.RoleMapper;
import by.kladvirov.repository.RoleRepository;
import by.kladvirov.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Transactional(readOnly = true)
    @Override
    public RoleDto findById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ServiceException("There is no such role", HttpStatus.NOT_FOUND));
        return roleMapper.toDto(role);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoleDto> findAll(Pageable pageable) {
        return roleMapper.toDto(roleRepository.findAll(pageable).toList());
    }

    @Override
    public List<Role> findAllByIdIn(List<Long> roleIds) {
        return roleRepository.findAllByIdIn(roleIds);
    }

    @Transactional
    @Override
    public RoleDto save(RoleCreationDto roleDto) {
        Role entity = roleMapper.toEntity(roleDto);
        return roleMapper.toDto(roleRepository.save(entity));
    }

    @Transactional
    @Override
    public void update(Long id, RoleCreationDto roleDto) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ServiceException("There is no such role", HttpStatus.NOT_FOUND));
        Role mappedRole = roleMapper.toEntity(roleDto);
        updateRole(role, mappedRole);
        roleRepository.save(role);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    private void updateRole(Role role, Role mappedRole) {
        role.setName(mappedRole.getName());
    }

}