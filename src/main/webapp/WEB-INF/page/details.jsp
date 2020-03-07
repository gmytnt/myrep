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
    <link rel="stylesheet" href="/static/css/details.css"/>
</head>
<body>
<div class="header">
    <%@include file="common/header.jsp"%>
</div>
<div class="main">

    <div class="copy">
        <div class="personal_info">
            <div class="personal_mark">

            </div>
            <div class="personal_data">

            </div>

        </div>
        <div class="copy_header">
            <div class="copy_title">

            </div>
            <div class="copy_related">
                <div class="article_secondary">

                </div>
                <div class="article_like">

                </div>
            </div>
        </div>
        <div class="copy_body">

        </div>
        <shiro:notAuthenticated>
            <div style="margin-left: 40%"><a href="/login"><button class="layui-btn">点击登录后评论</button></a></div>
        </shiro:notAuthenticated>
        <shiro:authenticated>
            <div class="edit">
                <h3>发表评论（点击照片，再点击添加照片的小图标可以修改大小）</h3>
                <div class="edit_body">
                    <div id="editor">
                    </div>
                </div>
                <div class="clearfix">
                    <button type="button" class="layui-btn" id="art_sbm">提交</button>
                </div>
            </div>
        </shiro:authenticated>
    </div>
    <div class="comment_all">

    </div>

</div>
<div class="footer">
    <%@include file="common/footer.jsp"%>
</div>
<script src="/static/plugin/layui/layui.js"></script>
<script type="text/javascript" src="/static/plugin/editor/wangEditor.js"></script>
<script type="text/javascript" src="/static/js/header.js"></script>
<script src="/static/js/tools.js"></script>
<script src="/static/js/details.js"></script>
</body>
</html>