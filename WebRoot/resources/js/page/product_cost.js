$(document).ready(function() {
	//国际化处理
	dataTranslate("product_cost", function(t) { $("*[data-i18n]").i18n();});

	resizeTable();
	
	loadData_machine();
	loadData();
	//页面大小调整
	//$.autosizeExclude($(".zl-iconSelect>select")[0], false);
//	$.autosizeExclude($(".zl-content-info-left-tableHead")[0], true);
//	$.autosizeExclude($(".zl-content-info-left-tableInner")[0], true);
//	$.autosizeAll();
//	
//	$.autosizeReturn(loadData_machine);
//	$.autosizeReturn(loadData);
})

/* 刷新前遮罩 */
function lock(area) {
	if(area == "right") {
		disableFrameShow($(".zl-content-info-left-table"));
		//disableFrameShow($(".zl-content-info-right"));
	}
	else {
		disableFrameShow($(".zl-content-top-selectInfo"));
		//disableFrameShow($(".zl-content-info"));
	}
}

/* 页面图表部分重新刷新 */
function searchResize() {
	resizeTable();
	
	searchResizeRight();
	
    loadData_machine();
    
}

/*页面表格滚动条重建*/
function resizeTable() {
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

/* 页面右侧图表刷新 */
function searchResizeRight() {
	//国际化处理
	dataTranslate("product_cost", function(t) { $("*[data-i18n]").i18n();});
    
	//加载图表
	loadData();
	
	//禁止重复加载
    disableFrameHide();
}