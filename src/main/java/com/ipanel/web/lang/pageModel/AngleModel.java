package com.ipanel.web.lang.pageModel;

/**
 * @author fangg
 * 2017年5月31日 下午5:32:33
 */
public class AngleModel {
	
	private Integer page;
	
	private Integer rows;
	
	private Integer id;
	
	private String name;

	private String imageName;
	
	private String uniqueImageName;
	
	private String imageUrl;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUniqueImageName() {
		return uniqueImageName;
	}

	public void setUniqueImageName(String uniqueImageName) {
		this.uniqueImageName = uniqueImageName;
	}
	
	
	
}
