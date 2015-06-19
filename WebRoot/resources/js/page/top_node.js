var userId= document.getElementById("pointform:userid").value;
var parentId = document.getElementById("pointform:parentid").value;
var nodeId=document.getElementById("pointform:nodeid").value;
var parentNode="-999";
var url="WSUserService/getAllNodesByParentNodeId.json";

$(document).ready(function(){
	if(parentId == "") {
		//设置默认父节点
		parentId = parentNode;
	}
	if(menuLoadFlag) {
		getNodeInfo(parentId);
	}
	
	//回到根节点按钮
	$(".zl-icon-home").mousedown(function() {
		$(this).addClass("active");
	}).mouseup(function() {
		//恢复左右按钮
		$(".zl-top-menu-left,.zl-top-menu-right").removeClass("disable");
		$(this).removeClass("active");
		$("#parent_node").hide();
		getNodeInfo(parentNode);
	});
	
	//左右切换按钮
	$(".zl-top-menu-left,.zl-top-menu-right").click(function() {
		var currentLeft = parseFloat($(".zl-top-menu-sub-content").css("margin-left").replace("px", ""));
		var minLeft = $(".zl-top-menu-sub-frame").width() - $(".zl-top-menu-sub-content").width();
		//var moveStep = $(".zl-top-menu").width();
		var moveStep = $(".zl-top-menu-sub-frame").width() / 2;
		var menuCount = $("#top_node .zl-top-menu").length; 
		var PAGE_SIZE = 4;
		if(parentId != "" && parentId != "-999") {
			PAGE_SIZE = 3;
		}
		
		if($(this).hasClass("zl-top-menu-left")) {
			currentLeft += moveStep;	
		}
		else {
			currentLeft -= moveStep;
		}
		//过度矫正
		$(".zl-top-menu-left,.zl-top-menu-right").removeClass("disable");
		//判断是否只有一页
		if(menuCount <= PAGE_SIZE) {
			currentLeft = 0;
			$(".zl-top-menu-left,.zl-top-menu-right").addClass("disable");
		}
		else {
			if(currentLeft >= 0) {
				currentLeft = 0;
				$(".zl-top-menu-left").addClass("disable");
			}
			if(currentLeft <= minLeft) {
				currentLeft = minLeft;
				$(".zl-top-menu-right").addClass("disable");
			}
		}
		//确认移动
		$(".zl-top-menu-sub-content").animate({marginLeft:currentLeft}, 200);
	});
});

/*
 * 从接口获取数据内容 
 */
function getNodeInfo(nodeId) {
	var storage = window.sessionStorage;
	if(storage.getItem("node_id") == nodeId) {
		formatInfo(storage.getItem("node_value"));
		return;
	}
	
	$.ajax({
		url: url,
		async: false,
		data: "nodeId="+nodeId+"&userId="+userId,
		cache: false,
		dataType: 'text',
		success: function(data) {
			//整理内容
			formatInfo(data);
			//重置cookie
			storage.setItem("node_id", nodeId);
			storage.setItem("node_value", data);
		}
	});
	function formatInfo(data) {
		//转换数据
		data = JSON.parse(data);
		data = data.data;
		//整理内容
		var objDataFrame = document.createElement("div");
		objDataFrame.className = "zl-top-menu-sub-frame";
		var objDataContent = document.createElement("div");
		objDataContent.className = "zl-top-menu-sub-content";
		for(var index = 0; index < data.length; index++) {
			var nId=data[index].nodeId;
			var name=data[index].name;							
			var num=data[index].childNodesNum;
			
			objDataContent.appendChild(createNodeMenu(nId, name, num));
		}
		objDataFrame.appendChild(objDataContent);
		
		//加载内容
		$("#top_node").empty().append(objDataFrame);
		
		//加载按钮事件
		$("#top_node").find(".zl-top-menu>span").click(function() {
			//是否有子节点
			if(parseInt($(this).attr("sub_num")) > 0) {
				//显示父节点
				$("#parent_node").show().empty().append(createNodeMenu($(this).attr("node_id"), $(this).text(), "0"));
				//显示子节点框架
				$("#top_node").addClass("sub");
				//加载点击父节点事件
				$("#parent_node .zl-top-menu:eq(0)>span").click(function() {
					nodeClick($(this).attr("node_id"));
				});
				
				//加载子节点
				getNodeInfo($(this).attr("node_id"));
				
				//调整子节点大小位置
				$("#top_node .zl-top-menu-sub-content").width(function() {
					var widthAll = 0;
					$(this).find(".zl-top-menu").each(function() {
						widthAll += $(this).width();
					});
					return widthAll + 1;
				});
				$("#top_node .zl-top-menu-sub-frame").width($(".zl-top-menu-center").width() - $(".zl-top-menu.menu-home").width() - $("#parent_node>.zl-top-menu").width() - 1);
				
				//重置父节点ID
				parentId = $(this).attr("node_id");
			}
			else {
				nodeClick($(this).attr("node_id"));	
			}
		});

		//调整内容大小、位置
		$(objDataContent).width(function() {
			//调整子节点大小位置
			var widthAll = 0;
			$(this).find(".zl-top-menu").each(function() {
				widthAll += $(this).width();
			});
			return widthAll + 1;
		});
		moveCurrentNode();
	}
}

/*
 * 动态创建节点元素
 */
function createNodeMenu(nId, name, num) {
	var objFrame = document.createElement("div");
	objFrame.className = "zl-top-menu";
	if(nodeId == nId) {
		$(objFrame).addClass("cur");
	}
	var objContent = document.createElement("span");
	objContent.setAttribute("node_id", nId);
	objContent.setAttribute("sub_num", num);
	$(objContent).text(name);
	var objSplit = document.createElement("div");
	objSplit.className = "zl-top-split";
	objFrame.appendChild(objContent);
	objFrame.appendChild(objSplit);
	
	return objFrame;
}

/*
 * 节点跳转事件
 */
function nodeClick(nId) {
	//后台ID转换
	document.getElementById("pointform:nodeid").value = nId;
	//更新页面
	document.getElementById("pointform:update").click();
}


/* 移动到合适位置方法 */
function moveCurrentNode() {
	var currentLeft = 0;
	//是否选中判断
	if($("#top_node .zl-top-menu.cur").length > 0 ) {
		//总长度判断
		if($(".zl-top-menu-sub-content").width() > $(".zl-top-menu-sub-frame").width()) {
			//超出，需要移动
			currentLeft = $(".zl-top-menu-sub-frame").offset().left - $("#top_node .zl-top-menu.cur").offset().left;
			var displayWidth = $(".zl-top-menu-sub-frame").offset().left + $(".zl-top-menu-sub-content").width() - $("#top_node .zl-top-menu.cur").offset().left;
			//移动后长度判断
			if(displayWidth < $(".zl-top-menu-sub-frame").width()) {
				//过短补充
				currentLeft += $(".zl-top-menu-sub-frame").width() - displayWidth;
			}
		    //移动
			$(".zl-top-menu-sub-content").css("margin-left", currentLeft);
		}	
	}
	
	//判断左右箭头
	if(currentLeft == 0) {
		$(".zl-top-menu-left").addClass("disable");
	}
	if(currentLeft + $(".zl-top-menu-sub-content").width() <= $(".zl-top-menu-sub-frame").width()) {
		$(".zl-top-menu-right").addClass("disable");
	}
}