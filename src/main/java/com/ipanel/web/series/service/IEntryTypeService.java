package com.ipanel.web.series.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.series.pageModel.EntryTypeModel;

public interface IEntryTypeService {

	public PageDataModel queryEntryTypeList(SysUser user, EntryTypeModel model);

	public String addEntryType(SysUser user, EntryTypeModel model, String fileType, CommonsMultipartFile angleImage);

	public String editEntryType(SysUser user, EntryTypeModel model, String fileType, CommonsMultipartFile angleImage);

	public void deleteEntryType(SysUser user, String ids);

}
