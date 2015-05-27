//公共变量
	//地图
	var map;
	// 多边形
	var polygon;
	//存储的数组多边形,建立多边形的索引
	var count = new Array();// 用来存储行政区多边形,
	//提示窗口
	var  infoWindow;
	//提示窗口
	var points = [];
	//提示框标题
	var title;
	//提示框内容
	var content;
	
	//var cities =[];
	// 画多边形的点存入points
	function setPositons(objet){
			points = [];
			temp = objet.markerPosition.split("|");
			for(var i=0;i < temp.length;i++){
			var latlng = temp[i];
			points.push(new AliLatLng(latlng.split(",")[0],latlng.split(",")[1]));
			}
		}

	function newMarker(object){
		
		
	//var icon=new AliIcon("../images/shangxialiao.png",{x:12,y:12},{x:8,y:8});
	var icp = new AliLatLng(object.iconPoint.split(",")[0],object.iconPoint.split(",")[1]);
	var marker = new AliMarker(icp);
	//marker.setIcon(icon);
	map.addOverlay(marker);
	return marker;
	}
	
	
		//图标
	function addMarker(marker){
//var icp = new AliLatLng(object.iconPoint.split(",")[0],object.iconPoint.split(",")[1]);
//var marker = new AliMarker(icp);

map.addOverlay(marker);

}

	//按照指定位置，标题，内容代开提示框
	function openInfoWin(point, title, content) {
		return map.openInfoWindow(point, title, content);
	}
	
	//关闭提示图框
	function closeInfoWin() {
		return map.closeInfoWindow();
	}
	
	// 为多边形添加移入的监听事件
	 function  addOverListener(polygon,infoPoint,title,content,displaylevel){
	  //图形添加监听事件 移入
		AliEvent.addListener(polygon, 'mouseover', function() {
			polygon.setOpacity(0.5);
			document.getElementById("myform:nodeidincontent").value=displaylevel;
			 if(document.getElementById("myform:nodeidincontent").value!=""){
				  document.getElementById("myform:hiddenbutton").click();}
		});
	  }
	 
	 function  addClickListener(polygon,infoPoint,title,content){
		  //图形添加监听事件 点击
			AliEvent.addListener(polygon, 'click', function() {
				document.getElementById("myform:hiddenbutton").click();
				infoWindow = openInfoWin(new AliLatLng(infoPoint.split(",")[0],infoPoint.split(",")[1]), title, content);
				infoWindow.moveIntoView();
				infoWindow.setContent(content+addStrcv(), false);
			});
		  }
	 
	 
	  function addStrcv(){
		  var str=$(".questioncss").html();
		  return str;
	  }
	  function cleardom(){
		  $('.questioncss').remove();
	  }
	  //为多边形添加移出的监听事件
	  function addOutListener(polygon){
	AliEvent.addListener(polygon, 'mouseout', function() {
			polygon.setOpacity(0);
			//closeInfoWin();			
		});
	 }
	 
	 function  addMarkerOverListener(marker,polygon,infoPoint,title,content){
		AliEvent.addListener(marker, 'mouseover', function() {	
			clearMap();	
			polygon.setOpacity(0.5);
			//infoWindow = openInfoWin(new AliLatLng(infoPoint.split(",")[0],infoPoint.split(",")[1]), title, content);
			
		});	 
	 }
	 
	  function addMarkerOutListener(marker,polygon){
	AliEvent.addListener(marker, 'mouseout', function() {
			polygon.setOpacity(0);
			//closeInfoWin();			
		});
	 }
	function clearMap(){
	  for(var i=0;i< count.length;i++)
               {
          count[i].setOpacity(0);
              };
	}
	
	
	
	//地图 加载类
	function mapService(person) {
	
		//给points赋值
		setPositons(person);
		//用points画出多边形
		polygon=new AliPolygon(points);
		count.push(polygon);
		
		marker = newMarker(person);
		addMarker(marker);

		polygon.setOpacity(0);
		polygon.setLineWeight(0);		
		
		
		
		//监听移入图形
		addOverListener(polygon,person.infoPoint,person.title,person.content,person.displaylevel);
		//监听点击图形
		addClickListener(polygon,person.infoPoint,person.title,person.content);
		//监听移出图形
		addOutListener(polygon);
		
		
		addMarkerOverListener(marker,polygon,person.infoPoint,person.title,person.content);
		
		addMarkerOutListener(marker,polygon);
		
			//将多边形添加到地图
		map.addOverlay(polygon);
		}

function delCookie(name)//删除cookie
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}
function getCookie(name)//取cookies函数        
{
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
     if(arr != null) return unescape(arr[2]); return null;

}
function SetCookie(name,value)
{
    var Days = 30; 
    var exp  = new Date();    
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+ ";path=/";
}
