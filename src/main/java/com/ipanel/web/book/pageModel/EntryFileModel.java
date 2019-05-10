package com.ipanel.web.book.pageModel;

import com.ipanel.web.entity.EntryAudioInfo;
import com.ipanel.web.entity.EntryFileInfo;
import com.ipanel.web.entity.EntryImageInfo;
import com.ipanel.web.utils.FileOperation;

/**
 * @author fangg
 * 2017年5月10日 下午12:09:10
 */
public class EntryFileModel {
	
	private Integer id;
	
	private String fileName;
	
	private String uniqueName;
	
	private String filePath;
	
	public static void parseEntityToModel(EntryFileInfo entryFIleInfo,EntryFileModel entryAudioModel) {
		entryAudioModel.setId(entryFIleInfo.getId());
		entryAudioModel.setFileName(entryFIleInfo.getFileName());
		entryAudioModel.setUniqueName(entryFIleInfo.getUniqueName());
		entryAudioModel.setFilePath(FileOperation.getTextFilePath(entryFIleInfo.getUniqueName()));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	
	

	
	
}
