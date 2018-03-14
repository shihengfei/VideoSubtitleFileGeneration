package com.qinmei.pojo;

/**
 * 视频处理 pojo
 * <p>Company: www.qinmei.com</p> 
 * @author 史恒飞 ，tel ： 18516417728
 * @version 1.0 ， 2018年3月7日下午2:40:21
 */
public class Video {

	/**
	 * 视频名称
	 */
	private String videoName;
	
	/**
	 * 视频私有还是公开
	 */
	private String privacy;
	
	/**
	 * 视频外网可访问地址
	 */
	private String videoUrl;
	
	/**
	 * 字幕语言
	 */
	private String language;
	
	/**
	 * 图片上传的服务器上的位置
	 */
	private String videoLocation;

	public String getVideoLocation() {
		return videoLocation;
	}

	public void setVideoLocation(String videoLocation) {
		this.videoLocation = videoLocation;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	
}
