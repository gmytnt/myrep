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
        /*创建编辑器*/
        function editorModify(data) {
            // 通过 url 参数配置 debug 模式。url 中带有 wangeditor_debug_mode=1 才会开启 debug 模式
            editor.customConfig.debug = location.href.indexOf('wangeditor_debug_mode=1') > 0
            editor.customConfig.uploadImgServer = '/uploadFile';  // 上传图片到服务器
            //         editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
            // 隐藏“网络图片”tab
            editor.customConfig.showLinkImg = false;
            editor.customConfig.height = 1000
            editor.customConfig.uploadFileName = 'file';
            // 忽略粘贴内容中的图片
            editor.customConfig.pasteIgnoreImg = true;
            editor.customConfig.menus = [
                'head',  // 标题
                'bold',  // 粗体
                'fontSize',  // 字号
                'fontName',  // 字体
                'italic',  // 斜体
                'underline',  // 下划线
                'strikeThrough',  // 删除线
                'foreColor',  // 文字颜色
                'backColor',  // 背景颜色
                'link',  // 插入链接
                'justify',  // 对齐方式
                'emoticon',  // 表情
                'image',  // 插入图片
                'table',  // 表格
                'undo',  // 撤销
                'redo'  // 重复
            ]
            editor.create();
        }

        /*获取url的参数*/
        let keyword=decodeURIComponent(location.search.substring(1));

        /*创建是否关注和显示用户信息*/
        function createUserInfo(data) {
            console.log(data.attention);
            let div=$('.personal_data');
            div.empty();
            let button;
            if(data.attention=="1"){
                button='<button type="button" class="layui-btn layui-btn-danger" id="unsubscribe"><i class="fa fa-heart-o" aria-hidden="true"></i>&nbsp;<span>取消关注</span></button>';
            }else if(data.attention=="55"){
            }else {
                button='<button type="button" class="layui-btn" id="attention"><i class="fa fa-plus-square-o" aria-hidden="true"></i>&nbsp;<span>关注</span></button>';
            }
            let ul='<ul><li><span>文章</span><span class="article_num">'+data.userArticleCount+'</span></li><li><span>粉丝</span><span class="fan_num">'+data.userAttentionCount+'</span></li>' +
                '<li><span>获赞</span><span class="likes_num">'+data.userArticleLikes+'</span></li><li><span>评论</span><span class="comments_num">'+data.userArticleComments+'</span></li> ' +
                '<li><span>访问</span><span class="views_num">'+data.userArticleViews+'</span></li> </ul>';
            div.append(button);
            div.append(ul);
            if(data.articleLike=="0"){
                $(".article_like").empty().append('<button type="button" class="layui-btn like_btn"><i class="fa fa-plus-square-o" aria-hidden="true"></i>&nbsp;<span>点赞</span></button>');
            }else if(data.articleLike=="1"){
                $(".article_like").empty().append('<button type="button" class="layui-btn layui-btn-danger"><i class="fa fa-heart-o" aria-hidden="true"></i>&nbsp;<span>已赞了</span></button>');
            }

        }
        function showArticleDetails(data) {

            console.log(data);
            $('.personal_mark').empty().append('<a href="/user?uid='+data.user.id+'"uid='+data.user.id+'><img src="'+data.user.avatar+'"/></a><a href="/user?uid='+data.user.id+'"uid='+data.user.id+'>'+data.user.username+'</a> ' +
                '<span class="sign_name">'+(data.user.signature==null||data.user.signature==""?"该友很赖，什么都没有":data.user.signature)+'</span>').append('<input type="hidden" id="others_id" name="othersId" value="'+data.user.id+'"/>');
            $('.copy_title').html(data.atitle);
            $('.copy_body').html(data.acontent);
            $('.copy_body').append('<input type="hidden" id="aid" name="aid" value="'+data.aid+'"/>');
            $('.article_secondary').empty().append('<span>发于&nbsp;'+dateFormat(new Date(data.atime))+'</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>赞&nbsp;'+data.alikeCount+'</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>评论&nbsp;'+data.acommentCount+'</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>阅读&nbsp;'+data.aviews+'</span>');

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
        /*点赞*/
        $('.main').on("click",'.like_btn',function () {
            console.log($('#others_id').val())
            $.post('/article/articleLike',{"aid":$('#aid').val()},function (data) {
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
        /*评论提交*/
        $('.main').on("click",'#art_sbm',function () {
            console.log($('#aid').val());
            console.log($(this).data("cid"));
            $.post('/article/addCommentArticle',{commentContent:editor.txt.html(),aid:$('#aid').val(),parentId:$(this).data("cid")},function (data) {
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

        /*获取文章内容*/
        new Promise((resolve, reject) => {
            $.ajax({
                type: "GET",
                url: "/article/findArticleBy?"+keyword,
                success: function(data){
                    resolve(data.article.aid);
                    console.log(data.attention);
                    showArticleDetails(data.article);
                    createUserInfo(data);
                }
            });
        }).then(value=>{
            $.ajax({
                type: "GET",
                url: "/article/findCommentArticleBy?aid="+value,
                success: function(data){;
                    console.log(data);
                    commentShow(data);
                }
            });
        });
        function commentShow(data) {
            let commentdiv=$('.comment_all').empty();
            let spanDom;
            if(data.itExist=="1"){
                spanDom='<span><a href="#editor" class="reply">回复</a></span>';
            }else {
                spanDom="";
            }
            for(let i of data.commentList){
                let likeSpan;
                if(i.status=='0'){
                    likeSpan='<div class="comment_laud"><span>'+i.comment.commentLikeCount+'</span><span><a href="javascript:;" class="comment_like">赞&nbsp;<i class="fa fa-heart-o" aria-hidden="true"></i></a></span></div> ';
                }else if(i.status=='1'){
                    likeSpan='<div class="comment_laud"  style="color: #FF5722"><span>'+i.comment.commentLikeCount+'</span><span>赞&nbsp;<i class="fa fa-heart-o" aria-hidden="true"></i></span></div> ';
                }
                let div='<div class="comment"><div class="comment_header"> <img src="'+i.comment.user.avatar+'"/> <a href="#">'+i.comment.user.username+'</a> </div> ' +
                    '<div class="comment_body">'+i.comment.commentContent+'</div> ' +
                    '<div class="comment_footer"  data-uid="'+i.comment.user.id+'" data-username="'+i.comment.user.username+'" data-cid="'+i.comment.commentId+'"> <span>'+dateFormat(new Date(i.comment.commentTime))+'</span>'+likeSpan+spanDom+'</div> </div>';
                commentdiv.append(div);
            }
        }
/*        function commentShow(data) {
            let commentdiv=$('.comment_all').empty();
            for(let i of data.commentList){
                let div='<div class="comment"><div class="comment_header"> <img src="'+i.user.avatar+'"/> <a href="#">'+i.user.username+'</a> </div> ' +
                    '<div class="comment_body">'+i.commentContent+'</div> ' +
                    '<div class="comment_footer"  data-uid="'+i.user.id+'" data-username="'+i.user.username+'" data-cid="'+i.commentId+'"> <span>'+dateFormat(new Date(i.commentTime))+'</span>' +
                    ' <div class="comment_laud"> <span>'+i.commentLikeCount+'</span> <span><a href="#">赞&nbsp;<i class="fa fa-heart-o" aria-hidden="true"></i></a></span> </div></div> </div>';
                commentdiv.append(div);
            }
        }*/

        $('.main').on("click",'.reply',function () {
            let username=$(this).parents(".comment_footer").data("username");
            let uid=$(this).parents(".comment_footer").data("uid");
            let cid=$(this).parents(".comment_footer").data("cid");
            editor.txt.html('<p><a href="/user/uid='+uid+'">@'+username+'</a></p>');
            $("#art_sbm").data("cid",cid);
        });
        $('.main').on("click",'.comment_like',function () {
            let cid=$(this).parents(".comment_footer").data("cid");
            $.post("/article/commentLike",{cid:cid},function (data) {
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
        /*日期转换器*/
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