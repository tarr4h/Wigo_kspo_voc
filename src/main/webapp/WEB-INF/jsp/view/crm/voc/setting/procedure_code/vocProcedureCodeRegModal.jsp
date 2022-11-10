<%--
  Created by IntelliJ IDEA.
  User: taewoohan
  Date: 2022/10/20
  Time: 3:05 PM
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

    .form_wrapper{
        overflow: auto;
        padding-bottom: 20px;
    }

    .form_row{
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        align-content: center;
        justify-content: center;
        height: 50px;
        text-align: center;
        width: 95%;
        margin: 17px auto auto;
        border-radius: 15px;
        border: 1px solid gray;
        box-shadow: 0 1px 1px rgba(0,0,0,0.16), 0 1px 2px rgba(0,0,0,0.23);
    }
    .form_row_left{
        width: 20%;
        height: 100%;
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        align-content: center;
        justify-content: center;
    }
    .form_row_right{
        width: 80%;
        height: 100%;
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        align-content: center;
        justify-content: center;
    }
    .form_row_title{
        font-size: 13px;
        font-weight: 500;
    }
    .form_row_input{
        width: 80%;
        height: 50%;
        text-align: center;
        border: 1px solid #201f1f73;
    }
    .form_row_input_short{
        width: 70%;
        height: 50%;
        text-align: center;
        border: 1px solid #201f1f73;
    }
    .form_row_button{
        border: 1px solid #201f1f73;
        width: 9%;
        margin-left: 1%;
        border-radius: 2px;
        font-size: 12px;
        color: #464646b8;
    }
    .form_row_button:hover{
        cursor: pointer;
    }
    .form_row_dpn{
        display: none;
    }

    .radio_wrapper{
        width: 50px;
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        align-content: center;
        justify-content: space-evenly;
    }

    .form_register_btn{
        width: 100%;
        height: 100%;
        border-radius: 15px;
        border: none;
    }
    .form_register_btn:hover{
        cursor: pointer;
    }
</style>

<div class="header">
    <h3>절차코드 등록모달</h3>
    <button id="close_btn" data-event="close">X</button>
</div>

<form id="regPrcdFrm">
    <div class="form_wrapper">
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">절차구분</span>
            </div>
            <div class="form_row_right">
                <select name="comnCd" class="form_row_input">
                </select>
            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">절차명</span>
            </div>
            <div class="form_row_right">
                <input type="text" name="prcdNm" class="form_row_input">
            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">처리기한</span>
            </div>
            <div class="form_row_right">
                <input type="number" name="deadline" class="form_row_input" value="1">
            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">담당부서</span>
            </div>
            <div class="form_row_right">
                <input type="text" name="dutyDeptNm" class="form_row_input_short" disabled>
                <input type="text" name="dutyDept" class="form_row_dpn">
                <button type="button" class="form_row_button" data-event="orgSearch">조회</button>
            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">담당자</span>
            </div>
            <div class="form_row_right">
                <input type="text" name="dutyEmpNm" class="form_row_input_short" disabled>
                <input type="text" name="dutyEmp" class="form_row_dpn">
                <button type="button" class="form_row_button" data-event="empSearch">조회</button>
            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">적용권한</span>
            </div>
            <div class="form_row_right">
                <input type="text" name="dutyRoleNm" class="form_row_input_short">
                <input type="text" name="dutyRole" class="form_row_dpn">
                <button type="button" class="form_row_button">조회</button>
            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">변경적용 가능여부</span>
            </div>
            <div class="form_row_right">
                <div class="radio_wrapper">
                    <label>가능</label>
                    <input type="radio" name="chngYn" class="form_row_radio" value="Y">
                </div>
                <div class="radio_wrapper">
                    <label>불가</label>
                    <input type="radio" name="chngYn" class="form_row_radio" value="N" checked>
                </div>
            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">VOC등록 사용여부</span>
            </div>
            <div class="form_row_right">
                <div class="radio_wrapper">
                    <label>사용</label>
                    <input type="radio" name="regUseYn" class="form_row_radio" value="Y">
                </div>
                <div class="radio_wrapper">
                    <label>미사용</label>
                    <input type="radio" name="regUseYn" class="form_row_radio" value="N" checked>
                </div>
            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">VOC접수 사용여부</span>
            </div>
            <div class="form_row_right">
                <div class="radio_wrapper">
                    <label>사용</label>
                    <input type="radio" name="recUseYn" class="form_row_radio" value="Y">
                </div>
                <div class="radio_wrapper">
                    <label>미사용</label>
                    <input type="radio" name="recUseYn" class="form_row_radio" value="N" checked>
                </div>
            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">VOC등록 필수여부</span>
            </div>
            <div class="form_row_right">
                <div class="radio_wrapper">
                    <label>예</label>
                    <input type="radio" name="regCompulsoryYn" class="form_row_radio" value="Y">
                </div>
                <div class="radio_wrapper">
                    <label>아니오</label>
                    <input type="radio" name="regCompulsoryYn" class="form_row_radio" value="N" checked>
                </div>
            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">VOC접수 필수여부</span>
            </div>
            <div class="form_row_right">
                <div class="radio_wrapper">
                    <label>예</label>
                    <input type="radio" name="recCompulsoryYn" class="form_row_radio" value="Y">
                </div>
                <div class="radio_wrapper">
                    <label>아니오</label>
                    <input type="radio" name="recCompulsoryYn" class="form_row_radio" value="N" checked>
                </div>
            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">TASK 적용여부</span>
            </div>
            <div class="form_row_right">
                <div class="radio_wrapper">
                    <label>적용</label>
                    <input type="radio" name="taskYn" class="form_row_radio" value="Y">
                </div>
                <div class="radio_wrapper">
                    <label>미적용</label>
                    <input type="radio" name="taskYn" class="form_row_radio" value="N" checked>
                </div>
            </div>
        </div>
        <div class="form_row">
            <button type="button" id="regForm" class="form_register_btn">등록</button>
        </div>
<%--        <div class="form_row">--%>
<%--            <div class="form_row_left">--%>
<%--                <span class="form_row_title"></span>--%>
<%--            </div>--%>
<%--            <div class="form_row_right"></div>--%>
<%--        </div>--%>
    </div>
</form>

<script>
    $(() => {
        selectComnCdList();
    });

    // evente listener
    $('#close_btn').on('click', function(){
        Utilities.closeModal();
    });
    $('#regForm').on('click', function(){
        regProcedureCode();
    })
    $('.form_row_button').on('click', function(){
        let type = $(this).data('event');
        switch(type){
            case 'orgSearch' : openComnModal('vocOrgSearchModal', 950, 650);break;
            case 'empSearch' : openComnModal('vocEmpSearchModal', 950, 650);break;
        }
    });

    // func
    /**
     * 절차 구분 option에 들어갈 공통코드(VOC절차코드 - VOC020) 조회 후 append
     */
    function selectComnCdList(){
        let param = {
            topComnCd : 'VOC020'
        };

        $.ajax({
            url: '<c:url value="${urlPrefix}/selectComnCdList${urlSuffix}"/>',
            data: param,
            success(res){
                $('select[name="comnCd"]').append(res);
            },
            error: console.log
        });
    }

    function regProcedureCode(){
        let form = $('#regPrcdFrm');
        let disabled = form.find(':input:disabled').removeAttr('disabled');
        let formArr = form.serializeArray();

        let topComnCd = {
            name : 'topComnCd',
            value : $('select[name="comnCd"]').find('option:selected').data('top-comn-cd')
        };
        formArr.push(topComnCd);

        disabled.attr('disabled','disabled');

        $.ajax({
            url : '<c:url value="${urlPrefix}/insert${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({
                formArr
            }),
            success(res, status, jqXHR){
                if(jqXHR.status === 200 && res === 1){
                    alert('등록되었습니다.');
                    Utilities.getOpener().location.reload();
                    Utilities.closeModal();
                }
            },
            error: console.log
        })
    }


    function openComnModal(pageNm, width, height){
        let url = `<c:url value='${urlPrefix}/openComnModal${urlSuffix}'/>/\${pageNm}`;
        Utilities.openModal(url, width, height);
    }

    function orgSearchCallback(data){
        console.log('org data : ', data);
        $('input[name="dutyDeptNm"').val(data.orgNm);
        $('input[name="dutyDept"').val(data.orgId);
    }

    function empSearchCallback(data){
        $('input[name="dutyEmpNm"').val(data.empNm);
        $('input[name="dutyEmp"').val(data.empId);
    }

</script>