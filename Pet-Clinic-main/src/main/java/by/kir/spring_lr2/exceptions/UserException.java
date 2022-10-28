package by.kir.spring_lr2.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserException extends Exception{
    public UserException(String mes){ super(mes);}

}
