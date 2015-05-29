

//禁止图片拖拽方法
function imgdragstart() {return false;}

//页面大小,国际化
$(document).ready(function() {
	//禁止图片拖拽
	for(i in document.images) document.images[i].ondragstart=imgdragstart;
	
	//大小调整
	$.autosizeInit(1920,1080,800,500);
	$.autosizeExclude($(".zl-bottom>*")[0], true);
	$.autosizeExclude($(".zl-top-logo-img>img")[0], false);
	$.autosizeAll();
	$.autosizeMore();
	
	//国际化处理
	dataTranslate("basicTemplate", function(t) { $("*[data-i18n]").i18n();});
	
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
	
	/*
	 * 废除代码，转为文字连接式
	//显示/隐藏语言选择框
	$(".top-right-frame .more-btn").click(function() {
		if($(".more-btn").text() == "<") {
			$(".top-right-frame").animate({"left": $(window).width() - $(".top-right-frame").width()}, 200);
			$(".more-btn").text(">");
		}
		else {
			$(".top-right-frame").animate({"left": $(window).width()}, 200);
			$(".more-btn").text("<");
		}
	});
	 */
});