/**
 * 普通工具类;
 */

/**
 * 查找指定id的元素对象;
 */
function $_(id) { return typeof id == 'string' ? document.getElementById(id) : id; }
/**
 * 查找指定名称的元素对象;
 */
function $$(name) { return typeof name == 'string' ? document.getElementsByName(name) : name; }
/**
 * 创建指定id的元素,并将其加入父节点;
 */
function _(tag, id, parent) {
    var e = document.createElement (tag);
    if (id) e.id = id;
    if (!parent) parent = document.body;
    parent.appendChild (e);
    return e;
}
/**
 * 返回是否为整数;
 */
function $_i (text) { return parseInt (text) == text; }
/**
 * 返回是否是小数;
 */
function $_f (text) { return parseFloat (text) == text; }
/**
 * 判读字符串是否是空串;
 */
function $_e (text) { return text.replace(/^\s*|\s*$_/, '').length == 0; }
/**
 * 判断字符串的长度是否是指定范围的;
 */
function $_tl (text, min, max) {
    var n = text.replace(/^\s*|\s*/, '').length;
    return n >= min && n <= max;  
}
/**
 * 返回下拉选择框是否有选择;
 * @param selection
 * @returns {Boolean}
 */
function $_s (selection) { return selection.selectedIndex > 0; }
/**
 * 全选下拉选择框;
 * @param id
 * @param name
 */
function toggleAll (id, name) {
    var all = $_(id);
    var a = $$ (name);
    if (a) for (var i = 0; i < a.length; i ++) {
        if (!a[i].disabled)
            a[i].checked = all.checked;
    }
}

function toggleAllClick (id, name) {
    var all = $_(id);
    var a = $$ (name);
    if (a) for (var i = 0; i < a.length; i ++) {
        if (!a[i].disabled) {
            a[i].checked = !all.checked;
            a[i].click();
        }
    }
}

/**
 * 返回下拉选择框选中的选择项;
 * @param name
 * @returns {Array}
 */
function getSelectedItems (name) {
    var r = [];
    var a = $$(name);
    if (a && a.length) for (var i = 0; i < a.length; i ++) {
        if (a[i].checked) r.push(a[i]);
    } else if (a && a.checked) {
        r.push(a);
    }
    return r;
}
/**
 * 查询提交或其它提交;
 * @param prefix
 */
function query (prefix) {
    if (prefix)
        $_(prefix+'_btnQuery').click();
    else
        $_('btnQuery').click();
}
/**
 * 保存提交;
 */
function save () { $_('btnSave').click(); }
/**
 * 确定提交;禁止和javascript内置函数同名;
 */
function _confirm () { $_('btnConfirm').click(); }
/**
 * 清除下拉选择框选中;
 */
function clearItems () { selectedItems = null; }


var selectedItems;

/**
 * 删除选中的条目;
 * @param name
 */
function removeItems (name) {
    selectedItems = getSelectedItems (name);
    if (!selectedItems || !selectedItems.length) {
        alert (message.error.select_target);
        return;
    }
    if(confirm(message.confirm.del_msg)){
    	doRemove(name);
    }
}
/**
 * 删除选中的条目;
 */
function doRemove (name) {
    if (!selectedItems) return;

    var s = '';
    for (var i = 0; i < selectedItems.length; i ++) {
        if (i != 0) s += ',';
        s += selectedItems [i].value;
    }
    if (window._prefix) {
        $_(_prefix + '_items').value = s;
        $_(_prefix + '_btnDelete').click();
    } else {
        $_('items').value = s;
        $_('btnDelete').click();
    }
}
/**
 * 判断数据是否在指定的范围内;
 * @param obj
 * @param min
 * @param max
 * @returns {Boolean}
 */
function $_value(obj,min,max){
	var num = 0;
	if(obj!= "")
		num = obj;
	return num >= min && num <= max;
}

/**
 * 查找指定节点的父节点;
 * @param current
 * @param nodeName
 * @returns abc
 */
function findParent (current, nodeName) {
    while (current && current.nodeName != nodeName) current = current.parentNode;
    return current;
}
function start(name)
{
	var enableItem=document.getElementsByName(name);
	if(enableItem==null||enableItem.length==0)	{
			return;
	 }
	var text=""; 
	for(var i=0;i<enableItem.length;i++) {		
		 if(enableItem[i].checked)  
		  text+=enableItem[i].value;	 
	}
	if(text=="")	{
	error ("错误", "请选择要启动的行");
	return;
	}
	prompt ("确认", "您确定要把选中的行设置为有效状态吗？", function(){document.getElementById("btnStart").onclick();});
	

}
/**
 * 禁止和javascript内置函数重名!
 * @param name
 */
function _close(name)
{
	var enableItem=document.getElementsByName(name);
	if(enableItem==null||enableItem.length==0)	{
			return;
	 }
	var text=""; 
	for(var i=0;i<enableItem.length;i++) {		
		 if(enableItem[i].checked)  
		  text+=enableItem[i].value;	 
	}
	if(text=="")	{
	error ("错误", "请选择要注销的行");
	return;
	}
	prompt ("确认", "您确定要把选中的行为无效状态吗？", function(){document.getElementById("btnClose").onclick();});		

}

/**
 * 导出方法;
 * @param url
 */
function cmd_exp( url ){
	var id ="_hidden_frame";
	var fra = document.getElementById(id);
	if( !fra ){
		fra = document.createElement("iframe");
		fra.style.display ="none";
		fra.setAttribute("id", id);
		fra.setAttribute("name", id);
		
		document.body.appendChild(fra);
	}
	fra.setAttribute("src", url);
}

/**
 * 调整页面高度;
 */
function adjust() {
	var main_table = $_(target_id + '-container');
	var ki = $_('title_panel').scrollWidth - 22;
	main_table.style.width = ($_('title_panel').scrollWidth - 22) + 'px';

	var f2 = $_(target_id + '-f2');
	var f3 = $_(target_id + '-f3');
	var f4 = $_(target_id + '-f4');

	if (BROWSER.isWebKit)
		$_(target_id + '-1-1').style.width = ($_(target_id + '-f1').clientWidth + 2) + 'px';
	else
		$_(target_id + '-1-1').style.width = ($_(target_id + '-f1').clientWidth) + 'px';

	var c4 = $_(target_id + '-c4');
	var c2 = $_(target_id + '-c2');
	var c3 = $_(target_id + '-c3');

	var cell4 = $_(target_id + '-2-2');
	c4.style.width = cell4.clientWidth + 'px';
	c2.style.width = cell4.clientWidth + 'px';
}
/**
 * 取消
 */
function cancel () { $_('btnCancel').click(); }
/**
 * 下载
 */
function download (aId) { 
	$_('attachmentId').value=aId;
	$_('btnDown').click(); 
}