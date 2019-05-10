package com.ipanel.web.system.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipanel.web.system.base.SystemUtils;
import com.ipanel.web.system.controller.resp.BaseResp;
import com.ipanel.web.system.service.impl.AccessCountServiceImpl;

@Controller
@RequestMapping("/systemController")
public class SystemController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);
	
	@Autowired
	private AccessCountServiceImpl accessCountService;
	
	@RequestMapping("/getCountInfomations")
	@ResponseBody
	public BaseResp getSysInfos(HttpSession session) {
		try {
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("base", SystemUtils.base());
			data.put("cpus", SystemUtils.cpus());
			data.put("memory", SystemUtils.memory());
			data.put("disks", SystemUtils.disks());
			data.put("counts", accessCountService.getAccessCounts());
			return new BaseResp(0, "Success", data);
			
		} catch(Exception e) {
			LOGGER.error("Get the infomations failed!", e);
			return new BaseResp(1, "Failed");
		}
	}
	
}
