var imgPercent = hjb51/1080; 

function loadData() {
var jsonData = document.getElementById("myform:beanvalue").value;
//alert(jsonData);
if(null !=jsonData && ""!=jsonData ){
	var barModel = eval('(' + jsonData + ')');
	generateChart(barModel);
}else{
	document.getElementById("highchartChart").innerHTML += "<div style=\"font-size:"+hjb51*30/1080+"px;text-align:center;" +
	"color:rgb(150,150,150);padding:"+hjb51*150/1080+"px;\" align=\"center\">没有数据，<BR/>请更换节点或查询条件<br/></div>";
}
}


function generateChart(barModel) {
    var chart;
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'highchartChart',
                type: 'bar',
                width:wjb51-20,
                height:hjb51*500/1080,
                backgroundColor: 'rgba(255, 255, 255, 0)',
                plotBorderColor : null,
                plotBackgroundColor: null,
                plotBackgroundImage:null,
                plotBorderWidth: null,
                plotShadow: false 
            },
            title: {
                text: barModel.title,
                margin:50,
                style:{
    				fontWeight:700,
    			}
            },
            xAxis: {
                categories: barModel.columnKeys
            },
            yAxis: {
            	min: 0,
                max:100,
                title: {
                    text: null
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
                    return ''+
                        this.series.name +': '+ this.y +'';
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
                }
            },
                series: [{
                name: barModel.rowKeys[4],
                data: barModel.data4,
                color:{
                	linearGradient: [0, 0,800, 400],
                	stops: [[0, 'rgba(200,0,0,1)'],[1, 'rgba(220,40,40,0.8)']]
                	}
            },{
                name: barModel.rowKeys[3],
                data: barModel.data0,
                color:{
                	linearGradient: [0, 0,800, 400],
                	stops: [[0, 'rgba(169,169,169,1)'],[1, 'rgba(20,20,20,0.9)']]
                	}
            },{
                name: barModel.rowKeys[2],
                data: barModel.data3,
                color:{
                	linearGradient: [0, 0,800, 400],
                	stops: [[0, 'rgba(255,215,0,1)'],[1, 'rgba(240,230,40,0.8)']]
            }
            },{
                name: barModel.rowKeys[1],
                data: barModel.data2,
                color:{
                	linearGradient: [0, 0,800, 400],
                	stops: [[0, 'rgba(127,255,0,1)'],[1, 'rgba(52,170,0,0.8)']]
                	}
            },{
                name: barModel.rowKeys[0],
                data: barModel.data1,
                color:{
                	linearGradient: [0, 0,800, 400],
                	stops: [[0, 'rgba(148,0,211,1)'],[1, 'rgba(170,0,229,0.8)']]
                	}
            }]
        });
};