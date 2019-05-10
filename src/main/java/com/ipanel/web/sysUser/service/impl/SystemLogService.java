package com.ipanel.web.sysUser.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ipanel.web.common.dao.BaseDao;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.entity.SystemLog;

@Service("systemLogService")
public class SystemLogService {
	@Resource(name = "baseDao")
	private BaseDao baseDao;
	
	public void saveSystemLog(String moduleName, String operatingFunction, String operatingDesc, SysUser sysUser) {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SystemLog systemLog = new SystemLog();
		systemLog.setModuleName(moduleName);
		systemLog.setOperatingFunction(operatingFunction);
		systemLog.setOperatingDate(format.format(date));
		systemLog.setOperatingDesc(operatingDesc);
		systemLog.setSysUser(sysUser);
		baseDao.save(systemLog);
	}
}
