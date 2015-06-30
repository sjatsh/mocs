var nowEqModId=0;
var contentName="content1";
var imgCount=0;
var imgFinishCount=0;
function insertElement(x,y,imgUrl,equSerialNo,pathThree,ipAddress){
	 var xPercent = $(".content-info-bg").width()/1920;
	 var yPercent = $(".content-info-bg").height()/720;
	
	 x=parseInt(x*xPercent);
	 y=parseInt(y*yPercent);

	equSerialNo=rtrim(equSerialNo);
	var status=imgUrl.substring(0,2);
	if(null!=ipAddress&&""!=ipAddress&&"null"!=ipAddress){
		
		document.getElementById(contentName).innerHTML+="<div style='left:"+x+"px;top:"+y+"px;background:transparent;position:absolute;' id='"+equSerialNo+"showimg' >" +
		  "<img src='./images/device/"+imgUrl+"'style='z-index: 9;'  " +
		  "class='dMenu'  id='"+equSerialNo+"' onclick=\"oneTClick('"+equSerialNo+"','"+status+"')\" /><br>" +
	  "</div>";
	}else{
		
			document.getElementById(contentName).innerHTML+="<div style='left:"+x+"px;top:"+y+"px;background:transparent;position:absolute;' id='"+equSerialNo+"showimg' >" +
			  "<img src='./images/device/"+imgUrl+"'style='z-index: 9;'   " +
			  "class='dMenu'  " +
			  " id='"+equSerialNo+"' onclick=\"oneTClick('"+equSerialNo+"','"+status+"')\" /><br>" +
		  "</div>";
	
	}
	
	//先获取原图片的大小，不是屏幕大小，然后用原图片大小来等比例缩小或者放大。
	function toggleContent() {
		  //图片加载完毕
		  if(imgFinishCount == imgCount) {
			  var contentName2 = "content1";
			  if(contentName == "content1") {
				  contentName2 = "content2";
			  }
			  $("#" + contentName).css("visibility", "visible");
			  $("#" + contentName2).css("visibility", "hidden");
			  contentName = contentName2;
		  }

	}
	  imgReady('./images/device/'+imgUrl, function () { //ready
		  var imgwjb51=this.width;
	   	  var imghjb51=this.height;
	   	  var imgPercent = $(".content-info-bg").height()/720; //缩小比例为浏览器宽度/1920. 用高的比例来缩小
	   	  equSerialNo = equSerialNo.replace(".", "\\.");
	   	  var tsts="#"+contentName+ ">div>#" + equSerialNo;
	   	  $(tsts).css({
	   		  "width": parseInt(imgwjb51*imgPercent)+"px",
	   		  "height": parseInt(imghjb51*imgPercent)+"px"
	   	  });
		  imgFinishCount++;
		  toggleContent();
	  },null,function() {	//error
		  imgFinishCount++;
		  toggleContent();
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
	
	if(number<=60){
		return "#00b050";
	}else if(number<=80){
		return "#fed400";
	}else if(number>80){
		return "#fed400";
	}
}

//自动刷新调用方法
function reloadEqu() { 
	//alert(1);
	var a = document.getElementById("myform:xz").value;
	var b = document.getElementById("myform:yz").value;
	var c = document.getElementById("myform:equipmentName").value;
	var f = document.getElementById("myform:image").value;
	var g = document.getElementById("myform:ppcrValue").value;
	var k = document.getElementById("myform:nodpValue").value;
	var m = document.getElementById("myform:equdayValue").value;
	var o = document.getElementById("myform:rcrValue").value;
	var p = document.getElementById("myform:pathThreeValue").value;
	var q = document.getElementById("myform:ipAddressValue").value;

	var xc = a.split(",");
	var yc = b.split(",");
	var vc = c.split(",");
	var fc = f.split(",");
	var gc = g.split(",");
	var hc = (gc[0] + "").split(":");
	var kc = k.split(",");
	var lc = (kc[0] + "").split(":");
	var mc = m.split(",");
	var nc = (mc[0] + "").split(":");
	var pc = p.split(",");
	var qc = q.split(",");
	var ft=hjb51*(72/1080)
	//alert(2);
	var bfh="<span style='font-family:Arial Black;font-size:"+ft+"px;'>%</span>";
	document.getElementById("ppf").innerHTML = hc[1]+bfh;			
	document.getElementById("ppf").style.color=returnColor(parseInt(hc[1]));
	document.getElementById("pit").innerHTML = lc[1];
	//document.getElementById("pit").style.color=returnColor(parseInt(lc[1]));
	document.getElementById("ee").innerHTML = nc[1]+bfh;
	document.getElementById("ee").style.color=returnColor(parseInt(nc[1]));

	document.getElementById("rcr").innerHTML = o+bfh;
	document.getElementById(contentName).innerHTML = "";
	
	/*$(".titlecontenspan").each(function() {
		$.autosizeElement(this,true);
	});*/
	
	//document.getElementById("content").style.height = hjb51* 703 / 1080 + "px";
	//document.getElementById("content").style.widht = wjb51 + "px";
	
	if ("" != xc && xc.length > 0) {
		imgCount = xc.length;
		imgFinishCount = 0;
		
		for ( var i = 0; i < xc.length; i++) {
			
			insertElement(parseInt(xc[i]), parseInt(yc[i]), fc[i] + "",vc[i] + "", pc[i] + "", qc[i] + "");
		}
		var sname = document.getElementById("myform:selectName").value;
		for(i in document.images)document.images[i].ondragstart=imgdragstart;
	}
	
}	

var TimeFn = null;//双击事件
function doubleClick(equSerialNo, pathThree, ipAddress, status) {
	document.getElementById("bigceng").innerHTML = "";
	clearTimeout(TimeFn);
	var tvalue = rtrim(equSerialNo);
	if (null != status && "gz" == status) {
		send(equSerialNo);
		//error_chip.jpg
		document.getElementById("bigceng").style.backgroundImage = "url('./images/device/error_"+ pathThree + "')";
		//alert(document.getElementById("bigceng").style.backgroundImage);
		document.getElementById("bigceng").style.display = "block";
		document.getElementById("myform:fp").value = equSerialNo;
		document.getElementById("myform:equInformation").click();

	} else {
		if (tvalue == "HTC2050i-SYB21-01"|| tvalue == "ETC3650-SYB21-02"|| tvalue == "TC500R-SYB21-03") {
			//document.getElementById("bigceng").style.backgroundImage="url('/mocs/images/hetbg.png')";
			document.getElementById("bigceng").style.backgroundImage = "url('./images/device/swfz_"+ pathThree + "')";
			document.getElementById("bigceng").style.display = "block";
			document.getElementById("myform:fp").value = "TC500R-SYB21-03,ETC3650-SYB21-02,HTC2050i-SYB21-01";
			document.getElementById("myform:equInformation").click();
			var ipV = document.getElementById("myform:ipAddressValue").value;
			var ip = ipV.split(",");
			document.getElementById("bigceng").innerHTML = "<div id='bigchartpanel' class='profile_bigceng_equ_div_one'>"
					+ "<iframe id='myif1'  frameborder='0' scrolling='no' class='profile_fram' src='"+ip[0]+"'></iframe>"
					+ "</div>"
					+ "<div id='bigchartpane2' class='profile_bigceng_equ_div_two'>"
					+ "<iframe id='myif2'  frameborder='0' scrolling='no' class='profile_fram' src='"+ip[1]+"'></iframe>"
					+ "</div>"
					+ "<div id='bigchartpane3' class='profile_bigceng_equ_div_three'>"
					+ "<iframe id='myif3'  frameborder='0' scrolling='no' class='profile_fram' src='"+ip[2]+"'></iframe>"
					+ "</div>"
					+ "<div class='profile_bigceng_equ_div_four'><img src='images/one_two_three.png'></div>";
		} else {
			document.getElementById("bigceng").style.backgroundImage = "url('./images/device/swfz"+ pathThree + "')";
			//document.getElementById("bigceng").style.backgroundImage="url('/mocs/images/swfzbg_one.png')";
			document.getElementById("bigceng").style.display = "block";
			document.getElementById("myform:fp").value = equSerialNo;
			document.getElementById("myform:equInformation")
					.click();
			//document.getElementById("bigceng").innerHTML="<div id='bigchartpanel' style='position: absolute;left: 785px;top:30px;z-index:60000;width:745px;height:500px;background-color:transparent;'>"+
			//											"<iframe id='myif1'  frameborder='0' scrolling='no' class='profile_fram' src='/mocs/images/"+ipAddress+"'></iframe>"+
			//										 "</div>";
			document.getElementById("bigceng").innerHTML = "<div id='bigchartpanel' class='profile_bigceng_equ_div_one'>"
					+ "<iframe id='myif1'  frameborder='0' scrolling='no' class='profile_fram' src='"+ipAddress+"'></iframe>"
					+ "</div>";

		}
	}
}
//单击事件
function oneTClick(equSerialNo, status) {
	clearTimeout(TimeFn);
	TimeFn = setTimeout(
			function() {
				if (null != status && "yx" == status) {
				//	SetCookie("equSerialNo", rtrim(equSerialNo));
					SetCookie("equSerialNo", rtrim(equSerialNo));
					act(3);
					//window.location.href = "#{facesContext.externalContext.requestContextPath}/index.faces?page=./device/workshop_management_new.xhtml";
					//window.location='index.faces?page=./device/workshop_management_new.xhtml';
					//SetCookie("menufirst",remember('/mocs/device/workshop_management.xhtml'));
					if(equSerialNo=="i5M4-CeBIT-03"||equSerialNo=="i5T3-CeBIT-02"||equSerialNo=="i5M8-CeBIT-04"
						||equSerialNo=="i5T3-CeBIT-01"){
						jumpto('/mocs/device/workshop_management_new.xhtml');
					}else{
						jumpto('/mocs/device/workshop_management.xhtml');
					}
					
					document.getElementById("myform:sbglys").click();
				} else {
					SetCookie("equSerialNo", rtrim(equSerialNo));
					act(3);
					//SetCookie("menufirst",remember('/mocs/device/workshop_management.xhtml'));
					//window.location.href = "#{facesContext.externalContext.requestContextPath}/index.faces?page=./device/workshop_management.xhtml";
					//window.location='index.faces?page=./device/workshop_management.xhtml';
					jumpto('/mocs/device/workshop_management.xhtml');
					document.getElementById("myform:sbglys").click();

				}

			}, 300);
}

function rtrim(s) {
	return s.replace(/(\s*$)/, "");
}
function yingcan() {
	document.getElementById("bigceng").style.display = "none";
}


function toPage(cs) {
	//alert(a);
	var syurl = document.getElementById("myform:syurl").value;
	var shurl = document.getElementById("myform:shurl").value;
	if (cs == "sy") {
		window.location.href = syurl;
	}
	if (cs == "sh") {
		window.location.href = shurl;
	}

}


function onCompleteClick() {
	var ef = document.getElementById("myform:eif").value;
	var tvb = document.getElementById("myform:fp").value;
	var tvc = tvb.split(",");
	var tva = tvc[0];
	var f = document.getElementById("myform:image").value;
	var g = f.indexOf(tva);

	var str = f.substring(g - 2, g);
	if (null != tvb && tvb != "") {
		var ei = ef.split(",");
		if (null != str && "gz" == str) {
			document.getElementById("bigceng").innerHTML += "<div class='profile_bigceng_font_div_one' align='center'><font class='profile_bigceng_font'>加工进度</font>"
					+ "<br/><font class='profile_bigceng_font_green'>"
					+ (typeof ei[0] == "undefined" ? "0" : ei[0])
					+ "%</font>"
					+ "</div>"
					+ "<div class='profile_bigceng_font_div_two_two' align='center'><font class='profile_bigceng_font'>完成工件数</font>"
					+ "<br/><font class='profile_bigceng_font_green'>"
					+ (typeof ei[1] == "undefined" ? "0" : ei[1])
					+ "</font>"
					+ "</div>"
					+ "<div class='profile_bigceng_font_div_three' align='center'><font class='profile_bigceng_font'>总能耗</font>"
					+ "<br/><font class='profile_bigceng_font_red'>"
					+ (typeof ei[2] == "undefined" ? "0" : ei[2])
					+ "</font>"
					+ "</div>"
					+ "<div class='profile_bigceng_font_div_four' align='center'><font class='profile_bigceng_font'>单件成本</font>"
					+ "<br/><font class='profile_bigceng_font_yellow'>￥"
					+ (typeof ei[3] == "undefined" ? "0" : ei[3])
					+ "</font>" + "</div>";
		} else {
			if (tva == "HTC2050i-SYB21-01"
					|| tva == "ETC3650-SYB21-02"
					|| tva == "TC500R-SYB21-03") {
				document.getElementById("bigceng").innerHTML += "<div class='profile_bigceng_font_div_one_two' align='center'><font class='profile_bigceng_font'>加工进度</font>"
						+ "<br/><font class='profile_bigceng_font_green'>"
						+ (typeof ei[0] == "undefined" ? "0"
								: ei[0])
						+ "%</font>"
						+ "</div>"
						+ "<div class='profile_bigceng_font_div_two' align='center'><font class='profile_bigceng_font'>完成工件数</font>"
						+ "<br/><font class='profile_bigceng_font_green'>"
						+ (typeof ei[1] == "undefined" ? "0"
								: ei[1])
						+ "</font>"
						+ "</div>"
						+ "<div class='profile_bigceng_font_div_three' align='center'><font class='profile_bigceng_font'>总能耗</font>"
						+ "<br/><font class='profile_bigceng_font_red'>"
						+ (typeof ei[2] == "undefined" ? "0"
								: ei[2])
						+ "</font>"
						+ "</div>"
						+ "<div class='profile_bigceng_font_div_four' align='center'><font class='profile_bigceng_font'>单件成本</font>"
						+ "<br/><font class='profile_bigceng_font_yellow'>￥"
						+ (typeof ei[3] == "undefined" ? "0"
								: ei[3]) + "</font>" + "</div>";
			} else {
				document.getElementById("bigceng").innerHTML += "<div class='profile_bigceng_font_div_one' align='center'><font class='profile_bigceng_font'>加工进度</font>"
						+ "<br/><font class='profile_bigceng_font_green'>"
						+ (typeof ei[0] == "undefined" ? "0"
								: ei[0])
						+ "%</font>"
						+ "</div>"
						+ "<div class='profile_bigceng_font_div_two_two' align='center'><font class='profile_bigceng_font'>完成工件数</font>"
						+ "<br/><font class='profile_bigceng_font_green'>"
						+ (typeof ei[1] == "undefined" ? "0"
								: ei[1])
						+ "</font>"
						+ "</div>"
						+ "<div class='profile_bigceng_font_div_three' align='center'><font class='profile_bigceng_font'>总能耗</font>"
						+ "<br/><font class='profile_bigceng_font_red'>"
						+ (typeof ei[2] == "undefined" ? "0"
								: ei[2])
						+ "</font>"
						+ "</div>"
						+ "<div class='profile_bigceng_font_div_four' align='center'><font class='profile_bigceng_font'>单件成本</font>"
						+ "<br/><font class='profile_bigceng_font_yellow'>￥"
						+ (typeof ei[3] == "undefined" ? "0"
								: ei[3]) + "</font>" + "</div>";
			}
		}
	}
}

function loadXML(xmlString) {

	var xmlDoc = null;
	//判断浏览器的类型
	//支持IE浏览器 
	if (!window.DOMParser && window.ActiveXObject) { //window.DOMParser 判断是否是非ie浏览器
		var xmlDomVersions = [ 'MSXML.2.DOMDocument.6.0',
				'MSXML.2.DOMDocument.3.0', 'Microsoft.XMLDOM' ];
		for ( var i = 0; i < xmlDomVersions.length; i++) {
			try {
				xmlDoc = new ActiveXObject(xmlDomVersions[i]);
				xmlDoc.async = false;
				xmlDoc.loadXML(xmlString); //loadXML方法载入xml字符串
				break;
			} catch (e) {
			}
		}
	}
	//支持Mozilla浏览器
	else if (window.DOMParser && document.implementation
			&& document.implementation.createDocument) {
		try {
			/* DOMParser 对象解析 XML 文本并返回一个 XML Document 对象。
			 * 要使用 DOMParser，使用不带参数的构造函数来实例化它，然后调用其 parseFromString() 方法
			 * parseFromString(text, contentType) 参数text:要解析的 XML 标记 参数contentType文本的内容类型
			 * 可能是 "text/xml" 、"application/xml" 或 "application/xhtml+xml" 中的一个。注意，不支持 "text/html"。
			 */
			domParser = new DOMParser();
			xmlDoc = domParser.parseFromString(xmlString,
					'text/xml');
		} catch (e) {
		}
	} else {
		return null;
	}

	return xmlDoc;
}

function send(equSerialNo) {
	var data = new Array();
	data[0] = "";
	data[1] = "";
	data[2] = "";
	//var macTemp="TC500R-SYB21-03";
	var macTemp = equSerialNo;
	var infoTemp = "";

	$.ajax({
				url : 'http://192.168.2.19:8080/mocs/WSBZService/Portal.json?method=DiagnosticMessage',
				data : 'parms=' + macTemp,
				cache : false,
				dataType : 'text',
				success : function(xml) {
					//alert(xml);
					var a = JSON.parse(xml);

					var xmldoc = loadXML(a.data);

					var x = xmldoc.documentElement.childNodes;
					//alert(xml);
					for (i = 0; i < x.length; i++) {
						if (x[i].nodeType == 1) {
							infoTemp = "error_" + x[i].nodeName
									+ ".png";
							// alert(infoTemp);
							// x[i].getAttribute("alertInfo");
							//infoTemp=x[i].getAttribute("alertInfo");	
							var a = (x[i].childNodes);
							for (j = 0; j < a.length; j++) {
								var cause = a[j]
										.getAttribute("cause");
								data[j] = j + 1 + "." + cause;
							}
							break;
						}
					}
					//alert(infoTemp+"2");
					var error = document.getElementById("bigceng");
					error.innerHTML += "<div  style='margin-left:775px;margin-top:280px;width:370px;height:255px;position: absolute;font-family:微软雅黑;color: #595959;'  align='center'>"
							+ "<div style='width:370px;height:25px;'></div>"
							+ "<b><font style='font-size:30px;'>"
							+ data[0]
							+ "</font></b>"
							+ "</div>"
							+ "<div  style='margin-left:1160px;margin-top:280px;width:370px;height:255px;position: absolute;font-family:微软雅黑;color: #595959;'  align='center'>"
							+ "<div style='width:370px;height:25px;'></div>"
							+ "<b><font style='font-size:30px;'>"
							+ data[1]
							+ "</font></b>"
							+ "</div>"
							+ "<div style='margin-left:1540px;margin-top:280px;width:370px;height:255px;position: absolute;font-family:微软雅黑;color: #595959;' align='center'>"
							+ "<div style='width:370px;height:25px;'></div>"
							+ "<b><font style='font-size:30px;'>"
							+ data[2] + "</font></b>" + "</div>";
					error.innerHTML += "<div  style='margin-left:775px;margin-top:10px;width:370px;height:255px;position: absolute;font-family:微软雅黑;color: #595959;'  align='center'>"
							+ "<div style='width:370px;height:80px;'></div>"
							+ "<b><font style='font-size:60px;'>故障分析</font></b>"
							+ "</div>"
							+ "<div  style='margin-left:1160px;margin-top:10px;width:370px;height:255px;position: absolute;background-image: url(/mocs/images/device/"
							+ infoTemp
							+ ");'  >"
							+ "</div>"
							+ "<div style='margin-left:1540px;margin-top:10px;width:370px;height:255px;position: absolute;font-family:微软雅黑;color: #595959;' align='center'>"
							+ "<div style='width:370px;height:80px;'></div>"
							+ "<b><font style='font-size:40px;'>故障信息已推送给</font></b><br/><br/>"
							+ "<b><font style='font-size:40px;color:green;'>王晓明</font></b>"
							+ "</div>";
				}
			});
}	

$("#dianji").click(function() {
	  $("#xsdiv").stop();
	  $("#xsdiv").animate({right:"0"}, 500);
	  $("#dianji").animate({right:"-100%"}, 500);
});
$("#ycinfoimg").click(function() {
	  $("#xsdiv").stop();
	  $("#xsdiv").animate({right:"-100%"}, 500);
	  $("#dianji").animate({right:"-63%"}, 500);
});

//无背景图时添加默认背景
var bgpath=document.getElementById("myform:bgpath").value+"";
if(null!=bgpath&&""!=bgpath){
	document.getElementById("centerbg").src="./images/"+bgpath;
}else{
	document.getElementById("centerbg").src="./images/WIS2_content_bg.png";
}

//页面加载事件
$(document).ready(function() {
	//国际化翻译
	dataTranslate("factory_profile", function(t) { $("*[data-i18n]").i18n();});
	reloadEqu();
});