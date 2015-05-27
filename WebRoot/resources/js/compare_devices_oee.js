var imgPercent = hjb51/1080;
/**
 * 加载图表数据
 */
function loadData() {
	var jsonData = document.getElementById("myform:devicesOeeChart").value;
	if( jsonData.length!=0){
		var barModel = eval('(' + jsonData + ')');
		generateLineChart(barModel);
	}else{
		document.getElementById("chart").innerHTML += "<div style=\"font-size:"+hjb51*30/1080+"px;text-align:center;" +
		"color:rgb(150,150,150);padding:"+hjb51*150/1080+"px;\" align=\"center\">没有数据，<BR/>请更换节点或查询条件<br/></div>";
	}
}

/**
 * 创建点线图
 * @param barModel
 */
function generateLineChart(barModel) {

	var devices = [ {'name' : '','data' : []} ];
	for ( var j = 0; j < parseInt(barModel.devicesSize); j++) {
		devices[j] = {'name' : '','data' : []};
		devices[j].name = barModel.serNames[j];
		devices[j].data = barModel.otValue[j];
	}
	
	var chart = new Highcharts.Chart({
		chart: {
			renderTo : 'chart',
			zoomType : "x",
			width : wjb51 - 20,
			height : hjb51*500/1080,
			backgroundColor:{linearGradient: [800, 0, 800, 500],
	        stops: [[0, 'rgba(225,225,225,1)'] , [1, 'rgba(255,255,255,0)']]},
	        borderRadius: 0
        },
        exporting : {
			enabled : false
		},
		credits: {  
			  enabled: false  
		}, 
        title: {
            text: barModel.title
        },
        subtitle: {
            text: null
        },
        xAxis: {
            labels : {
				rotation : -35,
				align : 'right'
			},
			categories :barModel.colData, 	
			title : {
				text : null,
			}
        },
        yAxis: {
            title: {
                text: 'Exchange rate'
            },
            min : 0.0,
			max : 4.0,
        },
        tooltip: {
            formatter : function() {
				return '' + this.x + ': ' + this.y;
			}
        },
        legend: {
            enabled : true,
			align:'center',	
            backgroundColor : '#FFFFFF',
			borderWidth : 0,
        },
        plotOptions: {
           bar : {
				dataLabels : {
					enabled : true
				}
			}
        },
        series:devices
    
	});
}




