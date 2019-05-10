package com.ipanel.web.sysUser.pageModel;

public class SysUserModel {
	
	private int page;
	
	private int rows;

	private Integer id;

	private String userName;

	private String phone;

	private String email;

	private String createTime;

	private String userTypeInfo;

	private String roleNames;
	
	private String roleIds;
	
	private String password;
	
	private String appNames;
	
	private String appIds;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUserTypeInfo() {
		return userTypeInfo;
	}

	public void setUserTypeInfo(String userTypeInfo) {
		this.userTypeInfo = userTypeInfo;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAppNames() {
		return appNames;
	}

	public void setAppNames(String appNames) {
		this.appNames = appNames;
	}

	public String getAppIds() {
		return appIds;
	}

	public void setAppIds(String appIds) {
		this.appIds = appIds;
	}
	

}
