package by.kladvirov.service.impl;

import by.kladvirov.dto.ServiceCreationDto;
import by.kladvirov.dto.ServiceDto;
import by.kladvirov.enums.Status;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.ServiceMapper;
import by.kladvirov.repository.ServiceRepository;
import by.kladvirov.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository repository;

    private final ServiceMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public ServiceDto findById(Long id) {
        by.kladvirov.model.Service service = repository.findById(id).orElseThrow(() -> new ServiceException("Cannot find service by id in service", HttpStatus.BAD_REQUEST));
        if(service.getStatus() == Status.DELETED){
            return null;
        }
        return mapper.toDto(service);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ServiceDto> findAll() {
        return mapper.toDto(repository.findAll().stream()
                .filter(service -> service.getStatus() != Status.DELETED)
                .toList()
        );
    }

    @Transactional
    @Override
    public ServiceDto save(ServiceCreationDto dto) {
        by.kladvirov.model.Service entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    @Override
    public void update(Long id, ServiceCreationDto dto) {
        by.kladvirov.model.Service service = repository.findById(id).orElseThrow(() -> new ServiceException("Cannot update service in service", HttpStatus.BAD_REQUEST));
        by.kladvirov.model.Service mappedService = mapper.toEntity(dto);
        updateService(service, mappedService);
        repository.save(service);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void updateService(by.kladvirov.model.Service target, by.kladvirov.model.Service source) {
        target.setServiceProvider(source.getServiceProvider());
        target.setUpdatedAt(source.getUpdatedAt());
        target.setStatus(source.getStatus());
        target.setSlot(source.getSlot());
        target.setCreatedAt(source.getCreatedAt());
        target.setPricePerHour(source.getPricePerHour());
        target.setUpdatedAt(source.getUpdatedAt());
    }

}
