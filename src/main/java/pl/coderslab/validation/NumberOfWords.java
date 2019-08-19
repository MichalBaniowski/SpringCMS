package pl.coderslab.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = WordsValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberOfWords {

    String message() default "za mała ilość słów";
    int min() default 1;

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
