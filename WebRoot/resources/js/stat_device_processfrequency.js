var imgPercent = hjb51/1080; //比例 
/**
 * 加载图表所需数据
 */
function loadData() {
	var jsonData = document.getElementById("myform:beanvalue").value;
	var barModel = eval('(' + jsonData + ')');
	
	if(barModel.size == 0){
		document.getElementById("lineChart").innerHTML += "<div style=\"font-size:"+hjb51*30/1080+"px;text-align:center;" +
		"color:rgb(150,150,150);padding:"+hjb51*150/1080+"px;\" align=\"center\">没有数据，<BR/>请更换节点或查询条件<br/></div>";
		document.getElementById("barChart").innerHTML += "<div style=\"font-size:"+hjb51*30/1080+"px;text-align:center;" +
		"color:rgb(150,150,150);padding:"+hjb51*150/1080+"px;\" align=\"center\">没有数据，<BR/>请更换节点或查询条件<br/></div>";
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
			 width:hjb51*960/1080,
	         height:hjb51*475/1080,
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
		colors:['#458B00'],
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
			 width:hjb51*960/1080,
	         height:hjb51*475/1080,
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
		colors:['#458B00'],
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
