package com.ipanel.web.series.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ipanel.web.common.dao.BaseDao;
import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.define.EntryTypeDefined;
import com.ipanel.web.entity.EntryTypeInfo;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.series.pageModel.EntryTypeModel;
import com.ipanel.web.series.service.IEntryTypeService;
import com.ipanel.web.sysUser.service.impl.SystemLogService;
import com.ipanel.web.utils.Constants;
import com.ipanel.web.utils.FileOperation;
import com.ipanel.web.utils.TimeUtil;
import com.ipanel.webapp.framework.core.dao.DaoQueryOperator;
import com.ipanel.webapp.framework.util.Log;

@Service("entryTypeServcie")
public class EntryTypeServiceImpl implements IEntryTypeService {

	private final String TAG= "EntryTypeServiceImpl";
	
	@Resource
	private BaseDao baseDao;
	
	@Resource(name="systemLogService")
	private SystemLogService systemLogService;
	
	@SuppressWarnings("unchecked")
	@Override
	public PageDataModel queryEntryTypeList(SysUser user,EntryTypeModel model) {
		List<EntryTypeInfo> entryTypeInfos = new ArrayList<EntryTypeInfo>();
		long total=0;
		List<Object[]> paramList = new ArrayList<Object[]>();
		if(model!=null&&model.getPage()!=null&&model.getRows()!=null){
			int page = model.getPage();
			int rows = model.getRows();
			String  typeName = model.getTypeName();
			if(StringUtils.isNotEmpty(typeName)){
				paramList.add(new Object[]{
					DaoQueryOperator.LIKE,"typeName",typeName
				});
				
			}
			Object[][] paramObj = null;
			if(paramList.size()>0){
				paramObj  = new Object[paramList.size()][3];
				for(int i=0;i<paramList.size();i++){
					paramObj[i]  = 	paramList.get(i);
				}			
			}
			entryTypeInfos = (List<EntryTypeInfo>)baseDao.query(null,false
					, EntryTypeInfo.class, paramObj, null, new String[]{"id"}, (page-1)*rows, rows);
			
			total = baseDao.count(null, false, EntryTypeInfo.class, paramObj);
		}else{
			entryTypeInfos = baseDao.query(EntryTypeInfo.class, null);
			total = baseDao.count(null, false, EntryTypeInfo.class, null);
		}
		
		List<EntryTypeModel> entryTypeModels = new ArrayList<EntryTypeModel>();
		for(EntryTypeInfo entryTypeInfo:entryTypeInfos){
			EntryTypeModel entryTypeModel = new EntryTypeModel();
			entryTypeModel.setId(entryTypeInfo.getId());
			entryTypeModel.setText(entryTypeInfo.getTypeName());
			entryTypeModel.setTypeName(entryTypeInfo.getTypeName());
			entryTypeModel.setRemark(entryTypeInfo.getRemark());
			entryTypeModel.setImageName(entryTypeModel.getImageName());
			entryTypeModel.setUniqueImageName(entryTypeModel.getUniqueImageName());
			entryTypeModel.setImageUrl(FileOperation.getImagePath(entryTypeModel.getUniqueImageName()));
			entryTypeModel.setAddTime(entryTypeInfo.getAddTime());
			entryTypeModels.add(entryTypeModel);
		}		
		return new PageDataModel((int)total,entryTypeModels);
	}
	
	@Override
	public String addEntryType(SysUser user,EntryTypeModel model, String fileType, CommonsMultipartFile image) {
		List<EntryTypeInfo> entryInfos = baseDao.query(EntryTypeInfo.class, new Object[][]{{
			DaoQueryOperator.EQ,"typeName",model.getTypeName()
		}});
		if(entryInfos!=null&&entryInfos.size()>0){
			return EntryTypeDefined.ENTRYTYPE_EXIST;
		}
		EntryTypeInfo entryTypeInfo = new EntryTypeInfo();
		entryTypeInfo.setTypeName(model.getTypeName());
		entryTypeInfo.setRemark(model.getRemark());
		entryTypeInfo.setAddTime(TimeUtil.getCurrentTime());
		entryTypeInfo.setImageName(image.getOriginalFilename());
		byte[] imageContent = image.getBytes();
		String filepicPath = "";
		String remoteFileName = null;
		if(StringUtils.isNotEmpty(image.getOriginalFilename())&&StringUtils.isNotEmpty(fileType)){
			remoteFileName= System.currentTimeMillis()+"."+fileType;
			filepicPath=Constants.BOOK_IMAGE_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+remoteFileName;
			//将图片上到资源服务器上nginx或apache,与内容提供商部署在同一台服务器
			FileOperation.writeNodeImages(remoteFileName, imageContent);	
			entryTypeInfo.setUniqueImageName(remoteFileName);
			entryTypeInfo.setImagePath(filepicPath);
		}
		baseDao.save(entryTypeInfo);
		try {
			systemLogService.saveSystemLog("图书套书系列管理", "添加套书系列", "用户\""+user.getUserName()+"\"添加了\""+model.getTypeName()+ "\"套书系列", user);
		} catch (Exception e) {
			Log.e(TAG, "系统添加日志出错"+e);
		}
		return null;
	}
	
	@Override
	public String editEntryType(SysUser user,EntryTypeModel model, String fileType, CommonsMultipartFile image){
		List<EntryTypeInfo> entryTypeInfos = baseDao.query(EntryTypeInfo.class, new Object[][]{
			{DaoQueryOperator.EQ,"typeName",model.getTypeName()},
			{DaoQueryOperator.NIN,"id",model.getId()}
		});
		if(entryTypeInfos!=null&& entryTypeInfos.size()>0){
			return EntryTypeDefined.ENTRYTYPE_EXIST;					
		}
		EntryTypeInfo entryTypeInfo = baseDao.get(EntryTypeInfo.class, model.getId());
		entryTypeInfo.setTypeName(model.getTypeName());
		entryTypeInfo.setRemark(model.getRemark());
		entryTypeInfo.setImageName(image.getOriginalFilename());
		byte[] imageContent = image.getBytes();
		String filepicPath = "";
		String remoteFileName = null;
		if(StringUtils.isNotEmpty(image.getOriginalFilename())&&StringUtils.isNotEmpty(fileType)){
			//把之前的图片删除
			if(StringUtils.isNotEmpty(entryTypeInfo.getUniqueImageName())){
				FileOperation.deleteNodeImages(entryTypeInfo.getUniqueImageName());
			}
			remoteFileName= System.currentTimeMillis()+"."+fileType;
			filepicPath=Constants.BOOK_IMAGE_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+remoteFileName;
			//将图片上到资源服务器上nginx或apache,与内容提供商部署在同一台服务器
			FileOperation.writeNodeImages(remoteFileName, imageContent);	
			entryTypeInfo.setUniqueImageName(remoteFileName);
			entryTypeInfo.setImagePath(filepicPath);
		}
		baseDao.update(entryTypeInfo);
		try {
			systemLogService.saveSystemLog(
					"专区管理/图书套书系列管理",
					"编辑套书系列",
					"用户\"" + user.getUserName() + "\"编辑了\""
							+ model.getTypeName() + "\"图书套书系列", user);
		} catch (Exception e) {
			Log.e(TAG, "系统添加日志出错"+e);
		}
		return null;		
	}
	@Override
	public void deleteEntryType(SysUser user, String ids) {
		StringBuffer nameSb = new StringBuffer();
		String[] entryTypeIdArr = ids.split(",");
		for(String idStr:entryTypeIdArr){
			Integer intId = Integer.parseInt(idStr);
			EntryTypeInfo entryType = baseDao.get(EntryTypeInfo.class, intId);
			if(entryType!=null){
				nameSb.append(entryType.getTypeName()+",");
				baseDao.delete(entryType);
			}				
		}
		try {
			systemLogService.saveSystemLog(
					"专区管理/图书套书系列管理",
					"删除图书套书系列",
					"用户\""
							+ user.getUserName()
							+ "\"删除了\""
							+ nameSb.toString().substring(0,
									nameSb.length() - 1) + "\"套书系列", user);
		} catch (Exception e) {
			Log.e(TAG, "系统添加日志出错"+e);
		}
	}
}
