package by.kladvirov.service;

import by.kladvirov.dto.AuthorityCreationDto;
import by.kladvirov.dto.AuthorityDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorityService {

    AuthorityDto findById(Long id);

    List<AuthorityDto> findAll(Pageable pageable);

    AuthorityDto save(AuthorityCreationDto authorityCreationDto);

    void update(Long id, AuthorityCreationDto authorityCreationDto);

    void delete(Long id);

}
