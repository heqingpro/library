package com.ipanel.web.contentProvider.pageModel;


public class AppModel  {
	
	private int page; //第几页
	
	private int rows; //每页显示的数量
	
	private Integer id; //内容提供商id
	
	private String name ;//专区内容提供商名
	
	private String appName ;//专区内容提供商名
	
	private String enName ;//英文名称
	
	private String remark ;//补充说明

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

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
}
