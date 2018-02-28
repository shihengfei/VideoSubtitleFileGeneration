package com.qinmei.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qinmei.pojo.SmsSend;
import com.qinmei.utils.JsonUtils;

/**
 * 短信发送接口
 * 
 * @author 史恒飞 ,tel 18516417728
 * @version 1.0 , 2018年1月18日上午11:28:29
 */
@Controller
public class SmsSendController {

	private static final String addr = "http://api.sms.cn/sms/";
	private static final String userId = "mohongliang";
	private static final String pwd = "9506e1d8cfbc8c4a2c7e886a4550e3ab";
	private static final String encode = "UTF-8";

	@RequestMapping("/smsSend")
	@ResponseBody
	public SmsSend send(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String msgContent = request.getParameter("msgContent");
		msgContent = msgContent+"【德译科技】";
		String mobile = request.getParameter("mobile");
		// 组建请求
		String straddr = addr + "?ac=send&uid=" + userId + "&pwd=" + pwd + "&mobile=" + mobile + "&encode=" + encode
				+ "&content=" + msgContent;
		StringBuffer sb = new StringBuffer(straddr);
		System.out.println("URL:" + sb);

		// 发送请求
		URL url = new URL(sb.toString());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		// 返回结果
		String inputline = in.readLine();
		
		SmsSend smsSend = JsonUtils.jsonToPojo(inputline, SmsSend.class);
		
		int stat = smsSend.getStat();
		System.out.println(stat);
		SmsSend send= new SmsSend();
		send.setStat(stat);
		switch (stat) {
		case 100:
			send.setMessage("发送成功");
			return send;
		case 101:
			send.setMessage("验证失败");
			return send;
		case 102:
			send.setMessage("短信不足");
			return send;
		case 103:
			send.setMessage("操作失败");
			return send;
		case 104:
			send.setMessage("非法字符");
			return send;
		case 105:
			send.setMessage(" 内容过多");
			return send;
		case 106:
			send.setMessage("号码过多");
			return send;
		case 107:
			send.setMessage("频率过快");
			return send;
		case 108:
			send.setMessage("号码内容空");
			return send;
		case 109:
			send.setMessage("账号冻结");
			return send;
		case 112:
			send.setMessage("号码错误");
			return send;
		case 113:
			send.setMessage("定时出错");
			return send;
		case 116:
			send.setMessage("禁止接口发送");
			return send;
		case 117:
			send.setMessage("绑定IP不正确");
			return send;
		case 161:
			send.setMessage("未添加短信模板");
			return send;
		case 162:
			send.setMessage("模板格式不正确");
			return send;
		case 163:
			send.setMessage("模板ID不正确");
			return send;
		case 164:
			send.setMessage("全文模板不匹配");
			return send;
		}
		return null;
	}
}
