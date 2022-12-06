package by.kir.spring_lr2.service;

import by.kir.spring_lr2.exceptions.AppointmentException;
import by.kir.spring_lr2.exceptions.PetException;
import by.kir.spring_lr2.exceptions.UserException;
import by.kir.spring_lr2.dto.AppointmentInfoDto;
import by.kir.spring_lr2.dto.AppointmentOutDto;
import by.kir.spring_lr2.dto.NewAppointment;
import by.kir.spring_lr2.model.Appointment;

import java.text.ParseException;
import java.util.List;

public interface AppointmentService {
    List<AppointmentOutDto> getAppointments(Long id, int sort, int page) throws PetException;

    void add(NewAppointment ap) throws ParseException, UserException, PetException;

    AppointmentInfoDto getInfo (Long id) throws AppointmentException;

    void save(Appointment appointment);

    void deleteById(Long id);

    Appointment findById(Long id) throws AppointmentException;
}
