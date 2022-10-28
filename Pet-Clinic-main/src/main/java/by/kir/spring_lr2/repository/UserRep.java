package by.kir.spring_lr2.repository;

import by.kir.spring_lr2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRep extends JpaRepository<User, Long> {
    User findByUsername(String name);
    User findByName(String name);
    User findByEmail(String email);
}
