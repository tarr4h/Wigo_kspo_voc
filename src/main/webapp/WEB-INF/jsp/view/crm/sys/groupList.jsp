<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<div class="gLeft">
	<div class="mBox1">
		<div class="gTitle1">
			<h3 class="mTitle1">그룹 정보</h3>
				<div class="gRt">
				
				<code:btnGroup name="Group"/>
				</div>
		</div>
			<div id="divGrid"  style="height:${leftBoxHeight1}"  data-grid-id="grdList" 
			data-get-url= "<c:url value='${urlPrefix}/getList${urlSuffix}'/>" 
			data-pagenation="N"
			 data-type="grid" data-grid-callback="onGridLoad" data-tpl-url="<c:url value='/static/gridTemplate/group/group.xml'/>">
        </div>
	</div>
</div>

<div class="gRight">
	<div class="mBox1">
		<div class="gTitle1">
			<h3 class="mTitle1">메뉴 권한</h3>
			<div class="gRt">
				<form role="form" id="frmSearch">
				<input type="hidden" id="systemCd" name="systemCd" value="0100000000"/>
				</form>
				<code:btnGroup name="GroupMenu"/>
			</div>
		</div>
		
		<div id="divGrid"  style="height:${rightBoxHeight2}"  data-grid-id="grdMenu"  data-get-url="<c:url value='${urlPrefix}/getGroupMenuList'/>${urlSuffix}"  data-pagenation="Y" data-type="grid"
	 data-tpl-url="<c:url value='/static/gridTemplate/group/groupMenu.xml'/>">
	</div>
	
	
	
	</div>
	<div class="mBox1 pt25 ">
	<div class="row">
	<div class="col w43per">
		<div class="gTitle1">
			<h3 class="mTitle1">소속 조직</h3>
			<div class="gRt">
				 <code:btnGroup name="GroupOrg" />
			</div>
		</div>
		<div id="divGrid"  style="height:${rightBoxHeight2}"  data-grid-id="grdOrg"  data-get-url="<c:url value='${urlPrefix}/getGroupUserList'/>${urlSuffix}" data-pagenation="Y" data-type="grid"  data-tpl-url="<c:url value='/static/gridTemplate/system/grpOrgRel.xml'/>">
	</div>
	</div>
	<div class="col w57per">
		<div class="gTitle1">
			<h3 class="mTitle1">소속 사원</h3>
			<div class="gRt">
				<code:btnGroup name="GroupEmp" />
			</div>
		</div>
		<div id="divGrid"  style="height:${rightBoxHeight2}"  data-grid-id="grdEmp"  data-get-url="<c:url value='${urlPrefix}/getGroupUserList'/>${urlSuffix}" data-pagenation="Y" data-type="grid"  data-tpl-url="<c:url value='/static/gridTemplate/system/grpEmpRel.xml'/>">
		</div>
	</div>
	
	</div>
	</div>
	

 
               


</div>   
<!-- <div class="contents_block" style="display:none"> -->
<!--   		<h3>사용자</h3> -->
<%--  		<div id="divGrid" style="height:300px"  data-grid-id="grdUser"  data-get-url="<c:url value='${urlPrefix}/getGroupUserList'/>${urlSuffix}" data-pagenation="Y" data-type="grid"  data-tpl-url="<c:url value='/static/gridTemplate/group/groupUser.xml'/>"> --%>
<!-- 	    </div> -->
<%-- 	    <code:btnGroup name="GroupUser" hideSave="true" /> --%>
<!--  	</div> -->
<script>
var _current_group_cd = null;
$("#selectCodeCombo").change(function(){
	searchMenu(1);
});
function grdList_load(gridView,gridId){  
	searchGroup();
}
function clearRefRows(){
	grdMenu.clear();
	grdEmp.clear();
	grdOrg.clear();
}
function grdList_rowChanged(grdView,oldRow,newRow,rowData){
	clearRefRows();
	_current_group_cd = rowData.grpCd;
	g_currentJson = rowData;
    if(newRow > -1 && rowData.stat != "c"){
    	searchMenu(1);
    	searchUser(1);
    	searchOrg(1);
    	searchEmp(1);
    }
}
function searchMenu(currentPageNo){
    var param = {
        grpCd : _current_group_cd,
        topMenuCd : $("#selectCodeCombo").val(),
        currentPageNo : currentPageNo?currentPageNo : 1 
    };
    url = "<c:url value='${urlPrefix}/getGroupMenuList'/>${urlSuffix}";
    grdMenu.loadUrl(url,param);
}

function searchUser(currentPageNo){
// 	var param = {
// 	        grpCd : _current_group_cd,
// 	        currentPageNo : currentPageNo?currentPageNo : 1 
// 	    };
// 	var url = "<c:url value='${urlPrefix}/getGroupUserList'/>${urlSuffix}";
//     grdUser.loadUrl(url,param);
}

function searchOrg(currentPageNo){
    var param = {
        grpCd : _current_group_cd
    };
    url = "<c:url value='${urlPrefix}/getGroupOrgList'/>${urlSuffix}";
    grdOrg.loadUrl(url,param);
}

function searchEmp(currentPageNo){
    var param = {
        grpCd : _current_group_cd
    };
    url = "<c:url value='${urlPrefix}/getGroupEmpList'/>${urlSuffix}";
    grdEmp.loadUrl(url,param);
}



function searchGroup(){
	$("form#frmSearch").find("#currentPageNo").val(1);
    search();
}
function newGroup(){
	
	grdList.addRow();
	clearRefRows();
// 	var url = "<c:url value='${urlPrefix}/add${urlSuffix}'/>";
//     Utilities.windowOpen(url,"addGroup",900,550);
// 	Utilities.openModal(url,500,277);
// 	Utilities.windowOpen(url,"addGroup",900,550);
}
function search()
{
    var url = "<c:url value='${urlPrefix}/getList${urlSuffix}'/>";
    var param = Utilities.formToMap("frmSearch");
    grdList.loadUrl(url,param);
}
function saveGroup(){
    var saveJson = grdList.getSaveJson();
    if(saveJson.length){
        if(!confirm("수정된 데이터를 저장하시겠습니까?"))
            return ;
        var url = "<c:url value='${urlPrefix}/save${urlSuffix}'/>";
        Utilities.getAjax(url,saveJson,true,function(data,jqXHR){
            if(Utilities.processResult(data,jqXHR,"그룹 저장에 실패했습니다."))
            {
                alert("그룹 저장에  성공했습니다.");
                grdList.resetAllRowStatus();
            }
        });
    }
    else{
        alert("수정된 데이터가 없습니다.");
    }
}
function saveGroupMenu(){
    var saveJson = grdMenu.getSaveJson();
    if(saveJson.length){
        if(!confirm("수정된 데이터를 저장하시겠습니까?"))
            return ;
        var url = "<c:url value='${urlPrefix}/saveGroupMenuList${urlSuffix}'/>";
        Utilities.getAjax(url,saveJson,true,function(data,jqXHR){
            if(Utilities.processResult(data,jqXHR,"그룹메뉴 저장에 실패했습니다."))
            {
                alert("그룹메뉴 저장에  성공했습니다.");
                grdMenu.resetAllRowStatus();
            }
        });
    }
    else{
        alert("수정된 데이터가 없습니다.");
    }
}


function saveGroupOrg(){
    var saveJson = grdOrg.getSaveJson();
    if(saveJson.length){
        if(!confirm("수정된 데이터를 저장하시겠습니까?"))
            return ;
        var url = "<c:url value='${urlPrefix}/saveGroupOrgList${urlSuffix}'/>";
        Utilities.getAjax(url,saveJson,true,function(data,jqXHR){
            if(Utilities.processResult(data,jqXHR,"저장에 실패했습니다."))
            {
                alert("저장에  성공했습니다.");
                grdOrg.reload();
            }
        });
    }
    else{
        alert("수정된 데이터가 없습니다.");
    }
}


function saveGroupEmp(){
    var saveJson = grdEmp.getSaveJson();
    if(saveJson.length){
        if(!confirm("수정된 데이터를 저장하시겠습니까?"))
            return ;
        var url = "<c:url value='${urlPrefix}/saveGroupEmpList${urlSuffix}'/>";
        Utilities.getAjax(url,saveJson,true,function(data,jqXHR){
            if(Utilities.processResult(data,jqXHR,"저장에 실패했습니다."))
            {
                alert("저장에  성공했습니다.");
                grdEmp.reload();
            }
        });
    }
    else{
        alert("수정된 데이터가 없습니다.");
    }
}


function addGroup(data){
    if(!data)
        return;
    data.stat = "n";
    grdList.addRow(data);
}
function addGroupMenu(list){
	if(list)
		   grdMenu.reload();
}
function addGroupUser(list){
//     if(list)
//            grdUser.addRows(list);
}
function addGroupOrg(list){
	if(list)
	{
		var rows = grdOrg.getJsonRows();
		var map = {};
		for(var i=0;i<rows.length;i++){
			var row = rows[i];
			map[row.orgId] = row;
		}
		var arr = [];
		for(var i=0;i<list.length;i++){
			list[i].grpCd = _current_group_cd;
			if(map[list[i].orgId])
				continue;
			arr.push(list[i]);
		}
		grdOrg.addRows(arr);
	}
}
function addGroupEmp(list){
	if(list)
	{
		var rows = grdEmp.getJsonRows();
		var map = {};
		for(var i=0;i<rows.length;i++){
			var row = rows[i];
			map[row.orgId] = row;
		}
		var arr = [];
		for(var i=0;i<list.length;i++){
			list[i].grpCd = _current_group_cd;
			if(map[list[i].orgId])
				continue;
			arr.push(list[i]);
		}
		grdEmp.addRows(arr);
	}
	
	
}


function removeGroup(){
	var saveJson = grdList.getCheckedJson();
    if(saveJson.length ==0)
    {
        alert("체크된 데이터가 존재하지 않습니다.");
        return;
    }
//    if(!removeValidate()) return;
    if(saveJson.length){
        if(!confirm("데이터를 삭제하면 복구할  수 없습니다. 계속하시겠습니까?"))
            return ;
        var url = "<c:url value='${urlPrefix}/removeGroupList${urlSuffix}'/>";
        Utilities.getAjax(url,saveJson,true,function(data,jqXHR){
            if(Utilities.processResult(data,jqXHR,"그룹 삭제에 실패했습니다."))
            {
                alert("그룹 삭제에  성공했습니다.");
                grdList.removeCheckRow();
            }
        });
    }
    else{
        alert("선택된 데이터가 없습니다.");
    }
}
function removeGroupMenu(){
    var saveJson = grdMenu.getCheckedJson();
    if(saveJson.length ==0)
    {
        alert("체크된 데이터가 존재하지 않습니다.");
        return;
    }
    
    if(saveJson.length){
        if(!confirm("데이터를 삭제하면 복구할  수 없습니다. 계속하시겠습니까?"))
            return ;
        var url = "<c:url value='${urlPrefix}/removeGroupMenuList${urlSuffix}'/>";
        Utilities.getAjax(url,saveJson,true,function(data,jqXHR){
            if(Utilities.processResult(data,jqXHR,"그룹메뉴 삭제에 실패했습니다."))
            {
                alert("그룹메뉴 삭제에  성공했습니다.");
                grdMenu.removeCheckRow();
            }
        });
    }
    else{
        alert("선택된 데이터가 없습니다.");
    }
}
function removeGroupUser(){
//     var saveJson = grdUser.getCheckedJson();
//     if(saveJson.length ==0)
//     {
//         alert("체크된 데이터가 존재하지 않습니다.");
//         return;
//     }
    
//     if(saveJson.length){
//         if(!confirm("데이터를 삭제하면 복구할  수 없습니다. 계속하시겠습니까?"))
//             return ;
//         var url = "<c:url value='${urlPrefix}/removeGroupUserList${urlSuffix}'/>";
//         Utilities.getAjax(url,saveJson,true,function(data,jqXHR){
//             if(Utilities.processResult(data,jqXHR,"그룹 사용자 삭제에 실패했습니다."))
//             {
//                 alert("그룹 사용자 삭제에  성공했습니다.");
//                 grdUser.removeCheckRow();
//             }
//         });
//     }
//     else{
//         alert("선택된 데이터가 없습니다.");
//     }
}

function removeGroupOrg(){
    var saveJson = grdOrg.getCheckedJson();
    if(saveJson.length){
        if(!confirm("데이터를 삭제하면 복구할  수 없습니다. 계속하시겠습니까?"))
            return ;
        var url = "<c:url value='${urlPrefix}/removeGroupOrgList${urlSuffix}'/>";
        Utilities.getAjax(url,saveJson,true,function(data,jqXHR){
            if(Utilities.processResult(data,jqXHR,"삭제에 실패했습니다."))
            {
                alert("삭제에  성공했습니다.");
                grdOrg.reload();
            }
        });
    }
    else{
        alert("선택된 데이터가 없습니다.");
    }
}
function removeGroupEmp(){
    var saveJson = grdEmp.getCheckedJson();
    if(saveJson.length){
        if(!confirm("데이터를 삭제하면 복구할  수 없습니다. 계속하시겠습니까?"))
            return ;
        var url = "<c:url value='${urlPrefix}/removeGroupEmpList${urlSuffix}'/>";
        Utilities.getAjax(url,saveJson,true,function(data,jqXHR){
            if(Utilities.processResult(data,jqXHR,"삭제에 실패했습니다."))
            {
                alert("삭제에  성공했습니다.");
                grdEmp.reload();
            }
        });
    }
    else{
        alert("선택된 데이터가 없습니다.");
    }
}
function newGroupMenu(){
	_current_group_cd;
	if(!_current_group_cd){
		alert("먼저 그룹을 선택하세요");
		return false;
	}
	var url = "<c:url value='${urlPrefix}/groupMenuPopup${urlSuffix}'/>?grpCd="+_current_group_cd+"&topMenuCd="+$("#selectCodeCombo").val();
	Utilities.openModal(url,1200,798);
//    Utilities.windowOpen(url,"addGroupMenu",700,680);
}
function newGroupUser(){
    _current_group_cd;
    if(!_current_group_cd){
        alert("먼저 그룹을 선택하세요");
        return false;
    }
    var url = "<c:url value='${urlPrefix}/groupUserPopup${urlSuffix}'/>?grpCd="+_current_group_cd;
    Utilities.openModal(url,700,798);
}


function newGroupOrg(){
    _current_group_cd;
    if(!_current_group_cd){
        alert("먼저 그룹을 선택하세요");
        return false;
    }
    Utilities.openOrgPop("addGroupOrg");
}


function newGroupEmp(){
    _current_group_cd;
    if(!_current_group_cd){
        alert("먼저 그룹을 선택하세요");
        return false;
    }
    Utilities.openEmpPop("addGroupEmp");
//    Utilities.windowOpen(url,"addGroupUser",700,680);
}
</script>