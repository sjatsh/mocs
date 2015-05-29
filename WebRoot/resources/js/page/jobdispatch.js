//甘特图 图片大小 
function init() {
	for(i in document.images){
		disableDraggingFor(document.images[i]);
	}
}
 
function disableDraggingFor(element) {
	element.draggable = false;
	element.onmousedown = function(event) {
    	event.preventDefault();
	    return false;
  	};
}
	
function setValueforHidd(){//对隐藏域进行赋值，以便jsf的ajax向控制bean传人参数
	var partid = document.getElementById("select_box_job_part_hdn").value;
	var equid = document.getElementById("select_box_job_equName_hdn").value;
	var taskNum = document.getElementById("select_box_job_taskNum_hdn").value;
	var jobState = document.getElementById("select_box_job_jobStatus_hdn").value;
	if(!jobState){
		jobState='10,20,30,40,50,80';
	}
	document.getElementById("myform:partTypeId").value=partid;
	document.getElementById("myform:taskNum").value=taskNum;
	document.getElementById("myform:eduTypeId").value=equid;
	document.getElementById("myform:jobState").value=jobState;
}
function chaxun_one(){
	document.getElementById("myform:chaxun_one").click();
}
function chaxun_two(){
	document.getElementById("myform:chaxun_two").click();
}

function onloadTab(loadtab){//甘特图和工单列表切换
	if("tab1"==loadtab){
		document.getElementById("example-container").style.display="block";
		document.getElementById("divtab2").style.display="none";
		document.getElementById("divtab3").style.display="none";
		document.getElementById("div_one").style.display="block";
		document.getElementById("div_two").style.display="block";
		document.getElementById("fenzu").style.visibility="hidden";
	}else{
		document.getElementById("myform:group").value =1;
		document.getElementById("example-container").style.display="none";
		document.getElementById("divtab2").style.display="block";
		document.getElementById("div_one").style.display="block";
		document.getElementById("div_two").style.display="none";
		document.getElementById("fenzu").style.visibility="visible";
	}
}
function sub(tt){
	if("tab1"==tt){
		document.getElementById("myform:loadtab").value="tab1";
		document.getElementById("gttimg").src="./images/tabselected.png"; 
		document.getElementById("gtt").style.color="white";
		document.getElementById("gdlbimg").src="./images/tabselect.png"; 
		document.getElementById("gdlb").style.color="#5a5a5a";
	}else{
		document.getElementById("myform:loadtab").value="tab2";
		document.getElementById("gttimg").src="./images/tabselect.png"; 
		document.getElementById("gtt").style.color="#5a5a5a";
		document.getElementById("gdlbimg").src="./images/tabselected.png"; 
		document.getElementById("gdlb").style.color="white";
		document.getElementById("myform:jobQuery").click();
	}
	onloadTab(tt);
}

/**
 * 判断时间大小
 * @param startdate 开始时间
 * @param enddate 结束时间
 * @returns {boolean}
 */
function dateCompare(startdate,enddate)   {
    var arr=startdate.split("-");
    var starttime=new Date(arr[0],arr[1],arr[2]);

    var arrs=enddate.split("-");
    var lktime=new Date(arrs[0],arrs[1],arrs[2]);

    return starttime.getTime() <= lktime.getTime();

}
 
function querybtn(pattern){
	var loadtab=document.getElementById("myform:loadtab").value;
    var startTime = document.getElementById("myform:startInputDate").value;
    var endTime = document.getElementById("myform:endInputDate").value;
    if(!dateCompare(startTime,endTime)){
        jAlert(ext_tip23);
        return;
    }
	if(loadtab == 'tab1'){//甘特图查询
        getTipMsg();
		jobchaxun();//具体业务逻辑在jobdispatch.js
	}else{
		var group = document.getElementById("myform:group").value;
		if(group == 1){
			document.getElementById("myform:chaxun_one").click();
		}else if(group == 2){
			document.getElementById("myform:chaxun_two").click();
		}
	}
	
}

function select(){
	var group = document.getElementById("myform:group").value;
	if(group == 1){//任务号为头
		document.getElementById("divtab3").style.display="none";
		document.getElementById("divtab2").style.display="block";
		document.getElementById("myform:jobQuery").click();
	}else if(group == 2){//设备为头
		document.getElementById("divtab2").style.display="none";
		document.getElementById("divtab3").style.display="block";
		document.getElementById("myform:jobQueryByEqu").click();
	}
}
//table表格字体剧中的问题
function changetr(){
	   	$("#gridview-1028-table>tbody>tr>td>div").each(function(){
	   		var h=$(this).height();
	   		
	   	   $(this).css("line-height",h+"px");
	   	  
	   	});
	   	}
	
function openUrl(url) {    
    features="height=858,width=1331,top:200,left:200,status=no,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=no";             
    winId=window.open(url,'addpage2',features); 
}
var zflag=1;

function switchV()
{
   switch(arguments[0])
   {
      case "week":ds.switchViewPreset("weekAndDay",start,end);
                  zflag=1;
                  ds.scrollToDate(new Date(),true);
      break;
      case "month":ds.switchViewPreset("monthAndYear",start,end);
                   zflag=2;
                   ds.scrollToDate(new Date(),true);
      break;
      case "year":ds.switchViewPreset("year",start,end);
                  zflag=3;
                  ds.scrollToDate(new Date(),true);
      break;
   }
}

function enlargeOrnarrow()
{
     //判断是选择了放大还是缩小按钮
     if(arguments[0]=="enlarge")
     {
         if(zflag==1)
         {
            ds.switchViewPreset("monthAndYear",start,end);zflag=2;
            ds.scrollToDate(new Date(),true);
            return;
         }
         if(zflag==2)
         {
            ds.switchViewPreset("year",start,end);zflag=3;
            ds.scrollToDate(new Date(),true);
            return;
         }
     }
     else
     {
         if(zflag==3)
         {
            ds.switchViewPreset("monthAndYear",start,end);zflag=2;
            ds.scrollToDate(new Date(),true);
            return;
         }
         if(zflag==2)
         {
	        ds.switchViewPreset("weekAndDay",start,end);zflag=1;
	        ds.scrollToDate(new Date(),true);
	        return;
         }
     }
}

function pageRelocation()
{getTipMsg();
   switch(arguments[0])
   {
      case "edit"://编辑工单
    	  if(editPlanId==-1)
          {
                jAlert(ext_tip8);
          }
          else
          {
              if(statusId==30 || statusId == 20 || statusId ==80)
              {
                 //openUrl("./jobdispatch/jobdispatch_updata.faces?editPId="+editPlanId);	
            	 document.getElementById("jobdispatchupdateshadow").style.display="block";
            	 //document.getElementById("jobdispatchupdate2").style.visibility="visible";
     			 document.getElementById("jobdispatchupdate2").src="./jobdispatch/jobdispatch_updata.faces?editPId="+editPlanId
              }
              else
              {
            	  jAlert(ext_tip22);
              } 
          }
      break;
    
      case "statistic": //工单统计
    	   openUrl("./jobdispatchstatics/jobdispatchstatics.faces?editPId="+editPlanId);
    
      break;
      
      case "add":   //工单添加 
    	     document.getElementById("jobdispatchaddshadow").style.display="block";
    	     //document.getElementById("jobdispatchadd2").style.visibility="visible";
			 document.getElementById("jobdispatchadd2").src="./jobdispatch/jobdispatch_add.faces"
      break;
   }
}

function pageLoad(){
	if(editPlanId==-1)
    {
		jAlert(ext_tip8);
    }else{
    	openUrl("./jobdispatch/jobdispatchNumEdit.faces?jobdispatchId="+editPlanId); 
    }
}

//工单列表的各种操作，并对需要的数据进行赋值
function changeStatus(status, jobdispatchid, pipstatus,equipment,partname,plannum,no,goodQuantityNum){
	goodQuantity = goodQuantityNum;
	equSerialNo = equipment;
	partName = partname;
	planNum = plannum;
	processNum = plannum;
	dispatchNo = no;
	editPlanId = jobdispatchid;
	statusId = pipstatus;
	if(status=='edit')
		return pageRelocation(status);
	var flag = switchStatus(status);//当返回false时点击的时取消阿牛
	return flag;
}
var ext_tip1 = "";
var ext_tip2 = "";
var ext_tip3 = "";
var ext_tip4 = "";
var ext_tip5 = "";
var ext_tip6 = "";
var ext_tip7 = "";
var ext_tip8 = "";
var ext_tip9 = "";
var ext_tip10 = "";
var ext_tip11 = "";
var ext_tip12 = "";
var ext_tip13 = "";
var ext_tip14 = "";
var ext_tip15 = "";
var ext_tip16 = "";
var ext_tip17 = "";
var ext_tip18 = "";
var ext_tip19 = "";
var ext_tip20 = "";
var ext_tip21 = "";
var ext_tip22 = "";
var ext_tip23 = "";
var ext_tip24 = "";
var ext_tip25 = "";
var ext_tip26 = "";
var ext_tip27 = "";
var ext_tip28 = "";
var ext_tip29 = "";
var ext_tip30 = "";
function getTipMsg(){
    dataTranslate("jobdispatch", function(t) {
        ext_tip1 = t("content.ext_tip1");
        ext_tip2 = t("content.ext_tip2");
        ext_tip3 = t("content.ext_tip3");
        ext_tip4 = t("content.ext_tip4");
        ext_tip5 = t("content.ext_tip5");
        ext_tip6 = t("content.ext_tip6");
        ext_tip7 = t("content.ext_tip7");
        ext_tip8 = t("content.ext_tip8");
        ext_tip9 = t("content.ext_tip9");
        ext_tip10 = t("content.ext_tip10");
        ext_tip11 = t("content.ext_tip11");
        ext_tip12 = t("content.ext_tip12");
        ext_tip13 = t("content.ext_tip13");
        ext_tip14 = t("content.ext_tip14");
        ext_tip15 = t("content.ext_tip15");
        ext_tip16 = t("content.ext_tip16");
        ext_tip17 = t("content.ext_tip17");
        ext_tip18 = t("content.ext_tip18");
        ext_tip19 = t("content.ext_tip19");
        ext_tip20 = t("content.ext_tip20");
        ext_tip21 = t("content.ext_tip21");
        ext_tip22 = t("content.ext_tip22");
        ext_tip23 = t("content.ext_tip23");
        ext_tip24 = t("content.ext_tip24");
        ext_tip25 = t("content.ext_tip25");
        ext_tip26 = t("content.ext_tip26");
        ext_tip27 = t("content.ext_tip27");
        ext_tip28 = t("content.ext_tip28");
        ext_tip29 = t("content.ext_tip29");
        ext_tip30 = t("content.ext_tip30");
    });
}
function switchStatus(){
    getTipMsg();
	if(arguments[0]=="dispatch"){//派工
		if(!(editPlanId==-1)){
			if(statusId==20){
				Ext.Ajax.request({
					url:"./jobdispatch/checkDispathById.action",
					method:"post",
					params:{
						dispatchNo:dispatchNo
					},
					success:function(response){
						var obj=Ext.decode(response.responseText);
						if(obj.flag){
							jConfirm(ext_tip1+obj.equSerialNo+ext_tip2, null, function(rs) {
								if(!rs){
									return false;
								}
								else {
									Ext.Ajax.request({
										url:"./jobdispatch/updateJobdispatchStatus.action",
										params:{
											dispatchId:editPlanId,
											status:30,
											flag:0
									 	},
									 	success:function(response){
											 var result=response.responseText;
											 if(result=="true"){
												 jAlert(ext_tip3);
												 ds.getEventStore().reload();
												 //1,状态更改成功后，还选中甘特图数据
												 //2,状态更改后更新当前状态。
												 statusId = 30;
//		 											 editPlanId=-1;
											 }else if(result=="false"){
												 jAlert(ext_tip4);
											 }
										 },
										 failure:function(response,opts){
											 jAlert(ext_tip5);
										 }
									});
								}
							});
						}else{
							jAlert(ext_tip6);
						}
					},
					failure:function(response,opts){
						jAlert(ext_tip5);
					}
				});
			}else{
				jAlert(ext_tip7);
			}
		}else{
			jAlert(ext_tip8);
        }
	}

   if(arguments[0]=="delete"){
	   
       if(statusId==30||statusId==20)
	   {
    	   jConfirm(ext_tip9, null, function(rs) {
    		   if(rs) {
					Ext.Ajax.request({
						url:"./jobdispatch/delete.action",//获取设备资源信息
						method:"post",
					    params:{
							dispatchId:editPlanId
					    },
					    success:function(response){
							 ds.getEventStore().reload();
							 editPlanId=-1;
					    },
					    failure:function(response,opts){
					    	jAlert(ext_tip5);
						}
					});
				}else{
					return false;
				}
    	   	});
			   
       }else {
    	   jAlert(ext_tip10);
       }
  }

	if(arguments[0]=="start")//工单启动  
    {
        if(!(editPlanId==-1))
        {
             if(statusId==30||statusId==80||statusId==70)
             {  
                  if(statusId==30)
                  {
						Ext.Ajax.request({
							url:"./jobdispatch/checkDispathById.action",
							method:"post",
							params:{
								dispatchNo:dispatchNo
							},
							success:function(response){
								var obj=Ext.decode(response.responseText);
								jConfirm(ext_tip11+obj.equSerialNo+ext_tip12+partName+ext_tip13+planNum+ext_tip14, null, function(rs) {
									if(!rs){
										return false;
									}
									else {
										var flag=0;//0：不启动同批次工单   1：启动
										Ext.Ajax.request({
											url:"./jobdispatch/updateJobdispatchStatus.action",//获取设备资源信息
											method:"post",
											params:{
												dispatchId:editPlanId,
												status:40,
												flag:flag
										 },
										 success:function(response){
											 var result=response.responseText;
											 if(result=="true")
											 {
												 jAlert(ext_tip15);
												 ds.getEventStore().reload();
												 //1,状态更改成功后，还选中甘特图数据
												 //2,状态更改后更新当前状态。
												 statusId = 40;
//		 											 editPlanId=-1;
											 }
											 else if(result=="false")
											  {
												 jAlert(ext_tip16);
											 }
										 },
										 failure:function(response,opts){
											 jAlert(ext_tip5);
											}
										}); 
									}
								});
							}
						});
						   
                  }
                  else
                  {
                	  jConfirm(ext_tip17+processNum+ext_tip18+goodQuantity+ext_tip19, null, function(rs) {
                		  if(rs){
                 	 		 var flag = 0;
                   		  	 Ext.Ajax.request({
                    				url:"./jobdispatch/updateJobDispatchWhenStop.action",//当关闭时更改工单状态
                    				method:"post",
                    				params:{
     	                 			jobdispatchId:editPlanId,
     	                 			status:60,
     	                 			flag:flag
                      			},
                      			success:function(response){
                      				var result=response.responseText;
                      				if(result=="true")
     								{
                      					jAlert(ext_tip20);
     									ds.getEventStore().reload();
     									 //1,状态更改成功后，还选中甘特图数据
     									 //2,状态更改后更新当前状态。
     									statusId = 60;
//      										editPlanId=-1;
     								}
     								else if(result=="false")
     								{
     									jAlert(ext_tip21);
     								}
     		                 	},
                      			failure:function(response,opts){
                      				jAlert(ext_tip5);
                      			}
                    			}); 
                      	  }else{
                      		  return false;
                      	  }
                	  });
            	 	  
                 }
             }
             else
             {
            	 jAlert(ext_tip24);
	         }
        }
        else
        {
        	jAlert(ext_tip8);
        }
    }
    
    if(arguments[0]=="pause")//工单暂停与恢复
    {
        if(!(editPlanId==-1))
        {
             if(statusId==50 || statusId==40)//暂停
             {   
            	 jConfirm(ext_tip25, null, function(rs) {
            		 if(rs){
             		   	 var flag = 0;
    	            	 Ext.Ajax.request({
    						url:"./jobdispatch/updateJobDispatchWhenPause.action",
    						method:"post",
    						params:{
    							dispatchId:editPlanId,
    							status:80,
    							flag:flag
    						},
    						success:function(response){
    							var result=response.responseText;
    							if(result=="true")
    							{
    								jAlert(ext_tip26);
    								ds.getEventStore().reload();
    								 //1,状态更改成功后，还选中甘特图数据
    								 //2,状态更改后更新当前状态。
    								statusId = 80;
//     									editPlanId=-1;
    							}
    							else if(result=="false")
    							{
    								jAlert(ext_tip27);
    							}
    						},
    						failure:function(response,opts){
    							jAlert(ext_tip5);
    						}
    					});
             		}else{
             			return false;
             		}
            	 });
         		
             }
             else if(statusId==80)//恢复
             {
            	 var flag = 0;
            	 Ext.Ajax.request({
 					url:"./jobdispatch/updateJobdispatchWhenRecover.action",
 					method:"post",
 					params:{
 						dispatchId:editPlanId,
 						status:50,
 						flag:flag
 					},
 					success:function(response){
 						var result=response.responseText;
 						if(result=="true")
 						{
 							jAlert(ext_tip28);
 							ds.getEventStore().reload();
 							 //1,状态更改成功后，还选中甘特图数据
							 //2,状态更改后更新当前状态。
 							statusId=50;
// 								editPlanId=-1;
 						}
 						else if(result=="false")
 						{
 							jAlert(ext_tip29);
 						}
 					},
 					failure:function(response,opts){
 						jAlert(ext_tip5);
 					}
 				});
             }else{
            	 jAlert(ext_tip30);
             }
        }
        else
        {
        	jAlert(ext_tip8);
        }
    }
    return true;
}

$(document).ready(function(){
	//国际化处理
	dataTranslate("jobdispatch", function(t) { 
		
		if(i18n.options.lng == 'zh'){
			$("*[data-i18n]").i18n();
		}else{
			$("#control_week").find(".zl-content-top-info-down-btn-img").attr("src", "images/jobplan/icon_week-02.png");
			$("#control_month").find(".zl-content-top-info-down-btn-img").attr("src", "images/jobplan/icon_month-02.png");
			$("#control_year").find(".zl-content-top-info-down-btn-img").attr("src", "images/jobplan/icon_year-02.png");
		   $("*[data-i18n]").i18n();
		}
		
	});

	//页面选择器加载
	$("#select_box_job_part").selectboxs();
	$("#select_box_job_jobStatus").selectboxs();
	$("#select_box_job_taskNum").selectboxs();
	$("#select_box_job_equName").selectboxs();
	
    //显示控制按钮权限
    if($("#myform\\:viewDisabled").val() == "false") {
    	//$("#viewDisabled").hide();
    	$("#control_info_edit,#control_scrap,#control_add,#control_edit,#control_delete,#control_ok,#control_start,#control_pause,#control_week,#control_month,#control_year").css("cursor", "default").attr("onclick", "return false;");
    }
    
	//页面大小调整
	$.autosizeExclude($("#img4")[0], false);
	$.autosizeExclude($("#img5")[0], false);
	$.autosizeExclude($("#img6")[0], false);
	$.autosizeExclude($("#example-container")[0], true);
	//$.autosizeExclude($("#divtab2")[0], true);
	 
});


//页面内按钮国际化
function transTableContent() {
	$(".ui-tt-c>span>a").each(function() {
		$(this).attr("data-i18n", $(this).text()).i18n();
	});
}


//跳转当前页
//sub("tab1");




