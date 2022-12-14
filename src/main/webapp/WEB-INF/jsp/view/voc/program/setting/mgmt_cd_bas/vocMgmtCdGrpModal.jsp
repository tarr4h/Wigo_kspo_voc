<%--
  Created by IntelliJ IDEA.
  User: tarr4h
  Date: 2022/10/19
  Time: 10:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<style>
    .register_wrapper{
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        align-content: center;
        justify-content: center;
        height: 70px;
    }

</style>


<div class="v_modal_header">
    <h3>분류코드 그룹 등록</h3>
    <a id="close_btn" class="func_btn" data-event="close">X</button>
</div>

<div class="register_wrapper">
    <div>등록 그룹명</div>
    <input type="text"id="mgmtCdNm">
    <div>적용 구분</div>
    <select id="comnCd">
    </select>
    <button id="register_btn" class="btn func_btn" data-event="add">등록</button>
</div>

<div class="v_btn_area">
    <div class="v_btn_wrapper">
        <button class="btn btn-blue func_btn" data-event="save">저장</button>
        <button class="btn btn-red func_btn" data-event="delete">삭제</button>
    </div>
</div>

<div class="v_grid_wrapper">
    <div id="divGrid1"
         data-get-url="<c:url value='${urlPrefix}/vocMgmtCdGrid${urlSuffix}'/>"
         data-grid-id="mgmtCdGrid"
         data-type="grid"
         data-tpl-url="<c:url value='/static/gridTemplate/voc/vocMgmtCd.xml${urlSuffix}'/>"
         style="width:100%;height:450px;"
    >
    </div>
</div>

<script>
    function onGridLoad(){
        loadGrid();
    }

    function onGridDataLoaded(){
        selectComnCdList();
    }

    // event listener
    $('.func_btn').on('click', function(){
        let type = $(this).data('event');
        switch(type){
            case 'add' : regCode(); break;
            case 'save' : saveRows(); break;
            case 'delete' : deleteRows(); break;
            case 'close' : Utilities.closeModal(); break;
        }
    });

    /**
     * 적용 구분 option에 들어갈 공통코드(VOC분류코드 사용구분 - VOC010) 조회
     */
    function selectComnCdList(){
        let param = {
            topComnCd : 'VOC010'
        };

        $.ajax({
            url: '<c:url value="${urlPrefix}/selectComnCdList${urlSuffix}"/>',
            data: param,
            success : function(res){
                $('#comnCd').append(res);
            },
            error: console.log
        });
    }

    function loadGrid(){
        let param = {
            recordCountPerPage : 10
        };

        mgmtCdGrid.loadUrl('', param);
    }

    function regCode(){
        let mgmtCdNm = $("#mgmtCdNm").val();
        let comnCd = $("#comnCd").val();
        let topComnCd = $("#comnCd").find('option:selected').data('top-comn-cd');
        let param = JSON.stringify({
            mgmtCdNm : mgmtCdNm,
            comnCd : comnCd,
            topComnCd : topComnCd
        });

        $.ajax({
            url : '<c:url value="${urlPrefix}/insert${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : param,
            success : function(res){
                alert(res.msg);
                if(res.result){
                    let opnr = Utilities.getOpener();
                    opnr.location.reload();

                    location.reload();
                }
            },
            error: console.log,
            complete: function(){

            }
        });
    }

    function deleteRows(){
        let rows = mgmtCdGrid.getCheckedJson();
        let param = JSON.stringify(rows);

        $.ajax({
            url : '<c:url value="${urlPrefix}/delete${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : param,
            success : function(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert(res + '건이 삭제되었습니다.');
                    let opnr = Utilities.getOpener();
                    opnr.location.reload();
                    location.reload();
                } else {
                    alert('오류가 발생했습니다.\n(에러코드 : ' + jqXHR.status + ')');
                }
            },
            error: console.log
        })
    }

    function saveRows(){
        let rows = mgmtCdGrid.getJsonRows();
        let param = JSON.stringify({rows: rows});

        $.ajax({
            url : '<c:url value="${urlPrefix}/update${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data: param,
            success : function(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert('저장되었습니다.');
                    let opnr = Utilities.getOpener();
                    opnr.location.reload();
                    location.reload();
                } else {
                    alert('오류가 발생했습니다.\n(에러코드 : ' + jqXHR.status + ')');
                }
            },
            error: console.log
        })
    }
</script>