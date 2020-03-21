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
    <link rel="stylesheet" href="/static/css/compiler.css"/>
</head>
<body>
<div class="header">
    <%@include file="common/header.jsp"%>
</div>
<div class="main">
    <div class="edit">
        <h3>发布文章（点击照片，再点击添加照片的小图标可以修改大小）</h3>
        <div class="edit_body">
            <form class="layui-form" action="">
                <input type="text" name="title" lay-verify="title" id="article_title" autocomplete="off" placeholder="请输入文章题目" class="layui-input article_title">
                <div class="layui-form-item">
                    <label class="layui-form-label article_assort">所属分类:</label>
                    <div class="layui-input-block sort">
                        <input type="checkbox" name="sort" value="1" title="NBA">
                        <input type="checkbox" name="sort" value="2" title="CBA">
                        <input type="checkbox" name="sort" value="3" title="学院派">
                        <input type="checkbox" name="sort" value="4" title="街球派">
                        <input type="checkbox" name="sort" value="5" title="致胜宝">
                    </div>
                </div>
            </form>

            <div id="editor">

            </div>

        </div>
        <!--<button id="btn1">获取html</button>-->
        <div class="clearfix">
            <button type="button" class="layui-btn" id="art_sbm">提交</button>
        </div>
    </div>
</div>
<div class="footer">
    <%@include file="common/footer.jsp"%>
</div>
<script src="/static/plugin/layui/layui.js"></script>
<script type="text/javascript" src="/static/plugin/editor/wangEditor.js"></script>
<script type="text/javascript" src="/static/js/header.js"></script>
<script src="/static/js/tools.js"></script>

<script>
    //一般直接写在一个js文件中
    layui.use(['layer', 'form','jquery','carousel','upload'], function(){
        var layer = layui.layer
            ,form = layui.form
            ,$=layui.jquery
            ,carousel=layui.carousel
            ,upload=layui.upload;

        var E = window.wangEditor
        var editor = new E('#editor')
        editorModify(editor);
        //自定义验证规则
        form.verify({
            title: function(value){
                if(value.length < 5){
                    return '标题至少得5个字符啊';
                }
            }
            ,pass: [
                /^[\S]{6,12}$/
                ,'密码必须6到12位，且不能出现空格'
            ]
            ,content: function(value){
                layedit.sync(editIndex);
            }
        });
        $.ajax({
            type: "GET",
            url: "/article/sortAll",
            success: function(msg){
                console.log(msg);
                console.log(msg.sort);
                console.log(createSort(msg.sort));
                createSort(msg.sort);
                form.render();
            }
        });
        function createSort(data) {
            let sortList=$('.sort');
            sortList.empty();
            for(let i of data){
                console.log(i);
                let input=$("<input type=\"checkbox\" name=\"sort\" value=\""+i.sortId+"\" title=\""+i.sortName+"\">");
                sortList.append(input);
            }
            return sortList;
        }
        $("#art_sbm").click(function () {
            let content=editor.txt.html();
            let title=$("#article_title").val();
            //获取input类型是checkBox并且 name="box"选中的checkBox的元素
            var sort = $('input:checkbox[name="sort"]:checked').map(function () {
                return $(this).val();
            }).get().join(",");
            if(title==""){
                layer.msg("标题不能为空",{icon:5});
                return
            }
            if(sort==""){
                layer.msg("至少选中一个分类",{icon:5});
                return;
            }
            if(content==""){
                layer.msg("内容不能为空",{icon:5});
                return
            }
            $.post("/article/savearticle",{
                "sort":sort,"title":title,"content":content
            },function (data) {
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
            });
        });
    });
    //        console.log(window.screen.width);
</script>

</body>
</html>