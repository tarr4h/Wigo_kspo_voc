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

<div class="wrapper">
    <div class="v_header">
        <h3 class="title" id="grid_title">수행목록 관리</h3>
        <div class="v_guide">
            <div class="v_guideDot"></div>
            <span>수행코드 목록입니다.</span>
        </div>
    </div>
    <div class="v_btn_area">
        <div class="v_btn_wrapper">
            <button class="v_grid_btn func_btn" data-event="save">저장</button>
            <button class="v_grid_btn func_btn" data-event="add">추가</button>
            <button class="v_grid_btn func_btn" data-event="delete">삭제</button>
        </div>
    </div>
    <div class="v_grid_wrapper">
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

    function deleteRows(){
        let rows = actvBasGrid.getCheckedJson();

        $.ajax({
            url : '<c:url value="${urlPrefix}/delete${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({rows}),
            success(res, status, jqXHR){
                console.log(res);
                alert('삭제되었습니다.');
                window.location.reload();
            },
            error: console.log
        })
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