package by.kir.spring_lr2.repository;

import by.kir.spring_lr2.model.Appointment;
import by.kir.spring_lr2.model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRep extends JpaRepository<Appointment, Long> {
    Page<Appointment> findAllByPet(Pet pet, Pageable pageable);
    List<Appointment> findAllByPet(Pet pet);
    //Page<Appointment> findAllByPetOrderByDateDesc(Pet pet, Pageable pageable);
}

