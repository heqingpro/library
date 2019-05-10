package com.ipanel.web.sysUser.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ipanel.web.common.dao.BaseDao;
import com.ipanel.web.common.interceptors.InvocationSecurityMetadataSource;
import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.entity.AppInfo;
import com.ipanel.web.entity.Permission;
import com.ipanel.web.entity.Role;
import com.ipanel.web.entity.RoleToPermission;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.entity.SysUserToApp;
import com.ipanel.web.entity.SystemLog;
import com.ipanel.web.entity.UserToRole;
import com.ipanel.web.sysUser.pageModel.LogModel;
import com.ipanel.web.sysUser.pageModel.PermissionModel;
import com.ipanel.web.sysUser.pageModel.RoleModel;
import com.ipanel.web.sysUser.pageModel.SysUserModel;
import com.ipanel.web.sysUser.service.ISysUserService;
import com.ipanel.web.utils.Constants;
import com.ipanel.web.utils.ExcelUtil;
import com.ipanel.webapp.framework.core.dao.DaoQueryOperator;
import com.ipanel.webapp.framework.util.Encrypt;
import com.ipanel.webapp.framework.util.Log;
import com.ipanel.webapp.framework.util.PropertyUtil;
import com.ipanel.webapp.framework.util.TimeUtil;

@Service("sysUserService")
public class SysUserServiceImpl implements ISysUserService {

	private List<String> roleNameList =  new ArrayList<String>();
	
	private String msg;
	
	private static final String TAG = "SysUserServiceImpl";

	@Resource(name = "baseDao")
	private BaseDao baseDao;
	
	@Resource(name = "systemLogService")
	private SystemLogService systemLogService;

	@Override
	public List<String> queryAllUrl() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) baseDao.queryOneField("url",Permission.class, null, null, null, null, null);
		return list;
	}

	@Override
	public List<String> getRoleNameByUrl(String url) {
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) baseDao.queryOneField("role.name",RoleToPermission.class, new Object[][] { {"permission.url", url } }, null, null, null,null);
		
		//baseDao.query(null, false,DaoQueryOperator.lt , , ascBy, descBy, firstResult, maxResult)
	
		
		List<Permission> permissionList = baseDao.query(Permission.class, new Object[][] { { DaoQueryOperator.EQ, "url", url  } });
		if(permissionList.size() > 0){
			Permission item = permissionList.get(0);
			roleNameList.clear();
			getRoleNames(item.getId());
			list.addAll(roleNameList);
			
		}
		Log.i(TAG, "***roleNameList:" + list);
		return list;
	}
	
	private void getRoleNames(int id){
		List<Permission> permissionList = getPermissionList(id);
		if(permissionList != null || permissionList.size() > 0){
			for(Permission permission : permissionList){
				@SuppressWarnings("unchecked")
				List<String> strs = (List<String>) baseDao.queryOneField("role.name",RoleToPermission.class, new Object[][] { {"permission.url", permission.getUrl() } }, null, null, null,null);
				roleNameList.addAll(strs);
				getRoleNames(permission.getId());
				Log.i(TAG, "***roleName:" + strs);
			}
		}
	}
	
	private List<Permission> getPermissionList(int id){
		return baseDao.query(Permission.class, new Object[][] { { DaoQueryOperator.EQ, "parentModel.id", id  } });
	}

	@Override
	public SysUser querySysUser(String userName, String password) {
		List<SysUser> userList = baseDao.query(SysUser.class,new Object[][] { { DaoQueryOperator.EQ, "userName", userName },{ DaoQueryOperator.EQ, "password", password } });
		if (userList != null && userList.size() > 0){
			return userList.get(0);
		}	
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PageDataModel queryRole(int page, int rows) {
		List<Role> dataList = (List<Role>) baseDao.query(null, false,Role.class, null, null, new String[] { "id" }, (page - 1)* rows, rows);
		long total = baseDao.count(null, false, Role.class, null);

		List<RoleModel> roleList = new ArrayList<RoleModel>();
		for (Role r : dataList) {
			String resourceIds = "";
			String resourceNames = "";
			RoleModel rm = new RoleModel();
			BeanUtils.copyProperties(r, rm);
			Set<RoleToPermission> models = r.getRoleToPermissionSet();
			Log.i(TAG, "********models==null:" + (models == null));
			if (models != null)
				Log.i(TAG, "*************size:" + models.size());

			for (RoleToPermission m : models) {
				Permission item = m.getPermission();
				if (item != null) {
					resourceIds += item.getId() + ",";
					resourceNames += item.getName() + ",";
				}
			}
			Log.i(TAG, "***resourceIds:" + resourceIds + " resourceNames:"+ resourceNames);

			if (resourceIds.length() > 0){
				resourceIds = resourceIds.substring(0, resourceIds.length() - 1);
			}
			if (resourceNames.length() > 0){
				resourceNames = resourceNames.substring(0,resourceNames.length() - 1);
			}
			rm.setResourceIds(resourceIds);
			rm.setResourceNames(resourceNames);
			roleList.add(rm);
		}
		return new PageDataModel((int) total, roleList);
	}

	@Override
	@SuppressWarnings("unchecked")
	public PageDataModel querySystemLog(LogModel m,String startTime,String endTime) {
		
		List<SystemLog> dataList = new ArrayList<SystemLog>();
		long total = 0;
		if((m.getUserName()==null||m.getUserName()=="")&&(startTime==""||startTime==null)&&(endTime==""||endTime==null)){
			//无条件查询
			dataList = (List<SystemLog>) baseDao.query(null, false,SystemLog.class, null, null, new String[] { "id" }, (m.getPage() - 1)* m.getRows(), m.getRows());
			total = baseDao.count(null, false, SystemLog.class, null);
		}else if((startTime!=""&&startTime!=null)||(endTime!=""&&endTime!=null)){
			//时间查询
			Object[][] params = null;
			if(m.getUserName()==null||m.getUserName()==""){
				params = new Object[][]{{DaoQueryOperator.GT,"operatingDate",startTime},{DaoQueryOperator.LT,"operatingDate",endTime},};
			}else{
				Object[][] usernames = new Object[][] { { "userName", m.getUserName() } };
				List<SysUser> userList = baseDao.query(SysUser.class, usernames);
				params = new Object[][]{{DaoQueryOperator.GT,"operatingDate",startTime},{DaoQueryOperator.LT,"operatingDate",endTime},{"sysUser",userList.get(0).getId()}};
			}
			
			dataList = (List<SystemLog>) baseDao.query(null, false, SystemLog.class, params, null, new String[]{"id"}, (m.getPage() - 1)* m.getRows(), m.getRows());
			total = baseDao.count(null, false, SystemLog.class, params);
		}else{
			//username查询
			Object[][] params = new Object[][] { { "userName", m.getUserName() } };
			List<SysUser> userList = baseDao.query(SysUser.class, params);
			Object[][] ids = new Object[][]{{"sysUser",userList.get(0).getId()}};
			dataList = (List<SystemLog>) baseDao.query(null, false,SystemLog.class, ids, null, new String[] { "id" }, (m.getPage() - 1)* m.getRows(), m.getRows());
			total = baseDao.count(null, false, SystemLog.class, ids);
		}
		
		JSONArray json = new JSONArray();
		
		List<LogModel> logList = new ArrayList<LogModel>();
		for (SystemLog r : dataList) {
			LogModel lm = new LogModel();
			BeanUtils.copyProperties(r, lm);
			SysUser su = r.getSysUser();

			lm.setUserName(su.getUserName());
			
			JSONObject jb = new JSONObject();
			try {
				jb.put("id", lm.getId());
				jb.put("moduleName", lm.getModuleName());
				jb.put("userName", lm.getUserName());
				jb.put("operatingFunction", lm.getOperatingFunction());
				jb.put("operatingDesc", lm.getOperatingDesc());
				jb.put("operatingDate", lm.getOperatingDate());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			json.put(jb);
			
			logList.add(lm);
		}		
		
		msg = json.toString();
		
		return new PageDataModel((int) total, logList);
	}

	@Override
	public List<PermissionModel> queryAllPermissionItem() {
		List<PermissionModel> resultData = new ArrayList<PermissionModel>();
		List<Permission> dataList = baseDao.query(Permission.class, null);
		for (Permission item : dataList) {
			Integer pid = 0;
			if (item.getParentModel() != null)pid = item.getParentModel().getId();
			PermissionModel model = new PermissionModel(pid,item.getId(), item.getName());
			resultData.add(model);
		}

		return resultData;
	}

	@Override
	public RoleModel editRole(SysUser user, RoleModel roleModel) {
		String resourceNames = "";
		Role role = baseDao.get(Role.class, roleModel.getId());
		BeanUtils.copyProperties(roleModel, role);

		// 删除此角色可访问的资源
		if (role != null) {
			baseDao.delete(RoleToPermission.class, "role.id",new Object[][] { { "role.id", role.getId() } });
			if (roleModel.getResourceIds() != null) {
				for (String id : roleModel.getResourceIds().split(",")) {
					Permission item = baseDao.get(Permission.class, Integer.parseInt(id));
					resourceNames += item.getName() + ",";
					RoleToPermission roleToPermission = new RoleToPermission();
					roleToPermission.setPermission(item);
					roleToPermission.setRole(role);
					baseDao.save(roleToPermission);
				}
			}
			if (resourceNames.length() > 0){
				resourceNames = resourceNames.substring(0,resourceNames.length() - 1);
			}	
			roleModel.setResourceNames(resourceNames);
			baseDao.update(role);
			
			systemLogService.saveSystemLog("系统管理/角色管理", "修改角色", "用户\""+user.getUserName()+"\"修改了\""+roleModel.getName()+"\"角色", user);
		}
		InvocationSecurityMetadataSource.resourceMap.clear();   //清空原来的权限
		InvocationSecurityMetadataSource.reLoadResource();   //重新加载权限资源
		return roleModel;
	}

	@Override
	public int getRoleByName(String name) {
		List<Role> list = baseDao.query(Role.class, new Object[][] { {DaoQueryOperator.EQ, "name", name } });
		if (list.size() > 0) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public int getRoleByNameAndId(String name, int id) {
		if (getRoleByName(name) == -1) {
			Role role = baseDao.get(Role.class, id);
			if (role.getName().equals(name)) {
				return 0;
			} else {
				return -1;
			}
		} else {
			return 0;
		}
	}

	@Override
	public RoleModel addRole(SysUser user, RoleModel roleModel) {
		String resourceIds = roleModel.getResourceIds();
		String resourceNames = "";
		String name = roleModel.getName();
		Role role = new Role();
		role.setName(name);
		baseDao.save(role);
		if (resourceIds != null && resourceIds.length() > 0) {
			for (String id : resourceIds.split(",")) {
				Permission item = baseDao.get(Permission.class, Integer.parseInt(id));
				resourceNames += item.getName() + ",";
				RoleToPermission roleToPermission = new RoleToPermission();
				roleToPermission.setPermission(item);
				roleToPermission.setRole(role);
				baseDao.save(roleToPermission);
			}
			systemLogService.saveSystemLog("系统管理/角色管理", "添加角色", "用户\""+user.getUserName()+"\"添加了\""+roleModel.getName()+"\"角色", user);
			if (resourceNames.length() > 0){
				resourceNames = resourceNames.substring(0,resourceNames.length() - 1);
			}	
		}
		roleModel.setResourceNames(resourceNames);
		InvocationSecurityMetadataSource.resourceMap.clear();   //清空原来的权限
		InvocationSecurityMetadataSource.reLoadResource();   //重新加载权限资源
		
		return roleModel;
	}

	@Override
	public void deleteRole(SysUser user, String ids) {
		String[] idArrayStr = ids.split(",");
		List<Integer> idArray = new ArrayList<Integer>();
		List<Role> roles = new ArrayList<Role>();
		for (String id : idArrayStr) {
			idArray.add(Integer.parseInt(id));
			Role u = baseDao.query(Role.class, new Object[][]{{DaoQueryOperator.EQ, "id", Integer.parseInt(id)}}).get(0);
			roles.add(u);
		}
		
		StringBuffer rolename = new StringBuffer();
		for(Role r : roles){
			rolename.append(r.getName()+",");
		}
		baseDao.delete(Role.class, "id", idArray);
		baseDao.delete(RoleToPermission.class, "role.id", idArray);
		
		
		systemLogService.saveSystemLog("系统管理/角色管理", "删除角色", "用户\""+user.getUserName()+"\"删除了\""+rolename.toString().substring(0, rolename.length()-1)+"\"角色", user);
		
		InvocationSecurityMetadataSource.resourceMap.clear();   //清空原来的权限
		InvocationSecurityMetadataSource.reLoadResource();   //重新加载权限资源

	}

	@Override
	@SuppressWarnings("unchecked")
	public PageDataModel queryAllSysUser(SysUserModel m) {
		try{
			Object[][] params = new Object[][] { { "userName", m.getUserName() } };
			int count = (int) baseDao.count(null, false, SysUser.class, params);
			List<SysUser> dataList = (List<SysUser>) baseDao.query(null, false,SysUser.class, params, null, null,(m.getPage() - 1) * m.getRows(), m.getRows());
			List<SysUserModel> list = new ArrayList<SysUserModel>();
			for (SysUser u : dataList) {
				String roleNames = "", roleIds = "";
				Set<UserToRole> roles = u.getUserToRoles();
				for (UserToRole ur : roles) {
					Role r = ur.getRole();
					if (r != null) {
						roleNames += r.getName() + ",";
						roleIds += r.getId() + ",";
					}
				}
				if (roleNames.length() > 0) {
					roleNames = roleNames.substring(0, roleNames.length() - 1);
					roleIds = roleIds.substring(0, roleIds.length() - 1);
				}
				//已授权的内容提供商
				String appNames = "", appIds = "";
				List<SysUserToApp> sysUserToApps = u.getSysUserToApps();
				for(SysUserToApp ua:sysUserToApps){
					AppInfo appInfo = ua.getAppInfo();
					if(appInfo!=null){
						appNames += appInfo.getAppName()+",";
						appIds += appInfo.getId()+",";
					}
				}
				if(appNames.length()>0){
					appNames = appNames.substring(0,appNames.length()-1);
					appIds = appIds.substring(0, appIds.length()-1);							
				}
				
				SysUserModel csm = new SysUserModel();
				BeanUtils.copyProperties(u, csm);
				csm.setRoleNames(roleNames);
				csm.setRoleIds(roleIds);
				csm.setUserTypeInfo(Constants.USER_TYPE_MAP.get(u.getUserType()));
				csm.setAppIds(appIds);
				csm.setAppNames(appNames);
				list.add(csm);
			}
			return new PageDataModel(count, list);
		}catch(Exception e){
			e.printStackTrace();
			Log.e(TAG,"***querySysUser throw exception:" + e);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PageDataModel querySysUser(int page, int rows) {
		List<SysUser> sysUserList = (List<SysUser>) baseDao.query(null, false,SysUser.class, null, null, null, (page - 1) * rows, rows);
		long count = baseDao.count(null, false, SysUser.class, null);
		List<SysUserModel> modelList = new ArrayList<SysUserModel>();
		for (SysUser u : sysUserList) {
			String roleNames = "", roleIds = "";
			Set<UserToRole> roles = u.getUserToRoles();
			for (UserToRole ur : roles) {
				Role r = ur.getRole();
				if (r != null) {
					roleNames += r.getName() + ",";
					roleIds += r.getId() + ",";
				}
			}
			if (roleNames.length() > 0) {
				roleNames = roleNames.substring(0, roleNames.length() - 1);
				roleIds = roleIds.substring(0, roleIds.length() - 1);
			}
			SysUserModel m = new SysUserModel();

			BeanUtils.copyProperties(u, m);
			m.setRoleNames(roleNames);
			m.setRoleIds(roleIds);
			m.setUserTypeInfo(Constants.USER_TYPE_MAP.get(u.getUserType()));
			modelList.add(m);
		}
		PageDataModel resultData = new PageDataModel((int) count, modelList);
		return resultData;
	}

	@Override
	public String addSysUser(SysUser user, SysUserModel sysUserModel) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(sysUserModel, sysUser);
		sysUser.setCreateTime(TimeUtil.format(new Date()));
		sysUser.setUserType(Constants.USER_TYPE_NORMAL_ADMIN);
		sysUser.setPassword(Encrypt.e(sysUser.getPassword()));
		baseDao.save(sysUser);
		String roleIds = sysUserModel.getRoleIds();
		String[] idArray = roleIds.split(",");
		for (String id : idArray) {
			Role role = baseDao.get(Role.class, Integer.parseInt(id));
			UserToRole userRole = new UserToRole();
			userRole.setRole(role);
			userRole.setSysUser(sysUser);
			baseDao.save(userRole);
		}
		try {
			systemLogService.saveSystemLog("系统管理/用户管理", "添加用户", "用户\""+user.getUserName()+"\"添加了\""+sysUserModel.getUserName()+"\"用户", user);
		} catch (Exception e) {
			Log.e(TAG, "日志记录异常");
		}
		//重新授权内容提供商
		String appIds = sysUserModel.getAppIds();
		String[] idArr = appIds.split(",");
		StringBuffer appNames = new StringBuffer();
		for(String id:idArr){
			AppInfo appInfo = baseDao.get(AppInfo.class, Integer.parseInt(id));
			SysUserToApp sysUserToApp = new SysUserToApp();
			sysUserToApp.setAppInfo(appInfo);
			sysUserToApp.setSysUser(sysUser);
			baseDao.save(sysUserToApp);
			appNames.append(appInfo.getAppName()+",");
		}
		try {
			systemLogService.saveSystemLog("系统管理/用户管理", "用户修改", "用户\""+user.getUserName()+"\"修改了\""+appNames+"\"内容提供商", user);	
		} catch (Exception e) {
			Log.e(TAG, "日志记录异常");
		}
		return null;
	}

	@Override
	public int getUserByName(String name) {
		List<SysUser> list = baseDao.query(SysUser.class, new Object[][] { {DaoQueryOperator.EQ, "userName", name } });
		if (list.size() > 0) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public String editSysUser(SysUser user, SysUserModel sysUserModel) {
		SysUser sysUser = baseDao.get(SysUser.class, sysUserModel.getId());
		sysUserModel.setCreateTime(sysUser.getCreateTime());
		sysUserModel.setPassword(sysUser.getPassword());
		BeanUtils.copyProperties(sysUserModel, sysUser);
		sysUser.setUserType(Constants.USER_TYPE_NORMAL_ADMIN);
		// 删除已有的角色
		baseDao.delete(UserToRole.class, "sysUser.id", new Object[][] { {DaoQueryOperator.EQ, "sysUser.id", sysUserModel.getId() } });

		// 重新分派角色
		String roleIds = sysUserModel.getRoleIds();
		StringBuffer roleNames = new StringBuffer();
		String[] idArray = roleIds.split(",");
		for (String id : idArray) {
			Role role = baseDao.get(Role.class, Integer.parseInt(id));
			UserToRole userRole = new UserToRole();
			userRole.setRole(role);
			userRole.setSysUser(sysUser);
			baseDao.save(userRole);
			roleNames.append(role.getName()+",");
		}
		try {
			systemLogService.saveSystemLog("系统管理/用户管理", "用户修改", "用户\""+user.getUserName()+"\"修改了\""+roleNames+"\"角色", user);	
		} catch (Exception e) {
			Log.e(TAG, "日志记录异常");
		}
		//删除已有的内容提供商
		baseDao.delete(SysUserToApp.class, "sysUser.id",new Object[][]{{
			DaoQueryOperator.EQ,"sysUser.id", sysUserModel.getId()
		}});
		
		//重新授权内容提供商
		String appIds = sysUserModel.getAppIds();
		String[] idArr = appIds.split(",");
		StringBuffer appNames = new StringBuffer();
		for(String id:idArr){
			AppInfo appInfo = baseDao.get(AppInfo.class, Integer.parseInt(id));
			SysUserToApp sysUserToApp = new SysUserToApp();
			sysUserToApp.setAppInfo(appInfo);
			sysUserToApp.setSysUser(sysUser);
			baseDao.save(sysUserToApp);
			appNames.append(appInfo.getAppName()+",");
		}
		try {
			systemLogService.saveSystemLog("系统管理/用户管理", "用户修改", "用户\""+user.getUserName()+"\"修改了\""+appNames+"\"内容提供商", user);	
		} catch (Exception e) {
			Log.e(TAG, "日志记录异常");
		}
		return null;
	}

	@Override
	public String updateUserPwd_reset(SysUser user, String id) {
		String pwd = PropertyUtil.getPropertyValue("default_resetpwd");
		
		SysUser users = baseDao.query(SysUser.class, new Object[][] { {DaoQueryOperator.EQ, "id", Integer.parseInt(id) } }).get(0);
		
		if (pwd == null){
			pwd = "111111";
		}
		baseDao.update(SysUser.class,new Object[][] { { "password", Encrypt.e(pwd) } },new Object[][] { { DaoQueryOperator.EQ, "id",Integer.parseInt(id) } });
		
		systemLogService.saveSystemLog("系统管理/用户管理", "密码重置", "用户\""+user.getUserName()+"\"重置了\""+users.getUserName()+"\"的密码", user);
		
		return null;
	}

	@Override
	public String deleteSysUser(SysUser user, String ids) {
		String[] idArray = ids.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		List<SysUser> users = new ArrayList<SysUser>();
		for (String id : idArray) {
			idList.add(Integer.parseInt(id));
			SysUser u = baseDao.query(SysUser.class, new Object[][]{{DaoQueryOperator.EQ, "id", Integer.parseInt(id)}}).get(0);
			users.add(u);
		}

		StringBuffer username = new StringBuffer();
		for(SysUser sur : users){
			username.append(sur.getUserName()+",");
		}
		baseDao.delete(UserToRole.class, "sysUser.id", idList);
		baseDao.delete(SysUser.class, "id", idList);
		
		
		systemLogService.saveSystemLog("系统管理/用户管理", "删除用户", "用户\""+user.getUserName()+"\"删除了\""+username.toString().substring(0, username.length()-1)+"\"用户", user);
		
		return null;
	}

	@Override
	public boolean checkMail(Integer id, String mail) {
		long count = 0;
		if (id != null && id > 0) {
			count = baseDao.count(null, false, SysUser.class,new Object[][] { { DaoQueryOperator.EQ, "email", mail } });
		} else
			count = baseDao.count(null, false, SysUser.class, new Object[][] {{ DaoQueryOperator.NIN, "id", new Integer[] { id } },{ DaoQueryOperator.EQ, "email", mail } });
		return count > 0 ? false : true;
	}

	@Override
	public boolean checkUname(Integer id, String userName) {
		long count = 0;
		if (id != null && id > 0) {
			count = baseDao.count(null, false, SysUser.class,new Object[][] { { DaoQueryOperator.EQ, "userName",userName } });
		} else{
			count = baseDao.count(null, false, SysUser.class, new Object[][] {{ DaoQueryOperator.NIN, "id", new Integer[] { id } },{ DaoQueryOperator.EQ, "userName", userName } });
		}
			
		return count > 0 ? false : true;
	}
	
	@Override
	public boolean checkRoleName(Integer id,String name){
		long count = 0;
		if (id == null || id == 0) {
			count = baseDao.count(null, false, Role.class,new Object[][] { { DaoQueryOperator.EQ, "name",name } });
		} else{
			count = baseDao.count(null, false, Role.class, new Object[][] {{ DaoQueryOperator.NIN, "id", new Integer[] { id } },{ DaoQueryOperator.EQ, "name", name } });
		}
			
		return count > 0 ? false : true;
	}

	@Override
	public String editUserRole(SysUser user, Integer[] idArray, String roleIds) {
		for (int i = 0; i < idArray.length; i++) {
			SysUser sysUser = baseDao.get(SysUser.class, idArray[i]);
			// 删除已有的角色
			baseDao.delete(UserToRole.class, "sysUser.id", new Object[][] { {DaoQueryOperator.EQ, "sysUser.id", sysUser.getId() } });

			String[] roleIdArray = roleIds.split(",");
			for (String id : roleIdArray) {
				Role role = baseDao.get(Role.class, Integer.parseInt(id));
				UserToRole userRole = new UserToRole();
				userRole.setRole(role);
				userRole.setSysUser(sysUser);
				baseDao.save(userRole);
			}
			
			systemLogService.saveSystemLog("系统管理/用户管理", "角色配置", "用户\""+user.getUserName()+"\"更改了\""+sysUser.getUserName()+"\"的角色", user);
		}
		return null;
	}


	@Override
	public String editUserHasApp(SysUser attribute, Integer[] ids, String appIds) {
		for(int i=0;i<ids.length;i++){
			SysUser sysUser = baseDao.get(SysUser.class, ids[i]);
			//删除已有的内容提供商
			baseDao.delete(SysUserToApp.class,"sysUser.id",new Object[][]{{ DaoQueryOperator.EQ,"sysUser.id",sysUser.getId() }});
			String[] appIdArray = appIds.split(",");
			for(String id:appIdArray){
				AppInfo appInfo = baseDao.get(AppInfo.class, Integer.parseInt(id));
				SysUserToApp sysUserToApp = new SysUserToApp();
				sysUserToApp.setAppInfo(appInfo);
				sysUserToApp.setSysUser(sysUser);
				baseDao.save(sysUserToApp);
			}
			systemLogService.saveSystemLog("系统管理/用户管理", "内容提供商授权", "用户\""+sysUser.getUserName()+"\"更改了\""+sysUser.getUserName()+"\"的内容提供商", sysUser);
		}
		return null;
	}
	/**
	 * 
	 *
	 * @create
	 * @since 修改登录用户密码
	 * @param id 当前登录用户
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @return -1 : 旧密码错误 0 :执行成功 1:其他错误
	 */
	@Override
	public int modifyPwd(int id, String oldPwd, String newPwd) {
		SysUser sysUser = baseDao.get(SysUser.class, id);
		if (sysUser == null){
			return 1;
		}	
		if (!Encrypt.e(oldPwd).equals(sysUser.getPassword())) {
			return -1;
		} else {
			int index = baseDao.update(SysUser.class, new Object[][] { {"password", Encrypt.e(newPwd) } }, new Object[][] { {DaoQueryOperator.EQ, "id", id } });
			if (index != -1) {
				systemLogService.saveSystemLog("设置", "修改密码", "用户"+sysUser.getUserName()+"修改了密码", sysUser);
				return 0;
			} else {
				return 1;
			}
		}
	}

	@Override
	public String deleteLog(String ids) {
		String[] idArray = ids.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		for (String id : idArray) {
			idList.add(Integer.parseInt(id));
		}
		baseDao.delete(SystemLog.class, "id", idList);
		
		return null;
	}

	@Override
	public byte[] downloadLogToExcel() {
		return ExcelUtil.downloadLogToExcel(msg);
	}

}
