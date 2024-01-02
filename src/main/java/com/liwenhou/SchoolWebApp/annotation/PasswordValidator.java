package com.liwenhou.SchoolWebApp.annotation;

import com.liwenhou.SchoolWebApp.validations.PasswordStrengthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface PasswordValidator {

    String message() default "Please choose a strong password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
