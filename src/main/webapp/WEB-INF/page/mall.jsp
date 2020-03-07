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
    <link rel="stylesheet" href="/static/css/mall.css"/>
</head>
<body>
<div class="header">
    <%@include file="common/header.jsp"%>
</div>
<div class="main">
    <div class="layui-row">
        <div class="hide_xs hide_sm layui-col-md2 left">
            <div class="content">
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
        <div class="layui-col-xs12 layui-col-sm8 layui-col-md7 middle">

            <div class="content">
                <div class="layui-carousel" id="test2">
                    <div carousel-item="">
                        <div>条目1</div>
                        <div>条目2</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="hide_xs layui-col-sm4 layui-col-md3 right">
            <div class="content mall_push">
                <div>1</div>
                <div>2</div>
            </div>
        </div>
    </div>
    <div class="recommend">
        <div class="layui-row">
            <div class="layui-col-xs6 layui-col-sm6 layui-col-md3 img_item">
                <img src="/static/images/logo.jpg">
            </div>
            <div class="layui-col-xs6 layui-col-sm6 layui-col-md3 img_item">
                <img src="/static/images/logo.jpg">
            </div>
            <div class="layui-col-xs6 layui-col-sm6 layui-col-md3 img_item">
                <img src="/static/images/logo.jpg">
            </div>
            <div class="layui-col-xs6 layui-col-sm6 layui-col-md3 img_item">
                <img src="/static/images/logo.jpg">
            </div>
        </div>
    </div>
</div>
<div class="footer">
    <%@include file="common/footer.jsp"%>
</div>
<script src="/static/plugin/layui/layui.js"></script>
<script type="text/javascript" src="/static/plugin/editor/wangEditor.js"></script>
<script src="https://cdn.bootcss.com/js-xss/0.3.3/xss.js"></script>
<script src="/static/js/header.js"></script>

<script>
    //一般直接写在一个js文件中
    layui.use(['layer', 'form','jquery','carousel','upload'], function(){
        var layer = layui.layer
            ,form = layui.form
            ,$=layui.jquery
            ,carousel=layui.carousel
            ,upload=layui.upload;

        /*轮播图*/
        //改变下时间间隔、动画类型、高度
        //改变下时间间隔、动画类型、高度
        carousel.render({
            elem: '#test2'
            ,interval: 1800
            ,anim: 'fade'
            ,height: '340px'
            ,width:'100%'
            ,marginTop:'0px'
        });
    });
</script>

</body>
</html>