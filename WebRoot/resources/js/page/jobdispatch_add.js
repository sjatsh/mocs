$(document).ready(function() {
	$.autosizeExclude($(".jobdispatch-content-info-tubiao>*")[0], true);
	$.autosizeExclude($(".jobdispatch-content-info-tubiao1>*")[0], true);
	parent.document.getElementById("jobdispatchadd2").style.visibility="visible";
	//选择框省略方法
	$(".zl-select select").selectAutoHide(10);	
	$(".zl-selectInput-bg-right").click(function(){
		$(this).parent().find(".rf-au-lst-cord").css({"left":"","top":""}).toggle();
	});
	//国际化翻译
	dataTranslate("jobdispatch_add", function(t) { $("*[data-i18n]").i18n();},"../static/i18n");
  	$.autosizeInit(1325,760,800,458);
  	$.autosizeAll();
  	$.autosizeMore();
 }); 

function guojihua(){
	dataTranslate("jobdispatch_add", function(t) { $("*[data-i18n]").i18n();},"../static/i18n");
}

function changeDisabled(){
	var planJob = document.getElementById("myform:planjob").value;
	var num = document.getElementById("myform:num").value;
	var taskNum = document.getElementById("myform:taskNum").value;
	if(planJob){
		if(num==null || num=="undefined" || num==""){
			document.getElementById("myform:num").disabled = false;
		}else{
			document.getElementById("myform:num").disabled = true;
		}
		if(taskNum==null || taskNum=="undefined" || taskNum==""){
		    document.getElementById("myform:taskNum").disabled = false;
		}else{
			document.getElementById("myform:taskNum").disabled = true;
		}
	 }else{
		document.getElementById("myform:num").disabled = false;
		document.getElementById("myform:taskNum").disabled = false;
		document.getElementById("myform:num").value = "";
		document.getElementById("myform:taskNum").value = "";
	}
	
}

function change(){
	$(".zl-select select").selectAutoHide(10);
	
	document.getElementById("myform:num").disabled = false;
	document.getElementById("myform:taskNum").disabled = false;
	document.getElementById("myform:num").value = "";
	document.getElementById("myform:taskNum").value = "";
}
     
     function sub(){ 
    	 var msg = "content.msg";
    	 var msg1 = "content.msg1";
    	 var msg2 = "content.msg2";
    	 var msg3 = "content.msg3";
    	 var msg4 = "content.msg4";
    	 var msg5 = "content.msg5";
    	 var msg6 = "content.msg6";
   	  dataTranslate("jobdispatch_add", function(t) { 
   		    msg = t(msg);
   		   msg1 = t(msg1); 
   		   msg2 = t(msg2); 
   		   msg3 = t(msg3);
   		   msg4 = t(msg4);
   		   msg5 = t(msg5);
   		   msg6 = t(msg6);
   		   },"../static/i18n");	
		var num = document.getElementById("myform:num").value;
		var partTypeId = document.getElementById("myform:partTypeIdInput").value;
		if(partTypeId==null || partTypeId=="undefined" || partTypeId==""){
			jAlert(msg);
			return false;
		}
		if(num==null || num=="undefined" || num==""){
			jAlert(msg1);
			return false;
		}
		var taskNum = document.getElementById("myform:taskNum").value;
		if(taskNum==null || taskNum=="undefined" || taskNum==""){
			jAlert(msg2);
			return false;
		}
		var startDate = document.getElementById("myform:startDateInputDate").value;
		var endDate = document.getElementById("myform:endDateInputDate").value;
		if(startDate==null || startDate=="undefined" || startDate==""){
			jAlert(msg3);
			return false;
		}
		if(endDate==null || endDate=="undefined" || endDate==""){
			jAlert(msg4); 
			return false;
		}
	 	var sDate = new Date(startDate.replace(/\-/g, "\/"));
	 	var eDate = new Date(endDate.replace(/\-/g, "\/"));
	 	if(sDate>eDate){
	 		jAlert(msg5); 
			return false;	 		
	 	}
		
       	var reg = new RegExp("^[0-9]*$");
       	var obj = document.getElementById("myform:num");
	    if(!reg.test(obj.value)){
	    	jAlert(msg6, null, function() {
	    		return;
	    	});
	    }
		
    }
    
    function cxbtn2(){
		document.getElementById("myform:btn_search2").click();
	}
    
    function cxbtn1(){
		document.getElementById("myform:btn_search1").click();
	}
    
    function cxbtn3(){
		document.getElementById("myform:btn_search3").click();
		parent.document.getElementById("jobdispatchadd2").src="";
		parent.document.getElementById("jobdispatchadd2").style.visibility="hidden";
	    parent.document.getElementById("jobdispatchaddshadow").style.display="none";
	}
    
    function cxbtn4(){
    	 var msg5 = "content.msg5";
    	 dataTranslate("jobdispatch_add", function(t) { 
    		   msg5 = t(msg5);
    		   },"../static/i18n");	
    	var startDate1 = document.getElementById("myform:startDateInputDate").value;
		var endDate1 = document.getElementById("myform:endDateInputDate").value;
		var sDate1 = new Date(startDate1.replace(/\-/g, "\/"));
	 	var eDate1 = new Date(endDate1.replace(/\-/g, "\/"));
	 	if(sDate1>eDate1){
	 		jAlert(msg5); 
					
	 	}else{
	 		document.getElementById("myform:btn_search4").click();
	 	}
	   }
    function cxbtn5(){
    	var isSuccess=document.getElementById("myform:isSuccess").value;
    	
    	var msg7 = "content.msg7";
    	dataTranslate("jobdispatch_add", function(t) { 
   		   msg7 = t(msg7);
   		   },"../static/i18n");	 
   	     
      	if(null!=isSuccess&&isSuccess==msg7){
      		jAlert(isSuccess, null, function() {
      		  parent.document.getElementById("jobdispatchadd2").style.visibility="hidden";
    		  parent.document.getElementById("jobdispatchaddshadow").style.display="none";
    		  parent.location.reload(); 
      		});
	    }else{
	    	var msg8 = "content.msg8";
	   	    dataTranslate("jobdispatch_add", function(t) { 
	   		   msg8 = t(msg8);
	   		   },"../static/i18n");	
	   	 jAlert(msg8);
	    }
    	
		 
	}