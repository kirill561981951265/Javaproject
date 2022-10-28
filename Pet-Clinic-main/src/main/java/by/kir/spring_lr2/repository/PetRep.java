package by.kir.spring_lr2.repository;

import by.kir.spring_lr2.model.Pet;
import by.kir.spring_lr2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRep extends JpaRepository<Pet, Long> {
    List<Pet> findAllByUser(User user);
}
