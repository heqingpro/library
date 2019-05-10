package com.ipanel.web.remoteServer.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ipanel.web.common.dao.BaseDao;
import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.entity.RemoteServer;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.remoteServer.pageModel.RemoteServerModel;
import com.ipanel.web.remoteServer.service.IRemoteServerService;
import com.ipanel.web.sysUser.service.impl.SystemLogService;
import com.ipanel.webapp.framework.core.dao.DaoQueryOperator;

@Service("remoteServerService")
public class RemoteServerServiceImpl implements IRemoteServerService{
	
private final  String TAG ="RemoteServerServiceImpl";
	
	@Resource(name = "baseDao")
	private BaseDao baseDao;
	
	@Resource(name = "systemLogService")
	private SystemLogService systemLogService;

	@Override
	public PageDataModel queryRemoteServerList(String remoteServerName,int page, int rows) {

		List<Object[]> paramList = new ArrayList<Object[]>();
		if (remoteServerName != null && !"".equals(remoteServerName)) {
			paramList.add(new Object[] { DaoQueryOperator.LIKE, "remoteServerName", remoteServerName });
		}
		
		Object[][] paramObj = null;
		if (paramList.size() > 0) {
			paramObj = new Object[paramList.size()][3];
			for (int i = 0; i < paramList.size(); i++){
				paramObj[i] = paramList.get(i);
			}	
		}
		
		List<RemoteServer> remoteServerList=(List<RemoteServer>) baseDao.query(null, false, RemoteServer.class, paramObj, new String[] { "id" },null, (page - 1) * rows, rows);
		long total=baseDao.count(null, false, RemoteServer.class, paramObj);
		List<RemoteServerModel> modelList=new ArrayList<RemoteServerModel>();
		for(RemoteServer remoteServer:remoteServerList){
			RemoteServerModel model=new RemoteServerModel();
			BeanUtils.copyProperties(remoteServer, model);
			modelList.add(model);
		}
		return new PageDataModel((int)total, modelList);
	
	}

	@Override
	public String addRemoteServer(SysUser user, RemoteServerModel model) {
		
		RemoteServer remoteServer=new RemoteServer();
		BeanUtils.copyProperties(model, remoteServer);
		baseDao.save(remoteServer);
		systemLogService.saveSystemLog("服务器管理/服务器管理", "添加服务器", "用户\""+user.getUserName()+"\"添加了\""+model.getRemoteServerName()+"\"服务器", user);
		return null;
	}

	@Override
	public String editRemoteServer(SysUser user, RemoteServerModel model) {
		
		RemoteServer remoteServer=baseDao.get(RemoteServer.class, model.getId());
		BeanUtils.copyProperties(model, remoteServer);
		baseDao.update(remoteServer);
		systemLogService.saveSystemLog("服务器管理/服务器管理", "修改服务器", "用户\""+user.getUserName()+"\"修改了\""+model.getRemoteServerName()+"\"服务器", user);
		return null;
	}

	@Override
	public void deleteRemoteServer(SysUser user, String ids) {
		StringBuffer remoteServerNameBuffer = new StringBuffer();
		String[] remoteServerIdArray = ids.split(",");
		for (String id : remoteServerIdArray) {
			Integer intId=Integer.parseInt(id);
			RemoteServer RemoteServer = baseDao.get(RemoteServer.class,intId );
			if (RemoteServer!=null){
				remoteServerNameBuffer.append(RemoteServer.getRemoteServerName()+",");
				baseDao.delete(RemoteServer.class,intId);
			}
		}
		systemLogService.saveSystemLog("服务器管理/服务器管理", "删除服务器", "用户\""+user.getUserName()+"\"删除了\""+remoteServerNameBuffer.toString().substring(0, remoteServerNameBuffer.length()-1)+"\"服务器", user);
	}

}
