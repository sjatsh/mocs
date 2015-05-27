function loadData() {
var columJsonData = document.getElementById("myform:beanvalue").value; //jQuery("#beanvalue").attr('value');
var columTwoJsonData = document.getElementById("myform:beanvalue1").value;
//alert("----->"+columTwoJsonData);
var columJsonData = eval('(' + columJsonData + ')');
var columTwoJsonData = eval('(' + columTwoJsonData + ')');
generateoneChart(columJsonData);
generatetwoChart(columTwoJsonData);
}

function loadDate_one(){
	var columJsonData = document.getElementById("myform:beanvalue").value; 
	var columJsonData = eval('(' + columJsonData + ')');
//	alert(columJsonData.data0);
	generateoneChart(columJsonData);
}
function loadDate_two(){
	var columTwoJsonData = document.getElementById("myform:beanvalue1").value;
	var columTwoJsonData = eval('(' + columTwoJsonData + ')');
	generatetwoChart(columTwoJsonData);
}

var generheight = hjb51*510/1080; 
var generseries = wjb51*70/1920; 
var generitemWidth = wjb51*150/1920; 
var generwidth = wjb51*130/1920; 
var genersymbolWidth = wjb51*32/1920; 
var generlegendx = wjb51*200/1920; 
var generfontSize = hjb51*20/1080;


function generateoneChart(barModel) {  
	chart = new Highcharts.Chart({
		
		 chart: {
	         type: 'column',
	         renderTo : 'productcostoneChart',
	         height:generheight,
	         width: barModel.rowkeys.length<31?50*33:50*barModel.rowkeys.length,
	         backgroundColor: 'rgba(255, 255, 255, 0)',
            plotBorderColor : null,
            plotBackgroundColor: null,
            plotBackgroundImage:null,
            plotBorderWidth: null,
            plotShadow: false
	     },
		
	            title: {
	                text: ''
	            },
	            xAxis: {
	            	categories: barModel.rowkeys
	                
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: ''
	                },
	                gridLineColor: "rgba(255, 255, 255, 0)",  
	                labels: {  //X轴的标签（下面的说明）
						enabled: false,               //是否显示
					},
	                   
	            },
	        
	        credits: {
	            	 text: '',
	            	 href: ''
	        },
			exporting:{ 
				 enabled:false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示 
			},
	            tooltip: {
	                formatter: function() {
	                    return '<b>'+ this.x +'</b><br/>'+
	                        this.series.name +': '+ this.y +'<br/>'+
	                        'Total: '+ this.point.stackTotal;
	                }
	            },
	            plotOptions: {
	                column: {
	                    stacking: 'normal',
	                    dataLabels: {
	                        enabled: false,
	                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
	                    }
	                },
	                series: {
	    	            pointWidth: generseries,
	    	        }
	            },
	            legend: {
	            	backgroundColor: 'rgba(255,255,255,0)',
					itemWidth: generitemWidth,
					width:generwidth,
					symbolWidth: genersymbolWidth,
	            	//layout: 'vertical',
	                //enabled:true,
	                x: -generlegendx,
	                //verticalAlign: 'bottom',
	                //y: 60,
	                //floating: true,
	                itemStyle:{
	                	fontSize:generfontSize
	                },
	                enabled:false,

	            },
	            colors:[                   
	         
	                    'rgba(0,160,64,1)',
	                    'rgba(255,220,0,1)',
	                    'rgba(191,191,191,1)',

	            ],
	            series: [{
	                name: '<SPAN style="color:rgb(89, 89, 89)">'+barModel.columnkeys[0]+'</SPAN>',
	                data: barModel.data0
	            }, {
	                name: '<SPAN style="color:rgb(89, 89, 89)">'+barModel.columnkeys[1]+'</SPAN>',
	                data: barModel.data1
	            }, {
	                name: '<SPAN style="color:rgb(89, 89, 89)">'+barModel.columnkeys[2]+'</SPAN>',
	                data: barModel.data2
	            }]
	          
	        });

   }

var twoheight = hjb51*510/1080;
var width90 = wjb51*90/1920; 
var twopointWidth = wjb51*70/1920; 

function generatetwoChart(barModel) {  

	
	chart = new Highcharts.Chart({
		
		 chart: {
	         type: 'column',
	         renderTo : 'productcosttwoChart',
	         height:twoheight,
	         width: barModel.rowkeys.length<31?width90*33:width90*barModel.rowkeys.length,
	         backgroundColor: 'rgba(255, 255, 255, 0)',
           plotBorderColor : null,
           plotBackgroundColor: null,
           plotBackgroundImage:null,
           plotBorderWidth: null,
           plotShadow: false
	     },
		
	            title: {
	                text: ''
	            },
	            xAxis: {
	            	categories: barModel.rowkeys
	                
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: ''
	                },
	                gridLineColor: "rgba(255, 255, 255, 0)",  
	                labels: {  //X轴的标签（下面的说明）
						enabled: false,               //是否显示
					},
	                   
	            },
	        
	        credits: {
	            	 text: '',
	            	 href: ''
	        },
			exporting:{ 
				 enabled:false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示 
			},
	            tooltip: {
	                formatter: function() {
	                    return '<b>'+ this.x +'</b><br/>'+
	                        this.series.name +': '+ this.y +'<br/>'+
	                        'Total: '+ this.point.stackTotal;
	                }
	            },
	            plotOptions: {
	                column: {
	                    stacking: 'normal',
	                    dataLabels: {
	                        enabled: false,
	                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
	                    }
	                },
	                series: {
	    	            pointWidth: twopointWidth,
	    	            shadow:true
	    	        }
	            },
	            legend: {
	            	backgroundColor: 'rgba(255,255,255,0)',
	            	enabled:false

	            },
	            colors:[                   
	         
	                    'rgba(0,160,64,1)',
	                    'rgba(255,220,0,1)',
	                    'rgba(191,191,191,1)',

	            ],
	            series: [{
	                name: barModel.columnkeys[0],
	                data: barModel.data0
	            }, {
	                name: barModel.columnkeys[1],
	                data: barModel.data1
	            }, {
	                name: barModel.columnkeys[2],
	                data: barModel.data2
	            }]
	          
	        });
    }