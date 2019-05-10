package com.ipanel.web.common.redis;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;






import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipanel.web.category.pageModel.NodeTreeModel;
import com.ipanel.web.common.Toolkit;
import com.ipanel.web.entity.NodeInfo;
import com.ipanel.webapp.framework.util.Log;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

@Service
public class RedisUtils {
	@Autowired
	private JedisPool jedisPool;

	private String TAG = RedisUtils.class.getName();

	public static String DEVICE_REGULAR_INFORM_KEY = "device_regular_inform";

	public static String DEVICE_TASK_KEY = "device_task";

	public static String PLUGIN = "plugin";

	public static String UPGRADE = "upgrade";

	public static String DEVICE_PLUGIN = "device_plugin";

	public static String DEVICE_USER_KEY = "device_user";

	public static String DEVICE_KEY = "device";

	public static String EXCHANGE_GOODS_KEY = "exchange_goods";

	public static String LOGIN_INFO_KEY = "login_info";

	public static String EXCHANGE_GOODS_HISTORY_KEY = "exchange_goods_history";

	public static String INTERACTIVE_ITEM = "interactive_item";

	public static String DEVICE_BAND_LEND_KEY = "device_band_lend";

	public static String DEVICE_BAND_LEND_HISTORY_KEY = "device_band_lend_history";

	public static String DEVICE_BAND_BORROW_KEY = "device_band_borrow";

	public static String DEVICE_BAND_BORROW_HISTORY_KEY = "device_band_borrow_history";

	public static String STB_CITY_AREA = "stb_city_area";

	public static String STB_AREA = "stb_area";

	public static String INTERACTIVE_REWARD = "interactive_reward";

	public static String DEVICE_LOGIN_PAGE_URL = "device_login_page_url";

	public static String DEVICE_DEFAULT_LOGIN_PAGE_URL = "device_default_login_page_url";

	public static String TODAY_EARN_CLOUD_MONEY = "today_earn_cloud_money";

	public static String SYSTEM_CONFIG_ITEM = "system_config_item";
	
	//分类缓存
	public static String NODE_INFO_KEY="node_info"; 
	//节目缓存
	public static String entry_Info_KEY="entry_info";

	/*public Map<String, String> getRegularInformData() {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			Map<String, String> dataMap = jedis
					.hgetAll(DEVICE_REGULAR_INFORM_KEY);
			jedis.del(DEVICE_REGULAR_INFORM_KEY);
			return dataMap;
		} catch (JedisException e) {
			Log.e(TAG, "***getRegularInformData throw JedisException:" + e);
			broken = this.handleJedisException(e);
			return null;
		} finally {
			this.closeResource(jedis, broken);
		}
	}*/
	/*public boolean saveTask(List<DeviceTask> taskList) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			for (DeviceTask task : taskList) {
				JSONObject obj = new JSONObject();
				obj.put("task_id", task.getId());
				obj.put("mac", task.getDeviceMac());
				obj.put("task_type", task.getTaskType());
				obj.put("param", task.getTaskParameter());
				// jedis.lpushx(SafeEncoder.encode(DEVICE_TASK_KEY),
				// SafeEncoder.encode(obj.toString()));
				jedis.lpush(DEVICE_TASK_KEY, obj.toString());
			}
			return true;
		} catch (JedisException e) {
			Log.e(TAG, "***saveTask throw JedisException:" + e);
			broken = this.handleJedisException(e);
			return false;
		} catch (Exception e) {
			Log.e(TAG, "***saveTask throw Exception:" + e);
			return false;
		} finally {
			this.closeResource(jedis, broken);
		}
	}*/

	

	/*public boolean savePlugin(List<Plugin> plugins, boolean needDelAll) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			if (needDelAll)
				jedis.del(PLUGIN);
			for (Plugin plugin : plugins) {
				JSONObject obj = new JSONObject();
				obj.put("installUrl",
						Toolkit.processNull(plugin.getInstallUrl()));
				obj.put("originalName",
						Toolkit.processNull(plugin.getOriginalName()));
				obj.put("iconUrl", Toolkit.processNull(plugin.getIconUrl()));
				obj.put("pluginDesc",
						Toolkit.processNull(plugin.getPluginDesc()));
				obj.put("paramDetail",
						Toolkit.processNull(plugin.getParamDetail()));
				obj.put("name", Toolkit.processNull(plugin.getName()));
				obj.put("cnName", Toolkit.processNull(plugin.getCnName()));
				obj.put("version", Toolkit.processNull(plugin.getVersion()));
				obj.put("developer", Toolkit.processNull(plugin.getDeveloper()));
				obj.put("category", Toolkit.processNull(plugin.getCategory()));
				Long result = jedis.hset(PLUGIN, plugin.getName() + "_"
						+ plugin.getVersion(), obj.toString());
				if (result != 1l)
					throw new Exception("hset result is success,result:"
							+ result);
			}
			return true;
		} catch (JedisException e) {
			Log.e(TAG, "***savePlugin throw JedisException:" + e);
			broken = this.handleJedisException(e);
			return false;
		} catch (Exception e) {
			Log.e(TAG, "***savePlugin throw Exception:" + e);
			return false;
		} finally {
			this.closeResource(jedis, broken);
		}
	}*/


	public boolean saveDevicePlugin(Map<String, String> dataMap) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			Pipeline p = jedis.pipelined();
			p.del(DEVICE_PLUGIN);
			Set<String> keySet = dataMap.keySet();
			Iterator<String> keyIt = keySet.iterator();
			while (keyIt.hasNext()) {
				String deviceKey = keyIt.next();
				String data = dataMap.get(deviceKey);
				p.hset(DEVICE_PLUGIN, deviceKey, data);
			}
			List<Object> resultList = p.syncAndReturnAll();
			for (Object result : resultList) {
				Long r = (Long) result;
				if (r != 1l)
					throw new Exception(
							"syncAndReturnAll result is not success,result:"
									+ r);
			}
			return true;
		} catch (JedisException e) {
			Log.e(TAG, "***saveDevicePlugin throw Exception:" + e);
			broken = this.handleJedisException(e);
			return false;
		} catch (Exception e) {
			Log.e(TAG, "***saveDevicePlugin throw Exception:" + e);
			return false;
		} finally {
			this.closeResource(jedis, broken);
		}
	}

	

	public boolean saveExchangeGoodsHistory(Map<String, String> exchangeLogMap) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			Pipeline p = jedis.pipelined();
			if (exchangeLogMap != null) {
				// 写入兑换历史记录
				// 清掉缓存中兑换历史记录数据
				boolean isExist = jedis.exists(EXCHANGE_GOODS_HISTORY_KEY);
				p.del(EXCHANGE_GOODS_HISTORY_KEY);
				// 写入历史记录数据
				Set<String> keySet = exchangeLogMap.keySet();
				Iterator<String> itSet = keySet.iterator();
				while (itSet.hasNext()) {
					String account = itSet.next();
					String data = exchangeLogMap.get(account);
					p.hset(EXCHANGE_GOODS_HISTORY_KEY, account, data);
				}
				List<Object> resultList = p.syncAndReturnAll();
				int index = 0;
				for (Object result : resultList) {
					boolean isOk = true;
					Long r = (Long) result;
					// 首先判断删除是否是成功的
					if (index == 0) {
						if (!isExist && r != 0) {
							isOk = false;
						} else if (isExist && r != 1) {
							isOk = false;
						}
					} else if (r != 1l) {
						isOk = false;
					}
					if (!isOk)
						throw new Exception(
								"syncAndReturnAll result is not success,result:"
										+ r);
					index++;
				}
			}
			return true;
		} catch (JedisException e) {
			Log.e(TAG, "***saveExchangeGoodsHistory throw JedisException:" + e);
			broken = this.handleJedisException(e);
			return false;
		} catch (Exception e) {
			Log.e(TAG, "***saveExchangeGoodsHistory throw Exception:" + e);
			return false;
		} finally {
			this.closeResource(jedis, broken);
		}
	}
	/**
	 * 分页查找redis
	 * @author fangg
	 * 2017年6月22日 下午2:33:15
	 * @param startOffset
	 * @param endOffset
	 * @return
	 */
	public String getNode(Integer startOffset,Integer endOffset){
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			String data = jedis.getrange(NODE_INFO_KEY, startOffset, endOffset);
			return Toolkit.checkNull(data);
		} catch (JedisException e) {
			Log.e(TAG, "***getNode(Integer startOffset,Integer endOffset) throw Exception:" + e);
			broken = this.handleJedisException(e);
		} finally {
			this.closeResource(jedis, broken);
		}
		return null;
	}
	/**
	 * 查找node,根据id查找，获取详情信息
	 * @author fangg
	 * 2017年6月21日 下午7:01:30
	 * @param field
	 * @return
	 */
	public String getNode(String field) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			String data = jedis.hget(NODE_INFO_KEY, field);
			return Toolkit.checkNull(data);
		} catch (JedisException e) {
			Log.e(TAG, "***getNode throw Exception:" + e);
			broken = this.handleJedisException(e);
		} finally {
			this.closeResource(jedis, broken);
		}
		return null;
	}
	/**
	 * 保存一条node数据到缓存
	 * @author fangg
	 * 2017年6月21日 下午6:52:19
	 * @param field  应该由父分类id与分类id组合查询
	 * @param value
	 * @return
	 */
	public boolean setNode(String field, String value) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			jedis.hset(NODE_INFO_KEY, field, value);
			return true;   
		} catch (JedisException e) {
			Log.e(TAG, "***setNode throw Exception:" + e);
			broken = this.handleJedisException(e);
		} finally {
			this.closeResource(jedis, broken);
		}
		return false;
	}
	/**
	 * 保存分类到缓存中
	 * @author fangg
	 * 2017年6月21日 下午6:40:14
	 * @param nodesList
	 * @return
	 */
	public boolean saveNodes(List<NodeTreeModel> nodesList) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			// 写入物品数据
			int index = 0;
			for (NodeTreeModel node : nodesList) {
				JSONObject obj = new JSONObject();
				obj=JSONObject.fromObject(node);
			
				jedis.hset(NODE_INFO_KEY, String.valueOf(node.getId()),
						obj.toString());
			}
			return true;
		} catch (JedisException e) {
			Log.e(TAG, "***saveNodes throw JedisException:" + e);
			broken = this.handleJedisException(e);
			return false;
		} catch (Exception e) {
			Log.e(TAG, "***saveNodes throw Exception:" + e);
			return false;
		} finally {
			this.closeResource(jedis, broken);
		}
	}
	/**
	 * 查找所有node
	 * @author fangg
	 * 2017年6月21日 下午7:05:02
	 * @return
	 */
	public Map<String, String> getNodeMap() {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			return jedis.hgetAll(NODE_INFO_KEY);
		} catch (JedisException e) {
			Log.e(TAG, "***getNodeMap throw Exception:" + e);
			broken = this.handleJedisException(e);
		} finally {
			this.closeResource(jedis, broken);
		}
		return null;
	}
	
	/**
	 * 获取分类在缓存中的数据量
	 * @author fangg
	 * 2017年6月21日 下午6:40:29
	 * @return
	 */
	public long countNodes() {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			long len = jedis.hlen(NODE_INFO_KEY);
			return len;
		} catch (JedisException e) {
			Log.e(TAG, "***countNodes throw Exception:" + e);
			broken = this.handleJedisException(e);
			return -1;
		} finally {
			this.closeResource(jedis, broken);
		}
	}
	/**
	 * 删除缓存中的分类
	 * @author fangg
	 * 2017年6月21日 下午6:41:28
	 * @param goodsList
	 * @param delAll
	 * @return
	 */
	public boolean deleteNodes(List<NodeTreeModel> dataList, boolean delAll) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			Pipeline p = jedis.pipelined();
			if (delAll){
				jedis.del(NODE_INFO_KEY);
			}else{
				for (NodeTreeModel data : dataList) {
					p.hdel(NODE_INFO_KEY, data.getId()+"");
				}				
			}
			p.sync();
			return true;
		} catch (JedisException e) {
			Log.e(TAG, "***deleteGoods throw Exception:" + e);
			broken = this.handleJedisException(e);
			return false;
		} finally {
			this.closeResource(jedis, broken);
		}		
	}

	

	

	
	public boolean updateStbCityArea(Map<String, String> dataMap) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			jedis.del(STB_CITY_AREA);
			jedis.hmset(STB_CITY_AREA, dataMap);
			return true;
		} catch (JedisException e) {
			Log.e(TAG, "***updateStbCityArea throw Exception:" + e);
			broken = this.handleJedisException(e);
			return false;
		} finally {
			this.closeResource(jedis, broken);
		}
	}


	

	public boolean setInteractiveItem(Map<String, String> dataMap) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			jedis.del(INTERACTIVE_ITEM);
			jedis.hmset(INTERACTIVE_ITEM, dataMap);
			return true;
		} catch (JedisException e) {
			Log.e(TAG, "***setInteractiveItem throw Exception:" + e);
			broken = this.handleJedisException(e);
		} finally {
			this.closeResource(jedis, broken);
		}
		return false;
	}



	public boolean setAllPluginData(Map<String, String> pluginDataMap,
			Map<String, String> devicePluginDataMap) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			jedis.del(PLUGIN);
			if (pluginDataMap != null && pluginDataMap.size() > 0)
				jedis.hmset(PLUGIN, pluginDataMap);
			jedis.del(DEVICE_PLUGIN);
			if (devicePluginDataMap != null && devicePluginDataMap.size() > 0)
				jedis.hmset(DEVICE_PLUGIN, devicePluginDataMap);
			return true;
		} catch (JedisException e) {
			Log.e(TAG, "***setAllPluginData throw Exception:" + e);
			broken = this.handleJedisException(e);
		} finally {
			this.closeResource(jedis, broken);
		}
		return false;
	}

	


	
	public boolean saveDeviceUser(List<String[]> dataList) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			jedis.del(DEVICE_USER_KEY);
			Pipeline p = jedis.pipelined();
			for (String[] data : dataList) {
				p.hset(DEVICE_USER_KEY, data[0], data[1]);
			}
			List<Object> resultList = p.syncAndReturnAll();
			for (Object result : resultList) {
				Long r = (Long) result;
				if (r != 1l)
					throw new Exception(
							"syncAndReturnAll result is not success,result:"
									+ r);
			}
			return true;
		} catch (JedisException e) {
			Log.e(TAG, "***saveDeviceUser throw JedisException:" + e);
			broken = this.handleJedisException(e);
		} catch (Exception e) {
			Log.e(TAG, "***saveDeviceUser throw Exception:" + e);
		} finally {
			this.closeResource(jedis, broken);
		}
		return false;
	}

	

	

	public boolean saveInteractiveReward(Map<String, String> dataMap) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			jedis.del(INTERACTIVE_REWARD);
			Pipeline p = jedis.pipelined();
			Set<String> keySet = dataMap.keySet();
			Iterator<String> keyIt = keySet.iterator();
			while (keyIt.hasNext()) {
				String account = (String) keyIt.next();
				String data = dataMap.get(account);
				p.hset(INTERACTIVE_REWARD, account, data);
			}

			List<Object> resultList = p.syncAndReturnAll();
			for (Object result : resultList) {
				Long r = (Long) result;
				if (r != 1l)
					throw new Exception(
							"syncAndReturnAll result is not success,result:"
									+ r);
			}
			return true;
		} catch (JedisException e) {
			Log.e(TAG, "***saveInteractiveReward throw JedisException:" + e);
			broken = this.handleJedisException(e);
		} catch (Exception e) {
			Log.e(TAG, "***saveInteractiveReward throw Exception:" + e);
		} finally {
			this.closeResource(jedis, broken);
		}
		return false;
	}

	public boolean saveInteractiveItem(Map<String, String> dataMap) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			jedis.del(INTERACTIVE_ITEM);
			Pipeline p = jedis.pipelined();
			Set<String> keySet = dataMap.keySet();
			Iterator<String> keyIt = keySet.iterator();
			while (keyIt.hasNext()) {
				String code = (String) keyIt.next();
				String data = dataMap.get(code);
				p.hset(INTERACTIVE_ITEM, code, data);
			}

			List<Object> resultList = p.syncAndReturnAll();
			for (Object result : resultList) {
				Long r = (Long) result;
				if (r != 1l)
					throw new Exception(
							"syncAndReturnAll result is not success,result:"
									+ r);
			}
			return true;
		} catch (JedisException e) {
			Log.e(TAG, "***saveInteractiveItem throw JedisException:" + e);
			broken = this.handleJedisException(e);
		} catch (Exception e) {
			Log.e(TAG, "***saveInteractiveItem throw Exception:" + e);
		} finally {
			this.closeResource(jedis, broken);
		}
		return false;
	}

	public boolean saveDeviceLoginPageUrl(List<String[]> dataList) {
		Log.i(TAG,
				"****enter saveDeviceLoginPageUrl dataList.size:"
						+ dataList.size());
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			Pipeline p = jedis.pipelined();
			for (String[] data : dataList) {
				p.hset(DEVICE_LOGIN_PAGE_URL, data[0], data[1]);
			}
			p.sync();
			return true;
		} catch (JedisException e) {
			Log.e(TAG, "***saveDeviceLoginPageUrl throw Exception:" + e);
			broken = this.handleJedisException(e);
		} finally {
			this.closeResource(jedis, broken);
		}
		return false;
	}

	public boolean saveDeviceDefaultLoginPageUrl(String url) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			jedis.set(DEVICE_DEFAULT_LOGIN_PAGE_URL, url);
			return true;
		} catch (JedisException e) {
			Log.e(TAG, "***saveDeviceDefaultLoginPageUrl throw Exception:" + e);
			broken = this.handleJedisException(e);
		} finally {
			this.closeResource(jedis, broken);
		}
		return false;
	}

	

	public boolean deleteDeviceDefaultLoginPageUrl() {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			jedis.del(DEVICE_DEFAULT_LOGIN_PAGE_URL);
			return true;
		} catch (JedisException e) {
			Log.e(TAG, "***deleteDeviceDefaultLoginPageUrl throw Exception:"
					+ e);
			broken = this.handleJedisException(e);
		} finally {
			this.closeResource(jedis, broken);
		}
		return false;
	}

	public boolean refreshAllDeviceLoginPageUrl(Map<String, String> dataMap) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			jedis.del(DEVICE_LOGIN_PAGE_URL);
			jedis.hmset(DEVICE_LOGIN_PAGE_URL, dataMap);
			return true;
		} catch (JedisException e) {
			Log.e(TAG, "***updateDeviceLoginPageUrl throw Exception:" + e);
			broken = this.handleJedisException(e);
			return false;
		} finally {
			this.closeResource(jedis, broken);
		}
	}

	public boolean saveTodayEarnCloudMoney(Map<String, String> dataMap) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			Pipeline p = jedis.pipelined();
			p.del(TODAY_EARN_CLOUD_MONEY);
			Set<String> keySet = dataMap.keySet();
			Iterator<String> keyIt = keySet.iterator();
			while (keyIt.hasNext()) {
				String account = keyIt.next();
				String data = dataMap.get(account);
				p.hset(TODAY_EARN_CLOUD_MONEY, account, data);
			}
			List<Object> resultList = p.syncAndReturnAll();
			for (Object result : resultList) {
				Long r = (Long) result;
				if (r != 1l)
					throw new Exception(
							"syncAndReturnAll result is not success,result:"
									+ r);
			}
			return true;
		} catch (JedisException e) {
			Log.e(TAG, "***saveTodayEarnCloudMoney throw JedisException:" + e);
			broken = this.handleJedisException(e);
			return false;
		} catch (Exception e) {
			Log.e(TAG, "***saveTodayEarnCloudMoney throw Exception:" + e);
			return false;
		} finally {
			this.closeResource(jedis, broken);
		}
	}

	public boolean saveSystemConfigItem(Map<String, String> dataMap) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			jedis.del(SYSTEM_CONFIG_ITEM);
			Pipeline p = jedis.pipelined();
			Set<String> keySet = dataMap.keySet();
			Iterator<String> keyIt = keySet.iterator();
			while (keyIt.hasNext()) {
				String itemType = (String) keyIt.next();
				String item = dataMap.get(itemType);
				p.hset(SYSTEM_CONFIG_ITEM, itemType, item);
			}

			List<Object> resultList = p.syncAndReturnAll();
			for (Object result : resultList) {
				Long r = (Long) result;
				if (r != 1l)
					throw new Exception(
							"syncAndReturnAll result is not success,result:"
									+ r);
			}
			return true;
		} catch (JedisException e) {
			Log.e(TAG, "***saveSystemConfigItem throw JedisException:" + e);
			broken = this.handleJedisException(e);
		} catch (Exception e) {
			Log.e(TAG, "***saveSystemConfigItem throw Exception:" + e);
		} finally {
			this.closeResource(jedis, broken);
		}
		return false;
	}

	protected boolean handleJedisException(JedisException e) {
		if (e instanceof JedisConnectionException) {
			Log.e(TAG, "Redis connection jedisException,e:" + e);
		} else if (e instanceof JedisDataException) {
			if ((e.getMessage() != null)
					&& (e.getMessage().indexOf("READONLY") != -1)) {
				Log.e(TAG, "Redis are read-only slave,e:" + e);
			} else {
				return false;
			}
		} else {
			Log.e(TAG, "Jedis exception happen,e:" + e);
		}
		return true;
	}

	protected void closeResource(Jedis jedis, boolean broken) {
		try {
			if (jedis == null)
				return;
			if (broken) {
				jedisPool.returnBrokenResource(jedis);
			} else {
				jedisPool.returnResource(jedis);
			}
		} catch (Exception e) {
			Log.e(TAG, "return back jedis failed, will fore close the jedis,e"
					+ e);
			jedis = null;

		}
	}

}
