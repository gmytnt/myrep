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
    <%@include file="common/header.jsp"%>
</div>
<div class="main">
    <div class="layui-row">
        <div class="hide_xs hide_sm layui-col-md2 left">
            <div class="content">
                <div class="nav">
                    <h3>模块分类</h3>
                    <ul>
                        <li class="nav_item"><a href="#">NBA</a></li>
                        <li class="nav_item"><a href="#">CBA</a></li>
                        <li class="nav_item"><a href="#">学院派</a></li>
                        <li class="nav_item"><a href="#">街头派</a></li>
                        <li class="nav_item"><a href="#">视频</a></li>
                        <li class="nav_item"><a href="#">致胜宝</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="layui-col-xs12 layui-col-sm8 layui-col-md7 middle">

            <div class="content">
                <div class="bbs_post">
                    <div class="bbs_header">
                        <h3></h3>
                        <div class="search">
                            <input type="text" class="search_input" placeholder="输入关键词"/>
                            <select name="search_select" class="search_select">
                                <option value="1">全部</option>
                                <option value="2">NBA</option>
                                <option value="3">CBA</option>
                                <option value="4">学院派</option>
                                <option value="5">街头派</option>
                                <option value="6">视频</option>
                                <option value="7">致胜宝</option>
                            </select>
                            <button class="layui-btn search_btn"><i class="fa fa-search" aria-hidden="true"></i></button>
                        </div>
                    </div>
                    <ul class="bbs_post_list">

                    </ul>
                    <div id="page"></div>
                </div>
            </div>
        </div>
        <div class="hide_xs layui-col-sm4 layui-col-md3 right">
            <div class="content">
                <div class="announcement">
                    <h3>温馨小贴纸</h3>
                    <div class="announcement_detail">
                        本网站用于有关篮球消息的分享和交流篮球论坛，希望各位篮球爱好者共同维护网站环境，如有违法行为后果自负，本网站不负责。
                    </div>
                    <div class="announcement_footer">
                        <a href="/article/compiler">
                            发帖子
                        </a>
                    </div>

                </div>
                <div class="advertisement">
                    <h3>广告栏</h3>
                    <div class="ad_carousel">
                        <div class="layui-carousel" id="ad_carousel_body" lay-filter="ad_carousel_body">
                            <div carousel-item="">
                                <div><a href="#"><img src="/static/images/logo.jpg"></a></div>
                                <div><a href="#"><img src="/static/images/baskeball1.jpg"></a></div>
                                <div><a href="#"><img src="/static/images/baskeball2.jpg"></a></div>
                                <div><a href="#"><img src="/static/images/bg1.jpg"></a></div>
                                <div><a href="#"><img src="/static/images/bg.jpg"></a></div>
                            </div>
                        </div>
                    </div>
                    <div class="ad_photo">
                        <a href="#">
                            <img src="/static/images/timg.jpg">
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="footer">
    <%@include file="common/footer.jsp"%>
</div>
<script src="/static/plugin/layui/layui.js"></script>
<script src="/static/js/header.js"></script>
<script>
    //一般直接写在一个js文件中
    layui.use(['layer', 'form','jquery','carousel','laypage'], function(){
        var layer = layui.layer
            ,form = layui.form
            ,$=layui.jquery
            ,laypage = layui.laypage
            ,carousel=layui.carousel;
//            $(".header").load("/header");
        let keyword=decodeURIComponent(window.location.search.substr(1));
        $(".bbs_header h3").html(keyword.split("=")[1]);
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
        function pages(count) {
            laypage.render({
                elem: 'page'
                , count: count
                , theme: '#e27111'
                , layout: ['prev', 'page', 'next']
                , limit: 20
                , jump: function (obj, first) {
                    if (!first) {
                        $.get('/article/findArticleSort'
                            , {type:keyword.split("=")[1], page: obj.curr, limit: obj.limit}
                            , function (data) {
                                showArticle(data.article);
                            });
                    }
                }
            })
        }
        $.ajax({
            type: "GET",
            url: "/article/findArticleSort",
            data:{type:keyword.split("=")[1],page: 1, limit:20},
            success: function(data){
//                console.log(msg);
                showArticle(data.article);
                pages(data.count);
            }
        });

        function showArticle(data) {
            let ul=$('.bbs_post_list');
            ul.empty();
            if(data.length>0) {
                for (let i of data) {
//                console.log(i);
                    let li = $('<li class="bbs_post_item"><a href="/user?uid=' + i.user.id + '" uid="' + i.user.id + '"><img src="' + i.user.avatar + '"></a>' +
                        '<div class="post"><p class="post_summary"><a href="/article/details?aid=' + i.aid + '" aid="' + i.aid + '">' + i.atitle + '</a></p>' +
                        '<div class="post_note"><span>发于&nbsp;' + dateFormat(new Date(i.atime)) + '</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>赞&nbsp;' + i.alikeCount + '</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>评论&nbsp;' + i.acommentCount + '</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>阅读&nbsp;' + i.aviews + '</span></div></div></li>')
                    ul.append(li);
                }
            }else {
                ul.append('<li><p>没有任何文章</p></li>');
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