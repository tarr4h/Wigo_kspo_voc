<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%> 
 <!DOCTYPE html>
<html lang="ko">
<head>

	<title>VOC</title>
	<tiles:insertTemplate template="header.jsp" />
</head>

<body class="bg-light-blue"> <!-- 회색배경 : class="bgGray" -->
<%-- <form id="frmMain" name="frmMain"> --%>
    <!-- module -->
<%--    <div class="mVm1 mLogin1">--%>

        <div class="login-con">
            <div class="login-box">
                <div class="login-img"></div>
                <div class="login-form">
                    <fieldset>
                        <legend>로그인</legend>
                        <div><img src="/static/kspo/images/logo_login.png" alt="KSPO국민체육진흥공단"></div>
                        <h1>KSPO-VOC</h1>
                        <dl>
                            <dt>아이디</dt>
                            <dd>
                                <input type="text" id="loginId" data-enter="signIn" class="it" title="아이디" placeholder="아이디를 입력하세요." value="wigo">
                            </dd>
                            <dt>비밀번호</dt>
                            <dd>
                                <input type="password" data-enter="signIn" id="loginPwd" class="it" title="비밀번호" placeholder="비밀번호를 입력하세요." value="1234">
                            </dd>
                        </dl>
                        <div class="save">
                            <label class="mCheckbox1">
                                <input type="checkbox" id="saveId" title="아이디 저장">
                                <span class="label">아이디 저장</span>
                            </label>
                        </div>
                        <div class="button">
                            <a href="#;" class="btn" data-click="signIn">로그인</a>
                        </div>
                    </fieldset>

                </div>
            </div>
        </div>

<%--    </div>--%>
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
    var cur = new Date();
	var param = {
			loginId : $("#loginId").val(),
			loginPwd : $("#loginPwd").val(),
			current : cur.getTime()
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