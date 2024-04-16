package by.kladvirov.service;

import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.dto.UserDto;
import by.kladvirov.entity.User;
import by.kladvirov.enums.UserStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserService {

    UserDto findById(Long id);

    User findByLogin(String login);

    List<UserDto> findUsersByRoleName(String roleName);

    List<String> findUserRoles(String login);

    List<UserDto> findAll(Pageable pageable);

    Boolean existsByEmail(String email);

    UserDto save(UserCreationDto userCreationDto);

    void update(Long id, UserCreationDto userCreationDto);

    void updateUserRoles(Long id, List<Long> roleIds);

    void deleteUserRoles(Long id, List<Long> roleIds);

    void deleteByLogin(String login);

    void delete(Long id);

    void updatePassword(String login, String password);

    void updateStatus(Long id, UserStatus status);

}
