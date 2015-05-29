/*!
 * autosize Plugin v2.1.0
 *
 * Power by jQuery
 * Author God Tang
 * Released under the MIT license
 */
(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD
        define(['jquery'], factory);
    } else if (typeof exports === 'object') {
        // CommonJS
        factory(require('jquery'));
    } else {
        // Browser globals
        factory(jQuery);
    }
}(function ($) {
    var oldDesignWidth = 0, oldDesignHeight = 0, designWidth = 0, designHeight = 0, limitWidth = 0, limitHeight = 0, currentWidth = 0, currentHeight = 0, minRatio = 0, maxRatio = 0;
    var initCount = 0;
    var returnFunc = [];
    var hideType = true;
    
    /*
     * 缩放转换
     * oldSize: 设计值(float)
     * wh: 缩放方向("w" || "h")
     * */
    function getNewSize(oldSize, wh) {
        //错误排除
        if(designWidth == 0 || designHeight == 0 || currentWidth == 0 || currentHeight == 0) return 0;
        //最小值判断
        if(currentWidth < limitWidth) {currentWidth = limitWidth;}
        if(currentHeight < limitHeight) {currentHeight = limitHeight;}

        //宽高比判断
        if(minRatio != 0 || maxRatio != 0) {
            var currentRatio = currentWidth / currentHeight;
            if(currentRatio < minRatio) {
                //低于最小值，高度限定
                currentHeight = currentWidth / minRatio;
            }
            if(currentRatio > maxRatio) {
                //高于最大值，宽度限定
                currentWidth = currentHeight * maxRatio;
            }
        }

        if(oldSize == "auto") return "auto";

        try {
            if(!isNaN(parseFloat(oldSize)) && parseFloat(oldSize) != 0) {
                oldSize = parseFloat(oldSize);
            }
        }
        catch (e) {
            return 0;
        }
        if(wh == "w") {
            //宽度
            return oldSize * currentWidth / designWidth + "px";
        }
        else {
            //高度
            return oldSize * currentHeight / designHeight + "px";
        }
    }

    /*
     * 检测属性是否存在
     * obj: 对象(object)
     * key: 属性名(string)
     * */
    function sizeCheck(obj, key) {
        var value = $(obj).css(key);
        var returnValue = false;
        try {
            if(!isNaN(parseFloat(value)) && parseFloat(value) != 0) {
                returnValue = true;
            }
        }
        catch (e) {}
        return returnValue;
    }

    /*
     * 尺寸变化
     * obj: 对象(object)
     * */
    function sizeChange(obj) {
        //获取当前大小
        currentWidth = $(window).width();
        currentHeight = $(window).height();

        var newWidth = ["width", "w", null];
        var newHeight = ["height", "h", null];
        var newLeft = ["left", "w", null];
        //var newRight = ["right", "w", null];
        var newMarginLeft = ["margin-left", "w", null];
        var newMarginRight = ["margin-right", "w", null];
        var newPaddingLeft = ["padding-left", "w", null];
        var newPaddingRight = ["padding-right", "w", null];
        var newTop = ["top", "h", null];
        //var newBottom = ["bottom", "h", null];
        var newFontSize = ["font-size", "h", null];
        var newLineHeight = ["line-height", "h", null];
        var newMarginTop = ["margin-top", "h", null];
        var newMarginBottom = ["margin-bottom", "h", null];
        var newPaddingTop = ["padding-top", "h", null];
        var newPaddingBottom = ["padding-bottom", "h", null];
        //var sizeAttribute = [newWidth, newHeight, newLeft, newRight, newMarginLeft, newMarginRight, newPaddingLeft, newPaddingRight, newTop, newBottom, newFontSize, newLineHeight, newMarginTop, newMarginBottom, newPaddingTop, newPaddingBottom];
        var sizeAttribute = [newWidth, newHeight, newLeft, newMarginLeft, newMarginRight, newPaddingLeft, newPaddingRight, newTop, newFontSize, newLineHeight, newMarginTop, newMarginBottom, newPaddingTop, newPaddingBottom];

        //缓存新尺寸
        for(var index = 0; index < sizeAttribute.length; index++) {
            if(sizeCheck(obj, sizeAttribute[index][0])) {
                sizeAttribute[index][2] = getNewSize($(obj).css(sizeAttribute[index][0]), sizeAttribute[index][1]);
            }
        }

        //数值变化
        for(var index = 0; index < sizeAttribute.length; index++) {
            if(sizeAttribute[index][2] != null) {
                $(obj).css(sizeAttribute[index][0], sizeAttribute[index][2]);
            }
        }
    }

    /*
     * 初始化尺寸变化
     * obj: 对象(object)
     * */
    function sizeChangeReload(obj) {
        //获取当前大小
        currentWidth = designWidth;
        currentHeight = designHeight;

        var newWidth = ["width", "w", null];
        var newHeight = ["height", "h", null];
        var newLeft = ["left", "w", null];
        //var newRight = ["right", "w", null];
        var newMarginLeft = ["margin-left", "w", null];
        var newMarginRight = ["margin-right", "w", null];
        var newPaddingLeft = ["padding-left", "w", null];
        var newPaddingRight = ["padding-right", "w", null];
        var newTop = ["top", "h", null];
        //var newBottom = ["bottom", "h", null];
        var newFontSize = ["font-size", "h", null];
        var newLineHeight = ["line-height", "h", null];
        var newMarginTop = ["margin-top", "h", null];
        var newMarginBottom = ["margin-bottom", "h", null];
        var newPaddingTop = ["padding-top", "h", null];
        var newPaddingBottom = ["padding-bottom", "h", null];
        //var sizeAttribute = [newWidth, newHeight, newLeft, newRight, newMarginLeft, newMarginRight, newPaddingLeft, newPaddingRight, newTop, newBottom, newFontSize, newLineHeight, newMarginTop, newMarginBottom, newPaddingTop, newPaddingBottom];
        var sizeAttribute = [newWidth, newHeight, newLeft, newMarginLeft, newMarginRight, newPaddingLeft, newPaddingRight, newTop, newFontSize, newLineHeight, newMarginTop, newMarginBottom, newPaddingTop, newPaddingBottom];

        //缓存新尺寸
        for(var index = 0; index < sizeAttribute.length; index++) {
            if(sizeCheck(obj, sizeAttribute[index][0])) {
                sizeAttribute[index][2] = getNewSize($(obj).css(sizeAttribute[index][0]), sizeAttribute[index][1]);
            }
        }

        //数值变化
        for(var index = 0; index < sizeAttribute.length; index++) {
            if(sizeAttribute[index][2] != null) {
                $(obj).css(sizeAttribute[index][0], sizeAttribute[index][2]);
            }
        }
    }

    /*
     * 遮盖屏闪层
     * type: 显示/隐藏(bool)
     * */
    function bodyHide(type) {
		if(type) {
	    	if(hideType) {	//全局开关
	            if($("#autosize_body_hide").length > 0) {
	                $("#autosize_body_hide").show();
	            }
	            else {
	                var obj = document.createElement("div");
	                $(obj).attr("id", "autosize_body_hide").css({
	                    position: "fixed",
	                    left: "0",
	                    top: "0",
	                    right: "0",
	                    bottom: "0",
	                    display: "block",
	                    backgroundColor: "White",
	                    zIndex: "200003"
	                }).addClass("auto-size-exclude").appendTo("body");
	            }
	        }
    	}
        else {
            $("#autosize_body_hide").fadeOut();
        }
    }

    /*
     * 初始化设计大小
     * width: 设计宽度(float)
     * height: 设计高度(float)
     * minWidth: 限定最小宽度(float)
     * minHeight: 限定最小高度(float)
     * */
    $.autosizeInit = function() {
        try {
            if(arguments.length < 4) {
                console.log("参数错误");
                return;
            }
            else if(arguments.length > 5) {
                minRatio = parseFloat(arguments[4]);
                maxRatio = parseFloat(arguments[5]);
            }
            if(initCount == 0) {
                oldDesignWidth = parseFloat(arguments[0]);
                oldDesignHeight = parseFloat(arguments[1]);
                initCount = 1;
            }
            designWidth = parseFloat(arguments[0]);
            designHeight = parseFloat(arguments[1]);
            limitWidth = parseFloat(arguments[2]);
            limitHeight = parseFloat(arguments[3]);
        }
        catch(e) {}
    }

    /*
     * 初始化缩放
     * */
    $.autosizeReload = function() {
        if(designWidth == 0 || designHeight == 0) {
            console.log("autosizeAll函数没有被初始化，请执行autosizeInit函数");
            return;
        }
        $(".auto-size,.auto-size-absolute").each(function() {
            sizeChangeReload(this);
        });

        $.autosizeInit(currentWidth, currentHeight, limitWidth, limitHeight);
    }

    /*
     * 还原缩放
     * */
    $.autosizeReset = function() {
        if(designWidth == 0 || designHeight == 0) {
            console.log("autosizeAll函数没有被初始化，请执行autosizeInit函数");
            return;
        }

        $.autosizeInit(oldDesignWidth, oldDesignHeight, limitWidth, limitHeight);
    }

    /*
     * 页面auto-size, auto-size-absolute元素缩放
     * */
    $.autosizeMore = function() {
        bodyHide(true);
        $.autosizeReload();
        if(designWidth == 0 || designHeight == 0) {
            console.log("autosizeAll函数没有被初始化，请执行autosizeInit函数");
            return;
        }
        $(".auto-size,.auto-size-absolute:not(.auto-size-exclude)").each(function() {
        	sizeChange(this);
        });

        $.autosizeInit(currentWidth, currentHeight, limitWidth, limitHeight);
        
        if(returnFunc.length > 0) {
        	for(var funcIndex = 0; funcIndex < returnFunc.length; funcIndex++) {
        		returnFunc[funcIndex]();
        	}
        }
        bodyHide(false);
    }

    /*
     * 页面单个auto-size, auto-size-absolute元素及其子元素缩放
     * */
    $.autosizeElement = function() {
    	var element = null, children = null, reFunc = null;
    	if(arguments.length >= 1) {
    		element = arguments[0];
    	}
    	if(arguments.length >= 2) {
    		children = arguments[1];
    	}
    	if(arguments.length >= 3) {
    		reFunc = arguments[2];
    	}
    	
        bodyHide(true);
        $.autosizeReset();
        //$.autosizeReload();
        if(designWidth == 0 || designHeight == 0) {
            console.log("autosizeElement函数没有被初始化，请执行autosizeInit函数");
            return;
        }
        if(!$(element).hasClass("auto-size-exclude")) {
            sizeChange(element);
        	if(children) {
        		$(element).find("*:not(.auto-size-exclude)").each(function() {
                	sizeChange(this);
                });
        	}
        }

        $.autosizeInit(currentWidth, currentHeight, limitWidth, limitHeight);
        
        if(reFunc != null && typeof(reFunc) == 'function') {
        	reFunc();
        }
        
        bodyHide(false);
    }

    /*
     * 页面所有元素缩放
     * */
    $.autosizeAll = function() {
        $("body *:not(.auto-size-exclude)").addClass("auto-size");
    }

    /*
     * 页面单个排除元素及其子元素设定
     * */
    $.autosizeExclude = function(element, children) {
        $(element).removeClass("auto-size").removeClass("auto-size-absolute").addClass("auto-size-exclude");
        if(children) {
            $(element).children().each(function() {
                $.autosizeExclude(this, true);
            });
        }
    }

    /*
     * 页面单个排除元素及其子元素设定
     * */
    $.autosizeReturn = function(func) {
    	if(typeof(func) == 'function') {
    		returnFunc.push(func);
    	}
    }
    
    /*
     * 是否显示全屏遮罩层方法
     */
    $.autosizeFrame = function(type) {
    	if(type == false) {
        	hideType = false;	
    	}
    }

    $(window).resize(function() {
        $.autosizeMore();
    });
}));
