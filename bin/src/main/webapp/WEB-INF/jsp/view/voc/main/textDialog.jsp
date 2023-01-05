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
    <strong class="title">텍스트</strong>
    <button id="btnCancel" data-btn-type="closeModal" class="btnClose"></button>
    <!-- //top -->

    <!-- titleBox -->
    <div class="contentsWrap">
        <h4>텍스트</h4>
    <!-- //titleBox -->
    <!-- form start -->
        <form id="frmMain">
            <div class="boardWrite mr0">
                <table>
                    <colgroup>
                        <col width="10%">
                        <col width="90%">
                    </colgroup>
                    <tbody id="textBody">
                        <tr>
                           <th id="textTitle"></th>
                           <td><textarea id="textValue" style="width:100%;height:300px">${text }</textarea></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </form>
        <div class="btnPBWrap">
            <button id="btnSave" data-click="save" class="btnBasic _em">저장</button>
            <button id="btnCancel" data-btn-type="closeModal" class="btnBasic">닫기</button>
        </div>
    </div>
</div>
<script>

function save(){
    if(Utilities.getOpener().changeText)
        Utilities.getOpener().changeText($("#textValue").val());
    Utilities.closeModal();
}
$(document).ready(function(){
    var param = Utilities.getOpener().textDialog.text;
    var title = Utilities.getOpener().textDialog.title;
    var readonly = Utilities.getOpener().textDialog.readonly;
    if(!param)
        param  = "";
    $("#textValue").val(param);
    if(!title)
        title = "text";
    $("#textTitle").html(title);
    if(readonly){
        $("#textValue").prop("readonly" , true);
        $("#btnSave").hide();
    }

});
</script>