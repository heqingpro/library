package com.ipanel.web.entity;

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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 图书音频信息
 * @author fangg
 * 2017年8月3日 下午6:01:04
 */
@Entity
@Table(name="entry_audio")
public class EntryAudioInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;//主键id
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="entryInfo_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private EntryInfo entryInfo;
	
	@Column(name="audio_name")
	private String audioName;//音频上传源文件名称
	
	@Column(name="unique_name")
	private String uniqueName;//音频唯一名称，去系统当前毫秒时间
	
	@Column(name="audio_path")
	private String audioPath;//音频存储的服务器路径
	
	@Column(name="audio_type")
	private String audioType;//音频格式(后缀)
	
	@Column(name="audio_size")
	private String audioSize;//音频占内存大小
	
	@Column(name="audio_content")
	private byte[] audioContent;//音频内容，二进制美容
	
	@Column(name="audio_width")
	private Integer audioWidth;//音频宽度
	
	@Column(name="audio_Height")
	private Integer audioHeight;//音频高度
	
	@Column(name="add_time")
	private String addTime;//上传时间
	
	@Column(name="operate_userId")
	private Integer operateUserId;//操作管理员id

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EntryInfo getEntryInfo() {
		return entryInfo;
	}

	public void setEntryInfo(EntryInfo entryInfo) {
		this.entryInfo = entryInfo;
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

	public String getAudioType() {
		return audioType;
	}

	public void setAudioType(String audioType) {
		this.audioType = audioType;
	}

	public String getAudioSize() {
		return audioSize;
	}

	public void setAudioSize(String audioSize) {
		this.audioSize = audioSize;
	}

	public byte[] getAudioContent() {
		return audioContent;
	}

	public void setAudioContent(byte[] audioContent) {
		this.audioContent = audioContent;
	}

	public Integer getAudioWidth() {
		return audioWidth;
	}

	public void setAudioWidth(Integer audioWidth) {
		this.audioWidth = audioWidth;
	}

	public Integer getAudioHeight() {
		return audioHeight;
	}

	public void setAudioHeight(Integer audioHeight) {
		this.audioHeight = audioHeight;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public Integer getOperateUserId() {
		return operateUserId;
	}

	public void setOperateUserId(Integer operateUserId) {
		this.operateUserId = operateUserId;
	}

	public String getAudioName() {
		return audioName;
	}

	public void setAudioName(String audioName) {
		this.audioName = audioName;
	}
	
	
}
