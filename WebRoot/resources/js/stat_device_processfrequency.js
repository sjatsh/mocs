/**
 * 加载图表所需数据
 */
function loadData() {
	var jsonData = document.getElementById("myform:beanvalue").value;
	var barModel = eval('(' + jsonData + ')');
	//barModel.size=0;
	if(barModel.size == 0){
		document.getElementById("lineChart").innerHTML += "<div style=\"font-size:0.3rem;text-align:center;" +
		"color:rgb(150,150,150);padding:1.5rem;\" align=\"center\"><span data-i18n='zwxxts'></span></div>";
		document.getElementById("barChart").innerHTML += "<div style=\"font-size:0.3rem;text-align:center;" +
		"color:rgb(150,150,150);padding:1.5rem;\" align=\"center\"><span data-i18n='zwxxts'></span></div>";
	}else{
		generateLineChart(barModel);
		var jsonData1 = document.getElementById("myform:beanvalue1").value;
		var barModel1 = eval('(' + jsonData1 + ')');
		generateBarChart(barModel1);
	}
}

/**
 * 创建线图表
 * @param barModel
 */
function generateLineChart(barModel) {
	chart = new Highcharts.Chart({
		chart : {
			renderTo : 'lineChart',
			type : 'line',
            width: $("#lineChart").width(),
            height:$("#lineChart").height(),
	         backgroundColor: 'rgba(255, 255, 255, 0)',
	         plotBorderColor : null,
	         plotBackgroundColor: null,
	         plotBackgroundImage:null,
	         plotBorderWidth: null,
	         plotShadow: false 
		},
		exporting: {
            enabled: false
        },
		title : {
			text : barModel.title,
			style:{
				fontWeight:700,
			}
		},
		subtitle : {
			text : null
		},
		colors:['rgba(0,129,206,0.8)'],
		xAxis : {
			categories : barModel.cuttingeventId,
			title : {
				text : barModel.xName,
			}
		},
		yAxis : {
			min : 0,
			title : {
				text : barModel.yName,
			},
			labels : {
				overflow : 'justify'
			}
		},
		tooltip : {
			formatter : function() {
				return '' + this.x + ': ' + this.y;
			}
		},
		plotOptions : {
			bar : {
				dataLabels : {
					enabled : true
				}
			}
		},
		legend : {
			enabled : false
		},
		credits : {
			enabled : false
		},
		series : [ {
			name : barModel.workTimeName,
			data : barModel.workTime
		} ]
	});
}

/**
 * 创建柱状图表
 * @param barModel
 */
function generateBarChart(barModel) {
	chart = new Highcharts.Chart({
		chart : {
			renderTo : 'barChart',
			type : 'bar',
			width: $("#barChart").width(),
			height:$("#barChart").height(),
			backgroundColor: 'rgba(255, 255, 255, 0)',
			plotBorderColor : null,
			plotBackgroundColor: null,
			plotBackgroundImage:null,
			plotBorderWidth: null,
			plotShadow: false 
		},
		title : {
			text : barModel.title,
			style:{
				fontWeight:700,
				fontSize:14
			}
		},
		subtitle : {
			text : null
		},
		colors:['rgba(0,129,206,0.8)'],
		exporting: {
            enabled: false
        },
		xAxis : {
			categories : barModel.areas,
			title : {
				text : barModel.xName,
				
			}
		},
		yAxis : {
			min : 0,
			title : {
				text : barModel.yName,
			},
			labels : {
				overflow : 'justify'
			}
		},
		tooltip : {
			formatter : function() {
				return '' + this.x + ': ' + this.y;
			}
		},
		plotOptions : {
			bar : {
				dataLabels : {
					enabled : true
				}
			}
		},
		legend : {
			enabled : false
		},
		credits : {
			enabled : false
		},
		series : [ {
			name : barModel.name,
			data : barModel.a
		} ]
	});
}
