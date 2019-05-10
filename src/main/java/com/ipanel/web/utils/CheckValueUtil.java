package com.ipanel.web.utils;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class CheckValueUtil {
	private List<String> messageList = new LinkedList<String>();
	private HttpServletRequest request;

	public CheckValueUtil(HttpServletRequest request) {
		this.request = request;
	}

	public CheckValueUtil() {
	}

	public List<String> getMessageList() {
		return messageList;
	}

	public CheckValueUtil httpEmptyCheck(String name, String key) {
		String value = request.getParameter(key);
		if (value == null || "".equals(value.trim())) {
			messageList.add("参数" + name + "不能为空");
		}
		return this;
	}

	public CheckValueUtil httpEmptyCheck(String name) {
		String value = request.getParameter(name);
		if (value == null || "".equals(value.trim())) {
			messageList.add("参数" + name + "不能为空");
		}
		return this;
	}

	public CheckValueUtil httpNumberCheck(String name) {
		String value = request.getParameter(name);
		try {
			if (value != null && !"".equals(value.trim())) {
				Long.parseLong(value);
			}
		} catch (NumberFormatException e) {
			messageList.add("参数" + name + "不是合法的数字");
		}
		return this;
	}

	public CheckValueUtil httpNumberCheck(String name, String key) {
		String value = request.getParameter(key);
		try {
			if (value != null && !"".equals(value.trim())) {
				Long.parseLong(value);
			}
		} catch (NumberFormatException e) {
			messageList.add("参数" + name + "不是合法的数字");
		}
		return this;
	}

	public CheckValueUtil emptyCheck(String name, String value) {
		if (value == null||"".equals(value.trim())) {
			messageList.add("参数key=" + name + ",value不能为空");
		}
		return this;
	}
	
	public CheckValueUtil emptyCheck(String name, Integer value) {
		if (value == null) {
			messageList.add("参数key=" + name + ",value不能为空");
		}
		return this;
	}

	public CheckValueUtil NumberCheck(String name, String value) {
		try {
			if (value != null && !"".equals(value.trim())) {
				Long.parseLong(value);
			}
		} catch (NumberFormatException e) {
			messageList.add("参数" + name + "不是合法的数字");
		}
		return this;
	}

	public String NumberCheckForZero(String name, int value) {
		if (value == 0) {
			return name;
		} else
			return null;
	}
}
