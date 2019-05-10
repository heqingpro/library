package com.ipanel.web.system.controller.resp;


/**
 * 
 * @author: lvchao
 * @mail: chao9038@hnu.edu.cn
 * @time: 2018年1月16日下午4:05:14
 */
public class BaseResp {
	private Integer code = 0;
	private String msg = "success";
	
	private Object data;
	
	public BaseResp() {
		super();
	}

	public BaseResp(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public BaseResp(Integer code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
