<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%> 

<div class="title">
        <h1>API 실행 상세</h1>
        <a href="#" data-btn-type="closeModal" class="close">팝업 닫기</a>
    </div>
 <div class="cont">
 
 <form role="form" id="frmSearch">
 <input type="hidden" id="apiCd" name="apiCd" value="${apiCd }" />
<div class="mSort2">

			<ul >
				<li class="w100per">
					 <div class="tit" style="width: 4%;">호출일</div>
                     <div class="txt" style="width:6%">
                     <input type="text" class="it" data-type="dateRangeStart" data-range-end="endDt" id="strtDt" name="strtDt" data-enter="search" data-button="Y" value="" />

                     </div>
                     <div class="txt" style="width:6%">
                     <input type="text" id="endDt" name="endDt" data-enter="search" data-button="Y" value="" class="it" />

                     </div>
<!--                      <div class="txt" style="width:40%"> -->
<!--                      	<a href="#" class="mBtn1 m lWhite" id="setToday" data-click="setToday" >오늘</a> -->
<!-- 						<a href="#" class="mBtn1 m lWhite" id="set1Week" data-click="set1Week" data-default-button="Y">1주일</a> -->
<!-- 						<a href="#" class="mBtn1 m lWhite" id="set1month" data-click="set1month" >1개월</a> -->
<!-- 						<a href="#" class="mBtn1 m lWhite" id="set3month" data-click="set3month" >3개월</a> -->
<!-- 						<a href="#" class="mBtn1 m lWhite" id="set6month" data-click="set6month" >6개월</a> -->
<!-- 						<a href="#" class="mBtn1 m lWhite" id="setAllmonth" data-click="setAllmonth" >전체</a> -->
<!--                      </div> -->
                     
                     <div class="tit" style="width: 4%;">채널</div>
                     <div class="txt" style="width:6%">
                     	<code:commCode id="sysCd" name="sysCd" codeCd="S000" prefixLabel="전체" prefixValue=""/>
                     </div>
                     
                     <div class="tit" style="width: 4%;">URL</div>
                     <div class="txt" style="width:6%">
                     	<input type="text" id="apiCallUrlLike" data-enter="search" name="apiCallUrlLike" class="it" placeholder="URL">
                     </div>
                     
                     <div class="tit" style="width: 4%;">성공여부</div>
                     <div class="txt" style="width:6%">
                     	<select id="successYn" name="successYn" class="select">
				    		<option value="">전체</option>
				    		<option value="Y">성공</option>
				    		<option value="N">실패</option>
						</select>
                     </div>
                     
                     
                     <div class="tit" style="width: 4%;">결과코드</div>
                     <div class="txt" style="width:6%">
                     	<input type="text" id="apiRsltCdLike" data-enter="search" name="apiRsltCdLike" class="it" placeholder="결과코드">
                     </div>
                     
                     
                     
                     
					 <div class="tit" style="width: 4%;">
                         <a href="#" class="mBtn1" data-click="search" >검색</a>
                     </div>
				</li>
			</ul>
		
		
			</div>
</form>
<div class="mBox1 ">
		<div class="gTitle1">
            <h3 class="mTitle1">실행결과</h3>
            <div class="gRt">
                <code:btnGroup name="ApiHist" hideDelete="true" hideInsert="true" hideSave="true" dispName="API실행로그" gridId="grdList"/>
            </div>
        </div>
        
        <div id="divGrid"  style="height:450px" data-grid-id="grdList" 
                                        data-pagenation="Y" 
                                        data-get-url="<c:url value='${urlPrefix}/apiHist/getList${urlSuffix}'/>" 
                                        data-type="grid" 
                                        data-grid-callback="onGridLoad" 
                                        data-tpl-url="<c:url value='/static/gridTemplate/api/apiHist.xml'/>"
>
</div>
</div>
 </div>

<!-- /.card -->
<!-- Horizontal Form -->
<!-- searchBox -->

<script>

function search()
{
    var url = "<c:url value='${urlPrefix}/apiHist/getList${urlSuffix}'/>";
    var param = Utilities.formToMap("frmSearch");
    grdList.loadUrl(url,param);
}

function grdList_load(gridView,gridId){
     search();
}
function grdList_btnDetail_buttonClicked(gridView,row,col,json){
    showDetail(json);
}
/* 그리드 컬럼 더블 클릭 */
function onGridCellDblClick(gridView,itemIndex, column, json, value){
	showDetail(json);
}

function showDetail(json){
	var url = "<c:url value='${urlPrefix}/apiHist/detail${urlSuffix}'/>/"+ json.apiHstCd;
    Utilities.openModal(url,1500,550);
}
set1month();
// set1month();
</script>