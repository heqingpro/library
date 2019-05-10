package com.ipanel.web.common.interceptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;

import com.ipanel.web.sysUser.service.ISysUserService;
import com.ipanel.webapp.framework.core.SpringContextAware;
import com.ipanel.webapp.framework.util.Log;

public class InvocationSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {
	
	private static final String TAG = "InvocationSecurityMetadataSource";

	public static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	private UrlMatcher urlMatcher = new AntUrlPathMatcher();

	public InvocationSecurityMetadataSource() {
		try {
			Log.i(TAG, "********************enter InvocationSecurityMetadataSource");
			loadResourceDefine();
		} catch (Exception e) {
			Log.e(TAG,"init InvocationSecurityMetadataSource throw exception:"+ e);
		}
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	// According to a URL, Find out permission configuration of this URL.
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		String url = ((FilterInvocation) object).getRequestUrl();
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			if (urlMatcher.pathMatchesUrl(resURL, url)) {
				return resourceMap.get(resURL);
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public static void reLoadResource(){
		try {
			new InvocationSecurityMetadataSource().loadResourceDefine();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadResourceDefine() throws Exception {
		ISysUserService sysUserService = SpringContextAware.getBean("sysUserService");
		Log.i(TAG, "***************sysUserService==null:"+(sysUserService==null));
		List<String> urls = sysUserService.queryAllUrl();
		Log.i(TAG, "**************urls==null:"+(urls==null));
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

		for (String url : urls) {
			Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
			// 查询出url对应的权限
			List<String> roelNames = sysUserService.getRoleNameByUrl(url);
			for (String roleName : roelNames) {
				ConfigAttribute ca = new SecurityConfig(roleName);
				atts.add(ca);
			}
			resourceMap.put(url, atts);
		}

		// //打印查看权限与资源的对应关系
		Log.i(TAG,"资源（URL）数量：" + resourceMap.size());
		Set<String> keys = resourceMap.keySet();
		for (String key : keys) {
			Log.i(TAG, "**url:"+key+" role:"+resourceMap.get(key));
		}

	}

}
