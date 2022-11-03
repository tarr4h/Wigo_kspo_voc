<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<div class="title">
        <h1>API 실행 상세 팝업</h1>
        <a href="#" data-btn-type="closeModal" class="close">팝업 닫기</a>
    </div>
<!-- popup -->
<div class="cont">

	<div class="mBox1">
	<!-- form start -->
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
                             <th>API이력코드</th>
                            <td>
                                ${hist.apiHstCd }
                            </td>
                            <th>호출일시</th>
                            <td>
                             ${hist.apiCallDt } 
                            </td>
                            
                        </tr>
						<tr>
                             <th>결과코드</th>
                            <td>
                                ${hist.apiRsltCd }
                            </td>
                            <th>결과메시지</th>
                            <td>
                             ${hist.apiRsltMsgTxn } 
                            </td>
                            
                        </tr>
						<tr>
                            <th>호출URL</th>
                            <td> ${hist.apiCallUrl } </td>
                            <th>실행시간(ms)</th>
                            <td> ${hist.apiExecMsec } </td>
                        </tr>
                        <tr>
                            <th>채널</th>
                            <td> <code:commCodeName codeCd="${hist.regrId }" upCodeCd="S000"/>  </td>
                            <th>IP주소</th>
                            <td> ${hist.apiCallIpAddr } </td>
                        </tr>
                        <tr>
                            <th>파라메터</th>
                            <td><textarea class="it" style="height:280px" data-text-type="json"  readonly id="apiParamTxn" name="apiParamTxn" >${hist.apiParamTxn }</textarea></td>
                            <th>리턴값</th>
                            <td><textarea class="it" style="height:280px" data-text-type="json"  readonly id="apiRsltTxn" name="apiRsltTxn" >${hist.apiRsltTxn }</textarea></td>
                        </tr>
					</tbody>
				</table>
			</div>
		</form>
	</div>
</div>
<script>

</script>