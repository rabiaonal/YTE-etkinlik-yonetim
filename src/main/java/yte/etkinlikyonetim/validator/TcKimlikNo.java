package yte.etkinlikyonetim.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TcKimlikNoValidator.class)
public @interface TcKimlikNo {
    String message() default "Geçersiz T.C. kimlik numarası.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
