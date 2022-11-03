package com.egov.voc.sys.mapper;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface CrmLogMapper {
	String value() default "";

}
