$(document).ready(function() {
	
	$(".mLnb li .dep1").on("click",function(){
		if ( $(this).parent().hasClass("selected") )
		{
			$(this).parent().removeClass("selected");
			$(this).next().slideUp();
		}
		else
		{
			$(this).parent().addClass("selected");
			$(this).next().slideDown();
		}
	});
	
	$(".btnClose").on("click",function(){
		if ( $("body").hasClass("closed") )
		{
			$("body").removeClass("closed");
		}
		else
		{
			$("body").addClass("closed");
		}
	});

	$(".jsTab1 a").on("click",function(){
		$(this).parent().parent().children().removeClass("selected");
		$(this).parent().parent().next().children().addClass("hidden");
		$(this).parent().addClass("selected");
		$( $(this).attr("href") ).removeClass("hidden");
		return false;
	});

	$(".jsTab2 a").on("click",function(){
		$(this).parent().parent().children().removeClass("selected");
		$(this).parent().parent().parent().children(".tabCont").addClass("hidden");
		$(this).parent().addClass("selected");
		$( $(this).attr("href") ).removeClass("hidden");
		return false;
	});

	
	$( ".date" ).datepicker({
		dateFormat: "yy-mm-dd",
		prevText: '이전 달',
		nextText: '다음 달',
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		showMonthAfterYear: true,
		yearSuffix: '년',
        showOn: "button",
        buttonImage: "images/ico_cal1.png",
        buttonImageOnly:true,
	});
	
	$(".jsBtnShow1").on("click", function(){
		$( $(this).attr("href") ).show();
		return false;
	});
	$(".jsBtnClose1").on("click", function(){
		$( $(this).attr("href") ).hide();
		return false;
	});
	
	$(".jsBtnToogle1").on("click", function(){
		if( $(this).next().css("display")  == "block" )
			$(this).next().hide();
		else
			$(this).next().show();
		return false;
	});

	/* main */
	
		/* animate */
		function aniOnOff($ele){
			var scrollPos = $(window).scrollTop()+$(window).height();
			if ( scrollPos > $ele.offset().top )	$ele.addClass("animate");
			else									$ele.removeClass("animate");
		}
		$(window).on("scroll load", function(event){
			if ( $(".jsAnimateMain1").length ) aniOnOff( $(".jsAnimateMain1") );
			if ( $("#jsAnimate2").length ) aniOnOff( $("#jsAnimate2") );
			if ( $("#jsAnimate3").length ) aniOnOff( $("#jsAnimate3") );
			if ( $("#jsAnimate4").length ) aniOnOff( $("#jsAnimate4") );
			if ( $("#jsAnimate5").length ) aniOnOff( $("#jsAnimate5") );
			if ( $("#jsAnimate6").length ) aniOnOff( $("#jsAnimate6") );
		});
		/* //animate */

		/* button */
		$(".jsBtnShow1").on("click",function(){
			$( $(this).attr("href") ).show();
			return false;
		});
		$(".jsBtnClose1").on("click",function(){
			$( $(this).attr("href") ).hide();
			return false;
		});
		$(".lMovie1 .modalBg").on("click",function(){
			$(this).parent().hide();
			return false;
		});
		/* //button */

		/* button */
		$(".jsTab1 a").on("click",function(){
			$(this).parent().children().removeClass("active");
			$(this).addClass("active");
			$(this).parent().parent().children().addClass("hidden");
			$( $(this).attr("href") ).removeClass("hidden");
			return false;
		});
		/* //button */

	/* //main */

	/* show popup when load */
	$(window).on("load", function(){
		if( window.location.href.split( "#" )[1] )
		{
			$("body").addClass("hiddenScroll");
			$("#" + window.location.href.split( "#" )[1] ).addClass("active");
			$("#" + window.location.href.split( "#" )[1] ).css("top", "0");
		}
	});
	/* //show popup when load */


});

/* add20220106 */
function openPopUp(url, name){
	var options = 'width=1000, height=600, top=30, left=30, resizable=no, scrollbars=no, location=no'; window.open(url, name, options);
	return false;
}
/* //add20220106 */