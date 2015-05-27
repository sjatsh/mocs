<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>SYMG</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
 <body style="width:1920px;height:1080px;background-image:url(images/index.png);background-repeat:no-repeat;overflow:scroll;overflow-x:hidden;overflow-y:hidden;">
		<div style="background-color:transparent;position:absolute;left:1770px;width:150px;height:150px;" onclick="location.href='http://192.168.2.11:8080/mocs/index.faces'">
		</div>
			
		
	</body>
</html>
