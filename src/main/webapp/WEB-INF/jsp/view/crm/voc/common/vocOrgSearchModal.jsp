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

/*    */
    .search_org{
        height: 40px;
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        align-content: center;
        justify-content: flex-start;
    }
    .search_org input{
        width: 200px;
    }
    .search_org button{
        height: 25px;
        border: 1px solid gray;
        width: 39px;
        font-size: 12px;
        margin-left: 5px;
    }
</style>

<div class="header">
    <h3>부서 조회</h3>
    <button id="close_btn" data-event="close">X</button>
</div>

<div class="org_container">
    <div class="org_container_left">
        <div class="mBox1">
            <div class="org_list">
                <div class="gTitle1">
                    <h3 class="mTitle1">트리로 찾기</h3>
                </div>
                <div class="tree_wrapper">
                    <div id="orgTree"
                         data-type="tree"
                         data-get-url="<c:url value='${urlPrefix }/getOrgTree'/>${urlSuffix}"
                         data-change-seq="Y"
                         style="width:100%;height:570px;overflow:auto;"
                    >
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="org_container_right">
        <div class="mBox1">
            <div class="org_emp_list">
                <h3 class="mTitle1">검색으로 찾기</h3>
                <div class="search_org">
                    <input type="text" id="deptInput"/>
                    <button id="deptSearch">검색</button>
                </div>
                <div class="grid_wrapper">
                    <div id="divGrid1"
                         data-get-url="<c:url value='${urlPrefix}/getOrgGrid${urlSuffix}'/>"
                         data-grid-id="orgListGrid"
                         data-type="grid"
                         data-tpl-url="<c:url value='/static/gridTemplate/voc/vocOrgList.xml${urlSuffix}'/>"
                         style="width:100%;height:360px;"
                    >
                    </div>
                </div>
            </div>
            <div class="selected_emp">
                <h3 class="mTitle1">선택 부서</h3>
                <div class="search_emp_result">
                    <div class="search_emp_result_row">
                        <span class="search_emp_result_title">부서명</span>
                        <input type="text" id="orgNm" class="search_emp_result_input">
                    </div>
                    <div class="search_emp_result_row">
                        <span class="search_emp_result_title">부서코드</span>
                        <input type="text" id="orgId" class="search_emp_result_input">
                    </div>
                </div>
            </div>
        </div>
        <button id="selectComplete" class="search_complete_btn">선택완료</button>
    </div>
</div>

<script>
    // opener에 orgSearchCallback()을 만들어 놓기~

    // event listener
    $('#close_btn').on('click', function(){
        Utilities.closeModal();
    });
    $('#deptSearch').on('click', function(){
        let keyword = $('#deptInput').val();
        loadGrid(keyword);
    });

    $('#selectComplete').on('click', function(){
        let opnr = Utilities.getOpener();
        let param = {
            orgId : $('#orgId').val(),
            orgNm : $('#orgNm').val()
        }
        opnr.orgSearchCallback(param);
        Utilities.closeModal();
    });

    function onGridCellDblClick(gridView,itemIndex, column, json, value) {
        let orgId = json.orgId;
        let orgNm = json.orgNm;
        $('#orgNm').val(orgNm);
        $('#orgId').val(orgId);

        selectTreeNode(orgId);
    }

    function onTreeSelect(data, node, tree){
        let orgId = node.id;
        let orgNm = node.text;
        $('#orgNm').val(orgNm);
        $('#orgId').val(orgId);
        // loadGrid(orgNm);
    }

    function selectTreeNode(orgId){
        let $orgTree = $('#orgTree');
        $orgTree.selectNode(orgId);
    }

    function loadGrid(keyword){
        let param = {
            keyword,
            recordCountPerPage : 10
        };

        orgListGrid.loadUrl('', param);
    }


</script>