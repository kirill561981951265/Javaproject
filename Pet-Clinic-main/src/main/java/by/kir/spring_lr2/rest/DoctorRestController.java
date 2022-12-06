package by.kir.spring_lr2.rest;

import by.kir.spring_lr2.exceptions.UserException;
import by.kir.spring_lr2.mapping.UserMapper;
import by.kir.spring_lr2.service.AppointmentService;
import by.kir.spring_lr2.service.PetService;
import by.kir.spring_lr2.service.UserService;
import by.kir.spring_lr2.aop.LogAnnotation;
import by.kir.spring_lr2.dto.PetOutDto;
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
import java.util.List;

@Tag(name="Doctor REST Controller", description="The controller accepts requests from the doctor page")
@RestController
@RequestMapping(value = "/api/doctor")
public class DoctorRestController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PetService petService;

    @Autowired
    public DoctorRestController(UserService userService, UserMapper userMapper, PetService petService, AppointmentService appointmentService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.petService = petService;
    }

    @Operation(
            summary = "Getting user's pets",
            description = "Allows you to get user's pets by user's FIO"
    )
    @LogAnnotation
    @GetMapping("/search/{fio}")
    public ResponseEntity getPets(@PathVariable(value = "fio") String fio) throws Exception {
            List<PetOutDto> list = petService.getPetsByFio(fio);
            if(list.size() == 0)
                return new ResponseEntity<>(new ResponseDto("Not found"), HttpStatus.NOT_FOUND);
            System.out.println(list);
            return new ResponseEntity<>(list, HttpStatus.OK);
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

    @Operation(
            summary = "Search users by FIO",
            description = "Allows you to search users with role 'user' by FIO"
    )
    @LogAnnotation
    @GetMapping("/users")
    public ResponseEntity getUsers(){
            List<String> list = userService.getAllFio();
            if (list.size() == 0)
                return new ResponseEntity<>(new ResponseDto("Not found"), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
