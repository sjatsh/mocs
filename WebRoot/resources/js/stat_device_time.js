var imgPercent = hjb51/1080; //比例 
/**
 * 
 */
function loadData() {
	var jsonData = document.getElementById("myform:beanvalue").value;
	if(null != jsonData && ""!=jsonData ){
		var barModel = eval('(' + jsonData + ')');
		generateChart(barModel);

	}else{
		document.getElementById("highchartChart").innerHTML += "<div style=\"font-size:"+hjb51*30/1080+"px;text-align:center;" +
				"color:rgb(150,150,150);padding:"+hjb51*150/1080+"px;\" align=\"center\">没有数据，<BR/>请更换节点或查询条件<br/></div>";
	}
}

/**
 * 
 * @param barModel
 */
function generateChart(barModel) {
    var chart;
    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'highchartChart',
            width:wjb51,
            height:hjb51*475/1080,
            type: 'bar',
            backgroundColor: 'rgba(255, 255, 255, 0)',
            plotBorderColor : null,
            plotBackgroundColor: null,
            plotBackgroundImage:null,
            plotBorderWidth: null,
            plotShadow: false 
        },
         title: {
            text: null
        },
        xAxis: {
            categories: null,
            title: {
                text: null
            },
            lineColor: "#ffffff",
            labels: {
                style: {
                    color: '#ffffff'
                }
            }
        },
        yAxis: {
            title: {
                text: null
            },
            gridLineColor: "#ffffff"
            	 , labels: {
                     style: {
                         color: '#ffffff'
                     }
             }
        },
        legend: {
            backgroundColor: '#FFFFFF',
            reversed: true,
            width:500,
            borderWidth: 0,
        },
        tooltip: {
            formatter: function() {
                return this.series.name +': '+formatTime(this.y,barModel.day,barModel.hour,barModel.minute,barModel.second);
            }
        },
        credits : {
			enabled : false
		},
		exporting: {
            enabled: false
        },
        plotOptions: {
        	series: {
        		stacking: 'normal'
        	},
            bar: {
                pointWidth: 50,
                groupPadding: 0,
            }
        },
            series: [{
            name: barModel.rowKeys[10],
            data: barModel.data9,
            stack: 'female',
            color:'#6173F9'
        }, {
            name: barModel.rowKeys[9],
            data: barModel.data8,
            stack: 'female',
            color:'#DD3EFA'
        }, {
            name: barModel.rowKeys[8],
            data: barModel.data10,
            stack: 'female',
            color:'#90B977'
        }, {
            name: barModel.rowKeys[7],
            data: barModel.data6,
            stack: 'female',
            color:'#32632B'
        }, {
            name: barModel.rowKeys[6],
            data: barModel.data7,
            stack: 'female',
            color:'#5E984E'
        }, {
            name: barModel.rowKeys[5],
            data: barModel.data5,
            stack: 'male',
            color:'#FC3E3E'
        }, {
            name: barModel.rowKeys[4],
            data: barModel.data4,
            stack: 'male',
            color:'#F9CC3B'
        }, {
            name: barModel.rowKeys[3],
            data: barModel.data2,
            stack: 'male',
            color:'#9861FA'
        }, {
            name: barModel.rowKeys[2],
            data: barModel.data3,
            stack: 'male',
            color:'#96D261'
        }, {
            name: barModel.rowKeys[1],
            data: barModel.data1,
            color:'#AAAAAA'
        }, {
            name: barModel.rowKeys[0],
            data: barModel.data0,
            color:'#52D8FB'
        }]
    });
};

