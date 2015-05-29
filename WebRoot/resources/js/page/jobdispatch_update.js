$(document).ready(function() {
	parent.document.getElementById("jobdispatchupdate2").style.visibility="visible";
	//选择框省略方法
	$(".zl-select select").selectAutoHide(10);	
	$("table>tbody>tr>td>div>a").attr("data-i18n", function() {
		return $(this).text();
	}); 	
	$.autosizeExclude($(".jobdispatch-content-info-tubiao1>*")[0], true);
	//国际化翻译
	dataTranslate("jobdispatch_update", function(t) { $("*[data-i18n]").i18n();},"../static/i18n");
	$.autosizeInit(1325,760,800,458);
	$.autosizeAll();
	$.autosizeMore();  	
});   
function tishi(){
	var msg = "content.msg";
	   dataTranslate("jobdispatch_update", function(t) { 
		   msg = t(msg);
		   },"../static/i18n");	 

	   jAlert(msg);
}	           
function checkEquSerialNum(){
	var msg1 = "content.msg1";
	var msg2 = "content.msg2";
	   dataTranslate("jobdispatch_update", function(t) { 
		   msg1 = t(msg1);
		   msg2 = t(msg2);
		   },"../static/i18n");	 
    var jobdispatchlistId = document.getElementById("myform:jobdispatchlistId").value;
    var flag;
	$.ajax({
			url:"../jobdispatch/checkEquSerialNum.action",
		method:"post",
		dataType: "json",//返回json格式的数据
		async:false,
			data:{
				jobdispatchlistId:jobdispatchlistId
			},
			success:function(response){
			var obj=response;
				if(!obj.flag){
					jAlert(msg1);
				}
				flag = obj.flag;
			},
			error:function(){
				jAlert(msg2);
		}
		});
	return flag;
}

//检测工单状态
function checkStatus(){
	
	var msg2 = "content.msg2";
	var msg3 = "content.msg3";
	var msg4 = "content.msg4";
	dataTranslate("jobdispatch_update", function(t) { 
	    msg2 = t(msg2);
	    msg3 = t(msg3);
	    msg4 = t(msg4);
	},"../static/i18n");	 
	jConfirm(msg3, null, function(rs) {
		if(rs){
			var jobdispatchlistId = document.getElementById("myform:jobdispatchlistId").value;
			var flag;
			$.ajax({
					url:"../jobdispatch/checkJobDispatchStatus.action",
				method:"post",
				dataType: "json",//返回json格式的数据
				async:false,
					data:{
						jobdispatchlistId:jobdispatchlistId
					},
					success:function(response){
					var obj=response;
						if(!obj.flag){
							jAlert(msg4);
						}else{
							
						}
						flag = obj.flag;
					},
				failure:function(response,opts){
					jAlert(msg2);
				}
				});
			return flag;
		}
	});
	return false;
	
}
//更改工单状态
function changeStatus(jobdispatchlistId,status){
	var msg = "content.msg";
	var msg5 = "content.msg5";
	var msg6 = "content.msg6";
	   dataTranslate("jobdispatch_update", function(t) { 
		    msg = t(msg);
		    msg5 = t(msg5);
		    msg6 = t(msg6);
		   },"../static/i18n");	 
	   $.ajax({
		   url:"../jobdispatch/changeJobDispatchStatus.action",
		   method:"post",
			dataType: "json",//返回json格式的数据
			async:false,
			data:{
				jobdispatchlistId:jobdispatchlistId,
				status:status
			},
			success:function(response){
				var obj=response;
				if(obj.flag){
					if(status==40)
						jAlert(msg);
					else
						jAlert(msg5);
				}
			},
			failure:function(response,opts){
				if(status==40)
					jAlert(msg);
				else
					jAlert(msg6);
			}
	   });
//	Ext.Ajax.request({
//			url:"../jobdispatch/changeJobDispatchStatus.action",
//		method:"post",
//			params:{
//				jobdispatchlistId:jobdispatchlistId,
//				status:status
//			},
//			success:function(response){
//			var obj=Ext.decode(response.responseText);
//				if(obj.flag){
//					if(status==40)
//						alert(msg);
//					else
//						alert(msg5);
//				}
//			},
//		failure:function(response,opts){
//			if(status==40)
//				alert(msg);
//			else
//				alert(msg6);
//		}
//		});
}

function dispatchJob(){
	
	var msg7 = "content.msg7";
	var msg8 = "content.msg8";
	   dataTranslate("jobdispatch_update", function(t) { 
		    msg7 = t(msg7);
		    msg8 = t(msg8);
		   },"../static/i18n");	
	var jobStatus = document.getElementById("myform:jobStatus").value;
	if(jobStatus!="20"){
		jAlert(msg7);
	}else{
		jConfirm(msg8, null, function(rs) {
			if(rs){
				var jobdispatchlistId = document.getElementById("myform:jobdispatchlistId").value;
				changeStatus(jobdispatchlistId,30);
			}
		});
	}
	return false;
}
//检测添加的设备是否存在
function checkData(){
	var msg9 = "content.msg9";
	dataTranslate("jobdispatch_update", function(t) { 
		   msg9 = t(msg9);
		   },"../static/i18n");	
	var serialNo = document.getElementById("myform:job_dispatch_add").value;
	var jobdispatchlistId = document.getElementById("myform:jobdispatchlistId").value;
	var flag;
	$.ajax({
			url:"../jobdispatch/checkEquSerailNo.action",
		method:"post",
		dataType: "json",//返回json格式的数据
		async:false,
			data:{
				jobdispatchlistId:jobdispatchlistId,
				serialNo:serialNo
			},
			success:function(response){
			var obj=response;
			if(obj.flag){
				jAlert(msg9);
			}
			flag = !obj.flag;
			}
		});
	return flag;
}
 function cxbtn1(){
		document.getElementById("myform:btn_search1").click();
}
function cxbtn3(){
	document.getElementById("myform:btn_search3").click();
	parent.document.getElementById("jobdispatchupdate2").src="";
	parent.document.getElementById("jobdispatchupdate2").style.visibility="hidden";
	parent.document.getElementById("jobdispatchupdateshadow").style.display="none";
}
function cxbtn4(){
  var msg11 = "content.msg11";
	  var msg12 = "content.msg12";
	  var msg13 = "content.msg13";
	  var msg14 = "content.msg14";
  var msg15 = "content.msg15";
  var msg16 = "content.msg16";
	  dataTranslate("jobdispatch_update", function(t) { 
		   msg11 = t(msg11);
		   msg12 = t(msg12);
		   msg13 = t(msg13);
		   msg14 = t(msg14);
		   msg15 = t(msg15);
       msg16 = t(msg16);
		   },"../static/i18n");	
	 
	  var jobdispatchlistName=document.getElementById("myform:jobdispatchlistName").value;
   	  var jobdispatchpartName=document.getElementById("myform:jobdispatchpartName").value;
      var jobdispatchlistNum=document.getElementById("myform:jobdispatchlistNum").value;
   	  var jobdispatchlistStartDate=document.getElementById("myform:jobdispatchlistStartDateInputDate").value;
   	  var jobdispatchlistEndDate=document.getElementById("myform:jobdispatchlistEndDateInputDate").value;
   	  var tt=dateCompare(jobdispatchlistStartDate,jobdispatchlistEndDate);
   	    if(null==jobdispatchpartName||""==jobdispatchpartName){
   	    	jAlert(msg12);	
   		}else if(null==jobdispatchlistNum||""==jobdispatchlistNum){
   			jAlert(msg13);	
   		}else if(null==jobdispatchlistStartDate||""==jobdispatchlistStartDate){
   			jAlert(msg14);	
   		}else if(null==jobdispatchlistEndDate||""==jobdispatchlistEndDate){
   			jAlert(msg15);
   		}else if(!tt){
   			jAlert(msg16);
   		}else if(null==jobdispatchlistName||""==jobdispatchlistName){
   			jAlert(msg11);	
   		}else{
		    document.getElementById("myform:btn_search4").click();
   	  }
}
function dateCompare(startdate,enddate)   {
    var arr=startdate.split("-");
    var starttime=new Date(arr[0],arr[1],arr[2]);

    var arrs=enddate.split("-");
    var lktime=new Date(arrs[0],arrs[1],arrs[2]);

    return starttime.getTime() <= lktime.getTime();
    
}
function cxbtn5(){
  var msg10 = "content.msg10";
   dataTranslate("jobdispatch_update", function(t) { 
    		   msg10 = t(msg10);
    		   },"../static/i18n");	 
   jAlert(msg10, null, function() {
	   parent.document.getElementById("jobdispatchupdate2").style.visibility="hidden";	 
	   parent.document.getElementById("jobdispatchupdateshadow").style.display="none";
	   parent.location.reload();  
   });
}
function checklistname(){
	  document.getElementById("myform:img1").click();
}
function addserialno(){
  	  document.getElementById("myform:img2").click();
}
function chongfu(){
	var msg00 = document.getElementById("myform:msg").value;
	if(msg00==null || msg00=="" ){
  		
  	  }else{
  		jAlert(msg00);	
  	  }
 }