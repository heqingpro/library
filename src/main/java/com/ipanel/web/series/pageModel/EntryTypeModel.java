package com.ipanel.web.series.pageModel;

public class EntryTypeModel {
	
	private Integer page;
	
	private Integer rows;
	
	private Integer id;
	
	private String typeName; //分类名称
	
	private String imageName;
	
	private String uniqueImageName;
	
	private String imageUrl;
	
	private String text; //分类名称，方便页面识别
	
	private String remark;  //备注
	
	private String addTime; // 添加时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getUniqueImageName() {
		return uniqueImageName;
	}

	public void setUniqueImageName(String uniqueImageName) {
		this.uniqueImageName = uniqueImageName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	
	
	

}
