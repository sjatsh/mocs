 		 function subClick(id){
			 document.getElementById(id).click();
	  	 }
		function menuClick(id){
	    	  var arr=new Array("zy","kc","wlqd","cg","jh");
	    	  for(var i=0;i<arr.length;i++){
	    		  if(arr[i]==id){
	    			  document.getElementById(arr[i]).style.display="block";
	    			  document.getElementById("xs"+arr[i]).className = "process_config_menuDiv active";
	    		  }else{
	    			  document.getElementById(arr[i]).style.display="none";
	    			  document.getElementById("xs"+arr[i]).className = "process_config_menuDiv";
	    		  }
	    	  }
	    	  //document.getElementById(id).click();
	      }
	      function disabelStockOrPostion(){
	    	  var isPositionCtrl=document.getElementById('myform:isPositionCtrl').value;
	    	  //alert(isPositionCtrl);
	    	  if(isPositionCtrl=="0"){
	    		  disabeFalse("myform:isStockCtrl:0");
	    		  disabeFalse("myform:isPostionCtrl:0");
	    		  //alert("false");
	    	  }else{
	    		  disabeTrue("myform:isStockCtrl:0");
	    		  disabeTrue("myform:isPostionCtrl:0");
	    		  disabeTrue("myform:stockNo");
	    		  disabeTrue("myform:postionNo");
	    		  document.getElementById("myform:isStockCtrl:0").checked=false;
	    		  document.getElementById("myform:isPostionCtrl:0").checked=false;
	    		  document.getElementById("myform:stockNo").value="";
	    		  document.getElementById("myform:postionNo").value="";
	    		  //alert("true");
	    	  }
	      }
	      function CtrlChange(v1,v2){
	    	  var isStockCtrl=document.getElementById(v1).checked;
	    	  if(isStockCtrl){
	    		  disabeFalse(v2);
	    	  }else{
	    		  disabeTrue(v2);
	    	  }
	      }
	      
	      function disabeTrue(id){
	    	  document.getElementById(id).disabled = true;
	      }
	      
	      function disabeFalse(id){
	    	  document.getElementById(id).disabled = false;
	      }
	      
	      function valueIsNullOrNaN(){
	    	 //alert(1);
	    	 var nodeid=document.getElementById("myform:nodeid").value;
	    	 var desc=document.getElementById("myform:desc").value;
	    	 var no=document.getElementById("myform:no").value;
	    	 var batchTime=document.getElementById("myform:batchTime").value;
	    	 var price=document.getElementById("myform:price").value;
	    	 var storageMax=document.getElementById("myform:storageMax").value;
	    	 var storageMin=document.getElementById("myform:storageMin").value;
	    	 var orderMax=document.getElementById("myform:orderMax").value;
	    	 var orderMin=document.getElementById("myform:orderMin").value;
	    	 var thresholdValue=document.getElementById("myform:thresholdValue").value;
	    	 
	    	 var unit=document.getElementById("myform:unit").value;
	    	 var className=document.getElementById("myform:className").value;
	    	 var pid=document.getElementById("myform:pid").value;
	    	 //alert(isNaN(batchTime));
	    	 if(null==nodeid||""==nodeid){
	    		 alert("节点不能为空请选着节点");
	    		 return false;
	    	 }else if(null==no||""==no){
	    		 alert("物料编号不能为空");
	    		 return false;
	    	 }else if(null==desc||""==desc){
	    		 alert("物料描述不能为空");
	    		 return false;
	    	 }else if(null==className||""==className){
	    		 alert("物料类型不能为空");
	    		 return false;
	    	 }	else if(null==pid||""==pid){
	    		 alert("物料类别不能为空");
	    		 return false;
	    	 }else if(isNaN(batchTime)){
	    		 alert("批次存储期限只能为数字");
	    		 return false;
	    	 }else if(isNaN(price)){
	    		 alert("价格只能为数字");
	    		 return false;
	    	 }else if(isNaN(storageMax)){
	    		 alert("库存计划最大值只能为数字");
	    		 return false;
	    	 }else if(isNaN(storageMin)){
	    		 alert("库存计划最小值只能为数字");
	    		 return false;
	    	 }else if(isNaN(orderMax)){
	    		 alert("订单最大值只能为数字");
	    		 return false;
	    	 }else if(isNaN(orderMin)){
	    		 alert("订单最小值只能为数字");
	    		 return false;
	    	 }else if(isNaN(thresholdValue)){
	    		 alert("安全数量只能为数字");
	    		 return false;
	    	 }else{
	    		 return true;
	    	 }
	      }
	      function selecChange(v1,v2){
	    	  var tt=document.getElementById(v1).value;
	    	  	  document.getElementById(v2).value=tt;
	      }