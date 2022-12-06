package by.kir.spring_lr2.rest;

import by.kir.spring_lr2.exceptions.AppointmentException;
import by.kir.spring_lr2.exceptions.PetException;
import by.kir.spring_lr2.exceptions.UserException;
import by.kir.spring_lr2.service.AppointmentService;
import by.kir.spring_lr2.aop.LogAnnotation;
import by.kir.spring_lr2.dto.AppointmentInfoDto;
import by.kir.spring_lr2.dto.NewAppointment;
import by.kir.spring_lr2.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.ParseException;

@Tag(name="Appointment REST Controller", description="The controller accepts requests from the doctor and user pages")
@RestController
@RequestMapping(value = "/api/appointment/")
public class AppointmentRestController {
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentRestController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Operation(
            summary = "Getting add appointment page",
            description = "Allows you to get a add appointment page"
    )
    @LogAnnotation
    @GetMapping("/add")
    public ModelAndView addAppointment(@RequestParam(value="idu") Long idu, @RequestParam(value="idp") Long idp, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addAppointment");
        NewAppointment ap = new NewAppointment();
        ap.setIdDoctor(idu);
        ap.setIdPet(idp);
        model.addAttribute("ap", ap);
        return modelAndView;
    }

    @Operation(
            summary = "Appointment addition",
            description = "Allows you to add a appointment"
    )
    @LogAnnotation
    @PostMapping("/doctor/save")
    public ResponseEntity save(@Valid @RequestBody NewAppointment ap) throws ParseException, UserException, PetException {
        appointmentService.add(ap);
        return new ResponseEntity<>(new ResponseDto(), HttpStatus.OK);
    }

    @Operation(
            summary = "Appointment deletion",
            description = "Allows you to delete a appointment"
    )
    @LogAnnotation
    @DeleteMapping("/doctor/delete/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Long id){
        appointmentService.deleteById(id);
        return new ResponseEntity<>(new ResponseDto(), HttpStatus.OK);
    }


    @Hidden
    @LogAnnotation
    @GetMapping("/doctor/check")
    public ResponseEntity check(){
        return new ResponseEntity(new ResponseDto(), HttpStatus.OK);
    }

    @Operation(
            summary = "Getting info appointment page",
            description = "Allows you to get a info appointment page"
    )
    @LogAnnotation
    @GetMapping("/{id}")
    public ModelAndView get(@PathVariable(value = "id") Long id, Model model) throws AppointmentException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("appointment");
        AppointmentInfoDto ap = appointmentService.getInfo(id);
        model.addAttribute("ap", ap);
        return modelAndView;
    }

    @Hidden
    @LogAnnotation
    @GetMapping("/get/check")
    public ResponseEntity checkGet(){return new ResponseEntity(new ResponseDto(), HttpStatus.OK);}
}
