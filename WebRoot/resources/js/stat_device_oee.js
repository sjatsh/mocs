/**
 * 加载图表所需数据
 */
function loadData() {
var jsonData = document.getElementById("myform:beanvalue").value;
//jsonData=null;
//alert(jsonData);
if(null !=jsonData && ""!=jsonData ){
	var barModel = eval('(' + jsonData + ')');
	generateChart(barModel);
}else{
	document.getElementById("highchartChart").innerHTML += "<div style=\"font-size:0.3rem;text-align:center;" +
	"color:rgb(150,150,150);padding:1.5rem;\" align=\"center\"><span data-i18n='zwxxts'></span></div>";
 }
}

/**
 * 创建柱状图
 * @param barModel
 */
var textStr = "content.tbtitle";
dataTranslate("stat_device_oee", function(t) { textStr = t(textStr);});
function generateChart(barModel) {
    var chart;
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'highchartChart',
                type: 'bar',
                width: $("#highchartChart").width(),
                height:$("#highchartChart").height(),
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
                height:15,
                
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
                color:'rgba(160,160,160,0.8)'
                //color:{
                	//linearGradient: [100, 0, 500, 500],
                	//stops: [[0, 'rgba(127,127,127,1)'] , [1, 'rgba(214,214,214,0)']]
                	//}
            }, {
                name: barModel.rowKeys[1],
                data: barModel.data1,
                color:'rgba(0,129,206,0.8)'
                //color:{
                	//linearGradient: [100, 0, 500, 500],
                	//stops: [[0, 'rgba(205,105,201,1)'] , [1, 'rgba(218,112,214,0)']]
                	//}
            }, {
                name: barModel.rowKeys[2],
                data: barModel.data2,
                color:'rgba(251,117,0,0.8)'
                //color:{
                	//linearGradient: [100, 0, 500, 500],
                	//stops: [[0, 'rgba(255,48,48,1)'] , [1, 'rgba(255,48,48,0)']]
                	//}
            },{
            	 name: barModel.rowKeys[0],
                 data: barModel.data0,
                 color:'rgba(108,204,71,0.8)'
                 //color:{
                 	//linearGradient: [0, 0, 800, 800],
                 	//stops: [[0, 'rgba(69,139,0,1)'] , [1, 'rgba(127,255,0,0.7)']]
                 	//}
            }]
            
        });
};