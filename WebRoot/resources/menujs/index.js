function act(index) {
    if(index != null) {
        var indexs = index.toString().split("-");
        indexs[0]++;
        $("#menus li").removeClass("active");
        $("#menus>li:eq(" + indexs[0] + ")").addClass("active");

        var selectStr = "#menus>li:eq(" + indexs[0] + ")";
        for(var indexLi = 1; indexLi < indexs.length; indexLi++) {
            selectStr += ">ul>li:eq(" + indexs[indexLi] + ")";
            $(selectStr).addClass("active");
        }

        setCookie("menuActive", index, 24);
    }
    else {
    	act("0");
    }
}

var clickAble = true;
function menuto(index, url) {
    if(clickAble) {
        act(index);
        jumpto(url);
    }
    else {
        clickAble = true;
    }
}

function json2Html(source) {
    var obj = eval("(" + source + ")");
    if(obj.content) {
        var parentObj = $("#menus");
        for(var index = 0; index < obj.content.length; index++) {
            var liObj = document.createElement("li");
            liObj.id = obj.content[index].moduleId;
            liObj.innerHTML = '<a href="javascript:void(0);" onclick="menuto(\'' + index + '\', \'' + obj.content[index].url + '\');">' + obj.content[index].label + '</a>';
            if(obj.content[index].pages) {
                var ulChild = document.createElement("ul");
                for(var indexChild = 0; indexChild < obj.content[index].pages.length; indexChild++) {
                    var objChild = obj.content[index].pages[indexChild];
                    var liChild = document.createElement("li");
                    if(objChild.pageId) {
                        liChild.id = objChild.pageId;
                    }
                    liChild.innerHTML = '<a href="javascript:void(0);" onclick="menuto(\'' + (index + "-" + indexChild) + '\', \'' + objChild.url + '\');">' + objChild.label + '</a>';

                    if(objChild.pages) {
                        var ulSub = document.createElement("ul");
                        for(var indexSub = 0; indexSub < objChild.pages.length; indexSub++) {
                            var objSub = objChild.pages[indexSub];

                            var liSub = document.createElement("li");
                            if(objSub.pageId) {
                                liSub.id = objSub.pageId;
                            }
                            liSub.innerHTML = '<a href="javascript:void(0);" onclick="menuto(\'' + (index + "-" + indexChild + "-" + indexSub) + '\', \'' + objSub.url + '\');">' + objSub.label + '</a>';

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

function setCookie(name, value, Hours) {
    var d = new Date();
    var offset = 8;
    var utc = d.getTime() + (d.getTimezoneOffset() * 60000);
    var nd = utc + (3600000 * offset);
    var exp = new Date(nd);
    exp.setTime(exp.getTime() + Hours * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";path=/;expires=" + exp.toGMTString() + ";";
}

function getCookie(name) {
    var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
    if (arr != null) return unescape(arr[2]);
    return null
}