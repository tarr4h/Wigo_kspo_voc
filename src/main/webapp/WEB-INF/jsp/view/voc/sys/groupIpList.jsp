<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%> 


 <div class="title">
        <h1>${refTpCdNm } IP 설정</h1>
        <a href="#" data-btn-type="closeModal"  class="close">팝업 닫기</a>
    </div>

<div class="cont">
    <div class="gTitle1 line">
            <h3 class="mTitle1">${refIdNm } - IP 설정 </h3>
            <div class="gRt">
<!--                 <a href="#" class="mBtn1 lWhite">승인</a> -->
<!--                 <a href="#" class="mBtn1 lWhite">중지</a> -->
<!--                 <a href="#" class="mBtn1 gray">승인요청</a> -->
<!--                 <a href="#" class="mBtn1 gray">중지요청</a> -->
<!--                 <a href="#" data-grid-id="grdList" data-click="saveGroupChecked" id="btnSaveList"  class="mBtn1 primary">저장</a> -->
<code:btnGroup name="GroupIp" />
            </div>
        </div>
        <div id="divGrid"  data-grid-id="grdList" style="height: 650px"
         data-get-url="<c:url value='${urlPrefix}/group/getGroupIpList${urlSuffix}'/>"
          data-pagenation="N" data-type="grid"
        data-tpl-url="<c:url value='/static/gridTemplate/system/ipRel.xml'/>">
        </div>
</div>


<script>


function onGridLoad(){
    search();
}
function search()
{
    var url = "<c:url value='${urlPrefix}/group/getGroupIpList${urlSuffix}'/>";
    var param = {   refId : '<c:out value="${refId}"/>',
    		      refTpCd : '<c:out value="${refTpCd}"/>'
                };
    grdList.loadUrl(url,param);
}
function newGroupIp(){
    grdList.addRow({
    	refId : '${refId}',
    	refTpCd : '${refTpCd}'
    });
}

function saveGroupIp(){
	 var saveJson = grdList.getSaveJson();
	    if(saveJson.length){
	        if(!confirm("수정된 데이터를 저장하시겠습니까?"))
	            return ;
	        if(!validateJson(saveJson))
	        	return;
	        var url = "<c:url value='${urlPrefix}/saveGroupIpList${urlSuffix}'/>";
	        Utilities.getAjax(url,saveJson,true,function(data,jqXHR){
	            if(Utilities.processResult(data,jqXHR,"저장에 실패했습니다."))
	            {
	                alert("저장에  성공했습니다.");
	                grdList.reload();
	            }
	        });
	    }
	    else{
	        alert("수정된 데이터가 없습니다.");
	    }
}

function removeGroupIp(){
	var saveJson = grdList.getCheckedJson();
    if(saveJson.length ==0)
    {
        alert("체크된 데이터가 존재하지 않습니다.");
        return;
    }
    else
    {
        if(!confirm("데이터를 삭제하면 복구할  수 없습니다. 계속하시겠습니까?"))
            return ;
        const list = [];
        for(let i=0;i<saveJson.length;i++){
        	if(saveJson[i].stat != 'c')
        		list.push(saveJson[i]);
        }
        if(list.length){
        	var url = "<c:url value='${urlPrefix}/removeGroupIpList${urlSuffix}'/>";
            Utilities.getAjax(url,list,true,function(data,jqXHR){
            if(Utilities.processResult(data,jqXHR,"그룹 삭제에 실패했습니다."))
            {
                alert("IP 삭제에  성공했습니다.");
                grdList.removeCheckRow();
            }
            });	
        } else {
        	alert("IP 삭제에  성공했습니다.");
            grdList.removeCheckRow();
        }
        
    }
    
}

function validateJson(list){
	for(let i=0;i<list.length;i++){
		const json = list[i];
		if(!validate(json)){
			return false;
		}
	}
	return true;
	
}

function validate(json){
	const row = json.itemIndex+1;
	if(!json.ipAddr)
	{
		alert(row + "행의 IP주소를 입력하세요");
		return false;
	}
	if(!json.useStaYmd)
    {
		alert(row + "행의 사용시작일을 입력하세요");
        return false;
    }
	if(!json.useEndYmd)
    {
		alert(row + "행의 사용종료일을 입력하세요");
        return false;
    }
	if(json.useStaYmd>json.useEndYmd)
    {
		alert(row + "행의 사용시작일은 종료일보다 이전이어야 합니다.");
        return false;
    }
	return true;
}
</script>