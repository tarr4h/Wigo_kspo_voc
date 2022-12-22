<%--
  @author: tarr4h
  @date: 2022-12-05
  @time: PM 4:56
  @description
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld" %>

<style>
    /* tab 관련 */
    .btnTopMargin{ /* grid의 상단 margin을 죽임 */
        margin-top: 0!important;
    }

    .tabArea{
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        justify-content: flex-start;
        align-content: flex-end;
        margin-top: 10px;
    }
    .gridTab{
        width: 110px;
        height: 25px;
        border: 1px solid gray;
        border-top-left-radius: 15px;
        border-top-right-radius: 15px;
        text-align: center;
        padding-top: 9px;
        font-size: 13px;
        border-bottom: none;
        margin-right: 3px;
        background-color: #848484bd;
        color: #f8f8f8b8;
        font-weight: 600;
    }
    .gridArea{
        display: none;
    }

    .tabSelected{
        border: 1px solid #0f1e4c;
        border-bottom: none;
        color: #0f1e4c;
        background-color: white!important;
    }


</style>

<div class="v_header">
    <div class="v_header_title">
        <h3>VOC 접수목록</h3>
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
                    <th>접수자</th>
                    <td>
                        <input type="text" name="recUsr" class="list_shFrmInput">
                        <input type="button" value="조회하기" class="list_searchBtn" data-event="recEmp">
                        <input type="button" value="MY VOC" class="list_searchBtn">
                    </td>
                    <th>유형</th>
                    <td>
                        <div id="typeWrapper"></div>
                    </td>
                </tr>
                <tr>
                    <th>부서</th>
                    <td>
                        <input type="text" name="ownershipOrg" class="list_shFrmInput">
                        <input type="button" value="조회하기" class="list_searchBtn" data-event="org">
                    </td>
                    <th>이슈등급</th>
                    <td>

                    </td>
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

    <div class="tabArea">
        <div class="gridTab tabSelected" data-grid="stndbyGrid" id="receiptStndby">접수대기</div>
        <div class="gridTab" data-grid="onGoingGrid" id="receiptOngoing">접수중</div>
        <div class="gridTab" data-grid="revokeGrid" id="receiptRevoke" >접수취소</div>
        <div class="gridTab" data-grid="reReceiptGrid" id="reReceipt">재접수</div>
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
    const $typeWrapper = $("#typeWrapper");

    let empSearchTarget;

    $(() => {
        setSelectDefault();
    });

    function onGridLoad(){
        let param = {};
        param.recordCountPerPage = 10;
        window['listGrid'].loadUrl('', param);
    }

    function onGridCellDblClick(gridView,itemIndex, column, json, value){
        // 페이지이동
        let menu = {
            menuId: "0104030200",
            menuLvlNo: 4,
            menuNm: "VOC 접수",
            menuUrl: "vocReceipt?regSeq=" + json.regSeq,
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

    /**
     * 검색영역 채널, 유형, 진행상태 select 기본값 호출
     */
    function setSelectDefault(){
        $channelWrapper.empty();
        $statusWrapper.empty();
        $typeWrapper.empty();
        setSelectBox('channel');
        setSelectBox('type');
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
     * 유형 셀렉트박스 변경 시 자식 code append
     */
    $typeWrapper.on('change', '.type', function(){
        $(this).nextAll().remove();
        let prntsCd = $(this).val();

        setSelectBox('type', prntsCd);
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

        switch(ctgr){
            case 'channel' : $channelWrapper.append(select);break;
            case 'type' : $typeWrapper.append(select);break;
        }

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
            case 'type' : url = '<c:url value="${urlPrefix}/selectType${urlSuffix}"/>';break;
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