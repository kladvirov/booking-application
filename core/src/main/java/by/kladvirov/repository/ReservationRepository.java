package by.kladvirov.repository;

import by.kladvirov.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByService_id(Long serviceId);

}
