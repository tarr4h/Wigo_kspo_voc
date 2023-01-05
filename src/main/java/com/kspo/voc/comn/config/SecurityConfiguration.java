package com.kspo.voc.comn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kspo.base.api.jwt.EzJwtService;
import com.kspo.base.api.jwt.security.EzJwtAccessHandler;
import com.kspo.base.api.jwt.security.EzJwtAuthenticationFilter;
import com.kspo.voc.comn.util.Constants;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {
	@Autowired
	EzJwtService ezJwtTokenProvider;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
				// rest api이므로 csrf 보안이 필요없으므로 disable처리.
				.cors().configurationSource(corsConfigurationSource()).and().csrf().disable().sessionManagement()
				// jwt token으로 인증할것이므로 세션필요없으므로 생성안함.
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				// 다음 리퀘스트에 대한 사용권한 체크(리소스 별 허용 범위 설정)
				.antMatchers(Constants._API_URL + "/**").hasRole("API").anyRequest().permitAll()
				// 인증 오류 발생 시 처리를 위한 핸들러 추가
				.and().exceptionHandling().accessDeniedHandler(new EzJwtAccessHandler()).and()
				// 인증 오류 발생 시 처리를 위한 핸들러 추가
				// iframe 사용가능, jwt token 필터를 id/password 인증 필터 전에 넣어라.
				.exceptionHandling().authenticationEntryPoint(new EzJwtAccessHandler()).and().headers().frameOptions()
				.disable().and().addFilterBefore(new EzJwtAuthenticationFilter(ezJwtTokenProvider),
						UsernamePasswordAuthenticationFilter.class);

//		http.httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
//				.csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리.
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증할것이므로
//				.and().authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크(리소스 별 허용 범위 설정)
//				.anyRequest().permitAll()
//				.antMatchers("/api/**").hasRole("API")
//				.and().exceptionHandling().accessDeniedHandler(new EzJwtAccessHandler()).and()
//				.exceptionHandling().authenticationEntryPoint(new EzJwtAccessHandler())
//				.and().headers().frameOptions().disable()
//				.and().addFilterBefore(new EzJwtAuthenticationFilter(ezJwtTokenProvider),
//						UsernamePasswordAuthenticationFilter.class)
//				; // iframe 사용가능
		return http.build();

	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		StrictHttpFirewall hf = new StrictHttpFirewall();
		hf.setAllowSemicolon(true);
//		return (web) -> web.ignoring().antMatchers("/actuator/**", "/error/**", "/static/**", "/login/**",
//				"/genGrid/**,/commCode/**,/util/**,/swagger-ui.html,/swagger-ui/**,/swagger-resources,/swagger-resources/**")
//				.and().ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).and()
//				.httpFirewall(hf);
		return (web) -> web.httpFirewall(hf);
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/api/**", configuration);
		return source;
	}

//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
//
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//
//		StrictHttpFirewall hf = new StrictHttpFirewall();
//		hf.setAllowSemicolon(true);
//		web.ignoring().antMatchers("/actuator/**", "/error/**", "/static/**", "/login/**",
//				"/genGrid/**,/commCode/**,/util/**,/swagger-ui.html,/swagger-ui/**,/swagger-resources,/swagger-resources/**")
//				.and().ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).and()
//				.httpFirewall(hf);
//
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//
//		http.httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
//				.csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리.
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증할것이므로
//																							// 세션필요없으므로 생성안함.
//				.and().authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크(리소스 별 허용 범위 설정)
//				.anyRequest().permitAll().and().headers().frameOptions().disable() // iframe 사용가능
//
//		;
//
//	}

}
