package by.kladvirov.service.impl;

import by.kladvirov.dto.ReservationCreationDto;
import by.kladvirov.dto.ReservationDto;
import by.kladvirov.enums.Status;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.ReservationMapper;
import by.kladvirov.model.Reservation;
import by.kladvirov.repository.ReservationRepository;
import by.kladvirov.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;

    private final ReservationMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public ReservationDto findById(Long id){
        Reservation reservation = repository.findById(id).orElseThrow(() -> new ServiceException("Cannot find reservation by id", HttpStatus.BAD_REQUEST));
        if (reservation.getStatus() == Status.DELETED){
            return null;
        }
        return mapper.toDto(reservation);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReservationDto> findAll() {
        return mapper.toDto(repository.findAll().stream()
                .filter(reservation -> reservation.getStatus() != Status.DELETED)
                .toList()
        );
    }

    @Transactional
    @Override
    public ReservationDto save(ReservationCreationDto dto) {
        if (hasReservation(dto)) {
            throw new ServiceException("Cannot save reservation in service because there is at this time a reservation", HttpStatus.BAD_REQUEST);
        }
        Reservation entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    @Override
    public void update(Long id, ReservationCreationDto dto) {
        if (hasReservation(dto)) {
            throw new ServiceException("Cannot update reservation in service because there is at this time a reservation", HttpStatus.BAD_REQUEST);
        }
        Reservation reservation = repository.findById(id).orElseThrow(() -> new ServiceException("Cannot update reservation in service", HttpStatus.BAD_REQUEST));
        Reservation mappedReservation = mapper.toEntity(dto);
        updateReservation(reservation, mappedReservation);
        repository.save(reservation);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void updateReservation(Reservation target, Reservation source) {
        target.setService(source.getService());
        target.setDateFrom(source.getDateFrom());
        target.setDateTo(source.getDateTo());
        target.setStatus(source.getStatus());
        target.setExpiresAt(source.getExpiresAt());
        target.setOrderedAt(source.getOrderedAt());
        target.setUserId(source.getUserId());
    }

    private boolean hasReservation(ReservationCreationDto dto) {
        List<Reservation> reservationsByServiceId = repository.findByService_id(dto.getServiceId());
        return reservationsByServiceId.stream()
                .anyMatch(reservation -> dto.getDateFrom().isAfter(reservation.getDateFrom()) &&
                        dto.getDateFrom().isBefore(reservation.getDateTo()) ||
                        dto.getDateTo().isAfter(reservation.getDateFrom()) &&
                                dto.getDateFrom().isBefore(reservation.getDateTo()) ||
                                        dto.getDateFrom().isBefore(reservation.getDateFrom()) &&
                                                dto.getDateTo().isAfter(reservation.getDateTo())
                );
    }

}
