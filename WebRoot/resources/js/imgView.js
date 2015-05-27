//id: 路径的id
//url: 图片的路径
//width: 窗口宽度
//height: 窗口高度
function openImgView(id,url,width,height) 
{
 var left, top;
 left = (window.screen.availWidth - width) / 2;
 top = (window.screen.availHeight - height) / 2;
 var per = 'width=' + width + ',height=' + height + ',left=' + left + ',top=' + top + ',screenX=' + left + ',screenY=' + top+',Resizable=no,location=no';
 var imgValue=document.getElementById(id).value;//获取图片路径
 var imgUrl=url;
 if(null==imgValue||""==imgValue){
	 OpenWindow=window.open("", "newwin", per); 
	 OpenWindow.document.write("<BODY >");
	 OpenWindow.document.write("<center><font size='3'>没有图片</font></center>");
	 OpenWindow.document.write("</BODY>");
	 OpenWindow.document.write("</HTML>");
	 OpenWindow.document.close();
 }else{
	var ss=imgValue.split(",");
	OpenWindow=window.open("", "newwin", per); 
	OpenWindow.document.write("<head>");
	OpenWindow.document.write("<script type='text/javascript' src='./javax.faces.resource/imgView.js.faces?ln=js'></script>");
	OpenWindow.document.write("</head>");
	OpenWindow.document.write("<BODY >");
	for(var i=0;i<ss.length;i++){
		var inhtml="";
		if(ss.length-1-i>0&&i==0){//如果是第一张 加上下一张的按钮
			inhtml=inhtml+"<div id='img0"+i+"' style='display:block;width:"+width+"px;height:"+height+"px;'>";
			inhtml=inhtml+"<img src='"+imgUrl+ss[i]+"'/><br/>";
			inhtml=inhtml+"<input type=\"button\" value=\"下一张\" onclick=\"nextBack('"+ss.length+"','"+(i+1)+"')\"/>";
			inhtml=inhtml+"</div>";
		}else if(ss.length-1-i>0){//中间得图片
			inhtml=inhtml+"<div id='img0"+i+"' style='display:none;width:"+width+"px;height:"+height+"px;'>";
			inhtml=inhtml+"<img src='"+imgUrl+ss[i]+"' /><br/>";
			inhtml=inhtml+"<input type=\"button\" value=\"上一张\" onclick=\"nextBack('"+ss.length+"','"+(i-1)+"')\"/>";
			inhtml=inhtml+"<input type=\"button\" value=\"下一张\" onclick=\"nextBack('"+ss.length+"','"+(i+1)+"')\"/>";
			inhtml=inhtml+"</div>";
		}else if((ss.length-1-i)==0){//最后一张图片
			inhtml=inhtml+"<div id='img0"+i+"' style='display:none;width:"+width+"px;height:"+height+"px;'>";
			inhtml=inhtml+"<img src='"+imgUrl+ss[i]+"'/><br/>";
			inhtml=inhtml+"<input type=\"button\" value=\"上一张\" onclick=\"nextBack('"+ss.length+"','"+(i-1)+"')\"/>";
			inhtml=inhtml+"</div>";
		}
		 OpenWindow.document.write(inhtml);
	 }
	 OpenWindow.document.write("</HTML>");
	 OpenWindow.document.close();
 }	 
}
function nextBack(imgValue,imgId){
	var ss=imgValue;
	for(var i=0;i<ss;i++){
		if(i==imgId){
			document.getElementById("img0"+i).style.display="block";
		}else{
			document.getElementById("img0"+i).style.display="none";
		}
	}
}