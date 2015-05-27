<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="i18n" uri="swg.com.cn/authority/i18n" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><i18n:i18n expression="app.passport" resource="application" /></title>
</head>
<frameset rows="80, *, 1" frameborder='no' border='0' framespacing='0'>
    <frame src="top2.jasmine" name="subitFrame" scrolling="no" noresize="noresize" id="subitFrame" title="topFrame" />
    <frameset cols="240,5,*" frameborder="no" border="0" framespacing="0">
      <frame src="sidebar.jasmine" name="leftFrame" scrolling="no" noresize="noresize" id="leftFrame" />
      <frame src="handler.html" name="bar" scrolling="no" noresize="noresize" id="bar" />
      <frame src="" name="workArea" id="mainFrame" />
    </frameset>
    <frame src="bottom.jasmine" name="bottomFrame" scrolling="no" noresize="noresize" id="bottomFrame" title="bottomFrame" />
</frameset>
<noframes><body>
</body></noframes>
</html>