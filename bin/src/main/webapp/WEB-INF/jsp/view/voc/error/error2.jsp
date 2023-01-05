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
				<div class="contentsWrap">
					<!-- error -->
					<div class="errorWrap">
						<!-- contWrap -->
						<div class="contWrap"> 
							<span class="imgError"></span>

							<div class="infoWrap">
								<p class="alert">페이지에 오류가 발생하였습니다.</p>
								<p class="info">죄송합니다. 요청하신 인터넷주소(URL)를 찾을 수 없습니다.</p>
								<p class="info">찾으시는 URL이 변경되었거나, 옮겨졌거나, 또는 삭제되었을 수 있습니다. URL이 잘못 입력되었는지 다시 한번 확인해 보시기 바랍니다.</p>
							</div>

							<div class="btnArea">
								<button type="button" data-click="goBack"  class="btnBasic">이전페이지</button>
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