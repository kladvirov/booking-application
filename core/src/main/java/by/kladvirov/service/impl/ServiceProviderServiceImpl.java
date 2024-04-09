package by.kladvirov.service.impl;

import by.kladvirov.dto.ServiceProviderCreationDto;
import by.kladvirov.dto.ServiceProviderDto;
import by.kladvirov.enums.Status;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.ServiceProviderMapper;
import by.kladvirov.model.ServiceProvider;
import by.kladvirov.repository.ServiceProviderRepository;
import by.kladvirov.service.ServiceProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private final ServiceProviderRepository repository;

    private final ServiceProviderMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public ServiceProviderDto findById(Long id) {
        ServiceProviderDto dto = mapper.toDto(repository.findById(id).orElseThrow(() -> new ServiceException("Cannot find service provider by id in service", HttpStatus.BAD_REQUEST)));
        if(dto.getStatus() == Status.DELETED){
            return null;
        }
        return dto;
    }

    @Transactional(readOnly = true)
    public List<ServiceProviderDto> findAll() {
        return mapper.toDto(repository.findAll().stream()
                .filter(serviceProvider -> serviceProvider.getStatus() != Status.DELETED)
                .toList()
        );
    }

    @Transactional
    public ServiceProviderDto save(ServiceProviderCreationDto dto) {
        if(hasName(dto)){
            throw new ServiceException("Cannot save service provider in service because its name is placed yet", HttpStatus.BAD_REQUEST);
        }
        ServiceProvider entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    public void update(Long id, ServiceProviderCreationDto dto) {
        ServiceProvider serviceProvider = repository.findById(id).orElseThrow(() -> new ServiceException("Cannot update service provider in service", HttpStatus.BAD_REQUEST));
        ServiceProvider mappedServiceProvider = mapper.toEntity(dto);
        updateServiceProvider(serviceProvider, mappedServiceProvider);
        repository.save(serviceProvider);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void updateServiceProvider(ServiceProvider target, ServiceProvider source) {
        target.setUpdatedAt(source.getUpdatedAt());
        target.setStatus(source.getStatus());
        target.setName(source.getName());
        target.setCreatedAt(source.getCreatedAt());
        target.setLocation(source.getLocation());
        target.setUpdatedAt(source.getUpdatedAt());
        target.setType(source.getType());
    }

    private boolean hasName(ServiceProviderCreationDto dto){
        ServiceProvider serviceProvider = repository.findByName(dto.getName());
        return serviceProvider != null;
    }

}
