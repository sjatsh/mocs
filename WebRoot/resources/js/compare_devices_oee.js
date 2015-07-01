/**
 * 加载图表数据
 */
function loadData() {
	var jsonData = document.getElementById("myform:devicesOeeChart").value;
	if( jsonData.length!=0){
		var barModel = eval('(' + jsonData + ')');
		generateLineChart(barModel);
	}else{
		document.getElementById("chart").innerHTML += "<div style=\"font-size:0.3rem;text-align:center;" +
		"color:rgb(150,150,150);padding:1.5rem;\" align=\"center\"><span data-i18n='zwxxts'></span></div>";
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
            width: $("#chart").width(),
            height: $("#chart").height(),
			plotBorderWidth: 1,
	        plotBackgroundImage: null,
	        backgroundColor: 'rgba(255, 255, 255, 0)',
            plotBorderColor : null,
            plotBackgroundColor: null,
            plotBackgroundImage:null,
            plotBorderWidth: null,
            plotShadow: false
        },
        exporting : {
			enabled : false
		},
		credits: {  
			  enabled: false  
		}, 
        title: {
            text: barModel.title,
            style:{  		
        		color:'#7A7A7A'
        	 }
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
        colors:[          
			'rgba(108,204,71,1)',
			'rgba(0,129,206,1)',
			'rgba(244,221,12,1)',
			'rgba(160,160,160,1)',
			'rgba(251,117,0,1)'
        ],
        series:devices
    
	});
}




