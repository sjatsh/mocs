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
var createWidth = wjb51*1894/1920;
var createHeight = hjb51*685/1080;
var createRowHeight = hjb51*39/1080;
var createColumnWidth = wjb51*374/1920;
var fieldStyle = "font-size:"+wjb51*25/1920+"px;";
var fieldWidth = wjb51*200/1920;
var defineWidth = wjb51*490/1920;
var defineHeight = hjb51*350/1080;
var fsize = 5;
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
						isexpand:'false'//tree甘特图是否折叠，true为展开
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
	    
	  //创建panel grid
	    ds = Ext.create('Sch.panel.SchedulerTree', {
	    	renderTo    : 'example-container',
	    	width:createWidth,
			height:createHeight,
	        rowHeight   : createRowHeight,
	        eventStore      : eventStore,
	        resourceStore   : resourceStore,
	        constrainDragToResource:true,
	        useArrows       : true,
	        viewPreset  : 'monthAndYear',//月和年刻度
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
	                text        : '<span style="font-size: 18.6px;">产品名称</span>',
	                width:createColumnWidth,
	                sortable    : true,
	                dataIndex   : 'Name'
	            }
	        ],
	        
	        eventBodyTemplate : new Ext.XTemplate(
	    			'<div class="sch-percent-allocated-bar" style="width:{Percentage}%;"></div><span id="sch-percent-allocated-text" class="sch-percent-allocated-text">{[values.planNum]}/{[values.finishNum]}</span>'
	        ).compile(),
	        
	      //鼠标移动提示
		     tooltipTpl: new Ext.XTemplate(
		        '<dl class="eventTip">',
		        	'<dt class="blocking">零件名称:{partName}</dt>',
		            '<dt class="icon-clock">开始时间:{[Ext.Date.format(values.StartDate, "Y-m-d G:i")]} </dt>',
		            '<dt class="icon-clock">结束时间:{[Ext.Date.format(values.EndDate, "Y-m-d G:i")]} </dt>',
		            '<dt class="icon-task">计划名称:{Name}</dt>',
		            '<dt class="connecting">计划数量:{planNum} 个</dt>',
	                '<dt class="blocking">完成数量:{finishNum} 个</dt>',
	                '<dt class="blocking">状态:{statusName}</dt>',
	            '</dl>'
		    ).compile(),
	        
	    });

	ds.scrollToDate(new Date(),true);
	
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
			document.getElementById("herf").href="javascript:openUrl('./jobplan/jobplan_add.faces?partId="+id+"')";
			document.getElementById("img2").title="新增作业计划";
		}
	});
	//点击具体的作业计划对应后台操作
	ds.on("eventclick",function(scheduler,eventRecord,e,eOpts){
		statusId=eventRecord.data.Status;
		planType = eventRecord.data.planType;
		var planId = eventRecord.data.Id;
		var partId = eventRecord.data.partId;
		editPlanId=planId;
		
		var StartDate = Ext.Date.format(eventRecord.data.StartDate, "Y-m-d G:i");
		var EndDate = Ext.Date.format(eventRecord.data.EndDate, "Y-m-d G:i");
//		alert(planType);
		if(planType == 1){
			document.getElementById("herf").href="javascript:openUrl('./jobplan/jobplan_add.faces?planId="+planId+"')";
			document.getElementById("img2").title="新增作业计划";
		}else if(planType == 2){
			document.getElementById("herf").href="javascript:openUrl('./jobdispatch/jobdispatch_add.faces?partId="+partId+"&StartDate="+StartDate+"&EndDate="+EndDate+"&planId="+planId+"')";
			document.getElementById("img2").title="新增工单";	
		}
		if(eventRecord.data.planType ==1 )
		 {
			 document.getElementById("jobplanimg").src="./images/jobplan/dispatchstart_un.png";
		     document.getElementById("jobplanaid").title="作业计划启动";//与xhtml有关 
	     }else
	     {
		 if(statusId==10)
		 {
			document.getElementById("jobplanimg").src="./images/jobplan/dispatchstart.png";
	        document.getElementById("jobplanaid").title="作业计划启动";//与xhtml有关
			 
		 }
		 else if(statusId==40)
	     {
	        document.getElementById("jobplanimg").src="./images/jobplan/dispatchstop.png";
	        document.getElementById("jobplanaid").title="作业计划停止";
			 
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









