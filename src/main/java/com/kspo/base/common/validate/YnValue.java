package com.kspo.base.common.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = YnValidator.class)
public @interface YnValue {

	String message() default "['Y/N'값만 입력 가능합니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
