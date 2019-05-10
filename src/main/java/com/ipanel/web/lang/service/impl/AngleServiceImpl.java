package com.ipanel.web.lang.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ipanel.web.common.dao.BaseDao;
import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.entity.AngleInfo;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.lang.pageModel.AngleModel;
import com.ipanel.web.lang.service.IAngleService;
import com.ipanel.web.sysUser.service.impl.SystemLogService;
import com.ipanel.web.utils.Constants;
import com.ipanel.web.utils.FileOperation;
import com.ipanel.webapp.framework.core.dao.DaoQueryOperator;
import com.ipanel.webapp.framework.util.Log;

/**
 * @author fangg
 * 2017年5月31日 下午5:33:33
 */
@Service("angleService")
public class AngleServiceImpl implements IAngleService {
	
	private final String TAG = "AngleServiceImpl";
	
	@Resource
	private BaseDao baseDao;
	
	@Resource(name="systemLogService")
	private SystemLogService systemLogService;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public PageDataModel queryAngleList(AngleModel model) {
		List<Object[]> paramList = new ArrayList<Object[]>();
		String name = model.getName();
		int page = model.getPage();
		int rows = model.getRows();
		if(StringUtils.isNotEmpty(name)){
			paramList.add(new Object[]{
					DaoQueryOperator.LIKE,"name",name
			});
		}
		Object[][] paramObj = null;
		if (paramList.size() > 0) {
			paramObj = new Object[paramList.size()][3];
			for (int i = 0; i < paramList.size(); i++) {
				paramObj[i] = paramList.get(i);
			}
		}	
		List<AngleInfo> angleInfos = new ArrayList<AngleInfo>();
		if(page==0||rows==0){
			angleInfos = (List<AngleInfo>)baseDao.query(null,false,AngleInfo.class,paramObj,null,new String[]{"id"},null,null);
		}else {
			angleInfos = (List<AngleInfo>)baseDao.query(null,false,AngleInfo.class,paramObj,null,new String[]{"id"},(page-1)*rows,rows);
		}
		long total = baseDao.count(null, false, AngleInfo.class, paramObj);
		List<AngleModel> angleModels = new ArrayList<AngleModel>();
		for(AngleInfo  angleInfo:angleInfos){
			AngleModel angleModel = new AngleModel();
			angleModel.setId(angleInfo.getId());
			angleModel.setName(angleInfo.getName());
			angleModel.setImageName(angleInfo.getImageName());
			angleModel.setUniqueImageName(angleInfo.getUniqueImageName());
			angleModel.setImageUrl(FileOperation.getImagePath(angleInfo.getUniqueImageName()));
			angleModels.add(angleModel);
		}		
		return new PageDataModel((int)total,angleModels);
	}

	
	@Override
	public String deleteAngle(SysUser user,String ids) {
		StringBuffer angleNameBuffer = new StringBuffer();
		String[] angleIdArray = ids.split(",");
		for(String id:angleIdArray){
			Integer idInteger = Integer.parseInt(id);
			AngleInfo angleInfo = baseDao.get(AngleInfo.class, idInteger);
			if(angleInfo!=null){
				angleNameBuffer.append(angleInfo.getName()+",");
				baseDao.delete(angleInfo);
				if(angleInfo.getUniqueImageName()!=null){
					FileOperation.deleteNodeImages(angleInfo.getUniqueImageName());
				}
			}
		}
		try {
			systemLogService.saveSystemLog(
					"专区管理/语种管理",
					"删除语种",
					"用户\""
							+ user.getUserName()
							+ "\"删除了\""
							+ angleNameBuffer.toString().substring(0,
									angleNameBuffer.length() - 1) + "\"语种", user);
		} catch (Exception e) {
			Log.e(TAG, "系统添加日志出错"+e);
		}
		return null;
	}

	@Override
	public String addAngle(SysUser user, AngleModel model,String fileType, CommonsMultipartFile image) {
		List<AngleInfo> angleInfos = baseDao.query(AngleInfo.class,new Object[][]{{
			DaoQueryOperator.EQ,"name",model.getName()
		}});
		if(angleInfos!=null&&angleInfos.size()>0){
			return "语种名称已存在";
		}		
		AngleInfo angleInfo = new AngleInfo();
		angleInfo.setId(model.getId());
		angleInfo.setName(model.getName());
		angleInfo.setImageName(image.getOriginalFilename());
		byte[] imageContent = image.getBytes();
		String filepicPath = "";
		String remoteFileName = null;
		if(StringUtils.isNotEmpty(image.getOriginalFilename())&&StringUtils.isNotEmpty(fileType)){
			remoteFileName= System.currentTimeMillis()+"."+fileType;
			filepicPath=Constants.BOOK_IMAGE_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+remoteFileName;
			//将图片上到资源服务器上nginx或apache,与内容提供商部署在同一台服务器
			FileOperation.writeNodeImages(remoteFileName, imageContent);	
			angleInfo.setUniqueImageName(remoteFileName);
			angleInfo.setImagePath(filepicPath);
		}
		baseDao.save(angleInfo);
		try {
			systemLogService.saveSystemLog("语种管理", "新增语种", "用户\""+user.getUserName()+"\"新增了\""+model.getName()+ "\"语种", user);
		} catch (Exception e) {
			Log.e(TAG, "系统添加日志出错"+e);
		}
		return null;
	}

	@Override
	public String editAngle(SysUser user, AngleModel model,String fileType, CommonsMultipartFile image) {
		List<AngleInfo> angleInfos = baseDao.query(AngleInfo.class,new Object[][]{{
			DaoQueryOperator.EQ,"name",model.getName()},
			{DaoQueryOperator.NIN,"id",model.getId()
		}});
		if(angleInfos!=null&&angleInfos.size()>0){
			return "语种名称已存在";
		}		
		AngleInfo angleInfo = baseDao.get(AngleInfo.class,model.getId());
		angleInfo.setId(model.getId());
		angleInfo.setName(model.getName());
		angleInfo.setImageName(image.getOriginalFilename());
		byte[] imageContent = image.getBytes();
		String filepicPath = "";
		String remoteFileName = null;
		if(StringUtils.isNotEmpty(image.getOriginalFilename())&&StringUtils.isNotEmpty(fileType)){
			//把之前的图片删除
			if(StringUtils.isNotEmpty(angleInfo.getUniqueImageName())){
				FileOperation.deleteNodeImages(angleInfo.getUniqueImageName());
			}
			remoteFileName= System.currentTimeMillis()+"."+fileType;
			filepicPath=Constants.BOOK_IMAGE_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+remoteFileName;
			//将图片上到资源服务器上nginx或apache,与内容提供商部署在同一台服务器
			FileOperation.writeNodeImages(remoteFileName, imageContent);	
			angleInfo.setUniqueImageName(remoteFileName);
			angleInfo.setImagePath(filepicPath);
		}
		baseDao.update(angleInfo);
		try {
			systemLogService.saveSystemLog("语种管理", "编辑语种", "用户\""+user.getUserName()+"\"编辑了\""+model.getName()+ "\"语种", user);
		} catch (Exception e) {
			Log.e(TAG, "系统添加日志出错"+e);
		}
		return null;
	}

}
