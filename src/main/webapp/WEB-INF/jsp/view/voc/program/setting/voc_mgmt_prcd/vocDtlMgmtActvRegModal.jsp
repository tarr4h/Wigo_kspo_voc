<%--
  @author: tarr4h
  @date: 2023-01-09
  @time: PM 1:22
  @description
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld" %>

<div class="v_modal_header">
    <h3>상세VOC 수행 등록</h3>
    <button id="close_btn" data-event="close">X</button>
</div>

<div class="grid_wrapper">
    <div id="divGrid1"
         data-get-url="<c:url value='${urlPrefix}/selectActvBasListGrid${urlSuffix}'/>"
         data-grid-id="actvBasGrid"
         data-type="grid"
         data-grid-callback="onGridLoad"
         data-tpl-url="<c:url value='/static/gridTemplate/voc/vocActvBasListSearch.xml${urlSuffix}'/>"
         style="width:100%;height:300px;"
    >
    </div>
</div>

<button id="regBtn">등록하기</button>


<script>
    function onGridLoad(){
        let param = {
            recordCountPerPage : 10,
            useYn : 'Y',
            mgmtTaskCd : '${key}'
        };
        window['actvBasGrid'].loadUrl('', param);
    }

    $('#regBtn').on('click', function(){
        let opnr = Utilities.getOpener();
        let rows = window['actvBasGrid'].getCheckedJson();

        opnr.insertMgmtActv(rows);
        Utilities.closeModal();
    });

    /**
     * 모달 닫기
     */
    $('#close_btn').on('click', function(){
        Utilities.closeModal();
    });
</script>