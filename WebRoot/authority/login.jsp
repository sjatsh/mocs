<%@page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="sso" uri="swg.com.cn/authority/sso" %>
<%@ taglib prefix="i18n" uri="swg.com.cn/authority/i18n" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <link href="../css/general.css" rel="stylesheet" type="text/css" />
    <link href="../authority/css/login.css" rel="stylesheet" type="text/css" charset="utf-8" />
    <script type="text/javascript" charset="utf-8" src="../js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="../authority/javascript/jasmine-ajax.js"></script>
    <script type="text/javascript" charset="utf-8" src="../authority/javascript/i18n/i18n.js"></script>
    <script type="text/javascript" charset="utf-8" src="../js/autosize.js"></script>
</head>
<body>
<form action="../login?formLoginPage=formLoginPage" method="post">
	<c:set var="location" value='<%= request.getParameter("location")%>'/>
	<c:if test="${!empty(location)}">
	    <input type="hidden" name="location" value="${location}" />
	</c:if>
	
	<div class="wapper auto-size">
		<div class="logo-frame auto-size">
			<img src="../authority/images/logo.png" >
			<img src="../authority/images/logo_in.png" >
			<img src="../authority/images/logo_out.png" >
			<div class="logo-face auto-size">
				<img class="" src="../authority/images/logo_pic.png" >
				<img class="hidden" src="../authority/images/logo_text.png" >
			</div>
		</div>
		<div class="auto-size login_line">
			<img src="../authority/images/split_line_1.png" >
			<img src="../authority/images/split_line_2.png" >
		</div>
		<div class="auto-size login_login">
			<div class="auto-size login_info">
				<input type="text" name="userName" class="auto-size zl-input-text" value="" placeholder="username"/><br>
				<input type="password" name="password" class="auto-size zl-input-text" value="" placeholder="password" /><br>
				<div class="auto-size zl-checkbox">
					<div class="auto-size zl-checkbox-select">
						<div class="auto-size zl-checkbox-get"></div>
					</div>
					<span class="auto-size zl-checkbox-content">Remember me on this computer.</span>
					<div class="auto-size zl-checkbox-clear"></div>
				</div><br>
				<sso:variable var="loginStatus" target="login.status" />
				<c:if test="${loginStatus != null}">
				   	<%-- <i18n:i18n expression="message.error.login.fail" /> --%>
				   	<span>User name or password error</span><br>
				</c:if>
				<a class="auto-size zl-button" onclick="loginSubmit()"><span>Login</span></a>
			    <a class="auto-size zl-button"><span>Cancel</span></a>
				<input type="submit" class="login_btn" value="" style="display: none" id="loginsubmit"/>
			</div>
		</div>
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
	<script type="text/javascript" src="../authority/javascript/login.js"></script>
</form>
</body>
</html>