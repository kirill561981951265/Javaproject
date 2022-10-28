package by.kir.spring_lr2.security;

import by.kir.spring_lr2.model.User;
import by.kir.spring_lr2.security.jwt.JwtUser;
import by.kir.spring_lr2.security.jwt.JwtUserFactory;
import by.kir.spring_lr2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        final JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("loadedUserByUsername - user with username: {} successfully loaded", username);

        return jwtUser;
    }
}