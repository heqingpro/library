package com.ipanel.web.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ipanel.web.common.dao.BaseDao;
import com.ipanel.web.entity.AccessCount;
import com.ipanel.web.system.controller.SystemController;
import com.ipanel.web.system.service.AccessCountService;

/**
 * 
 *
 * @author: lvchao
 * @mail: chao9038@hnu.edu.cn
 * @time: 2018年4月27日下午6:30:46
 */
@Service
public class AccessCountServiceImpl implements AccessCountService {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemController.class);
	
	@Resource
	private BaseDao baseDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<AccessCount> getAccessCounts() {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			HashMap<String, AccessCount> map = new HashMap<String, AccessCount>();
			for(int i=0; i<=14; i++) {
				Calendar ca = Calendar.getInstance();
				ca.add(Calendar.DATE, -i);
				Date date = ca.getTime();
				map.put(sdf.format(date), new AccessCount(sdf.format(date), 0));
			}
			
			Calendar ca = Calendar.getInstance();
			ca.add(Calendar.DATE, -14);
			Date date = ca.getTime();
			String hql = " from AccessCount a where a.id >='"+ sdf.format(date) +"' and a.id <= current_date() ";
			List<AccessCount> counts = (List<AccessCount>) baseDao.query(hql);
			
			for(AccessCount ac : counts) {
				map.put(ac.getId(), ac);
			}
			List<AccessCount> results = new ArrayList<AccessCount>(map.values());
			return sortList(results);
			
		} catch (Exception e) {
			logger.error("Get 15-day access counts failed!", e);
			return null;
		}
	}
	
	
	/**
	 * 按日期进行排序
	 *
	 * @author lvchao
	 * @createtime 2018年4月27日 下午6:31:04
	 *
	 * @param list
	 * @return
	 */
	public List<AccessCount> sortList(List<AccessCount> list) {
		Collections.sort(list, new Comparator<AccessCount>() {
			
			@Override
			public int compare(AccessCount ac1, AccessCount ac2) {
				return ac1.getId().compareTo(ac2.getId());
			}
		});
		return list;
	}

}
