<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%> 
<div class="contents" style="width:100%">
	<div class="contents_block">
		<h3>내정보 변경</h3>
		 <form role="form" id="frmMain">
			<ul class="table_form tablelist_search">
				<li class="table_tr">
                	<div class="table_th" style="width: 20%;">아이디</div>
	                <div class="table_td" style="width:80%">
	                <c:out value="${LOGIN_USER.loginId }" />
	                </div>
                </li>
                
                <li class="table_tr">
                	<div class="table_th" style="width: 20%;">성명</div>
	                <div class="table_td" style="width:80%">
	                <c:out value="${LOGIN_USER.loginId }" />
	                </div>
                </li>
                 <li class="table_tr">
                	<div class="table_th" style="width: 20%;">이메일</div>
	                <div class="table_td" style="width:80%">
	                <input type="text" id="emailAddr" name="emailAddr" maxlength="50" />
	                </div>
                </li>
                
                <li class="table_tr">
                	<div class="table_th" style="width: 20%;">연락처</div>
	                <div class="table_td" style="width:80%">
	                <input type="text" id="mphonNo" name="mphonNo" maxlength="11" class="small" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1')"/>
	                </div>
                </li>
			</ul>
		</form>
		
		<button type="button" data-click="changePassword" id="changePassword" class="btn_white">비밀번호변경</button>
	<code:btnGroup name="MyInfo" hideInsert="true" hideDelete="true"/>
	</div>
</div>

<script>

/* email & mphonNo 초기화 임시저장 변수 */
var emailReset = "";
var mphonNoReset = "";

/* 내 정보 조회 */
$(document).ready(function(e) {
	var param = {loginId : '<c:out value="${LOGIN_USER.loginId}"/>'};
	/* getUser 메서드 실행 */
	/* url = /myInfo/getUser*/
	var url = "<c:url value='${urlPrefix}/getUser${urlSuffix}'/>";
	/* Ajax */
	Utilities.getAjax(url,param,true,function(data,jqXHR){
	    if(Utilities.processResult(data,jqXHR,"조회 실패했습니다."))
	    {
	    	/* 성명 세팅*/
	    	$('#userNm').text(data.userNm);
	    	/* email & mphonNo세팅 */
	    	emailReset = data.emailAddr;
	    	$('#emailAddr').val(emailReset);
	    	mphonNoReset = data.mphonNo;
	    	$('#mphonNo').val(mphonNoReset);
	    }
	});
});

/* 비밀번호 변경 모달열기 function*/
function changePassword(){
	/* url = /myInfo/myInfoChgPsw */
	var url = "<c:url value='${urlPrefix}/myInfoChgPsw${urlSuffix}'/>";
	Utilities.openModal(url,500,361);
}

/* 이메일, 연락처 변경 function */
function saveMyInfo(){
	
	/* form데이터 */
	var param = Utilities.formToMap("frmMain");
	/* id 추가 */
	param.loginId = '<c:out value="${LOGIN_USER.loginId }" />';
	
	/* validate 체크*/
	if(!validate(param))
        return;
	
	/* saveMyInfo 메서드 실행 */
	/* url = /myInfo/saveMyInfo*/
	var url = "<c:url value='${urlPrefix}/saveMyInfo${urlSuffix}'/>";
	if(!confirm("수정된 데이터를 저장하시겠습니까?"))
	    return ;
	
	/* Ajax */
	Utilities.getAjax(url,param,true,function(data,jqXHR){
	    if(Utilities.processResult(data,jqXHR,"저장 실패했습니다."))
	    {
	        alert("저장되었습니다.");
	    }
	});
}

/* 이메일, 연락처 변경 Validate function */
function validate(param){
	//아이디 null일 경우 false
	if(!param.loginId){
        alert("아이디는 필수 입니다.");
        return false;
    }
	
	//이메일 null일 경우 false
	if(!param.emailAddr){
        alert("이메일은 필수 입니다.");
        $("#emailAddr").focus();
        return false;
    }
	
	//연락처 null일 경우 false
    if(!param.mphonNo){
        alert("연락처 필수 입니다.");
        $("#mphonNo").focus();
        return false;
    }
	
	//이메일 && 연락처 변경 없을 경우 false
	var email = '<c:out value="${LOGIN_USER.emailAddr }" />';
	var mphonNo = '<c:out value="${LOGIN_USER.mphonNo }" />';
	if(param.emailAddr == email && param.mphonNo == mphonNo){
		alert("변경된 데이터가 없습니다.");
        return false;
	}
 	
	//이메일 형식
	if(!Utilities.checkEmail(param.emailAddr)){
		alert("이메일 형식이 맞지 않습니다.");
		return false;
	}

    return true;
}

/* 변경내역 초기화 */
function reset(){
	$("#emailAddr").val(emailReset);
	$("#mphonNo").val(mphonNoReset);
}
</script>