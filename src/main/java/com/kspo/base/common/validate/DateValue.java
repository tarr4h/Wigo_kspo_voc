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
@Constraint(validatedBy = DateValidator.class)
public @interface DateValue {

	String message() default "[유효한 날짜(YYYYMMDD)가 아닙니다.]";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
