<%--
  @author: tarr4h
  @date: 2022-11-30
  @time: PM 5:34
  @description
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld" %>

<div class="v_header">
    <div class="v_header_title">
        <h3>VOC 등록목록</h3>
    </div>
</div>

<div class="content_wrapper">
    <div class="list_searchArea">
        <form action="" id="searchFrm">
            <table id="searchTable">
                <colgroup>
                    <col width="10%">
                    <col width="40%">
                    <col width="10%">
                    <col width="40%">
                </colgroup>
                <tbody>
                <tr>
                    <th>VOC번호</th>
                    <td>
                        <input type="text" name="regSeq" class="list_shFrmInput">
                    </td>
                    <th>제목</th>
                    <td>
                        <input type="text" name="title" class="list_shFrmInput">
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td>
                        <input type="text" name="content" class="list_shFrmInput">
                    </td>
                    <th>기간</th>
                    <td>
                        <input class="it list_shFrmInput list_date_slt" type="text" data-type="dateRangeStart" data-range-end="endDt" id="strtDt" name="strtDt" data-enter="search" data-button="Y" value="" />
                        <input class="it list_shFrmInput list_date_slt" type="text" id="endDt" name="endDt" data-enter="search" data-button="Y" value=""/>
                        <button class="mBtn1 m lWhite" id="setToday" data-click="setToday" style="margin: 1px 0px;">오늘</button>
                        <button class="mBtn1 m lWhite" id="set1Week" data-click="set1Week" style="margin: 1px 0px;" data-default-button="Y">1주일</button>
                        <button class="mBtn1 m lWhite" id="set1month" data-click="set1month" style="margin: 1px 0px;">1개월</button>
                        <button class="mBtn1 m lWhite" id="set3month" data-click="set3month" style="margin: 1px 0px;">3개월</button>
                        <button class="mBtn1 m lWhite" id="set6month" data-click="set6month" style="margin: 1px 0px;">6개월</button>
                        <button class="mBtn1 m lWhite" id="setAllmonth" data-click="setAllmonth" style="margin: 1px 0px;">전체</button>

                    </td>
                </tr>
                <tr>
                    <th>등록자</th>
                    <td>
                        <input type="text" name="regUsr" class="list_shFrmInput">
                        <input type="button" value="조회하기" class="list_searchBtn" data-event="regEmp">
                        <input type="button" value="MY VOC" class="list_searchBtn">
                    </td>
                    <th>채널</th>
                    <td>
                        <div id="channelWrapper"></div>
                    </td>
                </tr>
                <tr>
                    <th>부서</th>
                    <td>
                        <input type="text" name="ownershipOrg" class="list_shFrmInput">
                        <input type="button" value="조회하기" class="list_searchBtn" data-event="org">
                    </td>
                    <th>이슈등급</th>
                    <td></td>
                </tr>
                </tbody>
            </table>
        </form>
        <div class="list_buttonArea">
            <div class="list_addonBtnBox">
                <input type="button" id="addonBtn" value="상세검색">
            </div>
            <div class="list_searchBtnBox">
                <input type="button" id="temporaryApproval" class="list_searchBtn" data-event="approval" value="임시 결재">
                <input type="button" id="searchReset" class="list_searchBtn" data-event="reset" value="초기화">
                <input type="button" id="searchRows" class="list_searchBtn" data-event="search" value="검색">
            </div>
        </div>
        <div class="searchAddon">
            <div class="addonArea">

            </div>
        </div>
    </div>

    <div class="divWrapper">
        <div id="divGrid1"
             data-get-url="<c:url value='${urlPrefix}/selectList${urlSuffix}'/>"
             data-pagenation="Y"
             data-grid-id="listGrid"
             data-type="grid"
             data-tpl-url="<c:url value='/static/gridTemplate/voc/vocIntergrationList.xml${urlSuffix}'/>"
             style="width:100%;height:500px;"
        >
        </div>
    </div>
</div>

<script>
    const $channelWrapper = $('#channelWrapper');
    const $statusWrapper = $("#statusWrapper");

    let empSearchTarget;

    $(() => {
        setSelectDefault();
    });

    function onGridLoad(){
        let param = {};
        param.recordCountPerPage = 10;
        window['listGrid'].loadUrl('', param);
    }

    /**
     * Grid 조회
     * @param data
     */
    function loadGrid(data){
        let param = {data};
        param.recordCountPerPage = 10;

        window['listGrid'].loadUrl("", param);
    }

    function onGridCellDblClick(gridView,itemIndex, column, json, value){
        console.log('ongridCellDblClick json : ', json);

        // 페이지이동
        let menu = {
            menuId: "0104020100",
            menuLvlNo: 4,
            menuNm: "VOC 신규등록",
            menuUrl: "vocRegistration?regSeq=" + json.regSeq,
            topMenuId: "0100000000"
        };

        openMenu(menu);
    }

    function openMenu(menu){
        let topWin = Utilities.getTopWindow();

        for(let i = 0; i < window.parent.length; i++){
            let win = window.parent[i];
            win.location.reload();
        }

        topWin.openMenuTab(menu.menuId, menu.menuNm, menu.menuUrl);
        // topWin.removeTab("0104020100");
    }

    $('.list_searchBtn').on('click', function(){
        let evt = $(this).data('event');

        switch(evt){
            case 'search' : search();break;
            case 'reset' : resetForm();break;
            case 'regEmp' : empSearchTarget = 'reg';openComnModal('vocEmpSearchModal', 950, 650);break;
            case 'recEmp' : empSearchTarget = 'rec';openComnModal('vocEmpSearchModal', 950, 650);break;
            case 'org' : openComnModal('vocOrgSearchModal', 950, 650);break;
            case 'approval' : temporaryApproval(); break;
        }
    });


    function temporaryApproval(){
        let rows = window['listGrid'].getCheckedJson();

        $.ajax({
            url : '<c:url value="${urlPrefix}/temporaryApproval${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data: JSON.stringify({rows}),
            success(res){
                alert(res+'건이 결재처리되었습니다.(임시기능)');
                for(let i = 0; i < window.parent.length; i++){
                    let win = window.parent[i];
                    win.location.reload();
                }
            },
            error: console.log
        });
    }

    /**
     * 부서조회 callback
     * @param data
     */
    function orgSearchCallback(data){
        $('input[name="ownershipOrg"]').val(data.orgNm).data('value', data.orgId);
    }

    /**
     * 직원조회 callback
     * @param data
     */
    function empSearchCallback(data){
        switch(empSearchTarget){
            case 'reg' : $('input[name="regUsr"]').val(data.empNm).data('value', data.empId);break;
        }
    }

    function setSelectDefault(){
        $channelWrapper.empty();
        $statusWrapper.empty();
        setSelectBox('channel');
    }

    /**
     * 채널 셀렉트박스 변경 시 자식 code append
     */
    $channelWrapper.on('change', '.channel', function(){
        $(this).nextAll().remove();
        let prntsCd = $(this).val();

        setSelectBox('channel', prntsCd);
    });

    /**
     * channel/type select를 화면에 append
     * @param ctgr
     * @param prntsCd
     * @returns {Promise<boolean>}
     */
    async function setSelectBox(ctgr, prntsCd){
        let list = await getSelectList(ctgr, prntsCd);

        if(list.length === 0){
            return false;
        }

        let select = '<select class="v_td_select ' + ctgr + '">' +
            '<option value="" selected disabled>선택하세요</option>';
        for(let i = 0; i < list.length; i++){
            let option = `
                <option value="\${list[i].id}">\${list[i].text}</option>
            `;
            select += option;
        }

        $channelWrapper.append(select);
    }

    /**
     * channel/type List를 조회
     *  - 선택값에 따라서 자식코드를 불러온다.
     *  - 선택값이 없는 경우 최상단 채널 아래 코드부터 불러온다.
     * @param ctgr
     * @param prntsCd
     * @returns {Promise<unknown>}
     */
    function getSelectList(ctgr, prntsCd){
        let url;
        switch(ctgr){
            case 'channel' : url = '<c:url value="${urlPrefix}/selectChannel${urlSuffix}"/>';break;
        }

        return new Promise(function(resolve){
            $.ajax({
                url: url,
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

    function openComnModal(pageNm, width, height){
        let url = `<c:url value='${urlPrefix}/openComnModal${urlSuffix}'/>/\${pageNm}`;
        Utilities.openModal(url, width, height);
    }
</script>