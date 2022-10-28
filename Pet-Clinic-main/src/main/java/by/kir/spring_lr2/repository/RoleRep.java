package by.kir.spring_lr2.repository;

import by.kir.spring_lr2.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRep extends JpaRepository<Role, Long> {
   Role findByName(String name);
}
