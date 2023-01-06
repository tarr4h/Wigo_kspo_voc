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
<title>VOC</title>
<tiles:insertTemplate template="header.jsp" />
</head>

<body class="voc-main">
	
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
		<h1><a href="/" ><img src="/static/kspo/images/logo.png" alt="국민체육진흥공단"></a></h1>
		<!-- gnb -->
        <nav>
			<ul class="main_menu">
				<c:forEach var="topLevelMenu" items="${ menuTree.children}">
					<c:if test='${topLevelMenu.menuShowYn eq "Y" }'>
					<li>
						<a href="#;" >${topLevelMenu.menuNm }</a>
						<div class="sub_menu">
							
								<c:forEach var="subMenu" items="${ topLevelMenu.children}" varStatus="status">
								<c:if test='${subMenu.menuShowYn eq "Y" }'>
                                <dl>
<!-- 								<li> -->
                                    <dt><a href="#;">${subMenu.menuNm }</a></dt>
									<c:forEach var="menu3" items="${subMenu.children }" >
									<c:if test='${menu3.menuShowYn eq "Y" }'>
									
									<c:set var="menu3Data">data-top-menu-id="${ menu3.topMenuId}" data-menu-id="${menu3.menuId }" data-menu-lvl-no="${menu3.menuLvlNo }" data-menu-url="${menu3.menuUrl }" data-menu-nm="${menu3.menuNm }"</c:set>
									   <dd><a href="#;" data-click="showThirdMenu" ${menu3Data }>${menu3.menuNm }</a></dd>
									</c:if>
									</c:forEach>
								</dl>
								</c:if>
								</c:forEach>
						</div>
                        
                    </li>
					</c:if>
				</c:forEach>
			</ul>
            </nav>
		<!-- //gnb -->
		<div class="gRt">
			<span class="log"><c:out value="${LOGIN_USER.userNm }" />(<c:out value="${LOGIN_USER.loginId }" />)님 환영합니다.</span>
            <span class="time" id="elSessionTime">00:00</span>
            <a href="#" class="btn s btn-blue" data-click="extendSession">연장</a>
            <a href="#" class="btn s" data-click="logout">로그아웃</a>
            
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
			<div class="gRt">
				<a href="#;" data-click="closeAllTabs" class="mBtn1 s lWhite">전체닫기</a>
			</div>
		</div>
	</div>
	<!-- //midTab -->

	<div id="body">

		

		<div id="divMain">
			 
		</div>
		<!--
			<a href="#;" class="mBtn1">검색</a>
		-->
	</div>
	<!-- // footer -->

</div>

<!-- //menu -->
<script>

var _offsetMs = 0;
var _sessionTime = 0;
var _sessionTimer = 0;


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
	$("li[data-menu-depth=2][data-top-menu-id="+data.topMenuId+"]").show();
	
// 	const toggleHeader = $('#full-toggle'), sideHeader = $('.header_side');
	
// 	sideHeader.addClass('show-side');
//     toggleHeader.addClass('open-side');
	$("ul[data-menu-depth=3][data-menu-id]").hide();
	$("li[data-menu-depth=2][data-menu-id]").removeClass("active");
    showSecondMenu(data.menuId,true);
}
function showSecondMenu(menuId,forceOpen){
	
	let ul = $("ul[data-menu-depth=3][data-menu-id="+menuId+"]");
	let li = $("li[data-menu-depth=2][data-menu-id="+menuId+"]");
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
	console.log('showMenuData : ', data);
	goMenu(data);
}
function showFourthMenu(element,data){
	goMenu(data);
}
function showSubMenu(element,data){
	showSecondMenu(data.menuId);
	if(data.menuUrl){
		goMenu(data);
	}
}
function goMenu(menu){
	let url = menu.menuUrl;
	if(!url)
		return;

	openContentsTab(menu);
	
}



function openContentsTab(menu){
	let id = menu.menuId;
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
	let btn = disableClose ? '' : $('<button class="delete" data-menu-id="'+id+'" data-btn-type="removeTab">삭제</button>');
	let divTitle = $('<a href="#;" data-btn-type="activeTab" data-menu-id="'+id+'">'+title+'</a>');
		let html =$('<div class="swiper-slide" id="" data-tab-type="menuTab" data-menu-id="'+id+'"></div>');
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
	 
	 let iHtml = $('<iframe data-tab-type="iframe" id="jsContent" scrolling="no" data-menu-id="'+id+'" src='+url+' style="width:100%;min-height:500px;border:0px"></iframe>');
	 $("#divMain").append(iHtml);
	 //divMain
	 addTabHist(id);
	 return html;
}

function activeTab(id){
	Utilities.blockUI();
	$("[data-tab-type=menuTab][data-menu-id]").removeClass("active");
	$("[data-tab-type=menuTab][data-menu-id="+id+"]").addClass("active");
	
	$("[data-tab-type=iframe][data-menu-id]").hide();
	$("[data-tab-type=iframe][data-menu-id="+id+"]").show();
	Utilities.unblockUI();
// 	swiper.activeIndex
	addTabHist(id);
	let idx = findIndex(id);
	_TAB_SLIDE.slideTo(idx,400);
// 	swiper.slideTo(index, speed, runCallbacks)

}
function getActiveTabId(){
	var div = $(".active[data-tab-type=menuTab][data-menu-id]");
	if(div.length)
		return div.attr("data-menu-id");
	else return null;
}
function findTab(id){
	let div = $("#menuTabWrap").find("[data-tab-type=menuTab][data-menu-id="+id+"]");
	if(div.length)
		return div;
	else null;
}
function findIndex(id){
	let arr = $("#menuTabWrap").find("[data-tab-type=menuTab][data-menu-id]");
	for(let i=0;i<arr.length;i++){
		if($(arr[i]).attr("data-menu-id")==id)
			return i;
	}
	return -1;
}
function findIframe(id){
	let div = $("[data-tab-type=iframe][data-menu-id="+id+"]");
	if(div.length)
		return div;
	else null;
}
function resizeTabWnd(width,height){
	// data-tab-type="iframe"
	var id = getActiveTabId();
	if(!id)
		return;
	$("[data-tab-type=iframe][data-menu-id="+id+"]").height(height);
}

function closeAllTabs(){
	location.reload();
}


function extendSession(){
	var url = "/extendSession";
    Utilities.getAjax(url,null,false,function(result,jqXHR){
        
    });
}

function startSessionTime(){
	if(_sessionTimer)
		clearTimeout(_sessionTimer);
	var cur = new Date();
	var lastTouch = parseInt(Utilities.getCookie("lastTouch"));
	var lumode =  parseInt(Utilities.getCookie("lumode"));
	var tm =parseInt( ( cur.getTime() - lastTouch - _offsetMs)/1000);
	var lft = _sessionTime - tm;
	if(lft<0 || !lumode)
		lft = 0;
	if(lft ==0){
		Utilities.setCookie("lastTouch","1000");
		logout();
		return;
	}
	var min = "0"+parseInt(lft / 60);
	var sec = "0" + lft%60;
	var str = min.slice(-2) + ":" + sec.slice(-2);
	$("#elSessionTime").html(str);
// 	console.log(str);
	_sessionTimer = setTimeout(startSessionTime,1000);
}

function getOffsetTime(){
	var url = "/util/getTime";
	Utilities.getAjax(url,null,false,function(result,jqXHR){
        if(Utilities.processResult(result,jqXHR,""))
        {
        	var cur = new Date();
        	_offsetMs =   parseInt(result.currentTime) - cur.getTime();
        	_sessionTime = parseInt(result.sessionTime) ;
        	startSessionTime();
        }
    });
}
$(document).ready(function() {
	// openMenuTab("MAIN","통합고객정보 관리","/main",true);
	openMenuTab("MAIN","메인","/main",true);

	getOffsetTime();

});

</script>
</body>
</html>