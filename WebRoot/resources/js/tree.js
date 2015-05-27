
	function showMenu0(id,tree) {
		var display=document.getElementById(id).style.display;
		if (display=="" || display=="none") {
			
			/*
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
					var x = document.getElementById(tree).getBoundingClientRect().left + document.documentElement.scrollLeft;
					var y = document.getElementById(tree).getBoundingClientRect().top + document.documentElement.scrollTop	+ document.getElementById(tree).offsetHeight;
					
					document.getElementById(id).style.left = x;
					document.getElementById(id).style.top = y;
					document.getElementById(id).style.display = "block";
				}
			}else if(Sys.firefox){
				var x = document.getElementById(tree).getBoundingClientRect().left + document.documentElement.scrollLeft;
				var y = document.getElementById(tree).getBoundingClientRect().top + document.documentElement.scrollTop + document.getElementById(tree).offsetHeight;

				document.getElementById(id).style.left = x + "px";
				document.getElementById(id).style.top = y + "px";
				document.getElementById(id).style.display = "block";
			}else if(Sys.chrome){
				var x = document.getElementById(tree).getBoundingClientRect().left + document.documentElement.scrollLeft;
				var y = document.getElementById(tree).getBoundingClientRect().top + document.documentElement.scrollTop + document.getElementById(tree).offsetHeight;

				document.getElementById(id).style.left = x+ "px";
				document.getElementById(id).style.top = y+ "px";
				document.getElementById(id).style.display = "block";
				
			}else if(Sys.safari){
				var x = document.getElementById(tree).getBoundingClientRect().left + document.documentElement.scrollLeft;
				var y = document.getElementById(tree).getBoundingClientRect().top + document.documentElement.scrollTop + document.getElementById(tree).offsetHeight;

				document.getElementById(id).style.left = x+"px";
				document.getElementById(id).style.top = y+"px";
				document.getElementById(id).style.display = "block";
				
			}else{
				var x = document.getElementById(tree).getBoundingClientRect().left + document.documentElement.scrollLeft;
				var y = document.getElementById(tree).getBoundingClientRect().top + document.documentElement.scrollTop + document.getElementById(tree).offsetHeight;

				document.getElementById(id).style.left = x+"px";
				document.getElementById(id).style.top = y+"px";
				document.getElementById(id).style.display = "block";
			}
			*/
			document.getElementById(id).style.display = "block";
		}else{
			document.getElementById(id).style.display = "none";
		}
	}
	
	/**
	 * 
	 */
	function closeTreeDiv0(id){
		if(id==undefined)
			return false;
		var Sys = {};
		var ua = navigator.userAgent.toLowerCase();
		var s;
		(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : (s = ua
				.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : (s = ua
				.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : (s = ua
				.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua
				.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;

		if (Sys.ie) {
			document.getElementById(id).onmouseout=function(event){
				event = event ||window.event;
				var t=event.toElement;
	            if (!this.contains(t)) {
	            	this.style.display="none";
	            } 
            };
		}else if(Sys.firefox){
			document.getElementById(id).onmouseout=function(event){
				var s =event.relatedTarget;
	        	if (!this.contains(s)) {
	        		this.style.display="none";
	        	}            
			};
		}else if(Sys.chrome){
			document.getElementById(id).onmouseout=function(event){
				event = event ||window.event;
				var t=event.toElement;
	            if (!this.contains(t)) {
	            	this.style.display="none";
	            } 
            };
		}else if(Sys.safari){
			document.getElementById(id).onmouseout=function(event){
				event = event ||window.event;
				var t=event.relatedTarget;
	            if (!this.contains(t)) {
	            	this.style.display="none";
	            } 
		}
	}else{
		document.getElementById(id).onmouseout=function(event){
				event = event ||window.event;
				var t=event.toElement;
	            if (!this.contains(t)) {
	            	this.style.display="none";
	            } 
            };
		}
	} 
	
	//
	function judgeIdcard(num) 
	{   
	        num = num.toUpperCase();  
	        //
	        if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num)))   
	        { 
	            alert(''); 
	            return false; 
	        } 
	       
	        var len, re; 
	        len = num.length; 
	        if (len == 15) 
	        { 
	            re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/); 
	            var arrSplit = num.match(re);

	            
	            var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]); 
	            var bGoodDay; 
	            bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4])); 
	            if (!bGoodDay) 
	            { 
	                alert('');   
	                return false; 
	            } 
	            else 
	            { 
	                    
	                    var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
	                    var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
	                    var nTemp = 0, i;   
	                    num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6); 
	                    for(i = 0; i < 17; i ++) 
	                    { 
	                        nTemp += num.substr(i, 1) * arrInt[i]; 
	                    } 
	                    num += arrCh[nTemp % 11];   
	                    return true;   
	            }   
	        } 
	        if (len == 18) 
	        { 
	            re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/); 
	            var arrSplit = num.match(re);

	           
	            var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]); 
	            var bGoodDay; 
	            bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4])); 
	            if (!bGoodDay) 
	            { 
	                //alert(dtmBirth.getYear()); 
	                //alert(arrSplit[2]); 
	               // alert('''); 
	                return false; 
	            } 
	        else 
	        { 
	          
	            var valnum; 
	            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
	            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
	            var nTemp = 0, i; 
	            for(i = 0; i < 17; i ++) 
	            { 
	                nTemp += num.substr(i, 1) * arrInt[i]; 
	            } 
	            valnum = arrCh[nTemp % 11]; 
	            if (valnum != num.substr(17, 1)) 
	            { 
	                alert('' + valnum); 
	                return false; 
	            } 
	            return true; 
	        } 
	        } 
	        return false; 
	}  



	 