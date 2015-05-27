/**
 * 公司设置树.js
 */
var tree = null, root = null;
/**
 * 创建树;
 */
function buildTree () {
    TreeIcons.ImagePath = "../images";
    TreeIcons.FolderIcon = 'empty.gif';
    TreeIcons.FolderOpenIcon = 'empty.gif';
    TreeIcons.ItemIcon = 'empty.gif';
    
    loadTreeData ();
}

/**
 * 单击事件;
 * @param e
 */
function moduleClicked (e) {
    var node = e.tree.getSelectedNode();
    if (!node) return;
    setPermission (node, !node.checked);
}
/**
 * 设值权限;
 * @param node
 * @param checked
 */
function setPermission (node, checked) {
    var icon = node.folderIcon;
    icon.src = TreeIcons.ImagePath + '/' + (checked ? 'check-on.gif' : 'empty.gif');
    node.checked = checked;
    var children = node.getChildren();
    if (children.length > 0) for (var i = 0; i < children.length; i ++) {
        setPermission (children[i], checked);
    }
}
/**
 * 打开树节点;
 * @param e
 */
function willOpen (e) {
    var icon = e.source.folderIcon;
    var checked = e.source.checked;
    icon.src = TreeIcons.ImagePath + '/' + (checked ? 'check-on.gif' : 'empty.gif');
}

function rescerive( root,data ){
	for( var i=0;i< data.length;i++){
    	var n = data[i];
    	var moduleNode = new TreeNode (n.nodeId+"_"+n.nodeType, n.nodeName, null);
    	root.add (moduleNode);
    	if( n.childNodes.length ){
    		rescerive( moduleNode,n.childNodes );
    	}
    }
}

/**
 * 加载树的数据;
 * @param root
 */
function loadTreeData () {
	secure.queryTree (false,function (response) {
        var data = eval (response.responseText);
        
        if( data.length >1 ){
        	root = new TreeNode ('_root', '', null);
        }else{
        	var n = data[0];
        	data = n.childNodes;
        	root = new TreeNode (n.nodeId+"_"+n.nodeType, n.nodeName, null);
        }
        tree = new Tree (root);
        tree.install(document.getElementById('panel_body_main'));
        tree.addClickListener (moduleClicked);
        tree.setOpenListener  (willOpen);
        tree.setCloseListener (willOpen);
        
        rescerive( root, data );
        
        root.open();
    });
}
/**
 * 判断指定角色的权限;
 * @param roleId
 */
function cmd_set (id,flag) {
	base.openSet(id,flag,function(res){
		var data = eval( res.responseText );
		
		clearPermission (root,data);
		
	    openDialog ('user-list-panel', 'user-list-panel', false);
	    
	    tree._id   = id;
	    tree._flag = flag;
	});
}

/**
 * 选中公司;
 */
function clearPermission (node,map) {
	var id = node.id;
	if( map[id] ){
		node.checked = true;
		node.folderIcon.src = TreeIcons.ImagePath + '/check-on.gif';
	}else{
	    node.checked = null;
	    delete node.checked;
	    node.folderIcon.src = TreeIcons.ImagePath + '/empty.gif';
	}

    var children = node.getChildren();
    if (children.length){ 
    	node.open();
    	for (var i = 0; i < children.length; i ++) {
	        clearPermission(children [i],map);
	    }
    }
}

function checkAll( node, checkedArr ){
	var ids = node.id;
	if( node.checked ){
		checkedArr.push(ids.split("_")[0]);
	}
	var children = node.getChildren();
    if (children.length) 
	    for (var i = 0; i < children.length; i ++) {
	    	checkAll(children [i],checkedArr);
	    }
}

/**
 * 保存;
 */
function cmd_save () {
    if (!tree || !root|| !tree._id || !tree._flag ) return;

    var a = [];
    checkAll(root,a);
    if( !a.length ){
    	alert("请至少选择一个子公司!");
    	return;
    }
    base.saveSet (tree._id, a.join(','),tree._flag, function (response) {
    	var data = eval( response.responseText );
    	if( data.succ ){
    		alert(data.msg);
    		closeDialog ('user-list-panel', 'user-list-panel');
    	}else{
    		alert(data.msg);
    	}
    });
}

