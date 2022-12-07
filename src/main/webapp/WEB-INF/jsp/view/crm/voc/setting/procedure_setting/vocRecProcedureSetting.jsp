<%--
  Created by IntelliJ IDEA.
  User: tarr4h
  Date: 2022/10/20
  Time: 1:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<style>
    .gTitle1{
        border-bottom: 1px solid black;
        padding-bottom: 7px;
    }

    .tree_btn{
        width: 95px;
        height: 20px;
        background-color: white;
        border: 1px solid black;
        font-size: 11px;
        color: black;
    }

    /*  gRight  */
    .right_section_upper{
        height: 250px;
    }
    .right_section_lower{
        height: 516px;
        margin-top: 10px;
    }

    .v_org_btn_wrapper{
        padding-top: 7px;
        float: right;
        padding-right: 10px;
        height: 30px;
    }



    /*  component  */
    .v_area_wrapper{
        /*border: 1px solid gray;*/
        height: 93%;
        display: flex;
        justify-content: space-around;
        padding-top: 5px;
    }
    .v_area_wrapper div:last-child{
        margin-right: 0;
    }
    .v_area_section{
        border: 1px solid gray;
        height: 100%;
        width: 100%;
        padding: 5px;
        margin-right: 5px;
        /*margin-left: 5px;*/
    }
    .v_section_title{
        height: 30px;
    }
    .v_title_sm{
        margin-left: 7px;
        margin-right: 7px;
        font-size: 12px;
        font-weight: 600;
        margin-top: 4px;
        color: #1c1c1c;
        display: inline-block;
    }
    .v_title_sub_warn{
        color: red;
    }
    #prcdCompulsoryNoti{
        display: none;
    }
    .v_section_btn_wrapper{
        float: right;
        padding-right: 10px;
        height: 30px;
    }

    .sec_remove_btn{
        float: right;
        border: none;
        background: none;
        margin-right: 5px;
        font-weight: 500;
    }

    /*  grid  */
    #orgGrid_wrap_top{
        padding-top: 33px;
    }
    #procedureGrid_wrap_top{
        padding-top: 18px;
    }
    #taskGrid_wrap_top{
        padding-top: 18px;
    }
    #activityGrid_wrap_top{
        padding-top: 18px;
    }
</style>

<div class="gLeft" data-has-size="Y">
    <div class="mBox1">
        <div class="gTitle1">
            <h3 class="mTitle1">VOC 접수절차 분류코드 목록</h3>
        </div>
        <div id="divTree"
            data-type="tree"
            data-get-url="<c:url value='${urlPrefix}/vocRegistrationManagementCodeTree${urlSuffix}'/>"
            data-change-seq="Y"
        >
        </div>
    </div>
</div>

<div class="gRight">
    <div class="mBox1 right_section_upper">
        <div class="gTitle1">
            <h3 class="mTitle1">담당부서 지정</h3>
        </div>
        <div class="v_org_btn_wrapper">
            <button class="mBtn1 func_btn" data-target="org" data-event="save">저장</button>
            <button class="mBtn1 func_btn" data-target="org" data-event="add">추가</button>
            <button class="mBtn1 func_btn" data-target="org" data-event="delete">삭제</button>
        </div>
        <div class="duty_org_setting_wrapper">
            <div id="divGrid1"
                 data-get-url="<c:url value='${urlPrefix}/selctDutyOrgGrid${urlSuffix}'/>"
                 data-grid-id="orgGrid"
                 data-type="grid"
                 data-tpl-url="<c:url value='/static/gridTemplate/voc/vocProcedureSettingOrgList.xml${urlSuffix}'/>"
                 style="width:100%;height:200px;margin-top: 5px;"
            >
            </div>
        </div>
    </div>
    <div class="mBox1 right_section_lower">
        <div class="gTitle1">
            <h3 class="mTitle1">절차 설정</h3>
        </div>
        <%--    component로 적용될 영역 wrapper    --%>
        <div class="v_area_wrapper">
            <div class="v_area_section" id="section1">
                <div class="v_section_title">
                    <h3 class="v_title_sm">절차 등록/수정</h3>
                    <span class="v_title_sub_warn" id="prcdCompulsoryNoti">* 등록되지 않은 필수절차가 존재합니다. 반드시 추가해주세요.</span>
                </div>
                <div class="v_section_btn_wrapper">
                    <button class="mBtn1 func_btn" data-target="procedure" data-event="add">추가</button>
                    <button class="mBtn1 func_btn" data-target="procedure" data-event="delete">삭제</button>
                </div>
                <div id="divGrid2"
                     data-get-url="<c:url value='${urlPrefix}/selectProcedureGrid${urlSuffix}'/>"
                     data-grid-id="procedureGrid"
                     data-type="grid"
                     data-tpl-url="<c:url value='/static/gridTemplate/voc/vocProcedure.xml${urlSuffix}'/>"
                     style="width:100%;height:420px;"
                >
                </div>
            </div>

            <div class="v_area_section" id="section2" style="display: none;">
                <div class="v_section_title">
                    <h3 class="v_title_sm">task등록/수정</h3>
                    <button class="sec_remove_btn">X</button>
                </div>
                <div class="v_section_btn_wrapper">
                    <button class="mBtn1 func_btn" data-target="task" data-event="add" data-procedure="">추가</button>
                    <button class="mBtn1 func_btn" data-target="task" data-event="delete">삭제</button>
                </div>
                <div id="divGrid3"
                     data-get-url="<c:url value='${urlPrefix}/selectTaskGrid${urlSuffix}'/>"
                     data-grid-id="taskGrid"
                     data-type="grid"
                     data-tpl-url="<c:url value='/static/gridTemplate/voc/vocTask.xml${urlSuffix}'/>"
                     style="width:100%;height:420px;"
                >
                </div>
            </div>

            <div class="v_area_section" id="section3" style="display: none;">
                <div class="v_section_title">
                    <h3 class="v_title_sm">ACVITIVY등록/수정</h3>
                    <button class="sec_remove_btn">X</button>
                </div>
                <div class="v_section_btn_wrapper">
                    <button class="mBtn1 func_btn" data-target="activity" data-event="save">저장</button>
                    <button class="mBtn1 func_btn" data-target="activity" data-event="add" data-task="">추가</button>
                    <button class="mBtn1 func_btn" data-target="activity" data-event="delete">삭제</button>
                </div>
                <div id="divGrid4"
                     data-get-url="<c:url value='${urlPrefix}/selectActivityGrid${urlSuffix}'/>"
                     data-grid-id="activityGrid"
                     data-type="grid"
                     data-tpl-url="<c:url value='/static/gridTemplate/voc/vocActivity.xml${urlSuffix}'/>"
                     style="width:100%;height:420px;"
                >
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $('.func_btn').on('click', function(){
       let evt = $(this).data('event');
       let target = $(this).data('target');

       if(target === 'procedure'){
           switch(evt){
               case 'add' : openProcedureRegModal(900, 447); break;
               case 'delete' : deleteProcedure(); break;
           }
       } else if(target === 'task'){
            switch(evt){
                case 'add' : openTaskRegModal($(this).data('procedure'), 900, 531); break;
                case 'delete' : deleteTask(); break;
            }
       } else if(target === 'activity'){
            switch(evt){
                case 'add' : openActivityRegModal($(this).data('task'), 900, 740); break;
                case 'delete' : deleteActivity(); break;
            }
       } else if(target === 'org'){
           switch(evt){
               case 'save' : updateDirOrg(); break;
               case 'add' : openComnModal('vocOrgSearchModal', 950, 650);; break;
               case 'delete' : deleteDirOrg(); break;
           }
       }
    });


    /**
     * tree 선택 시 해당 node의 procedure 목록 호출
     * @param data
     * @param node
     * @param tree
     */
    function onTreeSelect(data,node,tree){
        let managementCd = data.id;
        let param = {
            managementCd
        };

        $(".v_area_section:nth-child(1)").css('width', '100%');
        $(".v_area_section:nth-child(2)").hide();
        $(".v_area_section:nth-child(3)").hide();

        loadGrid('procedureGrid', param);
        loadGrid('orgGrid', param);

        validateRequiredPrcd(managementCd);
    }

    /**
     * grid 버튼 클릭이벤트
     * @param gridView
     * @param row
     * @param col
     * @param json
     */
    async function onGridButtonClicked(gridView, row, col, json){
        let gridId = gridView.gridId;
        switch(gridId){
            case 'procedureGrid' : showTaskSecetion(json.mcPrcdSeq);break;
            case 'taskGrid' : showActivitySection(json.mcTaskSeq); break;
        }
    }

    /**
     * 분류코드에 필수절차가 등록되어있는지 여부 조회 후 안내문구 표시
     * @param managementCd
     * @returns {Promise<void>}
     */
    async function validateRequiredPrcd(managementCd){
        let dirCd = await selectDirCd(managementCd);
        $.ajax({
            url: '<c:url value="${urlPrefix}/validateRequiredPrcd${urlSuffix}"/>',
            data: {
                dirCd
            },
            success(res){
                let target = $("#prcdCompulsoryNoti");
                if(!res){
                    target.show();
                } else {
                    target.hide();
                }
            },
            error: console.log
        });
    }

    /**
     * grid에서 선택된 procedure 삭제
     *  - 이하 task, activity 삭제
     */
    async function deleteProcedure(){
        if(!confirm("삭제하시겠습니까?\n필수절차는 반드시 재등록을 진행해주세요.")){
            return false;
        }

        let grid = window['procedureGrid'];
        let prcdList = grid.getCheckedJson();
        let managementCd = $('#divTree').getSelectedNode().id;
        let dirCd = await selectDirCd(managementCd);

        $.ajax({
            url : '<c:url value="${urlPrefix}/deleteProcedure${urlSuffix}"/>',
            method : 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                dirCd,
                prcdList
            }),
            success(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert(res.msg);
                    if(res.result){
                       location.reload();
                    }
                }
            },
            error: console.log
        })
    }

    /**
     * grid에서 선택된 task 삭제
     *  - 이하 activity 삭제
     */
    function deleteTask(){
        let grid = window['taskGrid'];
        let taskList = grid.getCheckedJson();

        $.ajax({
            url: '<c:url value="${urlPrefix}/deleteTask${urlSuffix}"/>',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                taskList
            }),
            success(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert(res + '건이 삭제되었습니다.');
                    grid.reload();
                }
            },
            error: console.log
        });
    }

    /**
     * grid에서 선택된 activity 삭제
     */
    function deleteActivity(){
        let grid = window['activityGrid'];
        let actList = grid.getCheckedJson();

        $.ajax({
            url: '<c:url value="${urlPrefix}/deleteActivity${urlSuffix}"/>',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                actList
            }),
            success(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert(res + '건이 삭제되었습니다.');
                    grid.reload();
                }
            },
            error: console.log
        });
    }

    /**
     * activity 등록/수정 영역을 보여주고 나머지 섹션의 길이 변경.
     * task section add버튼 data-procedure attribute에 선택한 prcdSeq를 설정
     */
    function showActivitySection(mcTaskSeq){
        $('.func_btn[data-target="activity"][data-event="add"]').data('task', mcTaskSeq);

        let param = {
            mcTaskSeq
        };
        window['activityGrid'].loadUrl('', param);

        $(".v_area_section:nth-child(3)").show().css('width', '47%');
        $(".v_area_section:nth-child(1)").css('width', '13%');
        $(".v_area_section:nth-child(2)").css('width', '40%');
    }

    /**
     * task 등록/수정 영역을 보여주고 나머지 섹션의 길이 변경.
     */
    function showTaskSecetion(mcPrcdSeq){
        $('.func_btn[data-target="task"][data-event="add"]').data('procedure', mcPrcdSeq);

        let param = {
            mcPrcdSeq
        };
        window['taskGrid'].loadUrl('', param);

        $(".v_area_section:nth-child(2)").show().css('width', '70%');
        $(".v_area_section:nth-child(1)").css('width', '30%');

    }

    /**
     * 등록/수정 영역 삭제
     */
    $('.sec_remove_btn').on('click', function(){
        let sectionId = $(this).parent().parent().prop('id');
        $('#' + sectionId).hide().next().hide();

        if(sectionId === 'section3'){
            $(".v_area_section:nth-child(1)").css('width', '30%');
            $(".v_area_section:nth-child(2)").css('width', '70%');
        } else if (sectionId === 'section2'){
            $(".v_area_section:nth-child(1)").css('width', '100%');
        }
    });

    /**
     * procedure bas 조회
     * @param prcdSeq
     */
    function selectProcedureBas(prcdSeq){
        return new Promise(function(resolve) {
            $.ajax({
                url: '<c:url value="${urlPrefix}/selectPrcdBas${urlSuffix}"/>',
                data: {
                    prcdSeq
                },
                success(res){
                    resolve(res);
                },
                error: console.log
            })
        })
    }

    /**
     * 요청 grid의 url 호출
     * @param gridId
     * @param param
     */
    async function loadGrid(gridId, param){
        let dirCd = await selectDirCd(param.managementCd);
        param.dirCd = dirCd;

        $.each(window.gridArray, (i, e) => {
            if(e.gridId === gridId){
                e.loadUrl('', param);
            }
        });
    }

    /**
     * 담당부서 목록 조회
     * @param dirCd
     * @returns {*}
     */
    function selectDutyOrgList(dirCd){
        return new Promise(function(resolve){
            $.ajax({
                url: '<c:url value="${urlPrefix}/selectDutyOrgList${urlSuffix}"/>',
                data : {dirCd},
                success(res){
                    resolve(res);
                },
                error: console.log
            });
        })
    }

    /**
     * dirCd 조회
     * @param managementCd
     * @returns {Promise<unknown>}
     */
    function selectDirCd(managementCd){
        let param = {managementCd};
        return new Promise(function(resolve){
            $.ajax({
                url: '<c:url value="${urlPrefix}/selectDirCd${urlSuffix}"/>',
                data: param,
                success(res){
                    resolve(res);
                },
                error: console.log
            });
        })
    }

    /**
     * 부서 update/delete 시 parameter 세팅
     * @returns {Promise<string>}
     */
    async function setDirOrgListParam(){
        let managementCd = $('#divTree').getSelectedNode().id;
        let dirCd = await selectDirCd(managementCd);
        let orgList = window['orgGrid'].getJsonRows();

        let param = JSON.stringify({
            dirCd,
            orgList
        });

        return param;
    }

    /**
     * 부서 update
     * @returns {Promise<void>}
     */
    async function updateDirOrg(){
        let param = await setDirOrgListParam();

        $.ajax({
            url: '<c:url value="${urlPrefix}/updateDirOrg${urlSuffix}"/>',
            method: 'POST',
            contentType: 'application/json',
            data : param,
            success(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert(res.msg);
                }

                if(res.result){
                    location.reload();
                } else {
                    window['orgGrid'].reload();
                }
            },
            error: console.log
        })
    }

    /**
     * orgGrid 선택을 통하여 org 삭제
     */
    async function deleteDirOrg(){
        let param = await setDirOrgListParam();

        $.ajax({
            url: '<c:url value="${urlPrefix}/deleteDirOrg${urlSuffix}"/>',
            method: 'POST',
            contentType: 'application/json',
            data : param,
            success(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert(`\${res}건이 삭제되었습니다.\n주처리부서는 삭제되지 않습니다.`);
                    location.reload();
                }

            },
            error: console.log
        })
    }

    /**
     * 경로 - 부서 매핑 insert
     * @param orgId
     */
    function insertDirOrg(orgId){
        let managementCd = $('#divTree').getSelectedNode().id;

        $.ajax({
            url: '<c:url value="${urlPrefix}/insertDirOrg${urlSuffix}"/>',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                orgId,
                managementCd
            }),
            success(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert('등록되었습니다.');
                    location.reload();
                }
            },
            error: console.log
        });
    }

    /**
     * 부서조회모달 callback
     * @param data
     */
    function orgSearchCallback(data){
        insertDirOrg(data.orgId);
    }

    /**
     * activity 등록 모달 open
     * @param mcTaskSeq
     * @param width
     * @param height
     */
    function openActivityRegModal(mcTaskSeq, width, height){
        let pageNm = 'vocRegActivityModal';

        let url = '<c:url value="${urlPrefix}/openModal${urlSuffix}"/>/' + pageNm + "?mcTaskSeq=" + mcTaskSeq;
        Utilities.openModal(url, width, height);
    }

    /**
     * TASK 등록 모달 open
     * @param mcPrcdSeq
     * @param width
     * @param height
     */
    function openTaskRegModal(mcPrcdSeq, width, height){
        let pageNm = 'vocRegTaskModal';

        let url = '<c:url value="${urlPrefix}/openModal${urlSuffix}"/>/' + pageNm + "?mcPrcdSeq=" + mcPrcdSeq;
        Utilities.openModal(url, width, height);
    };

    /**
     * 절차 등록 모달 open
     *  - 담당부서 등록되어있지 않은 경우 return false
     * @param pageNm
     * @param width
     * @param height
     */
    async function openProcedureRegModal(width, height){
        let pageNm = 'vocRegProcedureModal';
        let treeNode = $('#divTree').getSelectedNode();
        let managementCd = treeNode.managementCd;

        let dirCd = await selectDirCd(managementCd);
        let dutyOrgList = await selectDutyOrgList(dirCd);

        if(dutyOrgList == null || dutyOrgList.length === 0){
            alert('담당 부서를 먼저 지정해주세요');
            return false;
        }

        let url = '<c:url value='${urlPrefix}/openModal${urlSuffix}'/>/' + pageNm + "?dirCd=" + dirCd;
        Utilities.openModal(url, width, height);
    }

    /**
     * 공통 모달 open(org, emp)
     * @param pageNm
     * @param width
     * @param height
     */
    function openComnModal(pageNm, width, height){
        let url = `<c:url value='${urlPrefix}/openComnModal${urlSuffix}'/>/\${pageNm}`;
        Utilities.openModal(url, width, height);
    }
</script>