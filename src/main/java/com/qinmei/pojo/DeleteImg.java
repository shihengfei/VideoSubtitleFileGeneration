package com.qinmei.pojo;

/**
 * 验证后删除照片
 * <p>Company: www.qinmei.com</p> 
 * @author 史恒飞 ，tel ： 18516417728
 * @version 1.0 ， 2018年2月28日上午10:30:43
 */
public class DeleteImg {

	/**
	 * 图片链接
	 */
	private String url;
	
	/**
	 * 图片存放位置
	 */
	private String imgLocation;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImgLocation() {
		return imgLocation;
	}

	public void setImgLocation(String imgLocation) {
		this.imgLocation = imgLocation;
	}
	
	
}
