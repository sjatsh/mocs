Ext.ns('App');
Ext.Loader.setConfig({ enabled : true, disableCaching : true });
Ext.Loader.setPath('App', '.');
Ext.Loader.setPath('Sch.plugin', '.');
var editPlanId=-1;
var statusId=-1;
var start;
var end;
var jId;
var rId;
var titleField;
var locationField;
var planId;
var planNumField;
var processNum;
var goodQuantity;
var dispatchNo;

//控制长宽的大小
var createWidth = wjb51*1620/1620;
var createHeight = hjb51*685/1080;
var createRowHeight = hjb51*39/1080;
var createColumnWidth = wjb51*320/1620;
var fieldWidth = wjb51*200/1620;
var defineWidth = wjb51*395/1620;
var defineHeight = hjb51*350/1080;
var fsize = 5;
var resourceStore;
var eventStore;
var equSerialNo;

//准备
Ext.onReady(function () {
	Ext.QuickTips.init();
	
    //定义资源类
	Ext.define('Resource', {
		extend : 'Sch.model.Resource',
		fields: [
			'Id','Name'
		]
	});

	//定义任务事件类
	Ext.define('Event', {
		extend : 'Sch.model.Event',
		fields: [
		    'Id','no','Name','TprocessName','planNum','finishNum','Percentage','Status','rId','goodQuantity','StatusName','partName','equSerialNo','batchNo'
	    ]
	});

	//定义获取资源Store函数
	resourceStore = Ext.create("Sch.data.ResourceStore", {
        model   : 'Resource',
        proxy   : {
            type    : 'ajax',
            url     : './jobdispatch/getDevicesList.action',//获取设备资源信息
            reader  : {
                type    : 'json',
                root    : 'data'
            }
        },
        //定义排序列名跟排序方式
        sortInfo: { field: 'Id', direction: "ASC" }
    });
				
	//定义获取事件Store函数
	eventStore = Ext.create("Sch.data.EventStore", {
        model   : 'Event',
        proxy   : {
            type    : 'ajax',
            url     : './jobdispatch/getjobDispatchList.action',//获取工单信息
            reader  : {
                type    : 'json',
                root    : 'data'
            }
        }
    });
		
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
	
	 //加载数据信息
	resourceStore.load({
		params: {
			taskNum : '',//任务编号默认为空
			jobstatus : '10,20,30,40,50,80',//工单状态id
			partid:'',
			planStime : start,
			planEtime : end
		}, 
		callback: function(records, options, success) {
			 
		},
		scope: this,
		add:false
	});
	eventStore.load({
		params: {
			taskNum : '',//任务编号默认为空
			jobstatus : '10,30,40,50,80',//工单状态id
			partid:'',
			planStime : start,
			planEtime : end
		}, 
		callback: function(records, options, success) {
			 
		},
		scope: this,
		add:false
	});
	
   ds = Ext.create("Sch.panel.SchedulerGrid", {
	renderTo    : 'example-container',
	width:createWidth,
	height:createHeight,
    rowHeight:createRowHeight,
	resourceStore   : resourceStore,
	eventStore      : eventStore,
	constrainDragToResource:true,
	viewPreset  : 'monthAndYear',
	startDate   : start,
	endDate     : end,
	readOnly : false,//禁止拖动和改变长度	
	columns: [
		{header:'<span class="job_manager_textstyle">设备名称</span>',sortable:true,width:createColumnWidth,dataIndex:'Name'}
	],
	
	//工单里面的字体
	eventBodyTemplate : new Ext.XTemplate(
			'<div class="sch-percent-allocated-bar" style="width:{Percentage}%;"></div><span id="sch-percent-allocated-text" class="sch-percent-allocated-text">{[values.Name]}</span>'
    ).compile(),

	//鼠标移动提示
     tooltipTpl: new Ext.XTemplate(
        '<dl class="eventTip" style="font-size:16px;">',
            '<dt class="icon-clock">开始时间:{[Ext.Date.format(values.StartDate, "Y-m-d G:i")]} </dt>',
            '<dt class="icon-clock">结束时间:{[Ext.Date.format(values.EndDate, "Y-m-d G:i")]} </dt>',
            '<dt class="icon-task">零件名称:{partName}</dt>',
            '<dt class="icon-task">工序名称:{TprocessName}</dt>',
            '<dt class="icon-task">计划数量:{planNum} 个</dt>',
            '<dt class="icon-task">完成数量:{finishNum} 个</dt>',
            '<dt class="icon-task">状态:{StatusName}</dt>',
        '</dl>'
    ).compile(),
    
    //鼠标双击编辑
    plugins: [
        this.editor = Ext.create("App.DemoEditor", {

        }),
        
        Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit: 1
        })            
    ],
    //创建新任务
    onEventCreated : function(newEventRecord) {

    }
});

     //右键菜单
    ds.on("eventcontextmenu",function(scheduler, eventRecord, e, eOpts ){
	   var dispatchid=eventRecord.data.Id;
	   e.stopEvent();
    });
     
 	//禁止作业计划的拖动
    ds.on("beforeeventresize",function(scheduler, record, e, eOpts){
    	return false;
    });
    
    //禁止改变作业计划时间长度
    ds.on("beforeeventdrag",function(scheduler, record, e, eOpts){
    	return false;
    });
    
    //禁止拖拽新建一个event
    ds.on("beforedragcreate",function( scheduler, resource, date, e, eOpts ){
        return false;
    });
		    
	 //点击具体的作业计划对应后台操作
	ds.on("eventclick",function(scheduler, eventRecord, e, eOpts ){
		statusId = eventRecord.data.Status;
		if(statusId == 20){
			//启动按钮
			document.getElementById("img8").src="./images/jobplan/jobplan_20.png";	
			//派工绿色
			document.getElementById("img7").src = "././images/jobplan/dispatch.png";
			//编辑按钮
			document.getElementById("img3").src = "./images/jobplan/jobplan_14.png";
			//工单暂停
			document.getElementById("dispatchimages").src = "./images/jobplan/dispatchpause_un.png";
			document.getElementById("dispatch").title = "工单暂停";
		}
		if(statusId == 30){
			//启动按钮
			document.getElementById("img8").src="./images/jobplan/jobplan_20.png";
			//派工灰色
			document.getElementById("img7").src = "././images/jobplan/dispatch_un.png";
			//编辑按钮
			document.getElementById("img3").src = "./images/jobplan/jobplan_14.png";
			//工单暂停
			document.getElementById("dispatchimages").src = "./images/jobplan/dispatchpause_un.png";
			document.getElementById("dispatch").title = "工单暂停";
			//工单启动
			document.getElementById("dispatchimg").src = "./images/jobplan/dispatchstart.png";
			document.getElementById("dispatchaid").title = "工单启动";
		}
		if(statusId == 40){
			//启动按钮
			document.getElementById("img8").src="./images/jobplan/jobplan_20_un.png";
			//工单停止
			document.getElementById("dispatchimg").src = "./images/jobplan/dispatchstop_un.png";
			document.getElementById("dispatchaid").title = "工单停止";
			//派工灰色
			document.getElementById("img7").src = "././images/jobplan/dispatch_un.png";
			//工单暂停
			document.getElementById("dispatchimages").src = "./images/jobplan/dispatchpause.png";
			document.getElementById("dispatch").title = "工单暂停";
			//编辑按钮
			document.getElementById("img3").src = "./images/jobplan/jobplan_14_un.png";
		}
		if(statusId == 50){
			//启动按钮
			document.getElementById("img8").src="./images/jobplan/jobplan_20_un.png";
			//编辑按钮
			document.getElementById("img3").src = "./images/jobplan/jobplan_14_un.png";
			//启动按钮
			document.getElementById("dispatchimg").src = "./images/jobplan/dispatchstart_un.png";
			//工单暂停
			document.getElementById("dispatchimages").src = "./images/jobplan/dispatchpause.png";
			document.getElementById("dispatch").title = "工单暂停";
			//派工灰色
			document.getElementById("img7").src = "././images/jobplan/dispatch_un.png";
		}
		if(statusId == 60){
			//启动按钮
			document.getElementById("img8").src="./images/jobplan/jobplan_20_un.png";
			//编辑按钮
			document.getElementById("img3").src = "./images/jobplan/jobplan_14_un.png";
			//派工灰色
			document.getElementById("img7").src = "././images/jobplan/dispatch_un.png";
		}
		if(statusId == 70){
			//启动按钮
			document.getElementById("img8").src="./images/jobplan/jobplan_20_un.png";
			//编辑按钮
			document.getElementById("img3").src = "./images/jobplan/jobplan_14_un.png";
			//工单停止
			document.getElementById("dispatchimg").src = "./images/jobplan/dispatchstop.png";
			document.getElementById("dispatchaid").title = "工单停止";
			//派工灰色
			document.getElementById("img7").src = "././images/jobplan/dispatch_un.png";
		}
		if(statusId == 80){
			//启动按钮
			document.getElementById("img8").src="./images/jobplan/jobplan_20_un.png";
			//工单停止
			document.getElementById("dispatchimg").src = "./images/jobplan/dispatchstop.png";
			document.getElementById("dispatchaid").title = "工单停止";
			//工单恢复
			document.getElementById("dispatchimages").src = "./images/jobplan/dispatchrecover.png";
			document.getElementById("dispatch").title = "工单恢复";
			//编辑按钮
			document.getElementById("img3").src = "./images/jobplan/jobplan_14.png";
			//派工灰色
			document.getElementById("img7").src = "././images/jobplan/dispatch_un.png";
		}
		 
		 //从后台得到数据
		 titleField.setValue(eventRecord.data.no);//工单编号
		 locationField.setValue(eventRecord.data.partName);//工单名称
		 planNumField.setValue(eventRecord.data.planNum);//工单数量
		 editPlanId=eventRecord.data.Id;
		 rId=eventRecord.data.rId;//rId:TEquipmentInfo.equId
		 dispatchNo=eventRecord.data.no;//工单编号
		 equSerialNo=eventRecord.data.equSerialNo;//设备序列号
		 partName = eventRecord.data.partName;//零件名称
		 planNum=eventRecord.data.planNum;//计划数量
		 var jobdispatchNO = eventRecord.data.no;
		 document.getElementById("importButton").href="javascript:openUrl('./erp/production_scrap_add.faces?jobdispatchNO="+jobdispatchNO+"')";
	});
		
	 //工单编号
	titleField = new Ext.form.TextField({   
         name        : 'no',
         labelAlign: 'right',
         width:fieldWidth
    });
  
	//工单名称
    locationField = new Ext.form.TextField({
     	name        : 'Name',
        labelAlign: 'right',
        width:fieldWidth
    });
    
    //数量
    planNumField = new Ext.form.TextField({
        name        : 'planNum',
        labelAlign: 'right',
        width:fieldWidth
    });
	 
     titleField.render("tjpId");
     locationField.render("tjpName");
     planNumField.render("tjpnum");
     //加载cookie信息
     loadCookie();
});

//定义编辑类
Ext.define('App.DemoEditor',  {
    extend : "Sch.plugin.EventEditor",
    height: defineHeight,
    width:270,
    initComponent : function() {
        Ext.apply(this, { 
            timeConfig      : {
                minValue    : '00:00',
                maxValue    : '24:00'
            },
            buttonAlign     : 'center',
            saveText		:'确定',
            cancelText 		:'取消',
            
            buildButtons : function() {
                this.saveButton = new Ext.Button({
                    text        : this.L('saveText'),
                    scope       : this,
                    handler     : this.onSaveClick
                });
                this.cancelButton = new Ext.Button({
                    text        : this.L('cancelText'),
                    scope       : this,
                    handler     : this.onCancelClick
                });
                return [ this.saveButton, this.cancelButton ];
            },
            
            // panel with form fields
            fieldsPanelConfig : {
            	    xtype       : 'container',                
	                layout      : 'card',  
	                width       : defineWidth, 
	                items       : [
            	       {
            	    	EventType   : 'Appointment',
                        xtype       : 'form',
                        style:'background:rgb(220,220,220)',
                        border      : false,
                        padding     : 16,
                        html:"<div id='editdiv' style='background:rgb(220,220,220);padding-top:10px;height:130px;'>" +
                		"<table>" +
                    		"<tr>" +
                        		"<td>" +
                        			"<div id='editdiv1'>工单编号:</div>" +
                        		"</td>" +
                        		"<td>" +
                        			"<div id='tjpId'></div>" +
                        		"</td>" +
                        	"</tr>" +
                        	"<tr>" +
                        		"<td>" +
                        			"<div id='editdiv2'>工单名称:</div>" +
                        		"</td>" +
                        		"<td>" +
                        			"<div id='tjpName'></div>" +
                        		"</td>" +
                        	"</tr>" +
                        	"<tr>" +
                        		"<td>" +
                        			"<div id='editdiv3'>数量:</div>" +
                        		"</td>" +
                        		"<td>" +
                        			"<div id='tjpnum'></div>" +
                        		"</td>" +
                    		"</tr>" +
                		"</table>" +
                		
                		"<script type='text/javascript'> " +
                    		" document.getElementById('editdiv').style.MarginTop=hjb51*20/1080+'px'; " +
                    		" document.getElementById('editdiv').style.height=hjb51*250/1080+'px'; " +
                    		
                    		" document.getElementById('editdiv1').style.width=wjb51*160/1920+'px'; " +
                    		" document.getElementById('editdiv1').style.fontSize=wjb51*19/1920+'px'; " +
                    		" document.getElementById('editdiv1').style.height=hjb51*60/1080+'px'; " +
                    		" document.getElementById('editdiv1').style.MarginLeft=wjb51*72/1920+'px'; " +
                    		" document.getElementById('editdiv1').style.MarginTop='-'+hjb51*4/1080+'px'; " +
                    		" document.getElementById('tjpId').style.width=wjb51*480/1920+'px'; " +
                    		" document.getElementById('tjpId').style.MarginTop='-'+hjb51*28/1080+'px'; " +
                    		" document.getElementById('tjpId').style.MarginLeft='-'+wjb51*80/1920+'px'; " +
                    		
                    		" document.getElementById('editdiv2').style.width=wjb51*160/1920+'px'; " +
                    		" document.getElementById('editdiv2').style.fontSize=wjb51*19/1920+'px'; " +
                    		" document.getElementById('editdiv2').style.height=hjb51*60/1080+'px'; " +
                    		" document.getElementById('editdiv2').style.MarginLeft=wjb51*72/1920+'px'; " +
                    		" document.getElementById('editdiv2').style.MarginTop='-'+hjb51*15/1080+'px'; " +
                    		" document.getElementById('tjpName').style.width=wjb51*480/1920+'px'; " +
                    		" document.getElementById('tjpName').style.MarginTop='-'+hjb51*34/1080+'px'; " +
                    		" document.getElementById('tjpName').style.MarginLeft='-'+wjb51*80/1920+'px'; " +
                    		
                    		" document.getElementById('editdiv3').style.width=wjb51*160/1920+'px'; " +
                    		" document.getElementById('editdiv3').style.fontSize=wjb51*19/1920+'px'; " +
                    		" document.getElementById('editdiv3').style.height=hjb51*60/1080+'px'; " +
                    		" document.getElementById('editdiv3').style.MarginLeft=wjb51*34/1920+'px'; " +
                    		" document.getElementById('editdiv3').style.MarginTop='-'+hjb51*14/1080+'px'; " +
                    		" document.getElementById('tproId').style.width=wjb51*480/1920+'px'; " +
                    		" document.getElementById('tproId').style.MarginTop='-'+hjb51*35/1080+'px'; " +
                    		" document.getElementById('tproId').style.MarginLeft='-'+wjb51*80/1920+'px'; " +
                    	
                		"</script> " +
            		"</div>",
                     items       : [
                                       
                                 ]                    
                    }
            	]
           }
        });
        this.callParent(arguments);
        
        //点击保存按钮的操作
      this.on("beforeeventsave" ,function(editor, eventRecord, eOpts){ 
    		statusId = eventRecord.data.Status;
    		if(statusId == 30){
    			//编辑作业计划
    			if(this.eventRecord.getId() != null){
    	    		Ext.Ajax.request({
    					url:"./jobdispatch/updateBasicJobdispatch.action",
    					method:"post",
    					success:function(response){
    					    alert("工单编辑成功！");
					    	 ds.getEventStore().reload({params:{
						    	ishighlight: "true"//设置鼠标移动到图表上面，是否高亮。不过这个反应很慢。
					    	 }});
    				    },
    					failure:function(response,opts){Ext.Msg.alert("failure");},
    					params:{
    						startTime:this.startTimeField.getValue(),
    						startDate:this.startDateField.getValue(),
    						durationTime:this.durationField.getValue(),
    						id:this.eventRecord.getId(),
    						resourceId:	this.eventRecord.getResourceId(),//设备附加信息--》equ.equId==resourceId					
    						name:locationField.getValue(),
    						num:planNumField.getValue(),
    						no:titleField.getValue()
    				    }
    			    });
    			}
    		}else{
    			alert("此状态不能编辑！");
    			return false;
    		}
    }); 
    
    },

    show : function(eventRecord) {
       Ext.Ajax.request({
			url:"./jobdispatch/getMaxJobDispatchId.action",//获取工单表中当前最大的ID
			method:"post",
			success:function(response,opts){
				var eventId=parseInt(Ext.decode(response.responseText))+1;
				document.getElementById("maxId").value=eventId;
			},
			failure:function(response,opts){
				Ext.Msg.alert("请求失败!");
			},
	    });
        
       var resourceId = eventRecord.getResourceId();
       var Id = document.getElementById("maxId").value;
       this.callParent(arguments);
    }
});

App.Scheduler = {
	init: function () {
		this.scheduler = this.createScheduler();
	},
	createScheduler: function () {
		return ds;
	}
};
var flag = true;
var eventData;
function jobchaxun(){//查询过滤
	var jobState = document.getElementById("select_box_job_jobStatus_hdn").value;
	var equid = document.getElementById("select_box_job_equName_hdn").value;
	var partid = document.getElementById("select_box_job_part_hdn").value;
	var taskNum = document.getElementById("select_box_job_taskNum_hdn").value;
	var startTime = document.getElementById("myform:startInputDate").value;
	var endTime = document.getElementById("myform:endInputDate").value;
	//根据参数重新获取甘特图信息
	this.eventStore.load({
		params: {
			taskNum : taskNum,//任务编号默认为空
			jobstatus : jobState,//工单状态id
			partid : partid,
			equid:equid,
			planStime : startTime,
			planEtime : endTime
		}, 
		callback: function(records, options, success) {//根据查询结果将甘特图上的时间调整为时间数据的时间
			 ds.setStart(new Date(Date.parse(startTime.replace(/-/g, "/"))));
			 ds.setEnd(new Date(Date.parse(endTime.replace(/-/g, "/"))));
		},
		scope: this,
		add:false
	});
	//根据参数重新获取设备名称信息
	this.resourceStore.load({
		params: {
			taskNum : taskNum,//任务编号默认为空
			jobstatus : jobState,//工单状态id
			partid : partid,
			equid : equid,
			planStime : startTime,
			planEtime : endTime
		}, 
		callback: function(records, options, success) {
			 
		},
		scope: this,
		add:false
	});
}

function loadCookie(){
	var cookieName = "jobdispathcNo";
	var cookie = document.cookie.match(new RegExp(";?"+cookieName+"=([^;]*)","i")); 
	if(cookie != null){
		this.eventStore.filter(function(task){
			if(task.get('no') == cookie[1])
				return true;
		});
		delcookie(cookieName);
	}
}
function delcookie(name){  
    var exp = new Date();   
    exp.setTime(exp.getTime() - 1);  
    var cval=getCookie(name);  
    if(cval!=null) 
    	document.cookie= name + "="+cval+";expires="+exp.toGMTString();  
}  