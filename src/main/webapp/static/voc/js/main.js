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