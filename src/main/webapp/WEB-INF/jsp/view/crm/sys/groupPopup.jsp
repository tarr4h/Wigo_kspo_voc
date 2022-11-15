<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%> 


 <div class="title">
        <h1>그룹 선택</h1>
        <a href="#" data-btn-type="closeModal"  class="close">팝업 닫기</a>
    </div>

<div class="cont">
	<div class="gTitle1 line">
            <h3 class="mTitle1">그룹 정보</h3>
            <div class="gRt">
<!--                 <a href="#" class="mBtn1 lWhite">승인</a> -->
<!--                 <a href="#" class="mBtn1 lWhite">중지</a> -->
<!--                 <a href="#" class="mBtn1 gray">승인요청</a> -->
<!--                 <a href="#" class="mBtn1 gray">중지요청</a> -->
                <a href="#" data-grid-id="grdList" data-click="saveGroupChecked" id="btnSaveList"  class="mBtn1 primary">저장</a>
            </div>
        </div>
        <div id="divGrid"  data-grid-id="grdList" data-get-url="<c:url value='${urlPrefix}/group/getGroupCheckList${urlSuffix}'/>" data-pagenation="N" data-type="grid" data-grid-callback="onGridLoad" data-tpl-url="<c:url value='/static/gridTemplate/group/groupPopup.xml'/>">
        </div>
</div>


<script>
function onGridDataLoaded(gridView,gridJson,srcJson){
    for(var i=0;i<gridJson.length;i++){
        var json = gridJson[i];
        gridView.checkItem(i,json.checkYn == "Y");
    }
}
function onGridLoad(){
    search();
}
function search()
{
    var url = "<c:url value='${urlPrefix}/group/getGroupCheckList${urlSuffix}'/>";
    var param = {userCd : '<c:out value="${userCd}"/>',
                 menuCd : '<c:out value="${menuCd}"/>'
            };
    grdList.loadUrl(url,param);
}
function saveGroupChecked(){
    var list = grdList.getCheckedJson();
    for(var i=0;i<list.length;i++){
        list[i].checkYn='Y';
        
        list[i].userCd='<c:out value="${userCd}"/>';
        list[i].menuCd='<c:out value="${menuCd}"/>';
        list[i].menuRegAuthYn='Y';
        list[i].menuReadAuthYn='Y';
        list[i].menuAmdAuthYn='Y';
        list[i].menuDelAuthYn='Y';
        
    }
    if(Utilities.getOpener().saveGroupChecked)
    	Utilities.getOpener().saveGroupChecked('<c:out value="${userCd}"/>' || '<c:out value="${menuCd}"/>',list);
    Utilities.closeModal();
}
</script>