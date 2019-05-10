package com.ipanel.web.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author fangg
 * 2017年6月21日 上午9:50:39
 */

public class RedisTest {

	@Test
	public void test(){
		Jedis jedis = new Jedis("192.168.18.66");
		jedis.set("greeting","hello,world!");		
		System.out.println(jedis.get("greeting"));
		jedis.set("name", "欢迎使用redis缓存");
		String name=jedis.get("name");
		System.out.println(name+"============");
		
	}
}
