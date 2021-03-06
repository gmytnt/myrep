<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=no"/>
    <title>篮球那些事--登录注册</title>
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
            <span>用户注册</span>
        </div>
        <form id="form_info">
            <div class="item">
                <i class="fa fa-user" aria-hidden="true"></i>
                <input type="text" placeholder="请输入账号" name="username">
            </div>
            <div class="item">
                <i class="fa fa-user" aria-hidden="true"></i>
                <input type="text" placeholder="请输入手机号" name="telephone">
            </div>
            <div class="item">
                <i class="fa fa-unlock-alt" aria-hidden="true"></i>
                <input type="password" placeholder="请输入密码" name="password">
            </div>
            <div class="item">
                <i class="fa fa-unlock-alt" aria-hidden="true"></i>
                <input type="password" placeholder="确认密码" name="repassword">
            </div>
            <div class="item">
                <input type="text" placeholder="输入验证码"
                       maxlength="4"
                       autocomplete="off"
                       class="inp kr_code"
                       id="code" name="code"
                >
                <img alt="验证码" onclick="this.src='/code?d=' + new Date()*1" src="/code" />
            </div>
            <div class="item">
                <input type="text" placeholder="手机验证码" class="phone_code">
                <button type="button" class="layui-btn layui-btn-normal get_verification">获取验证码</button>
            </div>
            <button class="login_btn" type="button">注册</button>
        </form>


    </div>
</div>
<script src="/static/plugin/layui/layui.js"></script>
<script>
    layui.use(['layer', 'form','jquery'], function() {
        var layer = layui.layer
            , form = layui.form
            , $ = layui.jquery;
        $("input[name=telephone]").blur(function () {
            let telephone=$(this).val()
            if (telephone == '' || !/^1[3|4|5|6|7|8|9][0-9]{9}$/.test(telephone)) {
                layer.msg("输入正确手机号", {icon: 2});
            }else {
                $.get('/findUserTelephone', {"telephone": telephone}, function (data) {
                    if(data.code==2){
                        $("[name=telephone]").val("");
                        layer.msg(data.message, {icon: 2});
                    }
                })
            }
        });
        $("input[name=username]").blur(function () {
            let username=$(this).val().trim();
            if (username == '') {
                layer.msg("用户不能为空", {icon: 2});
            }else {
                $.get('/findUserName', {"username": username}, function (data) {
                    if (data.code ==2) {
                        $("input[name=username]").val("");
                        layer.msg(data.message, {icon: 2});
                    }
                })
            }
        });
        $('.get_verification').click(function () {
            let code = $("#code").val().trim();
            let telephone = $("[name=telephone]").val().trim();
            console.log(code);
            console.log(telephone);
            if (code == '') {
                layer.msg("验证码不能为空", {icon: 2});
                return;
            }
            if (telephone == '' || !/^1[3|4|5|6|7|8|9][0-9]{9}$/.test(telephone)) {
                layer.msg("输入正确手机号", {icon: 2});
                return;
            }
            $.post('/verification', {"code": code, "telephone": telephone}, function (data) {
                layer.msg(data.message, {icon: 6});
            })
        });
        $('.login_btn').click(function () {
            let code = $(".phone_code").val();
            let telephone = $("[name=telephone]").val().trim();
            let username = $("[name=username]").val().trim();
            let pwd = $("[name=password]").val().trim();
            let repwd = $("[name=repassword]").val().trim();
            console.log(code);
            console.log(telephone);
            if (code == '') {
                layer.msg("验证码为空", {icon: 2});
                return;
            };
            if (username == '') {
                layer.msg("用户不能为空", {icon: 2});
                return;
            };
            if (pwd == '' || repwd == '') {
                layer.msg("密码不能为空", {icon: 2});
                return;
            } else if (pwd != repwd) {
                layer.msg("密码不一致", {icon: 2});
                return;
            };

            if (telephone == '' || !/^1[3|4|5|6|7|8|9][0-9]{9}$/.test(telephone)) {
                layer.msg("输入手机号码", {icon: 2});
                return;
            };
            $.post('/doregister', {
                "username": username, "code": code, "telephone": telephone,
                "password": pwd, "repassword": repwd,
            }, function (data) {
                if (data.code == "1") {
                    layer.msg(data.message, {icon: 6});
                    window.location.href="/"
                }else {
                    layer.msg(data.message, {icon: 5});
                }
            });
        });
    });

</script>

</body>
</html>