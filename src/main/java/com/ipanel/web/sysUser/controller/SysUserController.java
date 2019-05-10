package com.ipanel.web.sysUser.controller;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.common.model.BaseDataModel.ResponseDataModel;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.sysUser.pageModel.LogModel;
import com.ipanel.web.sysUser.pageModel.PermissionModel;
import com.ipanel.web.sysUser.pageModel.RoleModel;
import com.ipanel.web.sysUser.pageModel.SysUserModel;
import com.ipanel.web.sysUser.service.ISysUserService;
import com.ipanel.web.utils.Constants;
import com.ipanel.web.utils.DateUtil;
import com.ipanel.web.utils.TimeUtil;
import com.ipanel.webapp.framework.util.Log;

@Controller
@RequestMapping("/sysUserController")
public class SysUserController {

	private final String TAG = "SysUserController";

	@Autowired
	private ISysUserService sysUserService;

	@RequestMapping("/queryRole")
	@ResponseBody
	public PageDataModel queryRole(RoleModel role) {
		try {
			Log.i(TAG, "************enter queryRole,page:" + role.getPage()
					+ " rows:" + role.getRows());
			return sysUserService.queryRole(role.getPage(), role.getRows());
		} catch (Exception e) {
			Log.e(TAG, "***queryRole throw exception:" + e);
			return null;
		}
	}

	@RequestMapping("/querySystemLog")
	@ResponseBody
	public PageDataModel querySystemLog(HttpServletRequest request, LogModel m) {
		try {
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			/*
			 * if(startTime!=null){ startTime=startTime.replaceAll("-", "/"); }
			 * if(endTime!=null){ endTime=endTime.replaceAll("-", "/"); }
			 */
			Log.i(TAG, startTime + ";" + endTime
					+ "-------------------------------");
			return sysUserService.querySystemLog(m, startTime, endTime);
		} catch (Exception e) {
			Log.e(TAG, "***querySystemLog throw exception:" + e);
			return null;
		}
	}

	@RequestMapping("/queryPermissionItem")
	@ResponseBody
	public List<PermissionModel> queryPermissionItem() {
		try {
			return sysUserService.queryAllPermissionItem();
		} catch (Exception e) {
			Log.e(TAG, "***queryPermissionItem throw exception:" + e);
		}
		return null;
	}

	@RequestMapping("/editRole")
	@ResponseBody
	public ResponseDataModel editRole(HttpSession session, RoleModel roleModel) {
		try {
			Log.i(TAG,
					"***enter editRole id:" + roleModel.getId() + " name:"
							+ roleModel.getName() + " resourceIds:"
							+ roleModel.getResourceIds());
			if (this.sysUserService.getRoleByNameAndId(roleModel.getName(),
					roleModel.getId()) == -1) {
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
						"角色名冲突");
			}
			roleModel = this.sysUserService.editRole(
					(SysUser) session.getAttribute(Constants.SESSION_USER),
					roleModel);
			return new ResponseDataModel(ResponseDataModel.SUCCESS, roleModel);
		} catch (Exception e) {
			Log.e(TAG, "***editRole throw exception:" + e);
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, null);
		}
	}

	@RequestMapping("/addRole")
	@ResponseBody
	public ResponseDataModel addRole(HttpSession session, RoleModel roleModel) {
		try {
			Log.i(TAG, "***enter addRole name:" + roleModel.getName()
					+ " resourceIds:" + roleModel.getResourceIds());
			if (this.sysUserService.getRoleByName(roleModel.getName()) == -1) {
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
						"角色名已经存在");
			}
			roleModel = this.sysUserService.addRole(
					(SysUser) session.getAttribute(Constants.SESSION_USER),
					roleModel);
			return new ResponseDataModel(ResponseDataModel.SUCCESS, roleModel);
		} catch (Exception e) {
			Log.e(TAG, "***addRole throw exception:" + e);
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, null);
		}

	}

	@RequestMapping("/deleteRole")
	@ResponseBody
	public ResponseDataModel deleteRole(HttpSession session, String ids) {
		try {
			Log.i(TAG, "****enter deleteRole,ids:" + ids);
			this.sysUserService
					.deleteRole((SysUser) session
							.getAttribute(Constants.SESSION_USER), ids);
			return new ResponseDataModel(ResponseDataModel.SUCCESS, null);
		} catch (Exception e) {
			Log.e(TAG, "***deleteRole throw exception:" + e);
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, null);
		}
	}

	@RequestMapping("/querySysUser")
	@ResponseBody
	public PageDataModel querySysUser(SysUserModel m) {
		try {
			Log.i(TAG, "************enter querySysUser,page:" + m.getPage()
					+ " rows:" + m.getRows());
			return sysUserService.queryAllSysUser(m);
		} catch (Exception e) {
			Log.e(TAG, "***querySysUser throw exception:" + e);
			return null;
		}
	}

	@RequestMapping("/editSysUser")
	@ResponseBody
	public ResponseDataModel editSysUser(HttpSession session, SysUserModel model) {
		try {
			Log.i(TAG, "***enter editSysUser id:" + model.getId() + " name:"
					+ model.getUserName());
			this.sysUserService.editSysUser(
					(SysUser) session.getAttribute(Constants.SESSION_USER),
					model);
			return new ResponseDataModel(ResponseDataModel.SUCCESS, null);
		} catch (Exception e) {
			Log.e(TAG, "***editSysUser throw exception:" + e);
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, null);
		}
	}

	/**
	 * 批量授权内容提供商给用户
	 * 
	 * @author fangg 2017年5月31日 下午4:16:32
	 * @param session
	 * @param ids
	 * @param appIds
	 * @return
	 */
	@RequestMapping("/editUserHasApp")
	@ResponseBody
	public ResponseDataModel editUserHasApp(HttpSession session, Integer[] ids,
			String appIds) {
		try {
			Log.i(TAG, "***enter editUserHasApp idArray:" + ids + "appIds = "
					+ appIds);
			this.sysUserService.editUserHasApp(
					(SysUser) session.getAttribute(Constants.SESSION_USER),
					ids, appIds);
			return new ResponseDataModel(ResponseDataModel.SUCCESS, null);
		} catch (Exception e) {
			Log.e(TAG, "***editUserHasApp throw exception:" + e);
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, null);
		}
	}

	/**
	 * 批量配置用户角色
	 * 
	 * @param idArray
	 * @param roleIds
	 * @return
	 */
	@RequestMapping("/editUserRole")
	@ResponseBody
	public ResponseDataModel editUserRole(HttpSession session, Integer[] ids,
			String roleIds) {
		try {
			Log.i(TAG, "***enter editUserRole idArray:" + ids + "roleIds = "
					+ roleIds);
			this.sysUserService.editUserRole(
					(SysUser) session.getAttribute(Constants.SESSION_USER),
					ids, roleIds);
			return new ResponseDataModel(ResponseDataModel.SUCCESS, null);
		} catch (Exception e) {
			Log.e(TAG, "***editUserRole throw exception:" + e);
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, null);
		}
	}

	@RequestMapping("/addSysUser")
	@ResponseBody
	public ResponseDataModel addSysUser(HttpSession session,
			SysUserModel sysUserModel) {
		try {
			Log.i(TAG, "***enter addSysUser name:" + sysUserModel.getUserName());
			if (this.sysUserService.getUserByName(sysUserModel.getUserName()) == -1) {
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
						"用户名已经存在");
			}
			this.sysUserService.addSysUser(
					(SysUser) session.getAttribute(Constants.SESSION_USER),
					sysUserModel);
			return new ResponseDataModel(ResponseDataModel.SUCCESS, null);
		} catch (Exception e) {
			Log.e(TAG, "***addSysUser throw exception:" + e);
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, null);
		}
	}

	@RequestMapping("/deleteSysUser")
	@ResponseBody
	public ResponseDataModel deleteSysUser(HttpSession session, String ids) {
		try {
			Log.i(TAG, "****enter deleteSysUser,ids:" + ids);
			this.sysUserService
					.deleteSysUser((SysUser) session
							.getAttribute(Constants.SESSION_USER), ids);
			return new ResponseDataModel(ResponseDataModel.SUCCESS, null);
		} catch (Exception e) {
			Log.e(TAG, "***deleteSysUser throw exception:" + e);
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, null);
		}
	}

	@RequestMapping("/resetUserPwd")
	@ResponseBody
	public ResponseDataModel resetUserPwd(HttpSession session, String id) {
		try {
			Log.i(TAG, "****enter resetUserPwd,id:" + id);
			this.sysUserService.updateUserPwd_reset(
					(SysUser) session.getAttribute(Constants.SESSION_USER), id);
			return new ResponseDataModel(ResponseDataModel.SUCCESS, null);
		} catch (Exception e) {
			Log.e(TAG, "***resetUserPwd throw exception:" + e);
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, null);
		}
	}

	@RequestMapping("/checkMail")
	@ResponseBody
	public boolean checkMail(Integer id, String mail_adr) {
		try {
			return this.sysUserService.checkMail(id, mail_adr);
		} catch (Exception e) {
			Log.e(TAG, "***checkMail throw exception:" + e);
			return false;
		}
	}

	@RequestMapping("/checkUname")
	@ResponseBody
	public boolean checkUname(Integer id, String user_name) {
		try {
			if (this.sysUserService.getUserByName(user_name) == -1) {
				return false;
			}
			return true;
			// return this.sysUserService.checkUname(id, user_name);
		} catch (Exception e) {
			Log.e(TAG, "***checkUname throw exception:" + e);
			return false;
		}
	}

	@RequestMapping("/checkRoleName")
	@ResponseBody
	public boolean checkRoleName(Integer id, String name) {
		try {
			return this.sysUserService.checkRoleName(id, name);
		} catch (Exception e) {
			Log.e(TAG, "***checkRoleName throw exception:" + e);
			return false;
		}
	}

	@RequestMapping("/logout")
	@ResponseBody
	public ResponseDataModel logout(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		return new ResponseDataModel(ResponseDataModel.SUCCESS, null);
	}

	@RequestMapping("/modifyPwd")
	@ResponseBody
	public ResponseDataModel modifyPwd(HttpSession session, String oldPwd,
			String newPwd) {
		int id = (Integer) session.getAttribute(Constants.SESSION_USER_ID);
		Log.i(TAG, "****enter modifyPwd,id:" + id);
		int index = this.sysUserService.modifyPwd(id, oldPwd, newPwd);
		if (index == -1) {
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
					"旧密码与登录用户密码不一致");
		} else if (index == 0) {
			return new ResponseDataModel(ResponseDataModel.SUCCESS, "执行成功:");
		} else {
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, "");
		}
	}

	@RequestMapping("/deleteLog")
	@ResponseBody
	public ResponseDataModel deleteLog(String ids) {
		try {
			Log.i(TAG, "****enter deleteLog,ids:" + ids);
			this.sysUserService.deleteLog(ids);
			return new ResponseDataModel(ResponseDataModel.SUCCESS, null);
		} catch (Exception e) {
			Log.e(TAG, "***deleteLog throw exception:" + e);
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, null);
		}
	}

	@RequestMapping("downloadLogToExcel")
	@ResponseBody
	public void downloadLogToExcel(HttpServletResponse response) {
		try {
			Log.i(TAG, "***downloadLogToExcel***");
			byte[] datas = sysUserService.downloadLogToExcel();
			String fileName = "libraryMS_log"+TimeUtil.getCurrentTime()
					+ DateUtil.formatTimeForSimple(new Date()) + ".xls";
			// 设置response的Header
			response.setContentType("application/*;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ fileName);
			OutputStream os = response.getOutputStream();
			os.write(datas);
			os.flush();
			os.close();

			// return new ResponseDataModel(ResponseDataModel.SUCCESS, null);
		} catch (Exception e) {
			Log.e(TAG, "***downloadLogToExcel throw exception:" + e);
			// return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
			// null);
		}
	}

}
