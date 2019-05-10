package com.ipanel.web.common.interceptors;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ipanel.web.utils.Constants;
import com.ipanel.webapp.framework.util.Log;

public class SessionInterceptor implements HandlerInterceptor {

	private List<String> excludeUrls;

	private UrlMatcher urlMatcher = new AntUrlPathMatcher();

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 完成页面的render后调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object object, Exception exception)throws Exception {

	}

	/**
	 * 在调用controller具体方法后拦截
	 */
	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object object,ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在调用controller具体方法前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object object) throws Exception {
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		boolean flag = false;
		for (String s : excludeUrls) {
			flag = urlMatcher.pathMatchesUrl(s, url);
			if (flag){
				return true;
			}
				
		}
		HttpSession session = request.getSession();
		boolean isLogined = session != null&& session.getAttribute(Constants.SESSION_USER_NAME) != null&& !session.getAttribute(Constants.SESSION_USER_NAME).equals("");


		Log.i("SessionInterceptor", "*****isLogined:" + isLogined + " url:"+ url);

		if (isLogined) {
			return true;
		} else {
			session.invalidate();

			PrintWriter out = response.getWriter();
			StringBuilder builder = new StringBuilder();
			builder.append("<script type=\"text/javascript\">");
			builder.append("window.top.location.href=\"");
			builder.append(request.getContextPath());
			builder.append("/login.jsp\";</script>");
			out.print(builder.toString());
			out.close();
			String sendUrl = builder.toString();
			Log.i("SessionInterceptor", "*****sendUrl:" + sendUrl);
			return false;
		}
	}
}
