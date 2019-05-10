package com.ipanel.web.appUser.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ipanel.web.appUser.pageModel.AppUserModel;
import com.ipanel.web.book.pageModel.EntryModel;
import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.entity.SysUser;

/**
 * @author fangg
 * 2017年5月15日 下午3:54:41
 */
public interface IAppUserService {

	public PageDataModel queryUserList(SysUser attribute, AppUserModel model);

	public List<EntryModel> queryPersonalEntryList(SysUser attribute, AppUserModel model);

	public String importAppUser(CommonsMultipartFile appUserImportFile,
			String fileType, SysUser attribute);

}
