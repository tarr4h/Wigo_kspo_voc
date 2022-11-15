<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>


 <div class="mBox1 ">
 	<div class="gTitle1">
			<h3 class="mTitle1">Api Key 관리</h3>
				<div class="gRt">
					<code:btnGroup name="Apikey" gridId="grdList"  hideExcel="true"/>
				</div>
		</div>
 	<div  id="divGridList" style="height: ${rightBoxHeight5}"
			data-get-url="<c:url value='${urlPrefix}/getList'/>${urlSuffix}"
			data-grid-id="grdList" data-pagenation="N" data-type="grid"
			data-post="Y"
			data-tpl-url="<c:url value='/static/gridTemplate/api/apikey.xml'/>">
		</div>
		
</div>


                          
         
<script>

/* 공통코드 그리드 load */
function grdList_load(gridView, gridId) {
	search();
}



/* 검색조건 검색 */
function search() {
	var param = {
            topComnCd : "S000",
            prntsComnCd : "S000",
            comnCdLvlNo : 2,
            recordCountPerPage : 100000
        };
	grdList.loadUrl(null, param);
}

/* 대분류 추가 */
function newApikey(){
	var data = {
            prntsComnCd : "S000",
            comnCdLvlNo : 2,
            topComnCd : "S000",
        };
    addcode(data);;
}

function saveApikey(){
	saveCodeList(grdList);
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
			if (Utilities.processResult(data, jqXHR, "시스템 저장에 실패했습니다.")) {
				alert("시스템 저장에  성공했습니다.");
				gridView.reload();
			}
		});
	} else {
		alert("수정된 데이터가 없습니다.");
	}
}


/* 행추가 */
function addcode(data){
	if (!data)
		return;
	data.stat = "c";
	grdList.addRow(data);
}

/* 대분류 삭제 */
function removeApikey(){
	removeRow(grdList);
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
			if (Utilities.processResult(data, jqXHR, "시스템 삭제에 실패했습니다.")) {
				alert("시스템 삭제에  성공했습니다.");
				gridView.reload();
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
	    	alert("시스템코드를 입력하세요.");
	    	return false;
	    }
// 	    if(Utilities.isEmpty(cuGridData[i].comnCdOdrg)||cuGridData[i].comnCdOdrg<=0){
// 	    	alert("순번은 1이상입니다.");
// 	    	return false;
// 	    }
	    if(Utilities.isEmpty(cuGridData[i].comnCdNm)){
            alert("시스템코드명을 입력하세요.");
            return false;
        }
	}
	return true;
}


function grdList_btnApikey_buttonClicked(gridView,row,col,json){
	var url = "<c:url value='${urlPrefix}/getApikey${urlSuffix}'/>";
    Utilities.getAjax(url, json, true, function(data, jqXHR) {
        if (Utilities.processResult(data, jqXHR, "API키 발급에 실패했습니다.")) {
            grdList.setValue(json.rowKey, "apikey", data.token, false);
            
        }
    });
}
</script>