<%--
  @author: tarr4h
  @date: 2023-01-03
  @time: PM 4:31
  @description
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<style>
    .guide{
        margin-left: 1em;
        margin-top: 0.5em;
    }
    .guideDot{
        display: inline-block;
        transform: translateY(-1px);
        width: 5px;
        height: 5px;
        background-color: green;
        border-radius: 50px;
    }

    .btn_area{
        width: 100%;
        min-height: 2vh;
    }
    .btn_wrapper{
        float: right;
        margin-bottom: 0.5em;
    }
    .grid_btn{
        width: 6em;
        height: 2em;
        background-color: #e3e3e3;
        border: 1px solid gray;
        margin-left: 0.5em;
    }

    .grid_wrapper{
        width: 100%;
        min-height: 5vh;
    }
</style>

<div class="wrapper">
    <div class="header">
        <h3 class="title" id="grid_title">수행목록 관리</h3>
        <div class="guide">
            <div class="guideDot"></div>
            <span>수행코드 목록입니다.</span>
        </div>
    </div>
    <div class="btn_area">
        <div class="btn_wrapper">
            <button class="grid_btn func_btn" data-event="save">저장</button>
            <button class="grid_btn func_btn" data-event="add">추가</button>
            <button class="grid_btn func_btn" data-event="delete">삭제</button>
        </div>
    </div>
    <div class="grid_wrapper">
        <div id="divGrid1"
             data-get-url="<c:url value='${urlPrefix}/selectActvBasList${urlSuffix}'/>"
             data-grid-id="actvBasGrid"
             data-type="grid"
             data-grid-callback="onGridLoad"
             data-tpl-url="<c:url value='/static/gridTemplate/voc/vocActvBas.xml${urlSuffix}'/>"
             style="width:100%;height:735px;"
        >
        </div>
    </div>
</div>


<script>
    function onGridLoad(){
        let param = {};
        actvBasGrid.loadUrl('', param);
    };

    ////////////////////// on event //////////////////////////
    /**
     * class = func_btn 클릭 이벤트 : 분기 처리
     */
    $('.func_btn').on('click', function(){
        let event = $(this).data('event');
        switch(event){
            case 'save' : saveRows(); break;
            case 'add'  : openModal('vocActvBasRegModal', 500, 400); break;
            case 'delete' : deleteRows(); break;
        }
    });

    function saveRows(){
        let rows = actvBasGrid.getJsonRows();

        $.ajax({
            url : '<c:url value="${urlPrefix}/update${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data: JSON.stringify({rows}),
            success(res, status, jqXHR){
                console.log(res);
                if(jqXHR.status === 200){
                    alert('저장되었습니다.');
                    window.location.reload();
                }
            },
            error: console.log
        });
    }

    /**
     * 현 페이지 호출 requestMapping인 directory에서 모달 호출
     * @param pageNm
     * @param width
     * @param height
     * @param param
     */
    function openModal(pageNm, width, height, param){
        let url = `<c:url value='${urlPrefix}/openModal${urlSuffix}'/>/\${pageNm}`;
        Utilities.openModal(url, width, height);
    }




</script>