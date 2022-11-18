<%--
  Created by IntelliJ IDEA.
  User: tarr4h
  Date: 2022-11-16
  Time: PM 3:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<style>
  .content_wrapper {
      padding: 10px;
  }
  .grid_wrapper{
      padding: 0 20px 20px;
  }
</style>

<div class="v_modal_header">
    <h3>ACTIVITY 등록</h3>
    <button id="close_btn">X</button>
</div>

<div class="content_wrapper">
    <form id="activityFrm">
        <div class="form_row_wrapper">
            <div class="form_row">
                <div class="form_row_left">
                    <span class="form_row_title">ACTIVITY명</span>
                </div>
                <div class="form_row_right">
                    <input type="text" name="actNm" class="form_row_input">
                </div>
            </div>
            <div class="form_row lg">
                <div class="form_row_left">
                    <span class="form_row_title">설명</span>
                </div>
                <div class="form_row_right">
                    <textarea class="form_row_textarea" name="explanation" cols="30" rows="10"></textarea>
                </div>
            </div>
            <div class="form_row">
                <div class="form_row_left">
                    <span class="form_row_title">선택 기능</span>
                </div>
                <div class="form_row_right">
                    <input type="text" name="funcNm" class="form_row_input" disabled>
                    <input type="text" name="funcCd" class="form_row_dpn">
                </div>
            </div>
            <div class="grid_wrapper">
                <div id="divGrid"
                     data-get-url="<c:url value='${urlPrefix}/selectActivityFuncBasGrid${urlSuffix}'/>"
                     data-grid-id="actFuncGrid"
                     data-type="grid"
                     data-tpl-url="<c:url value='/static/gridTemplate/voc/vocActivityFunc.xml${urlSuffix}'/>"
                     style="width:100%;height:300px;"
                >
                </div>
            </div>

            <div class="form_row">
                <button type="button" id="regForm" class="form_register_btn">등록</button>
            </div>
        </div>
    </form>


</div>


<script>
    const mcTaskSeq = '${param.mcTaskSeq}';

    $("#close_btn").on('click', function () {
        Utilities.closeModal();
    });

    $("#regForm").on('click', function(){
        regActivity();
    });

    function onGridLoad(){
        let param = {
            mcTaskSeq
        }
        window['actFuncGrid'].loadUrl('', param);
    }

    function onGridButtonClicked(gridView, row, col, json){
        $('input[name="funcNm"]').val(json.funcNm);
        $('input[name="funcCd"]').val(json.funcCd);
    }

    function regActivity(){
        let form = $('#activityFrm');
        let disabled = form.find(':input:disabled').removeAttr('disabled');
        let formArr = form.serializeArray();

        let validateResult = validateValue(formArr);
        if(!validateResult){
            return false;
        }

        $.ajax({
            url: '<c:url value="${urlPrefix}/insertActivity${urlSuffix}"/>',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                formArr,
                mcTaskSeq
            }),
            success(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert('등록되었습니다.');
                    let opnr = Utilities.getOpener();
                    opnr.window['activityGrid'].reload();
                    Utilities.closeModal();
                }
            },
            error: console.log
        })

    }

    function validateValue(formArr){
        let returnVal = true;
        $.each(formArr, (i, e) => {
            let name = e.name;
            let value = e.value;

            if(value === ''){
                let target = $('.form_row_right [name="' + name + '"]');
                let text = target.parent().parent().find('span').text();
                alert(text + '을 입력해주세요.');
                target.focus();
                // returnVal = false;
                return false;
            }
        });

        return returnVal;
    }

</script>