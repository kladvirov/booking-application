package by.kladvirov.service;

import by.kladvirov.dto.ReservationCreationDto;
import by.kladvirov.dto.ReservationDto;

import java.util.List;

public interface ReservationService {

    ReservationDto findById(Long id);

    List<ReservationDto> findAll();

    List<ReservationDto>findAllByUsername(String username);

    ReservationDto save(ReservationCreationDto dto);

    void update(Long id, ReservationCreationDto dto);

    void delete(Long id);

    boolean isAllowedToRead(String username, Long id);

    boolean isAllowedToCreate(String firstUsername, String secondUsername);

    boolean isAllowedToUpdate(String firstUsername, String secondUsername);

    boolean isAllowedToDelete(String username, Long id);

    boolean isAdmin(String header);

}