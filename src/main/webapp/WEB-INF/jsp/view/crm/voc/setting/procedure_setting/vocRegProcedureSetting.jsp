<%--
  Created by IntelliJ IDEA.
  User: taewoohan
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
    .v_area_section{
        border: 1px solid gray;
        height: 100%;
        width: 100%;
        padding: 5px;
        /*margin-right: 5px;*/
        /*margin-left: 5px;*/
    }
    .v_section_title{
        height: 30px;
        display: flex;
        align-content: center;
        flex-wrap: wrap;
        flex-direction: row;
    }
    .v_title_sm{
        margin-left: 10px;
        font-size: 13px;
        font-weight: 500;
    }
    .v_section_btn_wrapper{
        float: right;
        padding-right: 10px;
        height: 30px;
    }

    /*  grid  */
    #procedureGrid_wrap_top{
        padding-top: 18px;
    }
    #orgGrid_wrap_top{
        padding-top: 33px;
    }
</style>

<div class="gLeft" data-has-size="Y">
    <div class="mBox1">
        <div class="gTitle1">
            <h3 class="mTitle1">VOC 등록절차 분류코드 목록</h3>
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
            <div id="divGrid2"
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
                </div>
                <div class="v_section_btn_wrapper">
                    <button class="mBtn1 func_btn" data-target="procedure" data-event="save">저장</button>
                    <button class="mBtn1 func_btn" data-target="procedure" data-event="add">추가</button>
                    <button class="mBtn1 func_btn" data-target="procedure" data-event="delete">삭제</button>
                </div>
                <div id="divGrid1"
                     data-get-url="<c:url value='${urlPrefix}/selectProcedureGrid${urlSuffix}'/>"
                     data-grid-id="procedureGrid"
                     data-type="grid"
                     data-tpl-url="<c:url value='/static/gridTemplate/voc/vocProcedure.xml${urlSuffix}'/>"
                     style="width:100%;height:420px;"
                >
                </div>
            </div>
<%--            <div class="v_area_section" id="section2">--%>
<%--                <div class="v_section_title">--%>
<%--                    <h3>단위2</h3>--%>
<%--                </div>--%>
<%--                <button class="sec_remove_btn">닫기</button>--%>
<%--            </div>--%>
<%--            <div class="v_area_section" id="section3">--%>
<%--                <div class="v_section_title">--%>
<%--                    <h3>단위3</h3>--%>
<%--                </div>--%>
<%--                <button class="sec_remove_btn">닫기</button>--%>
<%--            </div>--%>
        </div>
    </div>
</div>

<script>
    $('.func_btn').on('click', function(){
       let evt = $(this).data('event');
       let target = $(this).data('target');

       if(target === 'procedure'){
           switch(evt){
               case 'save' : ; break;
               case 'add' : openRegModal('vocRegProcedureModal_reg', 900, 447); break;
               case 'delete' : ; break;
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
     * 등록/수정 영역 삭제
     */
    $('.sec_remove_btn').on('click', function(){
        let sectionId = $(this).parent().prop('id');
        $('#' + sectionId).remove();
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

        loadGrid('procedureGrid', param);
        loadGrid('orgGrid', param);
    }

    /**
     * grid 버튼 클릭이벤트
     * @param gridView
     * @param row
     * @param col
     * @param json
     */
    async function onGridButtonClicked(gridView, row, col, json){


    }

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
     * 절차 등록 모달 open
     *  - 담당부서 등록되어있지 않은 경우 return false
     * @param pageNm
     * @param width
     * @param height
     */
    async function openRegModal(pageNm, width, height){
        let treeNode = $('#divTree').getSelectedNode();
        let managementCd = treeNode.managementCd;

        let dirCd = await selectDirCd(managementCd);
        let dutyOrgList = await selectDutyOrgList(dirCd);

        if(dutyOrgList == null || dutyOrgList.length === 0){
            alert('담당 부서를 먼저 지정해주세요');
            return false;
        }

        let url = '<c:url value='${urlPrefix}/openModal${urlSuffix}'/>/' + pageNm + "?managementCd=" + managementCd;
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