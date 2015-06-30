$(document).ready(function(){
	//国际化
	var option = {resGetPath:"static/i18n/__lng__/production_event_search.json"};
    i18n.init(option, function(t) { $("*[data-i18n]").i18n();});
});

function resizeTable() {
	$("#list-frame th span").i18n();
	$("#list-frame th").css("height", hjb51 * 44 / 1080 + "px");
	$("#list-frame #myform\\:datatable\\:tb td").css("height", hjb51 * 44 / 1080 + "px");
}
function onkeyupJObList(){
	document.getElementById("myform:jobbtn").click();
	document.getElementById("joblist").style.display="block";
}
function onfocusJobList(){
	document.getElementById("joblist").style.display="block";
}
function onblurJObList(){
	document.getElementById("joblist").style.display="none";
}
document.getElementById("joblist").onmouseout=function(event){
	var s =event.relatedTarget;
	if (!this.contains(s)) {
		this.style.display="none";
	}            
};
function treeOnclick(){
	document.getElementById("joblist").style.display="none";
}