/**
 * 加载图表所需数据
 */
function loadData() {
	var jsonData = document.getElementById("myform:lineChart").value;
	//alert(jsonData);
	//jsonData=null;
	if (null != jsonData && "" != jsonData && jsonData.length > 5) {
		var barModel = eval('(' + jsonData + ')');
		generateChart(barModel);
	} else {
		document.getElementById("highchartChart").innerHTML += "<div style=\"font-size:" + hjb51 * 30 / 1080 + "px;text-align:center;" + "color:rgb(150,150,150);padding:" + hjb51 * 150 / 1080 + "px;\" align=\"center\"><span data-i18n='zwxxts'></span></div>";
	}
}
/**
 * 创建线图表	
 * @param barModel
 */
var textStr = "content.tbtitle";
dataTranslate("stat_device_oee_trend",
function(t) {
	textStr = t(textStr);
});
function generateChart(barModel) {
	chart = new Highcharts.Chart({
		chart: {
			renderTo: 'highchartChart',
			type: 'line',
            width: $("#highchartChart").width(),
            height: $("#highchartChart").height(),
			/*backgroundColor:{linearGradient: [800, 0, 800, 500],stops: [[0, 'rgba(225,225,225,1)'] , [1, 'rgba(255,255,255,0)']]},
  */
			borderRadius: 0,
			backgroundColor: 'rgba(255, 255, 255, 0)',
			plotBorderColor: null,
			plotBackgroundColor: null,
			plotBackgroundImage: null,
			plotBorderWidth: null,
			plotShadow: false
		},
		title: {
			text: textStr,
			style: {
				color: '#7A7A7A'
			}
		},
		tooltip: {
			formatter: function() {
				return '' + this.series.name + '<br/>' + this.x + ': ' + this.y + '';
			}
		},
		credits: {
			enabled: false
		},
		exporting: {

			enabled: false
		},
		legend: {
			backgroundColor: '#FFFFFF',
			reversed: true,
			height: 15,
			//     layout:'vertical',
			//     align:'right'
		},
		xAxis: {
			categories: barModel.columnkeys
		},

		yAxis: {
			title: {
				//text:'指示值'
			},
			max: 1,
			min: 0
		},
		series: [{
			name: barModel.rowkeys[4],
			data: barModel.data4,
			color: 'rgba(244,221,12,1)'
		},
		{
			name: barModel.rowkeys[3],
			data: barModel.data3,
			color: 'rgba(251,117,0,0.4)'
		},
		{
			name: barModel.rowkeys[2],
			data: barModel.data2,
			color: 'rgba(251,117,0,1)'
		},
		{
			name: barModel.rowkeys[1],
			data: barModel.data1,
			color: 'rgba(108,204,70,0.4)'
		},
		{
			name: barModel.rowkeys[0],
			data: barModel.data0,
			color: 'rgba(108,204,70,1)'
		}]
	});
}