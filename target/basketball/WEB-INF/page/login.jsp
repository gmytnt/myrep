<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=no"/>
    <title>篮球那些事--登录页面</title>
    <link rel="stylesheet" href="/static/plugin/layui/css/layui.css"/>
    <link rel="stylesheet" href="/static/plugin/font-awesome/css/font-awesome.css"/>
    <link rel="stylesheet" href="/static/css/common.css"/>
    <link rel="stylesheet" href="/static/css/login.css"/>
</head>
<body>
<div class="container">
    <div class="login_box">
        <div class="item">
            <h1>篮球那些事</h1>
            <span>会员登录</span>
        </div>
        <form action="">
            <div class="item">
                <i class="fa fa-user" aria-hidden="true"></i>
                <input type="text" placeholder="输入手机/用户名" name="username">
            </div>
            <div class="item">
                <i class="fa fa-unlock-alt" aria-hidden="true"></i>
                <input type="password" placeholder="输入密码" name="password">
            </div>
            <button class="login_btn" type="button">登录</button>
        </form>
    </div>
</div>
<script src="/static/plugin/layui/layui.js"></script>
<script>
    layui.use(['layer', 'form','jquery'], function() {
        var layer = layui.layer
            , form = layui.form
            , $ = layui.jquery;
        $('input:text[name="username"]').keyup(function () {
            $(this).val($(this).val().trim());
        })
        $('input:password[name="password"]').keyup(function () {
            $(this).val($(this).val().trim());
        })
        $('.login_btn').click(function () {
            let username = $('input:text[name="username"]').val().trim();
            let pwd = $('input:password[name="password"]').val().trim();
            console.log(username);
            console.log(pwd);
            if (username == '') {
                layer.msg("用户不能为空", {icon: 2});
                return;
            };

            if (pwd == ''||pwd.length<6) {
                layer.msg("密码不能少于八位", {icon: 2});
                return;
            };

            $.post('/dologin', {"telephone": username, "password": pwd}, function (data) {
                console.log(data.code);
                if (data.code == "1") {
                    layer.msg(data.message, {icon: 6});
                    console.log(+"地址")
                    let url=document.referrer;
                    if (url == '') {
                        // 没有来源页面信息的时候，改成首页URL地址
                        window.location.href="/"
                    }else {
                        window.location.href=url;
                    }
                }else {
                    layer.msg(data.message, {icon: 5});
                }
            });
        });
    });
</script>
</body>
</html>