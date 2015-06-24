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
$(document).ready(function() {
//	$.autosizeExclude($(".table-title")[0], true);
//	$.autosizeExclude($(".table-body>div")[0], true);
//    $.autosizeReturn(resizeTable);
    dataTranslate("output_gather_report", function(t) { $("span").i18n();});
    resizeTable();
});