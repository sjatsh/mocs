function query(expand){//写的很乱，后续修改
	var startTime = document.getElementById("myform:startInputDate").value;
	var endTime = document.getElementById("myform:endInputDate").value;
	var partid = document.getElementById("select_box_part_hdn").value;
	var status = document.getElementById("select_box_hdn").value;
	
	/**YT 动态调整甘特图时间轴，定位当前*/
	ds.setStart(new Date(startTime));
	ds.setEnd(new Date(endTime));
	ds.scrollToDate(new Date(),true);
	
	//获取产品名称信息
	this.resourceStore.load({
		params: {
			partid : partid,
			planStatus:status,
			startTime:startTime,
			endTime:endTime,
			isexpand:expand//tree甘特图是否折叠，true为展开
		},  
		callback: function(records, options, success) {
		 
		},
		scope: this,
		add:false
	});

	//获取作业计划信息
	Ext.Ajax.request({
		url : "./jobplan/getjobEvent.action",
		method : "post",
		params : {
			partid : partid,
			planStatus:status,
			startTime:startTime,
			endTime:endTime
		},
		success : function(response,opts) {
			 var obj = Ext.decode(response.responseText);
			 this.eventStore.loadData(obj.data);
		},
		failure : function(response,opts) {
		}											
	});
}