package com.ipanel.web.system.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiskInfomation {
	private String devName; //盘符名称
	private String dirName; //盘符路劲
	private String sysTypeName; //盘符文件系统
	
	private Long total; //总容量
	private Long free; //剩余容量
	private Long used; //已用容量
	private Long avail; //剩余可用容量
	private Double usedPercent; //已用比例
	private Double freePercent; //剩余比例
	private Double availPercent; //剩余可用比例
	private Double unAvailPercent; //剩余损坏比例
}
