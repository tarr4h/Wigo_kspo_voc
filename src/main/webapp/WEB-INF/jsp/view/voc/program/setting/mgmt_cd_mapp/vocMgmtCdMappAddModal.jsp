<%--
  Created by IntelliJ IDEA.
  User: tarr4h
  Date: 2022-11-21
  Time: PM 12:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<style>
    #regBtn{
        position: fixed;
        bottom: 0;
        width: 100%;
        height: 30px;
        border-style: none;
        border-top: 1px solid gray;
    }
</style>

<div class="v_modal_header">
    <h3>절차 등록</h3>
    <a id="close_btn">X</a>
</div>

<div id="divTree"
     data-type="tree"
     data-get-url="<c:url value='${urlPrefix}/vocMgmtCdTree${urlSuffix}'/>"
     data-change-seq="Y"
>
</div>

<button id="regBtn">선택하기</button>


<script>
    $("#regBtn").on('click', function(){
        let treeNode = $('#divTree').getSelectedNode();
        if(treeNode.topMgmtCd === treeNode.id){
            alert('최상단코드는 매핑에 추가하실 수 없습니다.');
            return false;
        }

        if(!confirm(treeNode.mgmtCdNm + '을 추가하시겠습니까?')){
            return false;
        }

        let mgmtCd = treeNode.id;
        let param = {
            mgmtCd : mgmtCd
        };

        let opnr = Utilities.getOpener();
        opnr.addMappingCallback(param);
        Utilities.closeModal();
    });

    $("#close_btn").on('click', function(){
        Utilities.closeModal();
    });
</script>
