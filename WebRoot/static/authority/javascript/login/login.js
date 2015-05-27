var box;

function showBlockedDialog (id) {
    $.blockUI ({
        message: $('#' + id),
        bindEvents: false,
        css: {border:'none',textAlign:'left', left:"10%", top:"5%"}
    });
}

function login () {
    var user = {userName:$ ('#userName').val (), password:$ ('#password').val ()};
    $.ajax ({
        data: user,
        url : 'login/login.ajax',
        type : 'POST',
        success : onAjaxSuccess
    });
}

function onAjaxSuccess (result) {
    var ret = eval ('(' + result + ')');
    if (ret.status == 'ok') {
        $.unblockUI ();
        location.reload();
    } else if (ret.status == 'fail') {
        $('#message-holder').html (message.error.LOGIN_FAIL);
    }
}

function changePassword () {
    var oldPwd = $('#oldPwd').val ();
    var newPwd = $('#newPwd').val ();
    var chkPwd = $('#chkPwd').val ();
    
    newPwd = $.trim(newPwd);
    if(newPwd == ''){
    	$('#message-holder').html('修改密码不能为空!');
    	return;
    }
    var rePsd = /^[a-zA-Z0-9]+$/;
	if (!rePsd.test(newPwd)){
		$('#message-holder').html(message.error.password_check_msg);
		return;
	}
	if (newPwd.length < 6 ||
			newPwd.length > 16){
		$('#message-holder').html(message.error.password_length_check_msg);
		return;
	}

    if (newPwd != chkPwd) {
        $('#message-holder').html (message.error.PASSWORD_NOT_MATCH);
        return;
    }

    $.ajax ({
        data : {oldPassword: oldPwd, newPassword: newPwd},
        url : 'login/changePassword.ajax',
        type : 'POST',
        success : function (text) {
            var ret = eval ('(' + text + ')');
            if (ret.status == 'ok') {
                $.unblockUI ();
                $.blockUI ({
                    message: message.success.PASSWORD_CHANGED,
                    bindEvents: false,
                    css: {border:'none',textAlign:'left'}
                });
                setTimeout (function () {$.unblockUI();}, 3000);
            } else {
                switch (ret.cause)  {
                    case 'not-login-in' :
                        $('#message-holder').html (message.error.NOT_LOGIN);
                        break;
                    case 'old-password' :
                        $('#message-holder').html (message.error.PASSWORD_NOT_MATCH);
                        break;
                    case 'internal error' :
                        $('#message-holder').html (message.error.PASSWORD_NOT_MATCH);
                        break;
                }
            }
        }
    });
}