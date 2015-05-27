var divSelBox = []; //div弹出层上选择的checkBox值；
var selBox = [];//页面中选择的checkBox对象
var saveUserIds = []; //需要保存的值

/**
 * checkbox点击处理方法
 * @param obj
 */
function divCboxClick(obj) {
    if (obj.checked) {
        divSelBox.push(obj.value);
    } else {
        var cks = document.getElementsByName(obj.name);
        divSelBox.length = 0;
        for (var i = 0; i<cks.length; i++) {
            if (cks[i].checked)divSelBox.push(cks[i].value);
        }
    }
}

function cboxClick(obj) {
    if (obj.checked) {
        selBox.push(obj);
    } else {
        var cks = document.getElementsByName(obj.name);
        selBox.length = 0;
        for (var i = 0; i<cks.length; i++) {
            if (cks[i].checked)selBox.push(cks[i]);
        }
    }
}

function initUserIds(id) {
    var suis = document.getElementById(id + "saveUserIds").value;
    if (suis != '')saveUserIds = suis.split(",");
}

function saveUserIds2Str(id) {
    document.getElementById(id + "saveUserIds").value = saveUserIds.join(",");
//    alert(document.getElementById(id + "saveUserIds").value);
}


function cmd_confirm() {
   for (var i=0; i<divSelBox.length; i++) {
       saveUserIds.push(divSelBox[i]);
   }
    divSelBox.length = 0;
}

function del(id) {
    var cks = document.getElementsByName(id + "userId");
    saveUserIds.length = 0;
    for (var i = 0; i < cks.length; i++) {
        saveUserIds.push(cks[i].value);
    }
    selBox.length=0;
}