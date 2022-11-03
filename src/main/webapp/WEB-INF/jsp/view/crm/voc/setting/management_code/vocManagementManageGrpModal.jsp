<%--
  Created by IntelliJ IDEA.
  User: taewoohan
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

    .register_wrapper{
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        align-content: center;
        justify-content: center;
        height: 70px;
    }
    .register_title{
        font-size: 15px;
        margin-right: 10px;
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        align-content: center;
        justify-content: center;
    }
    .register_input{
        height: 26px;
        width: 210px;
        border: 1px solid gray;
        text-align: center;
    }
    .register_btn{
        border: 1px solid gray;
        width: 50px;
        font-size: 12px;
        margin-left: 10px;
    }

    .btn_area{
        width: 98%;
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
        width: 97%;
        margin: 25px auto auto;
    }

</style>



<div class="header">
    <h3>분류코드 그룹 등록</h3>
    <button id="close_btn" class="func_btn" data-event="close">X</button>
</div>

<div class="register_wrapper">
    <div class="register_title">등록 그룹명</div>
    <input type="text" class="register_input" id="codeNm">
    <button id="register_btn" class="register_btn func_btn" data-event="add">등록</button>
</div>

<div class="btn_area">
    <div class="btn_wrapper">
        <button class="grid_btn func_btn" data-event="save">저장</button>
        <button class="grid_btn func_btn" data-event="delete">삭제</button>
    </div>
</div>

<div class="grid_wrapper">
    <div id="divGrid1"
         data-get-url="<c:url value='${urlPrefix}/vocManagementCodeGrid${urlSuffix}'/>"
         data-grid-id="managementCdGrid"
         data-type="grid"
         data-tpl-url="<c:url value='/static/gridTemplate/voc/vocManagementCode.xml${urlSuffix}'/>"
         style="width:100%;height:450px;"
    >
    </div>
</div>

<script>
    $(document).ready(function(){
        setTimeout(() => {
            loadGrid();
        }, 300)
    });

    // event listener
    $('.func_btn').on('click', function(){
        let type = $(this).data('event');
        switch(type){
            case 'add' : regCode(); break;
            case 'save' : ; saveRows(); break;
            case 'delete' : deleteRows(); break;
            case 'close' : Utilities.closeModal(); break;
        }
    });

    function loadGrid(){
        let param = {
            recordCountPerPage : 10
        };

        managementCdGrid.loadUrl('', param);
    }

    function regCode(){
        let codeNm = $("#codeNm").val();

        $.ajax({
            url : '<c:url value="${urlPrefix}/insertVocManagementCode${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({
                codeNm
            }),
            success(res){
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
        let rows = managementCdGrid.getCheckedJson();

        $.ajax({
            url : '<c:url value="${urlPrefix}/deleteManagementCodeList${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify(
                rows
            ),
            success(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert(`\${res}건이 삭제되었습니다.`);
                    location.reload();
                } else {
                    alert(`오류가 발생했습니다.\n(에러코드 : \${jqXHR.status}`);
                }
            },
            error: console.log
        })
    }

    function saveRows(){
        let rows = managementCdGrid.getJsonRows();

        $.ajax({
            url : '<c:url value="${urlPrefix}/updateManagementCodeList${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data: JSON.stringify({
                rows
            }),
            success(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert('저장되었습니다.');
                    location.reload();
                } else {
                    alert(`오류가 발생했습니다.\n(에러코드 : \${jqXHR.status})`);
                }
            },
            error: console.log
        })
    }
</script>