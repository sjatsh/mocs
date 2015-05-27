/**
 * 权限树.js
 */
var tree = null, root = null;
var current_role_id = null;
/**
 * 创建树;
 */
function buildTree () {
    TreeIcons.ImagePath = "../images";
    TreeIcons.FolderIcon = 'empty.gif';
    TreeIcons.FolderOpenIcon = 'empty.gif';
    TreeIcons.ItemIcon = 'empty.gif';
    root = new TreeNode ('_root', '所有权限', null);
    tree = new Tree (root);
    tree.install($('#panel_body_main')[0]);
    tree.addClickListener (moduleClicked);
    tree.setOpenListener  (willOpen);
    tree.setCloseListener (willOpen);

    loadTreeData (root);
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
/**
 * 加载树的数据;
 * @param root
 */
function loadTreeData (root) {
    role.getPageList (function (response) {
        var data = eval (response.responseText);
        root.setCaption ("所有权限");
        root.folderIcon.src = TreeIcons.ImagePath + '/' + TreeIcons.FolderIcon;
        for (var i = 0; i < data.length; i ++) {
        	var app = data [i];
            var appNode = new TreeNode (app.appId, app.appName, null);
            root.add (appNode);
            for (var j = 0; j < app.modules.length; j ++) {
                var module = app.modules[j];
                var moduleNode = new TreeNode (module.moduleId, module.moduleName, null);
                appNode.add (moduleNode);
                for (var k = 0; k < module.pages.length; k++) {
                    var page = module.pages[k];
                    var pageNode = new TreeNode (page.pageId, page.pageName, null);
                    moduleNode.add (pageNode);
                    for (var l = 0; l < page.buttons.length; l++) {
                        var button = page.buttons[l];
                        var buttonNode = new TreeNode (button.buttonId, button.buttonName, null);
                        pageNode.add (buttonNode);
                    }
                }
            }
        }
    });
}
/**
 * 判断指定角色的权限;
 * @param roleId
 */
function assignPermission (roleId) {
    openDialog ('user-list-panel', 'user-list-panel', false);
/*
    if (tree == null) {
        buildTree ();
        root.folderIcon.src = TreeIcons.ImagePath + '/loading.gif';
        root.setCaption ('正在装载数据，请稍后...');
        setTimeout('loadTreeData (root)', 0);
    } else {
*/
        clearPermission (root);
//    }
    tree.roleId = roleId;
//    secure.getPermissionByRole (roleId, function (response) {
//        var list = eval (response.responseText);
//        var c = root.getChildren();
//        for (var i = 0; i < c.length; i ++) {
//            var children = c[i].getChildren();
//            for (var j = 0; j < children.length; j ++) {
//                for (var k = 0; k < list.length; k ++) {
//                    if (list[k] == children [j].id) {
//                        children[j].checked = true;
//                        children[j].folderIcon.src = TreeIcons.ImagePath + '/check-on.gif';
//                        break;
//                    }
//                }
//            }
//        }
//    });
}

/*
function markPermission (list, node) {
    var children = node.getChildren();
    for (var i = 0; i <)
}
*/
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
    if (!tree || !root || !tree.roleId) return;

    var a = [];
    var modules = root.getChildren();
    for (var i = 0; i < modules.length; i ++) {
        var children = modules [i].getChildren ();
        for (var j = 0; j < children.length; j ++) {
            if (children[j].checked) a.push (children [j].id);
        }
    }
    secure.setPermission ( a.join(','), function (response) {
    	var val = eval( response.responseText );
    	comboData( val );
    	closeDialog ('user-list-panel', 'user-list-panel');
    });
}

