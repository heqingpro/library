package com.ipanel.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 语种
 * @author fangg
 * 2017年5月31日 下午5:12:38
 */
@Entity
@Table(name="angle")
public class AngleInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="imageName")
	private String imageName;
	
	@Column(name="uniqueImageName")
	private String uniqueImageName;
	
	@Column(name="imagePath")
	private String imagePath;

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

	public String getUniqueImageName() {
		return uniqueImageName;
	}

	public void setUniqueImageName(String uniqueImageName) {
		this.uniqueImageName = uniqueImageName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	
	
	
	
	

}
