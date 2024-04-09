package by.kladvirov.service.impl;

import by.kladvirov.dto.AuthorityCreationDto;
import by.kladvirov.dto.AuthorityDto;
import by.kladvirov.entity.Authority;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.AuthorityMapper;
import by.kladvirov.repository.AuthorityRepository;
import by.kladvirov.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    private final AuthorityMapper authorityMapper;

    @Transactional(readOnly = true)
    @Override
    public AuthorityDto findById(Long id) {
        Authority authority = authorityRepository.findById(id)
                .orElseThrow(() -> new ServiceException("There is no such authority", HttpStatus.NOT_FOUND));
        return authorityMapper.toDto(authority);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AuthorityDto> findAll(Pageable pageable) {
        return authorityMapper.toDto(authorityRepository.findAll(pageable).toList());
    }

    @Transactional
    @Override
    public AuthorityDto save(AuthorityCreationDto authorityCreationDto) {
        Authority entity = authorityMapper.toEntity(authorityCreationDto);
        return authorityMapper.toDto(authorityRepository.save(entity));
    }

    @Transactional
    @Override
    public void update(Long id, AuthorityCreationDto authorityCreationDto) {
        Authority authority = authorityRepository.findById(id)
                .orElseThrow(() -> new ServiceException("There is no such authority", HttpStatus.NOT_FOUND));
        Authority mappedAuthority = authorityMapper.toEntity(authorityCreationDto);
        updateAuthority(authority, mappedAuthority);
        authorityRepository.save(authority);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        authorityRepository.deleteById(id);
    }

    private void updateAuthority(Authority authority, Authority mappedAuthority) {
        authority.setName(mappedAuthority.getName());
        authority.setRoles(mappedAuthority.getRoles());
    }

}
