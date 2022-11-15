<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VOC</title>

    <!-- ======================= css ===================== -->
<!--     <link rel="stylesheet" href="/static/crm/css/style.css"> -->
    <tiles:insertTemplate template="header.jsp" />
</head>
<body>
    <header class="header">
        <div class="container">
            <div class="header_menu">
                <a href="#" class="full_menu" id="full-toggle"><img src="/static/crm/images/menu.png" alt="fullmenu"></a>
                <h1><a href="/"><img src="/static/crm/images/logo.png" alt=""></a></h1>
    


                <!-- ===================== gnb ===================-->
                <ul class="gnb">
                	<c:forEach var="topLevelMenu" items="${ menuTree.children}">
					<c:if test='${topLevelMenu.menuShowYn eq "Y" }'>
					<li><a href="#" class="gnb_link">${topLevelMenu.menuNm }</a>
                        <ul class="sub">
                        	<li>
                        	<c:forEach var="subMenu" items="${ topLevelMenu.children}">
							<c:if test='${subMenu.menuShowYn eq "Y" }'>
								<a href="#" data-click="showToplevelMenu" class="sub_link" data-top-menu-cd="${subMenu.topMenuCd }" data-menu-cd= "${subMenu.menuCd }">${subMenu.menuNm }</a> 
							</c:if>
							</c:forEach>
							</li>
                        </ul>
                    </li>
					</c:if>
					</c:forEach>
					
                </ul>
            </div>
            <div class="header_user">
                <img src="/static/crm/images/user1.png" alt="">
                <p><c:out value="${LOGIN_USER.userNm }" />(<c:out value="${LOGIN_USER.loginId }" />)님 환영합니다.</p>
                <button class="btn_login" data-click="logout">로그아웃</button>
            </div>
        </div>

        <ul class="header_side">
        	<c:set value="1" var="cnt" />
        	<c:forEach var="topLevelMenu" items="${ menuTree.children}">
				<c:if test='${topLevelMenu.menuShowYn eq "Y" }'>
					<c:forEach var="subMenu" items="${ topLevelMenu.children}">
						<c:if test='${subMenu.menuShowYn eq "Y" }'>
						<c:set var="subMenuData">data-top-menu-cd="${ subMenu.topMenuCd}" data-menu-cd="${subMenu.menuCd }" data-menu-lvl-no="${subMenu.menuLvlNo }" data-menu-url="${subMenu.menuUrl }"</c:set>
						<li data-menu-depth="2" style="<c:if test='${cnt > 1 }'><c:set value="2" var="cnt" />display:none</c:if>" ${subMenuData }>
                			<a data-click="showSubMenu" href="#" class="side_depth1 " ${subMenuData }>${subMenu.menuNm }</a>
                			<c:if test="${!empty subMenu.children}">
                			<ul data-menu-depth="3" class="sub_depth2" style="display:none" ${subMenuData }>
                				<c:forEach var="menu3" items="${ subMenu.children}">
                				<c:set var="menu3Data">data-top-menu-cd="${ menu3.topMenuCd}" data-menu-cd="${menu3.menuCd }" data-menu-lvl-no="${menu3.menuLvlNo }" data-menu-url="${menu3.menuUrl }"</c:set>
                					<c:if test='${menu3.menuShowYn eq "Y" }'>
                			 <li ${menu3Data }>
                			 	<a href="#" data-click="showThirdMenu" class="side_depth2" ${menu3Data }>${menu3.menuNm }</a>
                			 	<c:if test="${!empty menu3.children}">
                			 		<ul class="sub_depth3" ${menu3Data }> 
                			 		<c:forEach var="menu4" items="${ menu3.children}">
                			 			<c:if test='${menu4.menuShowYn eq "Y" }'>
                			 			<c:set var="menu4Data">data-top-menu-cd="${ menu4.topMenuCd}" data-menu-cd="${menu4.menuCd }" data-menu-lvl-no="${menu4.menuLvlNo }" data-menu-url="${menu4.menuUrl }"</c:set>
                			 			<li ${menu4Data }>
                			 				<a href="#" data-click="showFourthMenu" class="side_depth3" ${menu4Data }>${menu4.menuNm }</a>
                			 			</li>
                			 			</c:if>
                			 		</c:forEach>
                			 		</ul>
                			 	</c:if>
                			 </li>		
                					</c:if>
                				</c:forEach>
                			</ul>
                			</c:if>
            			</li>
						</c:if>
					</c:forEach>
				</c:if>
			</c:forEach>
        </ul>
    </header>



    <div class="main">
        <div class="container main_grid">
            
<!--             콘텐츠 -->
            <tiles:insertAttribute name="content" />
        </div>

        <div class="wedget">
            <a href="#">
                <img src="/static/crm/images/widget1.png" alt="">
                <span>위젯1</span>
            </a>
            <a href="#">
                <img src="/static/crm/images/widget2.png" alt="">
                <span>위젯2</span>
            </a>
            <a href="#">
                <img src="/static/crm/images/widget3.png" alt="">
                <span>위젯3</span>
            </a>
        </div>

<!--         <div class="memo"> -->
<!--             <a href="#" class="memo_toggle" id="memo-toggle"> -->
<!--                 <img src="images/arrow.png" alt=""> -->
<!--             </a> -->

<!--             <div class="memo_container"> -->
<!--                 <form action=""> -->
<!--                     <div class="memo_txt"> -->
<!--                         <p>메모</p> -->
    
<!--                         <div class="memo_btn"> -->
<!--                             <button class="memo_new">신규</button> -->
<!--                             <button class="memo_del">삭제</button> -->
<!--                             <button class="memo_ok">저장</button> -->
<!--                         </div> -->
<!--                     </div> -->
<!--                     <div class="memo_write"> -->
<!--                         <textarea></textarea> -->
<!--                     </div> -->
<!--                 </form> -->
<!--             </div> -->
<!--         </div> -->

        
    </div>


    <!-- ======================== js ================ -->
    <script>
    
    function showToplevelMenu(element,data){
    	$("li[data-menu-depth=2]").hide();
    	$("li[data-menu-depth=2][data-top-menu-cd="+data.topMenuCd+"]").show();
    	
    	const toggleHeader = $('#full-toggle'), sideHeader = $('.header_side');
		
    	sideHeader.addClass('show-side');
        toggleHeader.addClass('open-side');
        showSecondMenu(data.menuCd);
    }
    function showSubMenu(element,data){
    	showSecondMenu(data.menuCd);
    	if(data.menuUrl){
    		goMenu(data);
    	}
    }
    function goMenu(menu){
    	var url = menu.menuUrl;
    	if(!url)
    		return;
//     	if(menu.menuPopupYn == "Y"){
//     		return;
//     	}
		openContentsTab(menu);
    }
    function openContentsTab(menu){
    	location.href= "/" + menu.menuUrl;
    }
    function showSecondMenu(menuCd){
    	var $li = $("li[data-menu-depth=2][data-menu-cd="+menuCd+"]");
    	$("ul[data-menu-depth=3]").hide();
    	$("ul[data-menu-depth=3][data-menu-cd="+menuCd+"]").show();
    	
    	$(".side_depth1").removeClass("active");
    	$(".side_depth1[data-menu-cd="+menuCd+"]").addClass("active");
//     	$(".sub_depth2").removeClass("active");
//     	$li.find(".sub_depth2").addClass("active");
    	
    }
	function showThirdMenu(element,data){
		goMenu(data);
    }
    function showFourthMenu(element,data){
    	goMenu(data);
    }
//     function recalGridList(){
// 		$("[data-type=grid]").each(function(){
// 			var grdId = $(this).attr("data-grid-id");
// 			if(grdId){
// 				window[grdId].refreshLayout();
// 			}
// 		});
//     }
    (function($) {
        jQuery(document).ready(function() {

            //memo_container
            const toggleMemo = $('#memo-toggle'),
                    memoPanel = $('.memo_container');

                    toggleMemo.on('click', () => {
                    memoPanel.toggleClass('show-memo');
                    toggleMemo.toggleClass('open-memo');
                    })

            
            //main left side
            const toggleSide = $('#side-toggle'),
                    sidePanel = $('.main_side');

                    toggleSide.on('click', () => {
                    sidePanel.toggleClass('show-side');
                    toggleSide.toggleClass('open-side');
//                     setTimeout(function(){recalGridList()}, 500);
                    })


            //header top menu
            const toggleHeader = $('#full-toggle'),
                    sideHeader = $('.header_side');

                    toggleHeader.on('click', () => {
                    sideHeader.toggleClass('show-side');
                    toggleHeader.toggleClass('open-side');
                    })



        })
     })(jQuery)
    </script>
</body>
</html>