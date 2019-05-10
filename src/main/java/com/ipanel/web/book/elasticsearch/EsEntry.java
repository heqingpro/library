package com.ipanel.web.book.elasticsearch;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ipanel.web.entity.EntryInfo;


/**
 * 
 * @author: lvchao
 * @mail: chao9038@hnu.edu.cn
 * @time: 2018年4月16日下午8:16:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="es_entry")
public class EsEntry {
	
	@Id
	private Integer id;//主键id
	
	//套数系列
	@Column(name="entry_type_id")
	private Integer entry_type_id;  
	
	@Column(name="lang_id")
	private Integer lang_id;//图书绑定语种，(代表语种)
	
	@Column(name="app_id")
	private Integer app_id; //所属的内容提供商Id,一对多
	
	//附加信息
	@Column(name="attach_info_id")
	private Integer attach_info_id;

	@Column(name="title")
	private String title;//图书名称

	@Column(name="short_name")
	private String short_name;//长度200限制
	
	@Column(name="author")
	private String author;//作者
	
	@Column(name="description")
	private String description;//简介
	
	@Column(name="pub_org")
	private String pub_org;// 出版机构
	
	@Column(name="editor")
	private String editor; //编者
	
	@Column(name="original_author")
	private String original_author; //原著作者
	
	@Column(name="years_id")
	private Integer years_id; //  年代id: 1古代 2近现代 3现当代
	
	@Column(name="uid")
	private String uid;//图书第三方id 
	
	@Column(name="global_guid")
	private String global_guid;//图书唯一编码 
	
	@Column(name="page_count")
	private Integer page_count; //页数
	
	@Column(name="width")
	private Integer width;//宽度
	
	@Column(name="height")
	private Integer height;//高度
	
	@Column(name="format_type")
	private Integer format_type; //格式:1无声 2有声,3 txt（三秦）
	
	@Column(name="path")
	private String path;//文件路径
	
	@Column(name="cover_image_id")
	private Integer cover_image_id;//图书封面
	
	@Column(name="cover_image")
	private String cover_image;//图书封面地址
	
	@Column(name="cover_thumb_nail_id")
	private Integer cover_thumb_nail_id;//图书封面小图
	
	@Column(name="cover_thumb_nail")
	private String cover_thumb_nail;//图书封面小图地址
	
	@Column(name="save_path")
	private String save_path;//图书保存路径
	
	@Column(name="content")
	private String content;//文本类图书的内容
	
	@Column(name="content_path")
	private String content_path;//文本类的路径
	
	@Column(name="audio_path")
	private String audio_path;//文本类的路径
	
	@Column(name="edition_type")
	private Integer edition_type;//版本类型
	
	@Column(name="is_prize")
	private Integer is_prize;//是否为获奖作品
	
	@Column(name="add_time")
	private String add_time;//添加时间
	
	@Column(name="modify_time")
	private String modify_time; // 更新时间
	
	@Column(name="operate_user_id")
	private Integer operate_user_id;//操作人
	
	@Column(name="rank_number")
	private Integer rank_number;//排序序号

	public EsEntry(EntryInfo e) {
		this.id = e.getId();
		this.entry_type_id = e.getEntryTypeInfo().getId();
		this.lang_id = e.getAngleInfo().getId();
		this.app_id = e.getAppInfo().getId();
		this.attach_info_id = e.getEntryAttachInfo().getId();
		this.title = e.getTitle();
		this.short_name = e.getShortName();
		this.author = e.getAuthor();
		this.description = e.getDescription();
		this.pub_org = e.getPubOrg();
		this.editor = e.getEditor();
		this.original_author = e.getOriginalAuthor();
		this.years_id = e.getYearsId();
		this.uid = e.getUid();
		this.global_guid = e.getGlobal_guid();
		this.page_count = e.getPageCount();
		this.width = e.getWidth();
		this.height = e.getHeight();
		this.format_type = e.getFormatType();
		this.path = e.getPath();
		this.cover_image_id = e.getCoverImageId();
		this.cover_image = e.getCoverImage();
		this.cover_thumb_nail_id = e.getCoverThumbNailId();
		this.cover_thumb_nail = e.getCoverThumbNail();
		this.save_path = e.getSavePath();
		this.content = e.getContent();
		this.content_path = e.getContentPath();
		this.audio_path = e.getAudioPath();
		this.edition_type = e.getEditionType();
		this.is_prize = e.getIsPrize();
		this.add_time = e.getAddTime();
		this.modify_time = e.getModifyTime();
		this.operate_user_id = e.getOperateUserId();
		this.rank_number = e.getRankNumber();
	}

}
