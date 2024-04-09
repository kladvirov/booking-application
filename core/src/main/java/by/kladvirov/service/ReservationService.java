package by.kladvirov.service;

import by.kladvirov.dto.ReservationCreationDto;
import by.kladvirov.dto.ReservationDto;

import java.util.List;

public interface ReservationService {

    ReservationDto findById(Long id);

    List<ReservationDto> findAll();

    ReservationDto save(ReservationCreationDto dto);

    void update(Long id, ReservationCreationDto dto);

    void delete(Long id);

}