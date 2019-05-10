package com.ipanel.web.system.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseInfomation {
	private String ip;
	private String hostName;
	
	private String osName;
	private String osArch;
	private String osVersion;
	
	private String jdkVersion; //JDK版本
	private String jdkVendor; //JDK供应商
}
