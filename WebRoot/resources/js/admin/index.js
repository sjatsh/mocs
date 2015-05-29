//打开连接方法
function openUrl(url) {
    features="status=no,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=no,channelmode=yes,fullscreen=yes";             
    winId=window.open(url,'newwindow',features);
}
//定义全局变量，浏览器大小
var wjb51 = 0;
var hjb51 = 0;
wjb51 = $(window).width()
hjb51 = $(window).height()
if(hjb51>wjb51){ //高大于宽 ，高等比缩小 
	hjb51 = wjb51*1080/1920;
}
if(600>hjb51){  //高小于600，赋值为600，设滚动条为滚动 
   hjb51 = 600; 
   wjb51 = 800;
   document.body.style.overflow = "scroll";
}
if(800>wjb51){
   hjb51 = 600;
   wjb51 = 800;
   document.body.style.overflow = "scroll";
}

/**
 * 获取时间
 */
function showClock() {
	d = new Date();
	hours = d.getHours();
	minutes = d.getMinutes();
	seconds = d.getSeconds();
	clck = (hours >= 12) ? "下午" : "上午";
	hours = (hours > 12) ? hours - 12 : hours;
	hours = (10 > hours) ? "0" + hours : hours;
	minutes = (10 > minutes) ? "0" + minutes : minutes;
	seconds = (10 > seconds) ? "0" + seconds : seconds;
	time = clck + " " + hours + ":" + minutes + ":" + seconds;
	thisTime.innerHTML = time;
	setTimeout("showClock()", 1000);
}

/**
 * 打开树形结构
 */
function toggleMenu() {
	var menus = $("#DropdownMenuBackground");
	menus.toggle();
}
/**
 * 关闭树形结构
 */
function hideMenu() {
	var menus = $("#DropdownMenuBackground");
	menus.hide();
}

/**
 * 树状图选中项表示
 */
function nodeChecked(_obj){	
	$(_obj).find(".usercheck").parents("li").addClass("act");	
}

/**
 * 顶部导航按钮
 */
function headNavigation(i) {
	//改变状态&按下按钮
	$(i).find("label")[0].click();
	return false;
}

/**
 * 导航按钮选中样式
 * @param 导航按钮
 */
function menuListChecked(_obj) {
	$(_obj).find(".act").parents(".top-info-foot-menu-btn").addClass("act");
}