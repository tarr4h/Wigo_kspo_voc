<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%> 

<!-- popup -->
<div class="popup">

	<!-- top -->
	<strong class="title">사용자 선택팝업</strong>
	<button id="btnCancel" data-btn-type="closeModal" class="btnClose"></button>
	<!-- //top -->

	<!-- contentsWrap -->
	<div class="contentsWrap">
		<h4>사용자</h4>
		<!-- searchBox -->
		<div class="searchBox">
			<form role="form" id="frmSearch">
				<input type="hidden" name="currentPageNo" id="currentPageNo" value="1" />
				<input type="hidden" name="exGrpCd" id="exGrpCd" value="${grpCd }" />
				<ul>
					<li>
						<label for="userNm">사용자명</label>
						<input type="text" id="userNmLike" data-enter="search" name="userNmLike" class="medium" placeholder="사용자명">
					</li>
				</ul>
				<ul>
					<li>
						<label for="loginId">사용자아이디</label>
						<input type="text" id="loginIdLike" data-enter="search" name="loginIdLike" class="medium" placeholder="사용자아이디">
					</li>
				</ul>
				<div class="btnArea2">
					<button type="button" id="btnReset" data-btn-type="reset" class="btnReset">초기화</button>
					<button type="button" id="btnSearch" data-click="searchMenu" class="btnSearch">검색</button>
				</div>
			</form>
		</div>
		<!-- //searchBox -->
		<div id="divGrid"   data-grid-id="grdList" data-get-url="<c:url value='${urlPrefix}/user/getList'/>${urlSuffix}" data-pagenation="Y" data-type="grid" data-tpl-url="<c:url value='/static/gridTemplate/group/groupUser2.xml'/>">
		</div>
		<div class="btnPBWrap">
			<div class="btn-group">
				<button type="button" data-grid-id="grdList" data-click="addSelected" id="btnAddUserList" class="btnBasic">선택추가</button>
			</div>
		</div>
	</div>
	<!-- //contentsWrap -->
</div>
<!-- //popup -->

<script>
function searchMenu(){
    $("form#frmSearch").find("#currentPageNo").val(1);
    search();
}
function search()
{
    var url = "<c:url value='${urlPrefix}/user/getList'/>${urlSuffix}";
    var param = Utilities.formToMap("frmSearch");
    grdList.loadUrl(url,param);
}
function grdList_pageMove(gridView,pageNo){
    $("form#frmSearch").find("#currentPageNo").val(pageNo);
    search();
}
function addSelected(){
     var list = grdList.getCheckedJson();
    if(list.length ==0)
    {
        alert("체크된 데이터가 존재하지 않습니다.");
        return;
    }
    for(var i=0;i<list.length;i++){
        list[i].grpCd = "${grpCd}";
        list[i].stat = "n";
        list[i]._attributes.checked = false;
    }
    var url = "<c:url value='${urlPrefix}/group/addGroupUserList'/>${urlSuffix}";
    Utilities.getAjax(url,list,true,function(data,jqXHR){
        if(Utilities.processResult(data,jqXHR,"그룹 사용자 추가에 실패했습니다."))
        {
            alert("그룹 사용자 추가에  성공했습니다.");
            Utilities.getOpener().addGroupUser(list);
            Utilities.closeModal();
        }
    });
   
}

</script>