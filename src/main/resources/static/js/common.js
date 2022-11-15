/* gnb */
var navi_show = function (num) {
	var show_num = num - 1;
	jQuery('.topNavi_2dth').stop(true, true).slideUp(300);
	jQuery('#topNavi' + num).stop(true, true).slideDown(300);
	jQuery('.gnb > li').removeClass('NaviOn');
	jQuery('.gnb > li:eq(' + show_num + ')').addClass('NaviOn');
}

jQuery(function () {
	jQuery('.gnb > li').hover(
		function () {
			jQuery(this).find('.topNavi_2dth').stop(true, true).slideDown(200);
		}, function () {
			jQuery(this).find('.topNavi_2dth').stop(true, true).slideUp(200);
		}
	);
});



jQuery(function () {
	jQuery('.lnb .twoDepth > a').click(
		function () {
			jQuery(this).next('.threeDepth').slideToggle();
			jQuery(this).parent('li').toggleClass('open');
			return false;
		}
	);
});


