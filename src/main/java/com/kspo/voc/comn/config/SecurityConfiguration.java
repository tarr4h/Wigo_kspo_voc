package com.kspo.voc.comn.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {

		StrictHttpFirewall hf = new StrictHttpFirewall();
		hf.setAllowSemicolon(true);
		web.ignoring().antMatchers("/actuator/**", "/error/**", "/static/**", "/login/**",
				"/genGrid/**,/commCode/**,/util/**,/swagger-ui.html,/swagger-ui/**,/swagger-resources,/swagger-resources/**")
				.and().ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).and()
				.httpFirewall(hf);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

//        logger.debug("[SecurityConfiguration >> configure]" );

		http.httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
				.csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리.
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증할것이므로
																							// 세션필요없으므로 생성안함.
				.and().authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크(리소스 별 허용 범위 설정)
				.anyRequest().permitAll().and().headers().frameOptions().disable() // iframe 사용가능

		;

	}

}
