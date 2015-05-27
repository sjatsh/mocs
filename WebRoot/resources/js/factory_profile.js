

var nowEqModId=0;


//VMC0656e-SHB21-05
//VMC850p-SHB21-04
//ETC3650-SHB21-02
//ETC3650-SHB21-01
function insertElement(x,y,imgUrl,equSerialNo,pathThree,ipAddress){
	 var xPercent = wjb51/2050;
	 var yPercent = hjb51/1080;
	
	 x=parseInt(x*xPercent);
	 y=parseInt(y*yPercent);
	 //alert(x+":"+y);
	//var tt005=document.getElementById("factoptt").style.widht;
	//alert(tt005);
	equSerialNo=rtrim(equSerialNo);
	var status=imgUrl.substring(0,2);
	//alert(1);
	if(null!=ipAddress&&""!=ipAddress&&"null"!=ipAddress){
		if(equSerialNo=="VMC850p-CCMT-07"){
			document.getElementById("content").innerHTML+="<div style='left:"+x+"px;top:"+y+"px;background:transparent;position:absolute;' id='"+equSerialNo+"showimg' >" +
			  "<img src='./images/device/"+imgUrl+"'style='z-index: 9;'   " +
			  "class='dMenu' ondblclick=\"doubleClick('"+equSerialNo+"','"+pathThree+"','"+ipAddress+"','"+status+"')\"    " +
			  " id='"+equSerialNo+"' /><br>" +
		  "</div>";
		}else{
			document.getElementById("content").innerHTML+="<div style='left:"+x+"px;top:"+y+"px;background:transparent;position:absolute;' id='"+equSerialNo+"showimg' >" +
			  "<img src='./images/device/"+imgUrl+"'style='z-index: 9;'   " +
			  "class='dMenu' ondblclick=\"doubleClick('"+equSerialNo+"','"+pathThree+"','"+ipAddress+"','"+status+"')\"    " +
			  " id='"+equSerialNo+"' onclick=\"oneTClick('"+equSerialNo+"','"+status+"')\" /><br>" +
		  "</div>";
		}
		
	}else{
		if(equSerialNo=="VMC850p-CCMT-07"){
			document.getElementById("content").innerHTML+="<div style='left:"+x+"px;top:"+y+"px;background:transparent;position:absolute;' id='"+equSerialNo+"showimg' >" +
			  "<img src='./images/device/"+imgUrl+"'style='z-index: 9;'   " +
			  "class='dMenu'  " +
			  " id='"+equSerialNo+"'  /><br>" +
		  "</div>";
		}else{
			document.getElementById("content").innerHTML+="<div style='left:"+x+"px;top:"+y+"px;background:transparent;position:absolute;' id='"+equSerialNo+"showimg' >" +
			  "<img src='./images/device/"+imgUrl+"'style='z-index: 9;'   " +
			  "class='dMenu'  " +
			  " id='"+equSerialNo+"' onclick=\"oneTClick('"+equSerialNo+"','"+status+"')\" /><br>" +
		  "</div>";
		}
		
	}
	
	//先获取原图片的大小，不是屏幕大小，然后用原图片大小来等比例缩小或者放大。
	  imgReady('./images/device/'+imgUrl, function () { 
	  var imgwjb51=this.width;
   	  var imghjb51=this.height;
   	  var imgPercent = wjb51/2150; //缩小比例为浏览器宽度/1920. 用高的比例来缩小 	
	  document.getElementById(equSerialNo).style.width=parseInt(imgwjb51*imgPercent)+"px";
	  document.getElementById(equSerialNo).style.height=parseInt(imghjb51*imgPercent)+"px";
	});  
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

function returnColor(number){
	//alert(number);
	if(number<=60){
		return "#00b050";
	}else if(number<=80){
		return "#fed400";
	}else if(number>80){
		return "#ff0000";
	}
}
	
	
