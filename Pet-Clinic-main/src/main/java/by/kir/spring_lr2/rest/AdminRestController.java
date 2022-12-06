package by.kir.spring_lr2.rest;

import by.kir.spring_lr2.dto.UserRegistrDto;
import by.kir.spring_lr2.exceptions.RegistrationException;
import by.kir.spring_lr2.mapping.UserMapper;
import by.kir.spring_lr2.service.UserService;
import by.kir.spring_lr2.aop.LogAnnotation;
import by.kir.spring_lr2.dto.ResponseDto;
import by.kir.spring_lr2.dto.UserAdminDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Admin REST Controller", description="The controller accepts requests from the admin page")
@RestController
@RequestMapping(value = "/api/admin/")
public class AdminRestController {

    private final UserService userService;

    private final UserMapper userMapper;

    @Autowired
    public AdminRestController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Operation(
            summary = "Doctor registration",
            description = "Allows you to register a doctor"
    )
    @LogAnnotation
    @PostMapping("/add")
    public ResponseEntity add(@RequestBody UserRegistrDto user) throws RegistrationException {
        ResponseDto responseDto= new ResponseDto();
        userService.register(user, "ROLE_DOCTOR");
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Get users",
            description = "Allows you to get a list of all users"
    )
    @LogAnnotation
    @GetMapping("/users")
    public ResponseEntity users(){
        List<UserAdminDto> list = userService.users();
        if(list.size() == 0)
            return new ResponseEntity<>(new ResponseDto("Not found"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
