package com.kspo.base.api.jwt.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kspo.voc.comn.util.Utilities;

/**
 * <pre>
 * com.ceragem.crm.common.jwt.entity - JwtUser.java
 * </pre>
 *
 * @ClassName : JwtUser
 * @Description : JWT 인증 사용조
 * @author : 김성태
 * @date : 2021. 1. 25.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

public class JwtUser implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -450121223238369547L;
	List<String> roles = new ArrayList<>();

	public JwtUser() {

	}

	public JwtUser(String defaultGroup) {
		if (Utilities.isNotEmpty(defaultGroup))
			roles.add("ROLE_" + defaultGroup);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
