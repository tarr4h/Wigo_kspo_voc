<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%> 


<div class="title">
        <h1>사원 선택 팝업</h1>
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
			<div class="tree mCustomScrollbar " style="height:${rightBoxHeight1}">
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

			
			
<!-- 			<div class="mBoard2 " > -->
			<form name="frmDetail" id="frmDetail">
	   		   	<input type="hidden" id="upOrgCd" name="prntsMenuCd" /> 
	   	<input type="hidden" id="orgLvlNo" name="menuLvlNo" />
	   	<input type="hidden" id="regrId" name="regrId" />
	   	<input type="hidden" id="regDt" name="regDt" />
	   	<input type="hidden" id="amdrId" name="amdrId" />
	   	<input type="hidden" id="amdDt" name="amdDt" />
	   	<input type="hidden"  name="orgId" id="orgId" />
		<input type="hidden" readonly name="orgNm" id="orgNm" />
		<input type="hidden" readonly name="orgEmpNm" id="orgEmpNm"   />
										
							</form>
							
		<div class="mSort2">
		<form role="form" id="frmSearch">
	   	<input  type="hidden" id="orgId" name="orgId" />

                
				<ul>
					<li class="w100per" >
						<div class="tit">직원명</div>
						<div class="txt">
							<input type="text" id="empNmLike" class="it" data-enter="search" name="empNmLike"  placeholder="사원명">
						</div>
						<div class="tit">사번</div>
						<div class="txt">
							<input type="text"class="it"   id="empIdLike" data-enter="search" name="empIdLike" placeholder="사번">
						</div>
						<div class="tit">재직상태</div>
						<div class="txt">
							<code:commCode id="statusCd" name="statusCd" codeCd="C040" prefixValue="" multiple="false" selectedValue="10"/>
						</div>
						<div class="txt">
						
						<label class="mCheckbox1">
													<input type="checkbox" name="chkAllEmp" id="chkAllEmp" />
													<span class="label">하부조직포함</span>
						</label>
						
						
							
						</div>
						<div class="button">
							<a href="#" data-click="search" class="mBtn1">검색</a>
						</div>
					</li>
					
				</ul>
				</form>
				
			</div>
<!-- 							</div> -->
			
		<div class="mBox1 ">
		<div class="gTitle1">
								<h3 class="mTitle1">사원목록</h3>
								<div class="gRt">
					                <a href="#" class="mBtn1 primary" data-click="selectEmp" id="btnSelectEmp" id="btnSave">선택</a>
					            </div>
							
			</div>
		<div id="divGrid"   style="height:525px" data-get-url="<c:url value='${urlPrefix}/employ/getList${urlSuffix}'/>"
      data-grid-id="grdList" data-type="grid" data-tpl-url="<c:url value='/static/gridTemplate/system/empPop.xml'/>">
    		</div>
		</div>
		
		
	</div>
</div>
</div>


<script>

// var _selectedData = null;

function onTreeSelect(data,node,tree){
	
	Utilities.mapToForm(data,frmDetail);
	$("#frmSearch").find("#orgId").val(data.orgId);
	search();
}	
function search(){
	var param = Utilities.formToMap("frmSearch");
	param.recordCountPerPage = 10000;
	 if(document.getElementById("chkAllEmp").checked){
	    	param.topOrgId = param.orgId; 
	    	param.orgId = "";
	    }
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

function selectEmp(){
	var list = grdList.getCheckedJson();
	if(list.length == 0)
	{
		alert("직원을 선택해 주세요");
		return;
	}
	var callbackName = "${callback}";
    if(!callbackName)
        callbackName = "onEmpSelect";
    var win = Utilities.getOpener();
    try{
        if(win[callbackName])
            win[callbackName](list);    
    }catch(e){}
    Utilities.closeModal();
}

</script>