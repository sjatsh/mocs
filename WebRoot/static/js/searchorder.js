//<![CDATA[
$(document).ready(function(){
	var status=document.getElementById("myform:hiddentime").value;
	var time=720000;
	var realtime=status*time;
$(".huadongimg").animate({left:'290px'},realtime);
});
 //]]>