<%--
  Created by IntelliJ IDEA.
  User: taewoohan
  Date: 2022/10/20
  Time: 1:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<style>
    .guide{
        margin-left: 1em;
        margin-top: 0.5em;
    }
    .guideDot{
        display: inline-block;
        transform: translateY(-1px);
        width: 5px;
        height: 5px;
        background-color: green;
        border-radius: 50px;
    }

    .btn_area{
        width: 100%;
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
        width: 100%;
        min-height: 5vh;
    }
</style>

<div class="wrapper">
    <div class="header">
        <h3 class="title" id="grid_title">TASK코드 관리</h3>
        <div class="guide">
            <div class="guideDot"></div>
            <span>TASK코드 목록입니다.</span>
        </div>
    </div>
    <div class="btn_area">
        <div class="btn_wrapper">
            <button class="grid_btn func_btn" data-event="save">저장</button>
            <button class="grid_btn func_btn" data-event="add">추가</button>
            <button class="grid_btn func_btn" data-event="delete">삭제</button>
        </div>
    </div>
    <div class="grid_wrapper">
        <div id="divGrid1"
             data-get-url="<c:url value='${urlPrefix}/selectTaskCodeList${urlSuffix}'/>"
             data-grid-id="taskCdGrid"
             data-type="grid"
             data-tpl-url="<c:url value='/static/gridTemplate/voc/vocTaskCode.xml${urlSuffix}'/>"
             style="width:100%;height:735px;"
        >
        </div>
    </div>
</div>

<script>
    $(() => {
        setTimeout(() => {
            let param = {};
            taskCdGrid.loadUrl('', param);
        }, 300);
    })

    // event listener
    $('.func_btn').on('click', function(){
        let event = $(this).data('event');
        switch(event){
            case 'save' : saveRows(); break;
            case 'add'  : openModal('vocTaskCodeRegModal', 800, 600); break;
            case 'delete' : deleteRows(); break;
        }
    });

    function chngTaskDuty(targetInfo, chngMap){
        $.ajax({
            url : '<c:url value="${urlPrefix}/chngTaskDuty${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({
                targetInfo, chngMap
            }),
            success(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert('변경되었습니다.');
                    location.reload();
                } else {
                    alert(`오류가 발생했습니다.\n에러코드 : \${jqXHR.status}`);
                }
            },
            error: console.log
        })
    }

    function orgSearchCallback(data){
        let jsonStorage = window.localStorage.getItem('vocProcedureCodeSettingSelectedBtnVal');
        let parsingStorage = JSON.parse(jsonStorage);

        let param = {
            id : data.orgId,
            nm : data.orgNm
        }
        chngTaskDuty(parsingStorage, param);
    }

    function empSearchCallback(data){
        let jsonStorage = window.localStorage.getItem('vocTaskCodeSettingSelectedBtnVal');
        let parsingStorage = JSON.parse(jsonStorage);

        let param = {
            id : data.empId,
            nm : data.empNm
        }

        chngTaskDuty(parsingStorage, param);
    }

    function onGridButtonClicked(gridView, row, col, json) {
        let target;
        switch(col){
            case 'chngDept': openComnModal('vocOrgSearchModal', 950, 650); target = 'duty_dept'; break;
            case 'chngEmp' : openComnModal('vocEmpSearchModal', 950, 650); target = 'duty_emp'; break;
            case 'chngRole' : break;
        }
        let param = {
            taskSeq : json.taskSeq,
            col : target
        };
        window.localStorage.setItem('vocTaskCodeSettingSelectedBtnVal', JSON.stringify(param));
    }

    function deleteRows(){
        let rows = taskCdGrid.getCheckedJson();
        if(rows.length === 0){
            alert('선택된 코드가 없습니다.');
        }

        $.ajax({
            url : '<c:url value="${urlPrefix}/deleteRows${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({rows}),
            success(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert(`\${res}건이 삭제되었습니다.`);
                    location.reload();
                } else {
                    alert(`오류가 발생했습니다.\n에러코드 : \${jqXHR.status}`);
                }
            },
            error : console.log
        })
    }

    function saveRows(){
        let rows = taskCdGrid.getJsonRows();
        $.ajax({
            url : '<c:url value="${urlPrefix}/saveRows${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({rows}),
            success(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert('저장되었습니다.');
                    location.reload();
                } else {
                    alert(`오류가 발생했습니다.\n에러코드 : \${jqXHR.status}`);
                }
            },
            error: console.log
        });
    }

    function openComnModal(pageNm, width, height){
        let url = `<c:url value='${urlPrefix}/openComnModal${urlSuffix}'/>/\${pageNm}`;
        Utilities.openModal(url, width, height);
    }

    function openModal(pageNm, width, height){
        let url = `<c:url value='${urlPrefix}/openModal${urlSuffix}'/>/\${pageNm}`;
        Utilities.openModal(url, width, height);
    }
</script>