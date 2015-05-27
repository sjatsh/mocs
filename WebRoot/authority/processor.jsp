<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="sso" uri="swg.com.cn/authority/sso" %>
<%@ taglib prefix="i18n" uri="swg.com.cn/authority/i18n" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title></title>
    <sso:variable var="targets" target="swg.authority.sso.target" type="java.util.List" />
    <c:forEach items="${targets}" var="target">
    <script type='text/javascript' charset='utf-8' src='${target}'></script>
    </c:forEach>
    <sso:variable var="next" target="swg.authority.login.location" type="java.lang.String" />
    <script type="text/javascript" charset="utf-8">
        function on_page_load () {
            var form = document.getElementById ('__auto_form');
            if (form) {
                form.submit ();
            } else {
                location.assign ('${next}');
            }
        }
    </script>
</head>
<body onload="on_page_load ()">
<% String action = request.getParameter("action");
	if("logout".equalsIgnoreCase(action)){%>
	正在注销用户信息，请稍候...
	<%} else{%>
    <i18n:i18n expression="login.label.processing" />
    <%} %>
<sso:variable var="method" target="swg.authority.login.method" type="java.lang.String" />
<sso:variable var="warp" target="swg.authority.login.form.data" />
<c:if test="${method == 'POST'}">
    <form action="${next}" method="${method}" style="display:none" id="__auto_form">
<%--
        <sso:formItem var="current">
        <input type="hidden" value="${current.value}" name="${current.name}" />
        </sso:formItem>
--%>
    </form>
</c:if>
</body>
</html>