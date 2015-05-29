function wuisnull() {
	var wlNo = document.getElementById("myform:wlNo").value;
	var wlType = document.getElementById("myform:wlType").value;
	var wlNumber = document.getElementById("myform:wlNumber").value;
	if (null == wlNo || "" == wlNo || "请选择" == wlNo) {
		alert("请输入物料编号!");
	} else if (null == wlType || "" == wlType) {
		alert("请输入需求类型!");
	} else if (null == wlNumber || "" == wlNumber) {
		alert("请输入需求数量!");
	} else if (isNaN(wlNumber)) {
		alert("需求数量只能为数字!");
	} else {
		document.getElementById("myform:wladd").click();
	}
}
function jbxxNext() {
	var partNo = document.getElementById("myform:partNo").value;
	var partName = document.getElementById("myform:partName").value;
	var processOrder = document.getElementById("myform:processOrder").value;
	var processType = document.getElementById("myform:processType").value;
	var ischeck = document.getElementById("myform:ischeck").value;
	if (processType == "1") {
		if (null == processOrder || "" == processOrder) {
			alert("请输入工序序号");
		} else if (isNaN(processOrder)) {
			alert("工序序号只能输入数字");
		} else if (null == partNo || "" == partNo) {
			alert("请输入工序编号!");
		} else if (null == partName || "" == partName) {
			alert("请输入工序名称!");
		} else {
			menuClick('jt');
		}
	} else if (processType == "2") {
		if (null == processOrder || "" == processOrder) {
			alert("请输入工序序号");
		} else if (isNaN(processOrder)) {
			alert("工序序号只能输入数字");
		} else if (null == partNo || "" == partNo) {
			alert("请输入工序编号!");
		} else if (null == partName || "" == partName) {
			alert("请输入工序名称!");
		} else if (null == ischeck || "" == ischeck) {
			alert("请输入是否必检");
		} else {
			menuClick('jt');
		}
	} else if (processType == "3") {
		menuClick('jt');
	} else {
		alert("请选择工序类型");
	}
}
function jtBack() {
	var programID = document.getElementById("myform:programID").value;
	var processingTime = document.getElementById("myform:processingTime").value;
	var theoryWorktime = document.getElementById("myform:theoryWorktime").value;
	var capacity = document.getElementById("myform:capacity").value;

	if (null == programID || "" == programID || "请选择" == programID) {
		alert("请选择程序!");
	} else if (null == processingTime || "" == processingTime) {
		alert("请输入加工时间!");
	} else if (isNaN(processingTime)) {
		alert("加工时间只能输入数字");
	} else if (null == theoryWorktime || "" == theoryWorktime) {
		alert("请输入节拍时间!");
	} else if (isNaN(theoryWorktime)) {
		alert("节拍时间只能输入数字");
	} else if (isNaN(capacity)) {
		alert("预计产能只能输入数字");
	} else {
		menuClick('jbxx');
	}

}
function jtNext() {
	var programID = document.getElementById("myform:programID").value;
	var processingTime = document.getElementById("myform:processingTime").value;
	var theoryWorktime = document.getElementById("myform:theoryWorktime").value;
	var capacity = document.getElementById("myform:capacity").value;

	if (null == programID || "" == programID || "请选择" == programID) {
		alert("请选择程序!");
	} else if (null == processingTime || "" == processingTime) {
		alert("请输入加工时间!");
	} else if (isNaN(processingTime)) {
		alert("加工时间只能输入数字");
	} else if (null == theoryWorktime || "" == theoryWorktime) {
		alert("请输入节拍时间!");
	} else if (isNaN(theoryWorktime)) {
		alert("节拍时间只能输入数字");
	} else if (isNaN(capacity)) {
		alert("预计产能只能输入数字");
	} else {
		menuClick('wl');
	}
}
function wlBack() {
	menuClick('jt');
}
function wlNext() {
	menuClick('zy');
}
function zyBack() {
	var installTime = document.getElementById("myform:installTime").value;
	var uninstallTime = document.getElementById("myform:uninstallTime").value;
	if (isNaN(installTime)) {
		alert("装夹时间只能输入数字!");
	} else if (isNaN(uninstallTime)) {
		alert("卸载时间只能输入数字!");
	} else {
		menuClick('wl');
	}
}
function zyNext() {
	var installTime = document.getElementById("myform:installTime").value;
	var uninstallTime = document.getElementById("myform:uninstallTime").value;
	if (isNaN(installTime)) {
		alert("装夹时间只能输入数字!");
	} else if (isNaN(uninstallTime)) {
		alert("卸载时间只能输入数字!");
	} else {
		menuClick('cb');
	}
}
function cbBack() {
	var mainMaterialCost = document.getElementById("myform:mainMaterialCost").value;
	var peopleCost = document.getElementById("myform:peopleCost").value;
	var subsidiaryMaterialCost = document
			.getElementById("myform:subsidiaryMaterialCost").value;
	var energyCost = document.getElementById("myform:energyCost").value;
	var deviceCost = document.getElementById("myform:deviceCost").value;
	var resourceCost = document.getElementById("myform:resourceCost").value;
	if (isNaN(mainMaterialCost)) {
		alert("主料成本只能输入数字！");
	} else if (isNaN(peopleCost)) {
		alert("人工成本只能输入数字！");
	} else if (isNaN(subsidiaryMaterialCost)) {
		alert("辅料成本只能输入数字！");
	} else if (isNaN(energyCost)) {
		alert("能源成本只能输入数字！");
	} else if (isNaN(deviceCost)) {
		alert("设备折旧只能输入数字！");
	} else if (isNaN(resourceCost)) {
		alert("资源消耗只能输入数字！");
	} else {
		menuClick('zy');
	}

}
function cbNext() {
	var mainMaterialCost = document.getElementById("myform:mainMaterialCost").value;
	var peopleCost = document.getElementById("myform:peopleCost").value;
	var subsidiaryMaterialCost = document
			.getElementById("myform:subsidiaryMaterialCost").value;
	var energyCost = document.getElementById("myform:energyCost").value;
	var deviceCost = document.getElementById("myform:deviceCost").value;
	var resourceCost = document.getElementById("myform:resourceCost").value;
	if (isNaN(mainMaterialCost)) {
		alert("主料成本只能输入数字！");
	} else if (isNaN(peopleCost)) {
		alert("人工成本只能输入数字！");
	} else if (isNaN(subsidiaryMaterialCost)) {
		alert("辅料成本只能输入数字！");
	} else if (isNaN(energyCost)) {
		alert("能源成本只能输入数字！");
	} else if (isNaN(deviceCost)) {
		alert("设备折旧只能输入数字！");
	} else if (isNaN(resourceCost)) {
		alert("资源消耗只能输入数字！");
	} else {
		menuClick('zj');
	}
}
function zjBack() {
	menuClick('cb');
}

function AddEqu() {
	var treetxt = document.getElementById("myform:treetxt").value;
	if (null == treetxt || "" == treetxt) {
		alert("请选择设备!");
	} else {
		subClick("myform:AddEquBtn");
	}
}
function AddJJData() {
	var partClassId = document.getElementById("myform:partClassId").value;
	var jjNo = document.getElementById("myform:jjNo").value;
	if (null == partClassId || "" == partClassId) {
		alert("请选夹具类别!");
	} else if (null == jjNo || "" == jjNo) {
		alert("请选择夹具类型编号!");
	} else {
		subClick("myform:AddJJBtn");
	}

}
function AddDJData() {
	var djselect = document.getElementById("myform:djselect").value;
	var djNum = document.getElementById("myform:djNum").value;
	if (null == djselect || "" == djselect || "请选择" == djselect) {
		alert("请选刀具类型编号!");
	} else if (null == djNum || "" == djNum) {
		alert("请选择刀具需求数量!");
	} else if (isNaN(djNum)) {
		alert("刀具需求数量只能输入数字!");
	} else {
		subClick("myform:AddDjBtn");
	}

}
function AddZJData() {
	var zjno = document.getElementById("myform:zjno").value;
	var zjname = document.getElementById("myform:zjname").value;
	var zjctype = document.getElementById("myform:zjctype").value;
	var zjsv = document.getElementById("myform:zjsv").value;
	var zjmaxv = document.getElementById("myform:zjmaxv").value;
	var zjminv = document.getElementById("myform:zjminv").value;
	var zjto = document.getElementById("myform:zjto").value;
	var zjto2 = document.getElementById("myform:zjto2").value;
	if (null == zjno || "" == zjno) {
		alert("请输入检验参数编号!");
	} else if (null == zjname || "" == zjname) {
		alert("请输入检验参数名称!");
	} else if (null == zjctype || "" == zjctype) {
		alert("请输入检验参数类型!");
	} else if (isNaN(zjsv)) {
		alert("标准值只能为数字!");
	} else if (isNaN(zjmaxv)) {
		alert("上限值只能为数字!");
	} else if (isNaN(zjminv)) {
		alert("下限值只能为数字!");
	} else if (isNaN(zjto)) {
		alert("允差上限只能为数字!");
	} else if (isNaN(zjto2)) {
		alert("允差下限只能为数字!");
	} else {
		subClick("myform:AddZjBtn");
	}
}
var status = 1;
function DisprocessDetal() {
	if (status == 1) {
		document.getElementById("processDetal").style.display = "block";
		status = 2;
	} else {
		document.getElementById("processDetal").style.display = "none";
		status = 1;
	}
}

function menuClick(id) {	//TAB切换
	$("#btn" + id).addClass("act").siblings(".menu-tabbar").removeClass("act");
	$("#" + id).show().siblings(".processlist-content").hide();
}
function of() {	//显示零件树
	$("#tree").show();
}
function cc() {	//隐藏零件树
	$("#tree").hide();
	translateReady();
}
$("#tree").mouseleave(function() {
	$(this).hide();
});

function close() {
	document.getElementById(":myform:addsb").style.display = "none";
}

function subClick(id) {
	document.getElementById(id).click();
}
function translateReady() {	//国际化翻译
	dataTranslate("parts_setting", function(t) {
		//选项替换
		$("option").attr("data-i18n", function() {return $(this).text()});
		//按钮替换
		$(".btn-conctrl").attr("data-i18n", function() {return $(this).text()});
		//按钮替换2
		$(".btn-toolbar>span").attr("data-i18n", function() {return $(this).text()});
		//表格替换
		$(".table-p th span").attr("data-i18n", function() {return $(this).text()});
		$("*[data-i18n]").i18n();
	}, "../static/i18nAdmin");
}