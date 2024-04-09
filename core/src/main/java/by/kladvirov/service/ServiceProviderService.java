package by.kladvirov.service;

import by.kladvirov.dto.ServiceProviderCreationDto;
import by.kladvirov.dto.ServiceProviderDto;

import java.util.List;

public interface ServiceProviderService {

    ServiceProviderDto findById(Long id);

    List<ServiceProviderDto> findAll();

    ServiceProviderDto save(ServiceProviderCreationDto dto);

    void update(Long id, ServiceProviderCreationDto dto);

    void delete(Long id);

}
