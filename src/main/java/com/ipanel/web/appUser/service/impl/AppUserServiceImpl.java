package com.ipanel.web.appUser.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ipanel.web.appUser.pageModel.AppUserModel;
import com.ipanel.web.appUser.service.IAppUserService;
import com.ipanel.web.book.pageModel.EntryModel;
import com.ipanel.web.common.dao.BaseDao;
import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.entity.AppUser;
import com.ipanel.web.entity.AppUserToEntry;
import com.ipanel.web.entity.EntryInfo;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.entity.num.EnumSimpleRecordType;
import com.ipanel.web.sysUser.service.impl.SystemLogService;
import com.ipanel.web.utils.CheckValueUtil;
import com.ipanel.web.utils.ExcelUtil;
import com.ipanel.web.utils.ExcelUtil.Excel;
import com.ipanel.webapp.framework.core.dao.DaoQueryOperator;

/**
 * @author fangg
 * 2017年5月15日 下午3:55:07
 */
@Service(value="appUserService")
public class AppUserServiceImpl implements IAppUserService {

	private final String TAG = "AppUserServiceImpl";
	
	@Resource
	private BaseDao baseDao;
	
	@Resource(name = "systemLogService")
	private SystemLogService systemLogService;
	
	@SuppressWarnings("unchecked")
	@Override
	public PageDataModel queryUserList(SysUser sysUser, AppUserModel model) {
		Object[][] params = new Object[][] { { "userName", model.getUserName() } };
		int count = (int) baseDao.count(null, false, AppUser.class, params);
		List<AppUser> dataList = (List<AppUser>) baseDao.query(null, false,AppUser.class, params, null, new String[]{"id"},(model.getPage() - 1) * model.getRows(), model.getRows());
		List<AppUserModel> modelList = new ArrayList<AppUserModel>();
		for(AppUser user:dataList){
			AppUserModel appUserModel  = new AppUserModel();
			appUserModel.setId(user.getId());
			appUserModel.setUserId(user.getUserUId());
			appUserModel.setCaId(user.getCaId());
			appUserModel.setUserName(user.getUserName());
			modelList.add(appUserModel);
		}
		return new PageDataModel(count, modelList);
	}

	@Override
	public List<EntryModel> queryPersonalEntryList(SysUser sysUser,AppUserModel model) {
		AppUser appUser = baseDao.get(AppUser.class, model.getId());
		if(appUser!=null){
			List<AppUserToEntry> appUserToEntries = appUser.getAppUserToEntry();
			if(appUserToEntries!=null&&appUserToEntries.size()>0){
				List<EntryModel> entryModels = new ArrayList<EntryModel>();
				for(AppUserToEntry appUserToEntry:appUserToEntries){
					EntryInfo entryInfo = appUserToEntry.getEntryInfo();
					if(entryInfo!=null){
						if(model.getRecordType()==null||model.getRecordType()==-1){//查出所有的
							EntryModel  entryModel = new EntryModel();
							entryModel.setId(entryInfo.getId());
							entryModel.setTitle(entryInfo.getTitle());
							entryModels.add(entryModel);
						}else if(model.getRecordType()==appUserToEntry.getRecordType()){//根据搜索条件返回数据
							EntryModel  entryModel = new EntryModel();
							entryModel.setId(entryInfo.getId());
							entryModel.setTitle(entryInfo.getTitle());
							entryModels.add(entryModel);
						}
						
					}
				}
				int beginIndex = (model.getPage()-1)*model.getRows();
				if(entryModels!=null&&entryModels.size()>=beginIndex){					
					entryModels = entryModels.subList(beginIndex, entryModels.size());
				}
				return entryModels;
			}
			
		}
		return null;
	}

	@Override
	public String importAppUser(CommonsMultipartFile appUserImportFile,
			String fileType, SysUser user) {
		InputStream in = null;
		try {
			in = appUserImportFile.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Excel excel = ExcelUtil.parseExcel(fileType, in);
		List<AppUserModel> appUserModels= ExcelUtil.excelToAppUserList(excel);
		if(appUserModels!=null&&appUserModels.size()>0){
			CheckValueUtil checkValueUtil = new CheckValueUtil();
			for(AppUserModel appUserModel:appUserModels){
				if(!checkValueUtil.emptyCheck("CAID", appUserModel.getCaId()).getMessageList().isEmpty()){
					return checkValueUtil.getMessageList().toString();
				}
				
			}
			for(AppUserModel appUserModel:appUserModels){
				List<Object[]> paramList = new ArrayList<Object[]>();
				if(appUserModel.getCaId()!=null){
					paramList.add(new Object[] { DaoQueryOperator.EQ, "caId",
							appUserModel.getCaId() });
				}
				Object[][] paramObj = null;
				if (paramList.size() > 0) {
					paramObj = new Object[paramList.size()][3];
					for (int i = 0; i < paramList.size(); i++) {
						paramObj[i] = paramList.get(i);
					}
				}
				long total = baseDao.count(null, false, AppUser.class, paramObj);
				if (total > 0) {
					continue ;
				}
				AppUser appUser = new AppUser();
				appUser.setCaId(appUserModel.getCaId());
				baseDao.save(appUser);
			}
		}
		systemLogService.saveSystemLog("数据统计管理/专区用户管理", "导入用户",
				"用户\"" + user.getUserName() + "\"导入了用户", user);
		return null;
	}

	
}
