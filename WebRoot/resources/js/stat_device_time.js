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
				"color:rgb(150,150,150);padding:"+hjb51*150/1080+"px;\" align=\"center\"><span data-i18n='zwxxts'></span></div>";
	}
}

/**
 * 
 * @param barModel
 */
var textStr = "content.tbtitle";
dataTranslate("stat_device_time", function(t) { textStr = t(textStr);});
function generateChart(barModel) {
    var chart;
    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'highchartChart',
            width: $("#highchartChart").width(),
            height:$("#highchartChart").height(),
            type: 'bar',
            backgroundColor: 'rgba(255, 255, 255, 0)',
            plotBorderColor : null,
            plotBackgroundColor: null,
            plotBackgroundImage:null,
            plotBorderWidth: null,
            plotShadow: false 
        },
         title: {
            text: textStr,
            style:{  		
        		color:'#7A7A7A'
        	 }
        },
        xAxis: {
            categories: null,
            title: {
                text: null
            },
            labels: {
            	enabled:false
            },
            /*lineColor: "#ffffff",
            labels: {
                style: {
                    color: '#ffffff'
                }
            }*/
        },
        yAxis: {
            title: {
                text: null
            },
            labels: {
            	enabled:false
            },
            /*gridLineColor: "#ffffff"
            	 , labels: {
                     style: {
                         color: '#ffffff'
                     }
            }*/
        },
        legend: {
            backgroundColor: '#FFFFFF',
            reversed: true,
            width:500,
            height:40,
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
            color:'rgba(160,160,160,0.6)'
        }, {
            name: barModel.rowKeys[9],
            data: barModel.data8,
            stack: 'female',
            color:'rgba(244,221,12,0.6)'
        }, {
            name: barModel.rowKeys[8],
            data: barModel.data10,
            stack: 'female',
            color:'rgba(251,117,0,0.6)'
        }, {
            name: barModel.rowKeys[7],
            data: barModel.data6,
            stack: 'female',
            color:'rgba(160,160,160,0.4)'
        }, {
            name: barModel.rowKeys[6],
            data: barModel.data7,
            stack: 'female',
            color:'rgba(108,204,71,1)'
        }, {
            name: barModel.rowKeys[5],
            data: barModel.data5,
            stack: 'male',
            color:'rgba(251,117,0,1)'
        }, {
            name: barModel.rowKeys[4],
            data: barModel.data4,
            stack: 'male',
            color:'rgba(244,221,12,1)'
        }, {
            name: barModel.rowKeys[3],
            data: barModel.data2,
            stack: 'male',
            color:'rgba(0,129,206,0.4)'
        }, {
            name: barModel.rowKeys[2],
            data: barModel.data3,
            stack: 'male',
            color:'rgba(108,204,71,0.4)'
        }, { 
            name: barModel.rowKeys[1],
            data: barModel.data1,
            color:'rgba(160,160,160,1)'
        }, {
            name: barModel.rowKeys[0],
            data: barModel.data0,
            color:'rgba(0,129,206,1)'
        }]
    });
};

