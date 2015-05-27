/**
 * 加载图表数据
 */
function loadData() {
	var jsonData = document.getElementById("myform:devicesOeeChart").value;
	//alert(jsonData);
	//jsonData={"title":"多台机床OEE比较","colData":["2013-08-06","2013-08-07","2013-08-08","2013-08-09","2013-08-10","2013-08-12","2013-08-13","2013-08-14","2013-08-15","2013-08-16","2013-08-19","2013-08-20","2013-08-21","2013-08-22","2013-08-23","2013-08-25","2013-08-26","2013-08-27","2013-08-28","2013-08-29","2013-08-30","2013-09-02","2013-09-03","2013-09-04","2013-09-05","2013-09-06","2013-09-09","2013-09-10","2013-09-11","2013-09-12","2013-09-13","2013-09-16","2013-09-17","2013-09-18","2013-09-22","2013-09-23","2013-09-24","2013-09-25","2013-09-26","2013-09-27","2013-09-28","2013-09-29","2013-09-30","2013-10-09","2013-12-19"],"otValue":[[0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.507,3.802,3.295,0.556,0.347,0.069,0.417,0.278,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0],[0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.135,0.135,0.045,0.451,0.497,0.361,0.0,0.316,0.045,0.09,0.181,0.09,0.135,0.406,0.045,0.226,0.0,0.0,0.045,0.497,0.406,0.045,0.0,0.0,0.0,0.0,0.316,0.09,0.0,0.0],[0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.558,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.475,0.0,1.7,1.675,1.144,0.354,0.0,0.0,0.0,0.156,0.0,0.292,0.01,0.0,0.01,0.042,0.021,0.0,0.01,0.031,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]],"serNames":["VMC0656e-SHB21-05","VMC850p-SHB21-04","ETC3650-SHB21-02"],"devicesSize":3}
	if( jsonData.length!=0){
		var barModel = eval('(' + jsonData + ')');
		generateLineChart(barModel);
		//document.getElementById("chart").innerHTML += "<div class=\"nodatacsstwo\" >"+jsonData+"</div>";
	}else{
		document.getElementById("chart").innerHTML += "<div class=\"nodatacsstwo\" >没有数据，<BR/>请更换节点或查询条件</div>";
	}
	
}

/**
 * 创建多线图
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
			width :wjb51,
			height :620/1080*hjb51,
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
                text: ''
            },
            min : 0.0
			
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



