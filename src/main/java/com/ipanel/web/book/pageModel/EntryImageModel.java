package com.ipanel.web.book.pageModel;

import com.ipanel.web.entity.EntryImageInfo;
import com.ipanel.web.utils.FileOperation;

/**
 * @author fangg
 * 2017年5月10日 下午12:09:10
 */
public class EntryImageModel {
	
	private Integer id;
	
	private String imageName;
	
	private String uniqueName;
	
	private String imagePath;
	
	private Integer position;// 0 :列表海报，1：详情海报
	
	
	public static void parseEntityToModel(EntryImageInfo entryImageInfo,EntryImageModel entryImageModel) {
		entryImageModel.setId(entryImageInfo.getId());
		entryImageModel.setImageName(entryImageInfo.getImageName());
		entryImageModel.setUniqueName(entryImageInfo.getUniqueName());
		entryImageModel.setImagePath(FileOperation.getImagePath(entryImageInfo.getUniqueName()));
		entryImageModel.setPosition(entryImageInfo.getPosition());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	
	
}
