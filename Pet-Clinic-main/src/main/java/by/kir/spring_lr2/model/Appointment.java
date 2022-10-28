package by.kir.spring_lr2.model;

import lombok.*;

import javax.persistence.*;

@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "appointments")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Appointment extends BaseEntity{

    @Column(name = "weight")
    private Float weight;

    @Column(name = "temp")
    private Float temp;

    @Column(name = "age")
    private String age;

    @Column(name = "history")
    private String history;

    @Column(name = "anamnesis")
    private String anamnesis;

    @Column(name = "complaintsC")
    private String complaintsC;

    @Column(name = "conditionC")
    private String conditionC;

    @Column(name = "diagnostics")
    private String diagnostics;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "purpose")
    private String purpose;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
