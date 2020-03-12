<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=no"/>
    <title>篮球那些事--index</title>
    <link rel="stylesheet" href="/static/plugin/layui/css/layui.css"/>
    <link rel="stylesheet" href="/static/plugin/font-awesome/css/font-awesome.css"/>
    <link rel="stylesheet" href="/static/css/common.css"/>
    <link rel="stylesheet" href="/static/css/header.css"/>
    <link rel="stylesheet" href="/static/css/index.css"/>
</head>
<body>
<div class="header">
    <%@include file="../common/header.jsp"%>
</div>
<div class="main">
    <div class="layui-row">
        <div class="hide_xs layui-col-sm2 left">
            <div class="content">
                <div class="nav">
                    <ul>
                        <li class="nav_item"><a href="#">用户模块</a></li>
                        <li class="nav_item"><a href="#">文章模块</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="layui-col-xs12 layui-col-sm10 middle">

            <div class="content">

            </div>
        </div>

    </div>
</div>
<div class="footer">
    <%@include file="../common/footer.jsp"%>
</div>
<script src="/static/plugin/layui/layui.js"></script>
<script>
    //一般直接写在一个js文件中
    layui.use(['layer', 'form','jquery','carousel','laypage'], function(){
        var layer = layui.layer
            ,form = layui.form
            ,$=layui.jquery
            ,laypage = layui.laypage
            ,carousel=layui.carousel;
//            $(".header").load("/header");
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
        /*轮播图*/
        //改变下时间间隔、动画类型、高度
        carousel.render({
            elem: '#ad_carousel_body'
            ,interval: 1800
            ,anim: 'fade'
            ,height: '260px'
            ,width: "100%"
        });
        //总页数大于页码总数
        function pages(count, typeId) {
            laypage.render({
                elem: 'demo7'
                , count: count
                , theme: '#4A90E2'
                , layout: ['prev', 'page', 'next']
                , limit: 20
                , jump: function (obj, first) {
                    if (!first) {
                        $.post('/News/GetNewsByPage'
                            , { page: obj.curr, limit: obj.limit, typeId: typeId }
                            , function (result) {
                                res = result.data;
                                setHtml(res);
                            });
                    }
                }
            })
        }
        $.ajax({
            type: "GET",
            url: "/article/findArticleAll",
            data:{page: 1, limit:8},
            success: function(msg){
//                console.log(msg);
                showArticle(msg.article)
            }
        });

        function showArticle(data) {
            let ul=$('.bbs_post_list');
            ul.empty();
            for(let i of data){
//                console.log(i);
                let li=$('<li class="bbs_post_item"><a href="/user?uid='+i.user.id+'" uid="'+i.user.id+'"><img src="'+i.user.avatar+'"></a>' +
                    '<div class="post"><p class="post_summary"><a href="/article/details?aid='+i.aid+'" aid="'+i.aid+'">'+i.atitle+'</a></p>' +
                    '<div class="post_note"><span>发于&nbsp;'+dateFormat(new Date(i.atime))+'</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>赞&nbsp;'+i.alikeCount+'</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>评论&nbsp;'+i.acommentCount+'</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>阅读&nbsp;'+i.aviews+'</span></div></div></li>')
                ul.append(li);
            }

        }
        function dateFormat(date, format = "YYYY-MM-DD HH:mm:ss") {
            const config = {
                YYYY: date.getFullYear(),
                MM: date.getMonth() + 1,
                DD: date.getDate(),
                HH: date.getHours(),
                mm: date.getMinutes(),
                ss: date.getSeconds()
            };
            for (const key in config) {
                format = format.replace(key, config[key]);
            }
            return format;
        }
    });
    //        console.log(window.screen.width);
</script>


</body>
</html>