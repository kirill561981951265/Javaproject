package by.kir.spring_lr2.rest;

import by.kir.spring_lr2.aop.LogAnnotation;
import by.kir.spring_lr2.dto.AppointmentOutDto;
import by.kir.spring_lr2.dto.PetRegistrDto;
import by.kir.spring_lr2.dto.ResponseDto;
import by.kir.spring_lr2.exceptions.PetException;
import by.kir.spring_lr2.repository.PetRep;
import by.kir.spring_lr2.mapping.PetMapper;
import by.kir.spring_lr2.service.AppointmentService;
import by.kir.spring_lr2.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name="Pet REST Controller", description="The controller accepts requests from the doctor and user pages")
@RestController
@RequestMapping(value = "/api/pet")
public class PetRestController {

    private final PetService petService;
    private final PetMapper petMapper;
    private final by.kir.spring_lr2.repository.PetRep PetRep;
    private final AppointmentService appointmentService;

    @Autowired
    public PetRestController(PetService petService, PetMapper petMapper, PetRep petRep, AppointmentService appointmentService) {
        this.petService = petService;
        this.petMapper = petMapper;
        this.PetRep = petRep;
        this.appointmentService = appointmentService;
    }

    @Operation(
            summary = "Getting user's pets",
            description = "Allows you to get user's pets"
    )
    @LogAnnotation
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getPets(@PathVariable(value = "id") Long id) throws Exception {
        List<PetRegistrDto> list = petService.getPets(id);
            if(list.size() == 0)
                return new ResponseEntity<>(new ResponseDto("Not found"), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(
            summary = "Pet addition",
            description = "Allows you to add a pet"
    )
    @LogAnnotation
    @PostMapping("/add")
    public ResponseEntity addPet(@Valid @RequestBody PetRegistrDto petRegistrDto){
        petService.add(petRegistrDto);
        return new ResponseEntity<>(new ResponseDto(), HttpStatus.OK);
    }

    @Operation(
            summary = "Pet deletion",
            description = "Allows you to delete a pet"
    )
    @LogAnnotation
    @DeleteMapping("/{id}")
    public ResponseEntity deletePet(@PathVariable(value = "id") Long id){
        petService.deleteById(id);
        return new ResponseEntity<>(new ResponseDto(), HttpStatus.OK);
    }

    @Operation(
            summary = "Getting pet's appointments",
            description = "Allows you to get pet's appointments"
    )
    @LogAnnotation
    @GetMapping("/history/{id}")
    public ResponseEntity petHistory(@PathVariable(value = "id") Long id, @RequestParam(value="sort") int sort, @RequestParam(value="page") int page) throws PetException {
        List<AppointmentOutDto> list = appointmentService.getAppointments(id, sort, page);
        if(list.size() == 0)
            return new ResponseEntity<>(new ResponseDto("Not found"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
