package com.liwenhou.Academy.annotation;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.liwenhou.Academy.validations.FieldsValueMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = { FieldsValueMatchValidator.class })
@Target({ ElementType.TYPE })
@Retention(RUNTIME)
public @interface FieldsValueMatch {

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String message() default "Fields values don't match";

    String field();

    String fieldMatch();

    @Target({ ElementType.TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        FieldsValueMatch[] value();
    }
}
