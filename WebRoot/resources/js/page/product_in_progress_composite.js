$(document).ready(function() {
	$.autosizeExclude($(".table-title")[0], true);
	$.autosizeExclude($(".table-body>div")[0], true);
	$.autosizeFrame(false);
	
	$("option").attr("data-i18n", function() {
		return $(this).text();
	});
	
	//国际化
	dataTranslate("product_in_progress_composite", function(t) { $("*[data-i18n]").i18n();});
    
    $.autosizeReturn(resizeTable);
    
});
function selectChage(){
	$("#myform\\:batchNo").each(function() {
		$.autosizeElement(this,true);
	});
}
function querybtn(){
	document.getElementById("myform:btn_search").click();
}
function resizeTable() {
	//重算表格位置
	var tableBodyTop =0;
	$(".table-body").css({
		top: tableBodyTop + "px",
		height: $(".zl-content-info").height() - tableBodyTop + "px"
	});

	if($(".table-body").hasClass("mCustomScrollbar")) {
		$(".table-body").mCustomScrollbar("update");
	}
	else {
		/* 隐藏式滚动条 */
		$(".table-body").mCustomScrollbar({
		    theme: "dark",
		    scrollInertia: 0,
			scrollbarPosition: "inside",
			autoHideScrollbar: true,
		}).find(".mCSB_container").css({
			marginRight: 0
		});	
	}
}

searchBtn();

//获取工序统计数据
function loadAjaxData(){
	$.ajax({ 
		url: "./productInProgress/getReportData.action", 
		dataType: "json",//返回json格式的数据
		success: function(responseText){
			var obj = responseText;
			$('#divtab2').html(drawTable(obj));
	  	},
	  	complete:function(){
	  		loadClickEvent();
	  	}
	});
}

//工序统计零件生成 
function drawTable(obj){
	var trs="";
	var beginTable = "<div class='ui-datatable ui-treetable ui-widget' style='margin-top: 6px;'><table id='detail_table' class='detail' border='0' cellspacing='0' cellpadding='0' width='100%'>"
	+"<thead>"
	+"<tr>"
			+"<th class='ui-state-default' width='6%'>&nbsp;</th>"
			+"<th class='ui-state-default' width='22%'>&nbsp;</th>"
			+"<th class='ui-state-default' width='15%'>&nbsp;</th>"
			+"<th class='ui-state-default' width='15%'>&nbsp;</th>"
			+"<th class='ui-state-default' width='7%'>&nbsp;</th>"
			+"<th class='ui-state-default' width='7%'>&nbsp;</th>"
			+"<th class='ui-state-default' width='7%'>&nbsp;</th>"
			+"<th class='ui-state-default' width='7%'>&nbsp;</th>"
			+"<th class='ui-state-default' width='7%'>&nbsp;</th>"
			+"<th class='ui-state-default' width='7%'>&nbsp;</th>"
		+"</tr>"
	+"</thead>"
	+"<tbody id='tbody' class='ui-treetable-data ui-widget-content'>";
	var data = obj.data;
	var size = data.length;
	var rowid = 0;
	for(var i=0;i<size;i++){
		var info = data[i];
		if(info.partName != ""){
	 	  	if(i>0){
			 	if(info.partName == data[i-1].partName){
			 		if(info.taskNum != data[i-1].taskNum){
			 			rowid = i;
			 			start="<tr class='parent ui-widget-content default' id='row"+i+"' title='点击这里展开/关闭' style='cursor: pointer;'>"
		 				+"<td style='width:6%;word-break: break-all;'><div class='ui-tt-c'>+/-</div></td>"
		 				part ="<td style='vertical-align:top;border-top: none; border-bottom: none;'><div class='ui-tt-c'>&nbsp;</div></td>";
			 		}else{
				 		start="<tr class='child-row"+rowid+" ui-widget-content default' style='display: none;'>"
					 	  	+"<td style='border-top: none; border-bottom: none;'><div class='ui-tt-c'></div></td>";
				 		part="<td style='vertical-align:top;border:none;'><div class='ui-tt-c'>&nbsp;</div></td>";
			 		}
			 	}else{
			 		rowid = i;
			 		start="<tr class='parent ui-widget-content default' id='row"+i+"' title='点击这里展开/关闭' style='cursor: pointer;'>"
		 				+"<td style='width:6%;word-break: break-all;'><div class='ui-tt-c'>+/-</div></td>"
			 		part ="<td style='vertical-align:top;width:22%;word-break: break-all;'><div class='ui-tt-c'>"+info.partName+"</div></td>";
			 	}
			 	if(info.taskNum == data[i-1].taskNum){
			 		taskNum="<td style='border-top: none; border-bottom: none;'><div class='ui-tt-c'>&nbsp;</div></td>";
	 	  		}else{
	 	  			taskNum="<td style='width:15%;word-break: break-all;'><div class='ui-tt-c'>"+info.taskNum+"</div></td>";
	 	  		}
			 	if(info.processName == data[i-1].processName){
			 		process="<td style='border-top: none; border-bottom: none;'><div class='ui-tt-c'>&nbsp;</div></td>";
			 		planNum = "<td style='border-top: none; border-bottom: none;'><div class='ui-tt-c'>&nbsp;</div></td>";
			 		finish = "<td style='border-top: none; border-bottom: none;'><div class='ui-tt-c'>&nbsp;</dvi></td>";
			 	}else{
			 		process="<td><div class='ui-tt-c'>"+info.processName+"</div></td>";
			 		planNum = "<td><div class='ui-tt-c'>"+info.planNum+"</div></td>";
			 		finish = "<td><div class='ui-tt-c'>"+info.finishNum+"</div></td>";
			 	}
	 	  	}else{
	 	  		start = "<tr class='parent ui-widget-content default' id='row"+i+"' style='cursor: pointer;'>"
	 				+"<td style='width:6%;word-break: break-all;'><div class='ui-tt-c'>+/-</div></td>"
	 	  		part ="<td style='vertical-align:top;width:22%;word-break: break-all;'><div class='ui-tt-c'>"+info.partName+"</div></td>";
	 	  		taskNum = "<td style='width:15%;word-break: break-all;'><div class='ui-tt-c'>"+info.taskNum+"</div></td>";
	 	  		process="<td style='width:15%;word-break: break-all;'><div class='ui-tt-c'>"+info.processName+"</div></td>";
	 	  		planNum = "<td style='width:7%;word-break: break-all;'><div class='ui-tt-c'>"+info.planNum+"</div></td>";
	 	  		finish = "<td style='width:7%;word-break: break-all;'><div class='ui-tt-c'>"+info.finishNum+"</div></td>";
	 	  	}
	 		end ="<td style='width:7%;word-break: break-all;'><div class='ui-tt-c'>"+info.equSerialNo+"</div></td>"
 				+"<td style='width:7%;word-break: break-all;'><div class='ui-tt-c'></div></td>"
 				+"<td style='width:7%;word-break: break-all;'><div class='ui-tt-c'></div></td>";
 			if(info.processName=="")
 				workInNum = "<td style='width:7%;word-break: break-all;'><div class='ui-tt-c'>"+info.workInNum+"</div></td>"
 			else
 				workInNum = "<td style='width:7%;word-break: break-all;'><div class='ui-tt-c'>/</div></td>"
	 		trend = "</tr>";
	 		trs += start+part+taskNum+process+planNum+finish+end+workInNum+trend;
		}
	}
	
	var tbody = trs;
	var endTable = "</tbody>"
				+"</table></div>";
	return beginTable+tbody+endTable;
}

//伸缩表格动作事件
function loadClickEvent(){
	$('tr.parent')
	.css("cursor","pointer")
	.attr("title","点击这里展开/关闭")
	.click(function(){
		$(this).siblings('.child-'+this.id).toggle('normal', function() {
			$(".table-body").mCustomScrollbar("update");
		});
	});
}

//更新滚动条
function updateScroll() {
	$(".table-body").mCustomScrollbar("update");
}

//tab标签点击事件
function sub(tt){
	if("tab1"==tt){
		document.getElementById("myform:loadtab").value="tab1";
		document.getElementById("tab2").style.display = "none";
		document.getElementById("tab3").style.display = "none";
		document.getElementById("myform:group").disabled=true;
		document.getElementById("ctx_group").style.visibility="hidden";
	}else{
		document.getElementById("myform:loadtab").value="tab2";
		document.getElementById("tab2").style.display = "block";
		document.getElementById("tab3").style.display = "block";
		document.getElementById("myform:group").value=1;
		document.getElementById("myform:group").disabled=false;
		document.getElementById("ctx_group").style.visibility="visible";
		loadAjaxData();
	}
	document.getElementById("myform:submt").click();
}

//获取批次统计详细内容
function getData(productName){
	$.ajax({
		url: "./productInProgress/getDataByproductName.action", 
		type:"post",
		data:{partName:productName},
		dataType: "json",//返回json格式的数据
		success: function(responseText){
			var obj = responseText;
			$('#divtab2').html(drawTable(obj));
	  	},
	  	complete:function(){
	  		loadClickEvent();
	  	}
	});
}

//切换工序统计类型
function change(){
	var loadtab=document.getElementById("myform:loadtab").value;
	
	var group = document.getElementById("myform:group").value;
	if(group == 1){
		document.getElementById("tab3").style.display="none";
		document.getElementById("tab2").style.display="block";
		var sTime = document.getElementById("myform:startTimeInputDate").value;
		var eTime = document.getElementById("myform:finishTimeInputDate").value;
		var partName = document.getElementById("myform:productName").value;
		var batchNo = document.getElementById("myform:batchNo").value;
		$.ajax({ 
			url: "./productInProgress/getReportData.action", 
			data:{descParam:"",startTime:sTime,endTime:eTime,partName:partName,batchNo:batchNo},
			dataType: "json",//返回json格式的数据
			success: function(responseText){
				var obj = responseText;
				$('#divtab2').html(drawTable(obj));
		  	},
		  	complete:function(){
		  		loadClickEvent();
		  	}
		});
	}else{
		document.getElementById("tab2").style.display="none";
		document.getElementById("tab3").style.display="block";
		$.ajax({ 
			url: "./productInProgress/getReportData.action", 
			data:{descParam:"",startTime:sTime,endTime:eTime,partName:partName,batchNo:batchNo},
			data:{descParam:"equ_job.equSerialNo"},
			dataType: "json",//返回json格式的数据
			contentType: 'application/json;charset=utf-8',
			success: function(responseText){
				var obj = responseText;
				$('#divtab3').html(drawTable1(obj));
			
		  	},
		  	complete:function(){
		  		loadClickEvent();
		  	}
		});
	}
}

//工序统计设备生成 
function drawTable1(obj){
	var trs="";
	var beginTable = "<div class='ui-datatable ui-treetable ui-widget' style='margin-top: 6px;'><table id='detail_table_equ' class='detail' border='0' cellspacing='0' cellpadding='0' width='100%'>"
	+"<thead>"
	+"<tr>"
		+"<th class='ui-state-default' width='6%'>&nbsp;</th>"
		+"<th class='ui-state-default' width='22%'>&nbsp;</th>"
		+"<th class='ui-state-default' width='17%'>&nbsp;</th>"
		+"<th class='ui-state-default' width='15%'>&nbsp;</th>"
		+"<th class='ui-state-default' width='10%'>&nbsp;</th>"
		+"<th class='ui-state-default' width='10%'>&nbsp;</th>"
		+"<th class='ui-state-default' width='10%'>&nbsp;</th>"
		+"<th class='ui-state-default' width='10%'>&nbsp;</th>"
	+"</tr>"
	+"</thead>"
	+"<tbody id='tbody' class='ui-treetable-data ui-widget-content'>";
	var data = obj.data;
	var size = data.length;
	var rowid = 0;
	for(var i=0;i<size;i++){
		var info = data[i];
	 	  	if(i>0){
	 	  		if(info.equSerialNo == data[i-1].equSerialNo){
	 	  			start="<tr class='child-row"+rowid+" ui-widget-content default' style='display: none;'>"
		 				+"<td style='border-top: none; border-bottom: none;'><div class='ui-tt-c'></div></td>";
	 				equSerialNo ="<td style='vertical-align:top;border-top: none; border-bottom: none;'><div class='ui-tt-c'>&nbsp;</div></td>";
	 	  		}else{
	 	  			rowid=i;
	 	  			start = "<tr class='parent ui-widget-content default' id='row"+i+"' title='点击这里展开/关闭' style='cursor: pointer;'>"
		 				+"<td style='width:6%;word-break: break-all;'><div class='ui-tt-c'>+/-</div></td>";
		 			equSerialNo = "<td style='vertical-align:top;'><div class='ui-tt-c'>"+info.equSerialNo+"</div></td>";
	 	  		}
	 	  		if(info.partName == data[i-1].partName && info.equSerialNo == data[i-1].equSerialNo){
	 	  			part ="<td style='border-top: none; border-bottom: none;'><div class='ui-tt-c'>&nbsp;</div></td>";
	 	  		}else{
	 	  			part="<td><div class='ui-tt-c'>"+info.partName+"</div></td>";
	 	  		}
	 	  		if(info.equSerialNo == data[i-1].equSerialNo && info.partName == data[i-1].partName && info.processName == data[i-1].processName){
// 		 	  		if(info.equSerialNo == data[i-1].equSerialNo && info.processName == data[i-1].processName){
	 	  			processName = "<td style='border-top: none; border-bottom: none;'><div class='ui-tt-c'>&nbsp;</div></td>";
	 	  			plan_finish="<td style='border-top: none; border-bottom: none;'><div class='ui-tt-c'>&nbsp;</div></td>"
		 			+"<td style='border-top: none; border-bottom: none;'><div class='ui-tt-c'>&nbsp;</div></td>"
	 	  		}else{
	 	  			processName="<td><div class='ui-tt-c'>"+info.processName+"</div></td>";
	 	  			plan_finish="<td><div class='ui-tt-c'>"+info.planNum+"</div></td>"
		 			+"  <td><div class='ui-tt-c'>"+info.finishNum+"</div></td>"
	 	  		}
	 	  	}else{
	 	  		rowid=i;
	 	  		start = "<tr class='parent ui-widget-content default' id='row"+i+"' title='点击这里展开/关闭' style='cursor: pointer;'>"
	 				+"<td style='width:6%;word-break: break-all;'><div class='ui-tt-c'>+/-</div></td>";
 				equSerialNo = "<td style='width:22%;word-break: break-all;'><div class='ui-tt-c'>"+info.equSerialNo+"</div></td>";
	 	  		part ="	<td style='width:17%;word-break: break-all;'><div class='ui-tt-c'>"+info.partName+"</div></td>";
	 	  		processName="<td style='width:15%;word-break: break-all;'><div class='ui-tt-c'>"+info.processName+"</div></td>";
	 	  		plan_finish="<td style='width:10%;word-break: break-all;'><div class='ui-tt-c'>"+info.planNum+"</div></td>"
	 			+"<td style='width:10%;word-break: break-all;'><div class='ui-tt-c'>"+info.finishNum+"</div></td>"
	 	  	}
	 		end ="<td style='width:10%;word-break: break-all;'><div class='ui-tt-c'></div></td>"
	 			+"<td style='width:10%;word-break: break-all;'><div class='ui-tt-c'></div></td>"
	 		+"</tr>";
	 		trs += start+equSerialNo+part+processName+plan_finish+end;
	}
	
	var tbody = trs;
	var endTable = "</tbody>"
				+"</table></div>";
	return beginTable+tbody+endTable;
}