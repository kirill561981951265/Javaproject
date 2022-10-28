package by.kir.spring_lr2.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Data
@Table(name = "activatecode")
@RequiredArgsConstructor
@RedisHash(value = "Activate", timeToLive = 86400)
public class ActivateCode implements Serializable {
    @Id
    private UUID id;
    private String activateCode;
    private boolean activate;
    private String username;
}
