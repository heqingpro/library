package com.ipanel.web.contentProvider.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ipanel.web.common.dao.BaseDao;
import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.contentProvider.pageModel.AppModel;
import com.ipanel.web.contentProvider.service.IAppService;
import com.ipanel.web.define.AppDefined;
import com.ipanel.web.entity.AppInfo;
import com.ipanel.web.entity.NodeInfo;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.entity.SysUserToApp;
import com.ipanel.web.sysUser.service.impl.SystemLogService;
import com.ipanel.web.utils.TimeUtil;
import com.ipanel.webapp.framework.core.dao.DaoQueryOperator;
import com.ipanel.webapp.framework.util.Log;

@Service("appService")
public class AppServiceImpl implements IAppService {

	private final String TAG = "AppServiceImpl";
	
	@Resource
	private BaseDao baseDao;
	
	@Resource(name="systemLogService")
	private SystemLogService systemLogService;
	
	@SuppressWarnings("unchecked")
	@Override
	public PageDataModel queryAppList(AppModel appModel,SysUser sysUser) {
		List<Object[]> paramList = new ArrayList<Object[]>();
		String appName = appModel.getAppName();
		int page  = appModel.getPage();
		int rows  = appModel.getRows();
		if(StringUtils.isNotEmpty(appName)){
			paramList.add(new Object[]{
					DaoQueryOperator.LIKE,"appName", appName			
			});
		}
		
		Object[][] paramObj = null;
		if (paramList.size() > 0) {
			paramObj = new Object[paramList.size()][3];
			for (int i = 0; i < paramList.size(); i++) {
				paramObj[i] = paramList.get(i);
			}
		}
		List<AppModel> modelList = new ArrayList<AppModel>();
		//超级管理员返回全部内容提供商
		if(sysUser==null){
			List<AppInfo> appList = (List<AppInfo>)baseDao.query(null,false,
					AppInfo.class,paramObj,null,new String[] { "id" },(page-1)*rows,rows);
			long total = baseDao.count(null, false, AppInfo.class, paramObj);
			for(AppInfo app:appList){
				AppModel model = new AppModel();
				model.setId(app.getId());
				model.setName(app.getAppName());
				model.setAppName(app.getAppName());
				model.setEnName(app.getEnName());
				model.setRemark(app.getRemark());
				modelList.add(model);
			}
			return new PageDataModel((int)total,modelList);
		}else {//普通管理员条件查询
			sysUser = baseDao.get(SysUser.class, sysUser.getId());
			List<SysUserToApp> sysUserToApps = sysUser.getSysUserToApps();
			for(SysUserToApp sysUserToApp:sysUserToApps){
				AppInfo appInfo = sysUserToApp.getAppInfo();
				if(StringUtils.isEmpty(appName)){
					AppModel model = new AppModel();
					model.setId(appInfo.getId());
					model.setName(appInfo.getAppName());
					model.setAppName(appInfo.getAppName());
					model.setEnName(appInfo.getEnName());
					model.setRemark(appInfo.getRemark());
					modelList.add(model);
				}else if(appInfo.getAppName().contains(appName)){
					AppModel model = new AppModel();
					model.setId(appInfo.getId());
					model.setName(appInfo.getAppName());
					model.setAppName(appInfo.getAppName());
					model.setEnName(appInfo.getEnName());
					model.setRemark(appInfo.getRemark());
					modelList.add(model);
				}
			}
			int total = modelList.size();
			if(modelList.size()>(page-1)*rows){
				modelList = modelList.subList((page-1)*rows, modelList.size());
			}else{
				modelList.clear();
			}
			return new PageDataModel(total,modelList);		
		}
	}

	@Override
	public PageDataModel queryApps(SysUser sysUser) {
		List<AppModel> modelList = new ArrayList<AppModel>();
		if(sysUser==null){
			List<AppInfo> appInfos = baseDao.query(AppInfo.class,new Object[][]{{}});
			for(AppInfo app:appInfos){
				AppModel model = new AppModel();
				model.setId(app.getId());
				model.setName(app.getAppName());
				model.setEnName(app.getEnName());
				modelList.add(model);
			}
		}else {
			sysUser = baseDao.get(SysUser.class, sysUser.getId());
			List<SysUserToApp> sysUserToApps = sysUser.getSysUserToApps();
			for(SysUserToApp sysUserToApp:sysUserToApps){
				AppInfo app = sysUserToApp.getAppInfo();
				AppModel model = new AppModel();
				model.setId(app.getId());
				model.setName(app.getAppName());
				model.setEnName(app.getEnName());
				modelList.add(model);
			}
		}		
		return new PageDataModel(modelList.size(), modelList);
	}
	
	@Override
	public String addApp(SysUser user, AppModel appModel) {
		List<AppInfo> appInfoList = baseDao.query(AppInfo.class, new Object[][]{{
			DaoQueryOperator.EQ,"appName",appModel.getAppName()
		}});
		if(appInfoList!=null&&appInfoList.size()>0){
			return AppDefined.APP_EXIST;
		}
		AppInfo appInfo = new AppInfo();
		appInfo.setAppName(appModel.getAppName());
		appInfo.setEnName(appModel.getEnName());
		appInfo.setRemark(appModel.getAppName());
		appInfo.setAddTime(TimeUtil.getCurrentTime());
		baseDao.save(appInfo);
		//保存跟节点到分类表里面
		NodeInfo nodeInfo = new NodeInfo();
		nodeInfo.setId(appInfo.getId());
		nodeInfo.setNodeName(appModel.getAppName());
		nodeInfo.setAppInfo(appInfo);
		nodeInfo.setParentNode_id(0);//根节点标识
		nodeInfo.setIsParent(1); //一定是父节点
		nodeInfo.setNodeType(-1);//根节点分类
		nodeInfo.setEnName(appModel.getEnName());
		nodeInfo.setAddtime(TimeUtil.getCurrentTime());
		baseDao.save(nodeInfo);
		appInfo.setRootNodeId(nodeInfo.getId());//将跟分类节点保存在内容提供商表里面
		//给当前用户授权,创建内容提供商的人会默认自动授权
		SysUserToApp sysUserToApp = new SysUserToApp();
		sysUserToApp.setAppInfo(appInfo);
		sysUserToApp.setSysUser(user);
		baseDao.save(sysUserToApp);
		try {
			systemLogService.saveSystemLog("专区内容提供商管理", "添加内容提供商", "用户\""+user.getUserName()+"\"添加了\""+appModel.getAppName()+ "\"内容提供商", user);
		} catch (Exception e) {
			Log.e(TAG, "系统添加日志出错"+e);
		}
		return null;
	}

	@Override
	public String editApp(SysUser user, AppModel model) {
		List<AppInfo> appList = baseDao.query(
				AppInfo.class,
				new Object[][] {
						{ DaoQueryOperator.EQ, "appName",
								model.getAppName() },
						{ DaoQueryOperator.NIN, "id", model.getId() } }
				);
		if (appList != null && appList.size() > 0) {
			return AppDefined.APP_EXIST;
		}
		AppInfo appInfo = baseDao.get(AppInfo.class, model.getId());
		appInfo.setAppName(model.getAppName());
		appInfo.setEnName(model.getEnName());
		appInfo.setRemark(model.getRemark());
		appInfo.setModifyTime(TimeUtil.getCurrentTime());
		baseDao.update(appInfo);
		//修改关联的根分类的名字
		NodeInfo nodeInfo = baseDao.get(NodeInfo.class, appInfo.getRootNodeId());
		nodeInfo.setNodeName(model.getAppName());
		nodeInfo.setEnName(model.getEnName());
		nodeInfo.setModifyTime(TimeUtil.getCurrentTime());
		baseDao.update(nodeInfo);
		try {
			systemLogService.saveSystemLog(
					"专区管理/内容提供商管理",
					"编辑内容提供商",
					"用户\"" + user.getUserName() + "\"编辑了\""
							+ model.getAppName() + "\"内容提供商", user);
		} catch (Exception e) {
			Log.e(TAG, "系统添加日志出错"+e);
		}
		return null;
	}

	@Override
	public void deleteApp(SysUser user, String ids) {
		StringBuffer appNameBuffer = new StringBuffer();
		String[] appIdArr = ids.split(",");
		for(String id :appIdArr){
			Integer intId = Integer.parseInt(id);
			AppInfo appInfo = baseDao.get(AppInfo.class, intId);
			if(appInfo!=null){
				appNameBuffer.append(appInfo.getAppName()+",");
				baseDao.delete(appInfo);//级联自动删除内容提供商下所有相关的分类
				//删除服务器上的静态资源图片
				/**
				 * ……
				 */
				
			}
		}
		
		try {
			systemLogService.saveSystemLog(
					"专区管理/内容提供商管理",
					"删除内容提供商",
					"用户\""
							+ user.getUserName()
							+ "\"删除了\""
							+ appNameBuffer.toString().substring(0,
									appNameBuffer.length() - 1) + "\"内容提供商", user);
		} catch (Exception e) {
			Log.e(TAG, "系统添加日志出错"+e);
		}
		
	}

	@Override
	public AppModel getAppDetail(Integer appId) {
		AppInfo app = baseDao.get(AppInfo.class, appId);
		AppModel model = new AppModel();
		model.setAppName(app.getAppName());
		model.setEnName(app.getEnName());
		model.setRemark(app.getRemark());
		model.setId(app.getId());
		return model;
	}

	

}
