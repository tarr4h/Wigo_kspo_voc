package com.kspo.voc;

import javax.servlet.http.HttpSessionListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.kspo.voc.comn.config.SessionListener;

@SpringBootApplication
//@ImportResource({"classpath:/kspo/springmvc/dispatcher-servlet.xml","classpath*:/kspo/spring/context-*.xml"})
//@Import(EgovBootInitialization.class)
//@Import(WebMvcConfigurer.class)
public class VocBootApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(VocBootApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return super.configure(builder);
	}

	@Bean
	HttpSessionListener httpSessionListener() {
		return new SessionListener();
	}

}
