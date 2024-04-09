package by.kladvirov.repository;

import by.kladvirov.dto.ReservationDto;
import by.kladvirov.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByService_id (Long serviceId);

}
