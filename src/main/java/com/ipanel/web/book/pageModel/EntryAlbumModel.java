package com.ipanel.web.book.pageModel;

import java.util.List;

/**
 * @author fangg
 * 2017年5月13日 下午2:23:34
 */
public class EntryAlbumModel {
	
	private Integer entryId; //图书id

	private List<EntryImageModel> entryImages;//图书详情照片集合
	
	

	public Integer getEntryId() {
		return entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	public List<EntryImageModel> getEntryImages() {
		return entryImages;
	}

	public void setEntryImages(List<EntryImageModel> entryImages) {
		this.entryImages = entryImages;
	}


	
	
	
}
