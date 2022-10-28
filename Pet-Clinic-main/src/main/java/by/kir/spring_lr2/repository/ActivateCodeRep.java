package by.kir.spring_lr2.repository;

import by.kir.spring_lr2.model.ActivateCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivateCodeRep extends CrudRepository<ActivateCode, String> {

}
