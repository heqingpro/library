package com.ipanel.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 图书文本信息
 * @author fangg
 * 2017年8月3日 下午6:01:04
 */
@Entity
@Table(name="entry_file")
@Data
public class EntryFileInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;//主键id
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="entryInfo_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private EntryInfo entryInfo;
	
	@Column(name="file_name")
	private String fileName;//音频上传源文件名称
	
	@Column(name="unique_name")
	private String uniqueName;//音频唯一名称，去系统当前毫秒时间
	
	@Column(name="file_path")
	private String filePath;//音频存储的服务器路径
	
	@Column(name="file_type")
	private String fileType;//音频格式(后缀)
	
	@Column(name="file_size")
	private String fileSize;//音频占内存大小
	
	@Column(name="file_content")
	private byte[] fileContent;//原始文件的二进制
	
	@Column(name="file_width")
	private Integer fileWidth;//音频宽度
	
	
	@Column(name="file_height")
	private Integer fileHeight;//音频高度
	
	@Column(name="add_time")
	private String addTime;//上传时间
	
	@Column(name="operate_userId")
	private Integer operateUserId;//操作管理员id

}