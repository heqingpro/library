package com.ipanel.web.book.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ipanel.web.book.pageModel.EntryAlbumModel;
import com.ipanel.web.book.pageModel.EntryModel;
import com.ipanel.web.book.pageModel.EntrySearchModel;
import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.entity.SysUser;

public interface IEntryService {
	
	/**
	 * 创建图书索引
	 * 
	 * 注意：创建之前会删除已存在的同名索引，创建成功后，自动同步数据库数据到ES
	 *
	 * @author lvchao
	 * @createtime 2018年4月17日 下午4:01:36
	 *
	 * @return true表示成功，false表示失败
	 */
	 boolean recreateBookIndex();

	 PageDataModel queryEntryList(SysUser sysUser, EntryModel model);

	 String addEntry(SysUser user,EntryModel model,CommonsMultipartFile coverImage,String coverImageFileType,CommonsMultipartFile coverThumbNail,String coverThumbNailType,CommonsMultipartFile entryImportFile,String fileType, CommonsMultipartFile audioFile, String audioType);

	 String editEntry(SysUser user,EntryModel model,CommonsMultipartFile coverImage,String coverImageFileType,CommonsMultipartFile coverThumbNail,String coverThumbNailType,CommonsMultipartFile entryImportFile,String fileType,CommonsMultipartFile audioFile,String audioType);

	 void deleteEntry(SysUser user, String ids);

	 EntryModel getEntryDetail(Integer id);

	 EntryAlbumModel getEntryAlbums(Integer entryId);

	 String editEntryAllAlbums(SysUser user, CommonsMultipartFile[] albumImages, Integer id);
	
	 String replaceEntryImage(SysUser user, CommonsMultipartFile file, Integer imageId, Integer position);

	 String deleteEntryImage(SysUser attribute, Integer imageId);

	 PageDataModel queryEntryByEntryType(SysUser sysUser, EntrySearchModel model);

	boolean changeEntryRankNumber(SysUser attribute, EntryModel model);

}
