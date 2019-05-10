package com.ipanel.web.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.ipanel.web.utils.Constants;
import com.ipanel.webapp.framework.util.Log;
import com.ipanel.webapp.framework.util.TimeUtil;

public class Toolkit {

	private static String TAG = "Toolkit";

	public static Object processNull(Object data) {
		return data == null ? "" : data;
	}

	public static String checkNull(String data) {
		return data == null ? "" : data;
	}
	
	public static String encodeByMD5(String originString) {
		if (originString == null)
			return null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] results = md5.digest(originString.getBytes());
			return byteArrayToHexString(results);
		} catch (Exception e) {
			return null;
		}
	}

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		String[] hexDigists = { "0", "1", "2", "3", "4", "5", "6", "7", "8",
				"9", "A", "B", "C", "D", "E", "F" };
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigists[d1] + hexDigists[d2];
	}
	
	public static Document getXMLDocument(InputStream is) throws Exception {
		byte[] dataByte = new byte[is.available()];
		is.read(dataByte);
		String data = new String(dataByte, "UTF-8");
		data = data.replaceAll("&", "&amp;");
		is.close();
		Log.i("Toolkit", "***data:" + data);
		ByteArrayInputStream bais = new ByteArrayInputStream(
				data.getBytes("UTF-8"));

		SAXReader saxReader = new SAXReader();
		saxReader.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(String arg0, String arg1)
					throws SAXException, IOException {
				return new InputSource(new StringReader(""));
			}
		});
		return saxReader.read(bais);
	}

	public static String packageBandTaskParam(String mac, String op,
			String method, String downBand, String upBand, String enable,
			String rentMode, String downBandRent, String upBandRent,
			String rentEnable) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Root>");
		sb.append("<User>").append(mac).append("</User>");
		sb.append("<OP>").append(op).append("</OP>");
		sb.append("<Method>").append(method).append("</Method>");
		sb.append("<DEVICE><BAND>");
		sb.append("<DownBand>").append(downBand).append("</DownBand>");
		sb.append("<UpBand>").append(upBand).append("</UpBand>");
		sb.append("<Enable>").append(enable).append("</Enable>");
		sb.append("<RENT>");
		if (enable != null)
			sb.append("<RentEnable>").append(rentEnable)
					.append("</RentEnable>");
		if (rentMode != null)
			sb.append("<RentMode>").append(rentMode).append("</RentMode>");
		if (downBandRent != null)
			sb.append("<DownBandRent>").append(downBandRent)
					.append("</DownBandRent>");
		if (upBandRent != null)
			sb.append("<UpBandRent>").append(upBandRent)
					.append("</UpBandRent>");
		sb.append("</RENT></BAND></DEVICE></Root>");
		return sb.toString();
	}

	public static String packageConfigTaskParam(String mac, String op,
			String method) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Root>");
		sb.append("<User>").append(mac).append("</User>");
		sb.append("<OP>").append(op).append("</OP>");
		sb.append("<Method>").append(method).append("</Method>");
		sb.append("</Root>");
		return sb.toString();
	}

	

	public static String packageWifiSsidTaskParam(String mac, String op,
			String method, Map<String, String> wifiMap,
			Map<String, String> ssidMap, Map<String, String> filterMap,
			boolean getClient, boolean getSite) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Root>");
		sb.append("<User>").append(mac).append("</User>");
		sb.append("<OP>").append(op).append("</OP>");
		sb.append("<Method>").append(method).append("</Method>");
		if (wifiMap != null) {
			sb.append("<WIFI>");
			sb.append(packageData(wifiMap));
		}
		if (ssidMap != null) {
			sb.append("<SSID>");
			sb.append(packageData(ssidMap));
			if (filterMap != null) {
				sb.append("<FILTER>");
				String filterMac = filterMap.get("MAC");
				filterMap.remove("MAC");
				if (filterMap.size() > 0) {
					sb.append(packageData(filterMap));
				}
				if (filterMac != null) {
					String[] filterMacArray = filterMac.split(",");
					for (String fm : filterMacArray)
						sb.append("<MAC>").append(fm).append("</MAC>");
				}
				sb.append("</FILTER>");
			}
			if (getClient) {
				sb.append("<CLIENT><HostName></HostName><MAC></MAC><IP></IP><Mask></Mask></CLIENT>");
			}
			sb.append("</SSID>");
		}
		if (getSite) {
			sb.append("<SITE><Chanel></Chanel><SSID></SSID><Mac></Mac><Signal></Signal></SITE>");
		}
		if (wifiMap != null) {
			sb.append("</WIFI>");
		}
		sb.append("</Root>");
		return sb.toString();
	}

	public static String packageWifiTaskParam(String mac, String op,
			String method, Map<String, String> paramMap) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Root>");
		sb.append("<User>").append(mac).append("</User>");
		sb.append("<OP>").append(op).append("</OP>");
		sb.append("<Method>").append(method).append("</Method>");
		if (paramMap != null) {
			sb.append("<WIFI>");
			if (paramMap.size() > 0) {
				sb.append(packageData(paramMap));
			}
			sb.append("</WIFI>");
		}
		sb.append("</Root>");
		return sb.toString();
	}
	
	public static String packageSetNetworkTaskParam(String mac, String op,
			String method, int networkEnable) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Root>");
		sb.append("<User>").append(mac).append("</User>");
		sb.append("<OP>").append(op).append("</OP>");
		sb.append("<Method>").append(method).append("</Method>");
		sb.append("<NetworkEnable>").append(networkEnable).append("</NetworkEnable>");
		sb.append("</Root>");
		return sb.toString();
	}
	
	public static String packagePluginParam(String mac, String op,
			String method, List<Map<String, String>> paramList,
			List<String[]> detailList) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Root>");
		sb.append("<User>").append(mac).append("</User>");
		sb.append("<OP>").append(op).append("</OP>");
		sb.append("<Method>").append(method).append("</Method>");
		if (paramList != null && paramList.size() > 0) {
			for (Map<String, String> paramMap : paramList) {
				sb.append("<PLUGIN>");
				sb.append(packageData(paramMap));
				if (detailList != null) {
					sb.append("<DETAIL>");
					sb.append(packageListData(detailList));
					sb.append("</DETAIL>");
				}
				sb.append("</PLUGIN>");
			}
		}
		sb.append("</Root>");
		return sb.toString();
	}

	public static String packagePluginDetailParam(String mac, String op,
			String method, String name, String version,
			List<String[]> pluginDetailList) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Root>");
		sb.append("<User>").append(mac).append("</User>");
		sb.append("<OP>").append(op).append("</OP>");
		sb.append("<Method>").append(method).append("</Method>");
		sb.append("<PLUGIN>");
		sb.append("<Name>").append(name).append("</Name>");
		sb.append("<Version>").append(version).append("</Version>");
		sb.append("<DETAIL>");
		sb.append(packageListData(pluginDetailList));
		sb.append("</DETAIL>");
		sb.append("</PLUGIN>");
		sb.append("</Root>");
		return sb.toString();
	}

	private static String packageData(Map<String, String> paramMap) {
		StringBuffer sb = new StringBuffer();
		Set<String> keySet = paramMap.keySet();
		Iterator<String> keyIt = keySet.iterator();
		while (keyIt.hasNext()) {
			String name = keyIt.next();
			String value = paramMap.get(name);
			value = Toolkit.transferred(value);
			sb.append("<").append(name).append(">").append(value).append("</")
					.append(name).append(">");
		}
		return sb.toString();
	}

	private static String packageListData(List<String[]> paramList) {
		StringBuffer sb = new StringBuffer();
		for (String[] data : paramList) {
			String name = data[0];
			String value = data[1];
			sb.append("<").append(name).append(">").append(value).append("</")
					.append(name).append(">");
		}
		return sb.toString();
	}

	/**
	 * 一年所有周的开始和结束日期
	 * 
	 * @param year
	 * @return
	 */
	public static List<String> weekDateList(int year) {
		List<String> weekList = new ArrayList<String>();
		// TODO 自动生成方法存根
		Calendar c_begin = new GregorianCalendar();
		Calendar c_end = new GregorianCalendar();
		c_begin.set(year, 0, 1); // Calendar的月从0-11
		c_end.set(year, 11, 31); // Calendar的月从0-11

		int count = 1;
		int preCount = -1;
		c_end.add(Calendar.DAY_OF_YEAR, 1); // 结束日期下滚一天是为了包含最后一天
		String st = "", et = "", date = "";
		while (c_begin.before(c_end)) {
			date = TimeUtil.format(c_begin.getTime(), "yyyy-MM-dd");
			if (count != preCount) {
				st = date;
				preCount = count;
			}
			if (c_begin.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				et = date;
				weekList.add(st + "_" + et);
				count++;
			}
			c_begin.add(Calendar.DAY_OF_YEAR, 1);
		}
		if (!date.equals(et)) {
			weekList.add(st + "_" + date);
		}
		return weekList;
	}

	/**
	 * 一年的所有日期
	 * 
	 * @param year
	 * @return
	 */
	public static List<String> allDayOfYear(int year) {
		List<String> dayList = new ArrayList<String>();
		// TODO 自动生成方法存根
		Calendar c_begin = new GregorianCalendar();
		Calendar c_end = new GregorianCalendar();
		c_begin.set(year, 0, 1); // Calendar的月从0-11
		c_end.set(year, 11, 31); // Calendar的月从0-11
		c_end.add(Calendar.DAY_OF_YEAR, 1);
		while (c_begin.before(c_end)) {
			String date = TimeUtil.format(c_begin.getTime(), "yyyy-MM-dd");
			c_begin.add(Calendar.DAY_OF_YEAR, 1);
			dayList.add(date);
		}
		return dayList;
	}

	public static int getChinaWeekIndex(Date date) {
		// 确认是星期几
		int weekDay = TimeUtil.getWeekDay(date);
		// 转换成中国习惯的周索引
		weekDay = weekDay - 1;
		if (weekDay == 0)
			weekDay = 7;
		return weekDay;
	}

	
	public static boolean isTimeRepeat(Date st1, Date et1, Date st2, Date et2) {

		if (st1.after(et2) || st1.equals(et2))
			return false;
		if (st2.after(et1) || st2.equals(et1))
			return false;

		return true;
	}

	public static Date[] getCrossTime(Date s1, Date e1, Date s2, Date e2) {
		// s1->e1时间段全在s2-->e2时间段以内
		if (s1.getTime() >= s2.getTime() && e1.getTime() <= e2.getTime()) {
			return new Date[] { s1, e1 };
		} else if (s2.getTime() >= s1.getTime() && e2.getTime() <= e1.getTime()) {// s2->e2时间段全在s1-->e1时间段以内
			return new Date[] { s2, e2 };
		} else if (s1.getTime() >= s2.getTime() && s1.getTime() < e2.getTime()// s2-->e2与s1-->e1左交叉
				&& e1.getTime() >= e2.getTime())
			return new Date[] { s1, e2 };
		else if (s2.getTime() >= s1.getTime() && s2.getTime() < e1.getTime() // s2-->e2与s1-->e2右交叉
				&& e2.getTime() > e1.getTime())
			return new Date[] { s2, e1 };
		return null;
	}

	@SuppressWarnings("unchecked")
	public static void createExcel(HashMap<String, Object> dataMap,
			String excelName, String excelPath, String title) throws Exception {
		File excelDir = new File(excelPath);
		if (!excelDir.exists()) {
			excelDir.mkdirs();
		}
		WritableWorkbook book = Workbook.createWorkbook(new File(excelDir,
				excelName));

		WritableSheet sheet = book.createSheet(title, 0);

		int dataFirstRowIndex = 0;
		int firstColumn = 0;
		if (title != null) {
			Label titleLabel = new Label(dataFirstRowIndex, firstColumn, title);
			sheet.addCell(titleLabel);
			dataFirstRowIndex = 1;
		}
		Label timeLabel = new Label(firstColumn, dataFirstRowIndex, "时间段");
		sheet.addCell(timeLabel);

		Set<String> keys = dataMap.keySet();
		int column = 1;
		int averageIndex = 0;
		for (String key : keys) {

			Label nameLabel = new Label(column, dataFirstRowIndex, key);
			sheet.addCell(nameLabel);
			HashMap<String, Double> ratingMap = (HashMap<String, Double>) dataMap
					.get(key);

			Set<String> nameSets = ratingMap.keySet();
			int i = dataFirstRowIndex + 1;
			for (String cName : nameSets) {
				if (column == 1) {
					Label label = new Label(0, i, cName);
					sheet.addCell(label);
				}
				Number dataNum = new Number(column, i, ratingMap.get(cName));
				sheet.addCell(dataNum);
				i++;
			}
			double averData = 0d;
			// double averData = Toolkit.getAvarageDoubel(ratingMap);
			if (column == 1) {// 第一列的时候最后添加平均值
				Label averLabel = new Label(0, i, "平均值");
				averageIndex = i;
				sheet.addCell(averLabel);
			}
			Number averNumber = new Number(column, averageIndex, averData);
			sheet.addCell(averNumber);
			column++;
		}
		book.write();
		book.close();
	}
	
	public static String transferred(String data){
		if(data==null||data.equals(""))
			return data;
		String v = data.replaceAll("&", "&amp;");
		v = v.replaceAll("<", "&lt;");
		v = v.replaceAll(">", "&gt;");
		return v;
	}

	public static int daysBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	public static void main(String[] args) {
		try {
			/*
			File file = new File("D:\\temp\\ppv.xml");
			FileInputStream fis = new FileInputStream(file);
			byte[] dataByte = new byte[fis.available()];
			fis.read(dataByte);
			
			String data = new String(dataByte);
			System.out.println(data);

			ByteArrayInputStream bis = new ByteArrayInputStream(
					data.getBytes("UTF-8"));
			Document document = new SAXReader().read(bis);
			Element rootEle = document.getRootElement();
			Element headerEle = rootEle.element("header");
			String action = headerEle.getText();
			System.out.println("action:"+action);
			*/
			
			//Date date = new Date();
			//date.setTime(1444640761000l);
			//System.out.println(TimeUtil.format(date));
			

		} catch (Exception e) {
			System.out.println(e);
		}

		/*
		 * try { Socket s = new Socket("1.202.237.200",8089); boolean r =
		 * s.isConnected(); System.out.println(r); } catch (Exception e) {
		 * System.out.println(e); }
		 */

	}

}
