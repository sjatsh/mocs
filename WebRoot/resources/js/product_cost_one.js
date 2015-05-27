function loadData() {
	var pieJsonData = document.getElementById("myform:beanvalue1").value;
	var columnAndLineJsonData = document.getElementById("myform:beanvalue2").value;
	var pieJsonData = eval('(' + pieJsonData + ')');  
	var columnAndLineJsonData = eval('(' + columnAndLineJsonData + ')'); 
	pieChart(pieJsonData);
	combinationChart(columnAndLineJsonData);
}
function loadData_machine() {
	var columJsonData = document.getElementById("myform:beanvalue").value;
	var columJsonData = eval('(' + columJsonData + ')'); 
	generateChart(columJsonData);
	}

var gencharheight = hjb51*280/1080;
var genpointWidth = wjb51*55/1920;
var genitemWidth = wjb51*140/1920;
var genwidth = wjb51*700/1920;
var gensymbolWidth = wjb51*32/1920;

function generateChart(barModel) {  
	
	chart = new Highcharts.Chart({
		
		 chart: {
	         type: 'column',
	         renderTo : 'productcostoneChart',
	         height:gencharheight,
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
	    	            pointWidth: genpointWidth,
	    	        }
	            },
	            legend: {
	            	backgroundColor: 'rgba(255,255,255,0)',
					itemWidth: genitemWidth-10,
					width:genwidth,
					symbolWidth: gensymbolWidth,

	            },
	            colors:[                   
	         
	                    'rgba(0,168,255,1)',
	                    'rgba(0,160,64,1)',
	                    'rgba(255,220,0,1)',
	                    'rgba(191,191,191,1)',
	                    {linearGradient: [682,340, 682, 320],
	                 	stops: [[0, 'rgba(138,138,138,1)'] , [1, 'rgba(89,89,89,1)']]},//深灰   

	            ],
	            series: [{
	                name: '<SPAN style="color:rgb(89, 89, 89)">'+barModel.columnkeys[0]+'</SPAN>',
	                data: barModel.data0
	            }, {
	                name: '<SPAN style="color:rgb(89, 89, 89)">'+barModel.columnkeys[1]+'</SPAN>',
	                data: barModel.data1
	            },{
	                name: '<SPAN style="color:rgb(89, 89, 89)">'+barModel.columnkeys[2]+'</SPAN>',
	                data: barModel.data2
	            }, {
	                name: '<SPAN style="color:rgb(89, 89, 89)">'+barModel.columnkeys[3]+'</SPAN>',
	                data: barModel.data3
	            },{
	                name: '<SPAN style="color:rgb(89, 89, 89)">'+barModel.columnkeys[4]+'</SPAN>',
	                data: barModel.data4
	            }]
	          
	        });

    }
    
var piemarginRight = wjb51*130/1920; 
var piemarginBottom1 = hjb51*25/1080; 
var piemarginBottom = piemarginBottom1.toString();
var piewidth = wjb51*880/1920; 
var pieheight = hjb51*440/1080;
var piefontsize = hjb51*20/1080; 

function pieChart(barModel) {


    chart = new Highcharts.Chart({
            chart: {
            	renderTo : 'productcosttwoChart',
                marginRight : piemarginRight,
                marginBottom : piemarginBottom,
        	    backgroundColor: 'rgba(255, 255, 255, 0)',
                plotBorderColor : null,
                plotBackgroundColor: null,
                plotBackgroundImage:null,
                plotBorderWidth: null,
                plotShadow: false	,
                width:piewidth,
                height:pieheight
  
            },
            title: {
                text: ''
            },
            tooltip: {
            	enabled:false,
        	    pointFormat: '<b>{point.name}</b> ￥{y} ',
            	percentageDecimals: 1
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        connectorColor: '#000000',
                        
                        format: '<b>{point.name}</b> ￥{y} ',
                        style:{
                        	fontSize:piefontsize,
                        	color:'rgba(138,138,138,1)'
                        	}
                    },
                    showInLegend: false
                }
            },
            
    		colors:[                   
    				   'rgba(0,168,255,1)', //兰
    				   'rgba(0,160,64,1)',  //绿
    				   'rgba(255,220,0,1)', //黄
    				   'rgba(191,191,191,1)', //灰
    				   'rgba(138,138,138,1)',//深灰   
    				],
    				credits: {
    						 text: '',
    						 href: ''
    				},
    				exporting:{ 
    					 enabled:false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示 
    				}, 
    				
            series: [{
                type: 'pie',
                name: 'Browser share',
                data: [
                       [barModel.rowKeys[0],   parseFloat(barModel.columnKeys[0])],
                       [barModel.rowKeys[1],   parseFloat(barModel.columnKeys[1])],
                       {
                           name: barModel.rowKeys[2],
                           y: parseFloat(barModel.columnKeys[2]),
                           sliced: true,
                           selected: true
                       },
                       [barModel.rowKeys[3],    parseFloat(barModel.columnKeys[3])],
                       [barModel.rowKeys[4],    parseFloat(barModel.columnKeys[4])]             
                   ]
            ,
            innerSize:'50%'
            
            }]
        });
    
    
}

var comheight = hjb51*250/1080;
var comwidth = wjb51*840/1920; 
var comitemWidth = wjb51*100/1920;
var comsymbolWidth = wjb51*32/1920;
var compointWidth = wjb51*55/1920;
var onewidth = wjb51*140/1920;

function combinationChart(barModel) {

chart = new Highcharts.Chart({
		
	 chart: {
         type: 'column',
         renderTo : 'productcostthreeChart',
         height:comheight, 
         width:barModel.rowkeys.length<6?onewidth*6:onewidth*barModel.rowkeys.length
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
                gridLineColor: "rgba(255,255,255,0)",  
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
			 legend: {
	             layout: 'horizontal',
	                align: 'right',
	                verticalAlign: 'top',
	                x: 10,
	                y: -10,
	                floating: true,
	                shadow: true,
	         	backgroundColor: 'rgba(255,255,255,0)',
					itemWidth: comitemWidth,
					itemHeight:40,
					//width:300,
					symbolWidth: comsymbolWidth
	
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
    	            pointWidth: compointWidth,

    	            

    	        }
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
            },{
                name: '<SPAN style="color:rgb(89, 89, 89)">'+barModel.columnkeys[2]+'</SPAN>',
                data: barModel.data2
            }]
        });
        
	
	
}
