package com.ipanel.web.book.controller.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author: lvchao
 * @mail: chao9038@hnu.edu.cn
 * @time: 2018年1月16日下午4:05:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResp {
	private Integer code = 0;
	private String msg = "success";
}
