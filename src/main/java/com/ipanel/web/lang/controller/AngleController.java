package com.ipanel.web.lang.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.common.model.BaseDataModel.ResponseDataModel;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.lang.pageModel.AngleModel;
import com.ipanel.web.lang.service.IAngleService;
import com.ipanel.web.utils.Constants;
import com.ipanel.webapp.framework.util.Log;

/**
 * @author fangg
 * 2017年5月31日 下午5:32:21
 */
@Controller
@RequestMapping("/angleController")
public class AngleController {
	
	private final String TAG = "angleController";
	
	@Autowired
	private IAngleService angleService;
	
	@RequestMapping("/queryAngleList")
	@ResponseBody
	public PageDataModel queryAngleList(HttpSession session,AngleModel model){
		try {
			Log.i(TAG,"*** queryAngleList enter :"+model.getName());
			return angleService.queryAngleList(model);
		} catch (Exception e) {
			Log.e(TAG,"*** queryAngleList throw Exception :"+e);
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/addAngle")
	@ResponseBody
	public ResponseDataModel addAngle(
			@RequestParam("angleImage") CommonsMultipartFile angleImage,
			HttpSession session,AngleModel model){
		try {
			Log.i(TAG,"*** addAngle enter :"+model.getName());
			String fileType = "";
			if(angleImage!=null){
				String fileName = angleImage.getOriginalFilename();
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
			String msg = angleService.addAngle((SysUser)session.getAttribute(Constants.SESSION_USER),model,fileType,angleImage);
			if(StringUtils.isNotEmpty(msg)){
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,msg);				
			}
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		} catch (Exception e) {
			Log.e(TAG,"*** addAngle throw Exception :"+e);
			e.printStackTrace();
			return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
		}
		
	}
	@RequestMapping("/editAngle")
	@ResponseBody
	public ResponseDataModel editAngle(
			@RequestParam("angleImage") CommonsMultipartFile angleImage,
			HttpSession session,AngleModel model){
		try {
			Log.i(TAG,"*** editAngle enter :"+model.getName());
			String fileType = "";
			if(angleImage!=null){
				String fileName = angleImage.getOriginalFilename();
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
			String msg = angleService.editAngle((SysUser)session.getAttribute(Constants.SESSION_USER),model,fileType,angleImage);
			if(StringUtils.isNotEmpty(msg)){
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,msg);				
			}
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		} catch (Exception e) {
			Log.e(TAG,"*** editAngle throw Exception :"+e);
			e.printStackTrace();
			return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
		}
		
	}
	@RequestMapping("/deleteAngle")
	@ResponseBody
	public ResponseDataModel deleteAngle(HttpSession session,String ids){
		try {
			Log.i(TAG,"*** deleteAngle ids="+ids);
			String msg = angleService.deleteAngle((SysUser)session.getAttribute(Constants.SESSION_USER),ids);
			if(StringUtils.isNotEmpty(msg)){
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,msg);				
			}
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		} catch (Exception e) {
			Log.e(TAG,"*** deleteAngle throw Exception :"+e);
			e.printStackTrace();
			return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
		}
		
	}

}
