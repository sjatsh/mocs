//左侧图表 
function loadData_machine() {
	var columJsonData = document.getElementById("myform:beanvalue").value;
	if(columJsonData == "") {
		columJsonData = "{}";
		$("#productcostoneChart .highcharts-container").empty();
	  } 
    var columJsonData = eval('(' + columJsonData + ')'); 
	
	generateChart(columJsonData);
}

//右侧图表
function loadData() {
	var pieJsonData = document.getElementById("myform:beanvalue1").value;
	var columnAndLineJsonData = document.getElementById("myform:beanvalue2").value;
	$("#emptyDiv").remove();
	$(".zl-content-info-right-top").show();
	if(pieJsonData == "") {
		pieJsonData = "{}";
		$(".zl-content-info-right-top").hide();
	} 
	var pieJsonData = eval('(' + pieJsonData + ')'); 
	if(columnAndLineJsonData == "") {
		columnAndLineJsonData= "{}";
		$("#machineCostchartbar .highcharts-container").empty();
	}
	var columnAndLineJsonData = eval('(' + columnAndLineJsonData + ')'); 
	pieChart(pieJsonData);
	combinationChart(columnAndLineJsonData);
	
	//全空处理
	if(typeof(pieJsonData.columnKeys) == "undefined" && typeof(columnAndLineJsonData.columnKeys) == "undefined") {
		var emptyInfo = "";
		dataTranslate("workshop_management", function(t) { emptyInfo = t("zwxxinfo");});
		$(".zl-content-info-right").append("<div id='emptyDiv' style='position: absolute;left: 0;top: 39%;text-align: center;width: 100%;height: 100%;font-size: 565%;line-height: 100%;color: #7A7A7A;'>" + emptyInfo + "</div>")
	}
}

/*var gencharheight = hjb51*280/1080;
var genpointWidth = wjb51*55/1920;
var genitemWidth = wjb51*140/1920;
var genwidth = wjb51*700/1920;
var gensymbolWidth = wjb51*32/1920;*/
function generateChart(barModel) {
	//排错
	if(typeof(barModel.rowkeys) == 'undefined') {return;}
	
	var genwidth = $('#costcenterlefttd3').width();	//960
	var gencharheight = $('#costcenterlefttd3').height();	//220
	var genLegendWidth = genwidth * .8;	//768
	var genpointWidth = genwidth * .08;	//76.8
	var genitemWidth = genwidth * .16 - 10;	//143.6
	var gensymbolWidth = genwidth * .04;	//38.4
	var genItemHeight = gencharheight * .1;	//22

	
	chart = new Highcharts.Chart({
		chart: {
			type: 'column',
			renderTo : 'productcostoneChart',
			height: gencharheight,
			backgroundColor: 'rgba(255, 255, 255, 0)',
			plotBorderColor : null,
			plotBackgroundColor: null,
			plotBackgroundImage:null,
			plotBorderWidth: null,
			plotShadow: false
		},
		
        title: {
            text: ''
        },
        xAxis: {
        	categories: barModel.rowkeys
            
        },
        yAxis: {
            min: 0,
            title: {
                text: ''
            },
            gridLineWidth: 0,
            labels: {
            	enabled: true,
            }
        },
        
        credits: {
            	 text: '',
            	 href: ''
        },
		exporting:{ 
			 enabled:false //用来设置是否显示‘打印?'导出'等功能按钮，不设置时默认为显礿
		},
		tooltip: {
            formatter: function() {
                return '<b>'+ this.x +'</b><br/>'+
                    this.series.name +': '+ this.y +'<br/>'+
                    'Total: '+ this.point.stackTotal;
            }
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                dataLabels: {
                    enabled: false,
                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
                }
            },
            series: {
	            pointWidth: genpointWidth,
	        }
        },
        legend: {
        	backgroundColor: 'rgba(255,255,255,0)',
        	align: "center",
			width:genLegendWidth,
			itemWidth: genitemWidth-10,
			itemHeight: genItemHeight,
			symbolWidth: gensymbolWidth,
			borderWidth: 0,

        },
        colors:[
                'rgba(0,129,206,0.6)',
                'rgba(108,204,71,0.4)',
                'rgba(244,221,12,1)',
                'rgba(160,160,160,1)',
                'rgba(251,117,0,1)'
        ],
        series: [{
            name: '<SPAN style="color:#7a7a7a">'+barModel.columnkeys[0]+'</SPAN>',
            data: barModel.data0
        }, {
            name: '<SPAN style="color:#7a7a7a">'+barModel.columnkeys[1]+'</SPAN>',
            data: barModel.data1
        },{
            name: '<SPAN style="color:#7a7a7a">'+barModel.columnkeys[2]+'</SPAN>',
            data: barModel.data2
        }, {
            name: '<SPAN style="color:#7a7a7a">'+barModel.columnkeys[3]+'</SPAN>',
            data: barModel.data3
        },{
            name: '<SPAN style="color:#7a7a7a">'+barModel.columnkeys[4]+'</SPAN>',
            data: barModel.data4
        }]
    });
}
    
/*var piemarginRight = wjb51*130/1920; 
var piemarginBottom1 = hjb51*25/1080; 
var piemarginBottom = piemarginBottom1.toString();
var piewidth = wjb51*880/1920; 
var pieheight = hjb51*440/1080;
var piefontsize = hjb51*20/1080; */


function pieChart(barModel) {
	//排错
	if(typeof(barModel.rowKeys) == 'undefined') {return;}

	var piewidth = $('.zl-content-info-right-top').width();	//960
	var pieheight = $('.zl-content-info-right-top').height();	//440
	var pieLegendTop = pieheight * .3;	//132
	var pieLegendRight = piewidth * .18;	//172.8
	var pieLegendItemMargin = pieheight * .04;	//17.6
	var pieLegendsymbolWidth = piewidth * .03;	//959.97
	var pieLegendItemFontSize = pieheight * .03;	//13.2
	var pieLegendItemHeight = pieheight * .05	//22

    chart = new Highcharts.Chart({
	    chart: {
	    	renderTo : 'productcosttwoChart',
		    backgroundColor: 'rgba(255, 255, 255, 0)',
            plotBorderColor : null,
            plotBackgroundColor: null,
            plotBackgroundImage: null,
            plotBorderWidth: null,
            plotShadow: false,
            width:piewidth,
            height:pieheight,
            events: {
            	load: function() {	//加载完成后执行事件
            	    //合计文字
            	    $(".zl-content-info-right-top-total").removeClass("hidden");
            	    //背景图
            	    $(".zl-content-info-right-top-bg").css("top", function() {
            	    	//因为图片width == height,而height是auto，所以图片加载完之前取不到，所以使用width的值
            	    	return (($(".zl-content-info-right-top").height() - $(this).width()) / 2);	
            	    }).removeClass("hidden");
            	}
            }
        },
        title: {
            text: ''
	    },
	    tooltip: {
	    	enabled: true,
		    pointFormat: '<b>{point.name}</b> ￥{point.y}  ',
	    	percentageDecimals: 1
	    },
	    plotOptions: {
	        pie: {
	            allowPointSelect: false,
	            cursor: 'pointer',
	            showInLegend: true,
                dataLabels: {
                    enabled: false
                },
                size: '75%',
	    	    innerSize: '40%'
	        }
	    },

        legend: {
        	floating: true,
        	layout: "vertical",
        	backgroundColor: 'rgba(255,255,255,0)',
        	align: "right",
        	verticalAlign: "top",
        	x: -pieLegendRight,
        	y: pieLegendTop,
        	itemMarginBottom: pieLegendItemMargin,
			borderWidth: 0,
			symbolWidth: pieLegendsymbolWidth,
			itemHeight: pieLegendItemHeight,
			itemStyle: {
				"font-size": pieLegendItemFontSize + "px",
				"color": "#7a7a7a"
			}

        },
		colors:[                   
			'rgba(0,129,206,0.6)',
			'rgba(108,204,71,0.4)',
			'rgba(244,221,12,1)',
			'rgba(160,160,160,1)',
			'rgba(251,117,0,1)'
		],
		credits: {
				 text: '',
				 href: ''
		},
		exporting:{ 
			enabled:false //用来设置是否显示‘打印?'导出'等功能按钮，不设置时默认为显礿
		}, 
				
	    series: [{
	        type: 'pie',
	        data: [
               [barModel.rowKeys[0], parseFloat(barModel.columnKeys[0])],
               [barModel.rowKeys[1], parseFloat(barModel.columnKeys[1])],
               {
                   name: barModel.rowKeys[2],
                   y: parseFloat(barModel.columnKeys[2]),
                   sliced: false,
                   selected: false
               },
               [barModel.rowKeys[3], parseFloat(barModel.columnKeys[3])],
               [barModel.rowKeys[4], parseFloat(barModel.columnKeys[4])]             
           ]
	    }]
	});
}

/*var comheight = hjb51*182/1080;
var comwidth = wjb51*883/1920; 
var comitemWidth = wjb51*100/1920;
var comsymbolWidth = wjb51*32/1920;
var compointWidth = wjb51*55/1920;
var onewidth = wjb51*140/1920;*/


function combinationChart(barModel) {
	//排错
	if(typeof(barModel.rowkeys) == 'undefined') {return;}

	var comwidth = $('.zl-content-info-right-bottom').width();	//960
	var comheight = $('.zl-content-info-right-bottom').height();	//270
	var comItemWidth = comwidth * .16 - 10;	//143.6
	var comItemHeight = comheight * .08;	//22
	var comsymbolWidth = comwidth * .04;	//38.4
	var compointWidth = comwidth * .06	//57.6
	chart = new Highcharts.Chart({
		
	chart: {
	    type: 'column',
	    renderTo : 'productcostthreeChart',
	    height:comheight, 
	    width:comwidth
    },
	
    title: {
        text: ''
    },
    xAxis: {
    	categories: barModel.rowkeys
        
    },
    yAxis: {
        min: 0,
        title: {
            text: ''
        },
        gridLineColor: "rgba(255,255,255,0)",  
        labels: {					
        	enabled: true,  //是否显示
		},
           
    },

    credits: {
		text: '',
		href: ''
    },
	exporting:{ 
		enabled:false //用来设置是否显示‘打印?'导出'等功能按钮，不设置时默认为显礿
	},
	legend: {
		layout: 'horizontal',
		align: 'center',
		verticalAlign: 'bottom',
		x: 0,
		y: 0,
		floating: false,
		backgroundColor: 'rgba(255,255,255,0)',
		borderWidth: 0,
		symbolWidth: comsymbolWidth,
		itemWidth: comItemWidth,
		itemHeight: comItemHeight
	},
	tooltip: {
        formatter: function() {
            return '<b>'+ this.x +'</b><br/>'+
                this.series.name +': '+ this.y +'<br/>'+
                'Total: '+ this.point.stackTotal;
        }
    },
    plotOptions: {
        column: {
            stacking: 'normal',
            dataLabels: {
                enabled: false,
            }
        },
        series: {
            pointWidth: compointWidth,
        }
    },
    colors:[          
       'rgba(108,204,71,0.8)',
       'rgba(244,221,12,0.8)',
       'rgba(0,129,206,0.8)',
    ],
    series: [{
	        name: '<SPAN style="color:#7a7a7a">'+barModel.columnkeys[0]+'</SPAN>',
	        data: barModel.data0
	    }, {
	        name: '<SPAN style="color:#7a7a7a">'+barModel.columnkeys[1]+'</SPAN>',
	        data: barModel.data1
	    },{
	        name: '<SPAN style="color:#7a7a7a">'+barModel.columnkeys[2]+'</SPAN>',
	        data: barModel.data2
	    }]
	});
}
