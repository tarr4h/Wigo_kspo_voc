<%--
  Created by IntelliJ IDEA.
  User: tarr4h
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

<div class="wrapper">
    <div class="v_header">
        <h3 class="title" id="grid_title">절차코드 관리</h3>
        <div class="v_guide">
            <div class="v_guideDot"></div>
            <span>절차코드 목록입니다.</span>
        </div>
    </div>
    <div class="v_btn_area">
        <div class="v_btn_wrapper">
            <button class="btn func_btn" data-event="save">저장</button>
            <button class="btn btn-blue func_btn" data-event="add">추가</button>
            <button class="btn btn-red func_btn" data-event="delete">삭제</button>
        </div>
    </div>
    <div class="v_grid_wrapper">
        <div id="divGrid1"
             data-get-url="<c:url value='${urlPrefix}/selectPrcdCdGrid${urlSuffix}'/>"
             data-grid-id="procedureCdGrid"
             data-type="grid"
             data-tpl-url="<c:url value='/static/gridTemplate/voc/vocPrcdBas.xml${urlSuffix}'/>"
             style="width:100%;height:735px;"
        >
        </div>
    </div>
</div>

<script>
    function onGridLoad(){
        let param = {};
        window['procedureCdGrid'].loadUrl('', param);
    }

    // event listener
    $('.func_btn').on('click', function(){
        let event = $(this).data('event');
        switch(event){
            case 'save' : saveRows(); break;
            case 'add'  : openModal('vocPrcdCdRegModal', 800, 600); break;
            case 'delete' : deleteRows(); break;
        }
    });

    function chngPrcdDuty(targetInfo, chngMap){
        $.ajax({
            url : '<c:url value="${urlPrefix}/chngPrcdDuty${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({
                targetInfo : targetInfo,
                chngMap : chngMap
            }),
            success : function(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert('변경되었습니다.');
                    location.reload();
                } else {
                    alert('오류가 발생했습니다.\n에러코드 : ' + jqXHR.status + ')');
                }
            },
            error: console.log
        })
    }

    function updateDeadline(param){
        $.ajax({
            url: '<c:url value="${urlPrefix}/updateDeadline${urlSuffix}"/>',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(param),
            success : function(res){
                if(res.result){
                    alert(res.msg);
                    window.location.reload();
                }
            },
            error: console.log
        })

    }

    /**
     * 처리기한 callback
     *  - data : {deadlineDate, deadlineHour, deadlineMinute)}
     * @param data
     */
    function deadlineModifyCallback(data){
        let jsonStorage = window.localStorage.getItem('vocProcedureCodeSettingSelectedBtnVal');
        let parsingJson = JSON.parse(jsonStorage);

        let param = data;
        param.prcdCd = parsingJson.prcdCd;
        param.deadline = parsingJson.deadline_convert;
        updateDeadline(param);
    }

    /**
     * 부서조회 callback
     * @param data
     */
    function orgSearchCallback(data){
        let jsonStorage = window.localStorage.getItem('vocProcedureCodeSettingSelectedBtnVal');
        let parsingStorage = JSON.parse(jsonStorage);

        let param = {
            id : data.orgId,
            nm : data.orgNm
        }
        chngPrcdDuty(parsingStorage, param);
    }

    /**
     * 직원조회 callback
     * @param data
     */
    function empSearchCallback(data){
        let jsonStorage = window.localStorage.getItem('vocProcedureCodeSettingSelectedBtnVal');
        let parsingStorage = JSON.parse(jsonStorage);

        let param = {
            id : data.empId,
            nm : data.empNm
        }

        chngPrcdDuty(parsingStorage, param);
    }

    function onGridCellDblClick(gridView,rowIndex,columnName,colIndex,fieldIndex){
        let target;
        switch(columnName){
            case 'dutyOrgNm': openComnModal('vocOrgSearchModal', 950, 650); target = 'duty_org'; break;
            case 'dutyEmpNm' : openComnModal('vocEmpSearchModal', 950, 650); target = 'duty_emp'; break;
            case 'dutyRole' : break;
            case 'deadlineConvert' : openComnModal('vocDeadlineModifyModal', 600, 200); target = 'deadline_convert'; break;
        }

        let row = gridView.getJsonRow(rowIndex);
        let param = {
            prcdCd : row.prcdCd,
            col : target
        };
        window.localStorage.setItem('vocProcedureCodeSettingSelectedBtnVal', JSON.stringify(param));
    }

    function deleteRows(){
        let rows = procedureCdGrid.getCheckedJson();
        if(rows.length === 0){
            alert('선택된 코드가 없습니다.');
        }

        $.ajax({
            url : '<c:url value="${urlPrefix}/delete${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({rows : rows}),
            success: function(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert(res + ' 건이 삭제되었습니다.');
                    location.reload();
                } else {
                    alert('오류가 발생했습니다.\n에러코드 : ' + jqXHR.status + ')');
                }
            },
            error : console.log
        })
    }

    function saveRows(){
        let rows = procedureCdGrid.getJsonRows();

        $.ajax({
            url : '<c:url value="${urlPrefix}/update${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({rows : rows}),
            success : function(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert('저장되었습니다.');
                    location.reload();
                } else {
                    alert('오류가 발생했습니다.\n에러코드 : ' + jqXHR.status + ')');
                }
            },
            error: console.log
        });
    }

    function openComnModal(pageNm, width, height){
        let url = '<c:url value='${urlPrefix}/openComnModal${urlSuffix}'/>/' + pageNm;
        Utilities.openModal(url, width, height);
    }

    function openModal(pageNm, width, height){
        let url = '<c:url value='${urlPrefix}/openModal${urlSuffix}'/>/' + pageNm;
        Utilities.openModal(url, width, height);
    }
</script>