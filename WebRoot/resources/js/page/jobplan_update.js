$(document).ready(function() {
	document.documentElement.style.fontSize = 100 / 580 * $("body").height() + "px";
	
     parent.document.getElementById("jobplanupdate2").style.visibility="visible";
 	 initPages();//根据状态，对页面的原始进行可用，和不可用设置
 	//选择框省略方法
 	$(".zl-select select").selectAutoHide(10);	
	//国际化翻译
    dataTranslate("jobplan_update", function(t) { $("span").i18n();},"../static/i18n");	
//    $.autosizeInit(1325,490,800,295);
//    $.autosizeAll();
//    $.autosizeMore();
});         
function initPages(){
	var status = document.getElementById("myform:statusHidden").value;
	
	if(status == 60 || status == 70){
		document.getElementById("myform:partTypeId").disabled=true;
		document.getElementById("myform:jobplanType").disabled=true;
		document.getElementById("myform:pjobplan").disabled=true;
		document.getElementById("myform:priority").disabled=true;
		document.getElementById("myform:no").disabled=true;
		document.getElementById("myform:name").disabled=true;
		document.getElementById("myform:proPlanNo").disabled=true;
		document.getElementById("myform:startTime").disabled=true;
		document.getElementById("myform:endTime").disabled=true;
	}
}
document.getElementById("myform:startTimePopupButton").style.display="none";
document.getElementById("myform:endTimePopupButton").style.display="none";
document.getElementById("myform:submitTimePopupButton").style.display="none";
function yzisclose1(){//验证是否添加成功 是否关闭
	  var msg2 = "top.msg2";
	  dataTranslate("jobplan_update", function(t) { 
		   msg2 = t(msg2);
		   },"../static/i18n");	 
       
	   var isSuccess=document.getElementById("myform:isSuccess").value;
	          
	   if(null!=isSuccess&&isSuccess==msg2){
		   jAlert(isSuccess, null, function() {
		         parent.document.getElementById("jobplanupdate2").style.visibility="hidden";
		         parent.document.getElementById("jobupdateshadow").style.display="none";
		         parent.location.reload(); 
		   });
	   }else{
		   jAlert(msg2); 
	   }
}
function onYZ(){
	  var msg3 = "top.msg3";
	  var msg4 = "top.msg4";
	  var msg5 = "top.msg5";
	  var msg6 = "top.msg6";
	  var msg7 = "top.msg7";
	  var msg8 = "top.msg8";
	  var msg9 = "top.msg9";
	  var msg10 = "top.msg10";
	  var msg11 = "top.msg11";
	  var msg12 = "top.msg12";
	  dataTranslate("jobplan_update", function(t) { 
		   msg3 = t(msg3);
		   msg4 = t(msg4);
		   msg5 = t(msg5);
		   msg6 = t(msg6);
		   msg7 = t(msg7);
		   msg9 = t(msg9);
		   msg11 = t(msg11);
		   msg12 = t(msg12);
		   },"../static/i18n");	
	  
	  var name=document.getElementById("myform:name").value;
	  var startTime=document.getElementById("myform:startTimeInputDate").value;
	  var endTime=document.getElementById("myform:endTimeInputDate").value;
	  var submitTime=document.getElementById("myform:submitTimeInputDate").value;
	  var planNum=document.getElementById("myform:planNum").value;
	  var tt=dateCompare(startTime,endTime);
	  var jobplanType=document.getElementById("myform:jobplanType").value;
	  
	 
	  if(null==name||""==name){
		  jAlert(msg9);
	  }else if(submitTime==null||submitTime==""){
		  jAlert(msg11);
	  }else if(null==planNum||""==planNum){
		  jAlert(msg12);
	  }else if(null==startTime||""==startTime){
		  jAlert(msg3);
	  }else if(null==endTime||""==endTime){
		  jAlert(msg4);
	  }else if(!tt){
		  jAlert(msg5);  
	  }else if(null==jobplanType||""==jobplanType){
		  jAlert(msg6);
	  }else if(jobplanType=="2"){
		  var pjobplan=document.getElementById("myform:pjobplan").value;
		  if(null==pjobplan||""==pjobplan){
			  jAlert(msg7);
		  }else{
			  document.getElementById("myform:btn_search1").click();
		  }
	  }else{
		  document.getElementById("myform:btn_search1").click();
	  }
}
function dateCompare(startdate,enddate)   
{   
var arr=startdate.split("-");    
var starttime=new Date(arr[0],arr[1],arr[2]);    
var starttimes=starttime.getTime();   
  
var arrs=enddate.split("-");    
var lktime=new Date(arrs[0],arrs[1],arrs[2]);    
var lktimes=lktime.getTime();   
  
if(starttimes>lktimes)    
{   
return false;   
}   
else  
return true;   
  
}  
function cxbtn2(){
		document.getElementById("myform:btn_search2").click();
		parent.document.getElementById("jobplanupdate2").src="";
	    parent.document.getElementById("jobplanupdate2").style.visibility="hidden";
		parent.document.getElementById("jobupdateshadow").style.display="none";
		
		
	}

