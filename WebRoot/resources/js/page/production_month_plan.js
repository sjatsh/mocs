//查询按钮
function querybtn() {
	$("#myform\\:btn_search").click();
}

function onOutput(){
	
	document.getElementById("myform:output").click();
}

function partClassName(){
	$("#myform\\:part_class_name").click();
}

/*//表格样式调整
function resizeTable() {
	//重算表格位置
	var tableBodyTop = $(".table-title>table").height() / 2;
	$(".table-body").css({
		top: tableBodyTop + "px",
		height: $(".zl-content-info").height() - tableBodyTop + "px"
	});

	if($(".table-body").hasClass("mCustomScrollbar")) {
		$(".table-body").mCustomScrollbar("update");
	}
	else {
		 隐藏式滚动条 
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

$(document).ready(function() {
	//国际化
	$("option").attr("data-i18n", function() {
		return $(this).text();
	});
	dataTranslate("production_scrap_report", function(t) {
		$("*[data-i18n]").i18n();
	});
	resizeTable();
	//样式调整
//	$.autosizeExclude($(".table-title")[0], true);
//	$.autosizeExclude($(".table-body>div")[0], true);
//	$.autosizeReturn(resizeTable);
}); */