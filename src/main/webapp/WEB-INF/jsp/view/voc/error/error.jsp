<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<div id="skipnavigation">
    <strong class="sound-only">반복영역 건너뛰기</strong>
    <a href="#gnbNav">메뉴 바로가기</a>
    <a href="#body">본문 바로가기</a>
</div>
<!-- //skipnav -->

<div id="wrap">
    <!-- body error -->
    <div id="body">
        <div class="mGrid1">

            <div class="error-con">
                <div class="img-error"></div>
                <div>
                    <ul>
                        <li>해당 서비스 접속이 원활하지 않습니다.</li>
                        <li>잠시 후에 다시 접속해 주시기 바랍니다.</li>
                    </ul>
                    <div class="text-center">
                        <a href="#;" class="btn btn-blue-fill big" data-click="goBack" >Go Home</a>
                    </div>      
                </div>
            </div>  

        </div>
    </div>
    <!-- //body error -->
</div>
	<!-- //wrap -->

<!-- //error -->
<script>
	function goBack() {

		if (parent != window) {
			if (document.referrer == parent.document.referrer
					|| document.referrer == parent.location.href) {
				Utilities.closeModal();
				return;
			}
			history.back();

		} else {
			history.back();
		}

	}
</script>