<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<div class="title">
        <h1>주소검색</h1>
        <a href="#" data-btn-type="closeModal" class="close">팝업 닫기</a>
    </div>

<div class="cont">
<div class="mSort2">
<form role="form" id="frmSearch">
				<ul>
					<li class="w32per">
						<div class="tit">주소검색</div>
						<div class="txt">
							<div class="mFlex2">
								<span class="flex">
                                    <input type="text" id="keyword" data-enter="search" name="keyword" class="it" placeholder="검색어">
                                </span>
                                <span class="button">
                                    <a href="#;" class="mBtn1"  id="btnSearch" data-click="search">검색</a>
                                </span>
							</div>
						</div>
					</li>
				</ul>
				</form>
			</div>
<!-- 	<div class="gTitle1 line"> -->
<!--             <h3 class="mTitle1">주소검색</h3> -->
<!--             <div class="gRt"> -->
<!--             </div> -->
<!--         </div> -->
        
	<div class="mBox1">
	<div id="divGrid" style="height:550px" data-grid-id="grdList" data-pagenation="Y" data-get-url="<c:url value='/address/getList${urlSuffix}'/>" 
data-type="grid" data-grid-callback="onGridLoad" data-tpl-url="<c:url value='/static/gridTemplate/system/address.xml'/>">
</div>
	</div>
</div>


<script>


function newUser(element,data){
    grdList.addRow();
}
function search()
{
    var url = "<c:url value='${urlPrefix}/address/getList${urlSuffix}'/>";
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
    $("#keyword")[0].focus();
}
function onGridCellDblClick(gridView,itemIndex, column, json, value){
	selectAddr(json);
}
function grdList_btnSel_buttonClicked(gridView,row,col,json){
   
	selectAddr(json);
    
}
function selectAddr(json){
	 var callbackName = "${callbackName}";
	    if(!callbackName)
	        callbackName = "onAddressSelect";
	    var win = Utilities.getOpener();
	    try{
	        if(win[callbackName])
	            win[callbackName](json);    
	    }catch(e){}
	    Utilities.closeModal();
}
</script>
