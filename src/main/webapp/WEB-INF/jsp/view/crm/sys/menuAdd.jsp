<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

 <!-- header -->
    <div class="title">
        <h1>메뉴추가</h1>
        <a href="#" data-btn-type="closeModal" class="close">팝업 닫기</a>
    </div>
    <!-- //header -->

    <!-- cont -->
    <div class="cont">
        
        <div class="gTitle1 line">
            <h3 class="mTitle1">메뉴정보</h3>
            <div class="gRt">
                <a href="#" class="mBtn1 primary" id="btnSave">저장</a>
            </div>
        </div>
        <!-- board -->
        <div class="mBoard2">
        <form id="frmMain">
            <input type="hidden" name="useYn" id="useYn" value="Y"/>
            <input type="hidden"  value='<c:out value="${topMenuCd }"/>' readonly="readonly" id="topMenuCd" name="topMenuCd"  placeholder="시스템메뉴">
		    <input type="hidden"  value='<c:out value="${prntsMenuCd }"/>' readonly="readonly" id="prntsMenuCd" name="prntsMenuCd"  placeholder="상위메뉴" />
		    
		    <input type="hidden" value='<c:out value="${menuIcon }"/>' required="required" id="menuIcon" name="menuIcon"  placeholder="메뉴아이콘">	
		    <input type="hidden" value="${empty menuPopupYn ? 'N' :menuPopupYn }" required="required" id="menuPopupYn" name="menuPopupYn"  placeholder="높이">
            <input type="hidden" value='<c:out value="${menuPopupHeight}"/>' required="required" id="menuPopupHeight" name="menuPopupHeight"  placeholder="높이">
            <input type="hidden" value='<c:out value="${menuPopupWidth }"/>' required="required" id="menuPopupWidth" name="menuPopupWidth"  placeholder="너비">
                  	
                 
                  
            <table>
                <caption>메뉴정보</caption>
                <colgroup>
                    <col width="10%">
                    <col width="15%">
                    <col width="10%">
                    <col width="15%">
                    <col width="10%">
                    <col width="15%">
                    <col width="10%">
                    <col width="15%">
                </colgroup>
                <tbody>
                    <tr>
                        <th scope="row" class="left"><span class="iMust">메뉴ID</span></th>
                        <td >
                            <input required="required" class="it" type="text" id="menuCd" name="menuCd" readonly value='<c:out value="${menuCd }"/>' placeholder="menuCd">
                        
                        </td>
                        <th scope="row" class="left"><span class="iMust"> 메뉴명</span></th>
                        <td colspan="6">
                            <input type="text" class="it" required="required" value='<c:out value="${menuNm }"/>' id="menuNm" name="menuNm" placeholder="메뉴명">
                        </td>
                        
                       
                        
                        
                    </tr>
                    <tr>
                    <th scope="row" class="left">메뉴URL</th>
                        <td >
                            <input type="text" class="it" value='<c:out value="${menuUrl }"/>' id="menuUrl" name="menuUrl" placeholder="메뉴 Url">
                        
                        </td>
                     <th scope="row" class="left">메뉴 설명</th>
                        <td colspan="9">
                            <input type="text" class="it" value='<c:out value="${menuExpln }"/>' id="menuExpln" name="menuExpln" placeholder="메뉴설명">
                        </td>
                    </tr>
                    <tr>
                    	 <th scope="row" class="left">메뉴순번</th>
                        <td >
                            <input type="text" class="it" value='<c:out value="${menuOdrg }"/>' readonly required="required" id="menuOdrg" name="menuOdrg"  placeholder="메뉴순번">
                        
                        </td>
                        <th scope="row" class="left">정보변경로그</th>
                        <td >
                            <input type="hidden" id="menuLvlNo" name="menuLvlNo" value='<c:out value="${menuLvlNo }"/>'/>
                            <code:commCode id="chngLogYn" name="chngLogYn" codeCd="S010" selectedValue="${chngLogYn}"/>
<%-- 								<c:out value="${menuLvlNo }"/> --%>
                        </td>
                        <th scope="row" class="left">메뉴표시</th>
                        <td >
                            <code:commCode id="menuShowYn" name="menuShowYn" codeCd="S050" selectedValue="${menuShowYn}"/>
                        
                        </td>
                        <th scope="row" class="left">권한체크</th>
                        <td >
                            <code:commCode id="menuAuthYn" name="menuAuthYn" codeCd="S050" selectedValue="${menuAuthYn}"/>
                        
                        </td>
                        
                        
                          
                    </tr>
                    
                </tbody>
            </table>
            </form>
        </div>
        <!-- //board -->
        
        
        
    </div>
    <!-- //cont -->



<script>

$("#btnSave").click(function(){
	
	var param = Utilities.formToMap("frmMain");
	if(param.menuLvlNo==1)
	{
		param.topMenuCd = param.menuCd;
		param.prntsMenuCd = "";
	}
	param.stat="c";
	if(!validate(param))
		return;
	var url = "<c:url value='${urlPrefix}/addMenu${urlSuffix}'/>";
	Utilities.getAjax(url,param,true,function(data,jqXHR){
		if(Utilities.processResult(data,jqXHR,"메뉴 추가에 실패했습니다."))
		{
			alert("메뉴추가에 성공했습니다.");
			Utilities.getOpener().addMenu(data.result);
			Utilities.closeModal();
		}
	});
});
function validate(param){
	if(!param.menuCd){
		alert("메뉴 아이디는 필수 입니다.");
		$("#menuCd").focus();
		return false;
	}
	if(!param.menuNm){
        alert("메뉴 명은 필수 입니다.");
        $("#menuNm").focus();
        return false;
    }
	if(!param.menuOdrg){
        alert("순번은 필수 입니다.");
        $("#menuOdrg").focus();
        return false;
    }
    if(!Utilities.isNumberOnly(param.menuOdrg)){
        alert("순번은 숫자 형식이어야합니다.");
        $("#menuOdrg").focus();
        return false;
    }
	
	return true;
}
</script>