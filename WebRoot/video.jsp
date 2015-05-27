<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich"
	xmlns:c="http://java.sun.com/jsp/jstl/core">
<ui:composition>
<body>
	<script type="text/javascript" src="js/jquery-1.9.1.min.js" ></script>
<h:form>
<div style="width:1920; height:1080;margin:0 auto;">
 <video id="z" width="1920"; height="1080" controls="controls" autoplay="autoplay">
        <source src="video/T5.mp4" type="video/mp4">
        Your browser does not support the video tag.
    </video>
    <script>
        setInterval(function () {
            Media = document.getElementById("z");
            if (Media.ended)
                window.location = "http://10.10.60.134:8080/mocs/index.faces";
                 //////////////////////////////////////////////////////////////
            else if (Media.paused)
                $("#add").fadeIn(300);
            else if (Media.play)
                $("#add").fadeOut(300);
            /////////////////////////////////////////////////////////////////////
        }, 500);
    </script>
</div>
<div id="add" style="width:700px; height:500px; display:none; border:0px solid black; position:absolute; 
	top:290px; left:610px;background-image: url('/mocs/images/video_backgroud.png');">
	<div style="position: absolute;margin-left: 0px; margin-top: 0px;background-image: url('/mocs/images/video_pre.png');border:0px solid black;
		width:350px;height:480px;z-index: 10;" 
		onclick="onback()">
	
	</div>
	<div style="position: absolute;margin-left:350px; margin-top: 0px;border:0px solid black;width:340px;height:480px;background-image: url('/mocs/images/video_pre.png');
		z-index: 10;" 
		onclick="onnext()">
	
	</div>
	<div style="position: absolute;margin-left:640px; margin-top:5px;border:0px solid black;width:50px;height:50px;background-image: url('/mocs/images/video_pre.png');
		z-index: 11;background-color: white;" 
		>
	
	</div>
	
</div>
</h:form>
</body>

<script type="text/javascript">
		//<![CDATA[
		     function onback(){
		     	location.href='http://10.10.60.134:8080/mocs/welcome.faces?type=1';
		     }
		     function onnext(){
		     	location.href='http://10.10.60.134:8080/mocs/index.faces';
		     }
		     function onyc(){
		     	document.getElementById("add").style.display="none";
		     }
        //]]>	
</script>


</ui:composition>
</html>
