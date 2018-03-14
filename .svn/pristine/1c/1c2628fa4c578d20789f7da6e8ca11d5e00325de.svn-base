package com.qinmei.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qinmei.pojo.PersonFace;
import com.qinmei.pojo.PersonName;
import com.qinmei.utils.FaceResult;
import com.qinmei.utils.JsonUtils;

/**
 * 人脸识别 Controller
 * 
 * <p>
 * Title: FaceRecognitionController
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: www.qinmei.com
 * </p>
 * 
 * @author 史恒飞 ，tel 18516417728
 * @version 1.0 ，@2018年1月10日下午7:00:02
 */
@Controller
public class FaceRecognitionController {

	/** 密钥 */
	@Value("${SUBSCRIPTION_KEY}")
	private String SUBSCRIPTION_KEY;

	/** 人员创建和添加人脸 uri */
	@Value("${CREATE_PERSON_URI}")
	private String CREATE_PERSON_URI;

	/** 人脸检测 uri */
	@Value("${FACE_DETECTION}")
	private String FACE_DETECTION;

	/** 人脸验证 uri */
	@Value("${FACE_VERIFICATION}")
	private String FACE_VERIFICATION;

	/**
	 * 创建人员并返回人员 PersonId
	 * <p>
	 * Title: createPerson
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param groupId
	 *            人员组 ID
	 * @param name
	 *            人员组 name
	 * @return
	 */
	@RequestMapping("/createPerson")
	@ResponseBody
	public FaceResult createPerson(HttpServletRequest httpRequest) {
		
		String name = httpRequest.getParameter("name");
		
		HttpClient httpclient = new DefaultHttpClient();
		int code = 0;
		try {
			URIBuilder builder = new URIBuilder(CREATE_PERSON_URI + "q-m_/persons");

			builder.setParameter("visualFeatures", "Categories,Description,Color");
			builder.setParameter("language", "en");

			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);

			// 请求头
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);

			PersonName personName = new PersonName();
			personName.setName(name);
			// 将对象转换为 json
			String jsonUrl = JsonUtils.objectToJson(personName);

			StringEntity reqEntity = new StringEntity(jsonUrl);
			request.setEntity(reqEntity);
			// 执行请求返回响应
			HttpResponse response = httpclient.execute(request);
			code = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				// 将返回数据转换为 json
				String jsonString = EntityUtils.toString(entity);
				jsonString = jsonString.replace("[", "");
				jsonString = jsonString.replace("]", "");
				PersonFace personFace = JsonUtils.jsonToPojo(jsonString, PersonFace.class);
				// 返回 personId
				// =============================================================
				return FaceResult.ok(personFace.getPersonId());
			}
		} catch (Exception e) {
			// Display error message.
			System.out.println(e.getMessage());
		}
		return FaceResult.build(code, "人员创建失败");
	}

	/**
	 * 人员人脸添加
	 * <p>Title: createFaceToPerson</p>
	 * <p>Description: </p>
	 * @param httpRequest
	 * @param img
	 * @return
	 */
	@RequestMapping("/createFaceToPerson")
	@ResponseBody
	public FaceResult createFaceToPerson(HttpServletRequest httpRequest,@RequestParam(value = "img") MultipartFile img) {

		// 获取人员 id
		String personId = httpRequest.getParameter("personId");
		
		// 调用文件上传
		String imageUrl = uploadImage(httpRequest,img);

		HttpClient httpclient = new DefaultHttpClient();
		int code = 0;
		try {
			// groupid 默认 q-m_
			URIBuilder builder = new URIBuilder(
					CREATE_PERSON_URI + "q-m_" + "/persons/" + personId + "/persistedFaces");

			builder.setParameter("visualFeatures", "Categories,Description,Color");
			builder.setParameter("language", "en");

			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);

			// 请求头
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);

			PersonFace personFace = new PersonFace();
			personFace.setUrl(imageUrl);
			// 将对象转换为 json
			String jsonUrl = JsonUtils.objectToJson(personFace);
			StringEntity reqEntity = new StringEntity(jsonUrl);
			request.setEntity(reqEntity);

			// 执行请求返回响应
			HttpResponse response = httpclient.execute(request);
			code = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			
 			if (entity != null) {
				// 将返回数据转换为 json
				String jsonString = EntityUtils.toString(entity);
				jsonString = jsonString.replace("[", "");
				jsonString = jsonString.replace("]", "");
				// 将 json 转化为 pojo
				PersonFace personFace2 = JsonUtils.jsonToPojo(jsonString, PersonFace.class);
				// 检测到人脸后，返回 PersistedFaceId
				return FaceResult.ok(personFace2.getPersistedFaceId());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return FaceResult.build(code, "人脸添加失败");
	}

	/**
	 * 人脸检测，返回 faceId
	 * <p>
	 * Title: getFaceId
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param url
	 *            传入人脸照片 url
	 * @return
	 */
	public String getFaceId(String imageUrl) {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			URIBuilder builder = new URIBuilder(FACE_DETECTION);

			builder.setParameter("visualFeatures", "Categories,Description,Color");
			builder.setParameter("language", "en");

			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);

			// 请求头
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);

			// 检测人脸照片的 url
			PersonFace personFace = new PersonFace();
			personFace.setUrl(imageUrl);
			// 将对象转换为 json
			String jsonUrl = JsonUtils.objectToJson(personFace);

			StringEntity reqEntity = new StringEntity(jsonUrl);
			request.setEntity(reqEntity);

			// 执行请求返回响应
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				// 将返回数据转换为 json
				String jsonString = EntityUtils.toString(entity);
				jsonString = jsonString.replace("[", "");
				jsonString = jsonString.replace("]", "");
				// 将 json 转化为 pojo
				PersonFace personFace2 = JsonUtils.jsonToPojo(jsonString, PersonFace.class);
				// 检测到人脸后，返回 faceId，从人员组通过传入用户的 id 获取 faceId, =================
				return personFace2.getFaceId();
			}
		} catch (Exception e) {
			// Display error message.
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 人脸验证
	 * <p>Title: faceVerification</p>
	 * <p>Description: </p>
	 * @param httpRequest
	 * @param img
	 * @return
	 */
	@RequestMapping("/faceVerification")
	@ResponseBody
	public String faceVerification(HttpServletRequest httpRequest,@RequestParam(value = "img") MultipartFile img) {
		// 获取人员 id
		String personId = httpRequest.getParameter("personId");

		// 调用文件上传
		String imageUrl = uploadImage(httpRequest,img);
		
		HttpClient httpclient = new DefaultHttpClient();
		int code = 0 ;
		try {
			URIBuilder builder = new URIBuilder(FACE_VERIFICATION);

			builder.setParameter("visualFeatures", "Categories,Description,Color");
			builder.setParameter("language", "en");

			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);

			// 请求头
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);

			// 调用人脸检测传入 url 地址，返回 faceId
			String faceId = getFaceId(imageUrl);

			// 将传递的数据封装
			PersonFace personFace = new PersonFace();
			// personGroupId 默认q-m_
			personFace.setPersonGroupId("q-m_");
			personFace.setFaceId(faceId);
			personFace.setPersonId(personId);
			// 将对象转换为 json
			String jsonUrl = JsonUtils.objectToJson(personFace);
			StringEntity reqEntity = new StringEntity(jsonUrl);
			request.setEntity(reqEntity);

			// 执行请求返回响应
			HttpResponse response = httpclient.execute(request);
			code = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				// 将返回数据转换为 json
				String jsonString = EntityUtils.toString(entity);
				jsonString = jsonString.replace("[", "");
				jsonString = jsonString.replace("]", "");
				// {"isIdentical":true,"confidence":1.0} 验证成功 true
				return jsonString;
			}
		} catch (Exception e) {
			// Display error message.
			System.out.println(e.getMessage());
		}
		return FaceResult.build(code, "验证失败，请重新传入照片!").toString();
	}

	/**
	 * 创建人员组
	 * <p>
	 * Title: createPersonGroup
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param groupId
	 * @param groupName
	 * @return
	 */
	public FaceResult createPersonGroup(String groupId, String groupName) {
		HttpClient httpclient = new DefaultHttpClient();
		int code = 0;
		try {
			URIBuilder builder = new URIBuilder("https://api.cognitive.azure.cn/face/v1.0/persongroups/" + groupId);
			builder.setParameter("visualFeatures", "Categories,Description,Color");
			builder.setParameter("language", "en");

			URI uri = builder.build();
			HttpPut request = new HttpPut(uri);

			// 请求头
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);

			PersonName personFace = new PersonName();
			personFace.setName(groupName);
			// 将对象转换为 json
			String jsonUrl = JsonUtils.objectToJson(personFace);

			StringEntity reqEntity = new StringEntity(jsonUrl);
			request.setEntity(reqEntity);

			// 执行请求返回响应
			HttpResponse response = httpclient.execute(request);
			code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				return FaceResult.ok();
			}
		} catch (Exception e) {
			// Display error message.
			System.out.println(e.getMessage());
		}
		return FaceResult.build(code, "创建人员组失败");
	}

	/**
	 * 人脸上传返回 http请求 url
	 * 
	 * <p>Title: uploadImage</p>
	 * <p>Description: </p>
	 * @param request
	 * @return
	 */
	public String uploadImage(HttpServletRequest request, MultipartFile img) {
		// 获取 人员 id
		String personId = request.getParameter("personId");
		
		// 获取工程所在根目录 
		File root2=new File("../webapps");
		// 为图片添加唯一识别
		UUID randomUUID = UUID.randomUUID();
		String rootDir2= null;
		try {
			rootDir2=root2.getCanonicalPath();
			// 保存图片到硬盘 
			img.transferTo(new File(rootDir2+"/upload/"+personId+randomUUID.toString()+".jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 图片访问 http 地址
		return "http://139.196.179.141:8080/upload/"+personId+randomUUID.toString()+".jpg";
	}
}
