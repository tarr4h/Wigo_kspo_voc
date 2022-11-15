<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%> 

<div class="title">
        <h1>조직 팝업</h1>
        <a href="#" data-btn-type="closeModal" class="close">팝업 닫기</a>
    </div>
 <div class="cont">
    
 <div class="mGrid1"> 
<div class="gLeft" style="top: 85px">
          <div class="mBox1" style="height:640px">
			<div class="gTitle1 ">
								<h3 class="mTitle1">조직 정보</h3>
								<div class="gRt">
									<div class="mFlex2">
                                           <span class="flex">
                                               <input type="text" id="sWord" data-type="searchTree" data-tree="divTree"  name="sWord" class="it" class="it" title="조직명" value="">
                                           </span>
                                           <a href="#;" class="mBtn1 m " data-type="searchTreeBtn" data-tree="divTree">검색</a>
                                     </div>
								</div>
			</div>
			<div class="tree mCustomScrollbar " style="height:${leftBoxHeight1}">
	         		<div>
	       	<div id="divTree"  style="height:100%" 
                data-type="tree" 
				data-get-url="<c:url value='${urlPrefix}/organization/getTreeList'/>${urlSuffix}"
				data-change-seq="N"
				></div>
	        </div>
		
			</div>
		</div>
	</div>
	<div class="gRight">
		<div class="mBox1 ">

			<div class="gTitle1">
								<h3 class="mTitle1">조직 목록</h3>
								<div class="gRt">
					                <a href="#" class="mBtn1 primary" data-click="selectOrg" id="btnSelectOrg" id="btnSave">선택</a>
					            </div>
							
			</div>
			
<!-- 			<div class="mBoard2 " > -->
			<form name="frmDetail" id="frmDetail">
	   		   	<input type="hidden" id="upOrgCd" name="prntsMenuCd" /> 
	   	<input type="hidden" id="orgLvlNo" name="menuLvlNo" />
	   	<input type="hidden" id="regrId" name="regrId" />
	   	<input type="hidden" id="regDt" name="regDt" />
	   	<input type="hidden" id="amdrId" name="amdrId" />
	   	<input type="hidden" id="amdDt" name="amdDt" />
								<table style="display:none">
									<caption>선택정보</caption>
									<colgroup>
										<col width="13%">
										<col width="20%">
										<col width="13%">
										<col width="20%">
										<col width="13%">
										<col width="20%">
									</colgroup>
									<tbody>
										<tr>
											<th scope="row" class="left">조직 ID</th>
											<td>
												<input type="text" readonly name="orgId" id="orgId" readonly="readonly" class="it"/>
											</td>
											<th scope="row" class="left">조직명</th>
											<td>
												<input type="text" readonly name="orgNm" id="orgNm" />
                          
											</td>
											<th scope="row" class="left">조직장</th>
											<td>
												<input type="text" readonly name="orgEmpNm" id="orgEmpNm"   />
											</td>
										</tr>
										
									</tbody>
								</table>
							</form>
<!-- 							</div> -->
			
<!-- 		</div>	 -->
<!-- 		<div class="mBox1 "> -->
		<div id="divGrid"   style="height:607px" data-get-url="<c:url value='${urlPrefix}/organization/getList${urlSuffix}'/>"
      data-grid-id="grdList" data-type="grid" data-tpl-url="<c:url value='/static/gridTemplate/system/orgPop.xml'/>">
    		</div>
		</div>
		
		
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
function selectOrg(){
	var list = grdList.getCheckedJson();
	if(list.length == 0)
	{
		alert("조직을 선택해 주세요");
		return;
	}
	var callbackName = "${callback}";
    if(!callbackName)
        callbackName = "onOrgSelect";
    var win = Utilities.getOpener();
    try{
        if(win[callbackName])
            win[callbackName](list);    
    }catch(e){}
    Utilities.closeModal();
}

</script>