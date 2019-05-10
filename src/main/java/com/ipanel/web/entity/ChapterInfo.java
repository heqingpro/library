package com.ipanel.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @author fangg
* 2017年12月28日 上午11:12:21
*/

public class ChapterInfo {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;//主键id
	
	
	@Column(name="title")
	private String title;//章节标题

	@Column(name="short_name")
	private String shortName;//长度200限制
		
	@Column(name="description")
	private String description;//简介
	
	

}
