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
        height: 100px;
    }
    .right_section_lower{
        height: 666px;
        margin-top: 10px;
    }

    /*  component  */
    .v_area_wrapper{
        /*border: 1px solid gray;*/
        height: 95%;
        display: flex;
        justify-content: space-around;
        padding-top: 5px;
    }
    .v_area_section{
        border: 1px solid gray;
        height: 100%;
        width: 100%;
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
    .v_section_btn{
        border: 1px solid gray;
        height: 80%;
        width: 48px;
        font-size: 12px;
    }
</style>

<div class="gLeft" data-has-size="Y">
    <div class="mBox1">
        <div class="gTitle1">
            <h3 class="mTitle1">VOC 등록절차 분류코드 목록</h3>
            <button class="gRt tree_btn func_btn" data-event="manage_grp">분류코드 설정</button>
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
            <h3 class="mTitle1">담당 지정</h3>
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
                    <button class="v_section_btn" data-target="procedure" data-event="save">저장</button>
                    <button class="v_section_btn" data-target="procedure" data-event="add">추가</button>
                    <button class="v_section_btn" data-target="procedure" data-event="delete">삭제</button>
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
    $('.v_section_btn').on('click', function(){
       let evt = $(this).data('event');
       let target = $(this).data('target');

       switch(evt){
           case 'save' : ; break;
           case 'add' : openRegModal('vocRegProcedureModal_reg', 900, 341); break;
           case 'delete' : ; break;
       };
    });


    /**
     * 등록/수정 영역 삭제
     */
    $('.sec_remove_btn').on('click', function(){
       let sectionId = $(this).parent().prop('id');
       $('#' + sectionId).remove();
    });

    /**
     * modal open
     * @param pageNm
     * @param width
     * @param height
     */
    function openRegModal(pageNm, width, height){
        let treeNode = $('#divTree').getSelectedNode();
        let url = '<c:url value='${urlPrefix}/openModal${urlSuffix}'/>/' + pageNm + "?managementCd=" + treeNode.managementCd;
        Utilities.openModal(url, width, height);
    }
</script>