<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<!-- popup -->
<div class="popup">
	<strong class="title">그룹 정보</strong>
	<button id="btnCancel" data-btn-type="closeModal" class="btnClose"></button>
	<!-- contentsWrap -->
	<div class="contentsWrap">
		<h4>그룹 정보</h4>
        <!-- form start -->
        <form id="frmMain">
			<div class="boardWrite">
				<table>
	          		<colgroup>
						<col width="15%">
						<col width="35%">
					</colgroup>
					<tbody>
			            <tr>
			                <th><label for="grpCd">그룹아이디</label></th>
			                <td>
			                	<input type="text" required="required" value='<c:out value="${group.grpCd }"/>'  class="form-control" id="grpCd" name="grpCd" placeholder="그룹아이디">
			                </td>
			            </tr>
						<tr>
			            	<th><label for="grpNm">그룹명</label></th>
			            	<td >
			                	<input required="required" type="text" data-change="resetCheckId" class="form-control" value='<c:out value="${group.grpNm }" />' id="grpNm" name="grpNm" placeholder="그룹명">
			                </td>
			            </tr>
					</tbody>
				</table>
			</div>  
		</form>
		<!-- //form end -->
		<div class="btnPBWrap">
			<button id="btnSave" class="btnBasic _em">저장</button>
			<button id="btnCancel" data-btn-type="closeModal" class="btnBasic">닫기</button>
		</div>
	</div>
	<!-- //contentsWrap -->
</div>
<!-- //popup -->
            <script>
           
           
            $("#btnSave").click(function(){
                
                var param = Utilities.formToMap("frmMain");
                if(!validate(param))
                    return;
                var url = "<c:url value='${urlPrefix}/addGroup${urlSuffix}'/>";
                Utilities.getAjax(url,param,true,function(data,jqXHR){
                    if(Utilities.processResult(data,jqXHR,"그룹 추가에 실패했습니다."))
                    {
                        alert("그룹추가에 성공했습니다.");
//                         opener.addGroup(data.result);
//                         top.close();
						Utilities.getOpener().addGroup(data.result);
						Utilities.closeModal();
                    }
                });
            });
            function validate(param){
                if(!param.grpCd){
                    alert("그룹 아이디는 필수 입니다.");
                    $("#userCd").focus();
                    return false;
                }
                  if(!param.grpNm){
                    alert("그룹 명은 필수 입니다.");
                    $("#userName").focus();
                    return false;
                }
                
                return true;
            }
            
            $(document).ready(function(){
                
            });
            </script>