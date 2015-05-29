var currentName;
var imgPercent = hjb51 / 1080; //比例 	 
var whval = 29 * imgPercent;
var userid= document.getElementById("pointform:userid").value;
var parentid = document.getElementById("pointform:parentid").value;
var parentNode="-999";
var url="WSUserService/getAllNodesByParentNodeId.json";
//整个块的索引
var thisSub=0;
var lastSub=-1;
//选中块的节点id
var node=parentNode;
var selectedNode;
//层级 
var layer=1;
//是否选中
var selected=false;
$(document).ready(function(){
	//alert("ajax");
	var ids=document.getElementById("pointform:nodeid").value;
	init(parentid,ids);
	if(ids != "") {
		$("#homemenu").css("display", "block");
	}
	$(".tohome").click(function(){
		if($("#homemenu").css("display") == "none") {
			$("#homemenu").css("display", "block");
			thisSub=0;
			selected=true;
			$("#homemenu").html("");
			init(parentNode,ids);
		}
		else {
			$("#homemenu").css("display", "none");
		}
	});
});
function init(node,ids){
	 $.ajax({
		url: url,
		data: "nodeId="+node+"&userId="+userid,
		cache: false,
		dataType: 'text',
		success: function (returnData) {
			var a=JSON.parse(returnData);
			var data=a.data;					
			//菜单位置
			var position;
			var fontSize;
			if(selected==true){
				position=0;
				layer=1;
			}
			else{
				position = -wjb51 * 223 / 1920* (layer - 1);
			}
			var content='<div class="kk" id="submenu'+layer+'" style="left:'+position+'px;position:absolute;">';
			for(var i=data.length-1;i>=0;i--){
			//for(var i=0;i<data.length;i++){
				var nodeId=data[i].nodeId;
				var name=data[i].name;							
				var num=data[i].childNodesNum;
				var l=name.length;
				//var parentId = data[i].parentId;  //得到父ID
				if(layer==1){
					if(l<=6)
						fontSize = hjb51 * 30 / 1080;
					else
						fontSize = hjb51 * 30 / 1080
						- (l - 6) * 3;	
				}else{
					    fontSize = hjb51 * 22 / 1080;
				}
				var top=hjb51*900/1080-hjb51*80/1080*(i+1);//这里
				if(node != "-999") {
					top = hjb51 * 602 / 1080 - hjb51 * 80 / 1080 * (i + 1);
				}
				//var top=990-101*(i+1);
				//node当前父节点id,nodeId当前节点id
				content+='<div class="ss select'+layer+'" no="'+(data.length-1-i)+'" node="'+node+'" name="'+name+'" nodeId="'+nodeId+'" v="'+layer+'" style="position:relative;font-weight:bold;font-size:'+fontSize+'px;background-image:url(./img/menu/2_14.png);">';
				if(num==0){
					content += '<span class="dd" style="cursor:pointer;" >'
						+ name + '</span>';
				}else{
					content+='<img class="mm subselect'+layer+'" v="'+layer+'" node='+nodeId+' style="z-index:10001;" src="./img/menu/2_03.png"/>';
					content+='<span class="selectmar" style="cursor:pointer;">'+name+'</span>';
				}
				content+='</div>';
			}
			content+='</div>';
			$("#homemenu").append(content);
			$(".kk").css('width', wjb51 * 223 / 1920 + "px");
			$(".ss").css('width', wjb51 * 223 / 1920 + "px");
			$(".ss").css('height',hjb51 * 80 / 1080 + "px");
			$(".ss").css('line-height',hjb51 * 80 / 1080 + "px");
			$(".mm").css('width', whval + "px");
			$(".mm").css('height', whval + "px");
			$(".mm").css('line-height',hjb51 * 80 / 1080 + "px");
			$(".mm").css('margin-left',wjb51 * 20 / 1920 + "px");
			$(".dd").css('margin-left',wjb51 * 30 / 1920 + "px");
			$(".selectmar").css('margin-left',wjb51 * 20 / 1920 + "px");
			$(".kk:last").each(function() {
				$(this).scrollTop(10);  //操作滚动条
				if($(this).scrollTop() > 0) { //判断是否有滚动
					$(this).css("top", "0");
				}
				else {
					$(this).css("top", "auto");
				}
				$(this).scrollTop(0);   //滚动条返回顶部

			});
			
			//滚动条隐藏
			var kkFrame = document.createElement("div");
			$(kkFrame).width($("#homemenu .kk").width()).height($("#homemenu .kk").height()).css({position:"absolute", bottom:"0", overflow:"hidden"});
			$("#homemenu .kk").width($("#homemenu .kk").width() +17);
			$(kkFrame).append($("#homemenu .kk")[0]);
			$("#homemenu").append($(kkFrame)[0]);
			
			//选中项背景改变
			for ( var i = 0; i < data.length; i++) {
				var id1=data[i].nodeId;
				if (id1==ids){
					thisSub=data.length-1-i;
					break;
				}
			}
			selected=false;
			$(".select"+layer).eq(thisSub).css("background-image","url(./img/menu/2_11.png)");

			//显示高度调整
			var parentKKBottom = $(".kk:last").offset().top + $(".kk:last").height();
			$(".kk:last .ss").each(function() {
				if($(this).css("background-image").indexOf("2_11.png") > -1) {
					var currentSSBottom = $(this).offset().top + $(this).height();
					if(currentSSBottom > parentKKBottom) {
						$(".kk:last").scrollTop(currentSSBottom - parentKKBottom);
					}
				}
			});
			
			$(".select"+layer).click(function(){
				$(".select"+layer).css("background-image","url(./img/menu/2_14.png)");
				$(this).css("background-image","url(./img/menu/2_11.png)");
				//当前菜单级数
				var v=parseInt($(this).attr("v"));
				selectedNode=$(this).attr("node");
				$("#homemenu").html("");
				selected=true;
				//thisSub=$(this).index();
				thisSub=parseInt($(this).attr("no"));
				var n=$(this).attr("nodeId");
				//alert(n);
				//仅供沈阳车间展示使用
				currentName=$(this).attr("name");
				if(currentName == "沈阳") location.href="http://10.10.140.58:8080/mocs/index.faces";
				else 
				   {
				      document.getElementById("pointform:nodeid").value = n;
				      document.getElementById("pointform:update").click();
				   }
				init(selectedNode,n);
			});	
			$(".subselect"+layer).click(function(e){
				e.stopPropagation();
				//$(".subselect").attr("src","./img/menu/2_03.png");
				//$(this).Children(".subselect").attr("src","./img/menu/2_07.png");
				//当前菜单级数
				var v=parseInt($(this).attr("v"));
				if(v==layer){
					layer++;
				}else if(v<layer){
					for(var i=v+1;i<=layer;i++){
						$("#submenu"+i).remove();
					}
					layer=v+1;
				}
				$(".subselect"+v).attr("src","./img/menu/2_03.png");
				$(this).attr("src","./img/menu/2_07.png");
				//alert(v);
				$(".select"+v).css("background-image","url(./img/menu/2_14.png)");
				$(this).parents(".select"+v).css("background-image","url(./img/menu/2_11.png)");
				node=$(this).attr("node");
				init(node,node);
			});
		}
	});
}