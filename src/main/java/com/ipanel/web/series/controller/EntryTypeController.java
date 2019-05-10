package com.ipanel.web.series.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.common.model.BaseDataModel.ResponseDataModel;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.series.pageModel.EntryTypeModel;
import com.ipanel.web.series.service.IEntryTypeService;
import com.ipanel.web.utils.Constants;
import com.ipanel.webapp.framework.util.Log;

/**
 * EntryTypeController
 * @author fangg
 * 2017年5月9日 上午9:18:06
 */

@Controller
@RequestMapping("/entryTypeController")
public class EntryTypeController {

	private final String TAG = "EntryTypeController";
	
	@Autowired
	private IEntryTypeService entryTypeService;
	
	@RequestMapping("/queryEntryTypeList")
	@ResponseBody
	public PageDataModel queryEntryTypeList(HttpSession session,EntryTypeModel model){
		try {
			Log.i(TAG, "*** queryEntryTypeList enter ,entryTypeName = "+model.getTypeName());
			return entryTypeService.queryEntryTypeList((SysUser)session.getAttribute(Constants.SESSION_USER),model);
		} catch (Exception e) {
			Log.e(TAG, "*** queryEntryTypeList throw Exception:"+e);
			e.printStackTrace();
			return null;
		}		
	}
	
	@RequestMapping("/addEntryType")
	@ResponseBody
	public ResponseDataModel addEntryType(@RequestParam("typeImage") CommonsMultipartFile typeImage
			,HttpSession session,EntryTypeModel model){
		try {
			Log.i(TAG, "*** addEntryType enter ,entryTypeName"+model.getTypeName());
			String fileType = "";
			if(typeImage!=null){
				String fileName = typeImage.getOriginalFilename();
				if (StringUtils.isNotEmpty(fileName)) {
					String[] fileNames = fileName.trim().split("\\.");
					if (fileNames != null && fileNames.length > 1) {
						fileType = fileNames[fileNames.length - 1].trim(); //后缀
					} else {
						return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
								"海报文件名异常");
					}
				}
			}			
			String msg = entryTypeService.addEntryType((SysUser)session.getAttribute(Constants.SESSION_USER), model,fileType,typeImage);
			if(StringUtils.isNotEmpty(msg)){
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, msg);
			}
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		} catch (Exception e) {
			Log.e(TAG, " ***　addEntryType　throw Exception:"+e);
			e.printStackTrace();
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		}
	}
	
	@RequestMapping("/editEntryType")
	@ResponseBody
	public ResponseDataModel editEntryType(@RequestParam("typeImage") CommonsMultipartFile typeImage,
			HttpSession session,EntryTypeModel model){
		try {
			Log.i(TAG, "***　editEntryType enter ,typeName="+model.getTypeName());
			String fileType = "";
			if(typeImage!=null){
				String fileName = typeImage.getOriginalFilename();
				if (StringUtils.isNotEmpty(fileName)) {
					String[] fileNames = fileName.trim().split("\\.");
					if (fileNames != null && fileNames.length > 1) {
						fileType = fileNames[fileNames.length - 1].trim(); //后缀
					} else {
						return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
								"海报文件名异常");
					}
				}
			}		
			String msg = entryTypeService.editEntryType((SysUser)session.getAttribute(Constants.SESSION_USER), model,fileType,typeImage);
			if(StringUtils.isNotEmpty(msg)){
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, msg);
			}
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		} catch (Exception e) {
			Log.e(TAG, " ***　editEntryType　throw Exception:"+e);
			e.printStackTrace();			
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		}
	}

	@RequestMapping("/deleteEntryType")
	@ResponseBody
	public ResponseDataModel deleteEntryType(HttpSession session,String ids){
		try {
			Log.i(TAG, "*** deleteEntryType enter,ids="+ids);
			entryTypeService.deleteEntryType((SysUser)session.getAttribute(Constants.SESSION_USER), ids);
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		} catch (Exception e) {
			Log.e(TAG, " ***　deleteEntryType　throw Exception:"+e);
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		}
	}
	
}
