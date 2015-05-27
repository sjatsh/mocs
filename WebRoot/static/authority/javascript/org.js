var ORG_NAME =[["nodeId", ""], 
	          ["name", ""], 
	          ["nodeType", "10"], 
	          ["seq", "1000"], 
	          ["contactPerson", ""], 
	          ["contactTelephone", ""], 
	          ["address", ""], 
	          ["description", ""], 
	          ["state", "1"],
	          ["mesNodeid", ""],
	          ["mesPwd", ""]];

var DEV_NAME =[["equType", ""],
	          ["workStationName", ""],
	          ["manuFacturer", ""],
	          ["manuFactureType", ""],
	          ["composeId", ""],
	          ["equDesc", ""]];


var op = "add";
/**
 * 列表;
 */
var template_list = null;

/**
 * 详细页面;
 */
var template_edit = null;

/**
 * 当前选择节点;
 */
var selectedNode	= null;

/**
 * 组织架构树;
 */
var tree		= null;

/**
 * 组织架构树设置;
 */
var setting =	null;
/**
 *组织架构树初始化数据;
 */
var zNodes	=	null;

/**
 * 列表;
 */
var _children = [];

/**
 * 初始化模板;
 */
function init_template(){
	if(jQuery("#template_list").length > 0 )
		template_list = TrimPath.parseDOMTemplate("template_list");
	
	if(jQuery("#template_edit").length > 0 )
		template_edit = TrimPath.parseDOMTemplate("template_edit");
}

/**
 * 初始化;
 */
function init(){
	init_template();
	
	init_org();
	
	setting = {view	:	{selectedMulti:false},
				 async	:	{autoParam : ["nodeId=nodeId"],
							 dataFilter : ds_filter,
							 dataType : "text",
							 enable : true,
							 otherParam : {"userId":USER_ID, "exclude":"", "filter":"", "pageId":PAGE_ID},
							 type : "get",
							 url : "org/queryOrgChild.ajax"},
				 callback:	{"onClick":nodeClick,
							 "onAsyncError":async_error,
							 "onAsyncSuccess":async_success}
				};
}

/**
 * 初始化组织架构树;
 */
function init_org(){
	 org.queryOrgTree(true, USER_ID, PAGE_ID, function(res){
		var result = eval(res.responseText);
		if(result.succ){
			zNodes = result.content;
			init_tree(zNodes);
		}else{
			alert(result.msg);
		}
	 });
}

/**
 * 创建树;
 */
function init_tree(zNodes){
	tree = jQuery.fn.zTree.init(jQuery("#tree"), setting, zNodes);
}

/************************tree 回调函数*******************************************************************/

/**
 * 节点树异步加载回调函数;
 */
function ds_filter(treeId, parentNode, res){
	if(res.succ){
		return res.content;
	}else{
		alert(result.msg);
		return [];
	}
}

function async_error(event, treeId, treeNode, XMLHttpRequest, textStatus, e){
	alert("Query Error!");
}

function async_success(event, treeId, treeNode, msg){
	if(treeNode == null && selectedNode != null){
		var node = tree.getNodeByParam("nodeId", selectedNode.nodeId, null);
		set_click_node(node);
		if(node != null)
			tree.selectNode(selectedNode);
	}
}

/**
 *	设置当前选择节点; 
 */
function set_click_node(treeNode){
	selectedNode	= treeNode;
	
	//如果设置当前选择节点为null,不选中当前树;
	if(treeNode == null)
		cancel_selected_node();
}

/**
 * 节点点击发生事件;
 */
function nodeClick(event, treeId, treeNode){
	op = "add";
	var id = treeNode.nodeId;
	org.queryOrgChild(id, USER_ID, "", "", PAGE_ID, function(res){
		var result = eval(res.responseText);
		if(result.succ){
			set_click_node(treeNode);
			
			var data = result.content;
			
			_children = [];
			for(var i=0; i < data.length; i++){
				_children.push(data[i]);
			}
			
			show_node_list(undefined, result);
		}else{
			alert(result.msg);
		}
	 });
}

/************************tree 回调函数*******************************************************************/


/**
 * 显示列表;
 */
function show_node_list(o, result){
	//1.如果存在新增节点就添加/更新;
	if( o != undefined ){
		if( isRoot(selectedNode, o.nodeId)){
			//更新根节点时,不用干任何事情;	
			if(op == "del"){
				cancel_selected_node();
				refresh_tree([o]);
				return;
			}
		}else{
			add_or_update_array(_children, o);
		}
	}
	
	//2.排序;
	_children.sort(sortNode);
	
	//3.刷新列表;
	result = (result == undefined) ? {"attach":{"manage":true}} : result;
	var html = template_list.process({"data":_children, "result":result});
	jQuery("#child_content").html(html);
	
	//4.同时在树上增加节点;
	if( o != undefined ){
		refresh_tree([o]);
	}
}


/**
 *刷新组织架构树;
 */
function refresh_tree(arr){
	if(tree == null){
		if(op == "add")
			init_tree(arr);
	}else{
		var par = selectedNode;
		if(arr.length > 0)
			if(isRoot(selectedNode, arr[0].nodeId)){
				par = null;
			}
		if(par != null && par.isParent == false){
			tree.reAsyncChildNodes(par.getParentNode(), "refresh");
		}else{
			tree.reAsyncChildNodes(par, "refresh");
		}
	}
}


function add_or_update_array(arr, o){
	var flag = -1 ;
	var len = arr.length - 1;
	
	var o_copy = $.extend({}, o);
	for ( var i = len; i >=0 ; i--) {
		var _o = arr[i];
		if(_o.nodeId == o.nodeId){
			jQuery.extend(arr[i], o);
			arr[i]["bindData"] = o_copy;
			flag = i;
			break;
		}
	}
	
	//删除;
	if(op == "del"){
		if(flag >= 0)
			arr.splice(flag,1);
	//编辑或新增;
	}else{
		if(flag == -1){
			o["bindData"] = o_copy;
			arr.push(o);
		}
	}
}

/**
 * 排序;
 * @param a
 * @param b
 * @returns {Number}
 */
function sortNode(a, b){
	if( a.seq == undefined ) return 1;
	if( b.seq == undefined ) return -1;
	return (a.seq - b.seq);
}

/**
 * 取消选中节点;
 */
function cancel_selected_node(){
	if(tree != null)
		tree.cancelSelectedNode();
	_children = [];
	jQuery("#child_content").html("");
}

/**
 * 设置添加form;
 */
function set_form(content){
	var form = jQuery("form")[0];
	
	var dev = false;
	if(content["device"]){
		dev = true;
	}
	
	for(var i=0; i < ORG_NAME.length; i++){
		var el = ORG_NAME[i];
		if(form[el[0]])
			form[el[0]].value = get(content, el[0], el[1]);
	}
	
	if(dev){
		for(var i=0; i < DEV_NAME.length; i++){
			var el = DEV_NAME[i];
			if(form[el[0]]){
					if(el[0] == "composeId"){
						var equSerialno = get(content["device"], "equSerialno");
						var symgEquId = get(content["device"], "symgEquId");
						if(symgEquId != "")
							form[el[0]].value = equSerialno + "@symg_swg@" + symgEquId;
					}else{
						form[el[0]].value = get(content["device"], el[0], el[1]);
					}
			}
		}
	}
	
}
 
function get(content, name, def){
	def = (def == undefined) ? "" : def;
	if("nodeType" == name){
		return (content[name] == undefined) ? def : ((content[name]["typeId"] == undefined) ? def : content[name]["typeId"]);
	}
	return (content[name] == undefined) ? def : content[name];   
}

/**
 * 提交表单;
 */
function get_form(){
	var form = jQuery("form")[0];
	var node = new Object();
	//是否提交设备信息;
	var nt = form["nodeType"].value;
	var dev = false;
	if("13" == nt){
		dev = true;
	}
	
	for(var i=0; i < ORG_NAME.length; i++){
		var el = ORG_NAME[i];
		if(form[el[0]]){
			if("nodeType" == el[0]){
				node[el[0]] = {"typeId":form[el[0]].value};
			}else{
				node[el[0]] = form[el[0]].value;
			}
		}
	}
	if(dev){
		node["device"] = new Object();
		for(var i=0; i < DEV_NAME.length; i++){
			var el = DEV_NAME[i];
			if(form[el[0]]){
					node["device"][el[0]] = form[el[0]].value;
			}
		}
	}
	
	return node;
}

/**
 * 扁平化;
 */
function cmd_reload(){
	jQuery("#btn_reload").attr("disabled","disabled");
	org.flatOrg(function(res){
		var result = eval(res.responseText);
		if(result.succ){
		}else{
			alert("Refresh Error!");
		}
		jQuery("#btn_reload").removeAttr("disabled");
	});
}

function sel(name, checked){
	if(checked)
		$("input[name=" + name + "]").attr("checked",true);
	else
		$("input[name=" + name + "]").removeAttr("checked");
}

/**
 * 判断是否要操作根节点;
 */
function isRoot(node, id){
	return (node == null || id == node.nodeId);
}
 
function getParId(node, id){
	return isRoot(node, id) ? "" : node.nodeId;
}

function showMask(){
	jQuery.blockUI({bindEvents:false,"message":"<div class='__add'></div>", "css":{"border":"none","top":"1%","width":"400"}});
}

function addMaskContent(content){
	jQuery(".__add").html(content);
}
