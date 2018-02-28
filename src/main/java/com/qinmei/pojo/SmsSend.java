package com.qinmei.pojo;

/**
 * 判断状态码
 * 
 * @author 史恒飞 ,tel 18516417728
 * @version 1.0 , 2018年1月18日上午11:04:09
 */
public class SmsSend {
// {"stat":"100","message":"??????"}
	private int stat;
	private String message;
	public int getStat() {
		return stat;
	}
	public void setStat(int stat) {
		this.stat = stat;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
