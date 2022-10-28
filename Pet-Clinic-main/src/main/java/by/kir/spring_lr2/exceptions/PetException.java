package by.kir.spring_lr2.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PetException extends Exception{

    public PetException(String mes){ super(mes);}
}