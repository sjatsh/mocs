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
    	var queryValue = event.value;
		var typeName = event.name;
		if(typeName=="materiel_storage_id_div"){//库房
			$.ajax({
				url : "./storage/getStorageIdValue.action",
				method : "post",
				dataType: "json",
				data : {
					storageValue : queryValue
				},
				success : function(response) {
					 var obj = response;
					 var len = obj.length;
					 if(len>0){
						 $('#table_materiel_storage_id').empty();
						 for(var i=0;i<len;i++){
							var map = obj[i];
							var tr = "<tr>" +
										"<td style='width:230px;'>" +
											"<label>" +
												"<input type='checkbox' name='materiel_storage_id_div' " +
													"onclick='s.changeSelected(this,event);' value='"+map.id+"' " +
													"onblur='s.hiddenList()'/>" +
												"<span>"+map.storageNo+"</span>" +
											"</label>" +
										"</td>" +
									"</tr>";
							 $('#table_materiel_storage_id').append(tr);
					 	 }
					 }else{
						 $('#table_materiel_storage_id').empty();
					 }
				},
				error : function(response) {
				}											
			});
		}else if(typeName=="materielNo_storage_id_div"){//物料编码
			$.ajax({
				url : "./storage/getMaterialIdValue.action",
				method : "post",
				dataType: "json",
				data : {
					materialValue : queryValue,
					flag:'no'
				},
				success : function(response) {
					 var obj = response;
					 var len = obj.length;
					 if(len>0){
						 $('#table_materielNo_storage_id').empty();
						 for(var i=0;i<len;i++){
							var map = obj[i];
							var tr = "<tr>" +
										"<td style='width:230px;'>" +
											"<label>" +
												"<input type='checkbox' name='materielNo_storage_id_div' " +
													"onclick='s.changeSelected(this,event);' value='"+map.id+"' " +
													"onblur='s.hiddenList()'/>" +
												"<span>"+map.no+"</span>" +
											"</label>" +
										"</td>" +
									"</tr>";
							 $('#table_materielNo_storage_id').append(tr);
					 	 }
					 }else{
						 $('#table_materielNo_storage_id').empty();
					 }
				},
				error : function(response) {
				}											
			});
		}else if(typeName=="materielDesc_id_div"){//物料描述
			$.ajax({
				url : "./storage/getMaterialIdValue.action",
				method : "post",
				dataType: "json",
				data : {
					materialValue : queryValue,
					flag:'name'
				},
				success : function(response) {
					 var obj = response;
					 var len = obj.length;
					 if(len>0){
						 $('#table_materielDesc_id').empty();
						 for(var i=0;i<len;i++){
							var map = obj[i];
							var tr = "<tr>" +
										"<td style='width:230px;'>" +
											"<label>" +
												"<input type='checkbox' name='materielDesc_id_div' " +
													"onclick='s.changeSelected(this,event);' value='"+map.id+"' " +
													"onblur='s.hiddenList()'/>" +
												"<span>"+map.name+"</span>" +
											"</label>" +
										"</td>" +
									"</tr>";
							 $('#table_materielDesc_id').append(tr);
					 	 }
					 }else{
						 $('#table_materielDesc_id').empty();
					 }
				},
				error : function(response) {
				}											
			});
		}else if(typeName=="materiel_positon_Id_div"){//库位
			$.ajax({
				url : "./storage/getPositionIdValue.action",
				method : "post",
				dataType: "json",
				data : {
					query : queryValue
				},
				success : function(response) {
					 var obj = response;
					 var len = obj.length;
					 if(len>0){
						 $('#table_materiel_positon_Id').empty();
						 for(var i=0;i<len;i++){
							var map = obj[i];
							var tr = "<tr>" +
										"<td style='width:230px;'>" +
											"<label>" +
												"<input type='checkbox' name='materiel_positon_Id_div' " +
													"onclick='s.changeSelected(this,event);' value='"+map.id+"' " +
													"onblur='s.hiddenList()'/>" +
												"<span>"+map.positionNo+"</span>" +
											"</label>" +
										"</td>" +
									"</tr>";
							 $('#table_materiel_positon_Id').append(tr);
					 	 }
					 }else{
						 $('#table_materiel_positon_Id').empty();
					 }
				},
				error : function(response) {
				}											
			});
		}else if(typeName=="storageNo_Batch_div"){//批次查询--库房
			$.ajax({
				url : "./storage/getStorageIdValue.action",
				method : "post",
				dataType: "json",
				data : {
					storageValue : queryValue
				},
				success : function(response) {
					 var obj = response;
					 var len = obj.length;
					 if(len>0){
						 $('#table_storageNo_Batch').empty();
						 for(var i=0;i<len;i++){
							var map = obj[i];
							var tr = "<tr>" +
										"<td style='width:230px;'>" +
											"<label>" +
												"<input type='checkbox' name='storageNo_Batch_div' " +
													"onclick='s.changeSelected(this,event);' value='"+map.id+"' " +
													"onblur='s.hiddenList()'/>" +
												"<span>"+map.storageNo+"</span>" +
											"</label>" +
										"</td>" +
									"</tr>";
							 $('#table_storageNo_Batch').append(tr);
					 	 }
					 }else{
						 $('#table_storageNo_Batch').empty();
					 }
				},
				error : function(response) {
				}											
			});
		}else if(typeName=="positonId_Batch_div"){//批次查询--库位
			$.ajax({
				url : "./storage/getPositionIdValue.action",
				method : "post",
				dataType: "json",
				data : {
					query : queryValue
				},
				success : function(response) {
					 var obj = response;
					 var len = obj.length;
					 if(len>0){
						 $('#table_positonId_Batch').empty();
						 for(var i=0;i<len;i++){
							var map = obj[i];
							var tr = "<tr>" +
										"<td style='width:230px;'>" +
											"<label>" +
												"<input type='checkbox' name='positonId_Batch_div' " +
													"onclick='s.changeSelected(this,event);' value='"+map.id+"' " +
													"onblur='s.hiddenList()'/>" +
												"<span>"+map.positionNo+"</span>" +
											"</label>" +
										"</td>" +
									"</tr>";
							 $('#table_positonId_Batch').append(tr);
					 	 }
					 }else{
						 $('#table_positonId_Batch').empty();
					 }
				},
				error : function(response) {
				}											
			});
		}else if(typeName=="storageNo_Seq_div"){//序列查询--库房
			$.ajax({
				url : "./storage/getStorageIdValue.action",
				method : "post",
				dataType: "json",
				data : {
					storageValue : queryValue
				},
				success : function(response) {
					 var obj = response;
					 var len = obj.length;
					 if(len>0){
						 $('#table_storageNo_Seq').empty();
						 for(var i=0;i<len;i++){
							var map = obj[i];
							var tr = "<tr>" +
										"<td style='width:230px;'>" +
											"<label>" +
												"<input type='checkbox' name='storageNo_Seq_div' " +
													"onclick='s.changeSelected(this,event);' value='"+map.id+"' " +
													"onblur='s.hiddenList()'/>" +
												"<span>"+map.storageNo+"</span>" +
											"</label>" +
										"</td>" +
									"</tr>";
							 $('#table_storageNo_Seq').append(tr);
					 	 }
					 }else{
						 $('#table_storageNo_Seq').empty();
					 }
				},
				error : function(response) {
				}											
			});
		}else if(typeName=="positonId_Seq_div"){//序列查询--库位
			$.ajax({
				url : "./storage/getPositionIdValue.action",
				method : "post",
				dataType: "json",
				data : {
					query : queryValue
				},
				success : function(response) {
					 var obj = response;
					 var len = obj.length;
					 if(len>0){
						 $('#table_positonId_Seq').empty();
						 for(var i=0;i<len;i++){
							var map = obj[i];
							var tr = "<tr>" +
										"<td style='width:230px;'>" +
											"<label>" +
												"<input type='checkbox' name='positonId_Seq_div' " +
													"onclick='s.changeSelected(this,event);' value='"+map.id+"' " +
													"onblur='s.hiddenList()'/>" +
												"<span>"+map.positionNo+"</span>" +
											"</label>" +
										"</td>" +
									"</tr>";
							 $('#table_positonId_Seq').append(tr);
					 	 }
					 }else{
						 $('#table_positonId_Seq').empty();
					 }
				},
				error : function(response) {
				}											
			});
		}else if(typeName=="materielNo_seq_storage_id_div"){//批次查询--物料编码
			$.ajax({
				url : "./storage/getMaterialIdValue.action",
				method : "post",
				dataType: "json",
				data : {
					materialValue : queryValue,
					flag:'no'
				},
				success : function(response) {
					 var obj = response;
					 var len = obj.length;
					 if(len>0){
						 $('#table_materielNo_seq_storage_id').empty();
						 for(var i=0;i<len;i++){
							var map = obj[i];
							var tr = "<tr>" +
										"<td style='width:230px;'>" +
											"<label>" +
												"<input type='checkbox' name='materielNo_seq_storage_id_div' " +
													"onclick='s.changeSelected(this,event);' value='"+map.id+"' " +
													"onblur='s.hiddenList()'/>" +
												"<span>"+map.no+"</span>" +
											"</label>" +
										"</td>" +
									"</tr>";
							 $('#table_materielNo_seq_storage_id').append(tr);
					 	 }
					 }else{
						 $('#table_materielNo_seq_storage_id').empty();
					 }
				},
				error : function(response) {
				}											
			});
		}else if(typeName=="materielNo_batch_storage_id_div"){//批次查询--物料编码
			$.ajax({
				url : "./storage/getMaterialIdValue.action",
				method : "post",
				dataType: "json",
				data : {
					materialValue : queryValue,
					flag:'no'
				},
				success : function(response) {
					 var obj = response;
					 var len = obj.length;
					 if(len>0){
						 $('#table_materielNo_batch_storage_id').empty();
						 for(var i=0;i<len;i++){
							var map = obj[i];
							var tr = "<tr>" +
										"<td style='width:230px;'>" +
											"<label>" +
												"<input type='checkbox' name='materielNo_batch_storage_id_div' " +
													"onclick='s.changeSelected(this,event);' value='"+map.id+"' " +
													"onblur='s.hiddenList()'/>" +
												"<span>"+map.no+"</span>" +
											"</label>" +
										"</td>" +
									"</tr>";
							 $('#table_materielNo_batch_storage_id').append(tr);
					 	 }
					 }else{
						 $('#table_materielNo_batch_storage_id').empty();
					 }
				},
				error : function(response) {
				}											
			});
		}else if(typeName=="seq_materielDesc_id_div"){//物料描述
			$.ajax({
				url : "./storage/getMaterialIdValue.action",
				method : "post",
				dataType: "json",
				data : {
					materialValue : queryValue,
					flag:'name'
				},
				success : function(response) {
					 var obj = response;
					 var len = obj.length;
					 if(len>0){
						 $('#table_seq_materielDesc_id').empty();
						 for(var i=0;i<len;i++){
							var map = obj[i];
							var tr = "<tr>" +
										"<td style='width:230px;'>" +
											"<label>" +
												"<input type='checkbox' name='seq_materielDesc_id_div' " +
													"onclick='s.changeSelected(this,event);' value='"+map.id+"' " +
													"onblur='s.hiddenList()'/>" +
												"<span>"+map.name+"</span>" +
											"</label>" +
										"</td>" +
									"</tr>";
							 $('#table_seq_materielDesc_id').append(tr);
					 	 }
					 }else{
						 $('#table_seq_materielDesc_id').empty();
					 }
				},
				error : function(response) {
				}											
			});
		}else if(typeName=="batch_materielDesc_id_div"){//物料描述
			$.ajax({
				url : "./storage/getMaterialIdValue.action",
				method : "post",
				dataType: "json",
				data : {
					materialValue : queryValue,
					flag:'name'
				},
				success : function(response) {
					 var obj = response;
					 var len = obj.length;
					 if(len>0){
						 $('#table_batch_materielDesc_id').empty();
						 for(var i=0;i<len;i++){
							var map = obj[i];
							var tr = "<tr>" +
										"<td style='width:230px;'>" +
											"<label>" +
												"<input type='checkbox' name='batch_materielDesc_id_div' " +
													"onclick='s.changeSelected(this,event);' value='"+map.id+"' " +
													"onblur='s.hiddenList()'/>" +
												"<span>"+map.name+"</span>" +
											"</label>" +
										"</td>" +
									"</tr>";
							 $('#table_batch_materielDesc_id').append(tr);
					 	 }
					 }else{
						 $('#table_batch_materielDesc_id').empty();
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