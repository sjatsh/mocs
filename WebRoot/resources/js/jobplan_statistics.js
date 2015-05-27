function loadData() {
var columJsonData = document.getElementById("myform:beanvalue").value; //jQuery("#beanvalue").attr('value');
var columJsonData = eval('(' + columJsonData + ')');
//alert("----->"+columJsonData.planValue.length);
statisticsOneChart(columJsonData);
}

function statisticsOneChart(barModel){	
	
	chart = new Highcharts.Chart({
    chart: {
        type: 'column',
        width: barModel.planValue.length<5?200*7:200*barModel.planValue.length,
        height: 350,
        renderTo : 'statisticsOneChart'
    },
    title: {
        text: ''
    },

    xAxis: {
        categories: barModel.columns
    },

    yAxis: {
        allowDecimals: false,
        min: 0,
        title: {
            text: ''
        },
        gridLineColor: "white",  
    },
    credits: {   //去掉广告
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
            stacking: 'normal'
        },
        series: {
            pointWidth: 40
        }
    },
    colors:[                   
            'rgba(0,168,255,1)',
            'rgba(254,0,0,1)',
            'rgba(0,160,64,1)',
            
            
         
    ],
 
    series: [{
        name: '计划产量',
        data: barModel.planValue,
        stack: 'male'
    }, {
        name: '不合格数',
        data: barModel.noactualValue,
        stack: 'female'
    }, {
        name: '合格数',
        data: barModel.actualValue,
        stack: 'female'
    }]
    
  /*  
    series: [{
        name: 'John',
        data: [5, 3, 4, 7, 2],
        stack: 'male'
    }, {
        name: 'Joe',
        data: [3, 4, 4, 2, 5],
        stack: 'male'
    }, {
        name: 'Jane',
        data: [2, 5, 6, 2, 1],
        stack: 'female'
    }, {
        name: 'Janet',
        data: [3, 0, 4, 4, 3],
        stack: 'female'
    }]
    */
    
    
});
	
	
}