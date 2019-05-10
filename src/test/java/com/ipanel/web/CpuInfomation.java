package com.ipanel.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpuInfomation {
	private Integer id; //第几核
	private Integer mHz; //频率，单位MHz
	private String vendor; //生产商
	private String model; //类别
	
	private Double user; //用户使用率
	private Double sys; //系统使用率
	private Double wait; //当前等待率
	private Double nice; //当前错误率
	private Double idle; //当前空闲率
	private Double combined; //总的使用率
}
