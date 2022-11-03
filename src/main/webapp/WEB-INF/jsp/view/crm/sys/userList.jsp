<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>
 <div class="mBox1">
 	<div class="gTitle1 line">
								<h3 class="mTitle1">사용자 정보</h3>
								<div class="gRt">
								</div>
			</div>
			<div class="mBoard2">
			<form role="form" id="frmSearch">
								<table>
									<caption>기본정보</caption>
									<colgroup>
										<col width="10%" />
										<col width="15%" />
										<col width="10%" />
										<col width="15%"  />
										<col width="10%" />
										<col width="15%"  />
										<col width="20%" />
									</colgroup>
									<tbody>
										<tr>
											<th scope="row" class="left"><span class="iMust">사용자명</span></th>
											<td>
												<input type="text" id="userNmLike" class="it" data-enter="search" name="userNmLike"  placeholder="사용자명">
											</td>
											<th scope="row" class="left">사용자아이디</th>
											<td>
												<input type="text" id="loginIdLike" data-enter="search" name="loginIdLike" class="it" placeholder="사용자아이디">
											</td>
											<th scope="row" class="left">상태</th>
											<td>
												<code:commCode id="delYn" name="delYn" codeCd="S030" prefixLabel="전체" prefixValue=""/>
											</td>
											
											<th scope="row" class="left">
											<a href="#" class="mBtn1" data-click="search">검색</a>
											</th>
											
										</tr>
										
									</tbody>
								</table>
								</form>
							</div>
	
 </div> 
 
 <div class="mBox1">
<div id="divGrid" style="height:500px" data-grid-id="grdList" data-pagenation="Y" data-get-url="<c:url value='${urlPrefix}/getList${urlSuffix}'/>" data-type="grid" data-grid-callback="onGridLoad" data-tpl-url="<c:url value='/static/gridTemplate/user/user.xml'/>">
								</div>

</div>
<script>


function newUser(element,data){
//  var url = "<c:url value='${urlPrefix}/add${urlSuffix}'/>";
//     Utilities.windowOpen(url,"addUser",900,550);
    grdList.addRow();
}
function search()
{
    var url = "<c:url value='${urlPrefix}/getList${urlSuffix}'/>";
    var param = Utilities.formToMap("frmSearch");
    grdList.loadUrl(url,param);
}
function grdList_pageMove(gridView,pageNo){
    $("form#frmSearch").find("#currentPageNo").val(pageNo);
    search();
}

function saveValidate(){
	var cuGridData = grdList.getSaveJson();
	for(var i=0; i<cuGridData.length; i++){
	    if(Utilities.isEmpty(cuGridData[i].loginId)){
	    	alert("아이디를 입력하세요.");
	    	return false;
	    }
	    if(Utilities.isEmpty(cuGridData[i].userNm)){
            alert("성명을 입력하세요.");
            return false;
        }
        if(Utilities.isNotEmpty(cuGridData[i].email) && !Utilities.checkEmail(cuGridData[i].email)){
            alert("이메일 형식이 맞지 않습니다.");
            return false;
        }
	}
	return true;
	//return true;
}

function saveUser(){

	if(!saveValidate()) return;
	
    var saveJson = grdList.getSaveJson();
    if(saveJson.length){
        if(!confirm("수정된 데이터를 저장하시겠습니까?"))
            return ;
        var url = "<c:url value='${urlPrefix}/save${urlSuffix}'/>";
        Utilities.getAjax(url,saveJson,true,function(data,jqXHR){
            if(Utilities.processResult(data,jqXHR,"사용자 저장에 실패했습니다."))
            {
                alert("사용자 저장에  성공했습니다.");
                grdList.reload();
            }
        });
    }
    else{
        alert("수정된 데이터가 없습니다.");
    }
}
function addUser(data){
    if(!data)
        return;
    data.stat = "n";
    grdList.addRow(data);
}
function removeUser(){
    var list = grdList.getCheckedJson();
    if(list.length ==0)
    {
        alert("체크된 데이터가 존재하지 않습니다.");
        return;
    }
    var saveJson = grdList.getCheckedJson();
    if(saveJson.length){
        if(!confirm("데이터를 삭제하면 복구할  수 없습니다. 계속하시겠습니까?"))
            return ;
        var url = "<c:url value='${urlPrefix}/removeUserList${urlSuffix}'/>";
        Utilities.getAjax(url,saveJson,true,function(data,jqXHR){
            if(Utilities.processResult(data,jqXHR,"사용자 삭제에 실패했습니다."))
            {
                alert("사용자 삭제에  성공했습니다.");
                grdList.removeCheckRow();
            }
        });
    }
    else{
        alert("선택된 데이터가 없습니다.");
    }
}

function grdList_btnGroup_buttonClicked(gridView,row,col,json){
    var url = "<c:url value='${urlPrefix}/group/groupPopup${urlSuffix}'/>?userCd="+json.userCd;
    Utilities.openModal(url,500,685);
    //Utilities.windowOpen(url,"groupPopup",500,580);
}
function grdList_btnRestPwd_buttonClicked(gridView,row,col,json){
    /* var loginId = json.loginId;
    var param  = {
            loginId : json.loginId
    };
    var url = "<c:url value='${urlPrefix}/user/resetPassword${urlSuffix}'/>";
    Utilities.getAjax(url,param,true,function(data,jqXHR){
        if(Utilities.processResult(data,jqXHR,"암호초기화에 실패했습니다."))
        {
            alert("암호초기화에  성공했습니다.");
//             grdList.removeCheckRow();
        }
    }); */

    var url = "<c:url value='${urlPrefix}/user/reset${urlSuffix}'/>?loginId="+json.loginId;
    Utilities.openModal(url,500,369);
}
// function grdList_secedeUser_ButtonClicked(gridView,row,col){
//     alert(col);
// }
function saveGroupChecked(userCd,saveJson){
    var url = "<c:url value='${urlPrefix}/group/setUserGroup${urlSuffix}'/>?userCd="+ userCd;
    
    Utilities.getAjax(url,saveJson,true,function(data,jqXHR){
        if(Utilities.processResult(data,jqXHR,"그룹 설정에 실패했습니다."))
        {
            alert("그룹 설정에  성공했습니다.");
            
        }
    });
}
function grdList_load(gridView,gridId){
     search();
}

</script>