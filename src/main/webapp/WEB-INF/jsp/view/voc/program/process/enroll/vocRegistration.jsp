<%--
  Created by IntelliJ IDEA.
  User: tarr4h
  Date: 2022-11-23
  Time: AM 9:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<div class="v_header">
    <div class="v_header_title">
        <h3>VOC 신규등록</h3>
    </div>
</div>

<div class="content_wrapper">
    <form id="registerFrm">
        <div class="mBoard2">
            <table id="registerTable">
                <colgroup>
                    <col width="15%">
                    <col width="35%">
                    <col width="15%">
                    <col width="35%">
                </colgroup>
                <tbody>
                    <tr>
                        <th class="left"><span class="iMust">고객정보</span></th>
                        <td class="left" colspan="3">
                            <div id="custInfo"></div>
                            <a class="btn">고객조회</a>
                        </td>
                    </tr>
                    <tr>
                        <th class="left"><span class="iMust">등록채널</span></th>
                        <td class="left">
                            <div id="channelWrapper"></div>
                        </td>
                        <th class="left">등록자</th>
                        <td class="left">
                            <input type="text" class="it" name="regUser" disabled>
                        </td>
                    </tr>
                    <tr>
                        <th class="left">대상</th>
                        <td class="left">
                            <div id="targetWrapper"></div>
                        </td>
                        <th class="left">등록부서</th>
                        <td class="left">
                            <input type="text" class="it" name="regOrg" disabled>
                        </td>
                    </tr>
                    <tr>
                        <th class="left">고객요구유형</th>
                        <td class="left">
                            <div id="typeWrapper"></div>
                        </td>
                        <th class="left">공개여부</th>
                        <td class="left">
                            <label for="publicY">공개</label>
                            <input type="radio" name="publicYn" value="Y" id="publicY">
                            <label for="publicY">비공개</label>
                            <input type="radio" name="publicYn" value="N" id="publicN">
                        </td>
                    </tr>
                    <tr>
                        <th class="left">제목</th>
                        <td class="left" colspan="3">
                            <input type="text" name="title" class="it" value="${registration.title}">
                        </td>
                    </tr>
                    <tr>
                        <th class="left">내용</th>
                        <td class="left" colspan="3">
                            <textarea name="content" class="it" style="height: 300px;" cols="30" rows="10">${registration.content}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <th class="left">등록자 의견</th>
                        <td class="left" colspan="3">
                            <input type="text" name="regComment" class="it" value="${registration.regComment}">
                        </td>
                    </tr>
                    <tr>
                        <th class="left">첨부파일 등록</th>
                        <td class="left" colspan="3">
                            <a class="btn">추가</a>
                            <div id="attachWrapper"></div>
                        </td>
                    </tr>
                    <tr>
                        <th colspan="4">
                            <div style="float:right;">
                                <a type="button" class="btn" data-event="temporary">임시등록</a>
                                <a type="button" class="btn btn-blue" data-event="insert">등록</a>
                            </div>
                        </th>
                    </tr>
                </tbody>
            </table>
        </div>



    </form>
</div>


<script>
    const $channelWrapper = $('#channelWrapper');
    const $typeWrapper = $("#typeWrapper");
    const $targetWrapper = $("#targetWrapper");

    window.onload = function(){
        selectChannel();
    }


    function selectChannel(prntsMgmtCd){
        $.ajax({
            url : '<c:url value="${urlPrefix}/selectMgmtChannelCd${urlSuffix}"/>',
            data : {
                prntsMgmtCd : prntsMgmtCd
            },
            success : function(res){
                console.log(res);
            },
            error: console.log
        });
    }


</script>