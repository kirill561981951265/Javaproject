package by.kir.spring_lr2;

import by.kir.spring_lr2.repository.RoleRep;
import by.kir.spring_lr2.repository.UserRep;
import by.kir.spring_lr2.dto.UserAdminDto;
import by.kir.spring_lr2.model.Role;
import by.kir.spring_lr2.model.User;
import by.kir.spring_lr2.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.junit.Assert;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class SpringLr2ApplicationTests {

    @MockBean
    private UserRep userRep;

    @MockBean
    private RoleRep roleRep;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testGetAllFio() {
        List<User> users = new ArrayList();
        User u = new User();
        u.setName("testUser");
        List<Role> roles = new ArrayList<>();
        Role r =new Role("ROLE_USER");
        r.setId(1L);
        roles.add(r);
        u.setRoles(roles);
        users.add(u);
        User u2 = new User();
        u2.setName("TestAdmin");
        roles = new ArrayList<>();
        roles.add(new Role("ROLE_ADMIN"));
        u2.setRoles(roles);
        users.add(u2);
        List<String> strs = new ArrayList<>();
        strs.add("testUser");

        when(userRep.findAll()).thenReturn(users);
        when(roleRep.findByName("ROLE_USER")).thenReturn(r);
        Assert.assertEquals(userService.getAllFio(), strs);
    }

    @Test
    void testFindByUsername(){
        List<User> users = new ArrayList();
        User u = new User();
        u.setUsername("testUser");
        users.add(u);
        User u2 = new User();
        u2.setUsername("TestAdmin");
        users.add(u2);

        when(userRep.findByUsername("TestAdmin")).thenReturn(u2);
        Assert.assertEquals(userService.findByUsername("TestAdmin"), u2);
    }

    @Test
    void testFindByEmail(){
        List<User> users = new ArrayList();
        User u = new User();
        u.setEmail("a@a");
        users.add(u);
        User u2 = new User();
        u2.setEmail("b@b");
        users.add(u2);

        when(userRep.findByEmail("a@a")).thenReturn(u);
        Assert.assertEquals(userService.findByEmail("a@a"), u);
    }

    @Test
    void testUsers(){
        List<User> users = new ArrayList();
        User u = new User();
        u.setId(1L);
        u.setUsername("test");
        u.setName("test");
        u.setPhoneNumber("123456789");
        u.setEmail("test@test");
        List<Role> roles = new ArrayList<>();
        Role r =new Role("ROLE_USER");
        r.setId(1L);
        roles.add(r);
        u.setRoles(roles);
        users.add(u);

        when(userRep.findAll()).thenReturn(users);
        List<UserAdminDto> res= userService.users();
        Assert.assertEquals(res.size(), 1);
        Assert.assertEquals(res.get(0).getId(), u.getId());
        Assert.assertEquals(res.get(0).getUsername(), u.getUsername());
        Assert.assertEquals(res.get(0).getName(), u.getName());
        Assert.assertEquals(res.get(0).getPhoneNumber(), u.getPhoneNumber());
        Assert.assertEquals(res.get(0).getEmail(), u.getEmail());
        Assert.assertEquals(res.get(0).getRoles(), "ROLE_USER");

    }
}
