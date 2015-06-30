
$(document).ready(function(){
	//国际化
	dataTranslate("productToStock", function(t) {
		$("*[data-i18n]").i18n();
	});
	tableTranslate();
});

function onYZ() {
	
	var partTypeId = document.getElementById("myform:partTypeId").value;
	var num = document.getElementById("myform:instockNum").value;
	var code = document.getElementById("myform:inventoryId").value;
	var materialPositionId = document.getElementById("myform:materialPositionId").value;
	if(null == partTypeId || "" == partTypeId) {
		jAlert("请选择产品名称！");
	}
	else if(null == code || "" == code){
		jAlert("请选择库房号！");
	}
	else if(null == num || "" == num){
		jAlert("请输入本次入库数量！");
	}
	else if(null == materialPositionId || "" == materialPositionId){
		jAlert("请选择库位号！");
	}
	else{
		document.getElementById("myform:submit").click();
	}
}

function onXZ()  {
	
    document.getElementById("myform:result").click();
	
}
function onZZ()  {
	var msg = document.getElementById("myform:isSuccess").value;
	jAlert(msg);
}
function check(){
	var msg = document.getElementById("myform:isSuccess").value;
	if(msg =="允许"){
		$(".div-dialog").show();
	}else {
	}
}

//表格翻译
function tableTranslate() {
	$(".table-p .ui-dt-c button span").each(function() {
		$(this).attr("data-i18n", $(this).text());
	});
	dataTranslate("productToStock", function(t) {
		$(".table-p *[data-i18n]").i18n();
	});
}

//计算按钮
function jsClick() {
	$("#myform\\:js").click();
}

//弹出框确认
function clickOk() {
	$("#myform\\:btnOk").click();
}

//关闭弹出框
function closeDlg() {
	$(".div-dialog").hide();
}