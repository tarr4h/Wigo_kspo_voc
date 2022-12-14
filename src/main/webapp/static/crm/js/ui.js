$(document).ready(function() {
	
	/* grid1 */
    $(".mGrid1 .gLeft .btnClose").on("click",function(){
		if ( $("#wrap").hasClass("closed") )
		{
			$("#wrap").removeClass("closed");
			setTimeout(function(){
				$(".mGrid1 > .gLeft").css("z-index","");
			},1000);
		}
		else
		{
			$("#wrap").addClass("closed");
			setTimeout(function(){
				$(".mGrid1 > .gLeft").css("z-index","1000");
			},1000);
		}

		if ( $("#wrapIframe").hasClass("closed") )
		{
			$("#wrapIframe").removeClass("closed");
			setTimeout(function(){
				$(".mGrid1 > .gLeft").css("z-index","");
			},1000);
		}
		else
		{
			$("#wrapIframe").addClass("closed");
			setTimeout(function(){
				$(".mGrid1 > .gLeft").css("z-index","1000");
			},1000);
		}
		return false;
    });
	$(".mGrid1 > .gLeft .btnWide").on("click",function(){
		if ( $("#wrap").hasClass("wide") )
			$("#wrap").removeClass("wide");
		else
			$("#wrap").addClass("wide");

		if ( $("#wrapIframe").hasClass("wide") )
			$("#wrap").removeClass("wide");
		else
			$("#wrap").addClass("wide");
			
		return false;
	});
	/* //grid1 */
    
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
	});

	/* gnb */
	$(".iFavor1").on("click",function(){
		if ( $(this).hasClass("active") )
			$(this).removeClass("active");
		else
			$(this).addClass("active");
		return false;
	});
	
	$(".lMenu .modal").on("click",function(){
		$(this).parent().removeClass("active");
	});
	$("#header .menu").on("click",function(){
		$( $(this).attr("href") ).addClass("active");
		return false;
	});
	/* //gnb */

	/* widget */
	$(".mWidget1 a.btn").on("click",function(){
		$(this).parent().addClass("active");
		return false;
	});
	$(".mWidget1").on("mouseleave",function(){
		$(this).removeClass("active");
		return false;
	});
	$(".mWidget1 .list li .delete").on("click",function(){
		$(this).parent().remove();
		return false;
	});
	/* //widget */

	/* menu */
	$(".lMenu .con li a").on("click",function(){
		if ( $(this).next("ul").length )
		{
			if ( $(this).parent().hasClass("active") )
			{
				$(this).next("ul").slideUp(function(){
					$(this).parent("li").removeClass("active");
				});
			}
			else
			{
				//$(this).parent().parent().children("li").removeClass("active");
				//$(".lMenu .con li ul").hide();
				$(this).parent("li").addClass("active");
				$(this).next("ul").slideDown();
			}
			return false;
		}
	});
	/* //menu */

	/* tab */
	$(".jsTab1 li > a").on("click",function(){
		$(this).parent().parent().children().removeClass("active");
		$(this).parent().addClass("active");
	});
	/* //tab */

	/* directory */
	$(document).on("click", ".mDir1 li a", function () {
		if ($(this).parent().hasClass("active"))
			$(this).parent().removeClass("active");
		else
		$(this).parent().addClass("active");
		return false;
	});
	/* //directory */
	
	 /* button */
	$(".jsBtnShow1").on("click", function(){
		$( $(this).attr("href") ).show();
		return false;
	});
	$(".jsBtnClose1").on("click", function(){
		$( $(this).attr("href") ).hide();
		return false;
	});
	 /* //button */

});
