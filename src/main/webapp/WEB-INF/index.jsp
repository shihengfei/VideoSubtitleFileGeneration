<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 上传图片格式 -->
	<div>创建人脸到人员</div>
	<form action="http://139.196.179.141:8080/qinmei-face-service/createFaceToPerson" method="post" enctype="multipart/form-data">
		<input name="personId" type="text" >
		<input name="img" type="file" >
		<input type="submit" value="提交" >
	</form>
	
	<div>创建人员</div>
	<form action="http://139.196.179.141:8080/qinmei-face-service/createPerson" method="post" >
		人员名称:<input name="name" type="text" >
		<input type="submit" value="提交" >
	</form>

	<div>验证</div>
	<form action="http://139.196.179.141:8080/qinmei-face-service/faceVerification" method="post" enctype="multipart/form-data">
		<input name="personId" type="text" >
		<input name="img" type="file" >
		<input type="submit" value="提交" >
	</form>
	
	<div>快递查询</div>
	<form action="http://139.196.179.141:8080/qinmei-face-service/expressQuery">
		公司名：<input name="com" type="text" >
		快递单号：<input name="nu" type="text">
		<input type="submit" value="查询" >
	</form>
	
	<!-- <div>测试图片地址</div>
	<form action="http://139.196.179.141:8080/qinmei-face-service/imgtest" method="post" enctype="multipart/form-data">
		<input name="personId" type="text" >
		<input name="img" type="file" >
		<input type="submit" value="提交" >
	</form> -->
</body>
</html>