package pl.coderslab.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class WordsValidator implements ConstraintValidator<NumberOfWords, String> {

    private int min;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(s.split(" "))
                .filter(word -> !word.isEmpty())
                .count() >= min;
    }

    @Override
    public void initialize(NumberOfWords constraintAnnotation) {
        min = constraintAnnotation.min();
    }
}
