package by.andron.service;

import by.andron.dto.ServiceProviderCreationDto;
import by.kladvirov.dto.core.ServiceProviderDto;

import java.util.List;

public interface ServiceProviderService {

    ServiceProviderDto findById(Long id);

    List<ServiceProviderDto> findAll();

    ServiceProviderDto save(ServiceProviderCreationDto dto);

    void update(Long id, ServiceProviderCreationDto dto);

    void delete(Long id);

}
