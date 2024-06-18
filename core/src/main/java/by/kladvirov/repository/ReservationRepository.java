package by.kladvirov.repository;

import by.kladvirov.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByService_id(Long serviceId);

    Optional<Reservation> findByUsernameAndId(String login, Long id);

    List<Reservation> findAllByUsername(String username);

}
