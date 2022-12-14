var _TREE_FOLDER_ICON = "far fa-folder-open";
var _TREE_LEAF_ICON = "far fa-sticky-note";
$(document).ready(function() {
    var treeList = $("[data-type=tree]");
    for (var i = 0; i < treeList.length; i++) {
        var el = treeList[i];
        initEzTreeUrl(el);
        
        
    }
});
function initEzTreeUrl(el){
    var url = $(el).attr("data-get-url");
    Utilities.getAjax(url, {}, true, function(data, jqXHR) {
        if (Utilities.processResult(data, jqXHR, "")) {
            createEzTree($(el), data);
            const treeId = $(el).attr("id");
            
            if(treeId){
				$('input[data-type=searchTree][data-tree='+treeId+']').keydown(function(e) {
	                try {
	                    if (e.which == 13) {
	                        e.preventDefault();
	                       	var word = $(this).val();
							$("#"+ treeId).searchTree(word);
	                    }
	                } catch (e) {
	                    console.error(e);
	                }
            	});
				$('[data-type=searchTreeBtn][data-tree='+treeId+']').click(function(e) {
	                try {
                        e.preventDefault();
                        
                       	var word = $('input[data-type=searchTree][data-tree='+treeId+']').val();
						$("#"+ treeId).searchTree(word);
	                } catch (e) {
	                    console.error(e);
	                }
            	});
			}
            
            /*
        data-type="searchTree" data-tree="divTree"
        
        var word = $("#sWord").val();
	$("#divTree").searchTree(word);
         */
            
        }
    });
}

$.fn.reload = function(url,param) {

        var $tree = $(this);
        
        url =url || $(this).attr("data-get-url");
        if(!param)
            param = {};
        Utilities.getAjax(url, param, true, function(data, jqXHR) {
            if (Utilities.processResult(data, jqXHR, "")) {
                
                var treeData = data;
                var rootText = $tree.attr("data-wrap-root");
                if (rootText) {
                    var id = "0000000000";
                    treeData = [{ id : id,
                                text : rootText,
                                children : treeData, 
                                icon : _TREE_FOLDER_ICON }];
                }
                $tree.jstree(true).settings.core.data = treeData;
                $tree.jstree(true).refresh();
            }
        });
};

$.fn.getSelectedNode = function() {
    if ($(this).data("type") != "tree")
        return null;
    var arr = $(this).jstree('get_selected');
    if (arr && arr.length) {
        var json = $(this).jstree('get_node', arr[0]);
        json.original.children = json.children;
        return json.original;
    }
    else
        return null;
};
$.fn.getNode = function(nodeId) {
    if ($(this).data("type") != "tree")
        return null;
    return $(this).jstree('get_node', nodeId);
};
$.fn.getNodeData = function(nodeId) {
    if ($(this).data("type") != "tree")
        return null;
    var json = $(this).jstree('get_node', nodeId);
    return json.original;

};
$.fn.setNodeData = function(nodeId, data) {
    if ($(this).data("type") != "tree")
        return null;
    var json = $(this).jstree('get_node', nodeId);
    json.original = data;
};
$.fn.getLevel = function(nodeId) {
    if ($(this).data("type") != "tree")
        return null;
    return $(this).jstree().get_path(nodeId).length;
};
$.fn.getParentJson = function(nodeId, level) {
    var arr = $(this).jstree().get_path(nodeId, "", true);
    if (level && level <= arr.length) {
        var json = $(this).jstree('get_node', arr[level - 1]);
        json.original.children = json.children;
        return json.original;
    }
    else return null;
};
$.fn.addNode = function(parentId, data) {
    if (!data.icon)
        data.icon = _TREE_FOLDER_ICON;
    return $(this).jstree().create_node(parentId, data);

};
$.fn.removeNode = function(nodeId) {
    return $(this).jstree().delete_node(nodeId);
};

$.fn.setText = function(nodeId, text) {
    return $(this).jstree().rename_node(nodeId, text);

};
$.fn.getChildren = function(nodeId) {
    var node = $(this).getNode(nodeId);
    return node.children;
};
$.fn.expandNode = function(nodeId){
    $(this).jstree().open_node(nodeId);
};

$.fn.collapseNode = function(nodeId){
    $(this).jstree().close_node(nodeId);
};

$.fn.selectNode = function(nodeId){
    $(this).jstree().deselect_all();
    $(this).jstree().select_node(nodeId,false);
};
$.fn.searchTree = function(word){
	 $(this).jstree(true).search(word);
};

$.fn.resort = function(nodeId) {
    var children = [].concat($(this).getChildren(nodeId));
    var dataList = [];
    var dataMap = {};
    for (var i = 0; i < children.length; i++) {
        var json = $(this).getNodeData(children[i]);
        dataList.push(json);
        dataMap[json.id] = json;
    }

    for (var i = 0; i < dataList.length; i++) {
        var data = dataList[i];
        var ord = i + 1;
        var ord2 = 0;
        var list = $(this).getChildren(nodeId);
        for (var j = 0; j < dataList.length; j++) {
            ord2 = (j + 1);
            var comp = dataMap[list[j]];
            if (comp == data)
                continue;
            if (Number(comp.ord) > Number(data.ord))
                break;
        }
        if (ord != ord2) {
            $(this).jstree().move_node(data.id, nodeId, ord2 - 1);
        }
    }

};

var _DND_TREE = false;
function createEzTree(el, treeData) {
//  el.css("overflow", "auto");
    var rootText = el.attr("data-wrap-root");
    if (rootText) {
        var id = "0000000000";
        //      treeData = [{id:id,text:rootText,chlidren:treeData}]; 
        treeData = [{ id : id,
                    text : rootText,
                    children : treeData, 
                    icon : _TREE_FOLDER_ICON }];

        //      treeData = item;
        //      return ret;

    }
    var plugins = ["loaded_state", "state", "changed"];
plugins.push("search");
    var checkCallback = true;
    var seqUrl = el.attr("data-seq-url");
    var seqCol = el.attr("data-seq-col");
    var changeSeq = el.attr("data-change-seq");
    var dragCallback = el.attr("data-seq-callback");
    var dragStartCallback = el.attr("data-drag-start-callback");
    var dragSaveCallback = el.attr("data-drag-save-callback");
//	var searchYn = el.attr("data-search");
	
    checkCallback = function(operation, node, node_parent, node_position, more) {
        /*
         * operation : 동작 상태('create_node', 'rename_node', 'delete_node', 'move_node', 'copy_node' or 'edit')
         * node : 선택된 노드 정
         * node_parent : Drop 된 트리의 부모 노드 정보
         * node_position : Drop 된 위치
         * more : 기타 정보
         */
        var $tree = el;
        if (operation === "edit") {
            return false;
        }
        else if (operation === "move_node") {
            if (node == null || node_parent == null)
                return;
            if (node_parent.id != node.parent)
                return false;
            if(more.dnd)
                _DND_TREE = true;
            //드롭시 발생
            if (_DND_TREE && !more.ref) {
                _DND_TREE = false;
                //                      var data = node_parent.original;
                var arr = [];
                var oldIdx = -1;
                for (var i = 0; node_parent && i < node_parent.children.length; i++) {
                    //                          var child = data.nodes[i];
                    //                          if(child.id != node.id)
                    if (node_parent.children[i] == node.id)
                        oldIdx = i;
                    arr.push(node_parent.children[i]);
                }
                var pos = node_position;
                arr.splice(node_position, 0, node.id);
                if(oldIdx < pos)
                {
                    arr.splice(oldIdx,1);
                }    
                else 
                {
                    arr.splice(oldIdx+1,1);
                }    
                dragCallback = $tree.data("dragCallback");
                if (dragCallback && window[dragCallback]) {
                    if(!arr || !arr.length)
                        return false;
                    var list = [];
                    for (var i = 0; i < arr.length; i++) {
                        var child = $tree.getNodeData(arr[i]);
                        list.push(child);
                    }
                    return window[dragCallback](node_parent.id, list);
                }
                
                else if (seqUrl && seqCol) {
                    if(!arr || !arr.length)
                        return false;
                    var list = [];
                    for (var i = 0; i < arr.length; i++) {
                        var child = $tree.getNodeData(arr[i]);
                        child[seqCol] = i + 1;
                        list.push(child);
                    }
                    
                    Utilities.getAjax(seqUrl, list, true, function(data, jqXHR) {
                        if (Utilities.processResult(data, jqXHR, "순서 저장에 실패했습니다.")) {
                          if(dragSaveCallback && window[dragSaveCallback]){
                              window[dragSaveCallback](data);
                          }
                            // alert("순서 저장에  성공했습니다.");

                        }
                    });
                    return true;
                }
                return false;
            }
            if(dragStartCallback&&window[dragStartCallback]){
                    return window[dragStartCallback](node.original,node_parent.original);
            }
            else if (node_parent.id != node.parent)
                return false;
            
            return true;
        }
        else {
            return true;
        }
    }
    if (changeSeq == "Y") {
        plugins.push("dnd");

    }
//if (searchYn == "Y") {
//        plugins.push("search");
//
//    }

    var gId = _PROGRAM_ID + el.attr("id");
    var option = {
        core: {
            data: treeData,
            loaded_state: true,
            multiple: false,
            check_callback: checkCallback,
        },
        "types": {
            "default": { "icon": _TREE_FOLDER_ICON },
            "file": { "icon": _TREE_LEAF_ICON }
        },

        //      'checkbox' : {            
        //            'deselect_all': true,
        //             'three_state' : false, 
        //        },
        //      "keep_selected_style" : false,
        "state": { "key": gId },

        "plugins": plugins,
        "search" : { "show_only_matches" : true, "show_only_matches_children" : true, }
    };
    //  option.dnd = dnd;
    el.on("init.jstree", function(e, data) {
        var that = $(this);
        var id = that.attr("id");
        var cFunction = "";
        if (id) {
            cFunction = id + "_TreeLoad";
        }
        if (cFunction && window[cFunction] && typeof (window[cFunction]) == "function") {
            return window[cFunction](that);
        }
        cFunction = "onTreeLoad";
        if (cFunction && window[cFunction] && typeof (window[cFunction]) == "function") {
            return window[cFunction](that);
        }

    });
    el.on("select_node.jstree", function(e, data) {
        var that = $(this);
        var id = that.attr("id");
        var cFunction = "";
        if (id) {
            cFunction = id + "_TreeSelect";
        }
        if (cFunction && window[cFunction] && typeof (window[cFunction]) == "function") {
            return window[cFunction](data.node.original, data.node, that);
        }
        cFunction = "onTreeSelect";
        if (cFunction && window[cFunction] && typeof (window[cFunction]) == "function") {
            return window[cFunction](data.node.original, data.node, that);
        }
    });
    el.on("deselect_node.jstree", function(e, data) {
        var that = $(this);
        var id = that.attr("id");
        var cFunction = "";
        if (id) {
            cFunction = id + "_TreeDeselect";
        }
        if (cFunction && window[cFunction] && typeof (window[cFunction]) == "function") {
            return window[cFunction](data.node.original, data.node, that);
        }
        cFunction = "onTreeDeselect";
        if (cFunction && window[cFunction] && typeof (window[cFunction]) == "function") {
            return window[cFunction](data.node.original, data.node, that);
        }
    });
    el.on("rename_node.jstree", function(e, data) {
        var oldVal = data.old;
        var text = data.node.text;
        if (oldVal != text) {

        }
    });
    
     el.on("refresh.jstree", function(e, data) {
        if($(this).attr("data-expand-all")=="Y")
            $(this).jstree("open_all");
    });
    el.on("ready.jstree", function(e, data) {
        if($(this).attr("data-expand-all")=="Y")
            $(this).jstree("open_all");
    });
    el.jstree(option);
}