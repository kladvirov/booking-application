package by.andron.service;

import by.andron.dto.ReservationCreationDto;
import by.kladvirov.dto.core.ReservationDto;

import java.util.List;

public interface ReservationService {

    ReservationDto findById(Long id);

    ReservationDto findByLoginAndId(String login, Long id);

    List<ReservationDto> findAll();

    ReservationDto save(ReservationCreationDto dto, String header);

    void update(Long id, ReservationCreationDto dto);

    void delete(Long id);

}