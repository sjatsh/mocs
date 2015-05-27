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

//准备
Ext.onReady(function () {
	Ext.QuickTips.init();
	//App.Scheduler.init();
//	 var subTopPanel = new Ext.Panel({
//	        id:"subP",
//	        width: 1000,
//	        //layout:"border",
//	        //style: "margin-left:30px;margin-top:20px",
//	        autoHeight: true,
//	        border: true,
//	        items: [],
//	        autoLoad:{url:"dd.jsp"}
//	       
//	    });
	
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
			           'No','Name','planId','finishNum','planNum','Priority','Percentage','Status','jobPlanfinisNum'
		            ]
		});

		//定义获取资源Store函数
		 var resourceStore = Ext.create("Sch.data.ResourceStore", {
	            model   : 'Resource',
	            proxy   : {
	                type    : 'ajax',
	                url     : './jobplan/getjobplanName.action',//获取产品名称信息
	                reader  : {
	                    type    : 'json',
	                    root    : 'data'
	                }
	            },
	            //定义排序列名跟排序方式
	            sortInfo: { field: 'Id', direction: "ASC" }//方向    升序
	        });
		 
		//定义获取事件Store函数
		 var eventStore = Ext.create("Sch.data.EventStore", {
	            model   : 'Event',
	            proxy   : {
	                type    : 'ajax',
	                url     : './jobplan/getjobEvent.action',//获取作业计划信息
	                reader  : {
	                    type    : 'json',
	                    root    : 'data'
	                }
	            }
			 
			/* model :'Event',
	            data : [{"Name":"莲花I-2013年7月第一批","Status":70,"EndDate":"2013-08-10 10:36:47","StartDate":"2013-08-01 10:36:39","ResourceId":100001,"Cls":"ext_wancheng","Id":1,"planId":100098,"finishNum":400,"planNum":400, "PercentAllocated" :60}]
			 */
			});
		 
		 //加载数据信息
		 resourceStore.load();
		 eventStore.load();
		
	    //定义开始时间
	    start = new Date(Date.parse("Jan 1,2014"));
	    end =new Date(Date.parse("May 31,2014")); //Sch.util.Date.add(start,Sch.util.Date.DAY, 730);
		//var end = new Date(2013, 5, 5, 24);
	   // var editor = new Sch.plugin.SimpleEditor({ dataIndex: 'Name' });
	    
	   //创建panel grid
	  //创建panel grid
	     ds = Ext.create("Sch.panel.SchedulerGrid", {
			renderTo    : 'example-container',
			//height : ExampleDefaults.height,
	        //width : ExampleDefaults.width,
			width:createWidth,
			height:createHeight,
	        rowHeight : createRowHeight,
			title: '<span class="job_manager_textstyle">作业计划队列管理</span>',
			resourceStore   : resourceStore,
			eventStore      : eventStore,
			constrainDragToResource:true,
			viewPreset  : 'weekAndDay',//月和年刻度
			startDate   : start,
			endDate     : end,
		    columns: [
				{ header : '<span class="job_manager_textstyle">产品名称</span>', //sortable:true,
width:createColumnWidth, dataIndex : 'Name' }
			],
			//作业计划的样式
			eventBodyTemplate : new Ext.XTemplate(
	         '<div  class="sch-percent-allocated-bar" style="width:{Percentage}%"></div><span id="sch-percent-allocated-text" class="sch-percent-allocated-text" >{[values.Name]}</span>'
	        ).compile(),
			
			/*//甘特图头部操作
			eventBodyTemplate: '<div class="sch-event-header">{headerText}</div><div class="sch-event-footer">{footerText}</div><div class="sch-percent-allocated-bar" style=" height:100% ;width:{PercentAllocated}% !important"></div><span class="sch-percent-allocated-text">{[values.PercentAllocated||0]}%</span>',

           border: true,
           tbar: [
               {
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
               }
		    ],
*/
			//鼠标移动提示
		     tooltipTpl: new Ext.XTemplate(
		        '<dl class="eventTip">',
		            '<dt class="icon-clock">开始时间:{[Ext.Date.format(values.StartDate, "Y-m-d G:i")]} </dt>',
		            '<dt class="icon-clock">结束时间:{[Ext.Date.format(values.EndDate, "Y-m-d G:i")]} </dt>',
		            '<dt class="icon-task">事件名称:{Name}</dt>',
		            '<dt class="connecting">计划数量{planNum} 个</dt>',
	                '<dt class="blocking">完成数量{finishNum} 个</dt>',
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
              //newEventRecord.set('Name',Ext.getCmp('newTaskName').getValue());
              //newEventRecord.set('Cls', 'ext_chuangjian');
              //newEventRecord.set('Id', document.getElementById("maxId").value);
           }
	     
	    });
	     
	    //ds.switchViewPreset("monthAndYear",new Date(),end);
         ds.scrollToDate(new Date(),true);
	     //右键菜单
	    ds.on("eventcontextmenu",function(scheduler, eventRecord, e, eOpts ){
	    	    e.stopEvent();
	    	    //alert(eventRecord.data.Id);
	    	    /*  if (!scheduler.ctx) {
		       	scheduler.ctx = new Ext.menu.Menu({
		                items: [{
		                    text: '删除事件',
		                    iconCls: 'icon-delete',
		                    handler : function() {
		                    	Ext.Msg.confirm("确认删除","确认要删除所选记录吗？",function(btn){
		                    		if(btn=="yes")
		                    		{
		                    			Ext.Ajax.request({
											url:"/mocs/jobplan/delete.action",
											method:"post",
											success:function(response){
												//var obj=Ext.decode(response.responseText);
										             Ext.Msg.alert("提示", "作业计划删除成功!", function () {
						    						      ds.getEventStore().reload();
							                         });
										    },
											failure:function(response,opts){Ext.Msg.alert("failure")},
											params:{
												m:"",
												id:eventRecord.data.Id
										    },
											failure:function(response,opts){
												Ext.Msg.alert("操作失败!");
											},
									    });
		                    			scheduler.eventStore.remove(scheduler.ctx.rec);
		                    	     }
		                    	});
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
								//eventRecord.setId();
                                alert(eventRecord.getId());
								Ext.Ajax.request({
									url:"/mocs/jobplan/split.action",
									method:"post",
									success:function(response){
										//var obj=Ext.decode(response.responseText);
										  Ext.Msg.alert("提示", "作业计划拆分成功!", function () {
				    						  ds.getEventStore().reload();
					                         });
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
		        scheduler.ctx.rec = eventRecord;
		        scheduler.ctx.showAt(e.getXY());*/
	     });
	     
	     
	     
	     //作业计划拖动
	     ds.on("eventdrop",function(scheduler, records, isCopy, eOpts ){
	    	 var record = records[0];
	    	 Ext.Ajax.request({
					url:"./jobplan/update.action",
					method:"post",
					dataType : 'json',  
					success:function(response){
						Ext.Msg.alert("提示", "作业计划拖动成功!", function () {
							ds.getEventStore().reload();			
	                         });
				    },
					failure:function(response,opts){
						Ext.Msg.alert("操作失败");
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
	      
	     //点击具体的作业计划对应后台操作
		 ds.on("eventclick",function(scheduler,eventRecord,e,eOpts){
			statusId=eventRecord.data.Status;
			 if(statusId==10)
			 {
				 document.getElementById("jobplanimg").src="./images/jobplan/dispatchstart.png";
                 document.getElementById("jobplanaid").title="作业计划启动";//与xhtml有关
				 
			 }
			 else if(statusId==20)
		     {
                 document.getElementById("jobplanimg").src="./images/jobplan/dispatchstop.png";
	             document.getElementById("jobplanaid").title="作业计划停止";
				 
		     }
			 Ext.Ajax.request({
				url:"./jobplan/jobDetail.action",//获取作业计划详细信息
				method:"post",
				success:function(response){
					var obj=Ext.decode(response.responseText);
					clickEvent(obj);	
					editPlanId=obj.jobPlanId;
				},
				failure:function(response,opts){Ext.Msg.alert("failure");},
				params:{
					m:"",
					p:eventRecord.data.Name,
			    }
		    });
			jobName=eventRecord.data.Name;
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
	 
		  titleField = new Ext.form.TextField({   
			    name        : 'No',
		        labelAlign: 'right',
			    labelStyle :fieldStyle,
			    width:fieldWidth
		  });

		locationField = new Ext.form.TextField({
				name        : 'Name',
			    labelAlign: 'right',
			    width:fieldWidth
			});

		 //从属生产计划名称
	    pId=new Ext.form.ComboBox({
				 id:"pId",
			     name:"planId",
			     listConfig  : { cls : 'sch-event-editor-ignore-click' },
			     displayField: 'displayfield',
			     valueField: 'valuefield',
			     mode: 'local',
			     hiddenName: 'pname',
			     width:fieldWidth,
			     //forceSelection: true,
			     //triggerAction: 'all',
			     labelAlign: 'right',
			     emptyText: '选择下拉值...',
			     listeners:{
					         scope: this,
					         'select':function(cb,rds,opt){
					        	   document.getElementById("hplanId").value=rds[0].get("valuefield");
					                  }
					        },
					   //selectOnFocus: true,
			     store:new Ext.data.Store({
			  	  // singleton:true,
			  	   proxy:{
			  		   type:'ajax',
			  		   url:'./jobplan/getSubordinatePlanInfoMap.action',
			  		   actionMethods:'post',
			  		   reader:{
			  			   type:'json',
			  			   root:'root'
			  		   }
			  	   },
			      fields:[{name:'displayfield'},{name:'valuefield'}],
				        autoLoad:true
				       
			     })
			});


		   planNumField = new Ext.form.TextField({
			    name        : 'planNum',
			    labelAlign: 'right',
			    width:fieldWidth
			});

		   //优先级
		   priId=new Ext.form.ComboBox({
				  id:"priorityId",
			      name:"Priority",
			      listConfig  : { cls : 'sch-event-editor-ignore-click' },
			      labelAlign: 'right',
			      displayField: 'displayfield',
			      valueField: 'displayfield',
			      mode: 'local',
			      width:fieldWidth,
			      hiddenName: 'pri',
			      //forceSelection: true,
			      //triggerAction: 'all',
			      emptyText: '选择下拉值...',
			      listeners:{
					         scope: this,
					         'select':function(cb,rds,opt){
					        	      document.getElementById("hpriorityId").value=rds[0].get("displayfield");
					                  }
					        },
			      store:new Ext.data.Store({
			    	  // singleton:true,
			   	  proxy:{
			   		   type:'ajax',
			   		   url:'./jobplan/getPriority.action',
			   		   actionMethods:'post',
			   		   reader:{
			   			   type:'json',
			   			   root:'root'
			   		   }
			       },
			       fields:[{name:'displayfield'}],
				   autoLoad:true
			      })
			 });
			
			    titleField.render("tjpId");
		        locationField.render("tjpName");
		        pId.render("tproId");
		        planNumField.render("tjpnum");
		        priId.render("tjplevel");
	
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
	var jobPlanId;
	var starttime;
	var process;
	var planId;
	var jobPlanNum;
	var priority;
	var endtime;
	var partId;
	var finishDate;
	var jobPlanName;
	var jobPlanFinishNum;
	var partName;
	
	if(obj.jobPlanId==null)
    {
		jobPlanId="";
    }
	else
    {
		jobPlanId=obj.jobPlanId;
	}
	if(obj.starttime==null)
    {
		starttime="";
    }
	else
    {
		starttime=obj.starttime;
	}
   /* if(obj.process==null)
	{
	   process=0;
	}
    else
    {
       process=obj.process;
    }*/
	if(obj.planId==null)
	{
	   planId="";
	}
	else
	{
	   planId=obj.planId;
	}
	if(obj.jobPlanNum==null)
	{
	   jobPlanNum="";
	   process=0;
	}
	else
	{
		jobPlanNum=obj.jobPlanNum;
		if(obj.jobPlanfinisNum==null)
		{
			process=0;
		}
		else
	    {
		   process=Math.round((obj.jobPlanfinisNum/jobPlanNum)*100);  
		}
		
	}
	if(obj.priority==null)
	{
	    priority="";
	}
	else
    {
	    priority=obj.priority;
	}
	if(obj.endtime==null)
	{
	    endtime="";
	}
	else
	{
		endtime=obj.endtime;
	}
	if(obj.partId==null)
	{
		partId="";
	}
	else
	{
		partId=obj.partId;
	}
	if(obj.finishDate==null)
	{
		finishDate="";
	}
	else
	{
		finishDate=obj.finishDate;
	}
	if(obj.jobPlanName==null)
	{
		jobPlanName="";
	}
	else
	{
		jobPlanName=obj.jobPlanName;
	}
	if(obj.jobPlanfinisNum==null)
	{
		jobPlanFinishNum="";
	}
	else
    {
		jobPlanFinishNum=obj.jobPlanfinisNum;
    }
	if(obj.partName==null)
	{
		partName="";
	}
	else
    {
		partName=obj.partName;
    }
	
	if(document.getElementById("tab1").style.display=="block")
    {
		
		var planIdval;  //字符串拼接
	    if(planId.length>6){
	    	planIdval = planId.substring(0, 6)+'...';
	    }else{
	    	planIdval = planId;
	    }
		
	document.getElementById("jobplan_top_right").innerHTML=""	  
    +" <hr />"
    +" <div id='topshowone' >"
    +" 	<ul>"
    +"     	<li id='topshowone1' style='float:left;'>作业计划ID : "+jobPlanId+"</li>"
    +"         <li id='topshowone2' style='float:left;'>开始时间 : "+starttime+"</li>"
    +"         <li id='topshowone3' style='float:left;'>结束时间 ："+endtime+"</li>"
    +"         <li id='topshowone4' style='float:left;'>生产计划ID :"+planIdval+"</li>"
    +"     </ul>"
    +" </div>"
    +" <div id='topshowtwo' >"
    +"	<ul>"
    +"     	<li id='topshowtwo1' style='float:left;'>计划数量 :"+jobPlanNum+"</li>"
    +"         <li id='topshowtwo2' style='float:left;'>优先级 ："+priority+"</li>"
    +"         <li id='topshowtwo3' style='float:left;'>进度 ："+process+"%</li>"
    +"         <li id='topshowtwo4' style='float:left;'></li>"
    +"         <li id='topshowtwo5' style='float:left;'>零件类型ID :"+partId+"</li>"
    +"     </ul>"
    +" </div>"
    +"  <div id='topshowthree' >"
    +" 	<ul>"
    +"<li id='topshowthree1' style='float:left;'>交货日期 ："+finishDate+"</li>"
    +"<li id='topshowthree2' style='float:left;'>作业计划名称 ："+jobPlanName+"</li>"
    +"  </ul>"
    +" </div>";
	
	document.getElementById("topshowone").style.width=wjb51*1536/1920+"px";
	document.getElementById("topshowone").style.height=hjb51*60/1080+"px";
	document.getElementById("topshowone").style.fontSize=wjb51*30/1920+"px";
	for(var i=1;5>i;i++){
		document.getElementById("topshowone"+i).style.width=wjb51*384/1920+"px";
		//document.getElementById("topshowone"+i).style.cssFloat='left';
	}
	document.getElementById("topshowtwo").style.width=wjb51*1536/1920+"px";
	document.getElementById("topshowtwo").style.height=hjb51*60/1080+"px";
	document.getElementById("topshowtwo").style.fontSize=wjb51*30/1920+"px";
	for(var i=1;6>i;i++){
		document.getElementById("topshowtwo"+i).style.width=wjb51*384/1920+"px";
		//document.getElementById("topshowtwo"+i).style.cssFloat='left';
	}
	document.getElementById("topshowthree").style.width=wjb51*1536/1920+"px";
	document.getElementById("topshowthree").style.height=hjb51*60/1080+"px";
	document.getElementById("topshowthree").style.fontSize=wjb51*30/1920+"px";
	document.getElementById("topshowthree1").style.width=wjb51*384/1920+"px";
	//document.getElementById("topshowthree1").style.cssFloat='left';
	document.getElementById("topshowthree2").style.width=wjb51*900/1920+"px";
	//document.getElementById("topshowthree2").style.cssFloat='left';
	
	
		}
	else if(document.getElementById("tab1").style.display=="none")
	{
		
		//var jobPlanDetail="<div id='type_detail' style='font-size:20px;'><table><tr><td width='380' align='center'><div style='display:block;margin-left:10px;'>"+jobPlanName+"</div></td><td width='150' align='center'><div style='display:block;margin-left:140px;'>"+partId+"</div></td><td width='145' align='center'><div style='display:block;margin-left:155px;'>"+jobPlanNum+"</div></td><td width='145' align='center'><div style='display:block;margin-left:158px;'>"+jobPlanFinishNum+"</div></td><td width='30' align='center'><div style='display:block;margin-left:156px;'>"+process+"%</div></td><td width='120' align='center'><div style='display:block;margin-left:132px;'>"+starttime+"</div></td><td width='180' align='center'><div style='display:block;margin-left:122px;'>"+endtime+"<div></td></tr>"
		//jobPlanDetail=jobPlanDetail+"</table></div>";
		
		var jobPlanDetail="<div id='type_detail' >" +
				"<table><tr><td id='type_detail_td1'  align='center'>"+jobPlanName+"</td><td id='type_detail_td2'  align='center'>"+partName+"</td><td id='type_detail_td3'  align='center'>"+jobPlanNum+"</td><td id='type_detail_td4'  align='center'>"+jobPlanFinishNum+"</td><td id='type_detail_td5'  align='center'>"+process+"%</td><td id='type_detail_td6'  align='center'>"+starttime+"</div></td><td id='type_detail_td7'  align='left'>"+endtime+"</td></tr>";
		jobPlanDetail=jobPlanDetail+"</table></div>";
		document.getElementById("type_detail").innerHTML=jobPlanDetail; 
		
		document.getElementById("type_detail").style.fontSize=wjb51*17/1920+"px";
		document.getElementById("type_detail").style.width=wjb51*80/100+"px";
		
		document.getElementById("type_detail_td1").style.width=wjb51*23/100+"px";
		document.getElementById("type_detail_td2").style.width=wjb51*13/100+"px";
		document.getElementById("type_detail_td3").style.width=wjb51*12/100+"px";
		document.getElementById("type_detail_td4").style.width=wjb51*12/100+"px";
		document.getElementById("type_detail_td5").style.width=wjb51*10/100+"px";
		document.getElementById("type_detail_td6").style.width=wjb51*17/100+"px";
		document.getElementById("type_detail_td7").style.width=wjb51*13/100+"px";
	}
}

/*var cs = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
 	   url: '/mocs/jobplan/getSubordinatePlanInfoMap.action',
        method: 'POST',
        listeners: {
            'beforeload': function (proxy, params) {
                params.m = "";
                params.p = "";
            }
        }
    }), // 要去获取的url,
    reader: new Ext.data.JsonReader({
        root: 'root',
        totalProperty: 'total',
        fields: [ {
                     name: 'displayfield'
                 },
              	{
              	    name: 'valuefield'
              	}
               ]
    })
   
});

cs.load();*/




//定义编辑类
Ext.define('App.DemoEditor',  {
	extend : "Sch.plugin.EventEditor",
	    height          : defineHeight,
	    width           : defineWidth, 
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
		                width       : defineWidth,
		                items       : [
	            	       {
	                        EventType   : 'Appointment',
	                        xtype       : 'form',
	                        style:'background:rgb(220,220,220)',
	                       // style       : 'background:#fff;',
	                       // cls         :  'jobplanA',
	                       // itemCls     :  'jobplanA' ,
	                        border      : false,
	                        padding     : 16,
	                        //html:"<div style='background:rgb(191,191,191);padding:10px;border-radius:15px;height:250px;'><table><tr><td><div style='width:160px;font-size:19px;height:60px;margin-top:8px;margin-left:15px;'>作业计划编号:</div></td><td><div id='tjpId' style='width:480px;margin-top:-22px;'></div></td></tr><tr><td><div style='width:160px;font-size:19px;height:60px;margin-left:15px;margin-top:-15px;'>作业计划名称:</div></td><td><div id='tjpName' style='width:480px;margin-top:-34px;'></div></td></tr><tr><td><div style='width:160px;font-size:19px;height:60px;margin-left:15px;margin-top:-14px;'>从属生产计划名称:</div></td><td><div id='tproId' style='width:480px;margin-top:-35px;'></div></td></tr><tr><td><div style='width:160px;font-size:19px;height:60px;margin-left:15px;margin-top:-14px;'>计划数量:</div></td><td><div id='tjpnum' style='width:480px;margin-top:-32px;'></div></td></tr><tr><td><div style='width:160px;font-size:19px;height:60px;margin-left:15px;margin-top:-12px;'>优先级:</div></td><td><div id='tjplevel' style='width:480px;margin-top:-34px;'></div></td></tr></table></div>",
	                        html:"<div id='editdiv' style='background:rgb(220,220,220);padding-top:10px;height:250px;'>" +
		                        		"<table>" +
			                        		"<tr>" +
				                        		"<td>" +
				                        			"<div id='editdiv1' >作业计划编号:</div>" +
				                        		"</td>" +
				                        		"<td>" +
				                        			"<div id='tjpId' ></div>" +
				                        		"</td>" +
			                        		"</tr>" +
			                        		"<tr>" +
				                        		"<td>" +
				                        			"<div id='editdiv2' >作业计划名称:</div>" +
				                        		"</td>" +
				                        		"<td>" +
				                        			"<div id='tjpName' ></div>" +
				                        		"</td>" +
			                        		"</tr>" +
			                        		"<tr>" +
				                        		"<td>" +
				                        			"<div id='editdiv3' >从属生产计划名称:</div>" +
				                        		"</td>" +
				                        		"<td>" +
				                        			"<div id='tproId' ></div>" +
				                        		"</td>" +
			                        		"</tr>" +
			                        		"<tr>" +
				                        		"<td>" +
				                        			"<div id='editdiv4' >计划数量:</div>" +
				                        		"</td>" +
				                        		"<td>" +
				                        			"<div id='tjpnum' ></div>" +
				                        		"</td>" +
			                        		"</tr>" +
			                        		"<tr>" +
				                        		"<td>" +
				                        			"<div id='editdiv5' >优先级:</div>" +
				                        		"</td>" +
				                        		"<td>" +
				                        			"<div id='tjplevel' ></div>" +
				                        		"</td>" +
			                        		"</tr>" +
		                        		"</table>" +
		                        		"<script type='text/javascript'> " +
		                        		" document.getElementById('editdiv').style.MarginTop=hjb51*10/1080+'px'; " +
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
		                        		
		                        		" document.getElementById('editdiv4').style.width=wjb51*160/1920+'px'; " +
		                        		" document.getElementById('editdiv4').style.fontSize=wjb51*19/1920+'px'; " +
		                        		" document.getElementById('editdiv4').style.height=hjb51*60/1080+'px'; " +
		                        		" document.getElementById('editdiv4').style.MarginLeft=wjb51*108/1920+'px'; " +
		                        		" document.getElementById('editdiv4').style.MarginTop='-'+hjb51*14/1080+'px'; " +
		                        		" document.getElementById('tjpnum').style.width=wjb51*480/1920+'px'; " +
		                        		" document.getElementById('tjpnum').style.MarginTop='-'+hjb51*32/1080+'px'; " +
		                        		" document.getElementById('tjpnum').style.MarginLeft='-'+wjb51*80/1920+'px'; " +
		                        		
		                        		" document.getElementById('editdiv5').style.width=wjb51*160/1920+'px'; " +
		                        		" document.getElementById('editdiv5').style.fontSize=wjb51*19/1920+'px'; " +
		                        		" document.getElementById('editdiv5').style.height=hjb51*60/1080+'px'; " +
		                        		" document.getElementById('editdiv5').style.MarginLeft=wjb51*125/1920+'px'; " +
		                        		" document.getElementById('editdiv5').style.MarginTop='-'+hjb51*12/1080+'px'; " +
		                        		" document.getElementById('tjplevel').style.width=wjb51*480/1920+'px'; " +
		                        		" document.getElementById('tjplevel').style.MarginTop='-'+hjb51*34/1080+'px'; " +
		                        		" document.getElementById('tjplevel').style.MarginLeft='-'+wjb51*80/1920+'px'; " +
		                        		
		                        		"</script> " +
	                        		"</div>",
	                        /* layout      : {
	                            type    : 'vbox',
	                            align   : 'stretch'
	                        },*/
	                        items       : [
	                     
	                                 
	                                 ]                    
	                    }
	            	]
	            	
	           }
	        });
	        
	        // this.on('expand', this.titleField.focus, this.titleField);
	        this.callParent(arguments);
	       
	        //点击保存按钮的操作
	        this.on("beforeeventsave" ,function(editor, eventRecord, eOpts){
	        	//alert(this.eventRecord.getId());
	        	if(this.eventRecord.getId()!=null){
	                //编辑作业计划
	        		/*Ext.Ajax.request({
	    				url:"/mocs/jobplan/save.action",
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
	    					name	: this.titleField.getValue(),
	    					no	: this.locationField.getValue()					
	    			    }
	    		    });*/
	        	}else{
	        		//this.eventRecord.setId(document.getElementById("maxId").value);
	        		this.eventRecord.setName(locationField.getValue());
	        		//alert(Ext.getCmp("planId").getValue());
	        		//alert(Ext.getCmp("priorityId").getValue());
	        		//添加作业计划
	        		//document.getElementById("newTaskName").value=this.locationField.getValue();
	        		Ext.Ajax.request({
	    				url:"./jobplan/save.action",
	    				method:"post",
	    				success:function(response){
	    					//var obj=Ext.decode(response.responseText);
	    				    Ext.Msg.alert("提示","作业计划添加成功!", function () {
	    						  ds.getEventStore().reload({params:{
	  						    	 ishighlight: "true"
	  								}});
	    					});
	    			    },
	    				failure:function(response,opts){Ext.Msg.alert("failure");},
	    				params:{
	    					startTime:this.startTimeField.getValue(),
	    					startDate:this.startDateField.getValue(),
	    					durationTime:this.durationField.getValue(),
	    					id	: document.getElementById("maxId").value,
	    					resourceId:	this.eventRecord.getResourceId(),    					
	    					name	:locationField.getValue(),
	    					no	: 	titleField.getValue(),
	    					planId:document.getElementById("hplanId").value,
	    					planNum:planNumField.getValue(),
	    					priority:document.getElementById("hpriorityId").value
	    			   }
	    		    });
	        	}
	        
	        }); 
	    },
	    
	    show : function(eventRecord) {
	       Ext.Ajax.request({
				url:"./jobplan/getMaxJobPlanInfoId.action",
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











