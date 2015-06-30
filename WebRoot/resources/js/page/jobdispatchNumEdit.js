$(document).ready(function() {
	//国际化
	dataTranslate("jobdispatchNumEdit", function(t) {
		$("*[data-i18n]").i18n();
	})
	
});

function tableTranslate() {
	dataTranslate("jobdispatchNumEdit", function(t) {
		$(".table-p *[data-i18n]").i18n();
	})
}

function showDialog() {
	$(".div-dialog").show();
}

function closeDialog() {
	$(".div-dialog").hide();
}

function clickOk() {
	$("#myform\\:ok").click();
}

function onYZ() {
	var eventNo = document.getElementById("myform:eventNo").value;
	var partTypeId = document.getElementById("myform:partTypeId").value;
	var jobdispatchId = document.getElementById("myform:jobdispatchId").value;
	var jobplanId = document.getElementById("myform:jobplanId").value;
	var bgTimeInputDate = document.getElementById("myform:bgTimeInputDate").value;
	var user = document.getElementById("myform:user").value;
	var count = document.getElementById("myform:count").value;
	var equId = document.getElementById("myform:equId").value;

	if (null == eventNo || "" == eventNo) {
		jAlert("请输入报工修改单号！");
	} else if (null == partTypeId || "" == partTypeId) {
		jAlert("请选择产品名称！");
	} else if (null == jobdispatchId || "" == jobdispatchId) {
		jAlert("请选择工单号！");
	} else if (null == jobplanId || "" == jobplanId) {
		jAlert("请选择批次计划号！");
	} else if (null == bgTimeInputDate || "" == bgTimeInputDate) {
		jAlert("请选择报工日期！");
	} else if (null == user || "" == user) {
		jAlert("请选择报工人员！");
	} else if (null == count || "" == count) {
		jAlert("请输入修改数量！");
	} else if (isNaN(count)) {
		jAlert("请输入正确的修改数量！");
	} else if (null == equId || "" == equId) {
		jAlert("请选择设备！");
	} else {
		document.getElementById("myform:submit").click();
	}
}

function onXZ() {
	var msg = document.getElementById("myform:isSuccess").value;
	if (null != msg && msg == "符合要求") {
		document.getElementById("myform:save").click();
	} else {
		jAlert(msg);
	};
}
function onZZ() {
	var msg = document.getElementById("myform:isSuccess").value;
	if (null != msg && msg == "添加成功") {
		jAlert(msg);
		closeDialog();
	} else {
		jAlert(msg);
	};
}