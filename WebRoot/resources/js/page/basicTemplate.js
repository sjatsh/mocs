//禁止图片拖拽方法
function imgdragstart() {return false;}

//页面加载后处理
$(document).ready(function() {
	//禁止图片拖拽
	for(i in document.images) document.images[i].ondragstart=imgdragstart;
	
	//国际化处理
	dataTranslate("basicTemplate", function(t) { $("*[data-i18n]").i18n();});
	
	//BODY撑满
	document.body.style.height = hjb51 + "px";
	
	//隐藏遮罩层
	$(".show-frame").hide();
	
	//选择语言切换
	$(".zl-top-translate>a").click(function() {
		var lngVal = this.id.replace("lng_", "");
		i18n.setLng(lngVal);
		//调用后台语言切换接口
		languageChangeBg(lngVal);
	});
	
	//退出按钮
	$(".zl-top-user-btn-text").click(function() {
		//清除后台数据缓存node, menu
		window.sessionStorage.removeItem("node_id");
		window.sessionStorage.removeItem("menu_value");
		
		$("#logform\\:logout").click();
	});
});