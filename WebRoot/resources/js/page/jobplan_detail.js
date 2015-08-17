//页面原有JS
function jobplanadd1(){
		 var plantype=document.getElementById("myform:plantype").value;
		 var id=document.getElementById("myform:flgId").value; 
		 var planId=document.getElementById("myform:planId").value;
		 var partId1=document.getElementById("myform:partId1").value;
		 var StartDate=document.getElementById("myform:subjobplanStartDate").value;
		 var EndDate=document.getElementById("myform:subjobplanEndDate").value;
		
	    if(plantype=="1"){
			 document.getElementById("jobaddshadow").style.display="block";
			 //document.getElementById("jobplanadd2").style.visibility="visible";
			 document.getElementById("jobplanadd2").src="./jobplan/jobplan_add.faces?planId="+planId+" "
			 document.getElementById("myform:plantype").value=null;
		 }else if(plantype=="2"){
			 
			 document.getElementById("jobdispatchaddshadow").style.display="block";
			 //document.getElementById("jobdispatchadd2").style.visibility="visible";
			 document.getElementById("jobdispatchadd2").src="./jobdispatch/jobdispatch_add.faces?partId="+partId1+"&StartDate="+StartDate+"&EndDate="+EndDate+"&planId="+planId+""
			 document.getElementById("myform:plantype").value=null;
		}else{
			 if(null!=id&&""!=id){
				document.getElementById("jobaddshadow").style.display="block";
				//document.getElementById("jobplanadd2").style.visibility="visible";
				document.getElementById("jobplanadd2").src="./jobplan/jobplan_add.faces?partId="+id+" "
				document.getElementById("myform:flgId").value=null;
			 }else{
				 document.getElementById("jobaddshadow").style.display="none";
				 
			 }
	}
}
function importadd(){
	 document.getElementById("taskimportshadow").style.display="block";
	 //document.getElementById("taskimport").style.display="block";
	 document.getElementById("taskimport2").src="./erp/import_add.faces "
	
}



$(document).ready(function(){
	//国际化处理
	dataTranslate("jobplan_detail", function(t) { 
		
		if(i18n.options.lng == 'zh'){
			$("*[data-i18n]").i18n();
		}else{
			$("#control_week").find(".zl-content-top-info-down-btn-img").attr("src", "images/jobplan/icon_week-02.png");
			$("#control_month").find(".zl-content-top-info-down-btn-img").attr("src", "images/jobplan/icon_month-02.png");
			$("#control_year").find(".zl-content-top-info-down-btn-img").attr("src", "images/jobplan/icon_year-02.png");
		   $("*[data-i18n]").i18n();
		}
		
	});
	
	 
	//选项渲染
	$("#select_box").selectboxs();
	$("#select_box_part").selectboxs();
	
    //显示控制按钮权限
//    if($("#myform\\:viewDisabled").val() == "false") {
    	/*$("#viewDisabled").hide();*/
//    	$("#control_add,#control_edit,#control_del,#control_status,#control_week,#control_month,#control_year,#control_big,#control_small,#control_import,.zl-button-text").css("cursor", "default").attr("onclick", "return false;");
//    }
    
    //选择框超出显示
    $(".zl-content-select").mouseover(function() {
    	$(this).attr("title", $(this).find("input[type='text']").val());
    })

	//页面大小调整
	//$.autosizeExclude($("#example-container")[0], true);
    
});
		
var zflag = 1;

function switchV() {
	switch (arguments[0]) {
	case "week":
		ds.switchViewPreset("weekAndDay", start, end);
		zflag = 1;
		ds.scrollToDate(new Date(), true);
		break;
	case "month":
		ds.switchViewPreset("monthAndYear", start, end);
		zflag = 2;
		ds.scrollToDate(new Date(), true);
		break;
	case "year":
		ds.switchViewPreset("year", start, end);
		zflag = 3;
		ds.scrollToDate(new Date(), true);
		break;
	}
}

function pageRelocation() {
    getTipMsg();
	switch (arguments[0]) {
	case "edit":
		if (editPlanId == -1) {
			 jAlert(tipTitle1);
		} else {
			if(planType==1){//当为作业计划时，不判断状态
				
				//openUrl("./jobplan/jobplan_updata.faces?editPId="+editPlanId);
				    document.getElementById("jobupdateshadow").style.display="block";
					//document.getElementById("jobplanupdate2").style.visibility="visible";
					document.getElementById("jobplanupdate2").src="./jobplan/jobplan_updata.faces?editPId="+editPlanId+" "
				editPlanId = -1;
			}else{
				if (statusId == 60 || statusId == 70) {
					jAlert(tipTitle2);
				} else {
					//openUrl("./jobplan/jobplan_updata.faces?editPId="+editPlanId);
					 document.getElementById("jobupdateshadow").style.display="block";
					 //document.getElementById("jobplanupdate2").style.visibility="visible";
				     document.getElementById("jobplanupdate2").src="./jobplan/jobplan_updata.faces?editPId="+editPlanId+" "
					editPlanId = -1;
				}
			}
		}
		break;
	case "control":
		if (editPlanId == -1) {
			Ext.Msg.alert(tipTitle3, tipTitle1, function() {
			});
		} else {
			openUrl("./jobplan/jobplan_control.faces?editPId="+ editPlanId);
			editPlanId = -1;
		}
		break;
	case "statistic":
		openUrl("./jobplan/jobplan_statistics.faces");
		editPlanId = -1;
		break;
	}
}

function deljobPlan() {
    getTipMsg();
	if (editPlanId == -1) {
		jAlert(tipTitle1);
	} else {
		if (statusId == 10) {
			jConfirm(tipTitle4, null, function(rs) {
				if(!rs){
					return;
				}
				else {
					Ext.Ajax.request({
		                url : "./jobplan/delete.action",
		                method : "post",
		                params : {
		                    id : editPlanId
		                },
		                success : function(response,opts) {
		                     var obj = Ext.decode(response.responseText);
		                            if(obj.success) {
		                            	jAlert(tipTitle5);
		                                editPlanId = -1;
		                                query('true');
		                            }else {
		                            	jAlert(tipTitle6);
		                            }
		                         },
		                failure : function(response,opts) {
		                         alert(tipTitle7);
		                }
		            });
				}
			});
		} else {
			jAlert(tipTitle8);
		}
	}
}

function openUrl(url) {
	features = "height=858,width=1331,top:200,left:200,status=no,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=no";
	winId = window.open(url, 'addpage', features);
}
var tipTitle1 = "";
var tipTitle2 = "";
var tipTitle3 = "";
var tipTitle4 = "";
var tipTitle5 = "";
var tipTitle6 = "";
var tipTitle7 = "";
var tipTitle8 = "";
var tipTitle9 = "";
var tipTitle10 = "";
var tipTitle11 = "";
var tipTitle12 = "";
var tipTitle13 = "";
var tipTitle14 = "";
var tipTitle15 = "";
function getTipMsg(){
    dataTranslate("jobplan_detail", function(t) {
        tipTitle1 = t("content.tipTitle1");
        tipTitle2 = t("content.tipTitle2");
        tipTitle3 = t("content.tipTitle3");
        tipTitle4 = t("content.tipTitle4");
        tipTitle5 = t("content.tipTitle5");
        tipTitle6 = t("content.tipTitle6");
        tipTitle7 = t("content.tipTitle7");
        tipTitle8 = t("content.tipTitle8");
        tipTitle9 = t("content.tipTitle9");
        tipTitle10 = t("content.tipTitle10");
        tipTitle11 = t("content.tipTitle11");
        tipTitle12 = t("content.tipTitle12");
        tipTitle13 = t("content.tipTitle13");
        tipTitle14 = t("content.tipTitle14");
        tipTitle15 = t("content.tipTitle15");
    });
}
function switchStatus() {
    getTipMsg();
	if (!(editPlanId == -1)) {
		if(planType == 1 ) { jAlert(tipTitle9);return;}
		if (statusId == 10 || statusId == 20) {
			jConfirm(tipTitle10, null, function(rs) {
				if(!rs){
					return;
				}
				else {
					if (statusId == 10) {
						Ext.Ajax.request({
		                    url : "./jobplan/updateJobPlanInfoStatus.action",
		                    method : "post",
		                    params : {
		                        jobPlanId : editPlanId,
		                        status : 40
		                    },
		                    success : function(response) {
		                        var result = response.responseText;
		                        if (result == "true") {
		                        	jAlert(tipTitle11);
		                            query('true');
		                             //1,状态更改成功后，还选中甘特图数据
		                             //2,状态更改后更新当前状态。
		                             statusId = 40;
		                             //editPlanId = -1;

		                        } else if (result == "false") {
		                        	jAlert(tipTitle12);
		                        }
		                    },
		                    failure : function(response, opts) {
		                    	jAlert(tipTitle13);
		                    }
		                });
					} else if (statusId == 70) {
						Ext.Ajax.request({
		                    url : "./jobplan/updateJobPlanInfoStatus.action",
		                    method : "post",
		                    params : {
		                        jobPlanId : editPlanId,
		                        status : 60
		                    },
		                    success : function(response) {
		                        var result = response.responseText;
		                        if (result == "true") {
		                            Ext.Msg.alert(tipTitle3,
		                                tipTitle14,
		                                    function() {
		                                        query('true');
		                                        //1,状态更改成功后，还选中甘特图数据
		                                         //2,状态更改后更新当前状态。
		                                        statusId = 60;
		                                    });
		                        } else if (result == "false") {
		                        	jAlert(tipTitle15);

		                        }
		                    },
		                    failure : function(response, opts) {
		                    	jAlert(tipTitle13);
		                    }
		                });

					}
				}
			});
		} else {
			jAlert(tipTitle8);
		}

	} else {
		jAlert(tipTitle1);
	}
}

function showNewPage(arg) {
	var url = "";
	switch (arg) {
	case "statistic":
		url = "./jobplan/jobplan_statistics.faces";
		break;
	}

	$("#showDiv").show();
	$("#disableAll").show();
	$("#ifContent").attr("src", url);
}

function closeIf() {
	$("#showDiv").hide();
	$("#disableAll").hide();
	$("#ifContent").attr("src", "");
}