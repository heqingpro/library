package com.ipanel.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.swing.text.Position;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


@Entity
@Table(name="entry_image")
public class EntryImageInfo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;//主键id
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="entryInfo_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private EntryInfo entryInfo;
	
	@Column(name="image_name")
	private String imageName;//图片名称
	
	@Column(name="unique_name")
	private String uniqueName;//图片唯一名称，去系统当前毫秒时间
	
	@Column(name="image_path")
	private String imagePath;//图片存储的服务器路径
	
	@Column(name="image_type")
	private String imageType;//图片格式(后缀)
	
	@Column(name="image_size")
	private String imageSize;//图片占内存大小
	
	@Column(name="image_content")
	private byte[] imageContent;//图片内容，二进制美容
	
	@Column(name="image_width")
	private Integer imageWidth;//图片宽度
	
	@Column(name="image_height")
	private Integer imageHeight;//图片高度
	
	@Column(name="position")
	private Integer position; //0:封面信息，1：图册信息
	
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

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getImageSize() {
		return imageSize;
	}

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

	public byte[] getImageContent() {
		return imageContent;
	}

	public void setImageContent(byte[] imageContent) {
		this.imageContent = imageContent;
	}

	public Integer getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	public Integer getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(Integer imageHeight) {
		this.imageHeight = imageHeight;
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

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	
	
	
	
	
	
	
	
	
	
	
}
