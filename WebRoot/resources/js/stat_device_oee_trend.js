/**
 * 加载图表所需数据
 */
function loadData() {
var jsonData=document.getElementById("myform:lineChart").value;
	//alert(jsonData);
	if(null!=jsonData&&""!=jsonData&&jsonData.length>5){
		var barModel = eval('(' + jsonData + ')');
		generateChart(barModel);
	}else{
		document.getElementById("highchartChart").innerHTML += "<div style=\"font-size:"+hjb51*30/1080+"px;text-align:center;" +
		"color:rgb(150,150,150);padding:"+hjb51*150/1080+"px;\" align=\"center\">没有数据，<BR/>请更换节点或查询条件<br/></div>";
	}
}
var wjb51=document.documentElement.clientWidth;
var hjb51=document.documentElement.clientHeight;
var imgPercent = document.documentElement.clientHeight/1080; //比例 
/**
 * 创建线图表	
 * @param barModel
 */
function generateChart(barModel) {
chart = new Highcharts.Chart({
 chart : {
  renderTo : 'highchartChart',
  type:'line',
  width: wjb51,
  height: hjb51*475/1080,
  /*backgroundColor:{linearGradient: [800, 0, 800, 500],stops: [[0, 'rgba(225,225,225,1)'] , [1, 'rgba(255,255,255,0)']]},
  */borderRadius: 0,
  backgroundColor: 'rgba(255, 255, 255, 0)',
  plotBorderColor : null,
  plotBackgroundColor: null,
  plotBackgroundImage:null,
  plotBorderWidth: null,
  plotShadow: false 
 },
 title: {
  text:'OEE趋势分析',
  style:{
		fontSize:'14px',
		fontWeight:700,
	 }
 },
 tooltip: {
	 formatter: function() {
         return ''+ this.series.name +'<br/>'+
         this.x +': '+ this.y +'';
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
//     layout:'vertical',
//     align:'right'
 },
 xAxis: {
  categories:barModel.columnkeys
 },

 yAxis: {
	 title: {
         text:'指示值'
     },
     max:1,
     min:0
 },
 series: [{
		  name: barModel.rowkeys[4],
		  data: barModel.data4,
		  color:'#99BF48'
		 }, {
		  name: barModel.rowkeys[3],
		  data: barModel.data3,
		  color:'#31D366'
		 },
		 {
		  name: barModel.rowkeys[2],
		  data: barModel.data2,
		  color:'#BAAA5C'
		},
		{
		  name: barModel.rowkeys[1],
		  data: barModel.data1,
		  color:'#FDC043'
		},
		{
			name: barModel.rowkeys[0],
			data: barModel.data0,
			color:'#43BFEE'
		}]
});
}
