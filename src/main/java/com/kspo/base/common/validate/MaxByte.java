package com.kspo.base.common.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.METHOD, ElementType.FIELD ,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MaxByteValidator.class)
public @interface MaxByte {
	int max() ;
	String message() default "[최대 '{max}'Byte 까지 입력가능합니다.]";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
