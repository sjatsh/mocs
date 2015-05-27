/**
 * 模式对话框js;
 */

// global variables //
var TIMER = 0;
var SPEED = 100;
var WRAPPER = 'content';

var last_position = {x:0, y:0};
var dragging = false;

// calculate the current window width //
function pageWidth() {
  return window.innerWidth != null ? window.innerWidth : document.documentElement && document.documentElement.clientWidth ? document.documentElement.clientWidth : document.body != null ? document.body.clientWidth : null;
}

// calculate the current window height //
function pageHeight() {
  return window.innerHeight != null? window.innerHeight : document.documentElement && document.documentElement.clientHeight ? document.documentElement.clientHeight : document.body != null? document.body.clientHeight : null;
}

// calculate the current window vertical offset //
function topPosition() {
  return typeof window.pageYOffset != 'undefined' ? window.pageYOffset : document.documentElement && document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop ? document.body.scrollTop : 0;
}

// calculate the position starting at the left of the window //
function leftPosition() {
  return typeof window.pageXOffset != 'undefined' ? window.pageXOffset : document.documentElement && document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft ? document.body.scrollLeft : 0;
}

// build/show the dialog box, populate the data and call the fadeDialog function //
function showDialog(title,message,type,autohide, okAction, cancelAction) {
  if(!type) {
    type = 'error';
  }
  if ("prompt" == type){
	  var dialog;
	  var dialogheader;
	  var dialogclose;
	  var dialogtitle;
	  var dialogcontent;
	  var dialogmask;
	  var content_panel;
	  var button_panel;
	  var btnOK, btnCancel;
	  if(!document.getElementById('dialog')) {
	    dialog = document.createElement('div');
	    dialog.id = 'dialog';
	    dialogheader = document.createElement('div');
	    dialogheader.id = 'dialog-header';
	    dialogtitle = document.createElement('div');
	    dialogtitle.id = 'dialog-title';
	    dialogclose = document.createElement('div');
	    dialogclose.id = 'dialog-close';
	    dialogcontent = document.createElement('div');
	    dialogcontent.id = 'dialog-content';
	
	      dialogmask = document.getElementById('dialog-mask') || _('div', 'dialog-mask', null);
	/*
	      if (!dialogmask) {
	          dialogmask = ;
	      }
	*/
	//    dialogmask = document.createElement('div');
	//    dialogmask.id = 'dialog-mask';
	//    document.body.appendChild(dialogmask);
	    document.body.appendChild(dialog);
	    dialog.appendChild(dialogheader);
	    dialogheader.appendChild(dialogtitle);
	    dialogheader.appendChild(dialogclose);
	    dialog.appendChild(dialogcontent);
	    dialogclose.setAttribute('onclick','hideDialog()');
	    dialogclose.onclick = hideDialog;
		
		content_panel = document.createElement('div');
		content_panel.id = 'dialog-content-panel';
		button_panel = document.createElement('div');
		button_panel.id = 'dialog-button-panel';
		dialogcontent.appendChild(content_panel);
		dialogcontent.appendChild(button_panel);
		
		var ul = document.createElement('ul');
		ul.className = 'dialog-button-list';
		var li = document.createElement('li');
		var a = document.createElement('a');
		a.id = 'dialog-btn-ok';
		a.setAttribute ('href', '#');
		a.innerHTML = '确定';
		li.appendChild(a);
		ul.appendChild(li);
		btnOK = a;
		
		li = document.createElement('li');
		a = document.createElement('a');
		a.id = 'dialog-btn-cancel';
		a.innerHTML = '取消';
		a.setAttribute ('href', '#');
		li.appendChild(a);
		ul.appendChild(li);	
		btnCancel = a;
		button_panel.appendChild(ul);
	  } else {
	    dialog = document.getElementById('dialog');
	    dialogheader = document.getElementById('dialog-header');
	    dialogtitle = document.getElementById('dialog-title');
	    dialogclose = document.getElementById('dialog-close');
	    dialogcontent = document.getElementById('dialog-content');
	    dialogmask = document.getElementById('dialog-mask');
		content_panel = document.getElementById('dialog-content-panel');
		btnOK = document.getElementById('dialog-btn-ok');
		btnCancel = document.getElementById('dialog-btn-cancel');
	  }
	  dialog.style.opacity = 1.00;
	  dialog.style.filter = 'alpha(opacity=100)';
	  dialog.alpha = 100;
	    dialogmask.style.visibility = "visible";
	    dialog.style.visibility = "visible";
	  var width = pageWidth();
	  var height = pageHeight();
	  var left = leftPosition();
	  var top = topPosition();
	  var dialogwidth = dialog.offsetWidth;
	  var dialogheight = dialog.offsetHeight;
	  var topposition = top + (height / 3) - (dialogheight / 2);
	  var leftposition = left + (width / 2) - (dialogwidth / 2);
	  dialog.style.top = topposition + "px";
	  dialog.style.left = leftposition + "px";
	  dialogheader.className = type + "header";
	  dialogtitle.innerHTML = title;
	  dialogcontent.className = type;
	  content_panel.innerHTML = message;
	  var content = document.getElementById(WRAPPER);
	  dialogmask.style.height = pageHeight () + 'px';
	//  dialog.timer = setInterval("fadeDialog(1)", TIMER);
	  if(autohide) {
	    dialogclose.style.visibility = "hidden";
	//    window.setTimeout("hideDialog()", (autohide * 1000));
	      dialog.style.visibility = 'hidden';
	  } else {
	    dialogclose.style.visibility = "visible";
	      dialog.style.visibility = 'visible';
	      dialog.style.display = 'block';
	  }
	  
	  if (type == 'error') btnCancel.style.visibility = 'hidden';
	  else btnCancel.style.visibility = 'visible';
	
	    if (okAction) dialog.okAction = okAction;
	    if (cancelAction) dialog.cancelAction = cancelAction;
	  
	  btnOK.onclick = function () {
	      dialog.currentAction = 'ok';
	//      hideDialog ();
	      if(okAction) okAction ();
	//      closeDialog("dialog", null);
	      hideDialog ();
	      if (title == '错误提示'){
	      		document.getElementById('dialog-mask').style.visibility = 'visible';	
	      }
	  };
	  btnCancel.onclick = function () {
	      dialog.currentAction = 'cancel';
	//	  hideDialog ();
	//      cancelAction ();
	      closeDialog("dialog", null);
	  };
	   
	   
	  if (BROWSER && BROWSER.isIE6) {
	      var __set = findSelectControl ();
		  if (__set.length) for (var i = 0; i < __set.length; i ++) {
			  __set [i].style.visibility = "hidden";
		  }
	  }
  
  }else{
	  alert(message);
  }
}

// hide the dialog box //
function hideDialog() {
  var dialog = document.getElementById('dialog');
  clearInterval(dialog.timer);
  dialog.timer = setInterval("fadeDialog(0)", TIMER);
  if (BROWSER && BROWSER.isIE6) {
      var __set = findSelectControl ();
      if (__set.length) for (var i = 0; i < __set.length; i ++) {
          __set [i].style.visibility = "visible";
      }
  }
}

// fade-in the dialog box //
function fadeDialog(flag) {
  if(flag == null) {
    flag = 1;
  }
  var dialog = document.getElementById('dialog');
  var value;
  if(flag == 1) {
    value = dialog.alpha + SPEED;
  } else {
    value = dialog.alpha - SPEED;
  }
  dialog.alpha = value;
  dialog.style.opacity = (value / 100);
  dialog.style.filter = 'alpha(opacity=' + value + ')';
  if(value >= 99) {
    clearInterval(dialog.timer);
    dialog.timer = null;
  } else if(value <= 1) {
    dialog.style.visibility = "hidden";
    document.getElementById('dialog-mask').style.visibility = "hidden";
      document.getElementById('dialog-mask').style.visibility = 'hidden';
    clearInterval(dialog.timer);

//      if (dialog.currentAction == 'ok' && dialog.okAction) dialog.okAction ();
//      else if (dialog.currentAction == 'cancel' && dialog.cancelAction) dialog.cancelAction ();
  }
}
/**
 * 查找所有的下拉选择框;
 * @param node
 * @returns {Array}
 */
function findSelectControl (node) {
    var __set = [];
	if (!node) node = document;
	var tmp = node.childNodes;
	if (tmp && tmp.length) for (var i = 0; i < tmp.length; i ++) {
		var n = tmp [i];
		if (n.nodeName.toLocaleLowerCase() == 'select')
			__set.push(n);
		else if (n.nodeType == 1) {
            var a = findSelectControl (n);
            if (a.length) for (var j = 0; j < a.length; j ++) {
                __set.push (a[j]);
            }
        }
	}
    return __set;
}
/**
 * 居中对话框;
 * @param dialog
 */
function centerDialog (dialog) {
    if (!dialog) return;
    var W = pageWidth(), H = pageHeight();
    var L = leftPosition(), T = topPosition();
    var w = dialog.offsetWidth, h = dialog.offsetHeight;
    var x = L + (W - w) / 2, y = T + (H - h) / 2;
    dialog.style.left = x + 'px';
    dialog.style.top = y + 'px';
}
/**
 * 打开对话框;
 * @param id
 * @param container
 * @param dragSupport
 * @param m 是否打开遮罩;
 */
function openDialog (id, container, dragSupport,m) {
	m = (m== undefined) ? true:m;
    var dialog = document.getElementById(id);
    dialog.style.display = 'block';
    centerDialog(dialog);

    if (container && BROWSER && BROWSER.isIE6) {
        var a = findSelectControl(document.getElementById(container));
        if (a && a.length) for (var i = 0; i < a.length; i ++) {
            a [i].style.visibility = 'hidden';
        }
    }
    
    if(m){
    	_mask();
    }

    if (dragSupport) {
        var title = dialog.firstChild;
        while (title.nodeType != 1) title = title.nextSibling;
        title.style.cursor = 'move';
        document.onselectstart = function () { return false; };
        title.onmousedown = function () {
            dragging = true;
            last_position.x = event.clientX;
            last_position.y = event.clientY;
        };
        title.onmouseup = function () { dragging = false; }
        title.onmousemove = function () {
            if (!dragging ) return;
            var x = event.clientX, y = event.clientY;
            var dx = x - last_position.x, dy = y - last_position.y;
            var oldX = parseInt (dialog.style.left, 10), oldY = parseInt (dialog.style.top, 10);
            var newX = oldX + dx, newY = oldY + dy;
            dialog.style.left = newX + 'px';
            dialog.style.top = newY + 'px';
            last_position.x = x;
            last_position.y = y;
        };
    }
}

function _mask(flag){
	var mask = document.getElementById('dialog-mask');
    if (!mask) {
        mask = document.createElement('div');
        mask.id = 'dialog-mask';
        document.body.appendChild(mask);
    }
    var h = pageHeight ();
    if( flag )
    	h += document.body.scrollHeight;
    mask.style.height = h + 'px';
    mask.style.visibility = 'visible';
}
/**
 * 关闭对话框;
 * @param id
 * @param container
 */
function closeDialog (id, container) {
    var dialog = document.getElementById(id);
    dialog.style.display = 'none';

    if (container && BROWSER && BROWSER.isIE6) {
        var a = findSelectControl(document.getElementById(container));
        if (a && a.length) for (var i = 0; i < a.length; i ++) {
            a [i].style.visibility = 'visible';
        }
    }
    document.getElementById('dialog-mask').style.visibility = 'hidden';
}

//var IE6 = window.navigator.userAgent.indexOf("MSIE 6.0") != -1;
/**
 * 成功对话框;
 */
function success (title, message) { showDialog(title, message, 'success', false, null, null); }
/**
 * 错误对话框;
 * @param title
 * @param message
 */
function error (title, message) { showDialog (title, message, 'error'); }
/**
 * 告警对话框;
 * @param title
 * @param message
 * @param okAction
 * @param cancelAction
 */
function warn (title, message, okAction, cancelAction) { showDialog (title, message, 'warning', false, okAction, cancelAction); }
/**
 * 提示对话框;
 * @param title
 * @param message
 * @param okAction
 * @param cancelAction
 */
function prompt (title, message, okAction, cancelAction) { showDialog (title, message, 'prompt', false, okAction, cancelAction); }