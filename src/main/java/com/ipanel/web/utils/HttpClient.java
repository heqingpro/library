package com.ipanel.web.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.ipanel.webapp.framework.util.Log;

public class HttpClient {
	
	private String TAG = "HttpClient";

	public static final int SUCCESS = 0x00;
	public static final int FAILURE = 0x01;
	public static final int EXCEPTION = 0x02;

	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";
	public static final String METHOD_PUT = "PUT";
	public static final String METHOD_DELETE = "DELETE";

	URL mURL;
	String mMethod = METHOD_GET;
	Map<String, String> mProperties;
	byte[] mPostData;
	boolean runFlag = false;
	private String encode = "UTF-8";

	public HttpClient(URL url) {
		mURL = url;
		mProperties = new TreeMap<String, String>();
	}

	public void setEncode(String code) {
		this.encode = code;
	}

	public void setRequestMethod(String method) {
		synchronized (HttpClient.this) {
			if (runFlag){
				return;	
			}	
		}
		mMethod = method;
	}

	public void setRequestProperty(String key, String val) {
		synchronized (HttpClient.this) {
			if (runFlag){
				return;
			}
				
		}
		mProperties.put(key, val);
	}

	public void setPostData(byte[] data) {
		synchronized (HttpClient.this) {
			if (runFlag){
				return;
			}
				
		}
		mPostData = data;
	}

	public HttpResponse execute() {
		synchronized (HttpClient.this) {
			if (runFlag){
				return null;
			}
				
			runFlag = true;
		}
		HttpResponse response = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();
			conn.setConnectTimeout(20000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod(mMethod);
			Iterator<String> it = mProperties.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				conn.setRequestProperty(key, mProperties.get(key));
			}
			if (METHOD_POST.equals(mMethod) || METHOD_PUT.equals(mMethod)) {
				conn.setRequestProperty("Content-type", "");
				conn.setRequestProperty("Content-Length",String.valueOf(mPostData.length));
				conn.getOutputStream().write(mPostData);
			} else {
				conn.connect();
			}
			int code = conn.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				StringBuilder sb = new StringBuilder();
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String input = null;

				while ((input = in.readLine()) != null) {
					sb.append(input);
				}
				response = new HttpResponse(SUCCESS, "success", sb.toString(),code);
			} else {
				response = new HttpResponse(FAILURE, "http response code is "+ code, "", code);
			}
		} catch (IOException e) {
			Log.e(TAG, "***execute throw exception:" + e);
			response = new HttpResponse(EXCEPTION, e.getMessage(), "", -1);
		} finally {
			synchronized (HttpClient.this) {
				runFlag = false;
			}
		}
		return response;
	}

	public class HttpResponse {

		int mResult;
		String mMessage;
		String mData;
		int mRespCode;

		HttpResponse() {
		}

		HttpResponse(int result, String msg, String data, int respCode) {
			mResult = result;
			mMessage = msg;
			mData = data;
			mRespCode = respCode;
		}

		public int getResult() {
			return mResult;
		}

		public String getMessage() {
			return mMessage;
		}

		public String getData() {
			return mData;
		}

		public int getmRespCode() {
			return mRespCode;
		}
	}

	public static void main(String[] args) {
		try {
			StringBuffer sb=new StringBuffer();
			sb.append("http://218.185.196.79:9090/stbservlet");
			sb.append("?attribute=ewf_json_stb_list_entry");
			sb.append("&parent_HUID=THFN22");
			URL url=new URL(sb.toString());
			HttpClient httpClient=new HttpClient(url);
			httpClient.setRequestMethod(HttpClient.METHOD_GET);
			HttpResponse reponseData=httpClient.execute();
			System.out.println(reponseData.getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}