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
                        <li class="nav_item"><a href="#">用户模块</a></li>
                        <li class="nav_item"><a href="#">文章模块</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="layui-col-xs12 layui-col-sm10 middle">

            <div class="content">
                <div class="admin_show">
                    <div class="query_user">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">用户名:</label>
                                <div class="layui-input-inline">
                                    <input type="text"  class="username" autocomplete="off" placeholder="输入用户名" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">手机号:</label>
                                <div class="layui-input-inline">
                                    <input type="text"  class="phone" autocomplete="off" placeholder="输入用户名" class="layui-input">
                                </div>
                            </div>
                            <button type="button" class="layui-btn query_bnt" data-type="reload" >查询</button>
                        </div>
                    </div>
                    <table class="layui-hide" id="userTable" lay-filter="userTable"></table>
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
<div id="edit" style="display: none">
    <p>设置用户<span class="edit_username"></span>为</p>
    <select name="interest" class="select_roles" lay-filter="aihao"></select>
    <button class="layui-btn edit_roles">提交</button>
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
//            $(".header").load("/header");
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
        let active = {
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
        //监听行单击事件
        let modelIndex;
        table.on('row(userTable)', function(obj){
            console.log(obj.data.id) //得到当前行数据
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
        });
        $(".edit_roles").click(function () {
            $.post("/admin/adminUpdateUserRoles",{"userId":$(this).data("uid"),"rolesId":$(".select_roles").val()},function (data) {
                console.log(data);
            })
        });
        //型号查询点击事件
        $('.query_bnt').on('click', function(){
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

    });
</script>


</body>
</html>