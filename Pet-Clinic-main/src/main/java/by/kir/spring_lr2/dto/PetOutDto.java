package by.kir.spring_lr2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class PetOutDto {
    private Long id;

    private String name;

    private String type;

    private String breed;

    private String gender;

    private String bday;

    private Long idUser;

    private String fio;
}
