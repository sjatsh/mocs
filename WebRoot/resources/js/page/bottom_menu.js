/* 是否可点 */
var clickAble = true;
var nowSelectMenuItem = document.getElementById("menuform:nowSelectMenuItem").value;
var menuLoadFlag = true;

$(document).ready(function() {
	//获取menu内容
	var menuListJson = null;
	if(menuLoadFlag) {
		var storage = window.sessionStorage;
		var cookieStr = storage.getItem("menu_value");
		if(typeof(cookieStr) != "undefined" && cookieStr != "" && cookieStr != null) {
			menuListJson = cookieStr;
		}
		else {
			menuListJson = getMenuListJson();	
		}
		storage.setItem("menu_value", menuListJson);
	}

    //生成menu DOM
	if(menuListJson != "");
	json2Html(menuListJson);
    //字体大小调整
	$(".child-menu>span,.sub-menu>span").each(function() {
		if($(this).text().length > 20) {
			$(this).css("font-size", 40 - $(this).text().length + "px");
		}
	});
	//重置菜单总长度
	$("#bottom_menus").width(function() {
		var sumWidth = 0;
		$(this).find(".main-menu").each(function() {
			sumWidth += $(this).width() + parseFloat($(this).css("margin-right").replace("px", ""));	//8是padding-right的值
		});
		return sumWidth + 1;
	});
	
	//指向当前menu
	act(getCookie("menuActive"));
	
    //移动到合适位置（优先最左）
    moveCurrent();
    
    //隐藏二级菜单
	$("#bottom_menus>.main-menu>.child-menus").hide();
	
	//左右移动
	$(".zl-bottom-bar-left,.zl-bottom-bar-right").click(function() {
		var currentLeft = parseFloat($("#bottom_menus").css("margin-left").replace("px", ""));
		var minLeft = $(".zl-bottom-bar-center").width() - $("#bottom_menus").width();
		var moveStep = $(".main-menu").width() + parseFloat($(".main-menu").css("margin-right").replace("px", ""));
		var PAGE_SIZE = 8;
		
		if($(this).hasClass("zl-bottom-bar-left")) {
			currentLeft += moveStep;	
		}
		else {
			currentLeft -= moveStep;
		}
		//过度矫正
		$(".zl-bottom-bar-left,.zl-bottom-bar-right").removeClass("disable");
		//判断是否只有一页
		if($("#bottom_menus .main-menu").length <= PAGE_SIZE) {
			currentLeft = 0;
			$(".zl-bottom-bar-left,.zl-bottom-bar-right").addClass("disable");
		}
		else {
			if(currentLeft > 0) {
				currentLeft = 0;
				$(".zl-bottom-bar-left").addClass("disable");
			}
			if(currentLeft < minLeft) {
				currentLeft = minLeft;
				$(".zl-bottom-bar-right").addClass("disable");
			}
		}
		//确认移动
		$("#bottom_menus").animate({marginLeft:currentLeft}, 200);
		
		//弹出层弹出-展会用
		bottomMouseEnter($(".zl-bottom")[0]);
	});
	
	//弹出层鼠标悬停弹出
	$(".zl-bottom").mouseenter(function() {
		bottomMouseEnter(this);
	});
	
	//弹出层鼠标离开收回
	$(".zl-bottom").mouseleave(function() {
		bottomMouseLeave(this);
	});
});

//弹出层弹出事件
function bottomMouseEnter(_this) {
	//检测状态
	if($(_this).attr("expand") == "1") {
		return;
	}
	//是否有二级菜单
	if($(".child-menus").is(":visible")) {
		return;
	}
	//标记展开
	$(_this).attr("expand", "1");
	
	//增量计算
	var upVal = hjb51 * 142 / 1080;
	//位置调整
	$(_this).animate({
		height: "+=" + upVal + "px"
	}, 100);
	//高度增加
	$(".zl-bottom-bar,.zl-bottom-bar>div,.zl-bottom-bar>div>img,.zl-bottom-bar-center>div").animate({
		height: "+=" + upVal + "px"
	}, 100);
	//内容变更
	$(".main-menu").animate({
		paddingTop: upVal + "px"
	}, 100);
	//图标显示
	$(".menu-icon").addClass("show");
}
//弹出层收回事件
function bottomMouseLeave(_this) {
	//检测状态
	if($(_this).attr("expand") != "1") {
		return;
	}

	//是否有二级菜单
	if(arguments.length == 1 && $(".child-menus").is(":visible")) {
		return;
	}
	//标记收起
	$(_this).attr("expand", "0");
	//增量计算
	var upVal = hjb51 * 142 / 1080;
	//位置调整
	$(_this).animate({
		height: "-=" + upVal + "px"
	}, 100);
	//高度增加
	$(".zl-bottom-bar,.zl-bottom-bar>div,.zl-bottom-bar>div>img,.zl-bottom-bar-center>div").animate({
		height: "-=" + upVal + "px"
	}, 100);
	//内容变更
	$(".main-menu").animate({
		paddingTop: "0px"
	}, 100);
	//图标隐藏
	$(".menu-icon").removeClass("show");
}

/* 点击事件 */
function menuto(index, url) {
	//图标跳转判断
	if(arguments.length == 3) {
		//强制跳转
	    if(clickAble) {
	        act(index);
	        jumpto(url);
	    }
	    else {
	        clickAble = true;
	    }
	}
	else {
		//同根按钮
		var rootIndex = index.split("-")[0];
		var rootIndexOld = getCookie("menuActive").split("-")[0];
		if(rootIndex == rootIndexOld) {
			//同根按钮
			//是否点根按钮
			if(index.split("-").length == 1) {
				//根按钮
				$("#bottom_menus>.main-menu:not(:eq(" + index + "))>.child-menus").hide();
				$("#bottom_menus>.main-menu:eq(" + index + ")>.child-menus").toggle();
			}
			else {
				//非根按钮
			    if(clickAble) {
			        act(index);
			        jumpto(url);
			    }
			    else {
			        clickAble = true;
			    }
			}
		}
		else {
			//非同根按钮，是否有根页面
			if(url != "/" && url != "") {
				//有根页面
			    if(clickAble) {
			        act(index);
			        jumpto(url);
			    }
			    else {
			        clickAble = true;
			    }
			}
			else {
				//无根页面，显示二级菜单
				$("#bottom_menus>.main-menu:not(:eq(" + index + "))>.child-menus").hide();
				$("#bottom_menus>.main-menu:eq(" + rootIndex + ")").addClass("show").find(".child-menus").toggle();
				
			}
		}
		//收回弹出层
		bottomMouseLeave($(".zl-bottom"), true);
	}
}
/* 选中事件 */
function act(index) {
    if(index != null) {
        var indexs = index.toString().split("-");
        //根节点样式
        $("#bottom_menus .main-menu").removeClass("cur");
        $("#bottom_menus>.main-menu:eq(" + indexs[0] + ")").addClass("cur");
        //子节点样式
        if(typeof(indexs[1]) != 'undefined') {
        	$("#bottom_menus .child-menu").removeClass("cur");
        	$("#bottom_menus>.main-menu:eq(" + indexs[0] + ") .child-menu:eq(" + indexs[1] + ")").addClass("cur"); 
        }
        
        //记录选择
        SetCookie("menuActive", index);
    }
    else {
    	act("0");
    }
}
/* 移动到合适位置方法 */
function moveCurrent() {
	var PAGE_SIZE = 8;
    var index = getCookie("menuActive").split("-")[0];
	var moveStep = $(".main-menu").width() + parseFloat($(".main-menu").css("margin-right").replace("px", ""));
	var minLeft = $(".zl-bottom-bar-center").width() - $("#bottom_menus").width();
	var currentLeft = 0;
	//判断是否只有一页
	if($("#bottom_menus .main-menu").length <= PAGE_SIZE) {
		$(".zl-bottom-bar-left,.zl-bottom-bar-right").addClass("disable");
	}
	else {
	    //判断是否最后一页
		if($("#bottom_menus .main-menu").length - index < PAGE_SIZE) {
	    	currentLeft = minLeft;
			$(".zl-bottom-bar-right").addClass("disable");
	    }
	    else {
	    	if(index == 0) {
	    		$(".zl-bottom-bar-left").addClass("disable");
	    	}
	    	currentLeft = -1 * moveStep * index;
	    }
	}
    
    //移动
	$("#bottom_menus").css("margin-left", currentLeft);
}
		
/* 获取菜单信息 */
function getMenuListJson() {
	var returnStr = "";
	$.ajax({
		async: false,
		type: "GET",
		url: "WSUserService/getMenuList.json",
		complete: function(xhr, ts) {
			returnStr = xhr.responseText;
		}
	});
	return returnStr;
}

/*
 * 菜单JSON转HTML内容
 */
function json2Html(source) {
    var obj = eval("(" + source + ")");
    if(obj.content) {
        var parentObj = $("#bottom_menus");
        for(var index = 0; index < obj.content.length; index++) {
            var liObj = document.createElement("div");
            liObj.className = "main-menu";
            liObj.id = obj.content[index].moduleId;
            liObj.innerHTML = '<img class="menu-cur" src="images/menu/cur_' + obj.content[index].colour + '.png" />' +
    			'<div class="menu-icon">' +
        		'<img class="menu-icon-bg" src="images/menu/menu-bg-' + obj.content[index].colour + '.png" />' +
        		'<img class="menu-icon-bgDisable" src="images/menu/menu-bg-dark.png" />' +
            	'<img class="menu-icon-info" src="images/menu/' + obj.content[index].moduleId + '.png" />' + 
        		'</div>' +
            	'<span>' + obj.content[index].label + '</span>' + 
            	'<img class="menu-foot" src="images/menu/menu_foot_' + 
            	obj.content[index].colour + '.png"/>';
            liObj.setAttribute("onclick", "menuto(\'" + index + "\', \'" + obj.content[index].url + "\');");
            if(obj.content[index].pages) {
                var ulChild = document.createElement("div");
                ulChild.className = "child-menus";
                $(ulChild).click(function() {return false;});
                var ulChildBg = document.createElement("div");
                ulChildBg.className = "child-menus-bg";
                $(ulChildBg).appendTo(ulChild);
                var ulChildIcon = document.createElement("img");
                ulChildIcon.className = "child-menus-icon";
                ulChildIcon.src = "./images/menu/" + obj.content[index].moduleId + ".png";
                ulChildIcon.setAttribute("onclick", "menuto(\'" + index + "\', \'" + obj.content[index].url + "\', true);");
                $(ulChildIcon).appendTo(ulChild);
                for(var indexChild = 0; indexChild < obj.content[index].pages.length; indexChild++) {
                    var objChild = obj.content[index].pages[indexChild];
                    var liChild = document.createElement("div");
                    liChild.className = "child-menu";
                    if(objChild.pageId) {
                        liChild.id = objChild.pageId;
                    }
                    liChild.innerHTML = '<span>' + objChild.label + '</span>';
                    liChild.setAttribute("onclick", "menuto(\'" + (index + "-" + indexChild) + "\', \'" + objChild.url + "\');");

                    if(objChild.pages) {
                        var ulSub = document.createElement("div");
                        ulSub.className = "sub-menus";
                        var ulSubBg = document.createElement("div");
                        ulSubBg.className = "child-menus-bg";
                        $(ulSubBg).appendTo(ulSub);
                        for(var indexSub = 0; indexSub < objChild.pages.length; indexSub++) {
                            var objSub = objChild.pages[indexSub];

                            var liSub = document.createElement("div");
                            liSub.className = "sub-menu";
                            if(objSub.pageId) {
                                liSub.id = objSub.pageId;
                            }
                            liSub.innerHTML = '<span>' + objSub.label + '</span>';
                            liSub.setAttribute("onclick", "menuto(\'" + (index + "-" + indexChild + "-" + indexSub) + "\', \'" + objSub.url + "\');");

                            ulSub.appendChild(liSub);
                        }
                        liChild.appendChild(ulSub);
                    }

                    ulChild.appendChild(liChild);
                }

                liObj.appendChild(ulChild);
            }

            parentObj.append(liObj);
        }
    }
}