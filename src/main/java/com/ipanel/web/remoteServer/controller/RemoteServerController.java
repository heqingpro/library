package com.ipanel.web.remoteServer.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.common.model.BaseDataModel.ResponseDataModel;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.remoteServer.pageModel.RemoteServerModel;
import com.ipanel.web.remoteServer.service.IRemoteServerService;
import com.ipanel.web.utils.Constants;
import com.ipanel.webapp.framework.util.Log;

@Controller
@RequestMapping("/remoteServerController")
public class RemoteServerController {
	
	private final String TAG="RemoteServerController";
	
	@Autowired
	private IRemoteServerService remoteServerService;
	
	@RequestMapping("/queryRemoteServerList")
	@ResponseBody
	public PageDataModel queryRemoteServerList(RemoteServerModel remoteServerModel){
		try {
			Log.i(TAG, "queryremoteServerList enter remoteServerName="+remoteServerModel.getRemoteServerName());
			return remoteServerService.queryRemoteServerList(remoteServerModel.getRemoteServerName(), remoteServerModel.getPage(), remoteServerModel.getRows());
		} catch (Exception e) {
			Log.i(TAG, "***queryremoteServerList throw exception:" + e);
			return null;
		}
		
	}
	
	@RequestMapping("/addRemoteServer")
	@ResponseBody
	public ResponseDataModel addRemoteServer(HttpSession session,RemoteServerModel model){
		try {
			Log.i(TAG, "****addRemoteServer****");
			String msg=remoteServerService.addRemoteServer((SysUser)session.getAttribute(Constants.SESSION_USER), model);
			if(msg!=null&&!"".equals(msg)){
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, msg);
			}
			return new ResponseDataModel(ResponseDataModel.SUCCESS, null);
		} catch (Exception e) {
			Log.e(TAG, "***addRemoteServer throw exception:" + e);
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, null);
		}
	}
	
	@RequestMapping("/editRemoteServer")
	@ResponseBody
	public ResponseDataModel editRemoteServer(HttpSession session,RemoteServerModel model){
		try {
			Log.i(TAG, "****editRemoteServer****");
			String msg=remoteServerService.editRemoteServer((SysUser)session.getAttribute(Constants.SESSION_USER), model);
			if(msg!=null&&!"".equals(msg)){
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, msg);
			}
			return new ResponseDataModel(ResponseDataModel.SUCCESS, null);
		} catch (Exception e) {
			Log.e(TAG, "***editRemoteServer throw exception:" + e);
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, null);
		}
	}
	
	@RequestMapping("/deleteRemoteServer")
	@ResponseBody
	public ResponseDataModel deleteRemoteServer(HttpSession session,String ids){
		try {
			Log.i(TAG, "****deleteRemoteServer enter ids=" + ids);
			remoteServerService.deleteRemoteServer((SysUser)session.getAttribute(Constants.SESSION_USER) ,ids);
			return new ResponseDataModel(ResponseDataModel.SUCCESS, null);
		} catch (Exception e) {
			Log.e(TAG, "***deleteRemoteServer throw exception:" + e);
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, null);
		}
	}
	
	
}
