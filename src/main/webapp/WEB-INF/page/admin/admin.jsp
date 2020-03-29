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
    <link rel="stylesheet" href="/static/css/admin.css"/>
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
                        <li class="nav_item action"><a href="javascript:;" data-url="/admin/adminFindUser">用户模块</a></li>
                        <li class="nav_item"><a href="javascript:;" data-url="/admin/adminArticleList">文章模块</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="layui-col-xs12 layui-col-sm10 middle">

            <div class="content">
                <div class="admin_show">

                </div>
            </div>
        </div>

    </div>
</div>
<div class="footer">
    <%@include file="../common/footer.jsp"%>
</div>
<div  id="userBar" style="display: none;">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
</div>
<div  id="articleBar" style="display: none;">
    <a class="layui-btn layui-btn-xs" lay-event="edit">详情</a>
</div>
<div id="edit" style="display: none">
    <p>设置<span class="edit_username"></span>为</p>
    <select name="rolse" class="select_roles"></select>
    <button class="layui-btn edit_roles">提交</button>
</div>
<div id="edit_article" style="display: none">
    <div class="copy">
        <div class="personal_info">
            <div class="personal_mark">

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
        <div class="article_edit">
            <select name="artile_status" class="select_status"></select>
            <button class="layui-btn edit_status">提交</button>
        </div>
    </div>
    <div class="comment_all">

    </div>
</div>
<script src="/static/plugin/layui/layui.js"></script>
<script src="/static/js/header.js"></script>
<script src="/static/js/tools.js"></script>
<script>
    //一般直接写在一个js文件中
    layui.use(['layer', 'form','jquery','carousel','laypage','table'], function() {
        var layer = layui.layer
            , form = layui.form
            , $ = layui.jquery
            , laypage = layui.laypage
            , table = layui.table
            , carousel = layui.carousel;
        let active;/*用户table重载*/
        let modelIndex;
        let activeArticle;
        let modelIndexArticle;
        function allUser() {
            $(".admin_show").empty();
            let chiledren='<div class="query_user">' +
                '<div class="layui-form-item" style="font-size: 18px">' +
                '<div class="layui-inline"><label class="layui-form-label">用户名:</label> <div class="layui-input-inline"> <input type="text"  class="username" autocomplete="off" placeholder="输入用户名" class="layui-input"> </div> </div> ' +
                '<div class="layui-inline"> <label class="layui-form-label">手机号:</label> <div class="layui-input-inline"> <input type="text"  class="phone" autocomplete="off" placeholder="输入用户名" class="layui-input"> </div> </div> ' +
                '</div> <button type="button" class="layui-btn query_bnt" data-type="reload" >查询</button> </div> ' +
                '<table class="layui-hide" id="userTable" lay-filter="userTable"></table>';
            $(".admin_show").append(chiledren);
            userTable();
        }
        function userTable() {
            table.render({
                elem: '#userTable'
                , url: '/admin/adminFindUser'
                , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
                , cols: [[
                    {field: 'id', width: "10%", title: 'ID', sort: true}
                    , {field: 'avatar', width: "10%", title: '头像',templet: function (d) {
                        console.log(d);
                        return '<img src="'+d.avatar+'" width="40px" height="40px"/>';}}
                    , {field: 'username', width: "10%", title: '用户名'}
                    , {field: 'roles', width: "10%", title: '角色',templet:(d)=>{return formataRoles(d.roles)}}
                    , {field: 'telephone', width: "15%", title: '手机'}
                    , {field: 'password', width: "20%", title: '密码'}
                    , {field: 'salt', width: "20%", title: '盐值'}
                    , {field: 'status', width: "10%", title: '状态',templet:(d)=>{return d.status=='1'?"可用":"禁用中"}}
                    , {field: 'signature', width: "10%", title: '签名',templet:(d)=>{return d.signature==null?"":d.signature}}
                    , {field: 'createIp', width: "10%", title: '创建IP'}
                    , {field: 'createTime', width: "15%", title: '创建时间',templet:(d)=>{return d.createTime==null?'':dateFormat(new Date(d.createTime))}}
                    , {field: 'loginIp', width: "10%", title: '登录IP'}
                    , {field: 'loginTime', width: "15%", title: '登录时间',templet:(d)=>{return d.loginTime==null?'':dateFormat(new Date(d.loginTime))}}
                    , {field: 'lastIp', width: "10%", title: '上次IP'}
                    , {field: 'lastTime', width: "15%", title: '上次时间',templet:(d)=>{return d.lastTime==null?'':dateFormat(new Date(d.lastTime))}}
                    ,{fixed: 'right', title:'操作', toolbar: '#userBar', width:150 ,align:'center'}
                ]]
                ,id: 'testReload'
                ,page: true //是否显示分页
                ,limit: 50 //每页默认显示的数量
            });
            active = {
                reload: function(){
                    let username=$(".username").val().trim();
                    let telephone=$(".phone").val().trim();
                    console.log(username)
                    console.log(telephone)
                    //执行重载
                    table.reload('testReload', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                            username:username,
                            telephone:telephone
                        }
                        ,url:'/admin/adminFindUser'
                    });
                }
            };
        }
        allUser();
        //监听行单击事件
        table.on('tool(userTable)', function(obj){
        /*    console.log(obj.data) //得到当前行数据
            console.log(obj) //得到当前行数据
            console.log(obj.event) //得到当前行数据*/
            if(obj.event=="edit"){
                $("#edit select").empty();
                $.get("/admin/adminFindRoles",function(data){
                    console.log(data);
                    for (let i of data.data){
                        $("#edit select").append('<option value="'+i.rolesId+'">'+i.rolesName+'</option>')
                    }
                });
                modelIndex=layer.open({
                    type: 1,
                    title:"设置用户角色",  //标题
                    skin :'layui-layer-molv',//皮肤
                    /* area: '500px' */
                    area:['500px','200px'],//宽高
                    offset: 'auto',	 //offset默认情况下不用设置。但如果你不想垂直水平
                    content: $('#edit') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    ,closeBtn : 1  //设置关闭按钮的样式  1  默认
                    ,shade: [0.8, '#fdffee']
                    ,shadeClose:true  //点击遮罩是否关闭弹层
                    ,anim: 4 //设置动画
                    ,maxmin :true //是否显示最大化和最小化的按钮 type=1 type=2有效
                });
                $(".edit_username").html(obj.data.username);
                $(".edit_roles").data("uid",obj.data.id);
            }

        });
        $(".edit_roles").click(function () {
            $.post("/admin/adminUpdateUserRoles",{"userId":$(this).data("uid"),"rolesId":$(".select_roles").val()},function (data) {
                console.log(data);
                if(data.code=="1"){
                    layer.close(modelIndex);
                    allUser();
                    active["reload"].call(this);
                }else {
                    layer.msg(data.message,{icon:5});
                }
            })
        });
        //型号查询点击事件
        $('.admin_show').on('click','.query_bnt', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        function formataRoles(data){
            var html = '';
            data.forEach(function(item){
                html += "<div>"+item.rolesName+"</div>";
            });
            return html;
        }
        /*用户结束*/
        /*点击导航栏样式改变*/
        $(".nav").on("click",".nav_item",function () {
            $('.nav_item').removeClass('action');
            $(this).addClass('action');
            let url=$(this).children('a').data("url");
            console.log(url);
            if(url=="/admin/adminFindUser"){
                allUser();
            }else if (url=="/admin/adminArticleList"){
                allArticle();
            }
        })
        /*文章开始*/
        function allArticle(){
            $(".admin_show").empty();
            let chiledren='<div class="query_user">' +
                '<div class="layui-form-item"  style="font-size: 18px">' +
                '<div class="layui-inline"><label class="layui-form-label">用户名:</label> <div class="layui-input-inline"> <input type="text"  class="username" autocomplete="off" placeholder="输入用户名" class="layui-input"> </div> </div> ' +
                '<div class="layui-inline"> <label class="layui-form-label">标题:</label> <div class="layui-input-inline"> <input type="text"  class="title" autocomplete="off" placeholder="输入标题" class="layui-input"> </div> </div>' +
                '<div class="layui-inline"><label class="layui-form-label">内容:</label> <div class="layui-input-inline"> <input type="text"  class="article_content" autocomplete="off" placeholder="输入用户名" class="layui-input"> </div> </div> ' +
                '</div>' +
                '<div class="layui-form-item"  style="font-size: 18px">'+
                '<label class="layui-form-label">状态:</label>'+
                ' <div class="layui-input-block">' +
                '<input type="radio" name="status" value="" title="全部" checked=""><span>全部</span>&nbsp;&nbsp;' +
                '<input type="radio" name="status" value="1" title="待审核"><span style="color: #5743ee">待审核</span>&nbsp;&nbsp;' +
                '<input type="radio" name="status" value="2" title="通过"> <span style="color: #008000">通过</span>&nbsp;&nbsp;' +
                '<input type="radio" name="status" value="3" title="不通过"> <span style="color: #FF4500">未通过</span>&nbsp;&nbsp;' +
                '<input type="radio" name="status" value="4" title="已删除"><span style="color: #000000">已删除</span>&nbsp;&nbsp;' +
                '</div> ' +
                '</div>' +
                '<button type="button" class="layui-btn query_bnt_article" data-type="reload" >查询</button>' +
                '</div> ' +
                '<table class="layui-hide" id="articleTable" lay-filter="articleTable"></table>';
            $(".admin_show").append(chiledren);
            articlTable();
        }
        function articlTable() {
            table.render({
                elem: '#articleTable'
                , url: '/admin/adminArticleList'
                ,done:function(res, curr, count){
                    /* alert(res);//后台url返回的json串
                     alert(curr);//当前页
                     alert(count);//数据总条数 */
//                    console.log(res);
                    let that=this.elem.next();
                    res.data.forEach(function (item,index) {
//                        console.log(that)
//                        console.log(index)
                        if(item.astatus=="1"){
                            let tr=that.find(".layui-table-box tbody tr[data-index='"+index+"']").css("background-color","rgba(8, 45, 242, 0.26)");
                        }else if(item.astatus=="2"){
                            let tr=that.find(".layui-table-box tbody tr[data-index='"+index+"']").css("background-color","rgba(77, 199, 0, 0.31)");
                        }else if(item.astatus=="3"){
                            let tr=that.find(".layui-table-box tbody tr[data-index='"+index+"']").css("background-color","rgba(255, 0, 0, 0.5)");
                        }else if(item.astatus=="4"){
                            let tr=that.find(".layui-table-box tbody tr[data-index='"+index+"']").css("background-color","rgba(0, 0, 0, 0.2)");
                        }
                    })

                }
                , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
                , cols: [[
                    {field: 'aid', width: "10%", title: 'ID', sort: true}
                    , {field: 'user', width: "10%", title: '用户名',templet: function (d) {
                        return '<div>'+d.user.username+'<div>';}}
                    , {field: 'atitle', width: "10%", title: '标题',templet:(d)=>{return d.atitle.length>15?d.atitle.substr(0,10)+".".repeat(3):d.atitle}}
                    , {field: 'acontent',title: '内容',templet:(d)=>{return d.acontent}}
                    , {field: 'astatus', width: "20%", title: '状态',templet:(d)=>{
                        if (d.astatus=="1") {
                            return '<span style="color:#5743ee;">待审核</span>'
                        }
                        if (d.astatus=="2") {
                            return '<span style="color:#008000;">通过</span>'
                        }
                        if (d.astatus=="3") {
                            return '<span style="color:#FF4500;">未通过</span>'
                        }
                        if (d.astatus=="4") {
                            return '<span style="color:#000000;">已删</span>'
                        }
                    }}
//                    , {field: 'salt', width: "20%", title: '盐值'}
//                    , {field: 'status', width: "10%", title: '状态',templet:(d)=>{return d.status=='1'?"可用":"禁用中"}}
//                    , {field: 'signature', width: "10%", title: '签名',templet:(d)=>{return d.signature==null?"":d.signature}}
//                    , {field: 'createIp', width: "10%", title: '创建IP'}
                    , {field: 'atime', width: "15%", title: '发布时间',templet:(d)=>{return d.atime==null?'':dateFormat(new Date(d.atime))}}
                    ,{fixed: 'right', title:'操作', toolbar: '#articleBar', width:150 ,align:'center'}
                ]]
                ,id: 'articleReload'
                ,page: true //是否显示分页
                ,limit: 50 //每页默认显示的数量
            });
            activeArticle= {
                reload: function(){
                    let username=$(".username").val().trim();
                    let title=$(".title").val().trim();
                    let content=$(".article_content").val().trim();
                    let astatus=$("input[name='status']:checked").val().trim();
                    console.log(astatus)
                    //执行重载
                    table.reload('articleReload', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                            username:username,
                            astatus:astatus,
                            title:title,
                            content:content
                        }
                        ,url: '/admin/adminArticleList'
                    });
                }
            };

            //型号查询点击事件
            $('.admin_show').on('click','.query_bnt_article', function(){
                var type = $(this).data('type');
                activeArticle[type] ? activeArticle[type].call(this) : '';
            });
        }
        //监听行单击事件
        table.on('tool(articleTable)', function(obj){
            /*    console.log(obj.data) //得到当前行数据
             console.log(obj) //得到当前行数据
             console.log(obj.event) //得到当前行数据*/
            console.log(obj.data.aid) //得到当前行数据
            if(obj.event=="edit"){

                modelIndexArticle=layer.open({
                    type: 1,
                    title:"文章详情",  //标题
                    skin :'layui-layer-molv',//皮肤
                    /* area: '500px' */
                    area:['98%','98%'],//宽高
                    offset: 'auto',	 //offset默认情况下不用设置。但如果你不想垂直水平
                    content: $('#edit_article') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
//                    ,closeBtn : 1  //设置关闭按钮的样式  1  默认
//                    ,shade: [0.8, '#fdffee']
                    ,shadeClose:true  //点击遮罩是否关闭弹层
                    ,anim: 4 //设置动画
                    ,maxmin :true //是否显示最大化和最小化的按钮 type=1 type=2有效
                });
                /*获取文章内容*/
                new Promise((resolve, reject) => {
                    $.ajax({
                        type: "GET",
                        url: "/admin/adminFindArticleBy?aid="+obj.data.aid,
                        success: function(data){
                            resolve(data.article.aid);
                            showArticleDetails(data.article);
                        }
                    });
                }).then(value=>{
                    $.ajax({
                        type: "GET",
                        url: "/admin/adminFindCommentArticle?aid="+value,
                        success: function(data){;
                            commentShow(data);
                        }
                    });
                });
            }

        });

        function showArticleDetails(data) {
            let option;
            console.log(data);
            $('.personal_mark').empty().append('<a href="/user?uid='+data.user.id+'"uid='+data.user.id+'><img src="'+data.user.avatar+'"/></a><a href="/user?uid='+data.user.id+'"uid='+data.user.id+'>'+data.user.username+'</a> ' +
                '<span class="sign_name">'+(data.user.signature==null||data.user.signature==""?"该友很赖，什么都没有":data.user.signature)+'</span>').append('<input type="hidden" id="others_id" name="othersId" value="'+data.user.id+'"/>');
            $('.copy_title').empty().html(data.atitle);
            $('.copy_body').empty().html(data.acontent);
            $('.copy_body').append('<input type="hidden" id="aid" name="aid" value="'+data.aid+'"/>');
            $('.article_secondary').empty().append('<span>发于&nbsp;'+dateFormat(new Date(data.atime))+'</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>赞&nbsp;'+data.alikeCount+'</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>评论&nbsp;'+data.acommentCount+'</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>阅读&nbsp;'+data.aviews+'</span>');
            if(data.astatus=="1"){
                option='<option value="1" selected>等待审核</option><option value="2">通过</option><option value="3">未通过</option><option value="4">删除</option>';
            }else if(data.astatus=="2"){
                option='<option value="2" selected>通过</option><option value="2" selected>未通过</option><option value="4">删除</option>';
            }else if (data.astatus=="3"){
                option='<option value="3" selected>未通过</option><option value="2" selected>通过</option><option value="4">删除</option>';
            }else if (data.astatus=="4"){
                option='<option value="4">删除</option>';
            }
            $(".article_edit select").empty().append(option);
            $(".edit_status").data("aid",data.aid);
        }

        function commentShow(data) {
            let commentdiv=$('.comment_all').empty();
            for(let i of data.commentList){
                let div='<div class="comment"><div class="comment_header"> <img src="'+i.user.avatar+'"/> <a href="#">'+i.user.username+'</a> </div> ' +
                    '<div class="comment_body">'+i.commentContent+'</div> ' +
                    '<div class="comment_footer"  data-uid="'+i.user.id+'" data-username="'+i.user.username+'" data-cid="'+i.commentId+'"> <span>'+dateFormat(new Date(i.commentTime))+'</span><div><span>'+i.commentLikeCount+'</span><span>赞</span></div></div></div>';
                commentdiv.append(div);
            }
        }
        $(".edit_status").click(function () {
            $.post("/admin/adminUpdateArticleStatus",{"aid":$(this).data("aid"),"astatus":$(".select_status").val()},function (data) {
                console.log(data);
                if(data.code=="1"){
                    layer.close(modelIndexArticle);
                    allArticle();
                    activeArticle["reload"].call(this);
                }else {
                    layer.msg(data.message,{icon:5});
                }
            })
        });
    });
</script>


</body>
</html>