layui.use(['layer', 'form','jquery','carousel','upload'], function(){
    var layer = layui.layer
        ,$=layui.jquery;

    /*文字动画*/
    [...$('.logo_name').text()].reduce((pre, cur, index) => {
        pre == index && ($('.logo_name').html(""));
        let span = document.createElement("span");
        span.textContent = cur;
        $('.logo_name').append(span);
    }, 0);
    /*点击导航栏样式改变*/
    $('.menu .menu_item').each(function () {
        $(this).click(function () {
            $('.menu_item').removeClass('action');
            $(this).addClass('action');
        });
    });
    /*小屏幕时候显示按钮*/
    $('.menu_toggler').click(function () {
        $('.menu').toggleClass("show");
        $('.menu').toggleClass('hide_sm');
        $('.menu').toggleClass('hide_xs');
    });
    /*获取屏幕变化值*/
    $(window).on("resize",function () {
        //获取窗口宽度
        var clientW=$(window).width()+17;
        console.log(clientW);
        if(clientW>992){
            $('.menu').removeClass("show");
            $('.menu').addClass('hide_sm');
            $('.menu').addClass('hide_xs');
        };

    });
    $(window).trigger("resize");

});