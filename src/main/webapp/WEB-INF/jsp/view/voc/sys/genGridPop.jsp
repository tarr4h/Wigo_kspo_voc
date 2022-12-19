<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<div class="title">
	<h1>DB</h1>
    <a href="#" data-btn-type="closeModal" class="close">팝업 닫기</a>
</div>
<div class="cont">
	<div class="gTitle1 line">
            <h3 class="mTitle1">저장 Text</h3>
            <div class="gRt">
             	<a href="#" class="mBtn1 primary" id="btnSave" data-click="save">저장</a>
            </div>
        </div>
	<div class="mBox1">
		<div class="mBoard2">
			<table>
					<colgroup>
						<col width="100%">
					</colgroup>
					<tbody>
						<tr>
							<td><textarea id="printXml" class="it" style="height:550px">${text }</textarea></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
</div>

<script>
var _SAVE_FILE_NAME = Utilities.getOpener()._SAVE_FILE_NAME;
var _TEXT  = Utilities.getOpener().genGridText;
$("#printXml").val(_TEXT);
function save(){
    
	Utilities.downloadText($("#printXml").val(), _SAVE_FILE_NAME);
}
$(document).ready(function(){
    

});
</script>