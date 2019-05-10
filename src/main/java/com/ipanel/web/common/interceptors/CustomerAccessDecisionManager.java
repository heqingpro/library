package com.ipanel.web.common.interceptors;

import java.util.Collection;
import java.util.Iterator;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class CustomerAccessDecisionManager implements AccessDecisionManager {

	private static final String TAG = "CustomerAccessDecisionManager";


	// authentication用户认证信息，具有的权限，object
	// 用户请求访问的URL，configAttributes权限集合，即访问资源需要具有的权限
	@Override
	public void decide(Authentication authentication, Object arg1,Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		// TODO Auto-generated method stub
		if (configAttributes == null){
			return;
		}
		

		Iterator<ConfigAttribute> ite = configAttributes.iterator();
		while (ite.hasNext()) {
			ConfigAttribute ca = ite.next();
			String needRole = ((SecurityConfig) ca).getAttribute();//访问该资源所需的角色
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (needRole.equals(ga.getAuthority())) {
					return;
				}
			}
		}
		throw new AccessDeniedException("no right!");
	}

	@Override
	public boolean supports(ConfigAttribute arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
