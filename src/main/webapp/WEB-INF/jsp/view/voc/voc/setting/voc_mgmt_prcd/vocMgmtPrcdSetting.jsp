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

    .grid_box{
        margin-top: 10px;
        border: 1px solid gray;
        padding: 15px;
        border-radius: 3px;
    }

    .btn_area{
        width: 100%;
        min-height: 2vh;
    }
    .btn_wrapper{
        float: right;
        margin-bottom: 0.5em;
    }

    .grid_wrapper{
        width: 100%;
        min-height: 5vh;
    }
</style>

<div class="gLeft" data-has-size="Y">
    <div class="mBox1">
        <div class="gTitle1">
            <h3 class="mTitle1">VOC 절차관리</h3>
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
            <h3 class="mTitle1" id="boxTitle"></h3>
        </div>
        <div class="grid_box">
            <div class="header">
                <h3 class="title">담당부서</h3>
                <div class="guide">
                    <div class="guideDot"></div>
                    <span>담당 부서를 등록/수정/삭제 하고, 주처리부서를 지정합니다.</span>
                </div>
            </div>
            <div class="btn_area">
                <div class="btn_wrapper">
                    <button class="btn func_btn" data-event="save">저장</button>
                    <button class="btn btn-blue func_btn" data-event="add">추가</button>
                    <button class="btn btn-red func_btn" data-event="delete">삭제</button>
                </div>
            </div>
            <div class="grid_wrapper">
                <div id="divGrid1"
                     data-get-url="<c:url value='${urlPrefix}/vocMgmtCdGrid${urlSuffix}'/>"
                     data-grid-id="mgmtCdGrid"
                     data-type="grid"
                     data-tpl-url="<c:url value='/static/gridTemplate/voc/vocMgmtCd.xml${urlSuffix}'/>"
                     style="width:100%;height:200px;"
                >
                </div>
            </div>
        </div>
        <div class="grid_box">
            <div class="header">
                <h3 class="title">절차 등록</h3>
                <div class="guide">
                    <div class="guideDot"></div>
                    <span>채널별로 절차를 등록/수정/삭제하고, 하위 업무를 관리합니다.</span>
                </div>
            </div>
            <div class="btn_area">
                <div class="btn_wrapper">
                    <button class="btn func_btn" data-event="add">추가</button>
                    <button class="btn btn-red func_btn" data-event="delete">삭제</button>
                </div>
            </div>
            <div class="grid_wrapper">
                <div id="divGrid2"
                     data-get-url="<c:url value='${urlPrefix}/vocMgmtCdGrid${urlSuffix}'/>"
                     data-grid-id="mcPrcdGrid"
                     data-type="grid"
                     data-tpl-url="<c:url value='/static/gridTemplate/voc/vocMgmtCd.xml${urlSuffix}'/>"
                     style="width:100%;height:353px;"
                >
                </div>
            </div>
        </div>
    </div>
</div>


<script>
    function onTreeSelect(data, node, tree){
        $('#boxTitle').text(data.mgmtCdNm);
    }
</script>