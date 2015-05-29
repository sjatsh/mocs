/* 后台语言切换接口调用 */
function languageChangeBg(langStr) {
	//前后端判断
	var bgValue = getBgLanguage();
	if(bgValue.toLowerCase() != langStr.toLowerCase()) {
		$.ajax({
			async: false,
			type: "GET",
			url: "WSUserService/setLocal.json",
			data: {"local": langStr},
			complete: function() {
				//清除后台数据缓存node, menu
				window.sessionStorage.removeItem("node_id");
				window.sessionStorage.removeItem("menu_value");
				//刷新隐藏域
				menuLoadFlag = false;
				location.reload();
			}
		});
	}
}
/* 后台语言读取接口 */
function getBgLanguage() {
	var returnStr = "";
	$.ajax({
		async: false,
		type: "GET",
		url: "WSUserService/getLocale.json",
		complete: function(xhr, ts) {
			//刷新隐藏域
			var obj = eval("(" + xhr.responseText + ")");
			returnStr = obj.locale;
		}
	});
	return returnStr;
}

$(document).ready(function() {
	//国际化处理
	dataTranslate("basicTemplate", function(t) { $("*[data-i18n]").i18n();});

	//后台国际化处理
	/* 汉诺威展专用，默认英文
	 * if(i18n.options.lng.toLowerCase() == 'zh-cn') {	//第一次加载
		i18n.setLng("en");
		//调用后台语言切换接口
		languageChangeBg("en");
	}*/
	if(typeof(i18n.options.lng) != 'undefined') {
		$(".zl-top-translate>a").removeClass("active");
		$("#lng_" + i18n.options.lng.split("-")[0]).addClass("active");
		languageChangeBg(i18n.options.lng.replace("-","_"));
	}
});