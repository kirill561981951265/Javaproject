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
public class PetRegistrDto {
    private Long id;

    @NotBlank(message = "Name must be specified")
    private String name;

    @NotBlank(message = "Type must be specified")
    private String type;

    @NotBlank(message = "Breed must be specified")
    private String breed;

    @NotBlank(message = "Gender must be specified")
    private String gender;

    @NotBlank(message = "Bday must be specified")
    private String bday;

    private Long idUser;
}
