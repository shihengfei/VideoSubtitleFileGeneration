<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 上传视频格式 multipart,通过该接口直接获取处理完成后的视频链接 -->
	<div>视频字幕添加</div><br>
	<form action="http://2zbkd4.natappfree.cc/qinmei-video-service/getVideoURL" method="post" enctype="multipart/form-data">
		<!-- 视频名称 -->
		视频名称：<input name="videoName" type="text" ><br>
		<!-- 视频是否公开，公开 Public，私有 Private -->
		视频是否公开，公开 Public，私有 Private:<input name="privacy" type="text" ><br>
		<!-- 视频母语，字幕生成语言，Chinese, -->
		视频母语，字幕生成语言:<input name="language" type="text" ><br>
		<input name="video" type="file" >
		<input type="submit" value="提交" >
	</form>
	
	<div>视频字幕文件获取</div><br>
	<form action="http://2zbkd4.natappfree.cc/qinmei-video-service/getVideoId" method="post" enctype="multipart/form-data">
		<!-- 视频名称 -->
		视频名称：<input name="videoName" type="text" ><br>
		<!-- 视频是否公开，公开 Public，私有 Private -->
		视频是否公开，公开 Public，私有 Private:<input name="privacy" type="text" ><br>
		<!-- 视频母语，字幕生成语言，Chinese, -->
		视频母语，字幕生成语言:<input name="language" type="text" ><br>
		<input name="video" type="file" >
		<input type="submit" value="提交" >
	</form>
	
	<div>通过视频 id 获取对应语言字幕文件</div><br>
	<form action="http://2zbkd4.natappfree.cc/qinmei-video-service/getVttUrl" method="post">
		视频 id:<input name="id" type="text"><br>
		字幕语言输入英文:<input name="language" type="text"><br>
		<input type="submit" value="提交">
	</form>
	
</body>
</html>