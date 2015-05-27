function loadData() {
//alert("11111111======>"+document.getElementById("myform:beanvalue").value);
var twoLineJsonData = document.getElementById("myform:beanvalue").value; //jQuery("#beanvalue").attr('value');
var polarJsonData = document.getElementById("myform:beanvalue1").value;
var twoColumnJsonData = document.getElementById("myform:beanvalue2").value;
//alert("----->"+polarJsonData);
var twoLineJsonData = eval('(' + twoLineJsonData + ')');
var polarJsonData = eval('(' + polarJsonData + ')');
var twoColumnJsonData = eval('(' + twoColumnJsonData + ')');
ajaxclickChart(twoLineJsonData);
polarChart(polarJsonData);
coumndataChart(twoColumnJsonData);
}

function loadData_no() { //带参数的传值
	var polarJsonData = document.getElementById("myform:beanvalue1").value;
	var twoColumnJsonData = document.getElementById("myform:beanvalue2").value;
	var polarJsonData = eval('(' + polarJsonData + ')');
	var twoColumnJsonData = eval('(' + twoColumnJsonData + ')');
	polarChart(polarJsonData);
	coumndataChart(twoColumnJsonData);
}

var ajaxmarginRight = wjb51*130/1920;
var ajaxmarginBottom = hjb51*25/1080;
var ajaxheight = hjb51*270/1080;
var ajaxwidth = wjb51*1535/1920;
var ajaxtitle = hjb51*20/1080;
var ajaxplotOptions = hjb51*2/1080;
var ajaxarea = hjb51*5/1080;

//1,Ajax loaded data, clickable points
function ajaxclickChart(barModel) {

	chart = new Highcharts.Chart({
	 chart: {
		 renderTo : 'productcostoneChart',
         type: 'line',
         marginRight: ajaxmarginRight,
         marginBottom: ajaxmarginBottom,
         height:ajaxheight,
         width:ajaxwidth,
         backgroundColor: 'rgba(255, 255, 255, 0)',
         plotBorderColor : null,
         plotBackgroundColor: null,
         plotBackgroundImage:null,
         plotBorderWidth: null,
         plotShadow: false
     },
     title: {
         text: '',
         x: -ajaxtitle //center
     },
     subtitle: {
         text: '',
         x: -ajaxtitle
     },
     xAxis: {
         categories: barModel.columns,
         labels: {  //X轴的标签（下面的说明）
				enabled: false,       //是否显示
			},
     },
     yAxis: {
         title: {
             text: ''
         },
         plotLines: [{
             value: 0,
             width: 1,
             color: '#808080'
         }],
         gridLineColor: "white",  
         labels: {  //X轴的标签（下面的说明）
				enabled: true,       //是否显示
			},

     },
		plotOptions: {
         line: {
             lineWidth: ajaxplotOptions,
             states: {
                 hover: {
                     lineWidth: ajaxplotOptions
                 }
             },
             marker: {
                 enabled: false
             }
      
         },
			area: {
             lineWidth: ajaxarea,
				lineColor:'rgba(206,238,255,1)',
             states: {
                 hover: {
                     lineWidth: ajaxarea
                 }
             },
             marker: {
                 enabled: false
             }
      
         }
     },
     tooltip: {
         valueSuffix: ''
     },
     credits: {
              text: '',
              href: ''
     },
     exporting:{ 
          enabled:false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示 
     }, 
     legend: {
         enabled:false,
     },
     series: [{
         name: '实际成本',
         data: barModel.columnValue,
			type:'area',
			color:'rgba(14,173,255,1)'
     },{
         name: '理论成本',
         data: barModel.constaValue,
			dashStyle: 'longdash',
			color: 'rgba(0,160,64,1)',
     }]
 });


}

var polarheight = hjb51*437/1080;

//2,Polar chart成本偏差
function polarChart(barModel) {
//	var width = jQuery(window).width();
//	var height = jQuery(window).height();
//	jQuery("#productcosttwoChart").width(width / 1.2).height(height / 1.5);	
	
	chart = new Highcharts.Chart({	
		
	    chart: {
	    	renderTo : 'productcosttwoChart',
	        polar: true,
	        height:polarheight,
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
	
	credits: {
        	 text: '',
        	 href: ''
    },
	
	exporting:{ 
		 enabled:false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示 
	},
    
    pane: {
        startAngle: 0,
        endAngle: 360
    },

    xAxis: {
        tickInterval: 72,
        min: 0,
        max: 360,
        labels: {
        	formatter: function () {
        		//return this.value + '';
        	}
        }
    },
        
    yAxis: {
        min: 0
    },
    
    plotOptions: {
        series: {
            pointStart: 0,
            pointInterval: 72
        },
        column: {
            pointPadding: 0,
            groupPadding: 0
        }
    },
    legend: {
        enabled:false
    },
    series: [{
        type: 'line',
        name: '实际',
		color: 'rgba(0,168,255,1)',
        data: barModel.columnValue
    }, {
        type: 'area',
        name: '理论',
		color: 'rgba(0,160,64,1)',
        data: barModel.constaValue
    }]
});
	
}

var coumnwidth = wjb51*1140/1920; 
var coumnheight = hjb51*433/1080;
var coumnplotOptions  = hjb51*60/1080;
var coumnlegendx = wjb51*480/1920; 
var coumnlegendy  = hjb51*20/1080;


function coumndataChart(barModel) {  //通过对比后json是可以改动的
//	var width = jQuery(window).width();
//	var height = jQuery(window).height();
//	jQuery("#productcostthreeChart").width(width / 1.2).height(height / 1.5);	
	
	chart = new Highcharts.Chart({
        data: {
//            table: document.getElementById('datatable')
        },
        chart: {
        	renderTo : 'productcostthreeChart',
            type: 'column',
            width:coumnwidth,
            height:coumnheight,
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
        yAxis: {
            allowDecimals: false,
            gridLineColor: "rgba(255,255,255,0)",  
            title: {
                text: ''
            }
        },
        xAxis: {
            categories: barModel.columns
        },
        tooltip: {
            formatter: function() {
                return '<b>'+ this.series.name +'</b><br/>'+
                    this.y +' '+ this.x.toLowerCase();
            }
        },
        plotOptions: {
         
            series: {
	            pointWidth: coumnplotOptions,

	        }
        },
        legend: {
        	 layout: 'vertical',
            enabled:true,
            x: coumnlegendx,
            verticalAlign: 'top',
            y: coumnlegendy,
            floating: true,
            
        },
		credits: {
       	 text: '',
       	 href: ''
   },
	exporting:{ 
		 enabled:false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示 
	},
        series: [{
            name: '实际成本',
            data: barModel.columnValue,
            color: 'rgba(0,168,255,1)',
        }, {
            name: '理论成本',
            data: barModel.constaValue,
            color: 'rgba(0,160,64,1)',
        }]
    });
}