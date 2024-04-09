package by.kladvirov.service;

import by.kladvirov.dto.ServiceCreationDto;
import by.kladvirov.dto.ServiceDto;

import java.util.List;

public interface ServiceService {

    ServiceDto findById(Long id);

    List<ServiceDto> findAll();

    ServiceDto save(ServiceCreationDto dto);

    void update(Long id, ServiceCreationDto dto);

    void delete(Long id);

}