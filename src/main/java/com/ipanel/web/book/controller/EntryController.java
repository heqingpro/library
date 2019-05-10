package com.ipanel.web.book.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ipanel.web.book.controller.resp.BaseResp;
import com.ipanel.web.book.pageModel.EntryAlbumModel;
import com.ipanel.web.book.pageModel.EntryModel;
import com.ipanel.web.book.pageModel.EntrySearchModel;
import com.ipanel.web.book.service.IEntryService;
import com.ipanel.web.common.model.BaseDataModel.ResponseDataModel;
import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.utils.Constants;
import com.ipanel.webapp.framework.util.Log;

@Controller
@RequestMapping("/entryController")
public class EntryController {
	
	private final String TAG = "EntryController";
	
	@Autowired
	private IEntryService entryService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EntryController.class);
	
	
	/**
	 * 重建索引
	 *
	 * @author lvchao
	 * @createtime 2018年4月18日 上午11:20:37
	 *
	 * @return
	 */
	@RequestMapping("/recreateBookIndex")
	@ResponseBody
	public BaseResp recreateBookIndex() {
		try {
			boolean flag = entryService.recreateBookIndex();
			if(flag == true) {
				return new BaseResp(0, "Index create and sync data to ES success!");
			}
		} catch(Exception e) {
			LOGGER.error("createBookIndex->Index create and sync data to ES failed!", e);
		}
		return new BaseResp(1, "Index create and sync data to ES failed!");
	}
	
	
	/**
	 * 获取图书列表，带分页
	 * @author fangg
	 * 2017年5月13日 下午4:59:36
	 * @param session
	 * @param model
	 * @param nodeId
	 * @return
	 */
	@RequestMapping("/queryEntryList")
	@ResponseBody
	public PageDataModel queryEntryList(HttpSession session, EntryModel model){
		try {
			Log.i(TAG, "*** queryEntryList enter appId="+model.getApp_Id()+", entryName="+model.toString());
			
			return entryService.queryEntryList((SysUser)session.getAttribute(Constants.SESSION_USER), model);
		} catch (Exception e) {
			Log.e(TAG, "*** queryEntryList throw Exception:"+e);
			e.printStackTrace();
			return null;
		}		
	}
	
	/**
	 * 根据套书系列获取图书列表
	 * @author fangg
	 * 2017年5月31日 上午11:53:56
	 * @return
	 */
	@RequestMapping("/queryEntryByEntryType")
	@ResponseBody
	public PageDataModel queryEntryByEntryType(HttpSession session,EntrySearchModel model){
		try {
			Log.i(TAG, "*** queryEntryByEntryType enter ,entryTypeName="+model.getEntryTypeName());
			return entryService.queryEntryByEntryType((SysUser)session.getAttribute(Constants.SESSION_USER),model);
		} catch (Exception e) {
			Log.e(TAG, "*** queryEntryByEntryType throw Exception:"+e);
			e.printStackTrace();
			return null;
		}	
	}
	
	/**
	 * 获取图书详情
	 *
	 * @author lvchao
	 * @createtime 2018年4月18日 上午11:20:12
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/getEntryDetail")
	@ResponseBody
	public EntryModel getEntryDetail(EntryModel model){
		try {
			Log.i(TAG, "*** getEntryDetail enter");
			return entryService.getEntryDetail(model.getId());
		} catch (Exception e) {
			Log.e(TAG, "*** getEntryDetail throw Exception:"+e);
			return null;
		}
	}
	
	/**
	 * 添加图书
	 * @author fangg
	 * 2017年5月13日 下午4:59:25
	 * @param listImage_tv
	 * @param listImage_app
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/addEntry")
	@ResponseBody
	public ResponseDataModel addEntry(@RequestParam("coverImage") CommonsMultipartFile coverImage
			,@RequestParam("coverThumbNail") CommonsMultipartFile coverThumbNail
			,@RequestParam("entryImportFile") CommonsMultipartFile entryImportFile
			,@RequestParam("audioFile") CommonsMultipartFile audioFile
			,HttpSession session,EntryModel model) {
		String fileType = "";
		String coverImageFileType="";
		String coverThumbNailType="";
		String audioType = "";
		try {
			Log.i(TAG, "*** addEntry enter appId="+model.getApp_Id()+" ,entryName="+model.getTitle());
			//tv端的入口海报
			if(coverImage!=null){
				String fileName = coverImage.getOriginalFilename();
				if(StringUtils.isNotEmpty(fileName)){
					String[] fileNames = fileName.trim().split("\\.");
					if (fileNames != null && fileNames.length > 1) {
						coverImageFileType = fileNames[fileNames.length - 1].trim(); //后缀
					} else {
						return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
								"封面海报文件名异常");
					}
				}
			}
			//封面小海报
			if(coverThumbNail!=null){
				String fileName = coverThumbNail.getOriginalFilename();
				if(StringUtils.isNotEmpty(fileName)){
					String[] fileNames = fileName.trim().split("\\.");
					if (fileNames != null && fileNames.length > 1) {
						coverThumbNailType = fileNames[fileNames.length - 1].trim(); //后缀
					} else {
						return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
								"封面小海报文件名异常");
					}
				}
			}
			if(entryImportFile!=null){
				String fileName = entryImportFile.getOriginalFilename();
				if(StringUtils.isNotEmpty(fileName)){
					String[] fileNames = fileName.trim().split("\\.");
					if (fileNames != null && fileNames.length > 1) {
						fileType = fileNames[fileNames.length - 1].trim();
					} else {
						return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
								"内容文件名异常");
					}
					if (!"txt".equals(fileType.toLowerCase())
							&& !"epub".equals(fileType.toLowerCase())
							&& !"pdf".equals(fileType.toLowerCase())
							&& !"mp3".equals(fileType.toLowerCase())
							&& !"mp4".equals(fileType.toLowerCase())
							&& !"wmv".equals(fileType.toLowerCase())
							) {
						return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
								"文件不正确，请正确选择以.txt或.epub或.pdf或.zip或.mp3结尾的文件");
					}
				}
			}
			//音频文件 
			if(audioFile!=null){
				String fileName = audioFile.getOriginalFilename();
				if(StringUtils.isNotEmpty(fileName)){
					String[] fileNames = fileName.trim().split("\\.");
					if (fileNames != null && fileNames.length > 1) {
						audioType = fileNames[fileNames.length - 1].trim();
					} else {
						return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
								"内容文件名异常");
					}
					if (!"mp3".equals(audioType.toLowerCase())
							&& !"mp4".equals(audioType.toLowerCase())
							&& !"wmv".equals(audioType.toLowerCase())) {
						return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
								"文件不正确，请正确选择以.mp3或.mp4或点.wmv结尾的文件");
					}
				}
			}
			
			String msg = entryService.addEntry((SysUser)session.getAttribute(Constants.SESSION_USER),model,coverImage,coverImageFileType,coverThumbNail,coverThumbNailType,entryImportFile,fileType,audioFile,audioType);
			if(StringUtils.isNotEmpty(msg)){
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, msg);
			}
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		} catch (Exception e) {
			Log.e(TAG, "*** addEntry throw Exception:"+e);
			e.printStackTrace();
			return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
		}
	}
	
	/**
	 * 修改图书排序
	 *
	 * @author lvchao
	 * @createtime 2018年4月19日 上午11:04:30
	 *
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/changeEntryRankNumber")
	@ResponseBody
	public ResponseDataModel changeEntryRankNumber(HttpSession session, EntryModel model){
		try {
			Log.i(TAG, "*** changeEntryRankNumber enter, entryName=" + model.getTitle());
			boolean flag = entryService.changeEntryRankNumber((SysUser)session.getAttribute(Constants.SESSION_USER), model);
			if(flag){
				return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
			} else {
				return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
			}
		} catch (Exception e) {
			Log.e(TAG, "*** changeEntryRankNumber throw Exception:"+e);
			e.printStackTrace();
			return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
		}
	}
	
	/**
	 * 修改图书
	 * @author fangg
	 * 2017年5月13日 下午4:59:11
	 * @param listImage_tv
	 * @param listImage_app
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/editEntry")
	@ResponseBody
	public ResponseDataModel editEntry(@RequestParam("coverImage") CommonsMultipartFile coverImage
			,@RequestParam("coverThumbNail") CommonsMultipartFile coverThumbNail
			,@RequestParam("entryImportFile") CommonsMultipartFile entryImportFile	
			,@RequestParam("audioFile") CommonsMultipartFile audioFile
			,HttpSession session,EntryModel model){
		String fileType = "";
		String audioType = "";
		String coverImageFileType="";
		String coverThumbNailType ="";
		try {
			Log.i(TAG, "*** addEntry enter ,entryName="+model.getTitle());
			//封面海报
			if(coverImage!=null){
				String fileName = coverImage.getOriginalFilename();
				if(StringUtils.isNotEmpty(fileName)){
					String[] fileNames = fileName.trim().split("\\.");
					if (fileNames != null && fileNames.length > 1) {
						coverImageFileType = fileNames[fileNames.length - 1].trim(); //后缀
					} else {
						return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
								"封面海报文件名异常");
					}
				}
			}
			//封面小海报
			if(coverThumbNail!=null){
				String fileName = coverThumbNail.getOriginalFilename();
				if(StringUtils.isNotEmpty(fileName)){
					String[] fileNames = fileName.trim().split("\\.");
					if (fileNames != null && fileNames.length > 1) {
						coverThumbNailType = fileNames[fileNames.length - 1].trim(); //后缀
					} else {
						return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
								"封面小海报文件名异常");
					}
				}
			}
			if(entryImportFile!=null){
				String fileName = entryImportFile.getOriginalFilename();
				if(StringUtils.isNotEmpty(fileName)){
					String[] fileNames = fileName.trim().split("\\.");
					if (fileNames != null && fileNames.length > 1) {
						fileType = fileNames[fileNames.length - 1].trim();
					} else {
						return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
								"内容文件名异常");
					}
					if (!"txt".equals(fileType.toLowerCase())
							&& !"epub".equals(fileType.toLowerCase())
							&& !"pdf".equals(fileType.toLowerCase())
							&& !"mp3".equals(fileType.toLowerCase())
							&& !"mp4".equals(fileType.toLowerCase())
							&& !"wmv".equals(fileType.toLowerCase())
							) {
						return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
								"文件不正确，请正确选择以.txt或.epub或.pdf或.mp3结尾的文件");
					}
				}
			}
			//音频文件 
			if(audioFile!=null){
				String fileName = audioFile.getOriginalFilename();
				if(StringUtils.isNotEmpty(fileName)){
					String[] fileNames = fileName.trim().split("\\.");
					if (fileNames != null && fileNames.length > 1) {
						audioType = fileNames[fileNames.length - 1].trim();
					} else {
						return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
								"内容文件名异常");
					}
					if (!"mp3".equals(audioType.toLowerCase())
							&& !"mp4".equals(audioType.toLowerCase())
							&& !"wmv".equals(audioType.toLowerCase())) {
						return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
								"文件不正确，请正确选择以.mp3或.mp4或点.wmv结尾的文件");
					}
				}
			}
			//编辑图书信息
			String msg = entryService.editEntry((SysUser)session.getAttribute(Constants.SESSION_USER),model,coverImage,coverImageFileType,coverThumbNail,coverThumbNailType,entryImportFile,fileType,audioFile,audioType);
			if(StringUtils.isNotEmpty(msg)){
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, msg);
			}
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		} catch (Exception e) {
			Log.e(TAG, "*** editEntry throw Exception:"+e);
			e.printStackTrace();
			return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
		}
	}
	
	/**
	 * 删除图书
	 * @author fangg
	 * 2017年5月13日 下午5:00:10
	 * @param session
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteEntry")
	@ResponseBody
	public ResponseDataModel deleteEntry(HttpSession session,String ids){
		try {
			Log.i(TAG, "*** deleteEntry enter ,ids="+ids);
			entryService.deleteEntry((SysUser)session.getAttribute(Constants.SESSION_USER), ids);
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		} catch (Exception e) {
			Log.e(TAG, "*** deleteEntry throw Exception:"+e);
			return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
		}
	}
	
	/**
	 * 获取图书详情图片
	 * @author fangg
	 * 2017年5月13日 下午4:58:59
	 * @param session
	 * @param entryId
	 * @return
	 */
	@RequestMapping("/getEntryAlbums")
	@ResponseBody
	public EntryAlbumModel getEntryAlbums(HttpSession session,Integer entryId){
		try {
			Log.i(TAG, "*** getEntryAlbums enter ,entryId="+entryId);
			return entryService.getEntryAlbums(entryId);
		} catch (Exception e) {
			Log.e(TAG, "*** getEntryAlbums throw Exception:"+e);
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 上传图书详情图片
	 * @author fangg
	 * 2017年5月13日 下午4:58:43
	 * @param albumImages_tv
	 * @param albumImages_app
	 * @param session
	 * @param id
	 * @param deviceType
	 * @return
	 */
	@RequestMapping("/editEntryAlbums")
	@ResponseBody
	public ResponseDataModel editEntryAlbums(@RequestParam("albumImages") CommonsMultipartFile[] albumImages,HttpSession session,Integer id){
		try {
			Log.i(TAG, "*** editEntryAlbums enter ,entryId="+id);
			String msg = entryService.editEntryAllAlbums((SysUser)session.getAttribute(Constants.SESSION_USER),albumImages,id);
			if(StringUtils.isNotEmpty(msg)){
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, msg);
			}
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		} catch (Exception e) {
			Log.e(TAG, "*** editEntryAlbums throw Exception:"+e);
			e.printStackTrace();
			return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
		}
	}
	
	
	/**
	 * 替换图书图片
	 * @author fangg
	 * 2017年5月13日 下午4:58:31
	 * @param image
	 * @param session
	 * @param imageId
	 * @return
	 */
	@RequestMapping("/replaceEntryImage")
	@ResponseBody
	public ResponseDataModel replaceEntryImage(@RequestParam("image") CommonsMultipartFile image,HttpSession session,Integer imageId,Integer position){
		try {
			Log.i(TAG, "*** replaceEntryImage enter ,imageId="+imageId);
			String msg = entryService.replaceEntryImage((SysUser)session.getAttribute(Constants.SESSION_USER),image,imageId,position);
			if(StringUtils.isNotEmpty(msg)){
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,msg);
			}
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		} catch (Exception e) {
			Log.e(TAG, "*** replaceEntryImage throw Exception:"+e);
			e.printStackTrace();
			return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
		}
	}
	
	/**
	 * 删除图书的图片
	 * @author fangg
	 * 2017年5月13日 下午4:59:52
	 * @param session
	 * @param imageId
	 * @return
	 */
	@RequestMapping("/delEntryImage")
	@ResponseBody
	public ResponseDataModel delEntryImage(HttpSession session ,Integer imageId){
		try {
			Log.i(TAG, "*** editEntryAlbums enter ,imageId="+imageId);
			String msg = entryService.deleteEntryImage((SysUser)session.getAttribute(Constants.SESSION_USER),imageId);
			if(StringUtils.isNotEmpty(msg)){
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,msg);
			}
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		} catch (Exception e) {
			Log.e(TAG, "*** editEntryAlbums throw Exception:"+e);
			e.printStackTrace();
			return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
		}
	}
	
}
