package com.qinmei.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qinmei.pojo.DeleteVideo;
import com.qinmei.pojo.Video;
import com.qinmei.utils.VideoResult;

/**
 * 视频字幕添加控制器
 * <p>
 * Company: www.qinmei.com
 * </p>
 * 
 * @author 史恒飞 ，tel ： 18516417728
 * @version 1.0 ， 2018年3月7日下午3:17:44
 */
@Controller
public class VideoController {

	@Value("${SUBSCRIPTION_KEY}")
	private String SUBSCRIPTION_KEY;

	/**
	 * 上传视频，获得视频 id
	 * <p>
	 * Title: getVttUrl
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param httpRequest
	 * @param video
	 * @return 视频 id
	 */
	@RequestMapping(value = "/getVideoId")
	@ResponseBody
	public String getVttUrl(HttpServletRequest httpRequest, @RequestParam(value = "video") MultipartFile video) {
		// 获取视频名称
		String videoName = httpRequest.getParameter("videoName");
		// 获取视频开放字段
		String privacy = httpRequest.getParameter("privacy");
		// 获取转换字幕语言
		String language = httpRequest.getParameter("language");
		// 调用视频上传方法返回视频储存位置和视频访问地址
		DeleteVideo deleteVideo = uploadVideo(videoName, video);
		String videoUrl = deleteVideo.getUrl();
		// 图片在服务器上保存的位置
		String videoLocation = deleteVideo.getVideoLocation();

		// 封装请求
		Video video2 = new Video();
		video2.setVideoName(videoName);
		video2.setPrivacy(privacy);
		video2.setLanguage(language);
		video2.setVideoUrl(videoUrl);
		video2.setVideoLocation(videoLocation);

		// 调用上传到微软服务器方法，返回视频处理结果 id
		String videoId = getVideoId(video2);

		// 通过 id 获取处理后的视频链接
		// String url = getVttUrl(videoId, language);

		return videoId;
	}

	/**
	 * 通过上传获取的视频 id 获取字幕文件链接
	 * <p>
	 * Title: getVttUrl
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param id
	 *            视频 id
	 * @param language
	 *            字幕语言
	 * @return 链接
	 */
	@RequestMapping(value = "getVttUrl", method = RequestMethod.POST)
	@ResponseBody
	public String getVttUrl(String id, String language) {
		HttpClient httpclient = HttpClients.createDefault();

		try {
			// 请求 url
			URIBuilder builder = new URIBuilder(
					"https://videobreakdown.azure-api.net/Breakdowns/Api/Partner/Breakdowns/" + id + "/VttUrl");
			// 获取字幕语言
			builder.setParameter("language", language);

			URI uri = builder.build();
			HttpGet request = new HttpGet(uri);
			request.setHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);

			// 发送请求
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String subtitlesUrl = EntityUtils.toString(entity);

				// 处理返回结果，去掉两边 "
				subtitlesUrl = subtitlesUrl.replace("\"", "");
				subtitlesUrl = subtitlesUrl.replace("\"", "");
				return subtitlesUrl;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
		return null;
	}

	/**
	 * 视频上传处理
	 * <p>
	 * Title: getVideoId
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return 视频处理 id
	 */
	public String getVideoId(Video video) {
		HttpClient httpclient = HttpClients.createDefault();
		try {
			// 请求 url
			URIBuilder builder = new URIBuilder(
					"https://videobreakdown.azure-api.net/Breakdowns/Api/Partner/Breakdowns?name="
							+ video.getVideoName() + "&privacy=" + video.getPrivacy());

			// 上传到微软的视频地址
			builder.setParameter("videoUrl", video.getVideoUrl());
			// 转换的语言(与语音保持一致)
			builder.setParameter("language", video.getLanguage());

			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Content-Type", "multipart/form-data");
			// 请求 OCP-APIM-订阅密钥 设置
			request.setHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);

			HttpResponse response = httpclient.execute(request);

			// 返回的处理后的视频 Id
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String videoId = EntityUtils.toString(entity);

				// 处理返回结果，去掉两边 "
				videoId = videoId.replace("\"", "");
				videoId = videoId.replace("\"", "");
				return videoId;
			}
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		} finally {
			// 删除上传后的视频
			File file = new File(video.getVideoLocation());
			file.delete();
		}
	}

	/**
	 * 视频上传到本地服务器
	 * <p>
	 * Title: uploadVideo
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param videoName
	 * @param video
	 * @return 可访问视频的外网地址
	 */
	public static DeleteVideo uploadVideo(String videoName, MultipartFile video) {

		// 获取工程所在根目录
		File root2 = new File("../webapps");
		// 为图片添加唯一识别
		UUID randomUUID = UUID.randomUUID();
		String rootDir2 = null;
		try {
			rootDir2 = root2.getCanonicalPath();
			rootDir2 = "D:/shfjava/java/apache-tomcat-7.0.79/webapps";
			// 保存视频到硬盘
			video.transferTo(new File(rootDir2 + "/upload/" + videoName + randomUUID.toString() + ".mp4"));

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		DeleteVideo deleteVideo = new DeleteVideo();
		deleteVideo.setUrl("http://2zbkd4.natappfree.cc/upload/" + videoName + randomUUID.toString() + ".mp4");
		deleteVideo.setVideoLocation(rootDir2 + "/upload/" + videoName + randomUUID.toString() + ".mp4");
		return deleteVideo;
	}
}
