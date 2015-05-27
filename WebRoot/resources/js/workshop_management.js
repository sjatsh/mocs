var imgPercent = hjb51/1080; //比例 
function loadData() {
	var fvalue = document.getElementById("myform:fValue").value;
	var svalue = document.getElementById("myform:sValue").value;
	var oeeValue=document.getElementById("myform:oeeValue").value;
	
	var fv=[Math.abs(parseFloat(fvalue))];
	var sv=[Math.abs(parseFloat(svalue))];
	var ov=[Math.abs(parseFloat(oeeValue))];
	
	generateChart(fv);
	generateChartTwo(sv);
	generateChartThree(ov);
	
	var jsonData1 = document.getElementById("myform:pieValue").value;
	
	if(null !=jsonData1 && ""!=jsonData1 ){
		var barModel = eval('(' + jsonData1 + ')');
		if((barModel.fz.y==null||barModel.fz.y==""||barModel.fz.y=='null')
			&&(barModel.kx.y==null||barModel.kx.y==""||barModel.kx.y=='null')&&(barModel.tz.y==null||barModel.tz.y==""||barModel.tz.y=='null')
			&&(barModel.zb.y==null||barModel.zb.y==""||barModel.zb.y=='null')&&(barModel.qx.y==null||barModel.qx.y==""||barModel.qx.y=='null')){
			document.getElementById("pieTureal").innerHTML="<br/><br/>暂无信息";
			//alert(1);
		}else{
			//alert(2);
			getPieChart(barModel);
		}
		
	}
	
	var jsonData2 = document.getElementById("myform:piecbValue").value;
	
	if(null !=jsonData2 && ""!=jsonData2 ){

		if(jsonData2.length<=3){
				document.getElementById("pietwo").innerHTML="<br/><br/>暂无信息";
		}else{
			var barModel = eval('(' + jsonData2 + ')');
			getPieChartTwo(barModel);
			var tb=document.getElementById("fontsizefour");
			tb.style.fontSize=hjb51*16/1080+"px";
			var rowNum=tb.rows.length;
		     for (i=0;i<rowNum;i++)
		     {
		         tb.deleteRow(i);
		         rowNum=rowNum-1;
		         i=i-1;
		     }
		     for(var j=0;j<barModel.length;j++){
		    		var bname=barModel[j].name+"";
					var bn=bname.split(",");
					//alert(bn[0]+"-"+bn[1]+"-"+barModel[j].color+"");
					row = document.createElement("tr");
					var cell;
					cell = document.createElement("td");
					cell.style="width:25%;";
					cell.innerHTML = "<div   style='float: left;background-color:"+barModel[j].color+";width:100%;'> &nbsp;</div>";
					row.appendChild(cell);
					
					var cell2;
					cell2 = document.createElement("td");
					cell2.style="width:75%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;";
					var ts=bn[1]+"--"+bn[0];
					cell2.innerHTML = ts.length > 20 ? ts.substr(0,20) + "..." : ts;
					row.appendChild(cell2);
					tb.appendChild(row);
			 }
		     
		    
		}
		
	}

	var jsonData3 = document.getElementById("myform:piecbtwoValue").value;
	
	if(null !=jsonData3 && ""!=jsonData3 ){
		//alert(jsonData3);
		if(jsonData3.length<=3){
				document.getElementById("piethree").innerHTML="<br/><br/>暂无信息";
		}else{
			var barModel = eval('(' + jsonData3 + ')');
			getPieChartThree(barModel);	
		}
		
	}

	
}


function generateChart(fv) {
	var chart = new Highcharts.Chart({
		chart: {
			renderTo: 'tutest',
	        type: 'gauge',
	        height:hjb51*220/1080,
	        width:wjb51*0.19,
	        plotBorderWidth: 1,
	        plotBackgroundImage: null,
	        backgroundColor: 'rgba(255, 255, 255, 0)',
            plotBorderColor : null,
            plotBackgroundColor: null,
            plotBackgroundImage:null,
            plotBorderWidth: null,
            plotShadow: false  
	    },
		credits: {
            	 text: '',
            	 href: ''
        },
	    title: {
	        text: '',
	        align: 'left',
	        style:{
	        	color:'#505050',
	        	fontSize:'12px'
	        }
	    },
	    
		exporting:{ 
			 enabled:false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示
		},
	    pane: [{
	        startAngle: -45,
	        endAngle: 45,
	        background: null,
	        center: ['50%', '130%'],
	        size: hjb51*320/1080
	    }],	    		        
	
	    yAxis: [{
	        min: 0,
	        max: 3000,
	        minorTickPosition: 'outside',
	        tickPosition: 'outside',
	        tickInterval: 1000,
	        labels: {
	        	rotation: 'auto',
	        	distance: 20
	        },
	        plotBands: [{
	        	from: 2500,
	        	to: 3000,
	        	color: '#C02316',
	        	innerRadius: '100%',
	        	outerRadius: '105%'
	        }],
	        pane: 0,
	        title: {
	        	text: "<br/><span>进给速度(mm/min)</span>",
	        	y: -40
	        }
	    }],
	    plotOptions: {
	    	gauge: {
	    		dataLabels: {
	    			enabled: false
	    		},
	    		dial: {
	    			radius: '100%'
	    		}
	    	}
	    },
	    series: [{
	        data: fv,
	        yAxis: 0
	    }]
	   },
		function(chart) {
		    setInterval(function() {
		        var left = chart.series[0].points[0],
		            leftVal, 
		            inc = (Math.random() - 0.5) * 3;
		        var fvalue = document.getElementById("myform:fValue").value;
		        leftVal = Math.abs(parseFloat(fvalue));
		        if(leftVal>=3000)
		        	leftVal=3000;
		        left.update(leftVal, false);
		        chart.redraw();
		
		    }, 500);
		
		}
	   );	

};

function generateChartTwo(sv) {
	var chart = new Highcharts.Chart({
		chart: {
			renderTo: 'tutest2',
	        type: 'gauge',
	        height:hjb51*170/1080,
	        width:wjb51*0.19,
	        plotBorderWidth: 1,
	        plotBackgroundImage: null,
	        backgroundColor: 'rgba(255, 255, 255, 0)',
            plotBorderColor : null,
            plotBackgroundColor: null,
            plotBackgroundImage:null,
            plotBorderWidth: null,
            plotShadow: false
	    },
		credits: {
            	 text: '',
            	 href: ''
        },
	    title: {
	        text: '',
	        align: 'left',
	        style:{
	        	color:'#505050',
	        	fontSize:'12px'
	        }
	    },
		exporting:{ 
			 enabled:false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示
		},
	    pane: [{
	        startAngle:-45,
	        endAngle: 45,
	        background: null,
	        center: ['50%','160%'],
	        size: hjb51*320/1080
	    }],	    		        
	    yAxis: [{
	        min: 0,
	        max: 3000,
	        minorTickPosition: 'outside',
	        tickPosition: 'outside',
	        tickInterval: 1000,
	        labels: {
	        	rotation: 'auto',
	        	distance: 20
	        },
	        plotBands: [{
	        	from: 2500,
	        	to: 3000,
	        	color: '#C02316',
	        	innerRadius: '100%',
	        	outerRadius: '105%'
	        }],
	        pane: 0,
	        title: {
	        	text: '<br/><span >主轴转速(r/min)</span>',
	        	y: -40
	        }
	    }],
	    
	    plotOptions: {
	    	gauge: {
	    		dataLabels: {
	    			enabled: false
	    		},
	    		dial: {
	    			radius: '100%'
	    		}
	    	}
	    },
	    	
	
	    series: [{
	        data: sv,
	        yAxis: 0
	    }]
	     	
	   },
	     	
	   function(chart) {
		    setInterval(function() {
		        var left = chart.series[0].points[0],
		            leftVal, 
		            inc = (Math.random() - 0.5) * 3;
		    	var svalue = document.getElementById("myform:sValue").value;
		        leftVal = Math.abs(parseFloat(svalue));
		        if(leftVal>=3000)
		        	leftVal=3000;
		        left.update(leftVal, false);
		        chart.redraw();
		
		    }, 500);
		
		}
	     	
	);	

};

function generateChartThree(ov) {
	var chart = new Highcharts.Chart({
		 chart: {
			 	renderTo: 'tubiao',
		        type: 'gauge',
		        height:hjb51*240/1080,
		        plotBackgroundColor: null,
		        plotBackgroundImage: null,
		        plotBorderWidth: 0,
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
		        	fontSize:'12px'
		        }
		    },
			
			credits: {
	            	 text: '',
	            	 href: ''
	        },
			
			exporting:{ 
				 enabled:false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示 
			},
		    
		    pane: {
		        startAngle: -150,
		        endAngle: 150,
		        background: [{
		            backgroundColor: {
		                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
		                stops: [
		                    [0, '#FFF'],
		                    [1, '#333']
		                ]
		            },
		            borderWidth: 0,
		            outerRadius: '109%'
		        }, {
		            backgroundColor: {
		                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
		                stops: [
		                    [0, '#333'],
		                    [1, '#FFF']
		                ]
		            },
		            borderWidth: 1,
		            outerRadius: '107%'
		        }, {
		            // default background
		        }, {
		            backgroundColor: '#DDD',
		            borderWidth: 0,
		            outerRadius: '100%',
		            innerRadius: '100%'
		        }]
		    },
		       
		    // the value axis
		    yAxis: {
		        min: 0,
		        max: 100,
		        
		        minorTickInterval: 'auto',
		        minorTickWidth: 1,
		        minorTickLength: 10,
		        minorTickPosition: 'inside',
		        minorTickColor: '#666',
		
		        tickPixelInterval: 30,
		        tickWidth: 2,
		        tickPosition: 'inside',
		        tickLength: 10,
		        tickColor: '#666',
		        labels: {
		            step: 2,
		            rotation: 'auto'
		        },
		        title: {
		            
		        },
		        plotBands: [{
		            from: 0,
		            to: 60,
		            color: '#55BF3B' // green
		        }, {
		            from: 60,
		            to: 80,
		            color: '#DDDF0D' // yellow
		        }, {
		            from: 80,
		            to: 100,
		            color: '#DF5353' // red
		        }]        
		    },
		
		    series: [{
		        name: 'Speed',
		        data: ov,
		        tooltip: {
		            valueSuffix: '%'
		        }
		    }]
	     	
	   },
	     	
	   function (chart) {
			if (!chart.renderer.forExport) {
			    setInterval(function () {
			        var point = chart.series[0].points[0],
			            newVal,
			            inc = Math.round((Math.random() - 0.5) * 20);
			        var oee=document.getElementById("myform:oeeValue");
			        var doee=document.getElementById("myform:DOEE");
			        var oeeValue=oee.value;
			        if(oeeValue>=100)
			        	oeeValue=100;
			        doee.value=oeeValue;
			        doee.style.color="#00A600";
			        newVal = Math.abs(parseFloat(oeeValue));
			        point.update(newVal);
			        
			    }, 1000);
			}
		}
	     	
	);	

};

/**
 * 创建饼图
 * @param barModel
 */
function getPieChart(barModel){
    chart = new Highcharts.Chart({
        chart: {
        	renderTo: 'pieTureal',
            type: 'pie',
            height:hjb51*240/1080,
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
    	    valueSuffix: '%',
    	    formatter: function() {
                return ''+ this.point.name +': '+ Highcharts.numberFormat(this.percentage, 1) +'% ('+
                             Highcharts.numberFormat(this.y, 0, ',') +')';
             },
	        style: {  //提示框内容的样式
	             color: 'black',
	             fontSize: '12px',   
	             borderColor: '#000000'
	        }
	       
    	  
        },
        series: [{
            name: '',
            data:[{name:barModel.qx.name,y:parseFloat(barModel.qx.y),color:'#009E39'},
                  {name:barModel.fz.name,y:parseFloat(barModel.fz.y),color:'#BDBEBD'},
                  {name:barModel.zb.name,y:parseFloat(barModel.zb.y),color:'#08AEFF'},
                  {name:barModel.kx.name,y:parseFloat(barModel.kx.y),color:'#FFDB00'},
                  {name:barModel.tz.name,y:parseFloat(barModel.tz.y),color:'#5A595A'}],
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
    	    	 return '<b>零件:</b>'+ nn[1] +'<br/><b>工序:</b>'+nn[0]+'<br/><b>数量:</b>'+Highcharts.numberFormat(this.y, 2, ',')+'<br/>'  
                 +'<b>百分比:</b>'+Highcharts.numberFormat(this.percentage, 2)+'%';
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

        }
        ]
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
    	    	 return '<b>零件:</b>'+ nn[1] +'<br/><b>工序:</b>'+nn[0]+'<br/><b>数量:</b>'+Highcharts.numberFormat(this.y, 2, ',')+'<br/>'  
                 +'<b>百分比:</b>'+Highcharts.numberFormat(this.percentage, 2)+'%';
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
        		 }
        		]
    });
}
function zwxx(){
	 
	var wcjh=document.getElementById("wcjh").innerHTML;
	wcjh=wcjh.replace(/\s+/g,""); 
	if(wcjh=="完成/计划:/"){
		document.getElementById("wcjh").innerHTML="完成/计划: 暂无信息";
	}
  var sysj=document.getElementById("sysj").innerHTML;
  	  sysj=sysj.replace(/\s+/g,""); 
	if(sysj=="剩余时间:min"){
		document.getElementById("sysj").innerHTML="剩余时间: 暂无信息";
	}
	  
  var gjid=document.getElementById("gjidtxt").innerHTML;
	gjid=gjid.replace(/\s+/g,""); 
   if(gjid==""||null==gjid){
		document.getElementById("gjidtxt").innerHTML="暂无信息";
   }
   var gjmc=document.getElementById("gjmc").innerHTML;
   gjmc=gjmc.replace(/\s+/g,""); 
   if(gjmc==""||null==gjmc){
		document.getElementById("gjmc").innerHTML="暂无信息";
   }
}
function AutoWindon(){
	//document.getElementById("factop").style.height=hjb51*27/100+"px";//63=9*3， -10是微调
	document.getElementById("factop").style.height=hjb51*994/1080+"px";//控制中间和上面的和的高度 
	document.getElementById("factop").style.width=wjb51+"px";
	
	
	document.getElementById("headtrfisttd").style.height=hjb51*265/1080+"px";


	
	document.getElementById("bgimg").style.height=hjb51*730/1080+"px";//背景图大小
	document.getElementById("headxlh").style.fontSize=hjb51*30/1080+"px";
	document.getElementById("headjgrw").style.fontSize=hjb51*60/1080+"px";
	document.getElementById("headxl").style.fontSize=hjb51*60/1080+"px";
	document.getElementById("headxcb").style.fontSize=hjb51*60/1080+"px"; //头部的缩小
	
	document.getElementById("firstrow").style.height=hjb51*275/1080+"px";
	document.getElementById("firstrowfs").style.fontSize=hjb51*60/1080+"px";//第一行高度字体调整
	
	
	document.getElementById("tworow").style.height=hjb51*247/1080+"px";
	document.getElementById("tworowfs").style.fontSize=hjb51*24/1080+"px";
	document.getElementById("threerowfs").style.fontSize=hjb51*24/1080+"px";
	document.getElementById("lp").style.height=hjb51*247/1080+"px";
	
	document.getElementById("threerow").style.height=hjb51*180/1080+"px";
	document.getElementById("fontsizeone").style.fontSize=hjb51*20/1080+"px";
	document.getElementById("fontsizetwo").style.fontSize=hjb51*19/1080+"px";
	
	document.getElementById("fontsizethree").style.fontSize=hjb51*35/1080+"px";
	document.getElementById("fontsizefour").style.fontSize=hjb51*16/1080+"px";
	for(var i=1;i<=7;i++){
		document.getElementById("title"+i).style.fontSize=hjb51*18/1080+"px";
		document.getElementById("title"+i).style.marginLeft=wjb51*20/1920+"px"; //标题字体大小  和左边距调整
		if(i<=5){
			document.getElementById("yanse"+i).style.height=hjb51*20/1080+"px";
			document.getElementById("yanse"+i).style.width=wjb51*40/1920+"px";//颜色块大小调整
			
		}
	}
	document.getElementById("title8").style.marginLeft=wjb51*270/1920+"px";
	document.getElementById("title8").style.fontSize=hjb51*50/1080+"px";
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
	
		
		if(label.length==2){
			var fs=hjb51*40/1080;
			document.getElementById("xyzabc").innerHTML=""+
					"<div style='font-size:"+fs+"px;font-weight:bolder;float:left;'>"+label[0]+"</div>"+
                    "<div style='font-size:"+fs+"px;font-weight:bolder;'>"+value[0]+"</div></br>"+
                    "<div style='font-size:"+fs+"px;font-weight:bolder;float:left;'>"+label[1]+"</div>"+
                    "<div style='font-size:"+fs+"px;font-weight:bolder;'>"+value[1]+"</div>";        
		}else 
		if(label.length==3){
			var fs=hjb51*40/1080;
		   	document.getElementById("xyzabc").innerHTML=""+
					"<div style='font-size:"+fs+"px;font-weight:bolder;float:left;'>"+label[0]+"</div>"+
			        "<div style='font-size:"+fs+"px;font-weight:bolder;'>"+value[0]+"</div><br/>"+
			        "<div style='font-size:"+fs+"px;font-weight:bolder;float:left;'>"+label[1]+"</div>"+
			        "<div style='font-size:"+fs+"px;font-weight:bolder;'>"+value[1]+"</div><br/>"+
			        "<div style='font-size:"+fs+"px;font-weight:bolder;float:left;'>"+label[2]+"</div>"+
			        "<div style='font-size:"+fs+"px;font-weight:bolder;'>"+value[2]+"</div>"; 
		}else 
		if(label.length==5){
			var fs=hjb51*40/1080;
			document.getElementById("xyzabc").innerHTML=""+
						"<div style='font-size:"+fs+"px;font-weight:bolder;float:left;'>"+label[0]+"</div>"+
						"<div style='font-size:"+fs+"px;font-weight:bolder;'>"+value[0]+"</div>"+
						"<div style='font-size:"+fs+"px;font-weight:bolder;float:left;'>"+label[1]+"</div>"+
						"<div style='font-size:"+fs+"px;font-weight:bolder;'>"+value[1]+"</div>"+  
						"<div style='font-size:"+fs+"px;font-weight:bolder;float:left;'>"+label[2]+"</div>"+
						"<div style='font-size:"+fs+"px;font-weight:bolder;'>"+value[2]+"</div>"+
						"<div style='font-size:"+fs+"px;font-weight:bolder;float:left;'>"+label[3]+"</div>"+
						"<div style='font-size:"+fs+"px;font-weight:bolder;'>"+value[3]+"</div>"+
						"<div style='font-size:"+fs+"px;font-weight:bolder;float:left;'>"+label[4]+"</div>"+
						"<div style='font-size:"+fs+"px;font-weight:bolder;'>"+value[4]+"</div>";            
		}else{
			document.getElementById("xyzabc").innerHTML="暂无信息";
		}
		ycwzgd();
		if(null==fu||""==fu){
			document.getElementById("wcth").innerHTML="暂无信息";
		}else{
			document.getElementById("wcth").innerHTML=fu;
		}
		if(null==est||""==est){
			document.getElementById("sysjTh").innerHTML="暂无信息";
		}else{
			document.getElementById("sysjTh").innerHTML=est;
		}
		
		document.getElementById("myform:wcTs").innerHTML=fu;
		
		if(null==pn||""==pn){
			document.getElementById("jhth").innerHTML="暂无信息";
		}else{
			document.getElementById("jhth").innerHTML=pn;
		}
		
		if(null==no||""==no){
			document.getElementById("gjidtxt").innerHTML="暂无信息";
		}else{
			document.getElementById("gjidtxt").innerHTML=no;
		}
		if(null==name||""==name){
			document.getElementById("gjmc").innerHTML="暂无信息";
		}else{
			document.getElementById("gjmc").innerHTML=name;
		}
		if(null==processName||""==processName){
			document.getElementById("gxmc").innerHTML="暂无信息";
		}else{
			document.getElementById("gxmc").innerHTML=processName;
		}
		
		partUpdateBg();
		
		if(null!=f&&""!=f){
			document.getElementById("fv").innerHTML="<div >"+f+"</div>";
		}else{
			document.getElementById("fv").innerHTML="<div style='font-size:16px;'>暂无信息</div>";
		}
		if(null!=s&&""!=s){
			document.getElementById("sv").innerHTML="<div >"+s+"</div>";
		}else{
			document.getElementById("sv").innerHTML="<div style='font-size:16px;'>暂无信息</div>";
		}
		
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
	if(null==wzgd||"工单:..."==wzgd||"工单:暂无信息"==wzgd){
		
	}else{
		document.getElementById("gongdan").innerHTML+="<div style='position: absolute;color:#bfbfbf;font-size:15px;"
	    	+"background-image: url(./images/gdnamebg.png');'><div style='width:100%;height:2px;'></div> <div style='widht:100%'>"+wzgd+"</div>"
	    	+"<div style='widht:100%;height:5px;'></div>";
	}
}
function ycwzgd(){
	 var wzgd=document.getElementById("myform:jobDispatchListNoValue").value;
	 var gdxsss="工单:"+(wzgd.substring(0,16).replace(/^\s+|\s+$/g,"")+"...");
	 if(null==wzgd||""==wzgd){
		 gdxsss="工单:暂无信息";
	 }
	document.getElementById("gongdan").innerHTML=gdxsss;
}
function partUpdateBg(){
	var partbg=document.getElementById("myform:partbg").value;
	if(null!=partbg&&""!=partbg){ 
		document.getElementById("lp").src="./images/part/"+partbg;
	}
	
}
