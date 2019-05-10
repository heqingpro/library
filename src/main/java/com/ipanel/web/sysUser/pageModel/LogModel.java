package com.ipanel.web.sysUser.pageModel;

public class LogModel {

	private int page;
	
	private int rows;
	
	private Integer id;

	private String moduleName;

	private String operatingFunction;

	private String operatingDesc;
	
	private String operatingDate;
	
	private String userName;
	
	public LogModel() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getOperatingFunction() {
		return operatingFunction;
	}

	public void setOperatingFunction(String operatingFunction) {
		this.operatingFunction = operatingFunction;
	}

	public String getOperatingDesc() {
		return operatingDesc;
	}

	public void setOperatingDesc(String operatingDesc) {
		this.operatingDesc = operatingDesc;
	}

	public String getOperatingDate() {
		return operatingDate;
	}

	public void setOperatingDate(String operatingDate) {
		this.operatingDate = operatingDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	
}
