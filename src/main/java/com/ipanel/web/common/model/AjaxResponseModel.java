package com.ipanel.web.common.model;

public class AjaxResponseModel
{	
	public final static boolean SUCCESS = true;
	public final static boolean NOT_SUCCESS = false;
	
	
	public static AjaxResponseModel RESPONSE_FAILURE_DATA_MODEL;
	public static AjaxResponseModel RESPONSE_SUCCESS_DATA_MODEL;
	static {
		RESPONSE_FAILURE_DATA_MODEL = new AjaxResponseModel(NOT_SUCCESS,"操作异常！");
		RESPONSE_SUCCESS_DATA_MODEL = new AjaxResponseModel(SUCCESS,"执行成功！");
	}
	
	
	
	private boolean responseFlag;
	private Integer responseCode;
	private String responseMessage;
	private Object data;
	
	public AjaxResponseModel()
	{
	}
	
	public AjaxResponseModel(boolean responseFlag,String responseMessage){
		this.responseFlag=responseFlag;
		this.responseMessage=responseMessage;
	}
	
	public AjaxResponseModel(Integer responseCode, String responseMessage)
	{
		this(responseCode,responseMessage,null);
	}
	
	public AjaxResponseModel(Integer responseCode, String responseMessage,
			Object data)
	{
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.data = data;
	}
	public Integer getResponseCode()
	{
		return responseCode;
	}
	public void setResponseCode(Integer responseCode)
	{
		this.responseCode = responseCode;
	}
	public String getResponseMessage()
	{
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage)
	{
		this.responseMessage = responseMessage;
	}
	public Object getData()
	{
		return data;
	}
	public void setData(Object data)
	{
		this.data = data;
	}

	public boolean isResponseFlag() {
		return responseFlag;
	}

	public void setResponseFlag(boolean responseFlag) {
		this.responseFlag = responseFlag;
	}
	
	
	
}
