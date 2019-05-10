package com.ipanel.web.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "entry_type")
public class EntryTypeInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// 主键id

	@Column(name = "typeName")
	private String typeName;

	@Column(name = "remark")
	private String remark;
	
	@Column(name="addTime")
	private String addTime;
	
	@Column(name="imageName")
	private String imageName;
	
	@Column(name="uniqueImageName")
	private String uniqueImageName;
	
	@Column(name="imagePath")
	private String imagePath;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "entryTypeInfo")	
	private List<EntryInfo> entryList;
	
	
	
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
	
	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
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

	public List<EntryInfo> getEntryList() {
		return entryList;
	}

	public void setEntryList(List<EntryInfo> entryList) {
		this.entryList = entryList;
	}

	
	
	
	
	

}
