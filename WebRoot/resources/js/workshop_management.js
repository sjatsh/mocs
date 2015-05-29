var imgPercent = hjb51/1080; //比例 
function loadData() {
	
	var sbequSerialNo=$("#sbequSerialNo").text();
	if(null!=sbequSerialNo){
		sbequSerialNo=sbequSerialNo.trim();
	}
	if(sbequSerialNo=="i5M4-CeBIT-03"||sbequSerialNo=="i5T3-CeBIT-02"||sbequSerialNo=="i5M8-CeBIT-04"
		||sbequSerialNo=="i5T3-CeBIT-01"){
		var ipAddressValue =document.getElementById("myform:ipAddressValue").value;
		if(null!=ipAddressValue&&""!=ipAddressValue){
			document.getElementById("fzsrc").src=ipAddressValue;
			
			document.getElementById("lp").style.display="none";
			document.getElementById("fzsrc").style.display="block";
		}
	}else{
		document.getElementById("lp").style.display="block";
		document.getElementById("fzsrc").style.display="none";
	}
	
	/*else{
		document.getElementById("fzimg").style.display="block";
		document.getElementById("fzsrc").style.display="none";
	}*/
	var fvalue = document.getElementById("myform:fValue").value;
	var svalue = document.getElementById("myform:sValue").value;
	var oeeValue=document.getElementById("myform:oeeValue").value;
	var fv=[Math.abs(parseFloat(fvalue))];
	var sv=[Math.abs(parseFloat(svalue))];
	var ov=[Math.abs(parseFloat(oeeValue))];
	if(fv>3000){
		fv=3000;
	}
	if(sv>3000){
		sv=3000;
	}
	//加载仪表图
	var zz1= 110/3000 *fv +30; 
	document.getElementById("zhizhen1").style.transform="rotate3d(0,0,1,"+zz1+"deg)";
	var zz2= 110/3000 *sv +30; 
	document.getElementById("zhizhen2").style.transform="rotate3d(0,0,1,"+zz2+"deg)";
	var deg=360*(ov/100);
	document.getElementById("oeeline").style.transform="rotate3d(0,0,1,"+deg+"deg)";

	//加载时间分布图
	var jsonData1 = document.getElementById("myform:pieValue").value;
	//jsonData1="";
	if(null !=jsonData1 && ""!=jsonData1 ){
		var barModel = eval('(' + jsonData1 + ')');
		if((barModel.fz.y==null||barModel.fz.y==""||barModel.fz.y=='null')
			&&(barModel.kx.y==null||barModel.kx.y==""||barModel.kx.y=='null')&&(barModel.tz.y==null||barModel.tz.y==""||barModel.tz.y=='null')
			&&(barModel.zb.y==null||barModel.zb.y==""||barModel.zb.y=='null')&&(barModel.qx.y==null||barModel.qx.y==""||barModel.qx.y=='null')){
			
			document.getElementById("pieTureal").innerHTML="<div class='pieTureal-txt' style='width: "+wjb51*(200/1920)+"px;"+
			"height:"+hjb51*(170/1080)+"px;line-height: "+hjb51*(170/1080)+"px;'>"+
			"<span data-i18n='zwxxinfo'></span><div>";
		}else{
			getPieChart(barModel);
		}
	}else{
		document.getElementById("pieTureal").innerHTML="<div class='pieTureal-txt' style='width: "+wjb51*(200/1920)+"px;"+
															"height:"+hjb51*(170/1080)+"px;line-height: "+hjb51*(170/1080)+"px;'>"+
															"<span data-i18n='zwxxinfo'></span><div>";
	}
	//加载月产量
	var jsonData2 = document.getElementById("myform:piecbValue").value;
	if(null !=jsonData2 && ""!=jsonData2 ){
		//jsonData2="";
		if(jsonData2.length<=3){
				document.getElementById("pietwo").innerHTML="<br/><br/><span data-i18n='zwxxinfo'></span>";
		}else{
			var barModel = eval('(' + jsonData2 + ')');
			getPieChartTwo(barModel);
			var tb=document.getElementById("fontsizefour");
			tb.innerHTML="";
			var tbstr="";
			var fs=hjb51*(16/1080)
		    for(var j=0;j<barModel.length;j++){
		    		var bname=barModel[j].name+"";
					var bn=bname.split(",");
					var ts=bn[1]+"--"+bn[0];
					    ts=ts.length > 20 ? ts.substr(0,20) + "..." : ts;
					tbstr+="<div  class='auto-size YSWH YSLT"+(j+1)+"' style='background-color:"+barModel[j].color+"'></div>"+
						   "<div class='auto-size FONTLH FONTLT"+(j+1)+"' style='font-size:"+fs+"'>"+ts+"</div>";
					
			 }
		    tb.innerHTML=tbstr;
		    $.autosizeElement(tb,true);
		}
	}
	//加载日产量
	var jsonData3 = document.getElementById("myform:piecbtwoValue").value;
	if(null !=jsonData3 && ""!=jsonData3 ){
		if(jsonData3.length<=3){
			document.getElementById("piethree").innerHTML="<br/><br/><span data-i18n='zwxxinfo'></span>";
		}else{
			var barModel = eval('(' + jsonData3 + ')');
			getPieChartThree(barModel);	
		}
	}
}


/**
 * 创建饼图
 * @param barModel
 */
function getPieChart(barModel){
    chart = new Highcharts.Chart({
        chart: {
        	renderTo: 'pieTureal',
            type: 'pie',
            height:hjb51*(170/1080),
	        width:wjb51*(200/1920),
            backgroundColor: 'rgba(0, 255, 255, 0)',
            plotBorderColor : null,
            plotBackgroundColor: null,
            plotBackgroundImage:null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '',
            align: 'left',
	        style:{
	        	color:'#505050',
	        	fontSize:'12px'
	        }
        },
        yAxis: {
            title: {
                text: ''
            }
        },
        credits: {  
        	  enabled: false  
        },  
        exporting: {
            enabled: false
        },
        plotOptions: {
            pie: {
            	 allowPointSelect: true,
                 cursor: 'pointer',
                 dataLabels: {
                     enabled: false
                 },
                 showInLegend: false
            }
        },
        legend: {
            layout:'vertical'
//            align:'right'  
        },
        tooltip: {
        	enabled:false
        },
        series: [{
            name: '',
            data:[{name:barModel.qx.name,y:parseFloat(barModel.qx.y),color:'rgba(108,204,71,1)'},
                  {name:barModel.fz.name,y:parseFloat(barModel.fz.y),color:'rgba(0,129,206,1)'},
                  {name:barModel.zb.name,y:parseFloat(barModel.zb.y),color:'rgba(0,129,206,0.4)'},
                  {name:barModel.kx.name,y:parseFloat(barModel.kx.y),color:'rgba(244,221,12,1)'},
                  {name:barModel.tz.name,y:parseFloat(barModel.tz.y),color:'rgba(160,160,160,1)'}],
             innerSize:'55%'
        }]
    });
}

/**
 * 创建饼图
 * @param barModel
 */
function getPieChartTwo(barModel){
    chart = new Highcharts.Chart({
        chart: {
        	renderTo: 'pietwo',
            type: 'pie',
            height:hjb51*240/1080,
            width:wjb51*(384/1920), 
            /*背景透明*/
            backgroundColor: 'rgba(255, 255, 255, 0)',
            plotBorderColor : null,
            plotBackgroundColor: null,
            plotBackgroundImage:null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '',
            align: 'left',
            
	        style:{
	        	color:'#505050'
	        }
        },
        yAxis: {
            title: {
                text: ''
            }
        },
        credits: {  
        	  enabled: false  
        },  
        exporting: {
            enabled: false
        },
        plotOptions: {
            pie: {
            	 allowPointSelect: true,
                 cursor: 'pointer',
                 dataLabels: {
                     enabled: false
                 },
                 showInLegend: false
            }
        },
        tooltip: {	
    	    valueSuffix: '%',
    	    formatter: function() {
    	    	var na=this.point.name;
    	    	var nn=na.split(",");
    	    	var title1 = "content.lj",title2 = "content.gx",title3 = "content.sl",title4 = "content.bfb";
				
    	    	dataTranslate("workshop_management", function(t) { 
					title1 = t(title1); 
					title2 = t(title2); 
					title3 = t(title3); 
					title4 = t(title4);
				});
    	    	return '<b style="color:rgba(102,102,102,1);">' + title1 + ':</b><b style="color:rgba(102,102,102,1);">'+ nn[1] +
				   '</b><br/><b style="color:rgba(102,102,102,1);">' + title2 + ':</b><b style="color:rgba(102,102,102,1);">'+nn[0]+
				   '</b><br/><b style="color:rgba(102,102,102,1);">' + title3 + ':</b><b style="color:rgba(102,102,102,1);">'+Highcharts.numberFormat(this.y, 2, ',')+
				   '</b><br/><b style="color:rgba(102,102,102,1);">' + title4 + ':</b><b style="color:rgba(102,102,102,1);">'+Highcharts.numberFormat(this.percentage, 2)+
				   '%</b>';
				
             },
	        style: {  //提示框内容的样式
	             color: 'black',
	             fontSize: '12px',   
	             borderColor: '#000000'
	        }
        },
        series: [{
            name: '',
            data:barModel,
            innerSize: '60%'
        		}]
    });
}

/**
 * 创建饼图
 * @param barModel
 */
function getPieChartThree(barModel){
    chart = new Highcharts.Chart({
        chart: {
        	renderTo: 'piethree',
            type: 'pie',
            height:hjb51*240/1080,
            width:wjb51*(384/1920),
            /*背景透明*/
            backgroundColor: 'rgba(255, 255, 255, 0)',
            plotBorderColor : null,
            plotBackgroundColor: null,
            plotBackgroundImage:null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
        	text: '',
            align: 'left',
	        style:{
	        	color:'#505050',
	        }
        },
        yAxis: {
            title: {
                text: ''
            }
        },
        credits: {  
        	  enabled: false  
        },  
        exporting: {
            enabled: false
        },
        plotOptions: {
            pie: {
            	 allowPointSelect: true,
                 cursor: 'pointer',
                 dataLabels: {
                     enabled: false,
                     format: '<b>{point.name}</b>: {point.percentage:.2f} %'
                 },
                 showInLegend: false
            }
        },
        tooltip: {
    	    valueSuffix: '%',
    	    formatter: function() {
    	    	var na=this.point.name;
    	    	var nn=na.split(",");
    	    	var title1 = "content.lj",title2 = "content.gx",title3 = "content.sl",title4 = "content.bfb";
				dataTranslate("workshop_management", function(t) { 
					title1 = t(title1); 
					title2 = t(title2); 
					title3 = t(title3); 
					title4 = t(title4);
				});
				return '<b style="color:rgba(102,102,102,1);">' + title1 + ':</b><b style="color:rgba(102,102,102,1);">'+ nn[1] +
					   '</b><br/><b style="color:rgba(102,102,102,1);">' + title2 + ':</b><b style="color:rgba(102,102,102,1);">'+nn[0]+
					   '</b><br/><b style="color:rgba(102,102,102,1);">' + title3 + ':</b><b style="color:rgba(102,102,102,1);">'+Highcharts.numberFormat(this.y, 2, ',')+
					   '</b><br/><b style="color:rgba(102,102,102,1);">' + title4 + ':</b><b style="color:rgba(102,102,102,1);">'+Highcharts.numberFormat(this.percentage, 2)+
					   '%</b>';
             },
	        style: {  //提示框内容的样式
	             color: 'black',
	             fontSize: hjb51*16/1080+'px',   
	             borderColor: '#000000'
	        }
        },
        series: [{
            name: '',
            data:barModel,
            innerSize: '50%'
        		 }]
    });
}

function reloadEqu(){
 	var x=document.getElementById("myform:xValue");
	var y=document.getElementById("myform:yValue");
	var z=document.getElementById("myform:zValue");
	var a=document.getElementById("myform:aValue");
	var b=document.getElementById("myform:bValue");
	var c=document.getElementById("myform:cValue");
	var u=document.getElementById("myform:uValue");
	var v=document.getElementById("myform:vValue");
	var w=document.getElementById("myform:wValue");
	var f=document.getElementById("myform:fValue").value;
	var s=document.getElementById("myform:sValue").value;
	var fu=document.getElementById("myform:funishnumValue").value;
	var est=document.getElementById("myform:estimateLastTimeValue").value;
	var pn=document.getElementById("myform:processNumValue").value;
	var no=document.getElementById("myform:noValue").value;
	var name=document.getElementById("myform:nameValue").value;
	var processName=document.getElementById("myform:processNameValue").value;
	
	var label=new Array();
	var value=new Array();
	if(null!=x.value&&""!=x.value){label.push("X");value.push(x.value);}
	if(null!=y.value&&""!=y.value){label.push("Y");value.push(y.value);}
	if(null!=z.value&&""!=z.value){label.push("Z");value.push(z.value);}
	if(null!=a.value&&""!=a.value){label.push("A");value.push(a.value);}
	if(null!=b.value&&""!=b.value){label.push("B");value.push(b.value);}
	if(null!=c.value&&""!=c.value){label.push("C");value.push(c.value);}
	if(null!=u.value&&""!=u.value){label.push("U");value.push(u.value);}
	if(null!=v.value&&""!=v.value){label.push("V");value.push(v.value);}
	if(null!=w.value&&""!=w.value){label.push("W");value.push(w.value);}

	for(var t=0;t<value.length;t++){
		var tv=value[t];
		value[t]=parseFloat(tv).toFixed(3);
	}
	if(label.length==2){
		document.getElementById("xyzabc").innerHTML=""+
                "<div class='auto-size fwh flt11'>"+label[0]+"</div>"+
                "<div class='auto-size vwh vlt11'>"+value[0]+"</div>"+     
                "<div class='auto-size fwh flt12'>"+label[1]+"</div>"+
                "<div class='auto-size vwh vlt12'>"+value[1]+"</div>";     
	}else if(label.length==3){
	   	document.getElementById("xyzabc").innerHTML=""+
		        "<div class='auto-size fwh flt21'>"+label[0]+"</div>"+
                "<div class='auto-size vwh vlt21'>"+value[0]+"</div>"+     
                "<div class='auto-size fwh flt22'>"+label[1]+"</div>"+
                "<div class='auto-size vwh vlt22'>"+value[1]+"</div>"+
                "<div class='auto-size fwh flt23'>"+label[2]+"</div>"+
                "<div class='auto-size vwh vlt23'>"+value[2]+"</div>";    
	}else if(label.length==5){
		document.getElementById("xyzabc").innerHTML=""+
					 "<div class='auto-size fwh flt31'>"+label[0]+"</div>"+
                	 "<div class='auto-size vwh vlt31'>"+value[0]+"</div>"+     
                	 "<div class='auto-size fwh flt32'>"+label[1]+"</div>"+
                	 "<div class='auto-size vwh vlt32'>"+value[1]+"</div>"+
                	 "<div class='auto-size fwh flt33'>"+label[2]+"</div>"+
                	 "<div class='auto-size vwh vlt33'>"+value[2]+"</div>"+
                	 "<div class='auto-size fwh flt34'>"+label[3]+"</div>"+
                	 "<div class='auto-size vwh vlt34'>"+value[3]+"</div>"+
                	 "<div class='auto-size fwh flt35'>"+label[4]+"</div>"+
                	 "<div class='auto-size vwh vlt35'>"+value[4]+"</div>";              
	}else{
		document.getElementById("xyzabc").innerHTML="<span data-i18n='zwxxinfo'></span>";
	}
	
	$("#xyzabc>div").each(function() {
		$.autosizeElement(this,true);
	});
	partUpdateBg();//零件背景图加载
	
	if(null!=f&&""!=f){
		document.getElementById("fv").innerHTML=f;
	}else{
		document.getElementById("fv").innerHTML="<span data-i18n='zwxxinfo'></span>";
	}
	if(null!=s&&""!=s){
		document.getElementById("sv").innerHTML=s;
	}else{
		document.getElementById("sv").innerHTML="<span data-i18n='zwxxinfo'></span>";
	}
	gdqypd(fu,est,pn,no,name,processName);//工单区域 是否为空判断和字符处理
	
	//仪表图数据加载
	var fvalue = document.getElementById("myform:fValue").value;
	var fv=[Math.abs(parseFloat(fvalue))];
	if(fv>3000){
		fv=3000;
	}
	var zz1= 110/3000 *fv +30; 
	document.getElementById("zhizhen1").style.transform="rotate3d(0,0,1,"+zz1+"deg)";
	
	var svalue = document.getElementById("myform:sValue").value;
	var sv=[Math.abs(parseFloat(svalue))];
	if(sv>3000){
		sv=3000;
	}
	var zz2= 110/3000 *sv +30; 
	document.getElementById("zhizhen2").style.transform="rotate3d(0,0,1,"+zz2+"deg)";
	
	var oee=document.getElementById("myform:oeeValue");
    var oeeValue=oee.value;
    if(oeeValue>=100)
     	oeeValue=100;
	var deg=360*(oeeValue/100);
	document.getElementById("oeeline").style.transform="rotate3d(0,0,1,"+deg+"deg)";
 }

function CheckImgExists(imgurl) {
	//先获取原图片的大小，不是屏幕大小，然后用原图片大小来等比例缩小或者放大。
	 var result =false;
	 imgReady(imgurl, function () { 
	  var imgw=this.width;
 	  var imgh=this.height;
 	 if (imgw > 0 || imgh > 0) {
 		result = true;
     } else {
         result = false;
     }
	}); 
	  return result;
}
function xswzgd(){
	var wzgd=document.getElementById("myform:jobDispatchListNoValue").value;
	if(null==wzgd||"..."==wzgd||"暂无信息"==wzgd){
		
	}else{
		document.getElementById("gongdan").innerHTML+="<div style='position: absolute;color:#bfbfbf;font-size:15px;"
	    	+"background-image: url(./images/gdnamebg.png');'><div style='width:100%;height:2px;'></div> <div style='widht:100%'>"+wzgd+"</div>"
	    	+"<div style='widht:100%;height:5px;'></div>";
	}
}

function gdqypd(fu,est,pn,no,name,processName){
	var wzgd=document.getElementById("myform:jobDispatchListNoValue").value;
	 var gdxsss=""+(wzgd.substring(0,30).replace(/^\s+|\s+$/g,"")+"...");
	 if(null==wzgd||""==wzgd){
		 gdxsss="<span data-i18n='zwxxinfo'></span>";
		 
	 }
	document.getElementById("gongdan").innerHTML=gdxsss;
	 
	if(null==fu||""==fu){
		document.getElementById("wcth").innerHTML="<span data-i18n='zwxxinfo'></span>";
	}else{
		document.getElementById("wcth").innerHTML=fu;
	}
	if(null==est||""==est){
		document.getElementById("sysjTh").innerHTML="<span data-i18n='zwxxinfo'></span>";
	}else{
		document.getElementById("sysjTh").innerHTML=est;
	}
	
	document.getElementById("myform:wcTs").innerHTML=fu;
	
	if(null==pn||""==pn){
		document.getElementById("jhth").innerHTML="<span data-i18n='zwxxinfo'></span>";
	}else{
		document.getElementById("jhth").innerHTML=pn;
	}
	
	if(null==no||""==no){
		document.getElementById("gjidtxt").innerHTML="<span data-i18n='zwxxinfo'></span>";
	}else{
		document.getElementById("gjidtxt").innerHTML=no;
	}
	if(null==name||""==name){
		document.getElementById("gjmc").innerHTML="<span data-i18n='zwxxinfo'></span>";
	}else{
		document.getElementById("gjmc").innerHTML=name;
	}
	if(null==processName||""==processName){
		document.getElementById("gxmc").innerHTML="<span data-i18n='zwxxinfo'></span>";
	}else{
		document.getElementById("gxmc").innerHTML=processName;
	}
	
	dataTranslate("workshop_management", function(t) { $("*[data-i18n]").i18n();});
}
function partUpdateBg(){
	var partbg=document.getElementById("myform:partbg").value;
	if(null!=partbg&&""!=partbg){ 
		document.getElementById("lp").src="./images/part/"+partbg;
		/*imgReady('./images/part/'+partbg, function () { //ready
			  var imgwjb51=this.width;
		   	  var imghjb51=this.height;
		   	  var imgPercent = wjb51/2150; //缩小比例为浏览器宽度/1920. 用高的比例来缩小 
		   	  $("#lp").css({
		   		  "width": parseInt(imgwjb51*imgPercent)+"px",
		   		  "height": parseInt(imghjb51*imgPercent)+"px"
		   	  });
		  }); */ 
	}else{
		document.getElementById("lp").src="./images/part/zwtp.png";
		/*imgReady('./images/part/zwtp.png', function () { //ready
			  var imgwjb51=this.width;
		   	  var imghjb51=this.height;
		   	  var imgPercent = wjb51/2150; //缩小比例为浏览器宽度/1920. 用高的比例来缩小 
		   	  $("#lp").css({
		   		  "width": parseInt(imgwjb51*imgPercent)+"px",
		   		  "height": parseInt(imghjb51*imgPercent)+"px"
		   	  });
		  });  */
	}
	
	
	
}

$(document).ready(function() {
	//国际化翻译
	dataTranslate("workshop_management", function(t) { $("*[data-i18n]").i18n();});
	
	/*var wzgd=document.getElementById("myform:jobDispatchListNoValue").value;
	var gdxsss=""+(wzgd.substring(0,25).replace(/^\s+|\s+$/g,"")+"...");
	document.getElementById("gongdan").innerHTML=gdxsss;*/
	//加载数据
   	loadData();
    reloadEqu();//加载数据
	partUpdateBg();//加载零件背景
	
	//页面尺寸调整
	
	$.autosizeExclude($("#fzi"), true);
	$.autosizeExclude($("#piethree>div")[0], true);
	$.autosizeExclude($("#pieTureal>div")[0], true);
	$.autosizeExclude($("#pietwo>div")[0], true);
	$.autosizeExclude($(".circle>img")[0], true);
	$.autosizeExclude($("#oeeline>img")[0], true);
	
	$.autosizeFrame(false);
});
