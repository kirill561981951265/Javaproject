package by.kir.spring_lr2.rest;

import by.kir.spring_lr2.exceptions.UserException;
import by.kir.spring_lr2.mapping.UserMapper;
import by.kir.spring_lr2.repository.UserRep;
import by.kir.spring_lr2.service.UserService;
import by.kir.spring_lr2.aop.LogAnnotation;
import by.kir.spring_lr2.dto.ResponseDto;
import by.kir.spring_lr2.dto.UserAboutDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Tag(name="User REST Controller", description="The controller accepts requests from the user page")
@RestController
@RequestMapping(value = "/api/user")
public class UserRestController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRep userRep;

    @Autowired
    public UserRestController(UserService userService, UserMapper userMapper, UserRep userRep) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userRep = userRep;
    }

    @Operation(
            summary = "Getting info about user",
            description = "Allows you to get info about user"
    )
    @LogAnnotation
    @GetMapping("/about")
    public ResponseEntity getUser(Principal principal){
        UserAboutDto user = userMapper.toUserAboutDto(userService.findByUsername(principal.getName()));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(
            summary = "Changing user data",
            description = "Allows you to change user date"
    )
    @LogAnnotation
    @PutMapping("/save")
    public ResponseEntity save(@Valid @RequestBody UserAboutDto userAboutDto) throws UserException {
            userService.update(userAboutDto);
            return new ResponseEntity<>(new ResponseDto(), HttpStatus.OK);
    }
}
