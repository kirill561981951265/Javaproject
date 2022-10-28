package by.kir.spring_lr2.service;

import by.kir.spring_lr2.dto.UserAboutDto;
import by.kir.spring_lr2.dto.UserAdminDto;
import by.kir.spring_lr2.dto.UserRegistrDto;
import by.kir.spring_lr2.exceptions.RegistrationException;
import by.kir.spring_lr2.exceptions.UserException;
import by.kir.spring_lr2.model.User;

import java.util.List;

public interface UserService {

    void register(UserRegistrDto user, String role) throws RegistrationException;

    List<User> getAll();

    List<String> getAllFio();

    boolean findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    User findByEmail(String email);

    User findById(Long id) throws UserException;

    void delete(Long id);

    void update(UserAboutDto userAboutDto) throws UserException;

    List<UserAdminDto> users();

    User findByName(String name);

    List<User> findAll();

    void save(User user);
}
