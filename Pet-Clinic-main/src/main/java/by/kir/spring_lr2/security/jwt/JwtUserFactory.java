package by.kir.spring_lr2.security.jwt;

import by.kir.spring_lr2.model.Role;
import by.kir.spring_lr2.model.Status;
import by.kir.spring_lr2.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory(){

    }

    public static JwtUser create (User user){
        return new JwtUser(user.getId(),
                    user.getUsername(),
                    user.getName(),
                    user.getPhoneNumber(),
                    user.getEmail(),
                    user.getPassword(),
                    mapToGrantedAuthority(new ArrayList<>(user.getRoles())),
                    user.getStatus().equals(Status.ACTIVE),
                    user.getUpdated()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthority(List<Role> userRoles){
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
