var wjb51 = 0;
var hjb51 = 0;
wjb51 = document.documentElement.clientWidth;
hjb51 = document.documentElement.clientHeight;
if(hjb51>wjb51){ //高大于宽 ，高等比缩小 
	hjb51 = wjb51*1080/1920; 
}
if(500>hjb51){  //高小于500，赋值为500，设滚动条为滚动 
	   hjb51 = 600; 
	   wjb51 = 800;
	   document.body.style.overflow="auto";
   }
if(800>wjb51){
	   hjb51 = 600;
	   wjb51 = 800;
	   document.body.style.overflow="auto";
}	
var fadeouttime = 500;
var settimeout = 100;