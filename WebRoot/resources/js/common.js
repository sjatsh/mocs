// JavaScript Document

/**
 * 获取时间
 */
function showClock() {
	document.getElementById("loadbtjpg").style.zIndex=0;
	document.getElementById("loadbtjpg").style.display="none";

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
 * 时间格式转换
 * @param seconda
 * @param dayxian
 * @param hourxian
 * @param minutexian
 * @param secondxian
 * @returns {String}
 */
function formatTime(seconda,dayxian,hourxian,minutexian,secondxian){
	second=seconda;
	day=0;
	hour=0;
	minute=0;
	number=0;
	day=second/86400;
	number=(second%86400);
	hour=number/3600;
	number=number%3600;
	minute=number/60;
	second=number%60;
	return  parseInt(day) +dayxian+ parseInt(hour) + hourxian + parseInt(minute) + minutexian + parseInt(second) +secondxian +'';
} 

/**
 * 判断浏览器类型
 * @returns {String}
 */
function getBrowserType() {
	var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;
    (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
    (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
    (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
    (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
    (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;

    if (Sys.ie){
    	return "ie";
    }
    if (Sys.firefox){
    	return "firefox";
    }
    if (Sys.chrome){
    	return "chrome";
    }
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
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

/**
 * 获取浏览器COOKIE
 * @param name
 * @returns
 */
function getCookie(name){
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
     if(arr != null) return unescape(arr[2]); return null;

}

/**
 * 删除浏览器COOKIE
 * @param name
 */
function delCookie(name){
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}

/**
 * 字符串截取
 * @param st
 * @param type
 * @returns 
 */
function toSubstring(st,type){
	var overstr="...";
	if(null==st){return "";}
	else if("1"==type&&st.length>10){ return st.substring(0,10)+overstr;}//截取10个字符  
	else if("2"==type&&st.length>16) {return st.substring(0,16)+overstr;}//截取20个字符  
	else if("3"==type&&st.length>30) {return st.substring(0,30)+overstr;}//截取30个字符  
	else if("4"==type&&st.length>15) {return st.substring(0,15)+overstr;}//截取15个字符  
	else if("5"==type&&st.length>5) {return st.substring(0,5)+overstr;}//截取15个字符  
	else {return st;}		
}

/** 
* 图片头数据加载就绪事件 - 更快获取图片尺寸 
* @version 2011.05.27 
* @see     http://blog.phpdr.net/js-get-image-size.html 
* @param   {String}    图片路径 
* @param   {Function}  尺寸就绪 
* @param   {Function}  加载完毕 (可选) 
* @param   {Function}  加载错误 (可选) 
* @example imgReady('http://www.google.com.hk/intl/zh-CN/images/logo_cn.png', function () { 
     alert('size ready: width=' + this.width + '; height=' + this.height); 
 }); 
*/  
var imgReady = (function () {  
 var list = [], intervalId = null,  

 // 用来执行队列  
 tick = function () {  
     var i = 0;  
     for (; i < list.length; i++) {  
         list[i].end ? list.splice(i--, 1) : list[i]();  
     };  
     !list.length && stop();  
 },  

 // 停止所有定时器队列  
 stop = function () {  
     clearInterval(intervalId);  
     intervalId = null;  
 };  

 return function (url, ready, load, error) {  
     var onready, width, height, newWidth, newHeight,  
         img = new Image();  

     img.src = url;  

     // 如果图片被缓存，则直接返回缓存数据  
     if (img.complete) {  
         ready.call(img);  
         load && load.call(img);  
         return;  
     };  

     width = img.width;  
     height = img.height;  

     // 加载错误后的事件  
     img.onerror = function () {  
         error && error.call(img);  
         onready.end = true;  
         img = img.onload = img.onerror = null;  
     };  

     // 图片尺寸就绪  
     onready = function () {  
         newWidth = img.width;  
         newHeight = img.height;  
         if (newWidth !== width || newHeight !== height ||  
             // 如果图片已经在其他地方加载可使用面积检测  
             newWidth * newHeight > 1024  
         ) {  
             ready.call(img);  
             onready.end = true;  
         };  
     };  
     onready();  

     // 完全加载完毕的事件  
     img.onload = function () {  
         // onload在定时器时间差范围内可能比onready快  
         // 这里进行检查并保证onready优先执行  
         !onready.end && onready();  

         load && load.call(img);  

         // IE gif动画会循环执行onload，置空onload即可  
         img = img.onload = img.onerror = null;  
     };  
     // 加入队列中定期执行  
     if (!onready.end) {  
         list.push(onready);  
         // 无论何时只允许出现一个定时器，减少浏览器性能损耗  
         if (intervalId === null) intervalId = setInterval(tick, 40);  
     };  
 };  
})();


function remember(yoururl){//alert(1);
	var d=getCookie("pathitem").split("|");
	for(var i=1;i<d.length;i++){
		var d2=d[i].split("*");
		if(d2[1]==yoururl){
			return d2[0];
			//alert(getCookie("menufirst")+"----"+d2[0]);
			//break;	
		}
	}
}

function jumpto(dd) {
	if (dd == "/" || dd == "") {
		
	} else if (dd != "") {
		SetCookie("menuthirdURL", dd);
		window.location = 'index.faces?page=' + dd;
	}
}
function tableAddLine(tableObj, frameObj, pageObj) {
	//填充区域高度
	var addLineHeight = $(frameObj).height() - $(tableObj).height() - $(pageObj).height();
	//填充区单行高度
	var addLineTrHeight = $($(tableObj).find("tbody tr")[0]).height();
	//填充区域行数
	var addLineCount = addLineHeight / addLineTrHeight + 1;
	//填充区域开始高度
	var addLineStart = $(tableObj).offset().top + $(tableObj).height();
	//填充区域开始样式
	var addLineStyleClass = "odd-row";
	if($(".rf-dt-b .rf-dt-r").length % 2 == 0) {
		addLineStyleClass = "even-row";
	}
	
	var addLineId = "add_table_line";
	var addLineDiv = document.createElement("div");
	addLineDiv.id = addLineId;
	$(addLineDiv).width($(tableObj).width()).height(addLineHeight).css("overflow", "hidden");
	//循环填充
	for(var indexLine = 0; indexLine < parseInt(addLineCount); indexLine++) {
		var tempLine = document.createElement("div");
		tempLine.style.height = addLineTrHeight + "px";
		tempLine.className = addLineStyleClass;
		//填充行样式变更
		if(addLineStyleClass == "odd-row") {
			addLineStyleClass = "even-row";
		}
		else {
			addLineStyleClass = "odd-row";
		}
		$(addLineDiv).append(tempLine);
	}
	
	$(frameObj).find("#" + addLineId).remove();
	$(frameObj).append(addLineDiv);
}

function searchBtn() {
	tableAddLine($("#list-frame table")[0], $("#list-frame .table-frame")[0], $("#list-frame .rf-ds"));	
}