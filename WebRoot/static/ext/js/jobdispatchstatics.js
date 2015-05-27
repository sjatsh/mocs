Ext.ns('App');
Ext.Loader.setConfig({ enabled : true, disableCaching : true });
Ext.Loader.setPath('App', '.');
Ext.Loader.setPath('Sch.plugin', '.');

var eventStore;
var start;
var end;

//控制长宽的大小
var wjb51 = 0;
var hjb51 = 0;
hjb51 = document.body.clientHeight;
wjb51 = document.body.clientWidth;
var createWidth = wjb51*1620/1620;
var createHeight = hjb51*685/1080;
var createRowHeight = hjb51*39/1080;
var createColumnWidth = wjb51*310/1620;

var jPid=document.getElementById("myform:tasknum").value;
var fsdateTime=document.getElementById("myform:startTimeInputDate").value;
var fedateTime=document.getElementById("myform:endTimeInputDate").value;

//准备
Ext.onReady(function () {
    Ext.QuickTips.init();
    //定义资源类
	Ext.define('Resource', {
		extend : 'Sch.model.Resource',
		fields: [
		         'Category','Type'
		]
	});

		//定义任务事件类
		Ext.define('Event', {
			extend : 'Sch.model.Event',
			fields: [
                 'Id','planNum','finishNum','Percentage','Status','equName','no'
			]
		});

		//定义获取资源Store函数
		
		 var resourceStore = Ext.create("Sch.data.ResourceStore", {
	            model   : 'Resource',
	            proxy   : {
	                type    : 'ajax',
	                url     : '../jobdispatch/getDevicesList.action',
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
	                url     : '../jobdispatch/getjobDispatchList.action',//获取工单信息
	                reader  : {
	                    type    : 'json',
	                    root    : 'data'
	                }
	            }
	        });
		 
		 //加载数据信息
		 resourceStore.load();
		 eventStore.load({
			params:{
				 nodeId: "8a8abc973f1dc2dc013f1dc3d7dc0001",
				 taskNum:jPid,
				 planStime:fsdateTime,
				 planEtime:fedateTime
		 }});
		
	    //定义开始时间
		start = new Date(Date.parse("Jan 1,2014"));
		end =new Date(Date.parse("May 31,2014"));
	    var editor = new Sch.plugin.SimpleEditor({ dataIndex: 'Name' });

	    //创建panel grid
	     ds = Ext.create("Sch.panel.SchedulerGrid", {
			renderTo    : 'example-container',
	        width:createWidth,
			height:createHeight,
	        rowHeight : createRowHeight,
			title: '<span class="job_manager_textstyle">工单统计管理</span>',
			resourceStore   : resourceStore,
			eventStore      : eventStore,
			viewPreset  : 'monthAndYear',
			readOnly:true,
			startDate   : start,
			endDate     : end,
		    endDate : Sch.util.Date.add(start,Sch.util.Date.DAY, 105),//结束时间往后推迟3个月
			columns: [
				{header:'<span class="job_manager_textstyle">设备名称</span>',sortable:true,width:createColumnWidth,dataIndex:'Name'}
			],
			eventBodyTemplate : new Ext.XTemplate(
				'<div class="sch-percent-allocated-bara" style="width:50%"></div>'
					+'<div class="sch-percent-allocated-bar" style="width:30%"></div>'
				+'<span id="sch-percent-allocated-text" class="sch-percent-allocated-text">{[values.Name]}</span>'
				
		    ).compile(),
        
	    /*//甘特图头部操作
			eventBodyTemplate: '<div class="sch-event-header">{headerText}</div><div class="sch-event-footer">{footerText}</div><div class="sch-percent-allocated-bar" style=" height:100% ;width:{PercentAllocated}% !important"></div><span class="sch-percent-allocated-text">{[values.PercentAllocated||0]}%</span>',

            border: true,*/
            tbar: [
                   
                 /*{
                    iconCls: 'icon-previous',
                    scale: 'medium',
                    scope : this,
                    handler: function () {
                        this.shiftPrevious();
                    }
                },
                {
                    id: 'span3',
                    enableToggle: true,
                    text: '选择日期',
                    toggleGroup: 'span',
                    scope : this,
                    menu :     Ext.create('Ext.menu.DatePicker', {
                        handler: function(dp, date){
                            var D = Ext.Date;
                            ds.setTimeSpan(D.add(date, D.DAY, 0), D.add(date, D.DAY, 30));
                        },
                        scope : this
                    })
                }*/
		    ],

			//鼠标移动提示
		     tooltipTpl: new Ext.XTemplate(
		        '<dl class="eventTip">',
		            '<dt class="icon-clock">开始时间:{[Ext.Date.format(values.StartDate, "Y-m-d G:i")]} </dt>',
		            '<dt class="icon-clock">结束时间:{[Ext.Date.format(values.EndDate, "Y-m-d G:i")]} </dt>',
		            '<dt class="icon-task">事件名称:{Name}</dt>',
		            '<dt class="icon-task">计划数量{planNum} 个</dt>',
	                '<dt class="icon-task">完成数量{finishNum} 个</dt>',
	                '<dt class="icon-task">状态{Status}</dt>',
		        '</dl>'
		    ).compile(),
		  //鼠标双击编辑
            plugins: [
               // this.editor = Ext.create("App.DemoEditor", {
                    // Extra config goes here
                //}),
                
                Ext.create('Ext.grid.plugin.CellEditing', {
                    clicksToEdit: 1
                })            
            ],
          //创建新任务
           // onEventCreated : function(newEventRecord) {
               // newEventRecord.set('Name', '新建任务');
              //  newEventRecord.set('Cls', 'ext_chuangjian');
               // newEventRecord.set('Id', document.getElementById("maxId").value);
           // }
	     
	  
      });
	     ds.scrollToDate(new Date(),true);
	     //右键菜单
	     /*ds.on("eventcontextmenu",function(scheduler, eventRecord, e, eOpts ){
	    	    var dispatchid=eventRecord.data.Id;
	    	    alert(dispatchid);
	    	    e.stopEvent();
		        if (!scheduler.ctx) {
		        	scheduler.ctx = new Ext.menu.Menu({
		                items: [{
		                    text: '删除事件',
		                    iconCls: 'icon-delete',
		                    handler : function() {		               
		                    	Ext.Ajax.request({
									url:"./jobdispatch/delete.action",
									method:"post",
				                    params:{
										id:dispatchid
								    },
								    success:function(response){
										var obj=Ext.decode(response.responseText);
							            ds.getEventStore().reload();
										//scheduler.refreshKeepingResourceScroll();
								    },
									failure:function(response,opts){
							            Ext.Msg.alert("操作失败!");
									},
							    });
		                    	scheduler.eventStore.remove(scheduler.ctx.rec);
		                    }
		                },
						{
		                    text: '拆分事件',
		                    iconCls: 'icon-split',
		                    handler : function(){
								var record = scheduler.ctx.rec;
								var clone = record.copy();
								var nbrMinutes = Sch.util.Date.getDurationInMinutes(record.getStartDate(), record.getEndDate()); 
								record.setEndDate(Ext.Date.add(record.getStartDate(), Ext.Date.MINUTE, nbrMinutes/2));
								clone.setStartDate(record.getEndDate());
                                alert(eventRecord.data.Id);
								Ext.Ajax.request({
									url:"./jobdispatch/split.action",
									method:"post",
									success:function(response){
										var obj=Ext.decode(response.responseText);
										ds.getEventStore().reload();
										//alert(obj.endtime);
										//Ext.getCmp("detailTel").setValue(obj.endtime);
										//alert("obj");
										//clickEvent(obj);
									},
									failure:function(response,opts){Ext.Msg.alert("failure")},
									params:{
										m:"",
										id: eventRecord.data.Id,
										time: record.getEndDate()
								    }
							    });
								scheduler.eventStore.add(clone);
							}
		                }
					]
		            });
		        	
		        }
		        //scheduler.ctx.rec = eventRecord;
		        scheduler.ctx.showAt(e.getXY());
	     });
	     
	     
	     
	     //作业计划拖动
	     ds.on("eventdrop",function(scheduler, records, isCopy, eOpts ){
	    	 var record = records[0];
	    	 Ext.Ajax.request({
					url:"./jobdispatch/update.action",
					method:"post",
					dataType : 'json',  
					success:function(response){
											
					},
					failure:function(response,opts){
						Ext.Msg.alert("操作失败")
					},
					params:{
						m:"",
						startTime:record.data.StartDate,
						endTime:record.data.EndDate,
						id:record.data.Id,
						resourceId:record.data.ResourceId
				    }
			    });
	     });
	     
	     //改变作业计划长度
	      ds.on("eventresizeend",function(scheduler, record, eOpts){
	    	  Ext.Ajax.request({
					url:"./jobdispatch/update.action",
					method:"post",
					dataType : 'json',  
					success:function(response){			
					},
					failure:function(response,opts){
						Ext.Msg.alert("failure")
					},
					params:{
						m:"",
						startTime:record.data.StartDate,
						endTime:record.data.EndDate,
						id:record.data.Id
				    }
			    });
	     });
	      
	     //新增作业计划方法
	     ds.on("beforeeventadd",function(scheduler, newEventRecord,eOpts ){
	    	//alert("a");
	     });
	     
	     ds.on("afterdragcreate",function(scheduler, eOpts){
	    	
	     });*/

	     //点击具体的作业计划对应后台操作
		 ds.on("eventclick",function(scheduler, eventRecord, e, eOpts ){
			  Ext.Ajax.request({
				url:"../jobdispatch/getJobDispatchsInfoOne.action",
				method:"post",
				success:function(response){
					var obj=Ext.decode(response.responseText);
					clickEvent(obj);
				},
				failure:function(response,opts){Ext.Msg.alert("failure");},
				params:{
					jobdispatchId:eventRecord.data.Id,
			    }
		    });
			
	    });
		
	
	
	/* var mainPanel = new Ext.Panel({
	        title: "i5WIS",
	        width: 1000,
	        //layout:"border",
	        //style: "margin-left:30px;margin-top:20px",
	        autoHeight: true,
	        border: true,
	        items: [subTopPanel,ds],
	        renderTo: "example-container"
	    });*/
	 

	
});


//-----------------------------后期修改Event类

/*//定义资源类
Ext.define('Resource', {
   extend : 'Sch.model.Resource',
   idProperty : 'resourceId',
   fields: [
       'resourceId',
       'name',
       'cls',
       'and',
    ]
});

//定义任务事件类
Ext.define('Event', {
    extend : 'Sch.model.Event',
    nameField : 'Name',
    fields: [
        'Name',
        'Cls',
        'StartDate',
		'EndDate',
        'ResourceId'
     ]
});*/

function clickEvent(obj) {
	var jdNo;
	var equName;
	var status;
	var planNum;
	var finishNum;
	var planStime;
	var planEtime;

	if(obj.data[0].no==null)
    {
		jdNo="";
    }
	else
    {
		jdNo=obj.data[0].no;
	}
	if(obj.data[0].equName==null)
    {
		equName="";
    }
	else
    {
		equName=obj.data[0].equName;
	}
	if(obj.data[0].Status==null)
    {
		status="";
    }
	else
    {
		switch(obj.data[0].Status)
		{
		case 30:
		  status="新建";
		  break;
		case 40:
		case 50:
		  status="上线/加工";
		  break;
		case 60:
		  status="结束";
		  break;
		case 70:
		  status="完工";
		  break;
		default:
		}
	}
	if(obj.data[0].planNum==null)
    {
		planNum=0;
    }
	else
    {
		planNum=obj.data[0].planNum;
	}
	if(obj.data[0].finishNum==null)
    {
		finishNum=0;
    }
	else
    {
		finishNum=obj.data[0].finishNum;
	}
	if(obj.data[0].StartDate==null)
    {
		planStime="";
    }
	else
    {
		planStime=obj.data[0].StartDate;
	}
	if(obj.data[0].EndDate==null)
    {
		planEtime="";
    }
	else
    {
		planEtime=obj.data[0].EndDate;
	}
	var jobDispatchDetail="<div id='type_detail' >" +
		"<table>" +
			"<tr>" +
				"<td id='type_detail_td2' align='center'>"+jdNo+"</td>" +
				"<td id='type_detail_td3' align='center'>"+"</td>"+
				"<td id='type_detail_td4' align='center'>"+equName+"</td>"+
				"<td id='type_detail_td5' align='center'>"+status+"</td>" +
				"<td id='type_detail_td6' align='center'>"+planNum+"</td>"+
				"<td id='type_detail_td7' align='center'>"+finishNum+"</td>"+
				"<td id='type_detail_td8' align='center'>"+planStime+"</td>"+
				"<td id='type_detail_td9' align='center'>"+planEtime+"</td>"+
			"</tr>";
	jobDispatchDetail=jobDispatchDetail+"</table></div>";
	document.getElementById("type_detail").innerHTML=jobDispatchDetail;
	
	document.getElementById("type_detail").style.fontSize=wjb51*16/1620+"px";
	document.getElementById("type_detail").style.width=wjb51*1264/1620+"px";
	document.getElementById("type_detail").style.marginLeft="-"+wjb51*11/1620+"px";
	
	document.getElementById("type_detail_td2").style.width=wjb51*10/100+"px";
	document.getElementById("type_detail_td3").style.width=wjb51*12/100+"px";
	document.getElementById("type_detail_td4").style.width=wjb51*10/100+"px";
	document.getElementById("type_detail_td5").style.width=wjb51*13/100+"px";
	document.getElementById("type_detail_td6").style.width=wjb51*12/100+"px";
	document.getElementById("type_detail_td7").style.width=wjb51*14/100+"px";
	document.getElementById("type_detail_td8").style.width=wjb51*10/100+"px";
	document.getElementById("type_detail_td9").style.width=wjb51*10/100+"px";
	
	document.getElementById("type_detail_td2").style.fontSize=wjb51*16/1620+"px";
	document.getElementById("type_detail_td3").style.fontSize=wjb51*16/1620+"px";
	document.getElementById("type_detail_td4").style.fontSize=wjb51*16/1620+"px";
	document.getElementById("type_detail_td5").style.fontSize=wjb51*16/1620+"px";
	document.getElementById("type_detail_td6").style.fontSize=wjb51*16/1620+"px";
	document.getElementById("type_detail_td7").style.fontSize=wjb51*16/1620+"px";
	document.getElementById("type_detail_td8").style.fontSize=wjb51*16/1620+"px";
	document.getElementById("type_detail_td9").style.fontSize=wjb51*16/1620+"px";
}

//定义编辑类
/*Ext.define('App.DemoEditor',  {
    
    extend : "Sch.plugin.EventEditor",
    height          : 260,
    width           : 350, 
    initComponent : function() {
        Ext.apply(this, { 
            timeConfig      : {
                minValue    : '00:00',
                maxValue    : '24:00'
            },
            buttonAlign     : 'center',
            saveText		:'确定',
            deleteText		:'删除',
            cancelText 		:'取消',
         
            // panel with form fields
            fieldsPanelConfig : {
                xtype       : 'container',                
                layout      : 'card',                  
                items       : [
                    // form for "Meeting" EventType
                    {
                        EventType   : 'Meeting',
                        xtype       : 'form',
                        layout      : 'hbox',
                        style       : 'background:#fff',
                        cls         : 'editorpanel',
                        border      : false,
                        items       : [
                            {
                                padding     : 10,
                                style       : 'background:#fff',
                                border      : false,
                                flex        : 2,
                                layout      : 'anchor',
                                defaults    : {
                                    anchor  : '100%'
                                },
                                items       : [                                                                                                                               
                                    this.titleField = new Ext.form.TextField({   
                                        labelAlign  : 'top',
                                        name        : 'Title',
                                        fieldLabel  : 'NO:'
                                    }),
                            
                                    this.locationField = new Ext.form.TextField({
                                        labelAlign  : 'top',
                                        name        : 'Location',
                                        fieldLabel  : '名称:'
                                    })
                                ]
                            }
                        ]                    
                    },
                    
                    {
                        EventType   : 'Appointment',
                        xtype       : 'form',
                        style       : 'background:#fff',
                        cls         : 'editorpanel',
                        border      : false,
                        padding     : 10,
                        layout      : {
                            type    : 'vbox',
                            align   : 'stretch'
                        },
                        
                        items       : [
                            new Ext.form.TextField({
                                labelAlign  : 'top',
                                name        : 'Location',
                                fieldLabel  : 'Location'
                            }),                        
                            {
                                xtype       : 'combo',
                                labelAlign  : 'top',
                                store       : [ "Dental", "Medical" ],
                                listConfig  : { cls : 'sch-event-editor-ignore-click' },
                                name        : 'Type',
                                fieldLabel  : 'Type'
                            }
                        ]                    
                    }
                ]
            }
        });

        this.on('expand', this.titleField.focus, this.titleField);
        this.callParent(arguments);
        
        //点击保存按钮的操作
        this.on("beforeeventsave" ,function(editor, eventRecord, eOpts){
        	alert(this.eventRecord.getId());
        	if(this.eventRecord.getId()!=null){
        		//编辑作业计划
        		Ext.Ajax.request({
    				url:"./jobdispatch/save.action",
    				method:"post",
    				success:function(response){
    					var obj=Ext.decode(response.responseText);
    			    },
    				failure:function(response,opts){Ext.Msg.alert("failure")},
    				params:{
    					m:" ",
    					startTime:this.startTimeField.getValue(),
    					startDate:this.startDateField.getValue(),
    					durationTime:this.durationField.getValue(),
    					id	: this.eventRecord.getId(),
    					resourceId:	this.eventRecord.getResourceId(),    					
    					name	: this.locationField.getValue(),
    					no	: this.titleField.getValue()			
    			    }
    		    });
        	}else{
        		this.eventRecord.setName(this.locationField.getValue());
        		//添加作业计划
        		Ext.Ajax.request({
    				url:"./jobdispatch/save.action",
    				method:"post",
    				success:function(response){
    					//var obj=Ext.decode(response.responseText);
    					alert("succuess");
    					ds.getEventStore().reload();
    			    },
    				failure:function(response,opts){Ext.Msg.alert("failure")},
    				params:{
    					m:" ",
    					startTime:this.startTimeField.getValue(),
    					startDate:this.startDateField.getValue(),
    					durationTime:this.durationField.getValue(),
    					id	: document.getElementById("maxId").value,
    					resourceId:	this.eventRecord.getResourceId(),    					
    					name	: this.locationField.getValue(),
    					no	: this.titleField.getValue()				
    			    }
    		    });
        	}
        
        }); 
    },

    show : function(eventRecord) {
       Ext.Ajax.request({
			url:"./jobdispatch/getMaxJobDispatchId.action",
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
});*/

App.Scheduler = {
	init: function () {
		this.scheduler = this.createScheduler();
	},
	createScheduler: function () {
		return ds;
	}
};

