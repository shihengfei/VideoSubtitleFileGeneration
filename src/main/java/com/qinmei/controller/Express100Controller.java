package com.qinmei.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 快递 100 接口
 * 
 * @author 史恒飞 ,tel 18516417728
 * @version 1.0 , 2018年1月17日上午8:40:00
 */
@Controller
public class Express100Controller {

	@RequestMapping("/expressQuery")
	@ResponseBody
	public String query(HttpServletRequest request){
		String com = request.getParameter("com");
		String nu = request.getParameter("nu");
		String uri = "http://api.kuaidi100.com/api?id=d9d25833cb8474aa&com=";
		// ********************************************
		try {
			URL url = new URL(uri+com+"&nu="+nu+"&show=0&muti=1&order=desc");
			// 打开连接
			URLConnection con = url.openConnection();
			con.setAllowUserInteraction(false);
			InputStream urlStream = url.openStream();
			// 请求
			String type = con.guessContentTypeFromStream(urlStream);
			// 设置响应格式
			String charSet = null;
			if (type == null)
				type = con.getContentType();

			if (type == null || type.trim().length() == 0 || type.trim().indexOf("text/html") < 0);
			// 设置编码格式
			if (type.indexOf("charset=") > 0)
				charSet = type.substring(type.indexOf("charset=") + 8);
			// 解析响应数据
			byte b[] = new byte[10000];
			int numRead = urlStream.read(b);
			String content = new String(b, 0, numRead);
			while (numRead != -1) {
				numRead = urlStream.read(b);
				if (numRead != -1) {
					// 返回多条数据读取
					String newContent = new String(b, 0, numRead, charSet);
					content += newContent;
				}
			}
			urlStream.close();
			return new String(content.getBytes("GBK"),"ISO-8859-1");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*@RequestMapping("/test")
	public String test(){
		return "index.jsp";
	}*/
}
