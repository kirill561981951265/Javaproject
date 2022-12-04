package by.kir.spring_lr2;

import by.kir.spring_lr2.model.Role;
import by.kir.spring_lr2.model.Status;
import by.kir.spring_lr2.model.User;
import by.kir.spring_lr2.security.JwtUserDetailsService;
import by.kir.spring_lr2.security.jwt.JwtTokenProvider;
import by.kir.spring_lr2.model.*;
import by.kir.spring_lr2.security.jwt.JwtUserFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class IntegrationTests {

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    private JwtUserDetailsService ser;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    private MockMvc mockMvc;

    protected void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    void testSetJwtTokenProviderWithTwoRolesByDoctor() throws Exception {
        setUp();

        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_DOCTOR"));
        roles.add(new Role("ROLE_ADMIN"));
        User user = new User();
        user.setStatus(Status.ACTIVE);
        user.setUsername("test");
        user.setRoles(roles);
        when(ser.loadUserByUsername("test")).thenReturn(JwtUserFactory.create(user));

        String token = jwtTokenProvider.createToken("test");
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/doctor/users").header("Authorization", "Bearer_" + token))
                .andExpect(status().isOk());
    }

    @Test
    void testSetJwtTokenProviderWithTwoRolesByAdmin() throws Exception {
        setUp();

        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_DOCTOR"));
        roles.add(new Role("ROLE_ADMIN"));
        User user = new User();
        user.setStatus(Status.ACTIVE);
        user.setUsername("test");
        user.setRoles(roles);
        when(ser.loadUserByUsername("test")).thenReturn(JwtUserFactory.create(user));

        String token = jwtTokenProvider.createToken("test");
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users").header("Authorization", "Bearer_" + token))
                .andExpect(status().isOk());
    }
}
