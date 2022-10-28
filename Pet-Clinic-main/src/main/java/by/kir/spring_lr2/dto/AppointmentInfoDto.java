package by.kir.spring_lr2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;


@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentInfoDto {

    private String date;

    private String weight;

    private String temp;

    private String history;

    private String anamnesis;

    private String complaints;

    private String condition;

    private String diagnostics;

    private String diagnosis;

    private String purpose;

    private String age;

    private String name;

    private String breed;

    private String gender;

    private String bday;

    private String type;

    private String doctorname;

    private String username;


}
