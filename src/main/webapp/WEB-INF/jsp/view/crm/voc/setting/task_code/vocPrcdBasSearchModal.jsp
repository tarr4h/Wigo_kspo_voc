<%--
  Created by IntelliJ IDEA.
  User: taewoohan
  Date: 2022/11/03
  Time: 5:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<style>
  .mBox1{
    padding: 10px;
    border: none;
  }

  .header{
    height: 15px;
    padding: 13px;
    background-color: #676767;
  }
  .header h3{
    display: inline;
    font-weight: 500;
    color: white;
  }
  .header button{
    float: right;
    border: none;
    background-color: inherit;
    color: white;
  }
</style>

<div class="header">
    <h3>자동적용 대상 절차 선택</h3>
    <button id="close_btn" data-event="close">X</button>
</div>

<div class="mBox1">
    <h3 class="mTitle1">선택된 절차</h3>
    <div class="grid_wrapper">
        <div id="divGrid2"
             data-get-url="<c:url value='${urlPrefix}/appliedPrcdGrid${urlSuffix}'/>"
             data-grid-id="appliedPrcdGrid"
             data-type="grid"
             data-grid-callback="onGridLoad"
             data-tpl-url="<c:url value='/static/gridTemplate/voc/vocProcedureBasSearch.xml${urlSuffix}'/>"
             style="width:100%;height:150px;"
        >
        </div>
    </div>

    <h3 class="mTitle1">선택 가능한 절차 목록</h3>
    <div class="grid_wrapper">
        <div id="divGrid1"
             data-get-url="<c:url value='${urlPrefix}/availablePrcdGrid${urlSuffix}'/>"
             data-grid-id="availablePrcdGrid"
             data-type="grid"
             data-grid-callback="onGridLoad"
             data-tpl-url="<c:url value='/static/gridTemplate/voc/vocProcedureBasSearch.xml${urlSuffix}'/>"
             style="width:100%;height:300px;"
        >
        </div>
    </div>
</div>



<script>
    function onGridLoad(){
        let param = {};
        param.prcdSeq = '${param.prcdSeq}';
        appliedPrcdGrid.loadUrl('', param);
        availablePrcdGrid.loadUrl('', param);
    }


    // evente listener
    $('#close_btn').on('click', function(){
        Utilities.closeModal();
    });

    function onGridButtonClicked(gridView, row, col, json) {
        let prcdNm = json.prcdNm;
        let prcdSeq = json.prcdSeq;

        let param = {
            prcdNm,
            prcdSeq
        };

        let opnr = Utilities.getOpener();
        opnr.prcdSearchCallback(param);
        Utilities.closeModal();
    }
</script>