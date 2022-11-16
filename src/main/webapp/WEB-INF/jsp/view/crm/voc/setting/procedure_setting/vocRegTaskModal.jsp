<%--
  Created by IntelliJ IDEA.
  User: tarr4h
  Date: 2022-11-16
  Time: AM 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<style>
    .content_wrapper{
        padding: 10px;
    }
    #regBtn{
        width: 100%;
        height: 35px;
        font-size: 13px;
        font-weight: 500;
        border: 1px solid gray;
    }
</style>

<div class="v_modal_header">
    <h3>TASK 등록</h3>
    <button id="close_btn">X</button>
</div>

<div class="content_wrapper">
    <div id="divGrid"
         data-get-url="<c:url value='${urlPrefix}/selectTaskBasSearchGrid${urlSuffix}'/>"
         data-grid-id="taskBasGrid"
         data-type="grid"
         data-tpl-url="<c:url value='/static/gridTemplate/voc/vocTaskBasSearch.xml${urlSuffix}'/>"
         style="width:100%;height:420px;"
    >
    </div>
</div>

<button id="regBtn">등록하기</button>

<script>
    const mcPrcdSeq = '${param.mcPrcdSeq}';

    $("#close_btn").on('click', function(){
        Utilities.closeModal();
    });

    function onGridLoad(){
        let param = {
            mcPrcdSeq
        };
        window['taskBasGrid'].loadUrl('', param);
    }

    $("#regBtn").on('click', function(){
       insertTask();
    });

    function insertTask(){
        let taskList = window['taskBasGrid'].getCheckedJson();
        let param = JSON.stringify({
            taskList,
            mcPrcdSeq
        });
        $.ajax({
            url: '<c:url value="${urlPrefix}/insertTask${urlSuffix}"/>',
            method: 'POST',
            contentType: 'application/json',
            data: param,
            success(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert(res + '건이 등록되었습니다.');
                    let opnr = Utilities.getOpener();
                    opnr.window['taskGrid'].reload();

                    Utilities.closeModal();
                }
            },
            error: console.log
        })
    }
</script>
