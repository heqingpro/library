package com.ipanel.web.book.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.epub.EpubReader;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ipanel.web.book.elasticsearch.ElasticsearchHelper;
import com.ipanel.web.book.elasticsearch.EsEntry;
import com.ipanel.web.book.pageModel.EditorImageBean;
import com.ipanel.web.book.pageModel.EntryAlbumModel;
import com.ipanel.web.book.pageModel.EntryAudioModel;
import com.ipanel.web.book.pageModel.EntryFileModel;
import com.ipanel.web.book.pageModel.EntryImageModel;
import com.ipanel.web.book.pageModel.EntryModel;
import com.ipanel.web.book.pageModel.EntrySearchModel;
import com.ipanel.web.book.service.IEntryService;
import com.ipanel.web.common.dao.BaseDao;
import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.define.EntryDefined;
import com.ipanel.web.entity.AngleInfo;
import com.ipanel.web.entity.AppInfo;
import com.ipanel.web.entity.EntryAttachInfo;
import com.ipanel.web.entity.EntryAudioInfo;
import com.ipanel.web.entity.EntryFileInfo;
import com.ipanel.web.entity.EntryImageInfo;
import com.ipanel.web.entity.EntryInfo;
import com.ipanel.web.entity.EntryToNode;
import com.ipanel.web.entity.EntryTypeInfo;
import com.ipanel.web.entity.NodeInfo;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.sysUser.service.impl.SystemLogService;
import com.ipanel.web.utils.BeanUtil;
import com.ipanel.web.utils.Constants;
import com.ipanel.web.utils.FileOperation;
import com.ipanel.web.utils.FileUtil;
import com.ipanel.web.utils.TimeUtil;
import com.ipanel.webapp.framework.core.dao.DaoQueryOperator;
import com.ipanel.webapp.framework.util.Log;

@Service("entryServcie")
public class EntryServiceImpl implements IEntryService {

	private final String TAG = "EntryServiceImpl"; 
	
	// 这里写死
	private final String INDEX = "library_book_index";
	private final String TYPE = "library_book_type";
	
	@Resource
	private BaseDao baseDao;
	
	@Resource(name="systemLogService")
	private SystemLogService systemLogService;
	
	@Autowired
	ElasticsearchHelper elasticsearchHelper;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EntryServiceImpl.class);
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean recreateBookIndex() {
		boolean flag = true;
		if(elasticsearchHelper.isIndexExist(this.INDEX)) { //索引存在的话，先删除
			flag = elasticsearchHelper.deleteIndex(this.INDEX);
		} 
		if(flag == true){
			flag = elasticsearchHelper.createIndex(this.INDEX, this.TYPE);
		}
		if(flag == true) { //索引创建成功后，同步数据中的数据到ES
			List<EsEntry> entrys = (List<EsEntry>) baseDao.query("from EsEntry");
			for(EsEntry entry : entrys) {
				Map<String, Object> map = new HashMap<String, Object>(); 
				BeanUtil.bean2Map(entry, map);
				JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
				flag = elasticsearchHelper.addData(jsonObject, this.INDEX, this.TYPE, String.valueOf(entry.getId()));
				if(flag == false) {
					elasticsearchHelper.deleteIndex(this.INDEX);
					break;
				}
			}
		}
		if(flag == true) {
			LOGGER.info("createBookIndex->Index create and sync data to ES success!");
		} else {
			LOGGER.error("createBookIndex->Index create and sync data to ES failed!");
		}
		return flag;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean changeEntryRankNumber(SysUser attribute, EntryModel model) {
		try {
			EntryInfo entryInfo = baseDao.get(EntryInfo.class, model.getId());
			if(model.getOperation() == -1) {
				List<EntryInfo> entrys = (List<EntryInfo>) baseDao.query("from EntryInfo e where e.rankNumber = " + (entryInfo.getRankNumber()-1));
				if(entrys.size() == 1) {
					for(EntryInfo entry : entrys) {
						entry.setRankNumber(entry.getRankNumber() + 1);
						baseDao.update(entry);
					}
					if(entryInfo.getRankNumber() - 1 >= 1) {
						entryInfo.setRankNumber(entryInfo.getRankNumber() - 1);
					} else {
						entryInfo.setRankNumber(1);
					}
				}
			} else if(model.getOperation() == 1) {
				List<EntryInfo> entrys = (List<EntryInfo>) baseDao.query("from EntryInfo e where e.rankNumber = " + (entryInfo.getRankNumber() + 1));
				if(entrys.size() == 1) {
					for(EntryInfo entry : entrys) {
						if(entry.getRankNumber() - 1 >= 1) {
							entry.setRankNumber(entry.getRankNumber() - 1);
						} else {
							entry.setRankNumber(1);
						}
						baseDao.update(entry);
					}
					entryInfo.setRankNumber(entryInfo.getRankNumber() + 1);
				}
			}
			return true;
			
		} catch (Exception e) {
			Log.e(TAG, "修改排序出错" + e);
			e.printStackTrace();
			return false;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public PageDataModel queryEntryList(SysUser sysUser, EntryModel model) {
		if(model.getApp_Id()==null){
			return null;
		}
		List<Object[]> paramList = new ArrayList<Object[]>();
		int page = model.getPage();
		int rows = model.getRows();
		String title = model.getTitle();
		String author = model.getAuthor();
		String oriAuthor = model.getOriginalAuthor();
		Integer yearsId = model.getYearsId();
		
		Log.i(TAG, "nodeId=="+model.getNodeId()+",appId ="+model.getApp_Id());
		
		if(model.getApp_Id()!=null){
			paramList.add(new Object[]{
				DaoQueryOperator.EQ,"appInfo.id",model.getApp_Id()	
			});
		}
		if(model.getLangId()!=null){
			paramList.add(new Object[]{
					DaoQueryOperator.EQ,"angleInfo.id",model.getLangId()
				});
		}
		if(yearsId!=null){
			paramList.add(new Object[]{
				DaoQueryOperator.EQ,"yearsId",yearsId	
				}
			);
		}
		if(model.getEntryTypeId()!=null){
			paramList.add(new Object[]{
					DaoQueryOperator.EQ,"entryTypeInfo.id",model.getEntryTypeId()	
				});
		}
		if(StringUtils.isNotEmpty(title)){
			paramList.add(new Object[]{
				DaoQueryOperator.LIKE,"title",title	
				}
			);
		}
		if(StringUtils.isNotEmpty(author)){
			paramList.add(new Object[]{
				DaoQueryOperator.LIKE,"author",author	
				}
			);
		}
		if(StringUtils.isNotEmpty(oriAuthor)){
			paramList.add(new Object[]{
				DaoQueryOperator.LIKE,"originalAuthor",oriAuthor	
				}
			);
		}
		
		Object[][] paramObj = null;
		if (paramList.size() > 0) {
			paramObj = new Object[paramList.size()][3];
			for (int i = 0; i < paramList.size(); i++) {
				paramObj[i] = paramList.get(i);
			}
		}
		List<EntryInfo> entryInfos = (List<EntryInfo>)baseDao.query(null, false, EntryInfo.class, paramObj, null, new String[]{"id"}, (page-1)*rows, rows);
		long total = baseDao.count(null, false, EntryInfo.class, paramObj);		
		List<EntryModel> entryModels = new ArrayList<EntryModel>();
		for (int i = 0; i < entryInfos.size(); i++) {
				EntryInfo entryInfo = entryInfos.get(i);
				EntryModel entryModel = new EntryModel();
				entryModel = parseModel(entryInfo,entryModel);			
				entryModels.add(entryModel);
		}
		
		entryModels = sortList(entryModels);
		
		return new PageDataModel((int)total, entryModels);
	}
	
	/**
	 * 给图书列表按关键字rankNumber升序排序
	 *
	 * @author lvchao
	 * @createtime 2018年4月19日 下午2:05:03
	 *
	 * @param list
	 * @return
	 */
	public List<EntryModel> sortList(List<EntryModel> list) {
		Collections.sort(list, new Comparator<EntryModel>() {
			
			@Override
			public int compare(EntryModel e1, EntryModel e2) {
				return e1.getRankNumber() - e2.getRankNumber();
			}
		});
		return list;
	}

	
	@Override
	public PageDataModel queryEntryByEntryType(SysUser sysUser,EntrySearchModel model){
		int page = model.getPage();
		int rows = model.getRows();
		String entryTypeName = model.getEntryTypeName();
		List<EntryTypeInfo> entryTypeInfos = baseDao.query(EntryTypeInfo.class, new Object[][]{{
			DaoQueryOperator.EQ,"typeName",entryTypeName
		}});
		
		EntryTypeInfo entryTypeInfo = entryTypeInfos.get(0);
		List<EntryInfo>  entryInfos= entryTypeInfo.getEntryList();
		int total = entryInfos.size();
		if((page-1)*rows<total){
			entryInfos = entryInfos.subList((page-1)*rows, total);	
		}else {
			entryInfos.clear();
		}
		
		List<EntryModel> entryModels = new ArrayList<EntryModel>();				
		for(EntryInfo entryInfo:entryInfos){
			EntryModel entryModel = new EntryModel();
			entryModel = parseModel(entryInfo,entryModel);
			entryModels.add(entryModel);
		}		
		return new PageDataModel(total, entryModels);
	}

	
	private EntryModel parseModel(EntryInfo entryInfo,EntryModel entryModel) {
		entryModel.setId(entryInfo.getId());
		entryModel.setTitle(entryInfo.getTitle());
		entryModel.setShortName(entryInfo.getShortName());
		entryModel.setAuthor(entryInfo.getAuthor());
		entryModel.setDesc(entryInfo.getDescription());
		entryModel.setEditor(entryInfo.getEditor());
		entryModel.setOriginalAuthor(entryInfo.getOriginalAuthor());
		entryModel.setIsPrize(entryInfo.getIsPrize());
		entryModel.setGlobal_guid(entryInfo.getGlobal_guid());
		entryModel.setHeight(entryInfo.getHeight());
		entryModel.setWidth(entryInfo.getWidth()); 
		entryModel.setPubOrg(entryInfo.getPubOrg());//出版机构
		entryModel.setPageCount(entryInfo.getPageCount());//页数
		entryModel.setUid(entryInfo.getUid());
		entryModel.setFormatType(entryInfo.getFormatType()); //音频，文本
		entryModel.setYearsId(entryInfo.getYearsId());//年代
		entryModel.setEditionType(entryInfo.getEditionType()); //编辑类型
		entryModel.setAddTime(entryInfo.getAddTime());
		entryModel.setModifyTime(entryInfo.getModifyTime());
		entryModel.setRankNumber(entryInfo.getRankNumber());
		
		//封面海报信息
		if(entryInfo.getCoverImageId()!=null){
			EntryImageInfo entryImageInfo = baseDao.get(EntryImageInfo.class,entryInfo.getCoverImageId());
			if(entryImageInfo!=null){
				String coverImageUrl= FileOperation.getImagePath(entryImageInfo.getUniqueName());
				entryModel.setCoverImageUrl(coverImageUrl);	
			}
		}
		//小海报信息
		if(entryInfo.getCoverThumbNailId()!=null){
			EntryImageInfo entryImageInfo = baseDao.get(EntryImageInfo.class, entryInfo.getCoverThumbNailId());
			if(entryImageInfo!=null){
				String coverThumbNail = FileOperation.getImagePath(entryImageInfo.getUniqueName());
				entryModel.setCoverThumbNailUrl(coverThumbNail);
			}
		}
		//语种信息
		if(entryInfo.getAngleInfo()!=null){
			entryModel.setLangId(entryInfo.getAngleInfo().getId());	
			entryModel.setLangName(entryInfo.getAngleInfo().getName());
		}
		//系列（套书信息）
		if(entryInfo.getEntryTypeInfo()!=null){
			entryModel.setEntryTypeId(entryInfo.getEntryTypeInfo().getId());
			entryModel.setEntryTypeName(entryInfo.getEntryTypeInfo().getTypeName());
		}
		//内容提供商信息
		if(entryInfo.getAppInfo()!=null){
			entryModel.setApp_Id(entryInfo.getAppInfo().getId());
			entryModel.setAppName(entryInfo.getAppInfo().getAppName());
		}
		//分类信息
		List<EntryToNode> entryToNodes  = entryInfo.getEntryToNodeList();
		StringBuffer nameBuffers = new StringBuffer();
		StringBuffer idBuffers = new StringBuffer();
		for(EntryToNode entry:entryToNodes){
			NodeInfo nodeInfo = entry.getNodeInfo();		
			idBuffers.append(","+nodeInfo.getId());
			nameBuffers.append(" "+nodeInfo.getNodeName());				
		}
		String typeIds = idBuffers.toString();
		if(typeIds.endsWith(",")){
			typeIds = typeIds.substring(0,typeIds.indexOf(typeIds.lastIndexOf(",")));
		}
		String typeNames = nameBuffers.toString();
		if(typeNames.endsWith(",")){
			typeNames = typeNames.substring(0,typeNames.indexOf(typeNames.lastIndexOf(",")));
		}
		entryModel.setNodeIds(typeIds);
		entryModel.setNodeNames(typeNames);
		//附加信息
		if(entryInfo.getEntryAttachInfo()!=null){
			entryModel.setAuditStatus(entryInfo.getEntryAttachInfo().getAuditStatus());
			entryModel.setBrowseCount(entryInfo.getEntryAttachInfo().getBrowseCount());
			entryModel.setDownloadCount(entryInfo.getEntryAttachInfo().getDownloadCount());
		}
		return entryModel;
	}
	
	
	private EntryModel parseDetailContent(EntryInfo entryInfo, EntryModel entryModel) {
		//图书音频信息
		EntryAudioInfo entryAudioInfo = entryInfo.getEntryAudio();
		List<EntryAudioModel> entryAudioModels = new ArrayList<EntryAudioModel>();
		if(entryAudioInfo!=null){
			EntryAudioModel entryAudioModel = new EntryAudioModel();
			EntryAudioModel.parseEntityToModel(entryAudioInfo, entryAudioModel);
			entryAudioModels.add(entryAudioModel);
		}
		entryModel.setEntryAudioModels(entryAudioModels);
		//图书内容文件信息
		EntryFileInfo entryFileInfo = entryInfo.getEntryFile();
		if(entryFileInfo!=null) {
			EntryFileModel entryFileModel = new EntryFileModel();
			EntryFileModel.parseEntityToModel(entryFileInfo, entryFileModel);
			entryModel.setContentModel(entryFileModel);
		}
		//图书画册信息
		List<EntryImageInfo> entryAlbumInfos = entryInfo.getEntryImageList();
		List<EntryImageModel> entryImageModels = new ArrayList<EntryImageModel>();
		for(EntryImageInfo entryImageInfo:entryAlbumInfos){
			if(entryImageInfo.getPosition()==1){	//去掉封面图片
				EntryImageModel entryImageModel = new EntryImageModel();
				EntryImageModel.parseEntityToModel(entryImageInfo, entryImageModel);
				entryImageModels.add(entryImageModel);	
			}
		}
		entryModel.setEntryImageModels(entryImageModels);
		//获取content
		if(StringUtils.isNotEmpty(entryInfo.getContent())){
			entryModel.setContent(entryInfo.getContent().replaceAll("\n", "<br>"));
		}
		return entryModel;
	}
	
	
	@Override	
	public String addEntry(SysUser user,EntryModel model,CommonsMultipartFile coverImage,String coverImageFileType,CommonsMultipartFile coverThumbNail,String coverThumbNailType,CommonsMultipartFile entryImportFile,String fileType,CommonsMultipartFile audioFile,String audioType){
		List<EntryInfo> entryInfos = baseDao.query(EntryInfo.class, new Object[][]{{DaoQueryOperator.EQ,"title",model.getTitle()}});
		if(model.getApp_Id()==null){
			return EntryDefined.ERROR;
		}
		if(entryInfos!=null && entryInfos.size()>0){
			for(EntryInfo entryInfo : entryInfos){
				if(model.getApp_Id()==entryInfo.getAppInfo().getId()){
					return EntryDefined.ENTRY_EXIST;	
				}					
			}
		}
		//转基本信息，并保存
		EntryInfo entryInfo = new EntryInfo();
		entryInfo = parseModelBasicInfoToEntity(model, entryInfo);//转换基本信息
		entryInfo.setOperateUserId(user!=null?user.getId():null);
		entryInfo.setAddTime(TimeUtil.getCurrentTime());
		entryInfo.setRankNumber((int) baseDao.count(null, false, EntryInfo.class, null) + 1);
		Serializable id = baseDao.save(entryInfo);
		
		//保存一个附加信息，用于后续的排序，统计用
		EntryAttachInfo  entryAttachInfo = new EntryAttachInfo();
		entryAttachInfo.setSortValue((Integer)id);
		entryAttachInfo.setEntryInfo(entryInfo);
		baseDao.save(entryAttachInfo);
		entryInfo.setEntryAttachInfo(entryAttachInfo);
		//保存套书系列信息
		if(model.getEntryTypeId()!=null){
			EntryTypeInfo  entryTypeInfo = baseDao.get(EntryTypeInfo.class, model.getEntryTypeId());
			entryInfo.setEntryTypeInfo(entryTypeInfo);
		}
		//保存语种关联信息
		if (model.getLangId()!=null) {
			AngleInfo angleInfo = baseDao.get(AngleInfo.class,model.getLangId());
			entryInfo.setAngleInfo(angleInfo);
		}
		//保存运营商关联信息
		if(model.getApp_Id()!=null){
			AppInfo appInfo = baseDao.get(AppInfo.class, model.getApp_Id());
			entryInfo.setAppInfo(appInfo);
		}
		//保存分类信息
		if(StringUtils.isNotEmpty(model.getNodeIds())){
			String[] nodeIdArr = model.getNodeIds().trim().split(",");
			for(String nodeIdStr:nodeIdArr){
				int nodeId = Integer.parseInt(nodeIdStr);
				NodeInfo nodeInfo = baseDao.get(NodeInfo.class, nodeId);
				EntryToNode entryToNode = new EntryToNode();
				entryToNode.setEntryInfo(entryInfo);
				entryToNode.setNodeInfo(nodeInfo);
				baseDao.save(entryToNode);
			}
		}
		
		//保存封面海报,并上传到服务器
		if(coverImage!=null&&StringUtils.isNotEmpty(coverImageFileType)){
			EntryImageInfo entryImageInfo = uploadImage(user,coverImage,coverImageFileType,0);
			entryImageInfo.setEntryInfo(entryInfo);	
			entryInfo.setCoverImageId(entryImageInfo.getId());
			entryInfo.setCoverImage(entryImageInfo.getImagePath());
		}
		//保存封面小海报信息，并上传至服务器
		if(coverThumbNail!=null&&StringUtils.isNotEmpty(coverThumbNailType)){
			EntryImageInfo entryImageInfo = uploadImage(user, coverThumbNail,coverThumbNailType, 0);
			entryImageInfo.setEntryInfo(entryInfo);
			entryInfo.setCoverThumbNailId(entryImageInfo.getId());
			entryInfo.setCoverThumbNail(entryImageInfo.getImagePath());
		}
		//保存txt文件，这里目前支持两种文件，与下面audio不同
		if(entryImportFile!=null&&StringUtils.isNotEmpty(fileType)) {
			if("mp3".equals(fileType)||"mp4".equals(fileType)||"wmv".equals(fileType)){
				EntryAudioInfo entryAudioInfo = uploadAudio(user, entryImportFile, fileType);
				if(entryAudioInfo!=null) {
					entryAudioInfo.setEntryInfo(entryInfo);	
					entryInfo.setAudioPath(entryAudioInfo.getAudioPath());
					entryInfo.setEntryAudio(entryAudioInfo);
				}	
			} else {
				EntryFileInfo entryFileInfo = null;
				if("txt".equals(fileType)){
					entryFileInfo = uploadTxt(user,entryImportFile, fileType, entryInfo);
				}else if("pdf".equals(fileType)) { //无法提取PDF内容，直接上上传原文件！
					entryFileInfo = uploadFile(user, entryImportFile, audioType);
				}else if("epub".equals(fileType)) {
					entryFileInfo = uploadEpub(user, entryImportFile, audioType);
				}
				if(entryFileInfo!=null) {
					entryFileInfo.setEntryInfo(entryInfo);
					entryInfo.setEntryFile(entryFileInfo);
					entryInfo.setContentPath(entryFileInfo.getFilePath());
				}
			} 
		}
		//保存音频文件audioType,音频电子书同步的资源
		if(audioFile!=null&&StringUtils.isNotEmpty(audioType)) {
			EntryAudioInfo entryAudioInfo = uploadAudio(user, audioFile, audioType);
			if(entryAudioInfo!=null) {
				entryAudioInfo.setEntryInfo(entryInfo);	
				entryInfo.setAudioPath(entryAudioInfo.getAudioPath());
				entryInfo.setEntryAudio(entryAudioInfo);
			}
		}
		
		//转换成EsEntry，保存到数据库、同步添加到ES
		try {
			EsEntry esEntry = new EsEntry(entryInfo);
			baseDao.save(esEntry);
			Map<String, Object> map = new HashMap<String, Object>(); 
			BeanUtil.bean2Map(esEntry, map);
			JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
			
			boolean flag = true;
			if(!elasticsearchHelper.isIndexExist(this.INDEX)) {
				flag = elasticsearchHelper.createIndex(this.INDEX, this.TYPE);
			}
			if(flag == true) {
				elasticsearchHelper.addData(jsonObject, this.INDEX, this.TYPE, String.valueOf(esEntry.getId()));
			}
			
			systemLogService.saveSystemLog("图书管理", "添加图书", "用户\""+user.getUserName()+"\"添加了\""+model.getTitle()+ "\"图书", user);
		} catch (Exception e) {
			Log.e(TAG, "系统添加日志出错"+e);
		}
		return null;
	}
	
	
	private EntryInfo parseModelBasicInfoToEntity(EntryModel model, EntryInfo entryInfo) {
		entryInfo.setTitle(model.getTitle());
		entryInfo.setShortName(model.getShortName());
		entryInfo.setAuthor(model.getAuthor());
		entryInfo.setDescription(model.getDesc());
		entryInfo.setEditor(model.getEditor());
		entryInfo.setOriginalAuthor(model.getOriginalAuthor());
		entryInfo.setIsPrize(model.getIsPrize());
		entryInfo.setGlobal_guid(model.getGlobal_guid());
		entryInfo.setHeight(model.getHeight());
		entryInfo.setWidth(model.getWidth()); 
		entryInfo.setPubOrg(model.getPubOrg());//出版机构
		entryInfo.setPageCount(model.getPageCount());//页数
		entryInfo.setUid(model.getUid());
		entryInfo.setFormatType(model.getFormatType()); //音频，文本
		entryInfo.setYearsId(model.getYearsId());//年代
		entryInfo.setEditionType(model.getEditionType()); //编辑类型
		return entryInfo;
	}

	
	public EntryImageInfo uploadImage(SysUser user,CommonsMultipartFile imageFile,String suffix,Integer position){
		byte[] imageContent = imageFile.getBytes();
		String filepicPath = "";
		String remoteFileName = null;
		if(!StringUtils.isEmpty(imageFile.getOriginalFilename())){
			remoteFileName= System.currentTimeMillis()+"."+suffix;
			//将图片上到资源服务器上nginx或apache,与后台部署在同一台服务器
			if(position==0){
				FileOperation.writeNodeImages(remoteFileName, imageContent);
				filepicPath=Constants.BOOK_IMAGE_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+remoteFileName;
			}else if(position==1){
				filepicPath=Constants.BOOK_ALBUM_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+remoteFileName;
				FileOperation.writeAlbumImages(remoteFileName, imageContent);
			}
			//保存到数据库
			EntryImageInfo  entryImageInfo = new EntryImageInfo();
			entryImageInfo.setImageName(imageFile.getOriginalFilename());
			entryImageInfo.setUniqueName(remoteFileName);
			entryImageInfo.setImageType(suffix);
			entryImageInfo.setImagePath(filepicPath);
			entryImageInfo.setPosition(position);
			entryImageInfo.setOperateUserId(user.getId());
			entryImageInfo.setAddTime(TimeUtil.getCurrentTime());
			baseDao.save(entryImageInfo);
			return entryImageInfo;
		}
		return null;
	}
	
	
	@SuppressWarnings("unused")
	public EntryImageInfo replaceEntryImage(SysUser user,CommonsMultipartFile imageFile,String suffix,EntryImageInfo entryImageInfo,Integer position){
		byte[]  imageContent = imageFile.getBytes();
		String remoteFileName = "";
		String filepicPath = "";
		if(!StringUtils.isEmpty(imageFile.getOriginalFilename())){
			remoteFileName= System.currentTimeMillis()+"."+suffix;
			if(position==0){
				filepicPath=Constants.BOOK_IMAGE_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+remoteFileName;
				//将图片上到资源服务器上nginx或apache,与后台部署在同一台服务器
				FileOperation.writeNodeImages(remoteFileName, imageContent);
				//删除原来服务器上的图片
				FileOperation.deleteNodeImages(entryImageInfo.getUniqueName());		
			}else if(position==1){
				filepicPath=Constants.BOOK_ALBUM_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+remoteFileName;
				//将图片上到资源服务器上nginx或apache,与后台部署在同一台服务器
				FileOperation.writeAlbumImages(remoteFileName, imageContent);
				String filePath=Constants.BOOK_ALBUM_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+remoteFileName;
				FileOperation.deleteFile(filePath);
			}
			//更新数据库信息
			entryImageInfo.setImageName(imageFile.getOriginalFilename());
			entryImageInfo.setUniqueName(remoteFileName);
			entryImageInfo.setImageType(suffix);
			entryImageInfo.setOperateUserId(user.getId());
			entryImageInfo.setAddTime(TimeUtil.getCurrentTime());
			baseDao.update(entryImageInfo);
			return entryImageInfo;
		}
		return null;

	}
	
	
	private EntryFileInfo uploadEpub(SysUser user, CommonsMultipartFile entryImportFile, String fileType) {
		try {  
		       EpubReader epubReader = new EpubReader();  
		       String fileEncode = System.getProperty("file.encoding"); 
		       Book book = epubReader.readEpub(entryImportFile.getInputStream(),fileEncode); 
		       Metadata metadata = book.getMetadata();
		       System.out.println("标题"+metadata.getTitles());
		       System.out.println("作者："+metadata.getAuthors());
		       
		       //通过spine获取线性的阅读菜单，此菜单依照阅读顺序排序  
		       List<SpineReference> spineReferences = book.getSpine().getSpineReferences();  
		       String descFileName = TimeUtil.getTimeStamp()+".js";
		       String rootPath = Constants.FILE_SERVER_ROOT_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG;
		       String descFilePath = Constants.BOOK_TEXT_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+descFileName;
		       
		       File bookFile = new File(rootPath+descFilePath);
		       if(!bookFile.getParentFile().exists()){
		    	   bookFile.getParentFile().mkdirs();
		       }
		       bookFile.createNewFile();
		       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(bookFile),Constants.BOOK_ENCODING));
		       StringBuilder sbBuilder = new StringBuilder();
		       //获取对应resource  
		       for(SpineReference spineReference : spineReferences) {
		    	   String html = new String(spineReference.getResource().getData());
		    	   if(html.indexOf("xlink:href=\"cover.jpeg\"") == -1) {
			    	   Document document = Jsoup.parse(html);
			    	   Element body = document.body();
			    	   if(StringUtils.isNotEmpty(body.text())) {
			    		   Elements aelements = body.getElementsByTag("a");
				    	   aelements.remove();
				    	   String regex = "<img[^>]*>|href=[^>]*|id=[^>]*|<a[^>]*>|</a>|<body*>|</body>";
				       	   String content = "<h1 align='center'>" + document.title() + "</h1>" + body.clearAttributes().toString().replaceAll(regex, "");
				    	   sbBuilder.append(content);
			    	   }
		    	   }
		       }
		       try {
		    	   	  JSONObject jsonObject = new JSONObject();
		    	   	  jsonObject.put("content", sbBuilder.toString());
			   		  bw.write(new String(jsonObject.toString().getBytes(),Constants.BOOK_ENCODING));
			   		} catch (IOException e) {
			   			e.printStackTrace();
			   			throw new Exception("readByteArrayFromInputStream IOException occured.", e);
			   		} finally {
			   			try {
			   				bw.close();
			   			} catch (IOException e) {
			   				throw new Exception("readByteArrayFromInputStream close ByteArrayOutputStream occured.",e);
			   			}
			   	}
		       //保存到数据库
		        EntryFileInfo entryFileInfo = new EntryFileInfo();
				entryFileInfo.setFileName(entryImportFile.getOriginalFilename());
				entryFileInfo.setUniqueName(descFileName);
				entryFileInfo.setFileType(fileType);
				entryFileInfo.setFilePath(descFilePath);
				entryFileInfo.setOperateUserId(user.getId());
				entryFileInfo.setAddTime(TimeUtil.getCurrentTime());
				return entryFileInfo;
		} catch (Exception e) {  
		    e.printStackTrace();  
		} 
		return null;
	}
	
	
	public  List<EditorImageBean> getImagesInContent(String content){
		List<EditorImageBean> imgList=new ArrayList<EditorImageBean>();
		Set<EditorImageBean> imgSet=new HashSet<EditorImageBean>();
		String regex="<img.*?/>";
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=pattern.matcher(content);
		while (matcher.find()) {
			EditorImageBean editorImage=new EditorImageBean();
			String imgStr = matcher.group();
			
			String regexSrc="(?<=src=\").*?(?=\")";
			Pattern patternSrc=Pattern.compile(regexSrc);
			Matcher matcherSrc=patternSrc.matcher(imgStr);
			
			String regexAlt="(?<=alt=\").*?(?=\")";
			Pattern patternAlt=Pattern.compile(regexAlt);
			Matcher matcherAlt=patternAlt.matcher(imgStr);
			
			if (matcherSrc.find()) {//匹配图片的存储名称
				String src = matcherSrc.group();
				String[] split = src.split("/");
				String title=split[split.length-1];
				editorImage.setTitle(title);
				System.out.println(title);
				if (matcherAlt.find()) {//匹配出图片的原始名称
					String alt = matcherAlt.group();
					editorImage.setAlt(alt);
				    System.out.println(alt);
				}
			}
			imgSet.add(editorImage);
		}
		imgList.addAll(imgSet);
		return imgList;
	}
	
	
	public EntryFileInfo uploadFile(SysUser user,CommonsMultipartFile file,String suffix) {
		byte[] fileContent = file.getBytes();
		String filePath = "";
		String remoteFileName = null;
		if(!StringUtils.isEmpty(file.getOriginalFilename())){
			remoteFileName= TimeUtil.getTimeStamp()+"."+suffix;
			filePath=Constants.BOOK_TEXT_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+remoteFileName;
			//将音频上到资源服务器上nginx或apache,与后台部署在同一台服务器
			FileOperation.writeFile(filePath,fileContent);	
			//保存到数据库
			EntryFileInfo entryFileInfo = new EntryFileInfo();
			entryFileInfo.setFileName(file.getOriginalFilename());
			entryFileInfo.setUniqueName(remoteFileName);
			entryFileInfo.setFileType(suffix);
			entryFileInfo.setFilePath(filePath);
			entryFileInfo.setOperateUserId(user.getId());
			entryFileInfo.setAddTime(TimeUtil.getCurrentTime());
			return entryFileInfo;
		}
		return null;
	}
	
	
	public EntryAudioInfo uploadAudio(SysUser user,CommonsMultipartFile audioFile,String suffix){
		byte[] audioContent = audioFile.getBytes();
		String fileAudioPath = "";
		String remoteFileName = null;
		if(!StringUtils.isEmpty(audioFile.getOriginalFilename())){
			remoteFileName= System.currentTimeMillis()+"."+suffix;
			fileAudioPath=Constants.BOOK_AUDIO_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+remoteFileName;
			//将音频上到资源服务器上nginx或apache,与后台部署在同一台服务器
			FileOperation.writeFile(fileAudioPath,audioContent);	
			//保存到数据库
			EntryAudioInfo entryAudioInfo = new EntryAudioInfo();
			entryAudioInfo.setAudioName(audioFile.getOriginalFilename());
			entryAudioInfo.setUniqueName(remoteFileName);
			entryAudioInfo.setAudioType(suffix);
			entryAudioInfo.setAudioPath(fileAudioPath);
			entryAudioInfo.setOperateUserId(user.getId());
			entryAudioInfo.setAddTime(TimeUtil.getCurrentTime());
			return entryAudioInfo;
		}
		return null;
	}
	
	
	public EntryAudioInfo replaceAudio(SysUser user,CommonsMultipartFile audioFile,String suffix,EntryAudioInfo entryAudioInfo){
		byte[] audioContent = audioFile.getBytes();
		String fileAudioPath = "";
		String remoteFileName = null;
		if(!StringUtils.isEmpty(audioFile.getOriginalFilename())){
			remoteFileName= System.currentTimeMillis()+"."+suffix;
			fileAudioPath=Constants.BOOK_AUDIO_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+remoteFileName;
			//保存新的音频信息
			FileOperation.writeFile(fileAudioPath,audioContent);		
			//删除原来服务器上的文件
			FileOperation.deleteFile(entryAudioInfo.getUniqueName());
			//保存到数据库
			entryAudioInfo.setAudioName(audioFile.getOriginalFilename());
			entryAudioInfo.setUniqueName(remoteFileName);
			entryAudioInfo.setAudioType(suffix);
			entryAudioInfo.setAudioPath(fileAudioPath);
			entryAudioInfo.setOperateUserId(user.getId());
			entryAudioInfo.setAddTime(TimeUtil.getCurrentTime());
			baseDao.update(entryAudioInfo);
			return entryAudioInfo;
		}
		return null;
	}
	
	
	@Override
	public String editEntry(SysUser user,EntryModel model,CommonsMultipartFile coverImage,String coverImageFileType,CommonsMultipartFile coverThumbNail,String coverThumbNailType,CommonsMultipartFile entryImportFile,String fileType,CommonsMultipartFile audioFile,String audioType){
		List<EntryInfo> entryInfos = baseDao.query(EntryInfo.class, new Object[][]{{
			DaoQueryOperator.EQ,"title",model.getTitle()
		}});
		EntryInfo entryInfo = baseDao.get(EntryInfo.class, model.getId());
		if(entryInfos!=null && entryInfos.size()>0){
			for(EntryInfo entry:entryInfos){
				if(entry.getId()!=entryInfo.getId()&&entry.getAppInfo().getId()==entryInfo.getAppInfo().getId()){
					return EntryDefined.ENTRY_EXIST;
				}					
			}			
		}		
		entryInfo = parseModelBasicInfoToEntity(model,entryInfo);//转换基本信息
		
		entryInfo.setOperateUserId(user!=null?user.getId():null);
		entryInfo.setModifyTime(TimeUtil.getCurrentTime());
		//保存套书系列信息
		if(model.getEntryTypeId()!=null){
			EntryTypeInfo  entryTypeInfo = baseDao.get(EntryTypeInfo.class, model.getEntryTypeId());
			entryInfo.setEntryTypeInfo(entryTypeInfo);
		}
		//保存语种关联信息
		if (model.getLangId()!=null) {
			AngleInfo angleInfo = baseDao.get(AngleInfo.class,model.getLangId());
			entryInfo.setAngleInfo(angleInfo);
		}
		//保存运营商关联信息
		if(model.getApp_Id()!=null){
			AppInfo appInfo = baseDao.get(AppInfo.class, model.getApp_Id());
			entryInfo.setAppInfo(appInfo);
		}
		//保存图书信息
		baseDao.save(entryInfo);
 		
		//保存分类信息
		//清除原来的分类信息
		List<EntryToNode> entryToNodes = entryInfo.getEntryToNodeList();
		if(entryToNodes!=null&&entryToNodes.size()>0){
			for(EntryToNode  entryToNode:entryToNodes){
				baseDao.delete(entryToNode);
			}
		}
		entryInfo.getEntryToNodeList().clear();
		if(StringUtils.isNotEmpty(model.getNodeIds())){
			String[] nodeIdArr = model.getNodeIds().trim().split(",");
			for(String nodeIdStr:nodeIdArr){
				if(StringUtils.isNotEmpty(nodeIdStr)){
					int nodeId = Integer.parseInt(nodeIdStr);
					NodeInfo nodeInfo = baseDao.get(NodeInfo.class, nodeId);
					EntryToNode  entryToNode = new EntryToNode();
					entryToNode.setEntryInfo(entryInfo);
					entryToNode.setNodeInfo(nodeInfo);
					baseDao.save(entryToNode);
				}
			}
		}		
		//保存封面海报,并上传到服务器
		if(coverImage!=null&&StringUtils.isNotEmpty(coverImageFileType)){
			if(entryInfo.getCoverImageId()!=null){
				EntryImageInfo entryImageInfo = baseDao.get(EntryImageInfo.class, entryInfo.getCoverImageId());
				FileOperation.deleteNodeImages(entryImageInfo.getUniqueName());
				baseDao.delete(entryImageInfo);
			}
			//保存新的海报信息
			EntryImageInfo entryImageInfo = uploadImage(user,coverImage,coverImageFileType,0);//最后一个参数表示上传的是封面图片还是详情图片
			entryImageInfo.setEntryInfo(entryInfo);	
			entryInfo.setCoverImageId(entryImageInfo.getId());
			entryInfo.setCoverImage(entryImageInfo.getImagePath());
		}
		//小海报信息
		if(coverThumbNail!=null&&StringUtils.isNotEmpty(coverThumbNailType)){
			if(entryInfo.getCoverThumbNailId()!=null){
				EntryImageInfo entryImageInfo = baseDao.get(EntryImageInfo.class,entryInfo.getCoverThumbNailId());
				FileOperation.deleteNodeImages(entryImageInfo.getUniqueName());
				baseDao.delete(entryImageInfo);
			}
			EntryImageInfo entryImageInfo = uploadImage(user, coverThumbNail, coverThumbNailType,0);
			entryImageInfo.setEntryInfo(entryInfo);
			entryInfo.setCoverThumbNailId(entryImageInfo.getId());
			entryInfo.setCoverThumbNail(entryImageInfo.getImagePath());
		}
		//执行数据库更新操作
		baseDao.update(entryInfo);
		//保存txt文件/音频文件 ，并将源文件上传至nginx目录下，有旧的文件先删除，保存新的
		if(entryImportFile!=null&&StringUtils.isNotEmpty(fileType)){
			 //先删除旧的文件
			if("mp3".equals(fileType)||"mp4".equals(fileType)||"wmv".equals(fileType)){
				if(entryInfo.getEntryAudio()!=null){
					EntryAudioInfo entryAudioInfo = entryInfo.getEntryAudio();
					replaceAudio(user, entryImportFile, fileType,entryAudioInfo);
				}else {
					EntryAudioInfo entryAudioInfo = uploadAudio(user, entryImportFile, fileType);
					if(entryAudioInfo!=null) {
						entryAudioInfo.setEntryInfo(entryInfo);
						entryInfo.setEntryAudio(entryAudioInfo);
						entryInfo.setAudioPath(entryAudioInfo.getAudioPath());
					}
				}
			}else {
				EntryFileInfo entryFileInfo = null;
				if("txt".equals(fileType)){
					if(entryInfo.getContentPath()!=null&&!"".equals(entryInfo.getContentPath())){
						FileOperation.deleteFile(entryInfo.getContentPath());
					}
					entryFileInfo = uploadTxt(user,entryImportFile, fileType, entryInfo);
				}else if("pdf".equals(fileType)) {
					if(entryInfo.getContentPath()!=null&&!"".equals(entryInfo.getContentPath())){
						FileOperation.deleteFile(entryInfo.getContentPath());
					}
					entryFileInfo = uploadFile(user, entryImportFile, fileType);
				}else if("epub".equals(fileType)) {
					if(entryInfo.getContentPath()!=null&&!"".equals(entryInfo.getContentPath())){
						FileOperation.deleteFile(entryInfo.getContentPath());
					}
					entryFileInfo = uploadEpub(user, entryImportFile, fileType);
				}
				if(entryFileInfo!=null) {
					EntryFileInfo sourceFileInfo  = entryInfo.getEntryFile();
					if(sourceFileInfo!=null) {
						sourceFileInfo.setFileName(entryFileInfo.getFileName());
						sourceFileInfo.setUniqueName(entryFileInfo.getUniqueName());
						sourceFileInfo.setFileType(entryFileInfo.getFileType());
						sourceFileInfo.setFilePath(entryFileInfo.getFilePath());
						sourceFileInfo.setOperateUserId(user.getId());
						sourceFileInfo.setAddTime(TimeUtil.getCurrentTime());
						entryInfo.setEntryFile(sourceFileInfo);
						entryInfo.setContentPath(sourceFileInfo.getFilePath());
					}else {
						entryFileInfo.setEntryInfo(entryInfo);	
						entryInfo.setEntryFile(entryFileInfo);
						entryInfo.setContentPath(entryFileInfo.getFilePath());	
					}
					
				}
			}
		}
		//保存音频文件audioType,音频电子书同步的资源,之前有就替换
		if(audioFile!=null&&StringUtils.isNotEmpty(audioType)) {
			if(entryInfo.getEntryAudio()!=null){
				EntryAudioInfo entryAudioInfo = entryInfo.getEntryAudio();
				replaceAudio(user, audioFile, audioType,entryAudioInfo);
			}else {
				EntryAudioInfo entryAudioInfo = uploadAudio(user, audioFile, audioType);
				if(entryAudioInfo!=null) {
					entryAudioInfo.setEntryInfo(entryInfo);
					entryInfo.setAudioPath(entryAudioInfo.getAudioPath());
					entryInfo.setEntryAudio(entryAudioInfo);
				}
			}
		}
		
		//转换成EsEntry，更新数据库、同步更新ES
		try {
			EsEntry esEntry = new EsEntry(entryInfo);
			baseDao.update(esEntry);
			Map<String, Object> map = new HashMap<String, Object>(); 
			BeanUtil.bean2Map(esEntry, map);
			JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
			
			boolean flag = true;
			if(!elasticsearchHelper.isIndexExist(this.INDEX)) {
				flag = elasticsearchHelper.createIndex(this.INDEX, this.TYPE);
				
			}
			if(flag == true) {
				elasticsearchHelper.updateDataById(jsonObject, this.INDEX, this.TYPE, String.valueOf(esEntry.getId()));
			}
			
			systemLogService.saveSystemLog("图书管理", "编辑图书", "用户\""+user.getUserName()+"\"编辑了\""+model.getTitle()+ "\"图书", user);
		} catch (Exception e) {
			Log.e(TAG, "系统添加日志出错"+e);
		}
		return null;
	}
	
	
	private EntryFileInfo uploadTxt(SysUser user,CommonsMultipartFile entryImportFile,
			String fileType, EntryInfo entryInfo) {
		try {
			String languageCode = FileUtil.getFileEncode(entryImportFile);
			InputStream inputStream = entryImportFile.getInputStream();
			//InputStreamReader reader = new InputStreamReader(inputStream);
			String remoteFileName = TimeUtil.getTimeStamp()+".js";
			String contentPath=Constants.BOOK_TEXT_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+remoteFileName;
			String rootPath = Constants.FILE_SERVER_ROOT_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG;
			//将音频上到资源服务器上nginx或apache,与后台部署在同一台服务器
			FileUtil.getFileString(inputStream,languageCode,rootPath+contentPath);
			entryInfo.setContentPath(contentPath);
			//保存到数据库
			EntryFileInfo entryFileInfo = new EntryFileInfo();
			entryFileInfo.setFileName(entryImportFile.getOriginalFilename());
			entryFileInfo.setUniqueName(remoteFileName);
			entryFileInfo.setFileType(fileType);
			entryFileInfo.setFilePath(contentPath);
			entryFileInfo.setOperateUserId(user.getId());
			entryFileInfo.setAddTime(TimeUtil.getCurrentTime());
			return entryFileInfo;
		} catch (Exception e) {
			Log.e(TAG, "保存内容文件:"+entryImportFile.getOriginalFilename()+"失败");
			e.printStackTrace();				
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void deleteEntry(SysUser user,String idsString){
		StringBuffer sb = new StringBuffer();
		String[] ids = idsString.trim().split(",");
		for(String id : ids){
			Integer intId = Integer.parseInt(id);
			EntryInfo entryInfo = baseDao.get(EntryInfo.class, intId);
			sb.append(entryInfo.getTitle()+",");
			baseDao.delete(entryInfo);
			
			//链式修改其他记录的rankNumber值
			List<EntryInfo> entrys = (List<EntryInfo>) baseDao.query("from EntryInfo e where e.rankNumber > " + entryInfo.getRankNumber());
			for(EntryInfo entry : entrys) {
				if(entry.getRankNumber() - 1 >= 1) {
					entry.setRankNumber(entry.getRankNumber() - 1);
				} else {
					entry.setRankNumber(1);
				}
				baseDao.update(entry);
			}
			
			//需要删除服务器上的海报资源,包含海报资源
			List<EntryImageInfo> entryImageInfos = entryInfo.getEntryImageList();
			for(EntryImageInfo entryImageInfo:entryImageInfos){
				String filePath="";
				if(entryImageInfo.getPosition()==0){
					 filePath=Constants.BOOK_IMAGE_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+entryImageInfo.getUniqueName();	
				}else if(entryImageInfo.getPosition()==1) {
					 filePath=Constants.BOOK_ALBUM_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+entryImageInfo.getUniqueName();
				}
				FileOperation.deleteFile(filePath);	
			}
			//删除音频资源
			if(entryInfo.getAudioPath()!=null&&!"".equals(entryInfo.getAudioPath())){
				FileOperation.deleteFile(entryInfo.getAudioPath());
			}
			//删除文件资源
			if(entryInfo.getContentPath()!=null&&!"".equals(entryInfo.getContentPath())){
				FileOperation.deleteFile(entryInfo.getContentPath());
			}
			
			//同步删除对应的es_entry表中记录、ES中对应的索引记录
			EsEntry esEntry = baseDao.get(EsEntry.class, intId);
			baseDao.delete(esEntry);
			if(elasticsearchHelper.isIndexExist(this.INDEX)) {
				elasticsearchHelper.deleteDataById(this.INDEX, this.TYPE, id);
			}
		}
		
		try {
			systemLogService.saveSystemLog("图书管理", "删除图书", "用户\""+user.getUserName()+"\"添加了\""+sb.toString()+ "\"图书", user);
		} catch (Exception e) {
			Log.e(TAG, "系统添加日志出错"+e);
		}				
	}

	
	@Override
	public EntryModel getEntryDetail(Integer id) {
		EntryInfo entryInfo = baseDao.get(EntryInfo.class, id);
		EntryModel  entryModel = new EntryModel();
		if(entryInfo!=null){
			entryModel = parseModel(entryInfo,entryModel);
			entryModel = parseDetailContent(entryInfo, entryModel);
		}
		return entryModel;
	}

	
	@Override
	public EntryAlbumModel getEntryAlbums(Integer entryId) {
		EntryInfo entryInfo = baseDao.get(EntryInfo.class,entryId);
		List<EntryImageInfo> entryImageInfos = entryInfo.getEntryImageList();
		List<EntryImageModel> entryImageModels = new ArrayList<EntryImageModel>();
		EntryAlbumModel entryAlbumModel = new EntryAlbumModel();
		for(EntryImageInfo entryImageInfo:entryImageInfos){
			EntryImageModel entryImageModel = new EntryImageModel();
			entryImageModel.setId(entryImageInfo.getId());
			entryImageModel.setImageName(entryImageInfo.getImageName());
			entryImageModel.setUniqueName(entryImageInfo.getUniqueName());
			entryImageModel.setImagePath(FileOperation.getImagePath(entryImageInfo.getUniqueName()));
			entryImageModels.add(entryImageModel);
		}
		entryAlbumModel.setEntryImages(entryImageModels);
		return entryAlbumModel;
	}

	
	@Override
	public String editEntryAllAlbums(SysUser user,CommonsMultipartFile[] albumImages,Integer id) {
		String fileType = "";
		EntryInfo entryInfo = baseDao.get(EntryInfo.class,id);
		List<EntryImageInfo> entryImageInfos = entryInfo.getEntryImageList();
		//删除之前的详情海报,TV端
		for (int i = 0; i < entryImageInfos.size(); i++) {
			EntryImageInfo entryImageInfo = entryImageInfos.get(i);
			baseDao.delete(entryImageInfo);	
		}			
		if(albumImages!=null&&albumImages.length>0){
			for(CommonsMultipartFile file:albumImages){
				String fileName = file.getOriginalFilename();
				if(StringUtils.isNotEmpty(fileName)){
					String[] fileNames = fileName.trim().split("\\.");
					if (fileNames != null && fileNames.length > 1) {
						fileType = fileNames[fileNames.length - 1].trim(); //后缀
						//保存新的海报图片
						EntryImageInfo 	entryImageInfo = uploadImage(user,file,fileType,0);
						entryImageInfo.setEntryInfo(entryInfo);
					} else {
						return "tv端海报文件名异常";
					}
				}
			}
		}
		try {
			systemLogService.saveSystemLog("图书管理", "编辑图书详情海报图书", "用户\""+user.getUserName()+"\"更新了\""+entryInfo.getTitle()+ "\"图书的详情海报", user);
		} catch (Exception e) {
			Log.e(TAG, "系统添加日志出错"+e);
		}	
		return null;
	}

	
	@Override
	public String replaceEntryImage(SysUser user,CommonsMultipartFile file,Integer imageId,Integer position) {
		EntryImageInfo entryImageInfo = baseDao.get(EntryImageInfo.class, imageId);
		if(entryImageInfo!=null){
			String fileName = file.getOriginalFilename();
			if(StringUtils.isNotEmpty(fileName)){
				String[] fileNames = fileName.trim().split("\\.");
				if (fileNames != null && fileNames.length > 1) {
					String fileType = fileNames[fileNames.length - 1].trim(); //后缀
					//更新图片
					replaceEntryImage(user,file, fileType, entryImageInfo,position);											
				} else {
					return "app端海报文件名异常";
				}
			}
		}else {
			return "图片不存在";
		}
		return null;
	}
	
	
	@Override
	public String deleteEntryImage(SysUser user, Integer imageId) {
		EntryImageInfo entryImageInfo = baseDao.get(EntryImageInfo.class, imageId);
		if(entryImageInfo!=null){
			baseDao.delete(entryImageInfo);
			//删除服务器图片
			FileOperation.deleteFile(entryImageInfo.getUniqueName());
		}else{
			return "图片不存在";
		}
		return null;
	}
	
}
