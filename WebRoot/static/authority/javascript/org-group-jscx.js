var saveBoxes = []; //保留需要保存的值
var delBoxes = [];  //保留需要移除的值
var divSaveBoxes = []; //保留div层上选择的值

/**
 * checkbox点击处理方法
 * @param obj
 */
function cboxClick(obj) {
    if (obj.checked) {
        delBoxes.push(obj);
    } else {
        var cks = document.getElementsByName(obj.name);
        delBoxes.length = 0;
        for (var i = 0; i<cks.length; i++) {
            if (cks[i].checked)delBoxes.push(cks[i]);
        }
    }
}

function divBoxClick(obj) {
    if (obj.checked) {
        divSaveBoxes.push(obj);
    } else {
        var cks = document.getElementsByName(obj.name);
        divSaveBoxes.length = 0;
        for (var i = 0; i<cks.length; i++) {
            if (cks[i].checked)divSaveBoxes.push(cks[i]);
        }
    }
}

/**
 * 从表格移除行
 * @param id  page页面定义的jscx的id
 * @param b  是否从表格中将行移除
 */
function delGroupRow (id, b) { //jscx定义的id  b:是否删除table行
    var table = document.getElementById(id+"_groupTab");
    var delIds = document.getElementById(id+"_delIds");
    for (var i = delBoxes.length-1; i >= 0; i --) {
        var box = delBoxes[i];
        delIds.value += "@_@" + box.value;
    }
    if (b && b == true) {
        var cks = document.getElementsByName(id + "_group_ck");
        for (var i = cks.length -1; i >= 0; i --) {
            if (!cks[i].checked) continue;
            var tr = cks[i].parentNode.parentNode;
            table.deleteRow(tr.rowIndex);
        }
    }
    delIds.value = delIds.value.substr(3);
    delBoxes.length=0;
}

function saveGroupRel (id) {
    var saveIds = document.getElementById(id + "saveIds");
    for (var i = 0; i < saveBoxes.length; i ++) {
        var box = saveBoxes[i];
        saveIds.value += "@_@" + box.value;
    }
    for (var i = 0; i < delBoxes.length; i++) {
        var box = delBoxes[i];
        saveIds.value += "@_@" + box.value;
    }
    saveIds.value = saveIds.substr(saveIds.value.indexOf("@_@"));
}

/**
 * 设置需要保留的值
 * @param id
 */
function resetGroup(id) {
    var saveIds = document.getElementById(id + "saveIds");
    var ids = saveIds.split("@_@");
    for (var i = 0; i < ids.length; i ++) {
        var box = document.getElementsByName(id + "_group_ck");
        for (var j = 0; j < box.length; j++) {
            if (ids[i] == box[j].value) saveBoxes.push(box[j]);
        }
    }
}

/**
 * 从数据库删除
 * id page中jscx的id 必填
 * @param obj 删除的orgGroupId 不填从checkBox获取
 */
function delGroup (id, obj) {
    if (delBoxes.length == 0) {
        alert(message.error.select_target);
        return;
    }
    if (confirm(message.confirm.del_msg)){
        var param = '';
        if (obj) param = obj;
        else {
            delGroupRow(id);
            param = document.getElementById(id + "_delIds").value;
        }
        orgGroup.delGroupByIds(param, function(responseData) {
           var result = eval(responseData.responseText);
           if (result.msg) {
               if (result.data == '') alert(message.success.del_msg);
               else {
                   alert(message.error.rel_del_msg + "\n" + result.data);
               }
               location.reload();
           } else alert(message.error.del_msg);
        });
    }
}

