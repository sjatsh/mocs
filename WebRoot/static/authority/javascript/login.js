$(document).ready(function() {
	//国际化强制英文
	$.ajax({
		async: false,
		type: "GET",
		url: "../WSUserService/setLocal.json",
		data: {"local": "en"}
	});
	
	//高度大小调整
	document.documentElement.style.fontSize = 100 / 1080 * $(window).height() + "px";
	
	//页面动画
	startAnimate();
	
	//logo旋转，直线动画
	setInterval(logoAnimate, 10);
});

/* 记住用户 */
$(".zl-checkbox").click(function() {
	$(this).find(".zl-checkbox-select").toggleClass("checked");
});

/* 登录按钮 */
function loginSubmit(){
	//清空菜单栏选择
	SetCookie("menuActive", "");
	document.getElementById("loginsubmit").click();
}

/* 页面出现动画 */
function startAnimate() {
	//隐藏元素
	$(".logo-frame,.login_line,.login_login").hide();
	//显示LOGO
	var logoHeight = $(".logo-frame").height();
	var logoTop = $(".logo-frame").css("margin-top");
	$(".logo-frame").css("margin-top", -1 * logoHeight).show().animate({marginTop:logoTop}, 750, function() {
		//显示彩条
		$(".login_line>img").css({"width":"0","margin-left":"50%"});
		$(".login_line").show().find("img").animate({width:"100%", marginLeft:"0%"}, 500, function() {
			//显示表单
			$(".login_login").fadeIn(750);
		});
	});
}

/* logo 动画 */
function logoAnimate() {
	//圆圈动画
	var targetImg = $(".logo-frame>img:eq(1)");
	if(targetImg.attr("cur_deg") == null) targetImg.attr("cur_deg", 0);
	var curDeg = parseFloat(targetImg.attr("cur_deg"));
	curDeg++;
	if(curDeg > 360) curDeg = 0;
	targetImg.css("transform", "rotate3d(0,0,1," + curDeg + "deg)");
	targetImg.attr("cur_deg", curDeg);
	//直线动画
	var targetImg2 = $(".login_line");
	targetImg2.css("left", (targetImg2.offset().left > 1920 ? 0 : targetImg2.offset().left + 1) + "px");
	/*
	return;
	if($(".logo-face>img").attr("cur_deg") == null) $(".logo-face>img").attr("cur_deg", 0);
	var curDeg = parseFloat($(".logo-face>img").attr("cur_deg"));
	curDeg += 1;
	if(curDeg / 90 <= 1) {
		//使用图片A
		$(".logo-face>img:eq(0)").show();
		$(".logo-face>img:eq(1)").hide();
		$(".logo-face>img:eq(1)").removeClass("hidden");
	}
	else {
		if(curDeg < 270) {
			curDeg = 270;
		}
		//使用图片B
		$(".logo-face>img:eq(0)").hide();
		$(".logo-face>img:eq(1)").show();
	}
	if(curDeg >= 450) {
		curDeg = -90;
	}
	$(".logo-face>img").css("transform", "rotate3d(0,1,0," + curDeg + "deg)");
	$(".logo-face>img").attr("cur_deg", curDeg);*/
}

/**
 * 添加浏览器COOKIE
 * @param name
 * @param value
 */
function SetCookie(name,value){
    var Days = 30; 
    var exp  = new Date();    
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";path=/;expires=" + exp.toGMTString();
}