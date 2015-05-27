<%@page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="sso" uri="swg.com.cn/authority/sso" %>
<%@ taglib prefix="i18n" uri="swg.com.cn/authority/i18n" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <link href="../authority/css/login.css" rel="stylesheet" type="text/css" charset="utf-8" />
    <script type="text/javascript" charset="utf-8" src="../authority/javascript/jasmine-ajax.js"></script>
    <script type="text/javascript" charset="utf-8" src="../authority/javascript/i18n/i18n.js"></script>
</head>
<body  class="body_bg">
<form action="../login?formLoginPage=formLoginPage" method="post">

<div class="container">

  <!--header-->
  	<c:set var="location" value='<%= request.getParameter("location")%>'/>
	<c:if test="${!empty(location)}">
	    <input type="hidden" name="location" value="${location}" />
	</c:if>
  <div class="container_wrapper">
    <div id="text_info">
      <div class="text_info_top">        
        <input type="text" name="userName" class="text_user" value=""/>
      </div>
      <!--text_info_top-->
      <div class="text_info_down">
        <input type="password" name="password" class="text_password" value="" />
      </div>     
      <!--text_info_down-->
      	
    </div>    
    <!--text_info-->
    <sso:variable var="loginStatus" target="login.status" />
    <div class="msgTop">
    <c:if test="${loginStatus != null}">
    <i18n:i18n expression="message.error.login.fail" />              
	</c:if>
	</div>  
    <div id="login_info">
      <input type="submit" class="login_btn" value="" />
    </div>
    <!--login_info-->
  </div>
  <!--main-->
  <div id="footer"> 用户条款 · Copyright © 2012 SYMG 保留一切权利 · SP许可证：琼ICP备13000765号· 建议反馈 </div>
  <!--footer-->
</div>

    <div style="display:none">
        <sso:variable var="locale" target="jasmine.i18n.resource.manager"/>
		<sso:variable var="locale" target="dreamwork.jasmine2.locale" type="java.util.Locale" />
		<sso:variable var="defaultLocale" target="jasmine.i18n.default.locale" type="java.util.Locale" />
		
		<c:if test="${locale == null}">
		    <c:set var="locale" value="${defaultLocale}" />
		</c:if>
		                <select onchange="I18n.changeLanguage (this.value)">
		<c:forEach items="${locales.supportedLocales}" var="current">
		                    <option value="${current.locale}"<c:if test='${current.locale == locale}'> selected</c:if>>
		                        ${current.displayName}
		                    </option>
		</c:forEach>
                </select>
      </div>
</form>
</body>
</html>