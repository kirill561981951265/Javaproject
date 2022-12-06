package by.kir.spring_lr2.rest;

import by.kir.spring_lr2.exceptions.RegistrationException;
import by.kir.spring_lr2.mapping.UserMapper;
import by.kir.spring_lr2.service.UserService;
import by.kir.spring_lr2.aop.LogAnnotation;
import by.kir.spring_lr2.dto.ResponseDto;
import by.kir.spring_lr2.dto.UserRegistrDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name="Registration REST Controller", description="The controller accepts requests from the registration page")
@RestController
@RequestMapping(value = "api/registration")
public class RegistrationRestController {

    private final UserMapper userMapper;
    private final UserService userService;

@Autowired
    public RegistrationRestController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }


    @Operation(
            summary = "User's registration",
            description = "Allows you to register a user"
    )
    @LogAnnotation
    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrDto user) throws RegistrationException {
        ResponseDto  responseDto= new ResponseDto();
        userService.register(user, "ROLE_USER");
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }
}
