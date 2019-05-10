package com.ipanel.web.contentProvider.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.common.model.BaseDataModel.ResponseDataModel;
import com.ipanel.web.contentProvider.pageModel.AppModel;
import com.ipanel.web.contentProvider.service.IAppService;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.utils.Constants;
import com.ipanel.webapp.framework.util.Log;

@Controller
@RequestMapping("/appController")
public class AppController {

	private final String TAG = "AppController";
	
	@Autowired
	private IAppService appService;
	/**
	 * 创建内容提供商
	 * @author fangg
	 * 2017年5月13日 下午5:02:58
	 * @param session
	 * @param appModel
	 * @return
	 */
	@RequestMapping("/addApp")
	@ResponseBody
	public ResponseDataModel addApp(HttpSession session,AppModel appModel){
		try {
			Log.i(TAG,"addApp enter appName="+ appModel.getAppName());
			String msg = appService.addApp((SysUser) session.getAttribute(Constants.SESSION_USER),appModel);
			if(StringUtils.isNotEmpty(msg)){
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, msg);
			}
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		} catch (Exception e) {
			Log.e(TAG, "***addApp throw exception:" + e);
			e.printStackTrace();
			return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
		}
	}	
	/**
	 * 获取内容提供商列表
	 * @author fangg
	 * 2017年5月13日 下午5:03:11
	 * @param session
	 * @param appModel
	 * @return
	 */
	@RequestMapping("/queryAppList")
	@ResponseBody
	public PageDataModel queryAppList(HttpSession session,AppModel appModel){
		try {
			Log.i(TAG,"*** queryAppList enter appName="+ appModel.getAppName());
			
			SysUser sysUser = (SysUser)session.getAttribute(Constants.SESSION_USER);
			if("superAdmin".equals(sysUser.getUserType())){
				return appService.queryAppList(appModel,null);	
			}
			return appService.queryAppList(appModel,sysUser);
						
		} catch (Exception e) {
			Log.e(TAG, "***queryAppList throw exception:" + e);
			e.printStackTrace();			
			return null;
		}
	}
	/**
	 * 用户授权的app,超级管理员的特权
	 * @author fangg
	 * 2017年5月31日 下午4:41:35
	 * @return
	 */
	@RequestMapping("/queryApps")
	@ResponseBody
	public PageDataModel queryApps(HttpSession session){
		try {
			Log.i(TAG,"*** queryApps enter");
			SysUser sysUser = (SysUser) session.getAttribute(Constants.SESSION_USER);
			//超级管理员，返回所有的内容提供商，非超级管理员，返回自身已授权的内容提供商
			if("superAdmin".equals(sysUser.getUserType())){
				return appService.queryApps(null);
			}
			return appService.queryApps(sysUser);			
		} catch (Exception e) {
			Log.e(TAG, "*** queryApps throw exception:" + e);
			return null;
		}
	}
	/**
	 * 查询内容提供商详情
	 * @author fangg
	 * 2017年5月13日 下午5:03:20
	 * @param appId
	 * @return
	 */
	@RequestMapping("/getAppDetail")
	@ResponseBody
	public AppModel getAppDetail(Integer appId){
		try {
			Log.i(TAG, "***　enter getAppDetail *** appId="+appId);
			return appService.getAppDetail(appId);
		} catch (Exception e) {
			Log.e(TAG, "***　getAppDetail throw Exception");
			return null;
		}
		
	}
	/**
	 * 修改内容提供商信息
	 * @author fangg
	 * 2017年5月13日 下午5:03:35
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/editApp")
	@ResponseBody
	public ResponseDataModel editApp(HttpSession session,AppModel model){
		try {
			Log.i(TAG, "****editApp appName=" + model.getAppName());
			String msg = appService.editApp((SysUser) session.getAttribute(Constants.SESSION_USER),model);
			if (msg != null && !"".equals(msg)) {
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, msg);
			}
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		} catch (Exception e) {
			Log.e(TAG, "***editApp throw exception:" + e);
			return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
		}
	}
	/**
	 * 删除内容提供商
	 * @author fangg
	 * 2017年5月13日 下午5:03:52
	 * @param session
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteApp")
	@ResponseBody
	public ResponseDataModel deleteApp(HttpSession session, String ids) {
		try {
			Log.i(TAG, "***deleteApp enter ids=" + ids);
			appService.deleteApp((SysUser) session.getAttribute(Constants.SESSION_USER), ids);
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		} catch (Exception e) {
			Log.e(TAG, "***deleteApp throw exception:" + e);
			return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}


