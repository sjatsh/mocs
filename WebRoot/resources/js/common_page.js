// JavaScript Document


	/**
	 *多台设备OEE趋势比较 
	 */
	function changTableRows(){
		var tab = document.getElementById("myform:serName") ;
	    var count = tab.rows.item(0).cells.length ;//表格总数据
	    
	    var r=count%6;
	    var row = (count-r)/9;
	    if(r>0){
	    	row=row+1;
	    }
	    var tableData = tab.rows.item(0);//获取table中所有数据信息，并存储
		var  data =new Array(count);
		for(var i=0;i<count;i++){
			data[i]=tableData.cells[i].innerHTML;
		}
		 tab.deleteRow(0);
		 changeTableInfo(tab,row,9,data,count);
	}	

	/**改变表格信息
	 *tableName--页面表格元素
	 *rows --总行数
	 *cells --单行显示列数
	 *data --初始数据
	 *count --数据总条数
	*/
	function changeTableInfo(tableName,rows,cells,data,count){
		for(var i=0;i<rows;i++){
			var newRow = tableName.insertRow(i); //动态创建一行
			newRow.style.height="35px"; 
			for(var j=0;j<cells;j++){
				var newcell = newRow.insertCell(j);
				if(count>(cells*i+j)){
		 	 		newcell.innerHTML = data[cells*i+j];
		 	 	} 
			}
		}
	}
	
	/**
	 * 树形结构控制
	 */
	function showMenu() {
		var display=document.getElementById("DropdownMenuBackground").style.display;
		if (display=="" || display=="none") {
			
			var Sys = {};
			var ua = navigator.userAgent.toLowerCase();
			var s;
			(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : (s = ua
					.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : (s = ua
					.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : (s = ua
					.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua
					.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;

			if (Sys.ie) {
				if (document.documentElement.getBoundingClientRect) {
					var x = document.getElementById("tre").getBoundingClientRect().left + document.documentElement.scrollLeft;
					var y = document.getElementById("tre").getBoundingClientRect().top + document.documentElement.scrollTop	+ document.getElementById("tre").offsetHeight;
					
					document.getElementById("DropdownMenuBackground").style.left = x;
					document.getElementById("DropdownMenuBackground").style.top = y;
					document.getElementById("DropdownMenuBackground").style.display = "block";
				}
			}else if(Sys.firefox){
				var x = document.getElementById("tre").getBoundingClientRect().left + document.documentElement.scrollLeft;
				var y = document.getElementById("tre").getBoundingClientRect().top + document.documentElement.scrollTop + document.getElementById("tre").offsetHeight;

				document.getElementById("DropdownMenuBackground").style.left = x + "px";
				document.getElementById("DropdownMenuBackground").style.top = y + "px";
				document.getElementById("DropdownMenuBackground").style.display = "block";
			}else if(Sys.chrome){
				var x = document.getElementById("tre").getBoundingClientRect().left + document.documentElement.scrollLeft;
				var y = document.getElementById("tre").getBoundingClientRect().top + document.documentElement.scrollTop + document.getElementById("tre").offsetHeight;

				document.getElementById("DropdownMenuBackground").style.left = x+ "px";
				document.getElementById("DropdownMenuBackground").style.top = y+ "px";
				document.getElementById("DropdownMenuBackground").style.display = "block";
				
			}else if(Sys.safari){
				var x = document.getElementById("tre").getBoundingClientRect().left + document.documentElement.scrollLeft;
				var y = document.getElementById("tre").getBoundingClientRect().top + document.documentElement.scrollTop + document.getElementById("tre").offsetHeight;

				document.getElementById("DropdownMenuBackground").style.left = x+"px";
				document.getElementById("DropdownMenuBackground").style.top = y+"px";
				document.getElementById("DropdownMenuBackground").style.display = "block";
				
			}else{
				var x = document.getElementById("tre").getBoundingClientRect().left + document.documentElement.scrollLeft;
				var y = document.getElementById("tre").getBoundingClientRect().top + document.documentElement.scrollTop + document.getElementById("tre").offsetHeight;

				document.getElementById("DropdownMenuBackground").style.left = x+"px";
				document.getElementById("DropdownMenuBackground").style.top = y+"px";
				document.getElementById("DropdownMenuBackground").style.display = "block";
			}
		}else{
			document.getElementById("DropdownMenuBackground").style.display = "none";
		}
	}
	
	/**
	 * 树形结构控制
	 */
	function closeTreeDiv(){
		var Sys = {};
		var ua = navigator.userAgent.toLowerCase();
		var s;
		(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : (s = ua
				.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : (s = ua
				.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : (s = ua
				.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua
				.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;

		if (Sys.ie) {
			document.getElementById("DropdownMenuBackground").onmouseout=function(event){
				event = event ||window.event;
				var t=event.toElement;
	            if (!this.contains(t)) {
	            	this.style.display="none";
	            } 
            };
		}else if(Sys.firefox){
			document.getElementById("DropdownMenuBackground").onmouseout=function(event){
				var s =event.relatedTarget;
	        	if (!this.contains(s)) {
	        		this.style.display="none";
	        	}            
			};
		}else if(Sys.chrome){
			document.getElementById("DropdownMenuBackground").onmouseout=function(event){
				event = event ||window.event;
				var t=event.toElement;
	            if (!this.contains(t)) {
	            	this.style.display="none";
	            } 
            };
		}else if(Sys.safari){
			document.getElementById("DropdownMenuBackground").onmouseout=function(event){
				event = event ||window.event;
				var t=event.relatedTarget;
	            if (!this.contains(t)) {
	            	this.style.display="none";
	            } 
		}
	}else{
		document.getElementById("DropdownMenuBackground").onmouseout=function(event){
				event = event ||window.event;
				var t=event.toElement;
	            if (!this.contains(t)) {
	            	this.style.display="none";
	            } 
            };
		}
	} 
	
	/**
	 * 显示吉祥物
	 */
	function showMascot(){
		document.getElementById("loadbtjpg").style.zIndex=10001;
		document.getElementById("loadbtjpg").style.display="block";
	}
	
	/**
	 * 头部导航控制
	 * @param i  headHavigation 
	 */
	function headHavigation(i) {
		switch (i) {
		case 1:
			document.getElementById("myform:top_info_foot_menui_ul_li_a1").className = "top_info_foot_menui_ul_li_a1_click";
			document.getElementById("myform:top_info_foot_menui_ul_li_a2").className = "top_info_foot_menui_ul_li_a2";
			document.getElementById("myform:top_info_foot_menui_ul_li_a3").className = "top_info_foot_menui_ul_li_a3";
			document.getElementById("myform:top_info_foot_menui_ul_li_a4").className = "top_info_foot_menui_ul_li_a4";
			break;
		case 2:
			document.getElementById("myform:top_info_foot_menui_ul_li_a1").className = "top_info_foot_menui_ul_li_a1_change";
			document.getElementById("myform:top_info_foot_menui_ul_li_a2").className = "top_info_foot_menui_ul_li_a2_click";
			//document.getElementById("myform:top_info_foot_menui_ul_li_a3").className = "top_info_foot_menui_ul_li_a3";
			//document.getElementById("myform:top_info_foot_menui_ul_li_a4").className = "top_info_foot_menui_ul_li_a4";
			break;
		case 3:
			//document.getElementById("myform:top_info_foot_menui_ul_li_a1").className = "top_info_foot_menui_ul_li_a1_change";
			//document.getElementById("myform:top_info_foot_menui_ul_li_a2").className = "top_info_foot_menui_ul_li_a2";
			document.getElementById("myform:top_info_foot_menui_ul_li_a3").className = "top_info_foot_menui_ul_li_a3_click";
			document.getElementById("myform:top_info_foot_menui_ul_li_a4").className = "top_info_foot_menui_ul_li_a4";
			break;
		case 4:
			//document.getElementById("myform:top_info_foot_menui_ul_li_a1").className = "top_info_foot_menui_ul_li_a1_change";
			//document.getElementById("myform:top_info_foot_menui_ul_li_a2").className = "top_info_foot_menui_ul_li_a2";
			document.getElementById("myform:top_info_foot_menui_ul_li_a3").className = "top_info_foot_menui_ul_li_a3";
			document.getElementById("myform:top_info_foot_menui_ul_li_a4").className = "top_info_foot_menui_ul_li_a4_click";
			break;
		}
	}
	
	/**
	 * tab页之间的切换样式控制
	 * @param i
	 */
	function changeTabStyle(i) {
		switch (i) {
		case 1:
			document.getElementById('changetab').className = 'shebeichaxunbiaoqian1';
			break;
		case 2:
			document.getElementById('changetab').className = 'shebeichaxunbiaoqian2';
			break;
		case 3:
			document.getElementById('changetab').className = 'shebeichaxunbiaoqian3';
			break;
		case 4:
			document.getElementById('changetab').className = 'shebeichaxunbiaoqian4';
			break;
		case 5:
			document.getElementById('changetab').className = 'shebeichaxunbiaoqian5';
			break;
		case 6:
			document.getElementById('changetab').className = 'shebeichaxunbiaoqian6';
			break;
		}
	}
	