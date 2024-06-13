package by.andron.service;

import by.andron.dto.ServiceCreationDto;
import by.kladvirov.dto.core.ServiceDto;

import java.util.List;

public interface ServiceService {

    ServiceDto findById(Long id);

    List<ServiceDto> findAll();

    ServiceDto save(ServiceCreationDto dto);

    void update(Long id, ServiceCreationDto dto);

    void delete(Long id);

}