package by.kladvirov.service.impl;

import by.kladvirov.dto.EmailDto;
import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.dto.UserDto;
import by.kladvirov.entity.Role;
import by.kladvirov.entity.User;
import by.kladvirov.enums.UserStatus;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.UserMapper;
import by.kladvirov.repository.UserRepository;
import by.kladvirov.service.RoleService;
import by.kladvirov.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final RoleService roleService;

    @Transactional(readOnly = true)
    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new ServiceException("There is no such user", HttpStatus.NOT_FOUND));
        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User getByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new ServiceException("There is no such user with following login", HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> findUsersByRoleName(String roleName) {
        return userMapper.toDto(userRepository.findUsersByRoleName(roleName));
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> findUserRoles(String login) {
        return userRepository.findUserRoles(login);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> findAll(Pageable pageable) {
        return userMapper.toDto(userRepository.findAllUsers(pageable));
    }

    @Transactional(readOnly = true)
    @Override
    public User findByEmail(EmailDto email) {
        return userRepository.findUserByEmail(email.getEmail())
                .orElseThrow(() -> new ServiceException("Cannot find user with email " + email, HttpStatus.NOT_FOUND));
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    @Override
    public UserDto save(UserCreationDto userDto) {
        User entity = userMapper.toEntity(userDto);
        return userMapper.toDto(userRepository.save(entity));
    }

    @Transactional
    @Override
    public void update(Long id, UserCreationDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new ServiceException("There is no such user", HttpStatus.NOT_FOUND));
        User mappedUser = userMapper.toEntity(userDto);
        updateUser(user, mappedUser);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void updateUserRoles(Long id, List<Long> roleIds) {
        User user = userRepository.findUserById(id).orElseThrow(() -> new ServiceException("There is no such user", HttpStatus.NOT_FOUND));
        List<Role> roles = roleService.findAllByIdIn(roleIds);
        user.getRoles().addAll(roles);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUserRoles(Long id, List<Long> roleIds) {
        User user = userRepository.findUserById(id).orElseThrow(() -> new ServiceException("There is no such user", HttpStatus.NOT_FOUND));
        user.getRoles().removeIf(role -> roleIds.contains(role.getId()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteByLogin(String login) {
        userRepository.deleteByLogin(login);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updatePassword(String login, String password) {
        userRepository.updatePassword(login, password);
    }

    @Transactional
    @Override
    public void updatePasswordByEmail(String email, String password) {
        userRepository.updatePasswordByEmail(email, password);
    }

    @Transactional
    @Override
    public void updateStatus(Long id, UserStatus status) {
        userRepository.updateStatus(id, status);
    }

    private void updateUser(User user, User mappedUser) {
        user.setName(mappedUser.getName());
        user.setSurname(mappedUser.getSurname());
        user.setLogin(mappedUser.getLogin());
        user.setPassword(mappedUser.getPassword());
        user.setEmail(mappedUser.getEmail());
        user.setRoles(mappedUser.getRoles());
    }

}
