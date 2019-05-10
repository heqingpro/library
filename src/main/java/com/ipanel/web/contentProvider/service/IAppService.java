package com.ipanel.web.contentProvider.service;

import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.contentProvider.pageModel.AppModel;
import com.ipanel.web.entity.SysUser;

public interface IAppService {

	public PageDataModel queryAppList(AppModel appModel, SysUser sysUser);

	public String addApp(SysUser user, AppModel appModel);

	public String editApp(SysUser user, AppModel model);

	public void deleteApp(SysUser user, String ids);

	public AppModel getAppDetail(Integer appId);

	public PageDataModel queryApps(SysUser sysUser);
	
	

}
