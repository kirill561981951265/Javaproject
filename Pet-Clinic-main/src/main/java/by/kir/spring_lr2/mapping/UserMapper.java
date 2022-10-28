package by.kir.spring_lr2.mapping;

import by.kir.spring_lr2.dto.*;
import by.kir.spring_lr2.model.Role;
import by.kir.spring_lr2.model.User;
import by.kir.spring_lr2.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMapper {

    public User toModel(UserRegistrDto dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        return user;
    }

    public User toModel(UserDto userDto){
       User user = new User();
       user.setEmail(userDto.getEmail());
       return user;
    }

    public User toModel(UserAuthDto dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }

    public UserAboutDto toUserAboutDto(User user){
        UserAboutDto dto = new UserAboutDto();
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());
        dto.setId(user.getId());
        return dto;
    }

    public UserAdminDto toUserAdminDto (User user){
        UserAdminDto dto = new UserAdminDto();
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());
        dto.setId(user.getId());
        dto.setId(user.getId());

        String r = "";
        List<Role> roles = user.getRoles();
        for (Role role: roles) {
            r += role.getName();
        }
        dto.setRoles(r);
        return dto;
    }
}
