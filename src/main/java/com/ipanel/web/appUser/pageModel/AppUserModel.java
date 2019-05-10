package com.ipanel.web.appUser.pageModel;
/**
 * @author fangg
 * 2017年5月15日 下午3:54:18
 */

public class AppUserModel {

	private Integer page;
	
	private Integer rows;
	
	private Integer id;
	
	private String userId;
	
	private String userName;
	
	private String caId;
	
	private String searchUserName;
	
	private Integer recordType; //用户记录类型，(0：我想做，1：我会做，2：我做过，3：历史记录:4)
	
	private Integer entryName;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCaId() {
		return caId;
	}

	public void setCaId(String caId) {
		this.caId = caId;
	}

	public String getSearchUserName() {
		return searchUserName;
	}

	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}

	public Integer getRecordType() {
		return recordType;
	}

	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}

	public Integer getEntryName() {
		return entryName;
	}

	public void setEntryName(Integer entryName) {
		this.entryName = entryName;
	}
	
	
	
	
	
	
}
