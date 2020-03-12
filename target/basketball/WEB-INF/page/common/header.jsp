<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<style>

</style>
<div class="header_body">
    <div class="logo">
        <img src="/static/images/logo.jpg">
        <div class="logo_name">篮球那些事</div>
    </div>
    <button class="menu_toggler hide_lg hide_md"><i class="fa fa-bars" aria-hidden="true"></i></button>
    <div class="hide_xs hide_sm menu">
        <ul>
            <li class="menu_item action"><a href="/">首页</a></li>
            <li class="menu_item"><a href="/article/sort?type=NBA">NBA</a></li>
            <li class="menu_item"><a href="/article/sort?type=CBA">CBA</a></li>
            <li class="menu_item"><a href="/article/sort?type=学院派">学院派</a></li>
            <li class="menu_item"><a href="/article/sort?type=街头派">街头派</a></li>
            <li class="menu_item"><a href="/article/sort?type=视频">视频</a></li>
            <li class="menu_item"><a href="/article/sort?type=致胜宝">致胜宝</a></li>
            <%-- <li class="menu_item"><a href="/mall">商城</a></li>--%>
            <%--<li class="menu_item"><a href="#">联系</a></li>--%>
            <shiro:hasRole name="superAdmin">
                <li class="menu_item"><a href="/admin/adminView">后台</a></li>
            </shiro:hasRole>
        </ul>
        <shiro:notAuthenticated>
            <div class="login_register">
                <a href="/register" class="layui-btn btn_reg">注册</a>
                <a href="/login" class="layui-btn layui-btn-normal btn_login">登录</a>
            </div>
        </shiro:notAuthenticated>
        <shiro:authenticated>
            <div class="user_message">
                <img src="<shiro:principal property="avatar"/>"class="user_img_info">
                <shiro:principal property="username"/>
                <div class="user_info">
                    <a href="/user?uid=<shiro:principal property="id"/>">个人信息</a>
                    <a href="/logout">退出</a>
                </div>
            </div>
        </shiro:authenticated>
    </div>
</div>
<script>
    for (let i of document.querySelectorAll(".menu li a")){
        if (i.getAttribute("href")==window.location.pathname+decodeURIComponent(window.location.search)){
//            console.log(i.parentElement.parentElement.children)
            for (let j of i.parentElement.parentElement.children){
//                console.log(j);
                j.classList.remove('action');
            }
            i.parentElement.classList.add('action');
        }
    }
</script>