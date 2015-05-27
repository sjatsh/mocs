var map = new BMap.Map("container");                        // 创建Map实例
map.centerAndZoom("西安", 5);     // 初始化地图,设置中心点坐标和地图级别
map.addControl(new BMap.NavigationControl()); 
var key = 1;    //开关
var newpoint;   //一个经纬度
var points = [];    //数组，放经纬度信息
var polyline = new BMap.Polyline(); //折线覆盖物
var isFirstPoint=1;	//判断是否为第一个点，是为1，否为0

function startTool(){   //开关函数
if(key==1){
        document.getElementById("startBtn").style.background = "green";
        document.getElementById("startBtn").style.color = "white";
        document.getElementById("startBtn").value = "开启状态";
        key=0;
    }
    else{
        document.getElementById("startBtn").style.background = "red";
        document.getElementById("startBtn").value = "关闭状态";
        key=1;
		isFirstPoint=0;
    }
}
map.addEventListener("click",function(e){   //单击地图，形成折线覆盖物
    newpoint = new BMap.Point(e.point.lng,e.point.lat);
    if(key==0){
    //    if(points[points.length].lng==points[points.length-1].lng){alert(111);}
        points.push(newpoint);  //将新增的点放到数组中
        polyline.setPath(points);   //设置折线的点数组
        map.addOverlay(polyline);   //将折线添加到地图上
       // document.getElementById("info").innerHTML += "new AliLatLng(" +e.point.lat  + "," + e.point.lng + "),</br>";   
		//如果不是第一个点打分割符
		var lat = e.point.lat.toString();
		var lng = e.point.lng.toString();
	   if(isFirstPoint==0){
	   document.getElementById("info").innerHTML +=  "|";
	   }
	   document.getElementById("info").innerHTML +=  lat.substring(0,6)  + "," + lng.substring(0,6);
	   isFirstPoint=0;
		//var lat = e.point.lat.toString();
		//var lng = e.point.lng.toString();
	   //document.getElementById("info").innerHTML +=  lat.substring(0,6)  + "," + lng.substring(0,6)+ "|"; 
	   //输出数组里的经纬度
    }
});
map.addEventListener("dblclick",function(e){   //双击地图，形成多边形覆盖物
if(key==0){
        map.disableDoubleClickZoom();   //关闭双击放大
var polygon = new BMap.Polygon(points);
        map.addOverlay(polygon);   //将折线添加到地图上
    }
});
function getBoundary(){       
    var bdary = new BMap.Boundary();
    var name = document.getElementById("districtName").value;
    bdary.get(name, function(rs){       //获取行政区域
        //map.clearOverlays();        //清除地图覆盖物       
        var count = rs.boundaries.length; //行政区域的点有多少个
        for(var i = 0; i < count; i++){
            var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000"}); //建立多边形覆盖物
            map.addOverlay(ply);  //添加覆盖物
           // map.setViewport(ply.getPath());    //调整视野         
        }                
    });   
}
