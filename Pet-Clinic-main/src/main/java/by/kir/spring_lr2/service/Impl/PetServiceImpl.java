package by.kir.spring_lr2.service.Impl;

import by.kir.spring_lr2.dto.PetOutDto;
import by.kir.spring_lr2.dto.PetRegistrDto;
import by.kir.spring_lr2.exceptions.PetException;
import by.kir.spring_lr2.exceptions.UserException;
import by.kir.spring_lr2.mapping.PetMapper;
import by.kir.spring_lr2.model.Pet;
import by.kir.spring_lr2.model.Status;
import by.kir.spring_lr2.model.User;
import by.kir.spring_lr2.repository.PetRep;
import by.kir.spring_lr2.repository.UserRep;
import by.kir.spring_lr2.service.PetService;
import by.kir.spring_lr2.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PetServiceImpl implements PetService {
    private final PetRep petRep;
    private final PetMapper petMapper;
    private final UserService userService;

    @Autowired
    public PetServiceImpl(PetRep petRep, PetMapper petMapper, UserRep userRep, UserService userService) {
        this.petRep = petRep;
        this.petMapper = petMapper;
        this.userService = userService;
    }

    @Override
    public void add(PetRegistrDto petRegistrDto) {
        Pet pet = petMapper.toModel(petRegistrDto);
        Date date = new Date();
        pet.setCreated(date);
        pet.setUpdated(date);
        pet.setStatus(Status.ACTIVE);
        save(pet);
    }

    @Override
    public List<PetRegistrDto> getPets(Long id) throws UserException {
        List<Pet> list = findAllByUser(userService.findById(id));
        List<PetRegistrDto> listp = new ArrayList<>();
        for (Pet p: list) {
            listp.add(petMapper.toPetRegistrDto(p));
        }
        return listp;
    }

    @Override
    public List<PetOutDto> getPetsByFio(String fio) throws Exception {
        User user = userService.findByName(fio);
        List<Pet> list = findAllByUser(user);
        List<PetOutDto> listp = new ArrayList<>();
        for (Pet p: list) {
            listp.add(petMapper.toPetOutDto(p));
        }
        return listp;
    }

    @Override
    public Pet findById(Long id) throws PetException {
        Pet pet =  petRep.findById(id).orElse(null);
        if (pet == null) {
            log.warn("In findById - pet not found with id: " + id);
            throw new PetException("pet not found with id: " + id);
        }
        return pet;
    }

    @Override
    public void save(Pet pet) {
        petRep.save(pet);
        log.info("In save - pet successfully saved");
    }

    @Override
    public void deleteById(Long id) {
        petRep.deleteById(id);
        log.info("In delete - pet with id: {} successfully deleted", id);
    }

    @Override
    public List<Pet> findAllByUser(User user) {
        return petRep.findAllByUser(user);
    }


}
