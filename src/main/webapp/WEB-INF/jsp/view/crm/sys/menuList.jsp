<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%> 

	<div class="gLeft" data-has-size="Y">
          <div class="mBox1">
			<div class="gTitle1 ">
								<h3 class="mTitle1">메뉴 정보</h3>
								<div class="gRt">
								</div>
			</div>
			<div class="tree mCustomScrollbar " style="height:${leftBoxHeight1}">
	         		<div>
	       	<div id="divTree"  style="height:100%" 
	               data-type="tree" 
				data-get-url="<c:url value='${urlPrefix}/getMenuTree'/>${urlSuffix}"
				data-change-seq="Y"
			    data-seq-url="<c:url value='${urlPrefix}/savemenuOdrgList${urlSuffix}'/>"
			    data-seq-col="menuOdrg"
				data-wrap-root ="메뉴"
				></div>
	        </div>
		
			</div>
		</div>
	</div>
	<div class="gRight">
		
		<div class="mBox1 p24">

			<div class="gTitle1">
								<h3 class="mTitle1">상세정보</h3>
								<div class="gRt">
									<a href="#" data-click="groupPop" id="btnGroupPop" class="mBtn1 m lWhite" style="cursor: pointer;">그룹권한</a>
									 <code:btnGroup name="MenuDetail"  />
								</div>
			</div>
			
			<div class="mBoard2 " >
			<form name="frmDetail" id="frmDetail">
	   	<input type="hidden" id="prntsMenuCd" name="prntsMenuCd" /> 
	   	<input type="hidden" id="topMenuCd" name="topMenuCd" />
	   	<input type="hidden" id="menuLvlNo" name="menuLvlNo" />
	   	<input type="hidden" id="menuOdrg" name="menuOdrg" />
	   	<input type="hidden" id="menuIconCd" name="menuIconCd" />
	   	<input type="hidden" id="regrId" name="regrId" />
	   	<input type="hidden" id="regDt" name="regDt" />
	   	<input type="hidden" id="amdrId" name="amdrId" />
	   	<input type="hidden" id="amdDt" name="amdDt" />
	   	<input type="hidden" id="menuPopupYn" name="menuPopupYn" />
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
											<th scope="row" class="left">메뉴코드</th>
											<td>
												<input type="text" name="menuCd" id="menuCd" readonly="readonly" class="it"/>
											</td>
											<th scope="row" class="left">메뉴명</th>
											<td>
												<input type="text" name="menuNm" id="menuNm" class="it" />
											</td>
											<th scope="row" class="left">메뉴설명</th>
											<td>
												<input type="text" name="menuExpln" id="menuExpln"  class="it" />
											</td>
											<th scope="row" class="left">사용여부</th>
											<td>
												<code:commCode name="useYn" id="useYn" codeCd="S010"/>
											</td>
										</tr>
										<tr>
											<th scope="row" class="left">메뉴주소</th>
											<td>
												<input type="text" name="menuUrl" id="menuUrl" class="it" />
											</td>
											<th scope="row" class="left">권한사용</th>
											<td>
												<code:commCode name="menuAuthYn" id="menuAuthYn" codeCd="S010"/>
											</td>
											<th scope="row" class="left">정보변경로그</th>
											<td >
												<code:commCode name="chngLogYn" id="chngLogYn" codeCd="S010"/>
											</td>
											<th scope="row" class="left">메뉴노출</th>
											<td >
												<code:commCode name="menuShowYn" id="menuShowYn" codeCd="S010"/>
											</td>
										</tr>
									</tbody>
								</table>
							</form>
							</div>
			
		</div>	
			

	   	<div class="mBox1" style="margin-top:14px">

			<div class="gTitle1">
								<h3 class="mTitle1">하부메뉴</h3>
								<div class="gRt">
									 <code:btnGroup name="Menu"/>
								</div>
								
			</div>
			
			<div class='' >
				<div id="divGrid"  style="height:${rightBoxHeight1}" data-get-url="<c:url value='${urlPrefix}/getList${urlSuffix}'/>"
      data-grid-id="grdList" data-type="grid" data-tpl-url="<c:url value='/static/gridTemplate/menu/menu.xml'/>">
    		</div>
			</div>
	   		 
	   		
	   	</div>
	   	
 	</div>


<script>

// var _selectedData = null;

function onTreeSelect(data,node,tree){

	if(!data.menuCd || data.menuCd == "0000000000"){
		var param = {menuLvlNo : 1};
		$("#frmDetail")[0].reset();
		$("#btnGroupPop").hide();
		$("#btnRemoveMenuDetail").hide();
		$("#btnSaveMenuDetail").hide();
	}
	else
	{
		$("#btnRemoveMenuDetail").show();
		$("#btnSaveMenuDetail").show();
		if( data.menuUrl && data.menuLvlNo != 1 ){
			$("#btnGroupPop").show();
		}
		else{
			$("#btnGroupPop").hide();
		}
		var param = {prntsMenuCd : data.menuCd};
		Utilities.mapToForm(data,frmDetail);
        
	}
	if(data.meneLevel == 1){
		$("#btnTokenPopbtnTokenPop").show();
	}else {
		$("#btnTokenPopbtnTokenPop").hide();
	}
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
            if(Utilities.processResult(data,jqXHR,"메뉴 저장에 실패했습니다."))
            {
                alert("메뉴 저장에  성공했습니다.");
                
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
	 Utilities.openModal(url,900,300);
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
            if(Utilities.processResult(data,jqXHR,"메뉴 삭제에 실패했습니다."))
            {
            	
                alert("메뉴 삭제에  성공했습니다.");
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
            if(Utilities.processResult(data,jqXHR,"메뉴 삭제에 실패했습니다."))
            {
                
                alert("메뉴 삭제에  성공했습니다.");

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