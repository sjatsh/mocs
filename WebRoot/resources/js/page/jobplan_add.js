 $(document).ready(function() {
	document.documentElement.style.fontSize = 100 / 580 * $("body").height() + "px";
	
	parent.document.getElementById("jobplanadd2").style.visibility="visible";
	$("option").attr("data-i18n", function() {
		return $(this).text();
    });
	//选择框省略方法
	$(".zl-select select").selectAutoHide(10);
	//国际化翻译
	dataTranslate("jobplan_add", function(t) { $("*[data-i18n]").i18n();},"../static/i18n");	
//	$.autosizeInit(1325,490,800,295);
//	$.autosizeAll();
//	$.autosizeMore();
	   
});          
  document.getElementById("myform:startTimePopupButton").style.display="none";
  document.getElementById("myform:endTimePopupButton").style.display="none";
  document.getElementById("myform:submitTimePopupButton").style.display="none";
  
  function yzisclose(){//验证是否添加成功 是否关闭
	  var isSuccess=document.getElementById("myform:isSuccess").value;
      var msg2 = "top.msg2";
	   dataTranslate("jobplan_add", function(t) { 
		   msg2 = t(msg2);
		   },"../static/i18n");
	   
	   if(null!=isSuccess&&isSuccess==msg2){
		   jAlert(isSuccess, null, function() {
		  		parent.document.getElementById("jobplanadd2").style.visibility="hidden";
		  		parent.document.getElementById("jobaddshadow").style.display="none";
		  		parent.location.reload();   
		   });
	  	}else{
	  		jAlert(isSuccess);
	  	} 
  	}
  
  function onYZ(){
	  var msg3 = "top.msg3";
	  var msg4 = "top.msg4";
	  var msg5 = "top.msg5";
	  var msg6 = "top.msg6";
	  var msg7 = "top.msg7";
      var msg8 = "top.msg8";
	  dataTranslate("jobplan_add", function(t) { 
		   msg3 = t(msg3);
		   msg4 = t(msg4);
		   msg5 = t(msg5);
		   msg6 = t(msg6);
		   msg7 = t(msg7);
           msg8 = t(msg8);
		   },"../static/i18n");	
	 
	  var startTime=document.getElementById("myform:startTimeInputDate").value;
	  var endTime=document.getElementById("myform:endTimeInputDate").value;
      var submitTime=document.getElementById("myform:submitTimeInputDate").value;
	  var tt=dateCompare(startTime,endTime);
	  var jobplanType=document.getElementById("myform:jobplanType").value;
	  if(null==startTime||""==startTime){
		  jAlert(msg3);
	  }else if(null==endTime||""==endTime){
		  jAlert(msg4);
	  }else if(!tt) {
		  jAlert(msg5);
      }else if(submitTime==null || submitTime ==""){
    	  jAlert(msg8);
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
function dateCompare(startdate,enddate)   {
    var arr=startdate.split("-");
    var starttime=new Date(arr[0],arr[1],arr[2]);

    var arrs=enddate.split("-");
    var lktime=new Date(arrs[0],arrs[1],arrs[2]);

    return starttime.getTime() <= lktime.getTime();
    
}
  function cxbtn2(){
		document.getElementById("myform:btn_search2").click();
		parent.document.getElementById("jobplanadd2").src="";
		parent.document.getElementById("jobplanadd2").style.visibility="hidden";
		parent.document.getElementById("jobaddshadow").style.display="none";
		
		
	}
  function checkplanno(){
	  document.getElementById("myform:img1").click();
  }