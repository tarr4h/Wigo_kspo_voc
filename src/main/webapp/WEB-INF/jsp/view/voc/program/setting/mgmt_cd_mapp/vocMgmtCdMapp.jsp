<%--
  Created by IntelliJ IDEA.
  User: tarr4h
  Date: 2022-11-21
  Time: AM 10:30
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

    .tree_btn_wrapper{
        width: 100%;
        display: flex;
        flex-direction: row;
        flex-wrap: nowrap;
        align-content: center;
        justify-content: flex-end;
    }
    .tree_btn{
        width: 95px;
        height: 25px;
        background-color: white;
        border: 1px solid black;
        font-size: 12px;
        color: black;
        margin-left: 5px;
    }
</style>

<div class="gLeft" data-has-size="Y" style="width: 100%;">
    <div class="mBox1">
        <div class="gTitle1">
            <h3 class="mTitle1">VOC 분류코드 매핑</h3>
            <div class="tree_btn_wrapper">
                <button class="tree_btn func_btn" data-event="add">추가</button>
                <button class="tree_btn func_btn" data-event="delete">삭제</button>
            </div>
        </div>
        <div id="divTree"
             data-type="tree"
             data-get-url="<c:url value='${urlPrefix}/vocMgmtCdMappTree${urlSuffix}'/>"
             data-change-seq="Y"
        >
        </div>
    </div>
</div>


<script>
    $('.func_btn').on('click', function(){
        let evt = $(this).data('event');
        switch(evt){
            case 'add' : openModal('vocMgmtCdMappAddModal', 600, 500);break;
            case 'delete' : deleteMapping(); break;
        }
    });

    // function onTreeSelect(data,node,tree){
    //
    // }

    /**
     * 매핑 삭제
     */
    function deleteMapping(){
        let target = $('#divTree').getSelectedNode();
        let mappCd = target.mappCd;

        $.ajax({
            url: '<c:url value="${urlPrefix}/delete${urlSuffix}"/>',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({mappCd : mappCd}),
            success: function(res){
                alert(res + '건이 삭제되었습니다.');
                location.reload();
            },
            error: console.log
        })
    }

    /**
     * 매핑 추가
     * @param mgmtCd
     */
    function addMapping(mgmtCd){
        let target = $('#divTree');
        let node = target.getSelectedNode();
        let prntsMappCd = node == null ? null : node.id;
        let param = {
            prntsMappCd : prntsMappCd,
            mgmtCd : mgmtCd
        };

        $.ajax({
            url: '<c:url value="${urlPrefix}/insert${urlSuffix}"/>',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(param),
            success : function(res){
                if(res.result){
                    alert(res.msg);
                    target.reload();
                    target.removeNode(node.id);
                } else {
                    alert(res.msg);
                }
            },
            error: console.log
        })
    }

    /**
     * 관리코드 매핑추가 모달 callback
     * @param data
     */
    function addMappingCallback(data){
        addMapping(data.mgmtCd);
    }

    function openModal(pageNm, width, height){
        let url = '<c:url value="${urlPrefix}/openModal${urlSuffix}"/>' + '/' + pageNm;
        Utilities.openModal(url, width, height);
    }
</script>