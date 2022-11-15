<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>


<!-- popup -->
<div class="popup">
	<!-- top -->
	<strong class="title">메뉴 정보${menuCd }</strong>
	<button id="btnCancel" data-btn-type="closeModal" class="btnClose"></button>
	<!-- //top -->

	<!-- titleBox -->
	<div class="contentsWrap">
		<div class="titleBox">
			<h4>순서변경</h4>
				<div class="btnRWrap">
					<button id="btnSave" data-click="saveSeq" class="btnBasic _em">저장</button>
					<button id="btnCancel" data-btn-type="closeModal" class="btnBasic">닫기</button>
				</div>
		</div>
	<!-- //titleBox -->
	<!-- form start -->
		<form id="frmMain">
			<div class="boardWrite">
				<table>
					<colgroup>
						<col width="10%">
						<col width="90%">
					</colgroup>
					<tbody id="sortBody">
					</tbody>
				</table>
			</div>
		</form>
	</div>
</div>
<script>
function saveSeq(){
	var list = $("#sortBody").find("tr");
	var arr = [];
	for(var i=0;i<list.length;i++){
		arr.push($(list[i]).data());
	}
	if(Utilities.getOpener().changeSeq)
		Utilities.getOpener().changeSeq(arr);
	Utilities.closeModal();
}
$(document).ready(function(){
	var param = Utilities.getOpener().sortList;
	var bd = $("#sortBody");
	bd.sortable();
	bd.disableSelection();
	for(var i=0;i<param.list.length;i++){
		var item = param.list[i];
		var tr = $("<tr><td>"+(i+1)+"</td><td>"+item[param.textKey]+"</td></tr>");
		tr.data(item);
		bd.append(tr);
	}
});

</script>