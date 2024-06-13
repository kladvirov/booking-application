package by.andron.service.impl;

import by.andron.dto.ServiceCreationDto;
import by.kladvirov.dto.core.ServiceDto;
import by.andron.exception.ServiceException;
import by.andron.mapper.ServiceMapper;
import by.andron.repository.ServiceRepository;
import by.andron.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository repository;

    private final ServiceMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public ServiceDto findById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new ServiceException("Cannot find service by id in service", HttpStatus.BAD_REQUEST)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ServiceDto> findAll() {
        return mapper.toDto(repository.findAll());
    }

    @Transactional
    @Override
    public ServiceDto save(ServiceCreationDto dto) {
        by.andron.model.Service entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    @Override
    public void update(Long id, ServiceCreationDto dto) {
        by.andron.model.Service service = repository.findById(id)
                .orElseThrow(() -> new ServiceException("Cannot update service in service", HttpStatus.BAD_REQUEST));
        by.andron.model.Service mappedService = mapper.toEntity(dto);
        updateService(service, mappedService);
        repository.save(service);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void updateService(by.andron.model.Service target, by.andron.model.Service source) {
        target.setServiceProvider(source.getServiceProvider());
        target.setUpdatedAt(source.getUpdatedAt());
        target.setStatus(source.getStatus());
        target.setSlot(source.getSlot());
        target.setCreatedAt(source.getCreatedAt());
        target.setPricePerHour(source.getPricePerHour());
        target.setUpdatedAt(source.getUpdatedAt());
    }

}
