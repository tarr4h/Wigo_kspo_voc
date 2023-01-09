<%--
  Created by IntelliJ IDEA.
  User: tarr4h
  Date: 2022/10/20
  Time: 4:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<style>
    #reg_btn{
        width: 100%;
        height: 53px;
        border: 1px solid gray;
    }
</style>

<div class="v_modal_header">
    <h3>처리기한 수정</h3>
    <button id="close_btn">X</button>
</div>

<div class="content_wrapper">
    <div class="form_wrapper">
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
    </div>
</div>
<button id="reg_btn">등록</button>


<script>
    // opener에 deadlineModifyCallback(data) 만들어두기.
    //  - data : {deadlineDate, deadlineHour, deadlineMinute)}

    $("#close_btn").on('click', function(){
        Utilities.closeModal();
    });

    $("#reg_btn").on('click', function(){
        sendDeadline();
    });

    function sendDeadline(){
        // if(!validate()){
        //     alert('처리기한을 설정해주세요.');
        //     return false;
        // }

        let param = {
            deadlineDate : $('input[name="deadlineDate"]').val(),
            deadlineHour : $('input[name="deadlineHour"]').val(),
            deadlineMinute : $('input[name="deadlineMinute"]').val()
        }

        let opnr = Utilities.getOpener();
        opnr.deadlineModifyCallback(param);
        Utilities.closeModal();
    }

    function validate(){
        if($('input[name="deadlineDate"]').val() === '0' && $('input[name="deadlineHour"]').val() === '0' && $('input[name="deadlineMinute"]').val() === '0'){
            return false;
        } else {
            return true;
        }
    }



</script>