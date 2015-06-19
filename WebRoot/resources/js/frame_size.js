var wjb51 = 0;
var hjb51 = 0, hWindow = 0;
wjb51 = document.documentElement.clientWidth;
hjb51 = document.documentElement.clientHeight;
hWindow = hjb51;
if(wjb51 / hjb51 < 1920 / 1080) {
	hWindow = 1080 / 1920 * wjb51;
}
var fadeouttime = 500;
var settimeout = 100;
//大小调整
document.documentElement.style.fontSize = 100 / 1080 * hWindow + "px";