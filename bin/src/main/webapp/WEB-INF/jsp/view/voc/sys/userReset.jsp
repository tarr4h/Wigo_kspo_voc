<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%> 
<!-- /.card -->
<!-- Horizontal Form -->

<div class="popup">
    <!-- top -->
    <strong class="title">비밀번호 초기화 팝업</strong>
    <button type="button" class="btnClose" data-btn-type="closeModal"></button>
    <!-- //top -->

    <div class="contentsWrap">
        <h4>비밀번호 초기화</h4>
	    <form role="form" id="frmPwd">
	        <div class="boardWrite">
	             <table>
	                 <colgroup>
	                     <col width="30%">
	                     <col width="70%">
	                 </colgroup>
	                 <tbody>
	                     <tr>
	                         <th class="asterisk">비밀번호</th>	       
	                         <td><input type="password" class="medium" id="loginPwd" data-enter="updatePwd" name="loginPwd" placeholder="비밀번호"></td>
	                     </tr>
	                     <tr>
	                         <th class="asterisk">확인</th>
	                         <td><input type="password" class="medium" id="pwdCfrm" data-enter="updatePwd" name="pwdCfrm" placeholder="비밀번호 재입력"></td>
	                     </tr>
	                 </tbody>
	             </table>
	             <p style="color:red">※비밀번호는 영문+숫자+특수문자 조합 10자리 이상</p>
	        </div>
	    </form>
	    <div class="btnArea center"> 
           <button type="button" data-click="updatePwd" id="btnSaveList" class="btnBasic _em">저장</button>
           <button type="button" data-btn-type="closeModal" id="btnCancel" class="btnBasic">닫기</button>
        </div>
    </div>
    <div id="pwdMsg" name="pwdMsg" class="center">
    
    </div>
</div>

<script>


$("#loginPwd").keyup(function(){
	$("#pwdMsg").html(Utilities.validatePasswordMsg($("#loginPwd").val()));
	if(Utilities.isEmpty($("#loginPwd").val())){
		$("#pwdMsg").html("");
    }
});

function updatePwd(){

	var pwd = Utilities.formToMap("frmPwd");
    if(!pwd.loginPwd){
        alert("변경하려는 비밀번호를 입력해주세요.");
        $("#loginPwd").focus();
        return false;
    }
    if(!pwd.pwdCfrm){
        alert("변경하려는 비밀번호를 재입력해주세요.");
        $("#pwdCfrm").focus();
        return false;
    }

    if(pwd.loginPwd != pwd.pwdCfrm){
    	alert("비밀번호가 맞지 않습니다. 다시확인해주세요.");
        $("#pwdCfrm").focus();
        return false;
    }

    if(!Utilities.validatePassword(pwd.loginPwd)){
    	$("#pwdMsg").html(Utilities.validatePasswordMsg(pwd.loginPwd));
    	return false;
    } 
    
    var param  = {
            loginId : '<c:out value="${loginId}"/>',
            loginPwd : pwd.loginPwd
    };
    var url = "<c:url value='${urlPrefix}/user/resetPassword${urlSuffix}'/>";
    Utilities.getAjax(url,param,true,function(data,jqXHR){
        if(Utilities.processResult(data,jqXHR,"암호초기화에 실패했습니다."))
        {
            alert("암호초기화에  성공했습니다.");
//             grdList.removeCheckRow();
            Utilities.closeModal();
        }
    });
}
</script>