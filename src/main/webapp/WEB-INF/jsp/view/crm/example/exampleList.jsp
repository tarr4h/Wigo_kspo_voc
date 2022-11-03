<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%> 
<!-- /.card -->
<!-- Horizontal Form -->
<!-- searchBox -->
<div class="searchBox">
    <form role="form" id="frmSearch">

        <ul>
            <li>
                <label for="postNm">콤보</label>
                <div class="selectBox">
                	<code:commCode codeCd="E030" id="combo1" name="combo1" prefixLabel="전체" />
                </div>
            </li>
            <li>
                <label for="postNm">멀티콤보</label>
                	<code:commCode codeCd="E030" id="combo2" name="menuCd" multiple="true"/>
            </li>
        	<li>
              <label for="postNm">연계콤보</label>
               <div class="selectBox">
              <select id="selTp1">
              	<option value="E010">방송구분</option>
              	<option value="E020">고/저학년구분</option>
              	<option value="E030">참여방법</option>
              	<option value="K010">메타데이터 유형</option>
              </select>
              </div>
               <div class="selectBox">
              <select id="selTp2" data-up-combo="selTp1">
              	<option value="">선택</option>
              </select>
              </div>
            </li>
           
            
        </ul>
        <ul>
            <li>
                <label for="useDt">시작일종료일</label>
                <input type="text" data-type="dateRangeStart" data-range-end="endDt" id="strtDt" name="strtDt" data-enter="search" data-button="Y" value=""/>
                            &nbsp;&#126;&nbsp;<input type="text" id="endDt" name="endDt" data-enter="search" data-button="Y" value=""/>
                            <button class="btnMini" id="setToday" data-click="setToday" style="margin: 1px 0px;">오늘</button>
                            <button class="btnMini" id="set1Week" data-click="set1Week" style="margin: 1px 0px;" data-default-button="Y">1주일</button>
                            <button class="btnMini" id="set1month" data-click="set1month" style="margin: 1px 0px;">1개월</button>
                            <button class="btnMini" id="set3month" data-click="set3month" style="margin: 1px 0px;">3개월</button>
                            <button class="btnMini" id="set6month" data-click="set6month" style="margin: 1px 0px;">6개월</button>
                            <button class="btnMini" id="setAllmonth" data-click="setAllmonth" style="margin: 1px 0px;">전체</button>
            </li>
        </ul>
        <ul>
            <li>
                <label for="postNm">날짜</label>
                <input type="text" data-type="date"  id="dt" name="dt" data-enter="search" data-button="Y" value=""/>
            </li>
            
            <li>
                <div style="display: flex;">
                    <div style="display: flex;">
                    <label for="searchItemSelect" style="width: 60px">검색어</label>
                    <div class="selectBox" style="width: 95px; margin-right: 5px">
                        <select class="selectbox" id="searchItemSelect" name="searchItemSelect">
                            <option value="stdnNm">학생명</option>
                            <option value="stdnLoginId">학생ID</option>
                            <option value="parentLoginId">보호자ID</option>
                        </select>
                    </div>
                    <input type="text" id="searchWord" data-enter="search" name="searchWord" class="medium" placeholder="검색어">
                </div>
                </div>
            </li>
        </ul>
        <div class="btnArea2">
            <button type="button" id="btnSearch" data-click="search" class="btnSearch">검색</button>
        </div>
    </form>
</div>
<!-- //searchBox -->
                    
<!-- titleBox -->
<div class="titleBox">
    <h4>목록</h4>
</div>
<!-- //titleBox -->
    
<div id="divGrid" style="height:520px" 
		data-post="Y" 
		data-grid-id="grdList" 
		data-pagenation="Y" 
		data-get-url="<c:url value='/example/getList'/>" 
		data-type="grid" 
		data-grid-callback="onGridLoad" 
		data-tpl-url="<c:url value='/static/gridTemplate/example/exampleList.xml'/>"
		>
</div>
<div class="btnRBWrap">
    <code:btnGroup name="Example" gridId="grdList" dispName="예제" forceSave="true" hideExcel="false" />
    <button type="button" data-name="예제" data-grid-id="grdList" data-disp-name="예제" data-click="excelDownload" id="btnExcelExample" class="btnBasic">엑셀저장</button>
<!--        <button type="button" data-grid-id="grdList" data-click="newUser" id="btnAddList" class="btnBasic">추가</button> -->
<!--        <button type="button" data-grid-id="grdList" data-click="saveUser" id="btnSaveList" class="btnBasic _em">저장</button> -->
<!--        <button type="button" data-grid-id="grdList" data-click="removeUser" id="btnDelList" class="btnBasic">삭제</button> -->
</div>
<form id="frmMove" method="post" name="frmMove" action="<c:url value='${urlPrefix}${urlSuffix}'/>">
	<input type="hidden" id="moveParam" name="moveParam" value='${moveParam }' />
</form>
<script>

function search()
{
    var url = "<c:url value='${urlPrefix}/getList${urlSuffix}'/>";
    var param = Utilities.formToMap("frmSearch");
    grdList.loadUrl(url,param);
}
function grdList_load(gridView,gridId){
     search();
}

</script>