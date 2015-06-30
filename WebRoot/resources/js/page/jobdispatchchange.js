$(document).ready(function() {
	//国际化
	dataTranslate("jobdispatchchange", function(t) {
		$("*[data-i18n]").i18n();
	})

	//页面选择器加载
	$("#select_box_No").selectboxs();
	$("#select_box_Name").selectboxs();
	
	//第一页面显示
	$("#step1").show();
});

//第一步验证
function onYZ() {
	var equId = document.getElementById("myform:hiddenequID").value;

	if (null == equId || "" == equId) {
		jAlert("请选择设备！");
	} else {
		document.getElementById("myform:addcentertd3td5comhiddle").click();
	}
}

//第二步验证
function onXZ() {
	var dispatchId = document.getElementById("myform:jobdispatchId").value;
	var flag = document.getElementById("myform:selectedprocessOrder").value;
	if (null == dispatchId || "" == dispatchId) {
		jAlert("请选择工单！");
	} else {
		if (flag == "400") {
			//报工信息2
			controlScreen(4);
		} else {
			//报工信息
			controlScreen(3);
		}
	}
}

//第三步1 验证
function onZZ() {
	var user = document.getElementById("myform:user").value;
	var num = document.getElementById("myform:txtNum").value;
	var startTime = document.getElementById("myform:startTimeInputDate").value;
	var finishTime = document.getElementById("myform:finishTimeInputDate").value;
	var tt = dateCompare(startTime, finishTime);
	//alert(startTime);
	if (null == user || "" == user) {
		jAlert("请选择报工人员！");
	} else if (null == num || "" == num) {
		jAlert("请填写报工数量！");
	} else if (num == 0) {
		jAlert("报工数量不能为0!");
	} else if (null == startTime || "" == startTime) {
		jAlert("请选择开始时间！");
	} else if (null == finishTime || "" == finishTime) {
		jAlert("请选择结束时间！");
	} else if (!tt) {
		jAlert("开始时间不能大于结束时间!");
	} else {
		document.getElementById("myform:submitButton").click();

	}
}
//第三步2 验证
function onZZ2() {
	var user = document.getElementById("myform:user2").value;
	var num = document.getElementById("myform:txtNum2").value;
	var startTime = document.getElementById("myform:startTime2InputDate").value;
	var finishTime = document.getElementById("myform:finishTime2InputDate").value;
	var tt = dateCompare(startTime, finishTime);
	if (null == user || "" == user) {
		jAlert("请选择报工人员！");
	} else if (null == num || "" == num) {
		jAlert("请填写报工数量！");
	} else if (num == 0) {
		jAlert("报工数量不能为0!");
	} else if (null == startTime || "" == startTime) {
		jAlert("请选择开始时间！");
	} else if (null == finishTime || "" == finishTime) {
		jAlert("请选择结束时间！");
	} else if (!tt) {
		jAlert("开始时间不能大于结束时间!");
	} else {
		document.getElementById("myform:submitButton2").click();

	}
}

//第三步1 验证2
function WZ() {
	var isSuccess = document.getElementById("myform:isSuccess").value;

	if (null != isSuccess && isSuccess != "符合要求") {

		//alert(isSuccess);
		document.getElementById("myform:txtNum").value = 1;
		document.getElementById("myform:txtNum").focus();
	} else {
		controlScreen(5);
	}
}

//第三步2 验证2
function WZ2() {
	var isSuccess = document.getElementById("myform:isSuccess").value;

	if (null != isSuccess && isSuccess != "符合要求") {

		alert(isSuccess);
		document.getElementById("myform:txtNum2").value = 1;
		document.getElementById("myform:txtNum2").focus();
	} else {
		controlScreen(6);
	}
}

//日期先后验证
function dateCompare(startDate, endDate) {
	//yyyy-mm-dd hh:mi:ss
	var beginTimes = startDate.substring(0, 10).split('-');
	var hour = startDate.substring(11, 13);
	var min = startDate.substring(14, 16);
	var seconde = startDate.substring(17, 19);
	var starttime = new Date(beginTimes[0], beginTimes[1], beginTimes[2], hour, min, seconde);
	var starttimes = starttime.getTime();
	//alert(starttimes);
	var endTimes = endDate.substring(0, 10).split('-');
	var endhour = endDate.substring(11, 13);
	var endmin = endDate.substring(14, 16);
	var endseconde = endDate.substring(17, 19);
	var endtime = new Date(endTimes[0], endTimes[1], endTimes[2], endhour, endmin, endseconde);
	var end = endtime.getTime();

	if (starttimes > end) {
		return false;
	} else {
		return true;
	}
}

function reslut() { //验证是否添加成功
	var isSuccess = document.getElementById("myform:isSuccess").value;

	if (null != isSuccess && isSuccess == "添加成功") {
		//alert(isSuccess);
		location.reload();
		//document.getElementById("editInfo").style.display = "none";
		//document.getElementById("comfirm").style.display = "block";
	} else {
		jAlert(isSuccess);
	}
}

function checkNum() { //验证报工数量
	var isSuccess = document.getElementById("myform:isSuccess").value;

	if (null != isSuccess && isSuccess != "符合要求") {

		jAlert(isSuccess);
		document.getElementById("myform:txtNum").value = 1;
		document.getElementById("myform:txtNum").focus();
	}
	//else{
	//	alert(isSuccess);
	//}
}
function checkNum2() { //验证报工数量
	var isSuccess = document.getElementById("myform:isSuccess").value;

	if (null != isSuccess && isSuccess != "符合要求") {

		jAlert(isSuccess);
		document.getElementById("myform:txtNum2").value = 1;
		document.getElementById("myform:txtNum2").focus();
	}

}

//dlg 国际化
function dlgTranslate() {
	dataTranslate("jobdispatchchange", function(t) {
		$(".div-dialog *[data-i18n]").i18n();
	});
}

/*显示DLG*/
function showDlg() {
	$(".div-dialog").show();
}

//控制画面
function controlScreen(type) {
	$(".zl-content-info").hide();
	if (type == 1) { //step1
		$("#step1").show();
	} else if (type == 2) { //step2
		$("#step2").show();
	} else if (type == 3) { //step3-1
		$("#step3_1").show();
	} else if (type == 4) { //step3-2
		$("#step3_2").show();
	} else if (type == 5) { //step4-1
		$("#step4_1").show();
	} else if (type == 6) { //step4-2
		$("#step4_2").show();
	}
}

function subClick(id) {
	document.getElementById(id).click();
}

function onSetNum(num) {
	document.getElementById("myform:txtNum").value = num;
}

function controlNum(s) {

	if (s == 1) {
		var txtNum = document.getElementById("myform:txtNum");
		var currentNum = parseInt(txtNum.value);

		txtNum.value = currentNum + 1;
	} else if (s == 2) {

		var txtNum = document.getElementById("myform:txtNum");
		var currentNum = parseInt(txtNum.value);
		if (currentNum != 1) {
			txtNum.value = currentNum - 1;
		}
	}
}

function setValueforHidd() { //对隐藏域进行赋值，以便jsf的ajax向控制bean传人参数
	var d = document.getElementById("equipment").style.visibility;
	//alert(d);
	if (d == "" || d == "visible") {
		var equId = document.getElementById("select_box_No_hdn").value;
		var equId2 = document.getElementById("select_box_Name_hdn").value;
		document.getElementById("myform:serachEquId").value = equId;
		document.getElementById("myform:serachEquId2").value = equId2;
	} else {
		return false;
	}
}

function setTitle() {
	var obj = document.getElementById("equipment");
	if (obj.scrollHeight > obj.clientHeight || obj.offsetHeight > obj.clientHeight) {
		document.getElementById("title").style.visibility = "visible";
	}
}

function btnclose() {
	location.reload();
}
function openUrl(url) {
	var features = "height=900,width=1500,top:200,left:200,status=no,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=no";
	winId = window.open(url, 'addpage', features);
}