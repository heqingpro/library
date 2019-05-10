package com.ipanel.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemInfomation {
	//物理内存
	private Long memTotal;
	private Long memUsed;
	private Long memFree;
	private Double memUsedPercent;
	private Double memFreePercent;
	
	//交换区
	private Long swapTotal;
	private Long swapUsed;
	private Long swapFree;
	private Double swapUsedPercent;
	private Double swapFreePercent;
}
