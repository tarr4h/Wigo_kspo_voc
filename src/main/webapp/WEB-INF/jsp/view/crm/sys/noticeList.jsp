<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%> 
<c:set var="wOffset" value="772px" />
<c:set var="wOffset2" value="778px" />
<c:set var="hOffset" value="780px" />
<c:set var="hOffset2" value="730px" />
<div class="mSort2">
<form role="form" id="frmSearch">
				<ul>
					<li class="w32per">
						<div class="tit" >
							검색구분
						</div>
						<div class="tit" >
						<select id="keyvalue" name="keyvalue" class="select" >
							<option value="title">제목</option>
<!-- 							<option value="contents">내용</option> -->
<!-- 							<option value="titcont">제목+내용</option> -->
						</select></div>
						<div class="txt">
							<div class="mFlex2">
								<span class="flex">
                                    <input type="text" id="keyword" data-enter="search" name="keyword" class="it" placeholder="검색어" data-type="autocomplete" data-url="/util/commCode/getCode" data-param-name="codeCd" data-label-name="comnCdNm" data-value-name="comnCd" data-callback="fnSelectText" >
                                </span>
                                <span class="button">
                                    <a href="#;" class="mBtn1"  id="btnSearch" data-click="search">검색</a>
                                </span>
							</div>
						</div>
					</li>
				</ul>
				</form>
			</div>
	<div class="gLeft" style="width:${wOffset};top:81px">
        <div class="mBox1" style="height:${hOffset2}">
			<div class="gTitle1">
				<h3 class="mTitle1">공지목록</h3>
				<div class="gRt">
					 <code:btnGroup name="Notice" hideSave="true" hideDelete="true" insertName="등록"/>
<!-- 					 <a href="#;" data-click="removeNoticeDetail" id="btnRemoveNoticeDetail" class="mBtn1 m" style="cursor: pointer;">삭제</a> -->
<!-- 					  <a href="#;" data-click="newNoticeDetail" id="btnNewNoticeDetail" class="mBtn1 m lWhite" style="cursor: pointer;">추가</a> -->
<!-- 					  <a href="#;" data-click="saveNoticeDetail" id="btnSaveNoticeDetail" class="mBtn1 m lWhite" style="cursor: pointer;">저장</a> -->
				</div>
								
			</div>
			
			<div class='' >
				<div id="divGrid"  style="height:${rightBoxHeight4}" 
					data-get-url="<c:url value='${urlPrefix}/getList${urlSuffix}'/>"
      				data-grid-id="grdList" data-type="grid"
      				data-pagination="Y" 
      				data-tpl-url="<c:url value='/static/gridTemplate/system/crmNtcartBas.xml'/>">
    		</div>
			</div>
			
		</div>
	</div>
	<div class="gRight " style="margin-left:${wOffset2}">
		
		<div class="mBox1 p24 ">
			<div class="gTitle1">
				<h3 class="mTitle1">공지상세</h3>
				<div class="gRt">
<!-- 					 <a href="#;" data-click="removeNoticeDetail" id="btnRemoveNoticeDetail" class="mBtn1 m lWhite" style="cursor: pointer;">수정</a> -->
					 <code:btnGroup name="NoticeDetail" hideInsert="true" saveName="수정"/>
				</div>
			</div>
			
			<div class="mBoard2 " >
			<form name="frmDetail" id="frmDetail">

	   	<input type="hidden" id="brdId" name="brdId" />
	   	<input type="hidden" id=ntcartId name="ntcartId" />
	   	<input type="hidden" id=upNtcartId name="upNtcartId" /> 
	   	<input type="hidden" id="topNtcartId" name="topNtcartId" />
	   	
	   	<input type="hidden" id="answerCtnts" name="answerCtnts" />
	   	<input type="hidden" id="noteStatusCd" name="noteStatusCd" />
	   	<input type="hidden" id="postStatusCd" name="postStatusCd" />
	   	<input type="hidden" id="noteYn" name="noteYn" />
	   	<input type="hidden" id="noteStaDt" name="noteStaDt" />
	   	<input type="hidden" id="noteEndDt" name="noteEndDt" />
	   	<input type="hidden" id="fileCd" name="fileCd" />
	   	<input type="hidden" id="ntcartPwd" name="ntcartPwd" />
								<table>
									<caption>선택정보</caption>
									<colgroup>
										<col width="10%">
										<col width="15%">
										<col width="10%">
										<col width="15%">
										<col width="10%">
										<col width="15%">
										<col width="10%">
										<col width="15%">
									</colgroup>
									<tbody>
										<tr>
											<th scope="row" class="left">등록자</th>
											<td>
												<input type="text" name="regrId" id="regrId" readonly="readonly" class="it"/>
											</td>
											<th scope="row" class="left">등록일</th>
											<td>
												<input type="text" name="regDtNm" id="regDtNm" readonly="readonly" class="it"/>
											</td>
											<th scope="row" class="left">게시시작일</th>
											<td>
												<input type="text" name="postStaDt" id="postStaDt" readonly="readonly" class="it"/>
											</td>
											<th scope="row" class="left">게시종료일</th>
											<td>
												<input type="text" name="postEndDt" id="postEndDt" readonly="readonly" class="it"/>
											</td>
										</tr>
										<tr>
											<th scope="row" class="left">게시물 제목</th>
											<td colspan= "7">
												<input type="text" name="ntcartNm" id="ntcartNm" readonly="readonly" class="it"/>
											</td>
										</tr>
										<tr>
											<th scope="row" class="left">게시물 내용</th>
											<td colspan= "7" >
												<textarea style="height:300px" id="ntcartCtnts" name="ntcartCtnts" readonly class="it"></textarea>
											</td>
										</tr>
										<tr>
											<th scope="row" class="left" >첨부파일</th>
											<td colspan= "7">
												<div id="attFiles" data-type="attachFiles" data-mode="edit" class="left" style="min-height:200px"></div>
											</td>
										</tr>
									</tbody>
								</table>
							</form>
							</div>
		</div>	
	   	
 	</div>


<script>

function fnSelectText(item){
// 	alert(item);
}

$("[data-click=removeNoticeDetail]").hide();
$("[data-click=saveNoticeDetail]").hide();
function search(){
    var param = Utilities.formToMap("frmSearch");
    grdList.loadUrl(null,{});
}
function grdList_load(gridView,gridId){
 search();
}
function grdList_rowChanged(grdList, oldRow, newRow, rowData) {
	$("[data-click=removeNoticeDetail]").show();
	$("[data-click=saveNoticeDetail]").show();
	Utilities.mapToForm(rowData,"frmDetail");
	let fileCd = rowData.fileCd;
	const uploader = $("#attFiles")[0].ezFileUploader;
	uploader.reset(fileCd);
	
}
function onSaveNotice(){
	location.reload();
}
function newNotice()
{
	var url = "<c:url value='${urlPrefix}/add${urlSuffix}'/>";
	Utilities.openModal(url,1200,900);
}
function saveNoticeDetail()
{
	var url = "<c:url value='${urlPrefix}/mod${urlSuffix}'/>?ntcartId="+ $("#ntcartId").val();
	Utilities.openModal(url,1200,900);
}
function deleteNotice(id){
	if(!id)
		return;
	var url = "<c:url value='${urlPrefix}/remove${urlSuffix}'/>";
	var saveJson = {
			ntcartId : id
	}
	if(!confirm("게시물을 삭제하시겠습니까?"))
		return false;
	Utilities.blockUI();
	Utilities.getAjax(url,saveJson,true,function(data,jqXHR){
		Utilities.unblockUI();
        if(Utilities.processResult(data,jqXHR,"게시물 삭제에 실패했습니다."))
        {
        	
            alert("게시물 삭제 성공했습니다.");
            location.reload();
        }
    });
}
function removeNoticeDetail()
{
	deleteNotice($("#ntcartId").val());
}

</script>