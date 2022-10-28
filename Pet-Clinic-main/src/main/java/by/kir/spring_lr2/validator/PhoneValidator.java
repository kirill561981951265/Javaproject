package by.kir.spring_lr2.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public  class PhoneValidator implements ConstraintValidator<CellPhone,String> {

    @Override
    public void initialize(CellPhone paramA) {
    }
    @Override
    public boolean isValid(String phoneNo, ConstraintValidatorContext ctx) {
        if(phoneNo == null){
            return false;
        }
            //'123456789'
        if (phoneNo.matches("\\d{9}")) return true;
            //разделяется '-', '.', ' '
        else if(phoneNo.matches("\\d{2}[-\\.\\s]\\d{3}[-\\.\\s]\\d{2}[-\\.\\s]\\d{2}")) return true;
            //(код оператора)
        else if(phoneNo.matches("\\(\\d{2}\\)\\d{3}\\d{2}\\d{2}")) return true;
            //(код страны)
        else if(phoneNo.matches("\\(\\d{3}\\)\\d{2}\\d{3}\\d{4}")) return true;

        else return false;
    }

}
