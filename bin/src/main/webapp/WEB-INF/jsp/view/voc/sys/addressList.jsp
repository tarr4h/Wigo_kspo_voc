<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%> 



<div class="mSort2">
<form role="form" id="frmSearch">
			<ul >
				<li class="w100per">
					 <div class="tit" style="width: 20%;">검색어</div>
                     <div class="txt" style="width:40%">
                     <input type="text" id="keyword" name="keyword" data-enter="search" class="it" placeholder="검색어" />

                     </div>
                      					 <div class="tit" style="width: 40%;">
                         <a href="#" class="mBtn1" data-click="search" >검색</a>
                     </div>
				</li>
			</ul>
		</form>
		
			</div>
			
 <div class="mBox1 ">
 	<div class="gTitle1">
			<h3 class="mTitle1">주소목록</h3>
				<div class="gRt">
				<button type="button" data-click="popupZip" id="btnPopupZip" class="mBtn1">팝업띄우기</button>
				</div>
		</div>
 	<div  id="divGrid" style="height:500px" data-grid-id="grdList" data-pagenation="Y" data-get-url="<c:url value='/address/getList${urlSuffix}'/>" 
		data-type="grid" data-grid-callback="onGridLoad" data-tpl-url="<c:url value='/static/gridTemplate/system/address.xml'/>">
		</div>
		
</div>

                
<script>
function onAddressSelect(json){
	alert(json.roadAddr);
}
function popupZip(){
	Utilities.openZipPop("onAddressSelect");
}
function newUser(element,data){
    grdList.addRow();
}
function search()
{
    var url = "<c:url value='${urlPrefix}/getList${urlSuffix}'/>";
    var param = Utilities.formToMap("frmSearch");
    if(!param.keyword)
    {
        alert("검색어를 입력해주세요");
        document.getElementById("keyword").focus();
        return;
    }
    grdList.loadUrl(url,param);
}

function grdList_load(gridView,gridId){
    
}
function grdList_btnSel_buttonClicked(gridView,row,col,json){

}
</script>