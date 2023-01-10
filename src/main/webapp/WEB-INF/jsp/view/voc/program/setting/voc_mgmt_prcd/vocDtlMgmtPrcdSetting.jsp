<%--
  @author: tarr4h
  @date: 2023-01-09
  @time: AM 9:45
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

    .tree_box{
        margin-top: 10px;
        margin-bottom: 10px;
    }
    .tree_title{
        height: 18px;
        padding: 10px;
        border: 1px solid gray;
        font-size: 13px;
        border-radius: 5px;
    }

    #tpTree{
        display: none;
    }

    #avaliableDirCd{
        display: none;
    }
    #unavailableDirCd{
        display: none;
        padding: 20px;
        font-size: 20px;
    }
</style>

<div class="gLeft" data-has-size="Y">
    <div class="mBox1">
        <div class="gTitle1">
            <h3 class="mTitle1">처리VOC 관리절차 설정</h3>
        </div>
        <div class="tree_box" id="chTree">
            <div class="tree_title">
                <span>채널선택</span>
            </div>
            <div id="divChTree"
                 data-type="tree"
                 data-get-url="<c:url value='${urlPrefix}/vocMgmtChCdTree${urlSuffix}'/>"
                 data-change-seq="Y"
            >
            </div>
        </div>
        <div class="tree_box" id="tpTree">
            <div class="tree_title">
                <span>유형선택</span>
            </div>
            <div id="divTpTree"
                 data-type="tree"
                 data-get-url="<c:url value='${urlPrefix}/vocMgmtTpCdTree${urlSuffix}'/>"
                 data-change-seq="Y"
            >
            </div>
        </div>
    </div>
</div>

<div class="gRight" data-has-size="Y">
    <div class="mBox1">
        <div class="gTitle1">
            <h3 class="mTitle1" id="boxTitle">채널을 선택해주세요.</h3>
        </div>
        <div id="unavailableDirCd">
        </div>
        <div id="avaliableDirCd">
            <div class="v_grid_box">
                <div class="header">
                    <h3 class="title">관리절차 등록</h3>
                    <div class="v_guide">
                        <div class="v_guideDot"></div>
                        <span>채널별 유형마다 절차를 등록/수정/삭제하고, 하위 업무를 관리합니다.</span>
                    </div>
                </div>
                <div class="v_btn_area">
                    <div class="v_btn_wrapper">
                        <button class="btn func_btn" data-event="prcdAdd">추가</button>
                        <button class="btn btn-red func_btn" data-event="prcdDel">삭제</button>
                    </div>
                </div>
                <div class="v_grid_wrapper">
                    <div id="divGrid1"
                         data-get-url="<c:url value='${urlPrefix}/selectMgmtPrcdGrid${urlSuffix}'/>"
                         data-grid-id="mgmtPrcdGrid"
                         data-type="grid"
                         data-tpl-url="<c:url value='/static/gridTemplate/voc/vocMgmtPrcd.xml${urlSuffix}'/>"
                         style="width:100%;height:300px;"
                    >
                    </div>
                </div>
            </div>
            <div class="v_grid_children">
                <div class="v_grid_box v_grid_box_half">
                    <div class="header">
                        <h3 class="title">TASK 등록</h3>
                        <div class="v_guide">
                            <div class="v_guideDot"></div>
                            <span>TASk 관리</span>
                        </div>
                    </div>
                    <div class="v_btn_area">
                        <div class="v_btn_wrapper">
                            <button class="btn func_btn" data-event="taskAdd">추가</button>
                            <button class="btn btn-red func_btn" data-event="taskDel">삭제</button>
                        </div>
                    </div>
                    <div class="v_grid_wrapper">
                        <div id="divGrid2"
                             data-get-url="<c:url value='${urlPrefix}/selectMgmtTaskGrid${urlSuffix}'/>"
                             data-grid-id="mgmtTaskGrid"
                             data-type="grid"
                             data-tpl-url="<c:url value='/static/gridTemplate/voc/vocMgmtTask.xml${urlSuffix}'/>"
                             style="width:100%;height:251px;"
                        >
                        </div>
                    </div>
                </div>
                <div class="v_grid_box v_grid_box_half">
                    <div class="header">
                        <h3 class="title">수행 등록</h3>
                        <div class="v_guide">
                            <div class="v_guideDot"></div>
                            <span>수행 관리</span>
                        </div>
                    </div>
                    <div class="v_btn_area">
                        <div class="v_btn_wrapper">
                            <button class="btn func_btn" data-event="actvAdd">추가</button>
                            <button class="btn btn-red func_btn" data-event="actvDel">삭제</button>
                        </div>
                    </div>
                    <div class="v_grid_wrapper">
                        <div id="divGrid3"
                             data-get-url="<c:url value='${urlPrefix}/selectMgmtActvGrid${urlSuffix}'/>"
                             data-grid-id="mgmtActvGrid"
                             data-type="grid"
                             data-tpl-url="<c:url value='/static/gridTemplate/voc/vocMgmtActv.xml${urlSuffix}'/>"
                             style="width:100%;height:251px;"
                        >
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script>
    window.onload = function(){
        window.localStorage.removeItem('vocDtlMgmtPrcddivChTree');
        window.localStorage.removeItem('vocDtlMgmtPrcddivTpTree');
    };

    let chDirCd = null;
    let tpDirCd = null;

    let chMgmtCd = null;
    let tpMgmtCd = null;

    $('.func_btn').on('click', function(){
        let evt = $(this).data('event');

        switch(evt){
            case 'prcdAdd' : openAddModal('vocDtlMgmtPrcdRegModal', tpDirCd, 600, 400);break;
            case 'taskAdd' : openTaskModal();break;
            case 'actvAdd' : openActvModal();break;
        }
    });



    /**
     * 우측영역에 안내문구를 삽입
     * @param type - 001 : 채널에 VOC관리절차 미등록 시, 002 : 유형미선택 시
     */
    function setUnavailableZone(type){
        let chTxt = '<span>선택하신 채널에 VOC 관리절차가 등록되지 않았습니다.</span>' +
             '<br/>' +
             '<br/>' +
             '<span>VOC 관리절차 설정을 먼저 진행해주세요.</span>';
        let tpTxt = '<span>유형을 선택하세요</span>';

        let selected;
        switch(type){
            case '001' : selected = chTxt;break;
            case '002' : selected = tpTxt;break;
        }

        $('#unavailableDirCd').empty().append(selected);
    }

    async function onTreeSelect(data, node, tree){
        let comnCd = data.comnCd; // 001 : 채널, 002: 유형
        let mgmtCd = data.mgmtCd;

        if(comnCd === '001'){
            chMgmtCd = mgmtCd;
            let dirCd = await selectSingleDirCd(mgmtCd);

            if(dirCd === 'undefined' || dirCd == null){
                chDirCd = null;
                setUnavailableZone('001');
                $('#tpTree').hide();
                $('#unavailableDirCd').show();
                $('#avaliableDirCd').hide();
            } else {
                chDirCd = dirCd;
                setUnavailableZone('002');
                $('#tpTree').show();
                $('#boxTitle').text('유형을 선택해 주세요.');
            }
        } else if(comnCd === '002'){
            tpMgmtCd = mgmtCd;
            $('#boxTitle').text(data.mgmtCdNm);
            $('#unavailableDirCd').hide();
            $('#avaliableDirCd').show();

            // 우측 그리드 호출
            let dirCd = await selectComboDirCd(chMgmtCd, tpMgmtCd);
            tpDirCd = dirCd;
            loadMgmtPrcdGrid(dirCd);
        }
    }

    function onGridCellClick(gridView,rowIndex,columnName,colIndex,fieldIndex){
        let grid = gridView;
        let gridId = grid.gridId;
        let targetCol = grid.getJsonRow(rowIndex);
        grid.uncheckAll();
        grid.check(rowIndex);

        switch(gridId){
            case 'mgmtPrcdGrid' : loadMgmtTaskGrid(tpDirCd, targetCol.mgmtPrcdCd);break;
            case 'mgmtTaskGrid' : loadMgmtActvGrid(targetCol.mgmtTaskCd);break;
        }
    }

    function loadMgmtActvGrid(mgmtTaskCd){
        let param = {
            mgmtTaskCd,
            recordCountPerPage: 10
        };

        window['mgmtActvGrid'].loadUrl('', param);
    }

    function loadMgmtTaskGrid(dirCd, mgmtPrcdCd){
        let param = {
            dirCd,
            mgmtPrcdCd,
            recordCountPerPage: 10
        };

        window['mgmtTaskGrid'].loadUrl('', param);
    }

    /**
     * 경로코드 별 관리절차 그리드 호출
     * @param dirCd
     */
    function loadMgmtPrcdGrid(dirCd){
        let param = {
            dirCd,
            recordCountPerPage : 10
        };

        window['mgmtPrcdGrid'].loadUrl('', param);
    }

    /**
     * 관리 수행을 등록
     * @param actvList
     */
    function insertMgmtActv(actvList){
        let mgmtTaskCd = window['mgmtTaskGrid'].getCheckedJson()[0].mgmtTaskCd;

        $.ajax({
            url : '<c:url value="${urlPrefix}/insertMgmtActv${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({
                actvList,
                mgmtTaskCd
            }),
            success(res){
                console.log(res);
            },
            error: console.log
        })
    }

    /**
     * 경로 절차를 등록
     * @param prcdList
     */
    function insertDirPrcd(prcdList){
        $.ajax({
            url : '<c:url value="${urlPrefix}/insertDirPrcd${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({
                prcdList,
                dirCd : tpDirCd
            }),
            success(res){
                alert('등록되었습니다.');
            },
            error: console.log
        });
    }

    /**
     * 유형에 등록할 dirCd 조회
     *  - 미존재 시 생성
     * @param chMgmtCd
     * @param tpMgmtCd
     */
    function selectComboDirCd(chMgmtCd, tpMgmtCd){
        return new Promise(function(resolve, reject){
            $.ajax({
                url : '<c:url value="${urlPrefix}/selectComboDirCd${urlSuffix}"/>',
                data : {
                    chMgmtCd,
                    tpMgmtCd
                },
                success(res){
                    resolve(res.dirCd);
                },
                error: console.log
            })
        });
    }

    /**
     * 단건의 경로코드 조회(채널)
     * @param mgmtCd
     * @return {Promise<unknown>}
     */
    function selectSingleDirCd(mgmtCd){
        return new Promise(function(resolve, reject){
            $.ajax({
                url : '<c:url value="${urlPrefix}/selectSingleDirCd${urlSuffix}"/>',
                data : {
                    chMgmtCd : mgmtCd
                },
                success(res){
                    resolve(res.dirCd);
                },
                error: console.log
            });
        })
    }

    /**
     * TASK 추가 모달 호출
     * @return {boolean}
     */
    function openTaskModal(){
        let checkedPrcd = window['mgmtPrcdGrid'].getCheckedJson();
        if(checkedPrcd.length > 1){
            alert('1개의 절차만 선택해 주세요.');
            return false;
        } else if(checkedPrcd.length === 0){
            alert('절차를 먼저 지정해 주세요');
            return false;
        }

        let mgmtPrcdCd = checkedPrcd[0].mgmtPrcdCd;
        openAddModal('vocDtlMgmtTaskRegModal', mgmtPrcdCd, 600, 400);
    }

    /**
     * 수행 추가 모달 호출
     * @return {boolean}
     */
    function openActvModal(){
        let checkedTask = window['mgmtTaskGrid'].getCheckedJson();
        if(checkedTask.length > 1){
            alert('1개의 TASK만 선택해 주세요.');
            return false;
        } else if(checkedTask.length === 0){
            alert('TASK를 먼저 지정해 주세요');
            return false;
        }

        let mgmtTaskCd = checkedTask[0].mgmtTaskCd;
        openAddModal('vocDtlMgmtActvRegModal', mgmtTaskCd, 600, 400);
    }

    function openAddModal(pageNm, key, width, height){
        let url = '<c:url value="${urlPrefix}/openAddModal${urlSuffix}"/>' + "/" + pageNm + "/" + key;
        Utilities.openModal(url, width, height);
    }



</script>

