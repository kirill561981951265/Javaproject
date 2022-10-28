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
public class NewAppointment {

    @NotBlank(message = "Weight must be specified")
    private String weight;

    @NotBlank(message = "Temperature must be specified")
    private String temp;

    @NotBlank(message = "History must be specified")
    private String history = "Содержание: квартира + улица\n" +
            "Другие животные: отсутствуют\n" +
            "Обработка от эктопаразитов: \n" +
            "Обработка от глистов: \n" +
            "Последняя вакцинация: \n" +
            "Кормление: смешанное\n" +
            "Перенесенные заболевания: нет\n" +
            "Перенесенные операции: нет\n" +
            "Принимаемые препараты: нет\n" +
            "Травмы укусы: нет";

    @NotBlank(message = "Anamnesis must be specified")
    private String anamnesis =  "Общее состояние: удовлетворительно\n" +
            "Аппетит: сохранен\n" +
            "Жажда: сохранена в обычном объеме\n" +
            "Стул: не изменен, регулярный\n" +
            "Мочеиспускание: сохранено в обычном объеме\n" +
            "Активность: сохранена\n" +
            "Рвота: нет\n" +
            "Кашель: нет";

    @NotBlank(message = "Complaints must be specified")
    private String complaints;

    @NotBlank(message = "Condition must be specified")
    private String condition = "Общее состояние: удовлетворительно\n" +
            "Слизистые оболочки: розовые\n" +
            "Трахеальный рефлекс: в норме\n" +
            "Упитанность: нориальна\n" +
            "Лимфоузлы: в норме\n" +
            "Дыхание: без потологических изменений\n" +
            "Брюшечная полость: без потологических изменений";

    @NotBlank(message = "Diagnostics must be specified")
    private String diagnostics;

    @NotBlank(message = "Diagnosis must be specified")
    private String diagnosis;

    @NotBlank(message = "Purpose must be specified")
    private String purpose;

    private Long idDoctor;

    private Long idPet;
}
