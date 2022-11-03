<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>MR.VOC</title>
<tiles:insertTemplate template="header.jsp" />
</head>

<body>
	
<!-- skipnavi -->
<div id="skipnavigation">
	<strong class="sound-only">반복영역 건너뛰기</strong>
	<a href="#gnbNav">메뉴 바로가기</a>
	<a href="#body">본문 바로가기</a>
</div>
<!-- //skipnav -->

<div id="wrap">

	<!-- header -->
	<div id="header">
<!-- 		<a href="#;" data-click="showLeftMenu" class="menu">메뉴보기</a> -->
		<h1>
			<a href="/" style="color: #f8f8f8;">
<%--				<img src="/static/crm/images/logo.png" alt="CERAGEM 통합고객">--%>
				KSPO VOC
			</a>
		</h1>
		<!-- gnb -->
		<div id="gnbNav" class="mGnb">
			<ul>
				<c:forEach var="topLevelMenu" items="${ menuTree.children}">
					<c:if test='${topLevelMenu.menuShowYn eq "Y" }'>
					<li>
						<a href="#;" class="dep1">${topLevelMenu.menuNm }</a>
						<div class="dep2">
							<ul>
								<c:forEach var="subMenu" items="${ topLevelMenu.children}" varStatus="status">
								<c:if test='${subMenu.menuShowYn eq "Y" }'>
								<li>
									<strong class="tit">${subMenu.menuNm }</strong>
									<c:forEach var="menu3" items="${subMenu.children }" >
									<c:if test='${menu3.menuShowYn eq "Y" }'>
									
									<c:set var="menu3Data">data-top-menu-cd="${ menu3.topMenuCd}" data-menu-cd="${menu3.menuCd }" data-menu-lvl-no="${menu3.menuLvlNo }" data-menu-url="${menu3.menuUrl }" data-menu-nm="${menu3.menuNm }"</c:set>
									
									
<!-- 									<div class="ls"><button class="iFavor1 active"></button> <a href="#;">통합고객 기본정보</a></div> -->
									<div class="ls"><button data-click="togMyMenu" data-btn-type="myMenuBtn" ${ menu3Data} class="iFavor1 ${menu3.wdgtYn eq 'Y' ? 'active' : '' }"></button><a href="#;" data-click="showThirdMenu" ${menu3Data }>${menu3.menuNm }</a></div>
									</c:if>
									</c:forEach>



                				

								</li>
								<c:if test="${status.count % 5 == 0 && status.count>0 && status.count != topLevelMenu.children.size() }" >
						<c:out value="</ul><ul>" escapeXml="false"/>		
								</c:if>
								</c:if>
								</c:forEach>
							</ul>
						</div>
                        
                    </li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		<!-- //gnb -->
		<div class="gRt">
			<span class="log"><c:out value="${LOGIN_USER.userNm }" />(<c:out value="${LOGIN_USER.loginId }" />)님 환영합니다.</span>
			<a href="#;" class="mBtn1 s lWhite" data-click="logout">로그아웃</a>
		</div>
	</div>
	<!-- //header -->

	<!-- midTab -->
	<div class="mMidTab">
		<div class="swiper" id="swiperTab">
			<div class="swiper-wrapper" id="menuTabWrap">
			
			</div>
			<div class="swiper-button-next"></div>
			<div class="swiper-button-prev"></div>
<!-- 			<div class="swiper-scrollbar"></div> -->
			<div class="gRt">
				<a href="#;" data-click="closeAllTabs" class="mBtn1 s lWhite">전체닫기</a>
			</div>
		</div>
	</div>
	<!-- //midTab -->

	<div id="body">
		<div class="mWidget1">
			<a href="#sFavor1" class="btn" data-click="showMyMenu">마이메뉴</a>
			<div id="jsFavor1" class="list mCustomScrollbar">
			<strong class="ti">마이메뉴</strong>
				<ul id="ulMyMenu">
				
				<c:forEach var="menu3" items="${ wdgtList}" varStatus="status">
					<c:set var="menu3Data">data-top-menu-cd="${ menu3.topMenuCd}" data-menu-cd="${menu3.menuCd }" data-menu-lvl-no="${menu3.menuLvlNo }" data-menu-url="${menu3.menuUrl }" data-menu-nm="${menu3.menuNm }"</c:set>
					<li ${menu3Data}><a href="#;"   data-click="showThirdMenu" ${menu3Data} >${menu3.menuNm }</a> <button class="delete" data-click="removeWdgt" ${ menu3Data}>삭제</button></li>
				</c:forEach>	

				</ul>
			</div>
		</div>
		

		<div id="divMain">
			 
		</div>
		<!--
			<a href="#;" class="mBtn1">검색</a>
		-->
	</div>
	<!-- // footer -->

</div>


<div id="jsMenu1" class="lMenu">
	<div class="modal"></div>
	<div class="con mCustomScrollbar">
		<ul>
			<c:set value="1" var="cnt" />
        	<c:forEach var="topLevelMenu" items="${ menuTree.children}">
				<c:if test='${topLevelMenu.menuShowYn eq "Y" }'>
					<c:forEach var="subMenu" items="${ topLevelMenu.children}">
						<c:if test='${subMenu.menuShowYn eq "Y" }'>
						<c:set var="subMenuData">data-top-menu-cd="${ subMenu.topMenuCd}" data-menu-cd="${subMenu.menuCd }" data-menu-lvl-no="${subMenu.menuLvlNo }" data-menu-url="${subMenu.menuUrl }" data-menu-nm="${subMenu.menuNm }"</c:set>
						<li data-menu-depth="2" style="<c:if test='${cnt > 1 }'><c:set value="2" var="cnt" />display:none</c:if>" ${subMenuData }>
                			<a data-click="showSubMenu" href="#;" class="dep1" ${subMenuData }>${subMenu.menuNm }</a>
                			<c:if test="${!empty subMenu.children}">
                			<ul data-menu-depth="3" ${subMenuData }>
                				<c:forEach var="menu3" items="${ subMenu.children}">
                				<c:set var="menu3Data">data-top-menu-cd="${ menu3.topMenuCd}" data-menu-cd="${menu3.menuCd }" data-menu-lvl-no="${menu3.menuLvlNo }" data-menu-url="${menu3.menuUrl }" data-menu-nm="${menu3.menuNm }"</c:set>
                					<c:if test='${menu3.menuShowYn eq "Y" }'>
                			 <li ${menu3Data }>
                			 	<a href="#;" data-click="showThirdMenu" class="" ${menu3Data }>${menu3.menuNm }</a>
                			 	<c:if test="${!empty menu3.children}">
                			 		<ul class="" ${menu3Data }> 
                			 		<c:forEach var="menu4" items="${ menu3.children}">
                			 			<c:if test='${menu4.menuShowYn eq "Y" }'>
                			 			<c:set var="menu4Data">data-top-menu-cd="${ menu4.topMenuCd}" data-menu-cd="${menu4.menuCd }" data-menu-lvl-no="${menu4.menuLvlNo }" data-menu-url="${menu4.menuUrl }" data-menu-nm="${menu4.menuNm }"</c:set>
                			 			<li ${menu4Data }>
                			 				<a href="#;" data-click="showFourthMenu" class="" ${menu4Data }>${menu4.menuNm }</a>
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















	</div>
</div>
<!-- //menu -->
<script>
	<%--	test for vim (tarr4h)   --%>
	$(".mGnb").on("click", "li", function(){
		// console.log("hover click change");

		let $target = $(this).find('div');
		let $css = $target.css('display');

		if($css == 'none'){
			$target.css('display', 'block');
		} else {
			$target.css('display', 'none');
		}
	})




window["mainIndex"] = true;
let _TAB_HIST = [];
var _TAB_SLIDE = new Swiper("#swiperTab", {
	slidesPerView: "auto",
// 	centeredSlides: true,
	navigation: {
		nextEl: ".mMidTab .swiper-button-next",
		prevEl: ".mMidTab .swiper-button-prev",
	},
// 	scrollbar: {
// 	    el: '.swiper-scrollbar',
// 	    draggable: true,
// 	  },
});


function showToplevelMenu(element,data){
	$("li[data-menu-depth=2]").hide();
	$("li[data-menu-depth=2][data-top-menu-cd="+data.topMenuCd+"]").show();
	
// 	const toggleHeader = $('#full-toggle'), sideHeader = $('.header_side');
	
// 	sideHeader.addClass('show-side');
//     toggleHeader.addClass('open-side');
	$("ul[data-menu-depth=3][data-menu-cd]").hide();
	$("li[data-menu-depth=2][data-menu-cd]").removeClass("active");
    showSecondMenu(data.menuCd,true);
}
function showSecondMenu(menuCd,forceOpen){
	
	
	let ul = $("ul[data-menu-depth=3][data-menu-cd="+menuCd+"]");
	let li = $("li[data-menu-depth=2][data-menu-cd="+menuCd+"]");
	if(ul.length){
		if(li.hasClass("active")){
			if(!forceOpen){
				ul.slideUp(function(){
					li.removeClass("active");
					});	
			}
			
		}else {
			li.addClass("active");
			ul.slideDown();
		}
	}
	
}
function showThirdMenu(element,data){
	goMenu(data);
}
function showFourthMenu(element,data){
	goMenu(data);
}
function showSubMenu(element,data){
	showSecondMenu(data.menuCd);
	if(data.menuUrl){
		goMenu(data);
	}
}
function goMenu(menu){
	let url = menu.menuUrl;
	if(!url)
		return;
// 	if(menu.menuPopupYn == "Y"){
// 		return;
// 	}
// 	$("#gnbNav").find(".dep2").css("display","none");
	$("#jsMenu1").removeClass("active");
	openContentsTab(menu);
	
}



function openContentsTab(menu){
	let id = menu.menuCd;
	let url =  "/" + menu.menuUrl;
	let title = menu.menuNm;
	openMenuTab(id,title,url,false);
// 	location.href= "/" + menu.menuUrl;
}

function openMenuTab(id,title,url,disableClose){
	var div = findTab(id);
	if(!div)
		div = createTab(id,title,url,!!disableClose);
	activeTab(id);
}
function removeTabHist(id){
	for(var i=0;i<_TAB_HIST.length;i++)
	{
		if(_TAB_HIST[i] == id)
		{
			_TAB_HIST.splice(i,1);
			break;
		}
	}
}
function addTabHist(id){
	removeTabHist(id);
	_TAB_HIST.unshift(id);
}
function removeTab(id){
// 	const div = findTab(id);
// 	if(div)
// 	{
// 		div.remove();
// 	}
	const idx = findIndex(id);
	if(idx> 0)
		_TAB_SLIDE.removeSlide(idx);
	const ifm = findIframe(id);
	ifm.remove();
	removeTabHist(id);
	if(_TAB_HIST.length)
		activeTab(_TAB_HIST[0]);	
	
}
function createTab(id,title,url,disableClose){
	let btn = disableClose ? '' : $('<button class="delete" data-menu-cd="'+id+'" data-btn-type="removeTab">삭제</button>');
	let divTitle = $('<a href="#;" data-btn-type="activeTab" data-menu-cd="'+id+'">'+title+'</a>');
		let html =$('<div class="swiper-slide" id="" data-tab-type="menuTab" data-menu-cd="'+id+'"></div>');
	html.append(divTitle);
	 
	 if(btn){
		 html.append(btn);
		 btn.click(function(){
			 removeTab(id);
		 });
	 }
	 _TAB_SLIDE.appendSlide(html);
// 	 $("#menuTabWrap").append(html);
	 divTitle.click(function(){
		 activeTab(id);
	 });
	 
	 let iHtml = $('<iframe data-tab-type="iframe" id="jsContent" scrolling="no" data-menu-cd="'+id+'" src='+url+' style="width:100%;min-height:500px;border:0px"></iframe>');
	 $("#divMain").append(iHtml);
	 //divMain
	 addTabHist(id);
	 return html;
}

function activeTab(id){
	Utilities.blockUI();
	$("[data-tab-type=menuTab][data-menu-cd]").removeClass("active");
	$("[data-tab-type=menuTab][data-menu-cd="+id+"]").addClass("active");
	
	$("[data-tab-type=iframe][data-menu-cd]").hide();
	$("[data-tab-type=iframe][data-menu-cd="+id+"]").show();
	Utilities.unblockUI();
// 	swiper.activeIndex
	addTabHist(id);
	let idx = findIndex(id);
	_TAB_SLIDE.slideTo(idx,400);
// 	swiper.slideTo(index, speed, runCallbacks)

}
function getActiveTabId(){
	var div = $(".active[data-tab-type=menuTab][data-menu-cd]");
	if(div.length)
		return div.attr("data-menu-cd");
	else return null;
}
function findTab(id){
	let div = $("#menuTabWrap").find("[data-tab-type=menuTab][data-menu-cd="+id+"]");
	if(div.length)
		return div;
	else null;
}
function findIndex(id){
	let arr = $("#menuTabWrap").find("[data-tab-type=menuTab][data-menu-cd]");
	for(let i=0;i<arr.length;i++){
		if($(arr[i]).attr("data-menu-cd")==id)
			return i;
	}
	return -1;
}
function findIframe(id){
	let div = $("[data-tab-type=iframe][data-menu-cd="+id+"]");
	if(div.length)
		return div;
	else null;
}
function resizeTabWnd(width,height){
	// data-tab-type="iframe"
	var id = getActiveTabId();
	if(!id)
		return;
	$("[data-tab-type=iframe][data-menu-cd="+id+"]").height(height);
}
function showLeftMenu(){
	$( "#jsMenu1").addClass("active");
}
function closeAllTabs(){
	location.reload();
}

function togMyMenu(el,data){
	if(el.hasClass("active")){
		removeMyMenu(data);
	}else {
		addMyMenu(data);
	}
}
function addMyMenu(data){
	
	var el = $("[data-btn-type=myMenuBtn][data-menu-cd="+data.menuCd+"]");
	el.addClass("active");
	var dataStr = 'data-menu-cd="'+data.menuCd+'" data-menu-nm="'+data.menuNm+'" data-menu-url="'+data.menuUrl+'"';
	var html = $('<li '+dataStr+'></li>');
	
	var a = $('<a href="#;" '+dataStr+'>'+data.menuNm+'</a>');
	a.click(function(){
		goMenu($(this).data())
	});
	html.append(a);
	var btn = $('<button class="delete" '+dataStr+'>삭제</button>');
	html.append(btn);
	btn.click(function(){
		removeMyMenu($(this).data());
	});
	$("#ulMyMenu").append(html);
	
	
	
	var url = "/addWdgt";
// 	Utilities.blockUI();
	Utilities.getAjax(url,data,true,function(result,jqXHR){
// 		Utilities.unblockUI();
        if(Utilities.processResult(result,jqXHR,"위젯추가에 실패했습니다."))
        {
        	
        }
    });
	
}


function removeMyMenu(data){
	
	var el = $("[data-btn-type=myMenuBtn][data-menu-cd="+data.menuCd+"]");
	el.removeClass("active");
	$("#ulMyMenu").find("li[data-menu-cd="+data.menuCd+"]").remove();
	
	var url = "/removeWdgt";
	
	Utilities.getAjax(url,data,true,function(result,jqXHR){
        if(Utilities.processResult(result,jqXHR,"위젯추가에 실패했습니다."))
        {
        	
        }
    });
	
	
	
	
}
function removeWdgt(elem,data){
	removeMyMenu(data);
}

function showMyMenu(element){
	element.parent().addClass("active");
	return false;
}


$(document).ready(function() {
	// openMenuTab("MAIN","통합고객정보 관리","/main",true);
	openMenuTab("MAIN","main","/main",true);

	$(".lMenu .modal").on("click",function(){
		$(this).parent().removeClass("active");
	});
	$(".jsBtnShowMenu1").on("click",function(){
		$(".lMenu").addClass("active");
	});
	
	$(".mWidget1").on("mouseleave",function(){
		$(this).removeClass("active");
		return false;
	});

});

</script>
</body>
</html>