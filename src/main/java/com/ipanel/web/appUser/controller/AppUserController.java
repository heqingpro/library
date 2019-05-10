package com.ipanel.web.appUser.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ipanel.web.appUser.pageModel.AppUserModel;
import com.ipanel.web.appUser.service.IAppUserService;
import com.ipanel.web.book.pageModel.EntryModel;
import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.common.model.BaseDataModel.ResponseDataModel;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.utils.Constants;
import com.ipanel.webapp.framework.util.Log;

/**
 * @author fangg
 * 2017年5月15日 下午3:36:02
 */
@Controller
@RequestMapping("/appUserController")
public class AppUserController {

	private final String TAG = "AppUserController";
	
	@Autowired
	private IAppUserService appUserService;
	
	@RequestMapping("/queryAppUserList")
	@ResponseBody
	public PageDataModel queryAppUserList(HttpSession session,AppUserModel model){
		try {
			Log.i(TAG, "*** queryUserList enter ,userId "+model.getCaId());
			return appUserService.queryUserList((SysUser)session.getAttribute(Constants.SESSION_USER),model);			
		} catch (Exception e) {
			Log.e(TAG, "*** queryUserList throw Exception:"+e);
			return null;
		}
	}
	
	@RequestMapping("/queryPersonalEntryList")
	@ResponseBody
	public List<EntryModel> queryPersonalEntryList(HttpSession session,AppUserModel  model){
		try {
			
			Log.i(TAG, "*** queryPersonalEntryList enter ,userId ="+model.getId());
			return appUserService.queryPersonalEntryList((SysUser)session.getAttribute(Constants.SESSION_USER),model);
		} catch (Exception e) {
			Log.e(TAG, "*** queryPersonalEntryList throw Exception:"+e);
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping("/importAppUser")
	@ResponseBody
	public ResponseDataModel importAppUser(@RequestParam("appUserImportFile") CommonsMultipartFile appUserImportFile,
			HttpSession session){
		String fileType = "";
		try {
			Log.i(TAG, "****importAppUser****");
			String fileName = appUserImportFile.getOriginalFilename();
			String[] fileNames = fileName.trim().split("\\.");
			if (fileNames != null && fileNames.length > 1) {
				fileType = fileNames[fileNames.length - 1].trim();
			} else {
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
						"文件名异常");
			}
		} catch (Exception e) {
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, "文件异常");
		}
		if (!"xls".equals(fileType.toLowerCase())
				&& !"xlsx".equals(fileType.toLowerCase())) {
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
					"文件不正确，请正确选择以.xls或.xlsx结尾的excel文件");
		}
		//上传文件
		try {
			String msg = appUserService.importAppUser(appUserImportFile,fileType,(SysUser) session.getAttribute(Constants.SESSION_USER));
			if (msg != null && !"".equals(msg)) {
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, msg);
			} else {
				return new ResponseDataModel(ResponseDataModel.SUCCESS, null);
			}
		} catch (Exception e) {
			Log.e(TAG, "*** importAppUser throw Exception:"+e.getMessage());
			e.printStackTrace();
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, "导入失败");
		}
		
	}
	
	
	
}
