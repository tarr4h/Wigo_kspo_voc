<%--
  Created by IntelliJ IDEA.
  User: taewoohan
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
    .mBox1{
        padding: 10px;
        border: none;
    }

    .header{
      height: 15px;
      padding: 13px;
      background-color: #676767;
    }
    .header h3{
      display: inline;
      font-weight: 500;
      color: white;
    }
    .header button{
      float: right;
      border: none;
      background-color: inherit;
      color: white;
    }

    .org_container{
        display: flex;
    }
    .org_container_left{
        width: 35%;
    }
    .org_container_right{
        width: 65%;
    }

    .org_search{
        height: 187px;
    }
    .org_search_input{
        height: 24px;
        width: 76%;
        border: 1px solid gray;
        padding-left: 5px;
    }
    .org_search_btn{
        width: 20%;
        border: 1px solid gray;
        margin-left: 5px;
        font-size: 12px;
    }
    .search_wrapper{
        width: 99%;
        height: 35px;
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        align-content: center;
        justify-content: flex-start;
    }
    .search_result_wrapper{
        width: 98.7%;
        height: 105px;
        border: 1px solid gray;
        overflow: auto;
    }

    .org_list{
        height: 400px;
    }

    .search_emp_result {
        height: 83px;
    }
    .search_emp_result_row{
        margin-top: 10px;
    }
    .search_emp_result_title{
        width: 20%;
        display: inline-block;
    }

    .search_emp_result_input{
        width: 40%;
        height: 25px;
    }

    .search_complete_btn{
        width: 98%;
        margin: auto;
        height: 30px;
        font-size: 12px;
    }
</style>

<div class="header">
    <h3>사원 조회</h3>
    <button id="close_btn" data-event="close">X</button>
</div>

<div class="org_container">
    <div class="org_container_left">
        <div class="mBox1">
            <div class="org_search">
                <div class="gTitle1">
                    <h3 class="mTitle1">부서 검색</h3>
                </div>
                <div class="search_wrapper">
                    <input type="text" id="deptInput" class="org_search_input">
                    <button id="deptSearch" class="org_search_btn">검색</button>
                </div>
                <div class="search_result_wrapper">
                </div>
            </div>
            <div class="org_list">
                <div class="gTitle1">
                    <h3 class="mTitle1">부서 목록</h3>
                </div>
                <div class="tree_wrapper">
                    <div id="orgTree"
                         data-type="tree"
                         data-get-url="<c:url value='${urlPrefix }/getOrgTree${urlSuffix}'/>"
                         data-change-seq="Y"
                         style="width:100%;height:380px;overflow:auto;"
                    >
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="org_container_right">
        <div class="mBox1">
            <div class="org_emp_list">
                <h3 class="mTitle1">부서 구성원</h3>
                <div class="grid_wrapper">
                    <div id="divGrid1"
                         data-get-url="<c:url value='${urlPrefix}/getEmpGrid${urlSuffix}'/>"
                         data-grid-id="empListGrid"
                         data-type="grid"
                         data-tpl-url="<c:url value='/static/gridTemplate/voc/vocEmpList.xml${urlSuffix}'/>"
                         style="width:100%;height:400px;"
                    >
                    </div>
                </div>
            </div>
            <div class="selected_emp">
                <h3 class="mTitle1">선택 사원</h3>
                <div class="search_emp_result">
                    <div class="search_emp_result_row">
                        <span class="search_emp_result_title">사원명</span>
                        <input type="text" id="empNm" class="search_emp_result_input">
                    </div>
                    <div class="search_emp_result_row">
                        <span class="search_emp_result_title">사원코드</span>
                        <input type="text" id="empId" class="search_emp_result_input">
                    </div>
                </div>
            </div>
        </div>
        <button id="selectComplete" class="search_complete_btn">선택완료</button>
    </div>
</div>

<script>
    // opener에 empSearchCallback()을 만들어 놓기~

    // orgTree정보 제거
    $(() => {
        let opnr = Utilities.getOpener();
        let opnrPrgmId = opnr._PROGRAM_ID;
        let strgKey = opnrPrgmId + 'orgTree';

        window.localStorage.removeItem(strgKey);
    });

    // event listener
    $('#close_btn').on('click', function(){
        Utilities.closeModal();
    });
    $('#deptSearch').on('click', function(){
        let keyword = $('#deptInput').val();
        selectOrgList(keyword);
    });
    $('.search_result_wrapper').on('click', '.resultOrgRow', function(){
        selectTreeNode($(this).data('org-id'));
    });
    $('#selectComplete').on('click', function(){
        let opnr = Utilities.getOpener();
        let param = {
            empId : $('#empId').val(),
            empNm : $('#empNm').val()
        }
        opnr.empSearchCallback(param);
        Utilities.closeModal();
    });

    function onGridCellDblClick(gridView,itemIndex, column, json, value) {
        let empId = json.empId;
        let empNm = json.empNm;
        $('#empNm').val(empNm);
        $('#empId').val(empId);
    }

    function loadGrid(orgId){
        let param = {
            orgId,
            recordCountPerPage : 10
        };

        empListGrid.loadUrl('', param);
    }

    function onTreeSelect(data, node, tree){
        loadGrid(node.id);
    }

    function selectTreeNode(orgId){
        let $orgTree = $('#orgTree');
        $orgTree.selectNode(orgId);
        loadGrid(orgId);
    }

    function setOrgList(list){
        let $target = $('.search_result_wrapper');
        $target.empty();

        let ul = '<ul>';
        $.each(list, (i, e) => {
            let li = `
                <li class='resultOrgRow' data-org-id='\${e.orgId}'>\${e.orgNm}</li>
            `;
            ul += li;
        });
        ul += '</li>';

        $target.append(ul);
    }

    function selectOrgList(keyword){
        $.ajax({
            url : '<c:url value="${urlPrefix}/selectOrgList${urlSuffix}"/>',
            data: {
                keyword
            },
            success(res){
                setOrgList(res);
            },
            error: console.log
        })
    }
</script>