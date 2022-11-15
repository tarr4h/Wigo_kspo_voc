<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>
<div class="card card-info">
              <div class="card-header">
                <h3 class="card-title">사용자 정보</h3>
              </div>
              <!-- /.card-header -->
              <!-- form start -->
              <form class="form-horizontal" id="frmMain">
                <input type="hidden" name="userCd" id="userCd" value="${user.userCd }" />
                <div class="card-body">
                 <div class="form-group row">
                    <label for="userName" class="col-sm-2 col-form-label right">사용자명</label>
                    <div class="col-sm-10">
                      <input type="text" required="required" value='<c:out value="${user.userName }"/>'  class="form-control" id="userName" name="userName" placeholder="사용자명">
                    </div>
                  </div>
                  
                  <div class="form-group row">
                    <label for="loginId" class="col-sm-2 col-form-label right">로그인아이디</label>
                    <div class="input-group col-sm-10">
                      <input type="hidden" id="chkId" />
                      <input required="required" type="text" data-change="resetCheckId" class="form-control" value='<c:out value="${user.loginId }" />' id="loginId" name="loginId" placeholder="로그인아이디">
	                  <div class="input-group-append">
	                    <span class="input-group-text" id='checkDupl' data-click="checkDupl"><i class="fas fa-check"></i> 중복체크</span>
	                  </div>
	                </div>
                  </div>
                  <div class="form-group row">
                    <label for="loginPwd" class="col-sm-2 col-form-label right">암호</label>
                    <div class="col-sm-10">
                      <input type="password" autocomplete="false" required="required" maxLength="16" class="js-mytooltip-pw form-control" id="loginPwd" name="loginPwd" placeholder="암호" 
                      data-mytooltip-direction="bottom"
                      data-mytooltip-dinamic-content="true" 
                      data-mytooltip-action="focus" 
                      data-mytooltip-animate-duration="0"

                      />
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="loginPwdCfg" class="col-sm-2 col-form-label right">암호확인</label>
                    <div class="col-sm-10">
                      <input type="password" autocomplete="false" required="required" maxLength="16" class="form-control" id="loginPwdCfg" name="loginPwdCfg" placeholder="암호확인">
                    </div>
                  </div>
                  <div class="form-group row" >
                    <label for="pwdExpireDt" class="col-sm-2 col-form-label right">암호만료일</label>
                    <div class="col-sm-10">
                      <input type="email" value='<c:out value="${user.pwdExpireDt }"/>' data-type="date" data-button="Y" required class="form-control" id="pwdExpireDt" name="pwdExpireDt"  placeholder="암호만료일">
                    </div>
                  </div>
                  <div class="form-group row" >
                    <label for="userEmail" class="col-sm-2 col-form-label right">이메일</label>
                    <div class="col-sm-10">
                      <input type="email" value='<c:out value="${user.userEmail }"/>' required class="form-control" id="userEmail" name="userEmail"  placeholder="이메일">
                    </div>
                  </div>
                  <div class="form-group row" >
                    <label for="mobileNo" class="col-sm-2 col-form-label right">전화</label>
                    <div class="col-sm-10">
                      <input type="text" value='<c:out value="${user.mobileNo }"/>' required class="form-control" id="mobileNo" name="mobileNo"  placeholder="전화">
                    </div>
                  </div>
                </div>  
                <!-- /.card-body -->
                <div class="card-footer">
                  <a id="btnCancel" href="#;" class="btn btn-default float-right"><i class="fas fa-ban"></i> 닫기</a>
                  <a id="btnSave" href="#;" class="btn btn-info float-right"><i class="fas fa-save"></i> 저장</a>
                </div>
                <!-- /.card-footer -->
              </form>
            </div>
            <script>
            function resetCheckId(){
            	$("#chkId").val("");
            }
            function checkDupl(){
            	var id = $("#loginId").val();
            	if(!id){
            		alert("아이디를 입력해 주세요");
            		return false;
            	}
            	var param = {
            			loginId: id
            	};
            	var url = "<c:url value='${urlPrefix}/getUser${urlSuffix}'/>";
                Utilities.getAjax(url,param,true,function(data,result){
                    if(data && data.loginId == id)
                    {
                    	alert("이미 사용중인 아이디 입니다.");
                    	return false;
                    }
                    alert("사용할 수 있는 아이디 입니다.");
                    $("#chkId").val(id);
                });
            }
            $("#btnCancel").click(function(){
            	top.close();
            });
            $("#btnSave").click(function(){
            	
            	var param = Utilities.formToMap("frmMain");
            	if(param.userLevel==1)
            	{
            		param.rtUserCd = param.userCd;
            		param.uprUserCd = "";
            	}
            	
            	if(!validate(param))
            		return;
            	var url = "<c:url value='${urlPrefix}/addUser${urlSuffix}'/>";
            	Utilities.getAjax(url,param,true,function(data,jqXHR){
            		if(Utilities.processResult(data,jqXHR,"사용자 추가에 실패했습니다."))
            		{
            			alert("사용자추가에 성공했습니다.");
            			opener.addUser(data.result);
            			top.close();
            		}
            	});
            });
            function validate(param){
            	if(!param.loginId){
            		alert("사용자 아이디는 필수 입니다.");
            		$("#userCd").focus();
            		return false;
            	}
            	if($("#chkId").val() != $("#loginId").val()){ 
            		alert("아이디 중복체크를 먼저 해주세요");
                    $("#checkDupl").focus();
                    return false;
            	}
            	 
            	if(!param.userName){
                    alert("사용자 명은 필수 입니다.");
                    $("#userName").focus();
                    return false;
                }
                if(!param.loginPwd){
                	alert("암호를 입력해 주세요");
                	$("#loginPwd").focus();
                    return false;
                }
            	if(!Utilities.validatePassword(param.loginPwd))
            	{
            		alert("암호복잡도가 맞지 않습니다.");
            		$("#loginPwd").focus();
            		return false;
            	}
            	if(param.loginPwd != param.loginPwdCfg){
                    alert("암호가 동일하지 않습니다.");
                    $("#loginPwdCfg").focus();
                    return false;
                }
            	return true;
            }
            
            $(document).ready(function(){
            	
            	$("#loginPwd").off("focus").on("focus", function() {
                    var value = $(this).val();
                    $('.js-mytooltip-pw').myTooltip('updateContent', Utilities.validatePasswordMsg(value));
                });
                $("#loginPwd").off("click").on("click", function() {
                    var value = $(this).val();
                    if (!Utilities.isEmpty(value)) {
                        $('.js-mytooltip-pw').myTooltip('updateContent', Utilities.validatePasswordMsg(value));
                    }
                });
                $("#loginPwd").off("keyup").on("keyup", function() {
                    $("#loginPwd").blur();
                    $("#loginPwd").focus();
                });
                
                $('.js-mytooltip-pw').myTooltip({
                    'offset' : 30,
                    'theme' : 'light',
                    'customClass' : 'mytooltip-content',
                    'content' : '<p>t</p>'
                });

            	
            });
            </script>