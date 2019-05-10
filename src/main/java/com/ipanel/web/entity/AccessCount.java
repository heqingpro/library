package com.ipanel.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="access_count")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessCount {
	
	@Id
	private String id;
	
	@Column(name="count")
	private Integer count = 0;

}