/**
 * 权限树.js
 */
var tree = null, root = null;
/**
 * 创建树;
 */
function buildTree () {
    TreeIcons.ImagePath = "../images";
//    TreeIcons.FolderIcon = 'empty.gif';
//    TreeIcons.FolderOpenIcon = 'empty.gif';
//    TreeIcons.ItemIcon = 'empty.gif';

    loadTreeData ();
}

/**
 * 单击事件;
 * @param e
 */
function moduleClicked (e) {
    var node = e.tree.getSelectedNode();
    if (!node) return;
    
    var id = node.id;
    var org = id.split("_").join(",");
    window.curr_org = org;
    open_new(org,"1000");
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
        tree.install($('#panel_body_main')[0]);
        tree.addClickListener (moduleClicked);
        tree.setOpenListener  (willOpen);
        tree.setCloseListener (willOpen);
        
        rescerive( root, data );
        
        root.open();
    });
}


/**
 * 清除指定节点的权限选择;
 */
function clearPermission (node) {
    node.checked = null;
    delete node.checked;
    node.folderIcon.src = TreeIcons.ImagePath + '/empty.gif';

    var children = node.getChildren();
    if (children.length) for (var i = 0; i < children.length; i ++) {
        clearPermission(children [i]);
    }
}
/**
 * 添加菜单;
 */
function saveConfig () {
}

