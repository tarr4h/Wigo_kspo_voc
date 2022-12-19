<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!-- wrap -->
	<div id="wrap">
		<!-- header -->
		<header id="header" class="sub">
			
		</header>
		<!-- //header -->


		<!-- container -->
		<div id="container">

				<!-- contentsWrap -->
				<div class="contentsWrap" style="padding-top: 100px;">
					<!-- error -->
					<div class="errorWrap">
						<!-- contWrap -->
						<div class="contWrap2"> 
							<span class="imgError2"></span>
							<div class="infoWrap2">
								<p class="info2">해당 서비스 접속이 원활하지 않습니다.</p>
								<p class="info2">잠시 후에 다시 접속해 주시기 바랍니다.</p>
							</div>

							<div class="errorBtnArea">
								<button type="button" data-click="goBack"  class="btnSearch btnError"><span class="imgHome"></span>go home</button>
							</div>
						</div>
					</div>
					<!-- //error -->

				</div>
				<!-- //contentsWrap -->
			
		</div>
		<!-- //container -->
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