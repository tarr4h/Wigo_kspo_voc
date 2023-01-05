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
    <strong class="title">음성파일</strong>
    <button id="btnCancel" data-btn-type="closeModal" class="btnClose"></button>
    <!-- //top -->
    
    <!-- contentsWrap -->
    <div class="contentsWrap">
        <audio id="audio" controls onerror="audioError()" src="${playUrl}" autoplay="autoplay" > 
        </audio>
    </div>
</div>
<script>

function audioError(){
	alert("재생에 실패했습니다.");
    Utilities.closeModal();
}
</script>   