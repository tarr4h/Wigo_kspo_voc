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


<div class="header">
    <h3>수행코드 등록모달</h3>
    <button id="close_btn" data-event="close">X</button>
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
                <th>등록 수행명</th>
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

    $(() => {
        getFuncTp();
    });

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
        let frmArr = $('#actvFrm').serializeArray();

        $.ajax({
            url : '<c:url value="${urlPrefix}/insert${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({frmArr}),
            success(res){
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
                topComnCd
            },
            success(res){
                $('select[name="funcTpCd"]').append(res);
            },
            error: console.log
        })
    }
</script>