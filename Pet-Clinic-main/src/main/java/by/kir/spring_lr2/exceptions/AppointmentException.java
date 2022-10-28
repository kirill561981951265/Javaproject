package by.kir.spring_lr2.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppointmentException extends Exception{

    public AppointmentException(String mes){ super(mes);}
}
