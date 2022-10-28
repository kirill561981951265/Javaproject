package by.kir.spring_lr2.mapping;

import by.kir.spring_lr2.dto.PetOutDto;
import by.kir.spring_lr2.dto.PetRegistrDto;
import by.kir.spring_lr2.model.Pet;
import by.kir.spring_lr2.repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetMapper {
    private final UserRep userRep;

    @Autowired
    public PetMapper(UserRep userRep) {
        this.userRep = userRep;
    }

    public Pet toModel (PetRegistrDto petRegistrDto){
        Pet pet = new Pet();
        pet.setName(petRegistrDto.getName());
        pet.setType(petRegistrDto.getType());
        pet.setBreed(petRegistrDto.getBreed());
        pet.setGender(petRegistrDto.getGender());
        pet.setBday(petRegistrDto.getBday());
        pet.setUser(userRep.findById(petRegistrDto.getIdUser()).get());
        return pet;
    }

    public PetRegistrDto toPetRegistrDto (Pet pet){
        PetRegistrDto dto = new PetRegistrDto();
        dto.setName(pet.getName());
        dto.setType(pet.getType());
        dto.setBreed(pet.getBreed());
        dto.setGender(pet.getGender());
        dto.setBday(pet.getBday());
        dto.setId(pet.getId());
        return dto;
    }

    public PetOutDto toPetOutDto(Pet pet){
        PetOutDto dto = new PetOutDto();
        dto.setName(pet.getName());
        dto.setType(pet.getType());
        dto.setBreed(pet.getBreed());
        dto.setGender(pet.getGender());
        dto.setBday(pet.getBday());
        dto.setIdUser(pet.getUser().getId());
        dto.setFio(pet.getUser().getName());
        dto.setId(pet.getId());
        return dto;
    }
}
