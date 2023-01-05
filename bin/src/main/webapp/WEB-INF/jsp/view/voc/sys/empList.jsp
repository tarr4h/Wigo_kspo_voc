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
								<h3 class="mTitle1">조직</h3>
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
			
			
			
			
                <div id="divTree"  style="height:100%" 
                data-type="tree" 
				data-get-url="<c:url value='${urlPrefix}/organization/getTreeList'/>${urlSuffix}"
				data-change-seq="N"
				></div>

        	</div>
		</div>
	</div>
	<div class="gRight">
		<div class="mBox1">
<!-- 		<div class="gTitle1"> -->
<!-- 								<h3 class="mTitle1">사원 검색</h3> -->
<!-- 								<div class="gRt"> -->
<!-- 								</div> -->
<!-- 			</div> -->
		<div class="mBoard2">
			<form role="form" id="frmSearch">
				<input type="hidden" readonly name="orgEmpNm" id="orgEmpNm"   />
                <input type="hidden" readonly name="orgId" id="orgId" />
                <input type="hidden" readonly name="orgNm" id="orgNm" />
								<table>
									<caption>기본정보</caption>
									<colgroup>
										<col width="10%" />
										<col width="15%" />
										<col width="10%" />
										<col width="15%"  />
										<col width="10%" />
										<col width="15%"  />
										<col width="10%"  />
										<col width="15%" />
									</colgroup>
									<tbody>
										<tr>
											<th scope="row" class="left"><span class="iMust">사원명</span></th>
											<td>
												<input type="text" class="it" id="empNmLike" data-enter="search" name="empNmLike"  placeholder="사원명">
											</td>
											<th scope="row" class="left">사번</th>
											<td>
												<input type="text" class="it" id="empIdLike" data-enter="search" name="empIdLike" placeholder="사번">
											</td>
											<th scope="row" class="left">재직 상태</th>
											<td>
												<code:commCode id="statusCd" name="statusCd" codeCd="C040" prefixValue="" multiple="true" selectedValue="10"/>
											</td>
											<td>
												<label class="mCheckbox1">
													<input type="checkbox" id="chkAllEmp" name="chkAllEmp" >
													<span class="label">하부조직원포함</span>
												</label>
											</td>
											
											<th scope="row" class="left">
											<a href="#" class="mBtn1" data-click="search">검색</a>
											</th>
											
										</tr>
										
									</tbody>
								</table>
								</form>
							</div>
			</div>
			<div class="mBox1 ">
		<div class="gTitle1">
								<h3 class="mTitle1">사원 목록</h3>
								<div class="gRt">
									<a href="#" data-click="syncEmp" id="btnSaveMenuDetail" class="mBtn1 m lWhite" style="cursor: pointer;">동기화</a>
								</div>				
		<div id="divGrid"  style="height:${rightBoxHeight4}" data-get-url="<c:url value='${urlPrefix}/getList${urlSuffix}'/>"
      data-grid-id="grdList" data-type="grid" data-tpl-url="<c:url value='/static/gridTemplate/system/empList.xml'/>">
    		</div>
    		
		</div>
	</div>
     </div>
     
<script>

function onTreeSelect(data,node,tree){
	
// 	Utilities.mapToForm(data,frmDetail);
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
function grdList_pageMove(gridView,pageNo){
    $("form#frmSearch").find("#currentPageNo").val(pageNo);
    search();
}

function syncEmp(){
	if(!confirm("직원정보를 지금 동기화 하겠습니까?"))
		return ;
	var url = "<c:url value='${urlPrefix}/sync${urlSuffix}'/>";
	Utilities.blockUI();
    Utilities.getAjax(url,null,false,function(data,jqXHR){
    	Utilities.unblockUI();
        if(Utilities.processResult(data,jqXHR,"직원 정보 동기화에 실패했습니다."))
        {
            alert("직원 정보 동기화에  성공했습니다.");
           location.reload();
        }
    });
}
</script>