package by.kir.spring_lr2.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RegistrationException extends Exception{
    public RegistrationException(String mes){ super(mes);}
}
