package by.kir.spring_lr2.mapping;

import by.kir.spring_lr2.dto.AppointmentInfoDto;
import by.kir.spring_lr2.dto.AppointmentOutDto;
import by.kir.spring_lr2.dto.NewAppointment;
import by.kir.spring_lr2.exceptions.PetException;
import by.kir.spring_lr2.exceptions.UserException;
import by.kir.spring_lr2.model.Pet;
import by.kir.spring_lr2.model.User;
import by.kir.spring_lr2.service.PetService;
import by.kir.spring_lr2.model.Appointment;
import by.kir.spring_lr2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentMapper {
    private final UserService userService;
    private final PetService petService;

    @Autowired
    public AppointmentMapper(UserService userService, PetService petService) {
        this.userService = userService;
        this.petService = petService;
    }

    public AppointmentOutDto toAppointmentOutDto (Appointment appointment){
        AppointmentOutDto dto = new AppointmentOutDto();
        dto.setId(appointment.getId());
        dto.setComplaints(appointment.getComplaintsC());
        dto.setDiagnosis(appointment.getDiagnosis());
        dto.setDate(appointment.getCreated().toString().substring(0, 10));
        return dto;
    }

    public Appointment toModel(NewAppointment dto) throws UserException, PetException {
        Appointment ap = new Appointment();
        ap.setWeight(Float.parseFloat(dto.getWeight()));
        ap.setTemp(Float.parseFloat(dto.getTemp()));
        ap.setHistory(dto.getHistory());
        ap.setAnamnesis(dto.getAnamnesis());
        ap.setComplaintsC(dto.getComplaints());
        ap.setConditionC(dto.getCondition());
        ap.setDiagnostics(dto.getDiagnostics());
        ap.setDiagnosis(dto.getDiagnosis());
        ap.setPurpose(dto.getPurpose());
        ap.setUser(userService.findById(dto.getIdDoctor()));
        ap.setPet(petService.findById(dto.getIdPet()));
        return ap;
    }

    public AppointmentInfoDto toInfoDto(Appointment ap){
        AppointmentInfoDto dto = new AppointmentInfoDto();
        dto.setWeight(ap.getWeight().toString());
        dto.setTemp(ap.getTemp().toString());
        dto.setHistory(ap.getHistory());
        dto.setAnamnesis(ap.getAnamnesis());
        dto.setComplaints(ap.getComplaintsC());
        dto.setCondition(ap.getConditionC());
        dto.setDiagnostics(ap.getDiagnostics());
        dto.setDiagnosis(ap.getDiagnosis());
        dto.setPurpose(ap.getPurpose());
        dto.setAge(ap.getAge());
        dto.setDate(ap.getCreated().toString().substring(0,10));

        Pet pet = ap.getPet();
        dto.setName(pet.getName());
        dto.setBreed(pet.getBreed());
        dto.setGender(pet.getGender());
        dto.setBday(pet.getBday());
        dto.setType(pet.getType());

        User user = pet.getUser();
        dto.setUsername(user.getName());

        User doctor = ap.getUser();
        dto.setDoctorname(doctor.getName());

        return dto;
    }
}
