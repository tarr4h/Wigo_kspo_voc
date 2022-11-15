<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%> 
 <div class="title">
        <h1>메뉴 선택</h1>
        <a href="#" data-btn-type="closeModal" class="close">팝업 닫기</a>
    </div>
    
    
    <div class="cont">
    	
        
<form role="form" id="frmSearch">
<input type="hidden" name="exGrpCd" id="exGrpCd" value="${grpCd }" />
<input type="hidden" name="topCodeCd" id="topCodeCd" value="${topCodeCd }" />
<div class="mSort2">

			<ul >
				<li class="w100per">
					 <div class="tit" style="width:20%">메뉴명</div>
					 <div class="txt" style="width:40%">
						<input type="text" id="menuNmLike" data-enter="searchMenu" name="menuNmLike" class="it" placeholder="메뉴명" />
					</div>
					<div class="txt" style="width:40%">
						<a href="#" class="mBtn1" data-click="search" >검색</a>
					</div>
					
				</li>
			</ul>
		
		
			</div>
</form>
<div class="mBox1 ">
<div class="gTitle1 line">
            <h3 class="mTitle1">메뉴 목록</h3>
            <div class="gRt">
                <a href="#" data-grid-id="grdList" data-click="addSelected" id="btnAddMenuList" class="mBtn1 primary" id="btnSave">선택추가</a>
            </div>
        </div>

<div id="divGrid"   data-grid-id="grdList" data-get-url="<c:url value='${urlPrefix}/menu/getList'/>${urlSuffix}" 
data-pagenation="Y" data-type="grid" data-tpl-url="<c:url value='/static/gridTemplate/group/groupMenu.xml'/>"></div>

    </div>
    </div>
    
    
    

<script>
function searchMenu(){
    $("form#frmSearch").find("#currentPageNo").val(1);
    search();
}
function search()
{
    var url = "<c:url value='${urlPrefix}/menu/getList'/>${urlSuffix}";
    var param = Utilities.formToMap("frmSearch");
    grdList.loadUrl(url,param);
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
    var url = "<c:url value='${urlPrefix}/group/addGroupMenuList'/>${urlSuffix}";
    Utilities.getAjax(url,list,true,function(data,jqXHR){
        if(Utilities.processResult(data,jqXHR,"그룹메뉴 추가에 실패했습니다."))
        {
            alert("그룹메뉴 추가에  성공했습니다.");
            Utilities.getOpener().addGroupMenu(list);
            Utilities.closeModal();
        }
    });
   
}
function grdList_load(gridView,gridId){
    search();
}
</script>