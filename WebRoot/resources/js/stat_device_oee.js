/**
 * 加载图表所需数据
 */
function loadData() {
var jsonData = document.getElementById("myform:beanvalue").value;
if(null !=jsonData && ""!=jsonData ){
	var barModel = eval('(' + jsonData + ')');
	generateChart(barModel);
}else{
	document.getElementById("highchartChart").innerHTML += "<div style=\"font-size:"+hjb51*30/1080+"px;text-align:center;" +
	"color:rgb(150,150,150);padding:"+hjb51*150/1080+"px;\" align=\"center\">没有数据，<BR/>请更换节点或查询条件<br/></div>";
 }
}

var imgPercent = hjb51/1080; //比例 
/**
 * 创建柱状图
 * @param barModel
 */
function generateChart(barModel) {
    var chart;
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'highchartChart',
                type: 'bar',
                width:wjb51,
                height:hjb51*475/1080,
                backgroundColor: 'rgba(255, 255, 255, 0)',
                plotBorderColor : null,
                plotBackgroundColor: null,
                plotBackgroundImage:null,
                plotBorderWidth: null,
                plotShadow: false 
            },
            title: {
                text: barModel.title
            },
            xAxis: {
                categories: barModel.columnKeys
            },
            yAxis: {
                min: 0,
                max:100,
                title: {
                    text: null
                },
            },
            legend: {
                backgroundColor: '#FFFFFF',
                reversed: true,
                width:100,
                y:-100,
                align:'right',
            },
            tooltip: {
                formatter: function() {
                    return ''+
                        this.series.name +': '+ this.y +'%';
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
                name: barModel.rowKeys[3],
                data: barModel.data3,
                color:'rgba(127,127,127,1)'
                //color:{
                	//linearGradient: [100, 0, 500, 500],
                	//stops: [[0, 'rgba(127,127,127,1)'] , [1, 'rgba(214,214,214,0)']]
                	//}
            }, {
                name: barModel.rowKeys[1],
                data: barModel.data1,
                color:'rgba(205,105,201,1)'
                //color:{
                	//linearGradient: [100, 0, 500, 500],
                	//stops: [[0, 'rgba(205,105,201,1)'] , [1, 'rgba(218,112,214,0)']]
                	//}
            }, {
                name: barModel.rowKeys[2],
                data: barModel.data2,
                color:'rgba(255,48,48,1)'
                //color:{
                	//linearGradient: [100, 0, 500, 500],
                	//stops: [[0, 'rgba(255,48,48,1)'] , [1, 'rgba(255,48,48,0)']]
                	//}
            },{
            	 name: barModel.rowKeys[0],
                 data: barModel.data0,
                 color:'rgba(69,139,0,1)'
                 //color:{
                 	//linearGradient: [0, 0, 800, 800],
                 	//stops: [[0, 'rgba(69,139,0,1)'] , [1, 'rgba(127,255,0,0.7)']]
                 	//}
            }]
            
        });
};