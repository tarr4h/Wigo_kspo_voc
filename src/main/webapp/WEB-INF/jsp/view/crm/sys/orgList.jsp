<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%> 


	<div class="gLeft">
          <div class="mBox1">
			<div class="gTitle1 ">
								<h3 class="mTitle1">조직 관리</h3>
								<div class="gRt">
									<div class="mFlex2">
                                                <span class="flex">
                                                    <input type="text" id="sWord" name="sWord" class="it" data-type="searchTree" data-tree="divTree" title="조직명" value="">
                                                </span>
                                                <a href="#;" class="mBtn1 m " data-type="searchTreeBtn" data-tree="divTree">검색</a>
                                                <a href="#;" data-click="syncOrg" id="btnSaveMenuDetail" class="mBtn1 m lWhite" style="cursor: pointer;">동기화</a>
                                            </div>
<!-- 									<a href="#" data-click="syncOrg" id="btnSaveMenuDetail" class="mBtn1 m lWhite" style="cursor: pointer;">동기화</a> -->
								</div>
			</div>
				
			<div class="tree mCustomScrollbar " style="height:${leftBoxHeight1}">
                <div id="divTree"  style="height:100%" 
                data-type="tree" 
				data-get-url="<c:url value='${urlPrefix}/getTreeList'/>${urlSuffix}"
				data-change-seq="N"
				data-search="Y"
				></div>

        	</div>
		</div>
	</div>
	<div class="gRight">
		<div class="mBox1">

			<div class="gTitle1 ">
								<h3 class="mTitle1">상세정보</h3>
								<div class="gRt">
								</div>
			</div>
			
			<div class="mBoard2 ">
			<form name="frmDetail" id="frmDetail">
	   	<input type="hidden" id="upOrgCd" name="prntsMenuCd" /> 
	   	<input type="hidden" id="orgLvlNo" name="menuLvlNo" />
	   	<input type="hidden" id="regrId" name="regrId" />
	   	<input type="hidden" id="regDt" name="regDt" />
	   	<input type="hidden" id="amdrId" name="amdrId" />
	   	<input type="hidden" id="amdDt" name="amdDt" />
								<table>
									<caption>선택정보</caption>
									<colgroup>
										<col width="13%">
										<col width="13%">
										<col width="11%">
										<col width="*">
										<col width="9%">
										<col width="*">
										<col width="9%">
										<col width="16%">
									</colgroup>
									<tbody>
										<tr>
											<th scope="row" class="left">조직 ID</th>
											<td>
												<input type="text" readonly name="orgId" id="orgId" readonly="readonly" class="it"/>
											</td>
											<th scope="row" class="left">조직명</th>
											<td>
												 <input type="text" readonly name="orgNm" id="orgNm" class="it"/>
											</td>
											<th scope="row" class="left">조직리더</th>
											<td>
												<input type="text" readonly name="orgEmpNm" id="orgEmpNm"  class="it" />
											</td>
											
										</tr>
										
									</tbody>
								</table>
							</form>
							</div>
			
		</div>	
			

	   	<div class="mBox1 ">

			<div class="gTitle1">
								<h3 class="mTitle1">하부조직</h3>
								<div class="gRt">
								</div>
								
			</div>
			
				<div id="divGrid" class='' style="height:${rightBoxHeight3}" data-get-url="<c:url value='${urlPrefix}/getList${urlSuffix}'/>"
      data-grid-id="grdList" data-type="grid" data-tpl-url="<c:url value='/static/gridTemplate/system/orgList.xml'/>">
    		</div>
			</div>
	   		 
	   		
	   	</div>





<script>


// var _selectedData = null;

function onTreeSelect(data,node,tree){
	var param = {upOrgId : data.orgId};
	Utilities.mapToForm(data,frmDetail);
	param.recordCountPerPage = 10000;
	grdList.loadUrl("",param);
}		
function getToken(element,data){
	var systemCd = $("#topMenuCd").val();
	  var url = "<c:url value='${urlPrefix}/menu/getToken${urlSuffix}'/>";
	  var param = {systemCd : systemCd};
      Utilities.getAjax(url,param,false,function(data,jqXHR){
    	  var token = data.token;
    	  $("#menuApiKey").val(token);
      });
}
function grdList_load(gridView,gridId){
//  search();
}

function onGridLoad(gridView,gridId){

}
function onGridCellClick(gridView,rowIndex,columnName,colIndex,fieldIndex){
    
}
function onGridCellDblClick(gridView,rowIndex,columnName,colIndex,fieldIndex){
    
}
function onGridRowChanged(gridView, oldRow, newRow){
    
}
function grdList_pageMove(gridView,pageNo){
    $("form#frmSearch").find("#currentPageNo").val(pageNo);
    search();
}
function saveMenuDetail(){
	var json =Utilities.formToMap("frmDetail");
	json.stat="U";
	saveMenu(null,[json]);
}
function saveMenu(element,saveJson){
	if(element)
    	saveJson = grdList.getSaveJson();
    if(saveJson.length){
        if(!confirm("수정된 데이터를 저장하시겠습니까?"))
            return ;
        var url = "<c:url value='${urlPrefix}/saveList${urlSuffix}'/>";
        Utilities.getAjax(url,saveJson,true,function(data,jqXHR){
            if(Utilities.processResult(data,jqXHR,"조직 저장에 실패했습니다."))
            {
                alert("조직 저장에  성공했습니다.");
                
                for(var i=0;i<saveJson.length;i++){
            		var json = saveJson[i];
            		$("#divTree").setText(json.menuCd,json.menuNm);
        			$("#divTree").setNodeData(json.menuCd,json);
            	}
                grdList.resetAllRowStatus();
            }
        });
    }
    else{
        alert("수정된 데이터가 없습니다.");
    }
}
function syncOrg(){
	if(!confirm("조직정보를 지금 동기화 하겠습니까?"))
		return ;
	var url = "<c:url value='${urlPrefix}/sync${urlSuffix}'/>";
	Utilities.blockUI();
    Utilities.getAjax(url,null,false,function(data,jqXHR){
    	Utilities.unblockUI();
        if(Utilities.processResult(data,jqXHR,"조직 정보 동기화에 실패했습니다."))
        {
            alert("조직 정보 동기화에  성공했습니다.");
           location.reload();
        }
    });
}
function newMenuDetail(){
	newMenu();
}
function newMenu(){
	var node = $("#divTree").getSelectedNode();
	var menuLvlNo =1;
	var prntsMenuCd = "";
	var topMenuCd = "";
	if(node&&node.menuLvlNo && node.menuCd)
	{
		menuLvlNo += node.menuLvlNo;
		prntsMenuCd = node.menuCd;
		topMenuCd = node.topMenuCd;
	}
	 var url = "<c:url value='${urlPrefix}/add${urlSuffix}'/>?menuLvlNo=" + menuLvlNo  + "&prntsMenuCd=" + prntsMenuCd + "&topMenuCd=" + topMenuCd;
//	 Utilities.windowOpen(url,"addMenu",900,930);
	 Utilities.openModal(url,900,749);
}


function addMenu(json){
	var parentId = json.prntsMenuCd;
	if(!parentId)
		parentId = "0000000000";
	
// 	grdList.addRow(json);
// 	json.id = json.menuCd;
// 	json.text = json.menuNm;
// 	json.ord = json.menuOdrg;
// 	json.stat = "n";
	$("#divTree").addNode(parentId,json);
	$("#divTree").resort(parentId);
	grdList.reload();
}
function removeMenu(){
    var list = grdList.getCheckedJson();
    if(list.length ==0)
    {
        alert("체크된 데이터가 존재하지 않습니다.");
        return;
    }
    var saveJson = grdList.getCheckedJson();
    if(saveJson.length){
        if(!confirm("데이터를 삭제하면 복구할  수 없습니다. 계속하시겠습니까?"))
            return ;
        var url = "<c:url value='${urlPrefix}/deleteList${urlSuffix}'/>";
        Utilities.getAjax(url,saveJson,true,function(data,jqXHR){
            if(Utilities.processResult(data,jqXHR,"조직 삭제에 실패했습니다."))
            {
            	
                alert("조직 삭제에  성공했습니다.");
                grdList.removeCheckRow();
                for(var i=0;i<saveJson.length;i++){
                 	$("#divTree").removeNode(saveJson[i].menuCd);
                 }
            }
        });
    }
    else{
        alert("선택된 데이터가 없습니다.");
    }
}
function removeMenuDetail(){
	var saveJson =Utilities.formToMap("frmDetail");
	if(saveJson){
        if(!confirm("데이터를 삭제하면 복구할  수 없습니다. 계속하시겠습니까?"))
            return ;
        var url = "<c:url value='${urlPrefix}/deleteList${urlSuffix}'/>";
        Utilities.getAjax(url,[saveJson],true,function(data,jqXHR){
            if(Utilities.processResult(data,jqXHR,"조직 삭제에 실패했습니다."))
            {
                
                alert("조직 삭제에  성공했습니다.");

                $("#divTree").removeNode(saveJson.menuCd);

            }
        });
    }
}
function groupPop(){
	var json =Utilities.formToMap("frmDetail");
	if(!json.menuCd)
		return;
	if(json.menuLvlNo == 1)
		return;
	if(!json.menuUrl)
		return;
	var url = "<c:url value='${urlPrefix}/group/groupPopup${urlSuffix}'/>?menuCd="+json.menuCd;
    /* Utilities.windowOpen(url,"groupPopup",500,580); */
    //Utilities.windowOpen(url,"addGroupUser",700,680);
    Utilities.openModal(url,500,685);
}
function grdList_btnGroup_buttonClicked(gridView,row,col,json){
    var url = "<c:url value='${urlPrefix}/group/groupPopup${urlSuffix}'/>?menuCd="+json.menuCd;
    /* Utilities.windowOpen(url,"groupPopup",500,580); */
    //Utilities.windowOpen(url,"addGroupUser",700,680);
    Utilities.openModal(url,500,685);
}
function saveGroupChecked(menuCd,saveJson){
    var url = "<c:url value='${urlPrefix}/group/setMenuGroup${urlSuffix}'/>?menuCd="+ menuCd;
    
    Utilities.getAjax(url,saveJson,true,function(data,jqXHR){
        if(Utilities.processResult(data,jqXHR,"그룹 설정에 실패했습니다."))
        {
            alert("그룹 설정에  성공했습니다.");
            
        }
    });
}
function onGridCellRender(gridView,colName,json){
	if(colName == "btnGroup" )
	{
		if(json.menuLvlNo == 1)
			return false;
		return  !!(json.menuUrl)
	}
}
</script>