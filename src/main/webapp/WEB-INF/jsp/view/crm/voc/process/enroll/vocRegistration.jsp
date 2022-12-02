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

<style>

</style>



<div class="v_header">
    <div class="v_header_title">
        <h3>VOC 신규등록(${param.regSeq})</h3>
    </div>
</div>

<div class="content_wrapper">
    <form id="registerFrm">
        <table id="registerTable" class="v_table">
            <colgroup>
                <col width="15%">
                <col width="35%">
                <col width="15%">
                <col width="35%">
            </colgroup>
            <tbody>
                <tr>
                    <th>고객정보</th>
                    <td>
                        <div id="custInfo"></div>
                        <button type="button" class="v_btn1">고객조회</button>
                    </td>
                    <th>등록자</th>
                    <td>
                        <input type="text" name="regUser" class="v_td_input_text" value="<c:if test="${regSeq == null}">${LOGIN_USER.userNm}</c:if><c:if test="${registration.regUsr != null}">${registration.regUsrNm}</c:if>" disabled>
                    </td>
                </tr>
                <tr>
                    <th>등록채널</th>
                    <td colspan="3">
                        <div id="channelWrapper"></div>
                    </td>
                </tr>
                <tr>
                    <th>제목</th>
                    <td colspan="3">
                        <input type="text" name="title" class="v_td_input_text" value="${registration.title}">
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3">
                        <textarea name="content" class="v_td_textarea" cols="30" rows="10">${registration.content}</textarea>
                    </td>
                </tr>
                <tr>
                    <th>등록자 의견</th>
                    <td colspan="3">
                        <input type="text" name="regComment" class="v_td_input_text" value="${registration.regComment}">
                    </td>
                </tr>
                <tr>
                    <th>첨부파일 등록</th>
                    <td colspan="3">
                        <button type="button" class="v_btn1">추가</button>
                        <div id="attachWrapper"></div>
                    </td>
                </tr>
                <tr>
                    <th colspan="4">
                        <button type="button" class="v_rBtn v_btn1 v_btn_green" data-event="insert">등록</button>
                        <button type="button" class="v_rBtn v_btn1" data-event="temporary">임시등록</button>
                    </th>
                </tr>
            </tbody>
        </table>
    </form>
</div>

<script>
    const regSeq = '${param.regSeq}';
    const $channelWrapper = $('#channelWrapper');

    $(() => {
        setChannel();
    });

    $('button').on('click', function(){
        let evt = $(this).data('event');

        switch(evt){
            case 'insert' : regVoc();break;
            case 'temporary' : saveVoc();break;
        }
    });

    function setChList(){
        let arr = [];

        $.each($('.channel'), (i, e) => {
            let val = $(e).val();
            if(val != null){
                arr.push($(e).val());
            }
        });

        return arr;
    }

    /**
     * 등록 후 통합검색 화면 랜딩
     *  - 나머지 tab 화면 reload
     */
    function afterReg(menu){
        let topWin = Utilities.getTopWindow();

        for(let i = 0; i < window.parent.length; i++){
            let win = window.parent[i];
            win.location.reload();
        }

        topWin.openMenuTab(menu.menuCd, menu.menuNm, menu.menuUrl);
        topWin.removeTab("0104020100");
    }

    /**
     * voc 임시저장
     */
    function saveVoc(){
        let form = $('#registerFrm');
        let disabled = form.find(':input:disabled').removeAttr('disabled');
        let formArr = form.serializeArray();

        let chList = setChList();

        $.ajax({
            url: '<c:url value="${urlPrefix}/registerTemporary${urlSuffix}"/>',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                regSeq,
                formArr,
                chList
            }),
            success(res){
                alert(res.msg);
                if(res.result){
                    // 페이지이동
                    let menu = {
                        menuCd: "0104010200",
                        menuLvlNo: 4,
                        menuNm: "VOC 등록",
                        menuUrl: "vocRegistrationList",
                        topMenuCd: "0100000000"
                    };
                    afterReg(menu);
                }
            },
            error: console.log
        });
    }

    /**
     * voc 등록
     */
    function regVoc(){
        let form = $('#registerFrm');
        let disabled = form.find(':input:disabled').removeAttr('disabled');
        let formArr = form.serializeArray();

        let chList = setChList();

        $.ajax({
            url: '<c:url value="${urlPrefix}/registerComplete${urlSuffix}"/>',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                formArr,
                chList
            }),
            success(res){
                alert(res.msg);
                if(res.result){
                    // 페이지이동
                    let menu = {
                        menuCd: "0104010100",
                        menuLvlNo: 4,
                        menuNm: "VOC 통합검색",
                        menuUrl: "vocIntergrationList",
                        topMenuCd: "0100000000"
                    };
                    afterReg(menu);
                }
            },
            error: console.log
        });
    }

    /**
     * 채널 셀렉트박스 변경 시 자식 code append
     */
    $channelWrapper.on('change', '.channel', function(){
        $(this).nextAll().remove();
        let prntsCd = $(this).val();

        setChannel(prntsCd);
    });

    /**
     * channelList를 화면에 append
     * @param prntsCd
     * @returns {Promise<boolean>}
     */
    async function setChannel(prntsCd){
        let channelList = await getChannel(prntsCd);

        if(channelList.length === 0){
            return false;
        }

        let select = '<select class="v_td_select channel">' +
            '<option value="" selected disabled>선택하세요</option>';
        for(let i = 0; i < channelList.length; i++){
            let option = `
                <option value="\${channelList[i].id}">\${channelList[i].text}</option>
            `;
            select += option;
        }

        $channelWrapper.append(select);
    }

    /**
     * channelList를 조회
     *  - 선택값에 따라서 자식코드를 불러온다.
     *  - 선택값이 없는 경우 최상단 채널 아래 코드부터 불러온다.
     * @param prntsCd
     * @returns {Promise<unknown>}
     */
    function getChannel(prntsCd){
        return new Promise(function(resolve){
            $.ajax({
                url: '<c:url value="${urlPrefix}/selectChannel${urlSuffix}"/>',
                data: {
                    prntsCd
                },
                success(res){
                    resolve(res);
                },
                error: console.log
            });
        })
    }
</script>