package com.ipanel.web.sysUser.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ipanel.web.common.dao.BaseDao;
import com.ipanel.web.entity.Role;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.entity.UserToRole;
import com.ipanel.web.utils.Constants;
import com.ipanel.webapp.framework.core.dao.DaoQueryOperator;
import com.ipanel.webapp.framework.util.Log;

public class UserDetailService implements UserDetailsService {

	private final static String TAG = "UserDetailService";

	@Resource(name = "baseDao")
	private BaseDao baseDao;

	@Override
	public UserDetails loadUserByUsername(String userName)throws UsernameNotFoundException, DataAccessException {
		Log.i(TAG, "***enter UserDetailService step1,userName:" + userName);
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		List<SysUser> sysUsers = baseDao.query(SysUser.class, new Object[][] { { DaoQueryOperator.EQ,"userName", userName } });
		SysUser sysUser = null;
		if (sysUsers != null && sysUsers.size() > 0){
			sysUser = sysUsers.get(0);
		}			

		// 超级管理员，具有全部角色
		if (Constants.USER_TYPE_SUPER_ADMIN.equals(sysUser.getUserType())) {
			List<Role> allRoles = baseDao.query(Role.class, null);
			for (Role r : allRoles) {
				GrantedAuthorityImpl authority = new GrantedAuthorityImpl(r.getName());
				auths.add(authority);
			}
		} else {
			Set<UserToRole> roles = sysUser.getUserToRoles();
			for (UserToRole r : roles) {
				if (r.getRole() != null) {
					GrantedAuthorityImpl authority = new GrantedAuthorityImpl(r.getRole().getName());
					auths.add(authority);
				}
			}
		}
		Log.i(TAG,"****UserDetailService step2 userType:"+ sysUser.getUserType() + " auths.size:" + auths.size());
		String password = sysUser.getPassword();
		User user = new User(userName, password, true, true, true, true, auths);
		Log.i(TAG, "****UserDetailService step3 user==null:"+ (user == null));
		return user;
	}

}
