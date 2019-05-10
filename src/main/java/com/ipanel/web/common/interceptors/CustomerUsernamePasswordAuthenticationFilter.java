package com.ipanel.web.common.interceptors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ipanel.web.entity.SysUser;
import com.ipanel.web.sysUser.service.ISysUserService;
import com.ipanel.web.utils.Constants;
import com.ipanel.webapp.framework.util.Encrypt;

public class CustomerUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	private static final String TAG = "CustomerUsernamePasswordAuthenticationFilter";

	public static final String VALIDATE_CODE = "validateCode";
	public static final String USERNAME = "loginUserName";
	public static final String PASSWORD = "loginPassword";

	@Resource
	private ISysUserService sysUserService;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response) throws AuthenticationException {

		// Log.i(TAG, "********************enter RatingUsernamePasswordAuthenticationFilter");

		if (!request.getMethod().toUpperCase().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: "+ request.getMethod());
		}
		// 检测验证码
		// checkValidateCode(request);

		String userName = obtainUsername(request);
		String password = obtainPassword(request);
		//Log.i(TAG, "********userName:" + userName + " password:" + password);

		// 验证用户账号与密码是否对应
		userName = userName.trim();
		password = password.trim();
		password = Encrypt.e(password); // 密码加密
		SysUser users = null;
		try {
			users = this.sysUserService.querySysUser(userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Log.i(TAG, "****users==null:" + (users == null));

		if (users == null){
			throw new AuthenticationServiceException("用户名或者密码错误！");
		}
			

		HttpSession session = request.getSession();
		if (session != null || getAllowSessionCreation()) {
			session.setAttribute(Constants.SESSION_USER_ID, users.getId());
			session.setAttribute(Constants.SESSION_USER_NAME,users.getUserName());
			session.setAttribute(Constants.SESSION_USER_TYPE,users.getUserType());
			session.setAttribute(Constants.SESSION_USER, users);
		}
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, password);
		setDetails(request, authRequest);
		// Log.i(TAG,"*************finished");
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected void checkValidateCode(HttpServletRequest request) {
		HttpSession session = request.getSession();

		String sessionValidateCode = obtainSessionValidateCode(session);
		// 让上一次的验证码失效
		session.setAttribute(VALIDATE_CODE, null);
		String validateCodeParameter = obtainValidateCodeParameter(request);
		if (StringUtils.isEmpty(validateCodeParameter)|| !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {
			throw new AuthenticationServiceException("验证码错误！");
		}
	}

	private String obtainValidateCodeParameter(HttpServletRequest request) {
		Object obj = request.getParameter(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	protected String obtainSessionValidateCode(HttpSession session) {
		Object obj = session.getAttribute(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(USERNAME);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(PASSWORD);
		return null == obj ? "" : obj.toString();
	}

}
