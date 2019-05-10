package com.ipanel.web.sysUser.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping()
public class IndexController {

	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		return "index";
	}
	
	@RequestMapping("/loginError")
	public String loginError(ModelMap modelMap){
		modelMap.put("login_error", "*用户名或密码错误");
		return "login";
	}
}
