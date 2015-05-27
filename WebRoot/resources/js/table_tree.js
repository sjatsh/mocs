/*
 * table background position fix
 * by god.tang
 * 2014-05-04
*/
function bgReposition(e) {
	 if($(e).find("table")[0]) {
		 $(e).each(function() {
			 var tableHeight = $(this).find("table tbody")[0].offsetHeight;
			 var tableRowsCount = $(this).find("table:first tbody tr").length;
			 if(tableRowsCount % 2 == 0) {
				 tableHeight -= 27;
			 }
			 $(this).css("background-position", "0 " + tableHeight + "px");
		 });
	 }
}
$(".table").mouseover(function() {
	bgReposition(this);
});	
$(document).ready(function() {
	bgReposition($(".table"));
});
$(".table").ajaxComplete(function() {

	bgReposition($(".table"));
});
$(".tableprocess").mouseover(function() {
	bgReposition(this);
});	
$(document).ready(function() {
	bgReposition($(".tableprocess"));
});
$(".tableprocess").ajaxComplete(function() {

	bgReposition(this);
});

/*
 * tree area show & hidden
 * by god.tang
 * 2014-05-13
*/
$("#tree_move_btn").click(function() {
	if($("#tree_move_btn.btn_tree_hidden")[0]) {
		$(".materail_class_treeDiv").animate({left: '-191px'}, 200);
		$(".materail_class_rightDiv").animate({left: '0'}, 200).addClass("hidden");
		$(".ui-datatable tfoot").animate({left: '170px'}, 200);
		$("#tree_move_btn.btn_tree_hidden").removeClass("btn_tree_hidden").addClass("btn_tree_show");
	}
	else {
		$(".materail_class_treeDiv").animate({left: '0px'}, 200);
		$(".materail_class_rightDiv").animate({left: '190px'}, 200).removeClass("hidden");
		$(".ui-datatable tfoot").animate({left: '360px'}, 200);
		$("#tree_move_btn.btn_tree_show").removeClass("btn_tree_show").addClass("btn_tree_hidden");
	}
	setTimeout("bgReposition($('.table'))", 200);
});