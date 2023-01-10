<%--
  @author: tarr4h
  @date: 2023-01-05
  @time: AM 9:43
  @description
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld" %>


<style>
    .gTitle1{
        border-bottom: 1px solid black;
        padding-bottom: 7px;
    }
</style>

<div class="gLeft" data-has-size="Y">
    <div class="mBox1">
        <div class="gTitle1">
            <h3 class="mTitle1">VOC 관리절차 설정</h3>
        </div>
        <div id="divTree"
             data-type="tree"
             data-get-url="<c:url value='${urlPrefix}/vocMgmtCdTree${urlSuffix}'/>"
             data-change-seq="Y"
        >
        </div>
    </div>
</div>

<div class="gRight" data-has-size="Y">
    <div class="mBox1">
        <div class="gTitle1">
            <h3 class="mTitle1" id="boxTitle">채널을 선택해주세요.</h3>
        </div>
        <div class="v_grid_box">
            <div class="header">
                <h3 class="title">담당부서</h3>
                <div class="v_guide">
                    <div class="v_guideDot"></div>
                    <span>담당 부서를 등록/수정/삭제 하고, 전체 프로세스에 대한 담당부서를 지정합니다.</span>
                </div>
            </div>
            <div class="v_btn_area">
                <div class="v_btn_wrapper">
                    <button class="btn func_btn" data-event="orgSave">저장</button>
                    <button class="btn btn-blue func_btn" data-event="orgAdd">추가</button>
                    <button class="btn btn-red func_btn" data-event="orgDel">삭제</button>
                </div>
            </div>
            <div class="v_grid_wrapper">
                <div id="divGrid1"
                     data-get-url="<c:url value='${urlPrefix}/selectDirOrgGrid${urlSuffix}'/>"
                     data-grid-id="dirOrgGrid"
                     data-type="grid"
                     data-tpl-url="<c:url value='/static/gridTemplate/voc/vocDirOrg.xml${urlSuffix}'/>"
                     style="width:100%;height:200px;"
                >
                </div>
            </div>
        </div>
        <div class="v_grid_box">
            <div class="header">
                <h3 class="title">관리절차 등록</h3>
                <div class="v_guide">
                    <div class="v_guideDot"></div>
                    <span>채널별로 절차를 등록/수정/삭제하고, 하위 업무를 관리합니다.</span>
                </div>
            </div>
            <div class="v_btn_area">
                <div class="v_btn_wrapper">
                    <button class="btn func_btn" data-event="prcdAdd">추가</button>
                    <button class="btn btn-red func_btn" data-event="prcdDel">삭제</button>
                </div>
            </div>
            <div class="v_grid_wrapper">
                <div id="divGrid2"
                     data-get-url="<c:url value='${urlPrefix}/selectMgmtPrcdGrid${urlSuffix}'/>"
                     data-grid-id="mgmtPrcdGrid"
                     data-type="grid"
                     data-tpl-url="<c:url value='/static/gridTemplate/voc/vocMgmtPrcd.xml${urlSuffix}'/>"
                     style="width:100%;height:353px;"
                >
                </div>
            </div>
        </div>
    </div>
</div>


<script>
    let selectedDirCd = null;

    $('.func_btn').on('click', function(){
        let evt = $(this).data('event');

        switch(evt){
            case 'orgAdd' : openComnModal('vocOrgSearchModal', 900, 650);break;
            case 'orgSave' : updateDirOrg();break;
            case 'orgDel' : deleteDirOrg();break;
            case 'prcdAdd' : openModal('vocMgmtPrcdRegModal', 600, 400);break;
            case 'prcdDel' : deleteMgmtPrcd();break;
        };
    })

    function onTreeSelect(data, node, tree){
        $.ajax({
            url : '<c:url value="${urlPrefix}/selectDirCd${urlSuffix}"/>',
            data: {
                chMgmtCd : data.mgmtCd
            },
            success : function(res){
                selectedDirCd = res.dirCd;
            },
            error: console.log,
            complete : function(){
                $('#boxTitle').text(data.mgmtCdNm);
                loadGrid(selectedDirCd, window['dirOrgGrid']);
                loadGrid(selectedDirCd, window['mgmtPrcdGrid']);
            }
        });
    }

    function loadGrid(dirCd, grid){
        let param = {
            dirCd : dirCd,
            recordCountPerPage : 10
        };

        grid.loadUrl('', param);
    }

    /**
     * 관리절차 삭제
     */
    function deleteMgmtPrcd(){
        let mgmtPrcdList = window['mgmtPrcdGrid'].getCheckedJson();

        $.ajax({
            url : '<c:url value="${urlPrefix}/deleteMgmtPrcd${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({
                mgmtPrcdList : mgmtPrcdList,
                dirCd : selectedDirCd
            }),
            success : function(res){
                alert(res.msg);
                if(res.result){
                    location.reload();
                }
            },
            error: console.log
        })
    }

    /**
     * 경로코드 절차 등록
     * @param prcdBasList
     */
    function insertDirPrcd(prcdBasList){
        $.ajax({
            url : '<c:url value="${urlPrefix}/insertDirPrcd${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({
                dirCd : selectedDirCd,
                prcdBasList : prcdBasList
            }),
            success : function(res){
                alert(res.msg);
                if(res.result){
                    location.reload();
                } else {
                    window['mgmtPrcdGrid'].reload();
                }
            },
            error: console.log
        })
    }

    /**
     * 담당부서 삭제
     */
    function deleteDirOrg(){
        let rows = window['dirOrgGrid'].getCheckedJson();

        $.ajax({
            url : '<c:url value="${urlPrefix}/deleteDirOrg${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({
                rows : rows,
                dirCd : selectedDirCd
            }),
            success : function(res){
                alert(res.msg);
                if(res.result){
                    location.reload();
                } else {
                    window['dirOrgGrid'].reload();
                }
            },
            error: console.log
        })
    }

    /**
     * 담당부서 업데이트
     */
    function updateDirOrg(){
        let rows = window['dirOrgGrid'].getJsonRows();

        $.ajax({
            url : '<c:url value="${urlPrefix}/updateDirOrg${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({
                rows : rows,
                dirCd : selectedDirCd
            }),
            success : function(res, status, jqXHR){
                alert(res.msg);
                if(res.result){
                    location.reload();
                } else {
                    window['dirOrgGrid'].reload();
                }
            },
            error: console.log
        });
    }

    /**
     * 경로코드 부서 등록
     * @param dirCd
     * @param orgId
     */
    function insertDirOrg(dirCd, orgId){
        $.ajax({
            url : '<c:url value="${urlPrefix}/insertDirOrg${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({
                dirCd : dirCd,
                orgId : orgId
            }),
            success: function(res){
                if(res > 0){
                    alert('부서가 추가되었습니다.');
                    location.reload();
                } else {
                    alert('오류가 발생했습니다. 관리자에게 문의해주세요.');
                }
            },
            error: console.log
        });
    }

    /**
     * 부서조회 callback
     * @param data
     */
    function orgSearchCallback(data){
        insertDirOrg(selectedDirCd, data.orgId);
    }

    /**
     * 모달 open
     * @param pageNm
     * @param width
     * @param height
     */
    function openModal(pageNm, width, height){
        let url = '<c:url value='${urlPrefix}/openModal${urlSuffix}'/>/' + pageNm;
        Utilities.openModal(url, width, height);
    }

    /**
     * 공통모달 open : 부서, 직원 조회 모달
     * @param pageNm
     * @param width
     * @param height
     */
    function openComnModal(pageNm, width, height){
        let url = '<c:url value='${urlPrefix}/openComnModal${urlSuffix}'/>/' + pageNm;
        Utilities.openModal(url, width, height);
    }

</script>