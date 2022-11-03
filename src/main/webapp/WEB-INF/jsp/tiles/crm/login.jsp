<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%> 
 <!DOCTYPE html>
<html lang="ko">
<head>

	<title>KSPO VOC</title>
	<tiles:insertTemplate template="header.jsp" />
</head>

<body class="bgGray"> <!-- 회색배경 : class="bgGray" -->
<%-- <form id="frmMain" name="frmMain"> --%>
    <!-- module -->
    <div class="mVm1 mLogin1">
    
        <div class="cont">
            <fieldset>
                <legend>로그인</legend>
                <h1>
<%--                    <img src="/static/crm/images/logo_login.png" alt="Create Well Life CERAGEM 통함 CRM">--%>
                    KSPO VOC
                </h1>
                <dl>
                    <dt>아이디</dt>
                    <dd>
                        <input type="text" class="it" data-enter="signIn" id="loginId" name="loginId" class="form-control" placeholder="id" value="wigo" >
                    </dd>
                    <dt>비밀번호</dt>
                    <dd>
                        <input type="password" data-enter="signIn" id="loginPwd" name="loginPwd" value="1qa@WS" class="it">
                    </dd>
                </dl>
                <div class="save">
                    <label class="mCheckbox1">
                        <input type="checkbox" title="아이디 저장" id="saveId">
                        <span class="label">아이디 저장</span>
                    </label>
                </div>
                <div class="button">
                    <a href="#" class="btn" data-click="signIn">로그인</a>
                </div>
            </fieldset>
        </div>
      
    </div>
    <!-- //module -->

<%-- 	 </form> --%>
	<script>
	if(top != window)
		top.location.href = location.href;
// $(document).ready(function(){
// 	if($("#loginId").val())
// 	{
// 		$("#pwd")[0].focus();
// 		$("#saveId").prop("checked",true);
// 	}
// 	else
// 		$("#loginId")[0].focus();
		
// });
function signIn(){
// 	var param = Utilities.formToMap("frmMain");
	var param = {
			loginId : $("#loginId").val(),
			loginPwd : $("#loginPwd").val()
	};
	if(!param.loginId){
		alert("로그인 아이디를 입력해 주세요");
		$("#loginId").focus();
		return false;
	}
	if(!param.loginPwd){
        alert("로그인 아이디를 입력해 주세요");
        $("#loginPwd").focus();
        return false;
    }
	var url = "<c:url value='/login/login${urlSuffix}'/>";
	Utilities.blockUI();
	Utilities.getAjax(url,param,true,function(data,jqXHR){
		Utilities.unblockUI();
        if(Utilities.processResult(data,jqXHR,"로그인에 실패했습니다."))
        {
        	if(window == Utilities.getTopWindow())
            	location.href="/";
            else
            {
                try{Utilities.closeModal()}catch(e){location.href="/";}
            }
        }
    });
	
}
</script>
</body>
</html>