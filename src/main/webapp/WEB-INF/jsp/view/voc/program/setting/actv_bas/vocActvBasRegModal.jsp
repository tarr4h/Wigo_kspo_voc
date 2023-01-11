<%--
  @author: tarr4h
  @date: 2023-01-03
  @time: PM 5:25
  @description
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld" %>

<style>
    .reg_table{
        width: 100%;
        border-collapse: collapse;
    }
    .reg_table th{
        border: 1px solid gray;
        background-color: #e8eeef;
    }
    .reg_table td{
        border: 1px solid gray;
        height: 50px;
        text-align: center;
    }
    .register_input {
        width: 210px;
        height: 26px;
        text-align: center;
    }

    .v_td_textarea{
        height: 189px;
        padding: 15px 5px;
    }

    .reg_btn{
        width: 100%;
        height: 36px;
        border: 1px solid gray;
        border-top: none;
    }
</style>


<div class="v_modal_header">
    <h3>ACTIVITY코드 등록모달</h3>
    <a id="close_btn" data-event="close">X</a>
</div>

<section>
    <form id="actvFrm">
        <table class="reg_table">
            <tbody>
            <tr>
                <th>기능유형</th>
                <td>
                    <select name="funcTpCd"></select>
                </td>
            </tr>
            <tr>
                <th>등록 ACTIVITY명</th>
                <td>
                    <input type="text" class="register_input" name="actvNm">
                </td>
            </tr>
            <tr>
                <th>설명</th>
                <td>
                    <textarea name="actvExpln" class="v_td_textarea" cols="30" rows="5"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
    <button id="register_btn" class="reg_btn">등록</button>
</section>

<script>
    window.onload = function(){
        getFuncTp();
    }

    /**
     * 모달 닫기
     */
    $('#close_btn').on('click', function(){
        Utilities.closeModal();
    });

    /**
     * 수행코드 등록
     */
    $('#register_btn').on('click', function(){
        let form = $('#actvFrm');
        let formMap = Utilities.formToMap(form);

        $.ajax({
            url : '<c:url value="${urlPrefix}/insert${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify(formMap),
            success : function(res){
                if(res > 0){
                    alert('등록되었습니다.');
                    Utilities.getOpener().location.reload();
                    Utilities.closeModal();
                }
            },
            error: console.log
        })
    });

    /**
     * 수행코드의 기능유형 조회 후 select에 세팅
     */
    function getFuncTp(){
        let topComnCd = 'VOC030';
        $.ajax({
            url : '<c:url value="${urlPrefix}/getFuncTp${urlSuffix}"/>',
            data : {
                topComnCd : topComnCd
            },
            success : function(res){
                $('select[name="funcTpCd"]').append(res);
            },
            error: console.log
        })
    }
</script>