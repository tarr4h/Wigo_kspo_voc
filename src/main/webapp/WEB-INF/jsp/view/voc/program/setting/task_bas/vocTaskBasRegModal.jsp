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
    <h3>TASK코드 등록모달</h3>
    <a id="close_btn" data-event="close">X</a>
</div>

<form id="regTaskFrm">
    <div class="form_wrapper">
        <div class="form_row">
            <div class="form_row_left">
                <span class="form_row_title">TASK명</span>
            </div>
            <div class="form_row_right">
                <input type="text" name="taskNm" class="form_row_input">
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
        <div class="form_row" id="autoApplyPrcdYn">
            <div class="form_row_left">
                <span class="form_row_title">절차자동적용 여부</span>
            </div>
            <div class="form_row_right">
                <div class="radio_wrapper">
                    <label>적용</label>
                    <input type="radio" name="autoApplyPrcdYn" class="form_row_radio" value="Y">
                </div>
                <div class="radio_wrapper">
                    <label>미적용</label>
                    <input type="radio" name="autoApplyPrcdYn" class="form_row_radio" value="N" checked>
                </div>
            </div>
        </div>
        <div class="form_row" id="autoApplyAllPrcdYn" style="display: none;">
            <div class="form_row_left">
                <span class="form_row_title">전체절차 자동적용 여부</span>
            </div>
            <div class="form_row_right">
                <div class="radio_wrapper">
                    <label>적용</label>
                    <input type="radio" name="autoApplyAllPrcdYn" class="form_row_radio" value="Y">
                </div>
                <div class="radio_wrapper">
                    <label>미적용</label>
                    <input type="radio" name="autoApplyAllPrcdYn" class="form_row_radio" value="N" checked>
                </div>
            </div>
        </div>
        <div class="form_row" id="autoApplyPrcd" style="display: none;">
            <div class="form_row_left">
                <span class="form_row_title">자동적용 대상절차</span>
            </div>
            <div class="form_row_right">
                <input type="text" name="autoApplyPrcdNm" class="form_row_input_short">
                <input type="text" name="autoApplyPrcdCd" class="form_row_dpn">
                <button type="button" class="form_row_button" data-event="prcdSearch">조회</button>
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
    ////////////////////// on event //////////////////////////
    /**
     * class = func_btn 클릭 이벤트 : 분기 처리
     */
    $('.form_row_button').on('click', function(){
        let type = $(this).data('event');
        switch(type){
            case 'orgSearch' : openComnModal('vocOrgSearchModal', 950, 650);break;
            case 'empSearch' : openComnModal('vocEmpSearchModal', 950, 650);break;
            case 'prcdSearch' : openPrcdBasSearchModal();break;
        }
    });

    /**
     * 자동적용여부 관련 라디오버튼 변경 시 event
     */
    $('.form_row_radio').on('change', function(){
        let radioNm = $(this).attr('name');
        switch(radioNm){
            case 'autoApplyPrcdYn' : toggleAutoApplyPrcdYn(); break;
            case 'autoApplyAllPrcdYn' : toggleAutoApplyAllPrcdYn(this); break;
        }
    });

    /**
     * 모달 닫기
     */
    $('#close_btn').on('click', function(){
        Utilities.closeModal();
    });

    /**
     * form 등록
     */
    $('#regForm').on('click', function(){
        regTaskCode();
    });

    ///////////////// 기능 ///////////////////
    /**
     * 자동적용여부 radio값 변경 시 처리
     *  - N인 경우 전체절차 자동적용여부, 자동적용 대상절차 영역 hide & 자동적용 대상절차 input value 초기화
     */
    function toggleAutoApplyPrcdYn(){
        let target = $('#autoApplyAllPrcdYn');
        target.toggle();

        let radio = target.find('input[type="radio"]:checked');
        if(radio.val() === 'N'){
            $('input[name="autoApplyAllPrcdYn"][value="Y"]').prop('checked', true);
            $('#autoApplyPrcd').find('input[type="text"]').val('');
            $('#autoApplyPrcd').hide();
        }
    }

    /**
     * 전체절차 자동적용여부 radio값 변경 시 처리
     *  - N인 경우 자동적용 대상절차 영역 show
     *  - Y인 경우 자동적용 대상절차 영역 hide & 해당영역 input value 초기화
     */
    function toggleAutoApplyAllPrcdYn(radio){
        let val = $(radio).val();
        let target = $('#autoApplyPrcd');

        if(val === 'N'){
            target.show();
        } else if(val === 'Y'){
            target.find('input[type="text"]').val('');
            target.hide();
        }
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

        // $.each(dutyValidate, (i, e) => {
        //     if($(e).val() === '' || $(e).val() == null){
        //         chk++;
        //     }
        // });

        for(let i = 0; i < dutyValidate.length; i++){
            let val = $(dutyValidate[i]).val();
            if(val === '' || val == null){
                chk++;
            }
        }

        if(chk === dutyValidate.length){
            alert('권한변경 불가인 경우, 담당부서/담당자/적용권한 중 최소 1개는 등록되어야 합니다.');
            return false;
        } else {
            return true;
        }

    }

    /**
     * task 코드 insert
     */
    function regTaskCode(){
        if(!validateDuty()){
            return false;
        }

        let form = $('#regTaskFrm');
        let disabled = form.find('input:disabled').removeAttr('disabled');

        let formMap = Utilities.formToMap(form);
        disabled.attr('disabled','disabled');

        $.ajax({
            url : '<c:url value="${urlPrefix}/insert${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify(formMap),
            success : function(res, status, jqXHR){
                if(jqXHR.status === 200 && res.result){
                    alert(res.msg);
                    Utilities.getOpener().location.reload();
                    Utilities.closeModal();
                } else if(!res.result){
                    alert(res.msg);
                    $('input[name="deadline"]').focus();
                }
            },
            error: console.log
        });
    }


    ////////////////// callback //////////////////
    /**
     * 부서 검색 모달 콜백
     * @param data
     */
    function orgSearchCallback(data){
        $('input[name="dutyOrgNm"]').val(data.orgNm);
        $('input[name="dutyOrgId"]').val(data.orgId);
    }

    /**
     * 사원 검색 모달 콜백
     * @param data
     */
    function empSearchCallback(data){
        $('input[name="dutyEmpNm"]').val(data.empNm);
        $('input[name="dutyEmpId"]').val(data.empId);
    }

    /**
     * 절차 검색 모달 콜백
     * @param data
     */
    function prcdSearchCallback(data){
        $('input[name="autoApplyPrcdNm"]').val(data.prcdNm);
        $('input[name="autoApplyPrcdCd"]').val(data.prcdCd);
    }


    /////////////////// 모달 관련 /////////////////////
    /**
     * 현 페이지 호출 requestMapping인 directory에서 모달 호출
     * @param pageNm
     * @param width
     * @param height
     * @param param
     */
    function openModal(pageNm, width, height){
        let url = '<c:url value='${urlPrefix}/openModal${urlSuffix}'/>/' + pageNm;
        Utilities.openModal(url, width, height);
    }

    /**
     * 공통 모달 호출
     * @param pageNm
     * @param width
     * @param height
     */
    function openComnModal(pageNm, width, height){
        let url = '<c:url value='${urlPrefix}/openComnModal${urlSuffix}'/>/' + pageNm;
        Utilities.openModal(url, width, height);
    }

    /**
     * 절차 검색 모달 호출
     */
    function openPrcdBasSearchModal(){
        let prcdCd = $('input[name="autoApplyPrcdCd"]').val();
        let url = '<c:url value='${urlPrefix}/openModal${urlSuffix}'/>/vocPrcdBasSearchModal' + "?prcdCd=" + prcdCd;
        Utilities.openModal(url, 900, 600);
    }

</script>