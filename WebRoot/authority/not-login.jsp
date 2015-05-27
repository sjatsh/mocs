<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>



<head>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<!DOCTYPE html>
    <title><%=smtcl.mocs.utils.authority.HelperUtil.title() %></title>
    <script type="text/javascript">
        function onPageLoad () {
         /**   var s = "top - " + window.top;
            s += '\r\n window.name = ' + window.name;
            s += '\r\n window.location = ' + location;
            s += '\r\n top.location = ' + top.location;
//            alert (s);

           if (window.location.toString() != top.location.toString ()) {
               // top.location.assign (location.toString());
            }
//            if (window != window.top) {
//                window.top.location.assign (location);
//            } else {
//                centerDialog(document.getElementById (('root')));
//            }
*/
        }
    </script>
</head>
<body onload="onPageLoad()" style="background-color:#BFD6E7">
<div id="root"  class="container_bg_1">
    <div class="container_info">
     <p><img src="images/main_exit_title.png" /></p>
     <div id="message" class="exits_p2"><a href="login.jsp" class="exit_link1"></a></div>
     <div class="clear"></div>
    </div>
    <!--container_info--> 
</div>
<!--container-->


</body>
</html>