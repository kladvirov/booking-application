package by.andron.repository;

import by.andron.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

    ServiceProvider findByName(String name);

}
