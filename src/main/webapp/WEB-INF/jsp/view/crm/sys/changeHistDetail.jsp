<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<div class="title">
        <h1>변경 이력 상세</h1>
        <a href="#" data-btn-type="closeModal" class="close">팝업 닫기</a>
    </div>
<!-- popup -->
<div class="cont">
	<div class="gTitle1 line">
            <h3 class="mTitle1">변경 이력 정보</h3>
            <div class="gRt">
            </div>
        </div>
        
	<div class="mBox1">
		<form id="frmMain">
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
                            <th>변경자 정보</th>
                            <td>
                                ${hist.userNm}(${hist.loginId})
                            </td>
                            <th>변경일시</th>
                            <td>
                             ${hist.chngCallDt } 
                            </td>
                            
                        </tr>
						
						<tr>
						    <th>메뉴</th>
                            <td> ${hist.menuPath } </td>
                            <th>호출URL</th>
                            <td> ${hist.chngCallUrl } </td>
                            
                        </tr>
                        <tr>
                            <th>실행시간(ms)</th>
                            <td> ${hist.chngExecMsec } </td>
                            <th>IP주소</th>
                            <td> ${hist.chngCallIpAddr } </td>
                        </tr>
                        <tr>
                            <th>파라메터</th>
                            <td><textarea style="height:200px" class="it" data-text-type="json" readonly id="chngParamTxn" name="chngParamTxn" >${hist.chngParamTxn }</textarea></td>
                            <th>리턴값</th>
                            <td><textarea style="height:200px" class="it" data-text-type="json" readonly id="chngRsltTxn" name="chngRsltTxn" >${hist.chngRsltTxn }</textarea></td>
                        </tr>
                        <tr>
                            <th>호출스택</th>
                            <td colspan="3"><textarea style="height:260px" class="it" data-text-type="json" readonly id="chngCallTxn" name="chngCallTxn" >${hist.chngCallTxn }</textarea></td>
                        </tr>
					</tbody>
				</table>
			</div>
		</form>
		</div>
</div>
<script>
$(document).ready(function(){
	var txt = $("#chngCallTxn").val();
	txt = formatJson(txt); 
	$("#chngCallTxn").val(txt);
	
});

</script>