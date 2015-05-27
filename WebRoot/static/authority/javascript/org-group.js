function checkCode(obj) {
    if (obj && obj.value != '') {
        orgGroup.checkCode(obj.value, function(responseData) {
            var result = eval(responseData.responseText);
            if (result) {
                changeDiv('base_div', 'tree_div', 'user_div');
                alert(message.error.orgGroup_code_valid_msg);
                obj.value = '';
            }
        });
    }
}

function checkName(obj) {
    if (obj && obj.value != '') {
        var groupId = document.getElementById("groupId").value;
        orgGroup.checkName(obj.value, groupId, function(responseData) {
            var result = eval(responseData.responseText);
            if (result) {
                changeDiv('base_div', 'tree_div', 'user_div');
                alert(message.error.orgGroup_name_valid_msg);
                obj.value = '';
            }
        });
    }
}

function check() {
    var code = document.getElementById("code").value;
    var name = document.getElementById("groupName").value;
    if (code == ''){
        changeDiv('base_div', 'tree_div', 'user_div');
        alert(message.error.orgGroup_code_check_msg);
        return;
    }
    if (name == '') {
        changeDiv('base_div', 'tree_div', 'user_div');
        alert(message.error.orgGroup_name_check_msg);
        return;
    }
    /*if (document.getElementById("errorCodeInfo").innerHTML != "") {
        changeDiv('base_div', 'tree_div', 'user_div')
        return;
    }*/
    var orgLength = document.getElementsByName("orgId").length;
    if (orgLength == 0) {
        alert(message.error.orgGroup_msg);
        changeDiv('tree_div', 'base_div', 'user_div');
        return;
    }
    /*var groupId = document.getElementById("groupId").value;
    if (groupId == ''){orgGroup.checkCode(code, function(responseData) {
            var result = eval(responseData.responseText);
            if (result) {
                changeDiv('base_div', 'tree_div', 'user_div');
                alert(message.error.orgGroup_code_valid_msg);
            } else {
                __client_stub (document.getElementById("btnSave"), 'click', null);
            }
        });
    } else*/
    document.getElementById("save_btn").disabled = true;
    __client_stub (document.getElementById("btnSave"), 'click', null);
}

function changeDiv(showDiv, hidenDiv, hidenDiv1) {
    document.getElementById(showDiv).style.display = "block";
    if (hidenDiv)document.getElementById(hidenDiv).style.display = "none";
    if (hidenDiv1)document.getElementById(hidenDiv1).style.display = "none";
}

function cmd_new_user(){
    query_user();
    jQuery.blockUI({ message: jQuery('#userDiv') });
}

function del_row(id) {
    var table = document.getElementById(id + "userTab");
    for (var i=selBox.length - 1; i>=0; i--) {
        var ck = selBox[i];
        table.deleteRow(ck.parentNode.parentNode.rowIndex);
    }
    del(id);
    saveUserIds2Str(id);
}

function query_user() {
    jQuery.post('user.jscx', {method:"ajax",userIds:jQuery('#users_saveUserIds').val(),loginName:jQuery('#loginName').val(), fieldName:"loginName,nickName"},function(res){
            jQuery("#userData").html(res);
    });
}

function cmd_cancel(){
	jQuery.unblockUI();
}

function cmd_confirm_user() {
    cmd_confirm();
    saveUserIds2Str("users_");
    jQuery('#btnQuery').click();
    cmd_cancel();
}
