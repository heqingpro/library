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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="nodeImage")
public class NodeImageInfo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="nodeInfo_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private NodeInfo nodeInfo;
	
	@Column(name="imageName")
	private String imageName;//图片名称
	
	@Column(name="uniqueName")
	private String uniqueName;//图片唯一名称，去系统当前毫秒时间
	
	@Column(name="imagePath")
	private String imagePath;//图片存储的服务器路径
	
	@Column(name="imageType")
	private String imageType;//图片格式(后缀)
	
	@Column(name="imageSize")
	private String imageSize;//图片占内存大小
	
	@Column(name="imageContent")
	private byte[] imageContent;//图片内容，二进制美容
	
	@Column(name="imageWidth")
	private Integer imageWidth;//图片宽度
	
	@Column(name="imageHeight")
	private Integer imageHeight;//图片高度
	
	@Column(name="status")
	private String status;//入口图片，相册图片
	
	@Column(name="deviceType")
	private String deviceType; //终端类型
	
	@Column(name="modifyTime")
	private String modifyTime;//编辑时间
	
	@Column(name="operateUserId")
	private String operateUserId;//操作管理员id

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public NodeInfo getNodeInfo() {
		return nodeInfo;
	}

	public void setNodeInfo(NodeInfo nodeInfo) {
		this.nodeInfo = nodeInfo;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getOperateUserId() {
		return operateUserId;
	}

	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	
}
