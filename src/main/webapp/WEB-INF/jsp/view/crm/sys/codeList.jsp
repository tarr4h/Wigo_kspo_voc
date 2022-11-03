<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>



<div class="gLeft">
	<div class="mBox1">
		<div class="gTitle1">
			<h3 class="mTitle1">대분류 목록</h3>
				<div class="gRt">
				<a href="#" data-click="excelDownload" id="excelDownload" class="mBtn1 m lWhite">엑셀다운로드</a>
				<a href="#" data-click="newCode" id="btnAddList" class="mBtn1 m lWhite">대분류 추가</a>
				<code:btnGroup name="Code" gridId="grdList" hideInsert="true" hideExcel="true"/>
				</div>
		</div>
			<div  id="divGrid" 
			style="height: ${leftBoxHeight1}"
			data-get-url="<c:url value='${urlPrefix}/getList'/>${urlSuffix}"
			data-grid-id="grdList" data-pagination="N" data-type="grid"
			data-post="Y"
			data-tpl-url="<c:url value='/static/gridTemplate/code/codeList1.xml'/>">
			</div>
	</div>
</div>


<div class="gRight">
<div class="mSort2">
<form role="form" id="frmSearch">
			<input type="hidden" name="currentPageNo" id="currentPageNo" value="1" />
    		<input type="hidden" name="comnCdLvlNo" id="comnCdLvlNo" value="1" />
			<ul >
				<li class="w100per">
					 <div class="tit" style="width: 20%;">대분류명</div>
                     <div class="txt" style="width:20%">
                     <input type="text" id="comnCdNm" name="comnCdNm" data-enter="search" class="it" placeholder="코드명" />

                     </div>
                      <div class="tit" style="width: 20%;">대분류코드</div>
                     <div class="txt" style="width:20%">
                     <input type="text" id="comnCdLike" name="comnCdLike" data-enter="search" class="it" placeholder="대분류코드" />

                     </div>
					 <div class="tit" style="width: 20%;">
                         <a href="#" class="mBtn1" data-click="search" >검색</a>
                     </div>
				</li>
			</ul>
		</form>
		
			</div>
	<div class="mBox1 ">
		<div class="gTitle1">
			<h3 class="mTitle1">소분류</h3>
				<div class="gRt">
				<code:btnGroup name="CodeDetail" gridId="grdDetail"  hideExcel="true"/>
				</div>
		</div>
		<div >
			<div id="divGridDetail" style="height: ${rightBoxHeight4}"
			data-get-url="<c:url value='${urlPrefix}/getList'/>${urlSuffix}"
			data-grid-id="grdDetail" data-pagenation="N" data-type="grid"
			data-grid-callback="onGridLoad"
			data-post="Y"
			data-tpl-url="<c:url value='/static/gridTemplate/code/codeList2.xml'/>">
			</div>
			
		</div>
	
	</div>
</div>
<script>
var _current_code_cd = null;

/* 공통코드 엑셀 다운로드 */
function excelDownload() {
	grdList.exportExcel("공통코드 리스트.xlsx");
}

/* 공통코드 엑셀 업로드 */
function excelUpload() {
	grdList.importExcel();
}

/* 공통코드 그리드 load */
function grdList_load(gridView, gridId) {
	search();
}

/* 하부코드 그리드 load */
function grdDetail_load(gridView, gridId) {
}

/* 공통코드 Row클릭 시 */
function grdList_rowChanged(grdList, oldRow, newRow, rowData) {
	if(rowData.stat == "c")
		_current_code_cd = "c";
	else
		_current_code_cd = rowData.comnCd;
	if (newRow > -1) {
		// 		grdDetail.commit();
		// 		var saveJson = grdDetail.getSaveJson();
		// 		if(saveJson.length){
		// 			if(!confirm("변경된 데이터가 존재합니다. 먼저 저장하시겠습니까?"))
		// 	        {
		// 				grdDetail.cancel();
		// 	        }
		// 			else
		// 				saveDetail();
		// 		} 
		var param = {
			topComnCd : rowData.topComnCd,
			prntsComnCd : rowData.comnCd,
			comnCdLvlNo : Number(rowData.comnCdLvlNo) + 1,
			recordCountPerPage : 100000
		};
		if(!param.prntsComnCd)
			param.prntsComnCd = "QWERTREWQ";
		var url = "<c:url value='${urlPrefix}/getList'/>${urlSuffix}";
		grdDetail.loadUrl(url, param);
	}
}


function onGridLoad(gridView, gridId) {
	
}

/* 그리드 컬럼 클릭 */
function onGridCellClick(gridView, rowIndex, columnName, colIndex,
		fieldIndex) {

}

/* 그리드 컬럼 더블 클릭 */
function onGridCellDblClick(gridView, rowIndex, columnName, colIndex,
		fieldIndex) {

}

function onGridRowChanged(gridView, oldRow, newRow) {

}

/* 검색조건 검색 */
function search() {
	$("form#frmSearch").find("#currentPageNo").val(1);
 	var url = "<c:url value='${urlPrefix}/getList${urlSuffix}'/>";
	var param = Utilities.formToMap("frmSearch");
	param.recordCountPerPage = 100000;
	grdList.loadUrl(url, param);
	grdDetail.clear();
}

/* 대분류 추가 */
function newCode(){
// 	var url = "<c:url value='${urlPrefix}/add${urlSuffix}'/>?codeLevel=1";
// 	Utilities.openModal(url,500,292,"addCode");
//	Utilities.windowOpen(url, "addCode", 900, 730);
	grdList.addRow({prntsComnCd:null,comnCdLvlNo:1});
}

/* 대분류 저장 */
function saveCode(){
	saveCodeList(grdList);
}

/* 소분류 저장 */
function saveCodeDetail(){
	saveCodeList(grdDetail);
}

/* 저장 */
function saveCodeList(gridView) {
	if(!saveValidate(gridView)) return;
	var saveJson = gridView.getSaveJson();
	if (saveJson.length) {
		if (!confirm("수정된 데이터를 저장하시겠습니까?"))
			return;
		if(saveValidate(gridView))
		var url = "<c:url value='${urlPrefix}/saveList${urlSuffix}'/>";
		Utilities.getAjax(url, saveJson, true, function(data, jqXHR) {
			if (Utilities.processResult(data, jqXHR, "코드 저장에 실패했습니다.")) {
				alert("코드 저장에  성공했습니다.");
				gridView.reload();
			}
		});
	} else {
		alert("수정된 데이터가 없습니다.");
	}
}

/* 소분류 행추가 */
function newCodeDetail(){
	if(!_current_code_cd){
		alert("먼저 공통코드를 선택하세요.");
		return false;
	}
	if(_current_code_cd =="c"){
		alert("먼저 신규 대분류를 저장하세요");
		return false;
	}
	var data = {
			prntsComnCd : grdList.getCurrentJson().comnCd,
			comnCdLvlNo : grdList.getCurrentJson().comnCdLvlNo + 1,
			topComnCd : grdList.getCurrentJson().topComnCd,
		};
	addcode(data);
}

/* 행추가 */
function addcode(data){
	if (!data)
		return;
	data.stat = "n";
	if (data.codeLevel == 1)
		grdList.addRow(data);
	else{
		data.stat = "c";
		grdDetail.addRow(data);
	}
}

/* 대분류 삭제 */
function removeCode(){
	removeRow(grdList);
}

/* 소분류 삭제 */
function removeCodeDetail(){
	removeRow(grdDetail);
}

/* 코드삭제(행삭제) */
function removeRow(gridView) {
 	var list = gridView.getCheckedJson();
	if (list.length == 0) {
		alert("체크된 데이터가 존재하지 않습니다.");
		return;
	}
	var saveJson = gridView.getCheckedJson();
	if (saveJson.length) {
		if (!confirm("데이터를 삭제하면 복구할  수 없습니다. 계속하시겠습니까?"))
			return;
		var url = "<c:url value='${urlPrefix}/deleteList${urlSuffix}'/>";
		Utilities.getAjax(url, saveJson, true, function(data, jqXHR) {
			if (Utilities.processResult(data, jqXHR, "코드 삭제에 실패했습니다.")) {
				alert("코드 삭제에  성공했습니다.");
				if(gridView == grdDetail){
					gridView.removeCheckRow();
				}
				else{
					search();
					grdDetail.clear();
				}
			}
		});
	} else {
		alert("선택된 데이터가 없습니다.");
	}
}

/* 하부코드 수정 Validate*/
function saveValidate(gridView){
	var cuGridData = gridView.getSaveJson();
	for(var i=0; i<cuGridData.length; i++){
	    if(Utilities.isEmpty(cuGridData[i].comnCd)){
	    	alert("코드아이디를 입력하세요.");
	    	return false;
	    }
// 	    if(Utilities.isEmpty(cuGridData[i].comnCdOdrg)||cuGridData[i].comnCdOdrg<=0){
// 	    	alert("순번은 1이상입니다.");
// 	    	return false;
// 	    }
	    if(Utilities.isEmpty(cuGridData[i].comnCdNm)){
            alert("코드명을 입력하세요.");
            return false;
        }
	}
	return true;
}
</script>