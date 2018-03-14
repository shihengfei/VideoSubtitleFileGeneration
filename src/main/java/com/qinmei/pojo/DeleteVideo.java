package com.qinmei.pojo;

/**
 * 上传后删除视频 
 * <p>Company: www.qinmei.com</p> 
 * @author 史恒飞 ，tel ： 18516417728
 * @version 1.0 ， 2018年2月28日上午10:30:43
 */
public class DeleteVideo {

	/**
	 * 视频链接
	 */
	private String url;
	
	/**
	 * 视频存放位置
	 */
	private String videoLocation;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVideoLocation() {
		return videoLocation;
	}

	public void setVideoLocation(String videoLocation) {
		this.videoLocation = videoLocation;
	}

}
