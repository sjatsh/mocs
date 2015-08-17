Ext.ns('App');
Ext.Loader.setConfig({ enabled : true, disableCaching : true });
Ext.Loader.setPath('App', '.');
Ext.Loader.setPath('Sch.plugin', '.');
var editPlanId=-1;
var statusId=-1;
var start;
var end;
var titleField;
var locationField;
var pId; 
var planNumField;
var priId;
var planType;

//控制长宽的大小
var createWidth = $("#jobplan_content").width();
var createHeight = $("#jobplan_content").height();
var createRowHeight = createHeight * 0.06;
var createColumnWidth = createWidth * 0.25;
/*
 * 未知代码
 * var fieldStyle = "font-size:"+wjb51*25/1920+"px;";
var fieldWidth = wjb51*200/1920;
var defineWidth = wjb51*490/1920;
var defineHeight = hjb51*350/1080;
var fsize = 5;*/
var resourceStore;
var eventStore;

//准备
Ext.onReady(function () {
		Ext.QuickTips.init();
		
		 //定义开始时间与结束时间
		var date = new Date();//获取当月时间
		date.setMonth(date.getMonth()-1);//将时间提前两个月
		date.setDate(1);
		start=date;
		
		var date1 = new Date();
		date1.setMonth(date1.getMonth()+2);//将时间延后两个月
		date1.setDate(1);
		date1=new Date(date1.getTime()-1000*60*60*24); //YT 修改，获取月份最后一天
		end=date1;
		    
	 /**YT 修改*/
       document.getElementById("myform:startInputDate").value=Ext.Date.format(date, "Y-m-d");          
	   document.getElementById("myform:endInputDate").value=Ext.Date.format(date1, "Y-m-d");
		   
	//定义资源类
		Ext.define('Resource', {
			extend : 'Sch.model.Resource',
			fields: [
				'Type'
			]
		});

		//定义任务事件类   作业计划表  tjobplaninfo
		Ext.define('Event', {
			extend : 'Sch.model.Event',
			fields: [
			           'No','Name','planId','finishNum','planNum','Priority','Percentage','Status','jobPlanfinisNum','partName','planType','statusName','partId'
		            ]
		});

		//定义获取资源Store函数
		resourceStore = Ext.create("Sch.data.ResourceTreeStore", {
	            model   : 'Resource',
	            proxy   : {
	                type    : 'ajax',
	                extraParams : {
	                	partid : document.getElementById("select_box_part_hdn").value,
						planStatus:document.getElementById("select_box_hdn").value,
						startTime:Ext.Date.format(date, "Y-m-d"),
						endTime:Ext.Date.format(date1, "Y-m-d"),
						isexpand:'true'//tree甘特图是否折叠，true为展开
					},
	                url:'./jobplan/getjobplanName.action'//获取产品名称信息
	            },
	            //定义排序列名跟排序方式
	            sortInfo: { field: 'Id', direction: "ASC" }//方向    升序
	        });
		 
		//定义获取事件Store函数
		 eventStore = Ext.create("Sch.data.EventStore", {
	            model   : 'Event',
	            proxy   : {
	                type    : 'ajax',
	                extraParams : {
	                	ishighlight:'true',
	                	partid : document.getElementById("select_box_part_hdn").value,
						planStatus:document.getElementById("select_box_hdn").value,
						startTime:Ext.Date.format(date, "Y-m-d"),
						endTime:Ext.Date.format(date1, "Y-m-d")
					},
	                url     : './jobplan/getjobEvent.action',//获取作业计划信息
	                reader  : {
	                    type    : 'json',
	                    root    : 'data'
	                }
	            }

			});
		
		 //加载数据信息
		 resourceStore.load();
		 eventStore.load();

		//设置弹出模版文字
		var tooltipTitle1,tooltipTitle2,tooltipTitle3,tooltipTitle4,tooltipTitle5,tooltipTitle6,tooltipTitle7,tooltipUnit;
		dataTranslate("jobplan_detail", function(t) { 
			tooltipTitle1 = t("content.tooltip_1"); 
			tooltipTitle2 = t("content.tooltip_2"); 
			tooltipTitle3 = t("content.tooltip_3"); 
			tooltipTitle4 = t("content.tooltip_4"); 
			tooltipTitle5 = t("content.tooltip_5"); 
			tooltipTitle6 = t("content.tooltip_6"); 
			tooltipTitle7 = t("content.tooltip_7");
			tooltipUnit = t("content.tooltipUnit");
		});
	  //创建panel grid
	    ds = Ext.create('Sch.panel.SchedulerTree', {
	    	renderTo    : 'example-container',
	    	width:createWidth,
			height:createHeight,
	        rowHeight   : createRowHeight,
	        eventStore      : eventStore,
	        resourceStore   : resourceStore,
	        constrainDragToResource:true,
	        useArrows       : false,
	        icon 			: null,
	        rootVisible		: true,
	    	viewPreset  : 'monthAndYear',
	        //viewPreset  : 'weekAndDay',
	        startDate   : start,
			endDate     : end,
	        multiSelect     : true,
	        columnLines     : false,
	        rowLines        : true,      
	        enableDragCreation:false,  //禁止鼠标拖动新建
	        enableEventDragDrop:false, //禁止拖动

	        onEventCreated : function(newFlight) {
	            newFlight.set('Name', 'New departure');
	        },

	        columns: [
	            {
	                xtype       : 'treecolumn', //this is so we know which column will show the tree
	                text        : '<span data-i18n="content.tree_title"></span>',
	                width		: createColumnWidth,
	                sortable    : false,
	                dataIndex   : 'Name'
	            }
	        ],
	        
	        eventBodyTemplate : new Ext.XTemplate(
	    			'<div class="sch-percent-allocated-bar" style="width:{Percentage}%;"></div><span id="sch-percent-allocated-text" class="sch-percent-allocated-text">{[values.planNum]}/{[values.finishNum]}</span>'
	        ).compile(),
	        
	      //鼠标移动提示
		    tooltipTpl: new Ext.XTemplate(
					'<dt class="blocking">' + tooltipTitle1 + ': {partName}</dt>',
					'<dt class="icon-clock">' + tooltipTitle2 + ': {[Ext.Date.format(values.StartDate, "Y-m-d G:i")]} </dt>',
					'<dt class="icon-clock">' + tooltipTitle3 + ': {[Ext.Date.format(values.EndDate, "Y-m-d G:i")]} </dt>',
					'<dt class="icon-task">' + tooltipTitle4 + ': {Name}</dt>',
					'<dt class="connecting">' + tooltipTitle5 + ': {planNum} ' + tooltipUnit + '</dt>',
					'<dt class="blocking">' + tooltipTitle6 + ': {finishNum} ' + tooltipUnit + '</dt>',
					'<dt class="blocking">' + tooltipTitle7 + ': {statusName}</dt>',
					'</dl>'
				).compile()
	    });

	ds.scrollToDate(new Date(),true);
	//局部翻译
	dataTranslate("jobplan_detail", function(t) { $("#example-container *[data-i18n]").i18n();});
	
	
	//改变作业计划长度
	ds.on("eventresizeend",function(scheduler, record, eOpts){
		 if(record.data.Status==10)
	     {
			 changeWorkPlanLength(record);
	     }
		 if(record.data.Status==20)
		 {
			 changeWorkPlanLength(record);
		 }
		 if(record.data.Status==30)
		 {
			 changeWorkPlanLength(record);
		 }
		 if(record.data.Status==40||record.data.Status==50)
		 {
			 
			 if(!(document.getElementById("hplanStartTime").value==record.data.StartDate))
				 {
				   Ext.Msg.alert("提示", "此状态只能拖动计划结束时间!", function () {
		         });
				 
				 }
			 else
				 {
				   changeWorkPlanLength(record);
			     }
		 }
		 if(record.data.Status==60||record.data.Status==70)
		 {
			 Ext.Msg.alert("提示", "此状态拖动无效!", function () {
	        });
	    }
	});
	
	
	ds.on("eventresizestart",function(scheduler, record, eOpts ){
		 
		 if(record.data.Status==40||record.data.Status==50)
		 {
			document.getElementById("hplanStartTime").value=record.data.StartDate;
		 }
	});
	
	//点击产品名称时，将新建按按钮->新增作业计划
	ds.on("select",function(this1,record, eOpts ){
		
		if(record.id.indexOf("Resource")>=0){
			var id = record.data.Id;
			document.getElementById("myform:flgId").value=id;
			$("#control_add").parent().addClass("active");
			 
		    //document.getElementById("control_add").href="javascript:openUrl('./jobplan/jobplan_add.faces?partId="+id+"')";
			//document.getElementById("control_add").click();
			//document.getElementById("img2").title="新增作业计划";
			
		} 
	});
	//点击具体的作业计划对应后台操作
	ds.on("eventclick",function(scheduler,eventRecord,e,eOpts){
		statusId=eventRecord.data.Status;
		planType = eventRecord.data.planType;
		var planId = eventRecord.data.Id;
		var partId = eventRecord.data.partId;
		editPlanId=planId;
		
		document.getElementById("myform:plantype").value=eventRecord.data.planType;
		var StartDate = Ext.Date.format(eventRecord.data.StartDate, "Y-m-d G:i");
		var EndDate = Ext.Date.format(eventRecord.data.EndDate, "Y-m-d G:i");
		
		//重置按钮状态
		$(".zl-content-top-info-down-btn").removeClass("active");
		//控制按钮权限
//	    if($("#myform\\:viewDisabled").val() == "false") {
//	    	return;
//	    }
	    
		//常开按钮
		$("#control_edit,#control_week,#control_month,#control_year,#control_big,#control_small").parent().addClass("active");

		//新增
		var addTitle1, addTitle2;
		dataTranslate("jobplan_detail", function(t) {
			addTitle1 = t("top.btn1");
			addTitle2 = t("top.btn1_2");
		});
		if(planType == 1){
			$("#control_add").attr("title", addTitle1).parent().addClass("active");
			$("#control_add").attr("href", "javascript:void(1);");
			document.getElementById("myform:planId").value=planId;
			
		}else if(planType == 2){
			$("#control_add").attr("title", addTitle2).parent().addClass("active");
			if(statusId==10)
	    	{
				$("#control_del").parent().addClass("active");
	    	}
			document.getElementById("myform:planId").value=planId;
			document.getElementById("myform:partId1").value=partId;
			document.getElementById("myform:subjobplanStartDate").value=StartDate;
			document.getElementById("myform:subjobplanEndDate").value=EndDate;
		}

		//状态
		if(eventRecord.data.planType ==1 )
		{
	    }else {
	    	if(statusId==10)
	    	{
				$("#control_status").parent().addClass("active");
	    	}
	    }
		jobName=eventRecord.data.Name;
	});
	
});

	App.Scheduler = {
		init: function () {
			this.scheduler = this.createScheduler();
		},
		createScheduler: function () {
			return ds;
		}
	};
//改变作业时间长度调用后台方法
	function  changeWorkPlanLength(record)
	{
		 Ext.Ajax.request({
				url:"./jobplan/update.action",
				method:"post",
				dataType : 'json',  
				success:function(response){
					Ext.Msg.alert("提示", "修改作业计划长度成功!", function () {
						ds.getEventStore().reload();			
	                    });
	           },
				failure:function(response,opts){
					Ext.Msg.alert("failure");
				},
				params:{
					m:"",
					startTime:record.data.StartDate,
					endTime:record.data.EndDate,
					id:record.data.Id,
					statusFlag:record.data.Status
			    }
		    });
	
	}









