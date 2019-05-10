package com.ipanel.web.appUser.pageModel;

import java.util.List;

import com.ipanel.web.book.pageModel.EntryModel;

/**
 * @author fangg
 * 2017年5月16日 上午10:48:44
 */
public class AppUserEntryModel {
	
	private Integer appUserId;
	
	private String appUserName;
	
	private List<EntryModel> entryModels;

	public Integer getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
	}

	public String getAppUserName() {
		return appUserName;
	}

	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}

	public List<EntryModel> getEntryModels() {
		return entryModels;
	}

	public void setEntryModels(List<EntryModel> entryModels) {
		this.entryModels = entryModels;
	}
	
	

}
