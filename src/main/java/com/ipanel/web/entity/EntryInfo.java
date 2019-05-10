package com.ipanel.web.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="entry")
@Data
public class EntryInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;//主键id
	
	//套数系列
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="entryType_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private EntryTypeInfo entryTypeInfo;  
	
	//语种关联
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="lang_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private AngleInfo angleInfo;//图书绑定语种，(代表语种)

	//关联的运营商
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="app_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private AppInfo appInfo; //所属的内容提供商Id,一对多
	
	//附加信息
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="attachInfo_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private EntryAttachInfo entryAttachInfo;

	@Column(name="title")
	private String title;//图书名称

	@Column(name="short_name")
	private String shortName;//长度200限制
	
	@Column(name="author")
	private String author;//作者
	
	@Column(name="description")
	private String description;//简介
	
	@Column(name="pub_org")
	private String pubOrg;// 出版机构
	
	@Column(name="editor")
	private String editor; //编者
	
	@Column(name="original_author")
	private String originalAuthor; //原著作者
	
	@Column(name="years_id")
	private Integer yearsId; //  年代id: 1古代 2近现代 3现当代
	
	@Column(name="uid")
	private String uid;//图书第三方id 
	
	@Column(name="global_guid")
	private String global_guid;//图书唯一编码 
	
	@Column(name="page_count")
	private Integer pageCount; //页数
	
	@Column(name="width")
	private Integer width;//宽度
	
	@Column(name="height")
	private Integer height;//高度
	
	@Column(name="format_type")
	private Integer formatType; //格式:1无声 2有声,3 txt（三秦）
	
	@Column(name="path")
	private String path;//文件路径
	
	@Column(name="cover_image_id")
	private Integer coverImageId;//图书封面
	
	@Column(name="cover_image")
	private String coverImage;//图书封面地址
	
	@Column(name="cover_thumbNail_id")
	private Integer coverThumbNailId;//图书封面小图
	
	@Column(name="cover_thumbNail")
	private String coverThumbNail;//图书封面小图地址
	
	@Column(name="save_path")
	private String savePath;//图书保存路径
	
	@Column(name="content")
	private String content;//文本类图书的内容
	
	@Column(name="content_path")
	private String contentPath;//文本类的路径
	
	@Column(name="audio_path")
	private String audioPath;//文本类的路径
	
	@Column(name="edition_type")
	private Integer editionType;//版本类型
	
	@Column(name="is_prize")
	private Integer isPrize;//是否为获奖作品
	
	@Column(name="add_time")
	private String addTime;//添加时间
	
	@Column(name="modify_time")
	private String modifyTime; // 更新时间
	
	@Column(name="operate_userId")
	private Integer operateUserId;//操作人
	
	@Column(name="rank_number")
	private Integer rankNumber;//排序序号
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "entryInfo")	
	private List<EntryImageInfo> entryImageList;  //拥有的海报信息
		
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "entryInfo")	
	private List<EntryToNode> entryToNodeList;   //归属的图书分类信息
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "entryInfo")	
	private EntryFileInfo entryFile;   //归属的图书文件信息
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "entryInfo")	
	private EntryAudioInfo entryAudio;  //拥有的音频信息，可能有多个
	

}
