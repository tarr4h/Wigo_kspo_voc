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
	<strong class="title">비밀번호 변경</strong>
	<button id="btnCancel" data-btn-type="closeModal" class="btnClose"></button>
	<!-- //top -->
	
	<!-- contentsWrap -->
	<div class="contentsWrap">
		<!-- form start -->
		<form id="frmMain">
			<!-- boardWrite -->
			<div class="boardWrite">	
				<table>
					<colgroup>
						<col width="30%">
						<col width="70%">
					</colgroup>
					<tbody>
						<tr>
							<th class="asterisk">현재 비밀번호</th>
							<td><input type="password" id="password" name="password" class="medium"></td>
						</tr>
						<tr>
							<th class="asterisk">비밀번호</th>
							<td><input type="password" id="pwd" name="pwd" class="medium"></td>
						</tr>
						<tr>
							<th class="asterisk">확인</th>
							<td><input type="password" id="pwdOk" name="pwdOk" class="medium"></td>
						</tr>
					</tbody>
				</table>
				<p style="color:red">※비밀번호는 영문+숫자+특수문자 조합 10자리 이상</p>
			</div>
			<!-- //boardWrite -->
		</form>
		
		<!-- btnArea -->
		<div class="btnPBWrap">
			<code:btnGroup name="MyInfoChgPwd" hideInsert="true" hideDelete="true" hasCancel="true"/>
		</div>
	</div>
	<!--// btnArea -->
	<div id="pwdMsg" name="pwdMsg" class="center">
   
	</div>
</div>
<script>
/* 비밀번호 정합성 validate */
$("#pwd").keyup(function(){
	$("#pwdMsg").html(Utilities.validatePasswordMsg($("#pwd").val()));
	if(Utilities.isEmpty($("#pwd").val())){
		$("#pwdMsg").html("");
    }
});

/* 비밀번호 변경 저장 function */
function saveMyInfoChgPwd(){
	
	/* form데이터 */
	var param = Utilities.formToMap("frmMain");
	/* id 추가 */
	param.loginId = '<c:out value="${LOGIN_USER.loginId }" />';
	
	
	/* validate 체크*/
	if(!validate(param))
        return;
	
	/* saveMyInfo 메서드 실행 */
	/* url = /myInfo/updatePwd*/
	var url = "<c:url value='${urlPrefix}/updatePwd${urlSuffix}'/>";
	if(!confirm("수정된 데이터를 저장하시겠습니까?"))
	    return ;
	
	/* Ajax */
	Utilities.getAjax(url,param,true,function(data,jqXHR){
	    if(Utilities.processResult(data,jqXHR,"저장 실패했습니다."))
	    {
	        alert("저장되었습니다.");
	        Utilities.closeModal();
	    }
	});
}

/* 비밀번호 변경 Validate function */
function validate(param){
	//현재 비밀번호 null일 경우 false
	if(!param.password){
        alert("현재 비밀번호는 필수 입니다.");
        $("#password").focus();
        return false;
    }
	
	//비밀번호 null일 경우 false
	if(!param.pwd){
        alert("비밀번호는 필수 입니다.");
        $("#pwd").focus();
        return false;
    }
	if(param.password == param.pwd){
		alert("현재비밀번호와 변경할 비밀번호가 동일합니다.");
        $("#pwd").focus();
        return false;
	}
	//확인 null일 경우 false
    if(!param.pwdOk){
        alert("확인은 필수 입니다.");
        $("#pwdOk").focus();
        return false;
    }
 	
	//변경 비밀번호와 변경확인 비밀번호가 다름
    if(param.pwd != param.pwdOk){
        alert("확인이 맞지 않습니다. 다시 확인해주세요.");
        return false;
    }
	
	//비밀번호 형식에 맞지 않으면 false
    if(!Utilities.validatePassword(param.pwd)){
    	$("#pwdMsg").html(Utilities.validatePasswordMsg(param.pwd));
    	return false;
    } 

    return true;
}

/* 비밀번호 변경 모달 취소버튼 function */
function cancelMyInfoChgPwd(){
	Utilities.closeModal();
}
</script>