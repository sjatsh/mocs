/*
 * 下啦多选框的js实现
 */
function SelectBoxClass(){
    this.selectedElement=null;
    this.state = false;
    this.name = '';
    this.name_hdn = '';
    this.cssid = '';
    //生成div层
    this.changeListState = function(event,element,divid){
     this.name = element.name;
     this.name_hdn = "#"+this.name+"_hdn";
     var e = event ? event : window.event;
     this.selectedElement = e.srcElement || e.target ;
     this.cssid = "#"+divid;
     $(this.cssid).css('left',this.selectedElement.offsetLeft + "px");
     $(this.cssid).css('top',(this.selectedElement.offsetTop+24)+"px");
     var display = $(this.cssid).css('display');
     $(this.cssid).css('display',(display=="block"?"none":"block"));
     if($(this.cssid).css('display')=="block"){
        changeCheckboxShow($(this.name_hdn).val(),this.name);
     }else{
        changeCheckboxHide();
     }

    };
    //改变读取的值
    this.changeSelected = function(option,event){
    	$(this.name_hdn).val(getallchecked(this.name));
    	this.selectedElement.value = getallcheckedname(this.name);
    };
    //隐藏
    this.hiddenList = function(){
     if(!this.state){
    	$(this.cssid).hide("fast");
        changeCheckboxHide(this.name);
     }
    };
    this.onkeyup = function(event){
    	var query = event.value;
		var typeName = event.name;
		if(typeName=="select_box_job_part"||typeName == "select_box_part"){
			getPartInfo(query,typeName);
		}else if(typeName=="select_box_job_taskNum"){
			Ext.Ajax.request({
				url : "./jobplan/getTaskNumList.action",
				method : "post",
				params : {
					taskNum : query
				},
				success : function(response,opts) {
					 var obj = Ext.decode(response.responseText);
					 var len = obj.length;
					 if(len>0){
						 $('#table_job_taskNum').empty();
						 for(var i=0;i<len;i++){
							var map = obj[i];
							var tr = "<tr><td style='width:180px;'>"                                 
					        	+"<label><input type='checkbox' name='select_box_job_taskNum' onclick='s.changeSelected(this,event);' value='"+map.value+"' onblur='s.hiddenList()'/><span>"+map.Name+"</span></label>"
					        	+"</td></tr>";
							 $('#table_job_taskNum').append(tr);
					 	 }
					 }
				},
				failure : function(response,opts) {
				}											
			});
		}else if(typeName=="select_box_job_equName"){
			Ext.Ajax.request({
				url : "./jobplan/getequNameList.action",
				method : "post",
				params : {
					equName : query
				},
				success : function(response,opts) {
					 var obj = Ext.decode(response.responseText);
					 var len = obj.length;
					 if(len>0){
						 $('#table_job_equName').empty();
						 for(var i=0;i<len;i++){
							var map = obj[i];
							var tr = "<tr><td style='width:180px;'>"                                 
					        	+"<label><input type='checkbox' name='select_box_job_equName' onclick='s.changeSelected(this,event);' value='"+map.Id+"' onblur='s.hiddenList()'/><span>"+map.equName+"</span></label>"
					        	+"</td></tr>";
							 $('#table_job_equName').append(tr);
					 	 }
					 }
				},
				failure : function(response,opts) {
				}											
			});
		}else if(typeName=="select_box_No"){
			$.ajax({
				url : "./jobplan/getequSerialNoList.action",
				method : "post",
				dataType: "json",
				data : {
					equSerialNo : query
				},
				success : function(response) {
					 var obj = response;
					 var len = obj.length;
					 if(len>0){
						 $('#table_No').empty();
						 for(var i=0;i<len;i++){
							var map = obj[i];
							var tr = "<tr><td style='width:230px;'>"                                 
					        	+"<label><input type='checkbox' name='select_box_No' onclick='s.changeSelected(this,event);' value='"+map.id+"' onblur='s.hiddenList()'/><span>"+map.no+"</span></label>"
					        	+"</td></tr>";
							 $('#table_No').append(tr);
					 	 }
					 }
				},
				error : function(response) {
				}											
			});
		}else if(typeName=="select_box_Name"){
			$.ajax({
				url : "./jobplan/getequNameWithoutNodeList.action",
				method : "post",
				dataType: "json",
				data : {
					equName : query
				},
				success : function(response) {
					 var obj = response;
					 var len = obj.length;
					 if(len>0){
						 $('#table_Name').empty();
						 for(var i=0;i<len;i++){
							var map = obj[i];
							var tr = "<tr><td style='width:230px;'>"                                 
					        	+"<label><input type='checkbox' name='select_box_Name' onclick='s.changeSelected(this,event);' value='"+map.id+"' onblur='s.hiddenList()'/><span>"+map.name+"</span></label>"
					        	+"</td></tr>";
							 $('#table_Name').append(tr);
					 	 }
					 }
				},
				error : function(response) {
				}											
			});
		}
		
	};
    //显示下拉框
    function changeCheckboxShow(value,name){
    	changeCheckboxHide();
    	var splitarr = value.split(",");
    	var num1 = splitarr.length;
    	for(var i=0;i<num1;i++){
    		$("input[type='checkbox'][name='"+name+"'][value='"+splitarr[i]+"']").attr("checked",true);
    	}
    }
    this.hiddenDiv = function(event){
		var Sys = {};
		var ua = navigator.userAgent.toLowerCase();
		var s;
		(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : (s = ua
				.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : (s = ua
				.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : (s = ua
				.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua
				.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;

		if (Sys.ie) {
			document.getElementById(event.id).onmouseout=function(event){
				event = event ||window.event;
				var t=event.toElement;
	            if (!this.contains(t)) {
	            	this.style.display="none";
	            } 
            };
		}else if(Sys.firefox){
			document.getElementById(event.id).onmouseout=function(event){
				var s =event.relatedTarget;
	        	if (!this.contains(s)) {
	        		this.style.display="none";
	        	}            
			};
		}else if(Sys.chrome){
			document.getElementById(event.id).onmouseout=function(event){
				event = event ||window.event;
				var t=event.toElement;
	            if (!this.contains(t)) {
	            	this.style.display="none";
	            } 
            };
		}else if(Sys.safari){
			document.getElementById(event.id).onmouseout=function(event){
				event = event ||window.event;
				var t=event.relatedTarget;
	            if (!this.contains(t)) {
	            	this.style.display="none";
	            } 
		}
	}else{
		document.getElementById(event.id).onmouseout=function(event){
				event = event ||window.event;
				var t=event.toElement;
	            if (!this.contains(t)) {
	            	this.style.display="none";
	            } 
            };
		}
	} 
    function changeCheckboxHide(name){
     $("input[type='checkbox'][name='"+name+"']").each(function(){
        this.checked=false;
     });
    }
    //获取勾选中的值，用逗号相隔
   function getallchecked(name){
     var cs = "";
     $("input[type='checkbox'][name='"+name+"']").each(function(){
        if(this.checked){
         cs = cs+","+this.value;
        }
     });
     //去掉最后的逗号
     if(cs.length>1){
        cs = cs.substring(1);
     }
     return cs;
   }
   //获取勾选中的名字,显示在页面中
   function getallcheckedname(name){
     var cs = "";
     var chkval = '';
     $("input[type='checkbox'][name='"+name+"']").each(function(){
        if(this.checked){
   	     chkval = $(this).next();
         cs = cs+","+chkval.text();
        }
     });
     if(cs.length>1){
        cs = cs.substring(1);
     }
     return cs;
   }
}
var s = new SelectBoxClass();
//获取零件信息,并更新零件信息div
function getPartInfo(partName,typeName){
	Ext.Ajax.request({
		url : "./jobplan/getPartList.action",
		method : "post",
		params : {
			partName : partName
		},
		success : function(response,opts) {
			 var obj = Ext.decode(response.responseText);
			 var len = obj.length;
			 if(len>0){
				 if(typeName == "select_box_job_part"){
					 $('#table_job_part').empty();
					 for(var i=0;i<len;i++){
							var map = obj[i];
							var tr = "<tr><td style='width:180px;'>"                                 
					        	+"<label><input type='checkbox' name='select_box_job_part' onclick='s.changeSelected(this,event);' value='"+map.id+"' onblur='s.hiddenList()'/><span>"+map.name+"</span></label>"
					        	+"</td></tr>";
							 $('#table_job_part').append(tr);
						 }
				 }else if(typeName == "select_box_part"){
					 $('#table_part').empty();
					 for(var i=0;i<len;i++){
							var map = obj[i];
							var tr = "<tr><td style='width:180px;'>"                                 
					        	+"<label><input type='checkbox' name='select_box_part' onclick='s.changeSelected(this,event);' value='"+map.id+"' onblur='s.hiddenList()'/><span>"+map.name+"</span></label>"
					        	+"</td></tr>";
							 $('#table_part').append(tr);
					 }
				 }
				
			 }
		},
		failure : function(response,opts) {
		}											
	});
}
(function($){
	$.fn.selectboxs = function selectboxs(){
		var select_name = $(this).attr("id");
		var select_name_hdn = select_name+"_hdn";
		var select_div_id = select_name+"_list";
		var str = "<input type='hidden' name='"+select_name_hdn+"' id='"+select_name_hdn+"'>";
		str += "<input type='text' autocomplete='off' name='"+select_name+"' class='t_selected' onclick=s.changeListState(event,this,'"+select_div_id+"'); id='input' onmouseover='s.state=true;' onmouseout='s.state=false;' onblur='s.hiddenList()' onkeyup='s.onkeyup(this)'/>";
		str += "<div class='t_select_list_table' id='"+select_div_id+"' onmouseover='s.state=true;' onmouseout='s.state=false;s.hiddenDiv(this)'>";
		var selectHtml = $(this).html();
		str += selectHtml+"</div>";
		$(this).html('');
		$(this).append(str);
	};	
})(jQuery);