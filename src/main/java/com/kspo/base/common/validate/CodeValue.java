package com.kspo.base.common.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CodeValidator.class)
public @interface CodeValue {
	String codeId() default "";

	String[] codes() default {};

	String message() default "[잘못된 코드입니다.]";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
