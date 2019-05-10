package com.ipanel.web.sysUser.service;

import java.util.List;

import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.sysUser.pageModel.LogModel;
import com.ipanel.web.sysUser.pageModel.PermissionModel;
import com.ipanel.web.sysUser.pageModel.RoleModel;
import com.ipanel.web.sysUser.pageModel.SysUserModel;
public interface ISysUserService {
	
	public List<String> queryAllUrl();
	
	public List<String> getRoleNameByUrl(String url);
	
	public SysUser querySysUser(String userName,String password);
	
	/**按页次查询角色数据
	 * @param page:页次
	 * @param rows:每次查询的记录size
	 * @return
	 */
	public PageDataModel queryRole(int page,int rows);
	
	public PageDataModel querySystemLog(LogModel m,String startTime,String endTime);
	
	public List<PermissionModel> queryAllPermissionItem();
	
	public RoleModel addRole(SysUser user, RoleModel roleModel);
	
	public RoleModel editRole(SysUser user, RoleModel roleModel);
	
	public void deleteRole(SysUser user, String ids);
	
	/**查询用户数据
	 * @param page
	 * @param rows
	 * @return
	 */
	public PageDataModel querySysUser(int page, int rows);
	
	public String addSysUser(SysUser user, SysUserModel sysUserModel);
	
	public String editSysUser(SysUser user, SysUserModel sysUserModel);
	
	public String deleteSysUser(SysUser user, String ids);
	
	/**校验电子邮箱是否已注册
	 * @param id
	 * @param mail
	 * @return
	 */
	public boolean checkMail(Integer id,String mail);
	
	/**校验用户帐号是否已注册
	 * @param id
	 * @param userName
	 * @return
	 */
	public boolean checkUname(Integer id,String userName);
	
	public boolean checkRoleName(Integer id,String name);
	
	public String editUserRole(SysUser user, Integer[] idArray,String roleIds);
	
	public String updateUserPwd_reset(SysUser user, String id);
	
	public int getRoleByName(String name);
	
	public int getUserByName(String name);
	
	public int getRoleByNameAndId(String name,int id);
	
	public int modifyPwd(int id,String oldPwd,String newPwd);
	
	public PageDataModel queryAllSysUser(SysUserModel m);
	
	public String deleteLog(String ids);
	
	public byte[] downloadLogToExcel();

	public String editUserHasApp(SysUser attribute, Integer[] ids, String appIds);

}
