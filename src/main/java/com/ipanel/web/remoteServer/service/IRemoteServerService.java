package com.ipanel.web.remoteServer.service;

import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.remoteServer.pageModel.RemoteServerModel;

public interface IRemoteServerService {
	
	public PageDataModel queryRemoteServerList(String remoteServerName,int page, int rows);
	
	public String addRemoteServer(SysUser user,RemoteServerModel model);
	
	public String editRemoteServer(SysUser user,RemoteServerModel model);
	
	public void deleteRemoteServer(SysUser user,String ids);
	
}
