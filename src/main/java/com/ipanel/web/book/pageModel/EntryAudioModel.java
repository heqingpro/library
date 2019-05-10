package com.ipanel.web.book.pageModel;

import com.ipanel.web.entity.EntryAudioInfo;
import com.ipanel.web.entity.EntryImageInfo;
import com.ipanel.web.utils.FileOperation;

/**
 * @author fangg
 * 2017年5月10日 下午12:09:10
 */
public class EntryAudioModel {
	
	private Integer id;
	
	private String audioName;
	
	private String uniqueName;
	
	private String audioPath;
	
	public static void parseEntityToModel(EntryAudioInfo entryAudioInfo,EntryAudioModel entryAudioModel) {
		entryAudioModel.setId(entryAudioInfo.getId());
		entryAudioModel.setAudioName(entryAudioInfo.getAudioName());
		entryAudioModel.setUniqueName(entryAudioInfo.getUniqueName());
		entryAudioModel.setAudioPath(FileOperation.getAudioFilePath(entryAudioInfo.getUniqueName()));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAudioName() {
		return audioName;
	}

	public void setAudioName(String audioName) {
		this.audioName = audioName;
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getAudioPath() {
		return audioPath;
	}

	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}

	

	
	
}
