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
        <h3 class="title" id="grid_title">TASK코드 관리</h3>
        <div class="v_guide">
            <div class="v_guideDot"></div>
            <span>TASK코드 목록입니다.</span>
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
             data-get-url="<c:url value='${urlPrefix}/selectTaskCodeList${urlSuffix}'/>"
             data-grid-id="taskBasGrid"
             data-type="grid"
             data-grid-callback="onGridLoad"
             data-tpl-url="<c:url value='/static/gridTemplate/voc/vocTaskBas.xml${urlSuffix}'/>"
             style="width:100%;height:735px;"
        >
        </div>
    </div>
</div>

<script>
    function onGridLoad(){
        let param = {};
        taskBasGrid.loadUrl('', param);

    };

    ////////////////////// on event //////////////////////////
    /**
     * class = func_btn 클릭 이벤트 : 분기 처리
     */
    $('.func_btn').on('click', function(){
        let event = $(this).data('event');
        switch(event){
            case 'save' : saveRows(); break;
            case 'add'  : openModal('vocTaskBasRegModal', 800, 600); break;
            case 'delete' : deleteRows(); break;
        }
    });


    /**
     * grid 더블클릭 이벤트
     * @param gridView
     * @param rowIndex
     * @param columnName
     */
    function onGridCellDblClick(gridView,rowIndex, columnName){
        let target;
        let row = gridView.getJsonRow(rowIndex);

        switch(columnName){
            case 'dutyOrgNm': openComnModal('vocOrgSearchModal', 950, 650); target = 'duty_org'; break;
            case 'dutyEmpNm' : openComnModal('vocEmpSearchModal', 950, 650); target = 'duty_emp'; break;
            case 'dutyRole' : break;
            case 'autoApplySetting' : openPrcdBasSearchModal(row); target = 'autoApplySetting'; break;
        };

        if(target != null){
            let param = {
                taskCd : row.taskCd,
                col : target
            };
            window.localStorage.setItem('vocTaskCodeSettingDblClickVal', JSON.stringify(param));
        };
    }

    function onGridButtonClicked(gridView, row, col,json){
        let target;
        switch(col){
            case 'autoApplySetting' : openPrcdBasSearchModal(json); target = 'autoApplySetting'; break;
        }
        if(target != null){
            let param = {
                taskCd : json.taskCd,
                col : target
            };
            window.localStorage.setItem('vocTaskCodeSettingSelectedBtnVal', JSON.stringify(param));
        };
    }


    ////////////////// 기능 ////////////////////
    /**
     * 전체절차 자동적용이 자동적용여부 미선택 시 체크되지 않도록 검증
     * @param rows
     * @returns {}
     */
    function validateCheckbox(rows){
        let param = {
            result : true
        }

        // $.each(rows, (i, e) => {
        //     if(e.autoApplyPrcdYn === 'N' && e.autoApplyAllPrcdYn === 'Y'){
        //         param.result = false;
        //         param.rowKey = e.rowKey;
        //     }
        // });

        for(let i = 0; i < rows.length; i++){
            let row = rows[i];
            if(row.autoApplyPrcdYn === 'N' && row.autoApplyAllPrcdYn === 'Y'){
                param.result = false;
                param.rowKey = e.rowKey;
            }
        }

        return param;
    };

    /**
     * task 목록 저장
     *  - grid 전체 대상.
     */
    function saveRows(){
        let rows = taskBasGrid.getJsonRows();

        let validateResult = validateCheckbox(rows);
        if(!validateResult.result){
            alert('자동적용여부 미설정 시 전체절차 자동적용을 사용하실 수 없습니다.');
            taskBasGrid.focus(validateResult.rowKey, 'autoApplyAllPrcdYn');
            return false;
        }

        $.ajax({
            url : '<c:url value="${urlPrefix}/saveRows${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({rows:rows}),
            success : function(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert('저장되었습니다.');
                    location.reload();
                } else {
                    alert('오류가 발생했습니다.\n에러코드 : ' + jqXHR.status);
                }
            },
            error: console.log
        });
    }

    /**
     * task 삭제
     *  - grid에서 check된 row
     */
    function deleteRows(){
        let rows = taskBasGrid.getCheckedJson();
        if(rows.length === 0){
            alert('선택된 코드가 없습니다.');
        }

        $.ajax({
            url : '<c:url value="${urlPrefix}/deleteRows${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({rows:rows}),
            success : function(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert(res + '건이 삭제되었습니다.');
                    location.reload();
                } else {
                    alert('오류가 발생했습니다.\n에러코드 : ' + jqXHR.status);
                }
            },
            error : console.log
        })
    }

    /**
     * task에 할당된 권한 변경
     *  - targetInfo : 대상정보(col)
     *  - chngMap : 변경정보(id, nm)
     * @param targetInfo
     * @param chngMap
     */
    function chngTaskDuty(targetInfo, chngMap){
        $.ajax({
            url : '<c:url value="${urlPrefix}/chngTaskDuty${urlSuffix}"/>',
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
                    alert('오류가 발생했습니다.\n에러코드 : ' + jqXHR.status);
                }
            },
            error: console.log
        })
    }

    ////////////////// callback //////////////////
    /**
     * 부서 검색 모달 콜백
     * @param data
     */
    function orgSearchCallback(data){
        let jsonStorage = window.localStorage.getItem('vocTaskCodeSettingDblClickVal');
        let parsingStorage = JSON.parse(jsonStorage);

        let param = {
            id : data.orgId,
            nm : data.orgNm
        }
        chngTaskDuty(parsingStorage, param);
    }

    /**
     * 사원 검색 모달 콜백
     * @param data
     */
    function empSearchCallback(data){
        let jsonStorage = window.localStorage.getItem('vocTaskCodeSettingDblClickVal');
        let parsingStorage = JSON.parse(jsonStorage);

        let param = {
            id : data.empId,
            nm : data.empNm
        }

        chngTaskDuty(parsingStorage, param);
    }

    /**
     * 절차 검색 모달 콜백
     * @param data
     */
    function prcdSearchCallback(data){
        let jsonStorage = window.localStorage.getItem('vocTaskCodeSettingSelectedBtnVal');
        let parsingStorage = JSON.parse(jsonStorage);
        data.taskCd = parsingStorage.taskCd;

        $.ajax({
            url: '<c:url value="${urlPrefix}/updateAutoApplyPrcd${urlSuffix}"/>',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success : function(res, status, jqXHR){
                if(res === 1){
                    alert('변경되었습니다.');
                    location.reload();
                }
            },
            error: console.log
        });
    }


    /////////////////// 모달 관련 /////////////////////
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
     * 현 페이지 호출 requestMapping인 directory에서 모달 호출
     * @param pageNm
     * @param width
     * @param height
     * @param param
     */
    function openModal(pageNm, width, height, param){
        let url = '<c:url value='${urlPrefix}/openModal${urlSuffix}'/>/' + pageNm;
        Utilities.openModal(url, width, height);
    }

    /**
     * 절차 검색 모달 호출
     * @param json
     * @returns {boolean}
     */
    function openPrcdBasSearchModal(json){
        let autoApplyPrcdYn = json.autoApplyPrcdYn;
        if(autoApplyPrcdYn === 'N'){
            alert('자동적용 미사용시 세부설정이 불가합니다.');
            return false;
        }

        let autoApplyAllPrcdYn = json.autoApplyAllPrcdYn;

        if(autoApplyAllPrcdYn === 'N'){
            let prcdCd = json.autoApplyPrcdCd;

            let url = '<c:url value='${urlPrefix}/openModal${urlSuffix}'/>/vocPrcdBasSearchModal' + "?prcdCd=" + prcdCd;
            Utilities.openModal(url, 900, 600);
        } else {
            alert('전체절차 자동적용인 경우 세부설정이 불가합니다.');
            return false;
        }
    }



</script>