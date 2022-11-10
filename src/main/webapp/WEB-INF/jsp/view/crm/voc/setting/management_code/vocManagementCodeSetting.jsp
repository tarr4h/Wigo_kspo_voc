<%--
  Created by IntelliJ IDEA.
  User: taewoohan_
  Date: 2022/10/19
  Time: 10:03 AM
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

    .gTitle1{
        border-bottom: 1px solid black;
        padding-bottom: 7px;
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
    .tree_btn{
        width: 95px;
        height: 20px;
        background-color: white;
        border: 1px solid black;
        font-size: 11px;
        color: black;
    }

    .grid_wrapper{
        width: 100%;
        min-height: 5vh;
    }
</style>

<div class="gLeft" data-has-size="Y">
    <div class="mBox1">
        <div class="gTitle1">
            <h3 class="mTitle1">VOC 분류코드</h3>
            <button class="gRt tree_btn func_btn" data-event="manage_grp">그룹관리</button>
        </div>
        <div id="divTree"
             data-type="tree"
             data-get-url="<c:url value='${urlPrefix}/vocManagementCodeTree${urlSuffix}'/>"
             data-change-seq="Y"
        >
        </div>
    </div>
</div>

<div class="gRight" data-has-size="Y">
    <div class="mBox1">
        <div class="header">
            <h3 class="title" id="grid_title"></h3>
            <div class="guide">
                <div class="guideDot"></div>
                <span>분류코드 목록입니다.</span>
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
                 data-get-url="<c:url value='${urlPrefix}/vocManagementCodeGrid${urlSuffix}'/>"
                 data-grid-id="managementCdGrid"
                 data-type="grid"
                 data-tpl-url="<c:url value='/static/gridTemplate/voc/vocManagementCode.xml${urlSuffix}'/>"
                 style="width:100%;height:735px;"
            >
            </div>
        </div>
    </div>
</div>

<script>
    let selectedCd = null;

    // event listener
    $('.func_btn').on('click', function(){
        let event = $(this).data('event');
        if(selectedCd == null && event !== 'manage_grp'){
            return false;
        };
        switch(event){
            case 'save' : saveRows(); break;
            case 'add'  : openRegModal('vocManagementRegModal', 500, 180, selectedCd); break;
            case 'delete' : deleteRows(); break;
            case 'manage_grp' : openRegModal('vocManagementManageGrpModal', 900, 600); break;
        }
    });

    function deleteRows(){
        let rows = managementCdGrid.getCheckedJson();

        $.ajax({
            url : '<c:url value="${urlPrefix}/delete${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify(
                rows
            ),
            success(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert(`\${res}건이 삭제되었습니다.`);
                    location.reload();
                } else {
                    alert(`오류가 발생했습니다.\n(에러코드 : \${jqXHR.status}`);
                }
            },
            error: console.log
        })
    }

    function saveRows(){
        let rows = managementCdGrid.getJsonRows();

        $.ajax({
            url : '<c:url value="${urlPrefix}/update${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data: JSON.stringify({
                rows
            }),
            success(res, status, jqXHR){
                if(jqXHR.status === 200){
                    alert('저장되었습니다.');
                    location.reload();
                } else {
                    alert(`오류가 발생했습니다.\n(에러코드 : \${jqXHR.status})`);
                }
            },
            error: console.log
        })
    }

    function openRegModal(pageNm, width, height, prntsCd){
        let url = `<c:url value='${urlPrefix}/openModal${urlSuffix}'/>/\${pageNm}` + "?prntsCd=" + prntsCd;
        Utilities.openModal(url, width, height);
    }

    function onTreeSelect(data, node, tree){
        if(data.lvl === 5){
            alert('5depth까지 등록 가능합니다.');
            node.state.opened = false;
            node.state.selected = false;
            node.state.loaded = false;
            return false;
        }

        let prntsCd = data.id;
        let codeNm = data.text;

        selectedCd = prntsCd;
        $("#grid_title").text(codeNm);

        loadGrid(prntsCd);
    }

    function loadGrid(prntsCd){
        let param = {
            prntsCd,
            recordCountPerPage : 10
        };

        managementCdGrid.loadUrl('', param);
    }

</script>
