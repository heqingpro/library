package com.ipanel.web.lang.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.lang.pageModel.AngleModel;

/**
 * @author fangg
 * 2017年5月31日 下午5:32:47
 */
public interface IAngleService {

	public String addAngle(SysUser attribute, AngleModel model,String fileType, CommonsMultipartFile image);

	public String editAngle(SysUser attribute, AngleModel model,String fileType, CommonsMultipartFile image);
	
	public PageDataModel queryAngleList(AngleModel model);

	public String deleteAngle(SysUser attribute, String ids);

	

}
