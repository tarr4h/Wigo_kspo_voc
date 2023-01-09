<%--
  Created by IntelliJ IDEA.
  User: tarr4h
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

<div class="v_modal_header">
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
                <span class="form_row_title">담당 변경적용 가능여부</span>
            </div>
            <div class="form_row_right">
                <div class="radio_wrapper">
                    <label>가능</label>
                    <input type="radio" name="dutyChngYn" class="form_row_radio" value="Y">
                </div>
                <div class="radio_wrapper">
                    <label>불가</label>
                    <input type="radio" name="dutyChngYn" class="form_row_radio" value="N" checked>
                </div>
            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">담당부서</span>
            </div>
            <div class="form_row_right">
                <input type="text" name="dutyOrgNm" class="form_row_input_short duty_validate" disabled>
                <input type="text" name="dutyOrgId" class="form_row_dpn">
                <button type="button" class="form_row_button" data-event="orgSearch">조회</button>
            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">담당자</span>
            </div>
            <div class="form_row_right">
                <input type="text" name="dutyEmpNm" class="form_row_input_short duty_validate" disabled>
                <input type="text" name="dutyEmpId" class="form_row_dpn">
                <button type="button" class="form_row_button" data-event="empSearch">조회</button>
            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">적용권한</span>
            </div>
            <div class="form_row_right">
                <input type="text" name="dutyRoleNm" class="form_row_input_short duty_validate">
                <input type="text" name="dutyRoleCd" class="form_row_dpn">
                <button type="button" class="form_row_button">조회</button>
            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">처리기한</span>
            </div>
            <div class="form_row_right">
                <input type="number" name="deadlineDate" class="form_row_input_quater" value="0"><span class="quater_note_right">일</span>
                <input type="number" name="deadlineHour" class="form_row_input_quater" value="0"><span class="quater_note_right">시간</span>
                <input type="number" name="deadlineMinute" class="form_row_input_quater" value="0"><span class="quater_note_right">분</span>

            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">VOC 적용여부</span>
            </div>
            <div class="form_row_right">
                <div class="radio_wrapper">
                    <label>사용</label>
                    <input type="radio" name="vocApplyYn" class="form_row_radio" value="Y">
                </div>
                <div class="radio_wrapper">
                    <label>미사용</label>
                    <input type="radio" name="vocApplyYn" class="form_row_radio" value="N" checked>
                </div>
            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">상세VOC 적용여부</span>
            </div>
            <div class="form_row_right">
                <div class="radio_wrapper">
                    <label>사용</label>
                    <input type="radio" name="vocDtlApplyYn" class="form_row_radio" value="Y">
                </div>
                <div class="radio_wrapper">
                    <label>미사용</label>
                    <input type="radio" name="vocDtlApplyYn" class="form_row_radio" value="N" checked>
                </div>
            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">VOC 필수 적용여부</span>
            </div>
            <div class="form_row_right">
                <div class="radio_wrapper">
                    <label>예</label>
                    <input type="radio" name="vocEstlApplyYn" class="form_row_radio" value="Y">
                </div>
                <div class="radio_wrapper">
                    <label>아니오</label>
                    <input type="radio" name="vocEstlApplyYn" class="form_row_radio" value="N" checked>
                </div>
            </div>
        </div>
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">VOC상세 필수 적용여부</span>
            </div>
            <div class="form_row_right">
                <div class="radio_wrapper">
                    <label>예</label>
                    <input type="radio" name="vocDtlEstlApplyYn" class="form_row_radio" value="Y">
                </div>
                <div class="radio_wrapper">
                    <label>아니오</label>
                    <input type="radio" name="vocDtlEstlApplyYn" class="form_row_radio" value="N" checked>
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
                    <input type="radio" name="taskUseYn" class="form_row_radio" value="Y">
                </div>
                <div class="radio_wrapper">
                    <label>미적용</label>
                    <input type="radio" name="taskUseYn" class="form_row_radio" value="N" checked>
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

    /**
     * 권한변경 가능여부에 따라 담당부서/담당자/적용권한 등록여부 체크 및 결과값 반환
     * @returns {boolean}
     */
    function validateDuty(){
        let dutyChngYn = $('input[name="dutyChngYn"]:checked').val();
        if(dutyChngYn === 'Y'){
            return true;
        }

        let dutyValidate = $('.duty_validate');
        let chk = 0;

        $.each(dutyValidate, (i, e) => {
           if($(e).val() === '' || $(e).val() == null){
               chk++;
           }
        });

        if(chk === dutyValidate.length){
            alert('권한변경 불가인 경우, 담당부서/담당자/적용권한 중 최소 1개는 등록되어야 합니다.');
            return false;
        } else {
            return true;
        }

    }

    function regProcedureCode(){
        if(!validateDuty()){
            return false;
        }

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
        $('input[name="dutyOrgNm"]').val(data.orgNm);
        $('input[name="dutyOrgId"]').val(data.orgId);
    }

    function empSearchCallback(data){
        $('input[name="dutyEmpNm"]').val(data.empNm);
        $('input[name="dutyEmpId"]').val(data.empId);
    }

</script>