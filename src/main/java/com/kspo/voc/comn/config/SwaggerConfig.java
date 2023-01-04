package com.kspo.voc.comn.config;

import java.util.Arrays;
import java.util.List;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Value("${springdoc.server-url}")
	String serverUrl;

	@Bean
	public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion) {
		Info info = new Info().title("VOC API Server").version(appVersion).description("VOC API 서버입니다.");

		List<Server> servers = Arrays.asList(new Server().url(serverUrl).description("VOC API"),
				new Server().url(serverUrl.replace("https", "http")).description("VOC API"));

		SecurityScheme securityScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
				.bearerFormat("JWT").in(SecurityScheme.In.HEADER).name("Authorization");

		SecurityRequirement schemaRequirement = new SecurityRequirement().addList("bearerAuth");

		return new OpenAPI().components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
				.security(Arrays.asList(schemaRequirement)).info(info).servers(servers);
	}

	@Bean
	public GroupedOpenApi allApi() {
		return GroupedOpenApi.builder().group("all").packagesToScan("com.kspo.voc.api","com.kspo.base.api").build();
	}



	@Bean
	public GroupedOpenApi baseApi() {
		return GroupedOpenApi.builder().group("base").packagesToScan("com.kspo.base.api").build();
	}

	@Bean
	public GroupedOpenApi vocApi() {
		return GroupedOpenApi.builder().group("voc").packagesToScan("com.kspo.voc.api").build();
	}
}
