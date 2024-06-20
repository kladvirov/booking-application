package by.kladvirov.repository;

import by.kladvirov.entity.User;
import by.kladvirov.enums.UserStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User u left join fetch u.roles where u.id = :id")
    Optional<User> findUserById(@Param("id") Long id);

    @Query("from User u left join fetch u.roles r left join fetch r.authorities where u.login = :login")
    Optional<User> findByLogin(@Param("login") String login);

    @Query("from User u left join fetch u.roles")
    List<User> findAllUsers(Pageable pageable);

    Boolean existsByEmail(String email);

    @Query("from User u left join u.roles r where r.name = :roleName")
    List<User> findUsersByRoleName(@Param("roleName") String roleName);

    @Query("select r.name from Role r left join r.users u where u.login = :login")
    List<String> findUserRoles(@Param("login") String login);

    @Modifying
    @Query("update User u set u.password = :password where u.login = :login")
    void updatePassword(@Param("login") String login, @Param("password") String password);

    @Modifying
    @Query("update User u set u.balance = :balance where u.login = :login")
    void updateBalance(@Param("login") String login, @Param("balance") BigDecimal balance);

    @Modifying
    @Query("update User u set u.status = :status where u.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") UserStatus status);

    @Modifying
    void deleteByLogin(String login);

}
