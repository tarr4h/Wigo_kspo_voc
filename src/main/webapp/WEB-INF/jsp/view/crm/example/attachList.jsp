<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%> 

<div class="mBox1">
<div class="mBoard2">
			<form role="form" id="frmSearch">
								<table>
									<caption>기본정보</caption>
									<colgroup>
										<col width="10%" />
										<col width="10%" />
										<col width="10%"  />
										<col width="10%" />
										<col width="10%"  />
										<col width="10%" />
										<col width="10%" />
										<col width="10%" />
										<col width="10%" />
										<col width="10%" />
									</colgroup>
									<tbody>
										<tr>
											<th scope="row" class="left">업로드일</th>
											<td>
												<input class="it" type="text" data-type="dateRangeStart" data-range-end="endDt" id="strtDt" name="strtDt" data-enter="search" data-button="Y" value="" />
											</td>
											<td>
												<input  class="it" type="text" id="endDt" name="endDt" data-enter="search" data-button="Y" value=""/>
											</td>
											<td scope="row" class="left" colspan="2">
												<button class="mBtn1 m lWhite" id="setToday" data-click="setToday" style="margin: 1px 0px;">오늘</button>
					                            <button class="mBtn1 m lWhite" id="set1Week" data-click="set1Week" style="margin: 1px 0px;" data-default-button="Y">1주일</button>
					                            <button class="mBtn1 m lWhite" id="set1month" data-click="set1month" style="margin: 1px 0px;">1개월</button>
					                            <button class="mBtn1 m lWhite" id="set3month" data-click="set3month" style="margin: 1px 0px;">3개월</button>
					                            <button class="mBtn1 m lWhite" id="set6month" data-click="set6month" style="margin: 1px 0px;">6개월</button>
					                            <button class="mBtn1 m lWhite" id="setAllmonth" data-click="setAllmonth" style="margin: 1px 0px;">전체</button>
											</td>
											<th>
												검색어
											</th>
											<td scope="row" class="left">
												<select class="select" id="searchItemSelect" name="searchItemSelect">
						                            <option value="fileName">파일명</option>
						                            <option value="extention">확장자</option>
						                            <option value="catetory">category</option>
						                        </select>
											</td>
											
											<td scope="row" class="left">
												<input type="text" id="searchWord" data-enter="search" name="searchWord" class="medium" placeholder="검색어">
											</td>
																				
											<th scope="row" class="left">
											<button class="mBtn1" data-click="search" >검색</button>
											</th>
											
										</tr>
										
									</tbody>
								</table>
								</form>
							</div>
		
			</div>
			
			
<div class="mBox1 " >

<div class="gTitle1">
			<h3 class="mTitle1">첨부파일</h3>
				<div class="gRt">
					<code:btnGroup name="FileInfo" gridId="grdList" dispName="예제" forceInsert="true" hideInsert="false" hideExcel="false" />
				</div>
		</div>
 	<div id="divGrid"  style="height:520px" 
				data-post="Y" 
				data-grid-id="grdList" 
				data-pagenation="Y" 
				data-get-url="<c:url value='/attach/getList'/>" 
				data-type="grid" 
				data-grid-callback="onGridLoad" 
				data-tpl-url="<c:url value='/static/gridTemplate/example/attachList.xml'/>"
				>
			</div>
		
</div>


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
function newFileInfo(){
    Utilities.openFilePopup();
//     var url = "<c:url value='file/fileInfo${urlSuffix}'/>";
   /*  Utilities.windowOpen(url,"fileInfo",700,550); */
//     Utilities.openModal(url,900,550);
}
function grdList_btnFileinfo_buttonClicked(gridView,row,col,json){
	Utilities.openFilePopup(json.fileCd,null,'Y');
//     var url = "<c:url value='file/fileInfo${urlSuffix}'/>?fileCd=" + json.fileCd;
    /* Utilities.windowOpen(url,"fileInfo",700,550); */
//     Utilities.openModal(url,900,550);
}
setToday();
</script>