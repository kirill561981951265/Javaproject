package by.kir.spring_lr2.service.Impl;

import by.kir.spring_lr2.dto.UserAboutDto;
import by.kir.spring_lr2.dto.UserAdminDto;
import by.kir.spring_lr2.dto.UserRegistrDto;
import by.kir.spring_lr2.exceptions.RegistrationException;
import by.kir.spring_lr2.exceptions.UserException;
import by.kir.spring_lr2.mapping.UserMapper;
import by.kir.spring_lr2.model.Role;
import by.kir.spring_lr2.model.Status;
import by.kir.spring_lr2.model.User;
import by.kir.spring_lr2.repository.RoleRep;
import by.kir.spring_lr2.repository.UserRep;
import by.kir.spring_lr2.service.MailSender;
import by.kir.spring_lr2.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRep userRep;
    private final RoleRep roleRep;
    private final PasswordEncoder passwordEncoder;
    private final MailSender mailSender;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRep userRep, RoleRep roleRep, PasswordEncoder passwordEncoder, MailSender mailSender, UserMapper userMapper) {
        this.userRep = userRep;
        this.roleRep = roleRep;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.userMapper = userMapper;
    }

    @Override
    public void register(UserRegistrDto userRegistrDto, String role) throws RegistrationException {

        User user = userMapper.toModel(userRegistrDto);
        if (findByUsername(user.getUsername()) != null) {
            throw new RegistrationException("Username is already taken");
        }
        if (findByEmail(user.getEmail()) != null) {
            throw new RegistrationException("Email is already taken");
        }
        Role roleUser = roleRep.findByName(role);
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setRoles(userRoles);
        Date date = new Date();
        user.setCreated(date);
        user.setUpdated(date);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);

        save(user);
    }

    @Override
    public List<User> getAll() {
        List<User> result = findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public List<String> getAllFio() {
        List<User> result = findAll();
        List<String> list = new ArrayList<>();
        for (User user: result) {
            if (user.getRoles().contains(roleRep.findByName("ROLE_USER")))
                list.add(user.getName());
        }
        log.info("IN getAllString - {} users found", result.size());
        return list;
    }

    @Override
    public User findByUsername(String username) {
        return userRep.findByUsername(username);
    }


    public boolean findByUsernameAndPassword(String username, String password) {
        User user = findByUsername(username);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public User findByEmail(String email) {
        User result = userRep.findByEmail(email);
        return result;
    }

    @Override
    public User findById(Long id) throws UserException {
        User result = userRep.findById(id).orElse(null);
        if (result == null) {
            log.warn("In findById - User not found with id: " + id);
            throw new UserException("User not found with id: " + id);
        }
        log.info("In findById - user found by id: {}", id);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRep.deleteById(id);
        log.info("In delete - user with id: {} successfully deleted", id);
    }

    @Override
    public void update(UserAboutDto userAboutDto) throws UserException {
        User user = findById(userAboutDto.getId());
        user.setPhoneNumber(userAboutDto.getPhoneNumber());
        user.setEmail(userAboutDto.getEmail());
        save(user);
        log.info("In update - user successfully updated");
    }

    @Override
    public List<UserAdminDto> users() {
        List<User> users = findAll();
        List<UserAdminDto> usersdto = new ArrayList<>();
        for (User u: users) {
            usersdto.add(userMapper.toUserAdminDto(u));
        }
        return usersdto;
    }

    @Override
    public User findByName(String name) {
        return userRep.findByName(name);
    }

    @Override
    public List<User> findAll() {
        return userRep.findAll();
    }

    @Override
    public void save(User user) {
        userRep.save(user);
        log.info("In save - user successfully saved");
    }

}
