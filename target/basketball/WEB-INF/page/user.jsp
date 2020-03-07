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
    <link rel="stylesheet" href="/static/css/user.css"/>
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
                    <ul>
                        <li class="nav_item action"><a href="javascript:;" data-url="/article/findArticleUserAll">所有文章</a></li>
                        <li class="nav_item"><a href="javascript:;" data-url="/attentionUserInfo">关注</a></li>
                        <li class="nav_item"><a href="javascript:;" data-url="/followersUserInfo">粉丝</a></li>
                        <%--<li class="nav_item"><a href="#">视频</a></li>--%>
                    </ul>
                </div>
                <div class="personal_information">
                    <%--<button type="button" class="layui-btn" id="attention"><i class="fa fa-plus-square-o" aria-hidden="true"></i>&nbsp;<span>关注</span></button>--%>
                    <%--<a href="#"><img src="/static/images/default.jpg"></a>--%>
                    <%--<h6>mytntn</h6>--%>
                    <%--<p>一切为了努力</p>--%>
                    <%--<ul>--%>
                        <%--<li><span>文章</span><span>1</span></li>--%>
                        <%--<li><span>关注</span><span>1</span></li>--%>
                        <%--<li><span>粉丝</span><span>1</span></li>--%>
                        <%--<li><span>获赞</span><span>1</span></li>--%>
                        <%--<li><span>评论</span><span>1</span></li>--%>
                        <%--<li><span>访问</span><span>1</span></li>--%>
                    <%--</ul>--%>
                </div>
            </div>
        </div>
        <div class="layui-col-xs12 layui-col-sm8 layui-col-md7 middle">

            <div class="content">
                <div class="bbs_post">
                    <ul class="bbs_post_list">

                    </ul>
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
    layui.use(['layer', 'form','jquery','carousel'], function(){
        var layer = layui.layer
            ,form = layui.form
            ,$=layui.jquery
            ,carousel=layui.carousel;
//            $(".header").load("/header");

        /*轮播图*/
        //改变下时间间隔、动画类型、高度
        carousel.render({
            elem: '#ad_carousel_body'
            ,interval: 1800
            ,anim: 'fade'
            ,height: '260px'
            ,width: "100%"
        });
        /*获取url的参数*/
        let keyword=decodeURIComponent(location.search.substring(1));
        console.log(keyword);
        $.ajax({
            type: "GET",
            url: "/article/findArticleUserAll?" + keyword,
            success: function (data) {
//                console.log(data);
                showArticle(data)
            }
        });
        $.ajax({
            type: "GET",
            url: "/userInfo?"+keyword,
            success: function(data){
                console.log(data);
                userInfo(data);
            }
        });
        $.ajax({
            type: "GET",
            url: "/attentionUserInfo?"+keyword,
            success: function(data){
                console.log(data);
            }
        });
        /*点击导航栏样式改变*/
        $('.nav .nav_item').each(function () {
            $(this).click(function () {
                $('.nav_item').removeClass('action');
                $(this).addClass('action');
                let url=$(this).children('a').data("url");
                console.log(url);
                $.get(url+"?"+keyword,function (data) {
                    console.log(data);
                    if(url=="/article/findArticleUserAll"){
                        showArticle(data);
                    }else{
                        showAttentionUserInfo(data);
                    }
                })
            });
        });
        function userInfo(data) {
            if(data.user!=null){
                let  button='<button type="button" class="layui-btn" id="attention"><i class="fa fa-plus-square-o" aria-hidden="true"></i>&nbsp;<span>关注</span></button>';
                if (data.isLogin!="2"){
                    if(data.attention=="1"){
                        button='<button type="button" class="layui-btn layui-btn-danger" id="unsubscribe"><i class="fa fa-heart-o" aria-hidden="true"></i>&nbsp;<span>取消关注</span></button>';
                    }else if(data.attention=="55"){
                        button="";
                        $('.nav>ul').append('<li class="nav_item"><a href="javascript:;" data-url="/article/findArticleUserAll">个人中心</a></li>');
                    }
                }
                let dom='<input type="hidden" id="others_id" name="othersId" value="'+data.user.id+'"/><a href="/user?uid='+data.user.id+'"><img src="'+data.user.avatar+'"></a><h6>'+data.user.username+'</h6><p>'+(data.user.signature==null||data.user.signature==""?"该友很赖，什么都没有":data.user.signature)+'</p> ' +
                    '<ul> <li><span>文章</span><span>'+data.userArticleCount+'</span></li> <li><span>关注</span><span>'+data.attentionCount+'</span></li> ' +
                    '<li><span>粉丝</span><span>'+data.userAttentionCount+'</span></li> <li><span>获赞</span><span>'+data.userArticleLikes+'</span></li> ' +
                    '<li><span>评论</span><span>'+data.userArticleComments+'</span></li> <li><span>访问</span><span>'+data.userArticleViews+'</span></li> </ul>';
                $('.personal_information').empty().append(button+dom);
            }

        }
        function showArticle(data) {
            let ul=$('.bbs_post_list');
            ul.empty();
            if(data.article.length>0) {
                for (let i of data.article) {
//                console.log(i);
                    let li = $('<li class="bbs_post_item"><a href="/user?uid=' + i.user.id + '" uid="' + i.user.id + '"><img src="' + i.user.avatar + '"></a>' +
                        '<div class="post"><p class="post_summary"><a href="/article/details?aid=' + i.aid + '" aid="' + i.aid + '">' + i.atitle + '</a></p>' +
                        '<div class="post_note"><span>发于&nbsp;' + dateFormat(new Date(i.atime)) + '</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>赞&nbsp;' + i.alikeCount + '</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>评论&nbsp;' + i.acommentCount + '</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>阅读&nbsp;' + i.aviews + '</span></div></div></li>')
                    ul.append(li);
                }
            }else{
                ul.append('<li><p>没有任何文章</p></li>');
            }
        }
        function showAttentionUserInfo(data) {
            let ul=$('.bbs_post_list');
            ul.empty();
            console.log(data.userList);
            if(data.userList.length>0){
                for(let i of data.userList){
                    let button='<button class="layui-btn attention"  data-uid="'+i.user.id+'"><i class="fa fa-plus-square-o" aria-hidden="true"></i>&nbsp;<span>关注</span></button> ';
                    if(i.status=="1"){
                        button='<button class="layui-btn layui-btn-danger unsubscribe" data-uid="'+i.user.id+'"><i class="fa fa-heart-o" aria-hidden="true"></i>&nbsp;<span>取消关注</span></button>';
                    }else if(i.status=="3"){
                        button="<span>自己</span>";
                    }
                    let li='<li class="bbs_post_item"><a href="/user?uid='+i.user.id+'"><img src="'+i.user.avatar+'"></a> ' +
                        '<div class="post"> <div class="post_note"><a href="/user?uid='+i.user.id+'"><span>'+i.user.username+'</span></a></div> ' +
                        '<p class="post_summary">'+(i.user.signature==null||i.user.signature==""?"该友很赖，什么都没有":i.user.signature)+'</p> </div>'+button+'</li>';
                    ul.append(li);
                }
            }else{
                ul.append('<li><p>没有任何关注者</p></li>');
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
        /*关注事件*/
        $('.main').on("click",'#attention',function () {
            console.log($('#others_id').val())
            $.post('/attention',{"othersId":$('#others_id').val()},function (data) {
                console.log(data);
                if(data.code=="3"){
                    layer.msg(data.message,{icon:5});
                    setTimeout('window.location.href="/login"',500);
                }else if(data.code=="1"){
                    layer.msg(data.message,{icon:6});
                    setTimeout('window.location.reload()',500);
                }else {
                    layer.msg(data.message,{icon:5});
                }
            })
        });
        /*取消关注事件*/
        $('.main').on("click",'#unsubscribe',function () {
            console.log($('#others_id').val())
            $.post('/unsubscribe',{"othersId":$('#others_id').val()},function (data) {
                console.log(data);
                if(data.code=="3"){
                    layer.msg(data.message,{icon:5});
                    setTimeout('window.location.href="/login"',500);
                }else if(data.code=="1"){
                    layer.msg(data.message,{icon:6});
                    setTimeout('window.location.reload()',500);
                }else {
                    layer.msg(data.message,{icon:5});
                }
            })
        });
        /*关注事件*/
        $('.main').on("click",'.attention',function () {
            let othersId=$(this).data('uid');
            console.log(othersId);
            $.post('/attention',{"othersId":othersId},function (data) {
                console.log(data);
                if(data.code=="3"){
                    layer.msg(data.message,{icon:5});
                    setTimeout('window.location.href="/login"',500);
                }else if(data.code=="1"){
                    layer.msg(data.message,{icon:6});
                    setTimeout('window.location.reload()',500);
                }else {
                    layer.msg(data.message,{icon:5});
                }
            })
        });
        /*取消关注事件*/
        $('.main').on("click",'.unsubscribe',function () {
            let othersId=$(this).data('uid');
            console.log(othersId);
            $.post('/unsubscribe',{"othersId":othersId},function (data) {
                console.log(data);
                if(data.code=="3"){
                    layer.msg(data.message,{icon:5});
                    setTimeout('window.location.href="/login"',500);
                }else if(data.code=="1"){
                    layer.msg(data.message,{icon:6});
                    setTimeout('window.location.reload()',500);
                }else {
                    layer.msg(data.message,{icon:5});
                }
            })
        });
    });
    //        console.log(window.screen.width);
</script>


</body>
</html>