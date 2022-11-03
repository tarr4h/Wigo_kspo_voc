<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>
<c:set var="addMode" value="${addMode==true}" />
<div class="title">
        <h1>${addMode? "공지 등록" : "공지 수정" }</h1>
        <a href="#" data-btn-type="closeModal" class="close">팝업 닫기</a>
    </div>

<div class="cont">
		<div class="gTitle1">
            <h3 class="mTitle1">상세정보</h3>
            <div class="gRt">
            <code:btnGroup name="Notice" hideDelete="${addMode? true:false }" hideInsert="true" />
            </div>
        </div>
	<div class="mBox1 ">
		
		
		
		<form id="frmMain">
		<input type="hidden" id="stat" name="stat" value="${addMode ? 'c' : 'u'}"/>
		<input type="hidden" id="brdId" name="brdId" value="${vo.brdId }"/>
	   	<input type="hidden" id=ntcartId name="ntcartId" value="${vo.ntcartId }"/>
	   	<input type="hidden" id=upNtcartId name="upNtcartId" value="${vo.upNtcartId }" /> 
	   	<input type="hidden" id="topNtcartId" name="topNtcartId" value="${vo.topNtcartId }" />
	   	
	   	<input type="hidden" id="answerCtnts" name="answerCtnts" value="${vo.answerCtnts }"/>
	   	<input type="hidden" id="noteStatusCd" name="noteStatusCd" value="${vo.noteStatusCd }"/>
	   	<input type="hidden" id="postStatusCd" name="postStatusCd" value="${vo.postStatusCd }"/>
	   	<input type="hidden" id="noteYn" name="noteYn" value="${vo.noteYn }"/>
	   	<input type="hidden" id="noteStaDt" name="noteStaDt" value="${vo.noteStaDt }"/>
	   	<input type="hidden" id="noteEndDt" name="noteEndDt" value="${vo.noteEndDt }"/>
	   	<input type="hidden" id="fileCd" name="fileCd" value="${vo.fileCd }"/>
	   	<input type="hidden" id="ntcartPwd" name="ntcartPwd" value="${vo.ntcartPwd }"/>
	   	
			<div class="mBoard2">
				<table>
					<colgroup>
						<col width="15%">
						<col width="35%">
						<col width="15%">
                        <col width="35%">
					</colgroup>
					<tbody>
						<tr>
<!--                              <th>변경이력코드</th> -->
<!--                             <td> -->
<%--                                 ${hist.chngHstCd } --%>
<!--                             </td> -->
                        <th class="left"><span class="iMust">게시물 제목</span></th>
                            <td class="left" colspan="3">
                                <input type="text" name="ntcartNm" id="ntcartNm" value="${vo.ntcartNm }" class="it"/>
                            </td>
                        </tr>
						<tr>
						    <th class="left">게시기간</th>
                            <td class="left" colspan="3"><input class="it" type="text" value="" data-type="dateRangeStart" data-range-end="postEndDt" id="postStaDt" name="postStaDt" data-enter="search" data-button="Y" /> 
                            ~ <input  class="it" type="text" id="postEndDt" name="postEndDt" data-enter="search" data-button="Y" value="" />
                            </td>
                            
                        </tr>
                        <tr>
                            <th class="left"><span class="iMust">내용</span></th>
                            <td class="left" colspan="3"><textarea style="height:300px" id="ntcartCtnts" name="ntcartCtnts" class="it">${vo.ntcartCtnts }</textarea></td>
                        </tr>
                        <tr>
                            <th class="left">첨부파일</th>
                            <td class="left" colspan="3">
                            <div id="attFiles" class="left" style="min-height:200px" data-type="attachFiles" data-file-cd="${vo.fileCd }" data-file-odrg="1"  data-mode="edit"></div>
                            </td>
                        </tr>
                        
					</tbody>
				</table>
			</div>
		</form>
	</div>
	
</div>


<script>
	$("#postStaDt").val(moment("${vo.postStaDt }").format("YYYYMMDD"));
	$("#postEndDt").val(moment("${vo.postEndDt }").format("YYYYMMDD"));
	function saveNotice(){
		var saveJson = Utilities.formToMap("frmMain");
		if(!saveJson.ntcartNm){
			alert("게시물 제목을 입력하세요");
			$("#ntcartNm")[0].focus();
			return;
			
		}
		if(!saveJson.ntcartCtnts){
			alert("게시물 내용을 입력하세요");
			$("#ntcartCtnts")[0].focus();
			return;
			
		}
		var url = "<c:url value='${urlPrefix}/save${urlSuffix}'/>";
		Utilities.getAjax(url,saveJson,true,function(data,jqXHR){
            if(Utilities.processResult(data,jqXHR,"게시물 저장에 실패했습니다."))
            {
            	
                alert("게시물 저장에에  성공했습니다.");
                Utilities.getOpener().onSaveNotice();
                Utilities.closeModal();
            }
        });
	}
	
	function removeNotice()
	{
		Utilities.getOpener().deleteNotice($("#ntcartId").val());
	}
</script>
