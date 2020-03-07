package com.mytnt.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.mytnt.pojo.Article;
import com.mytnt.pojo.ArticleSort;
import com.mytnt.pojo.Comment;
import com.mytnt.pojo.User;
import com.mytnt.service.ArticleService;
import com.mytnt.service.UserService;
import com.mytnt.tool.Tools;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by meiyan on 2020/2/23.
 */
@Controller
@RequestMapping("/article/")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;
    @RequestMapping("compiler")
    public String compiler(){
        return "compiler";
    }
    /*查询所有分类*/
    @RequestMapping("sortAll")
    @ResponseBody
    public Object sortAll(){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<ArticleSort> articleSortList=articleService.findSortAll();
//        System.out.println(articleSortList);
        resultMap.put("sort",articleSortList);
        return JSONObject.toJSONString(resultMap);
    }
    @RequestMapping("details")
    public String details(){
        return "details";
    }
    /*添加文章*/
    @RequestMapping(value = "savearticle",method = RequestMethod.POST)
    @ResponseBody
    public Object saveArticle(@RequestParam String title,@RequestParam String sort,@RequestParam String content){
        Map<String, String> resultMap = new HashMap<String, String>();
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getPrincipal();
        if(user==null){
            resultMap.put("code","3");
            resultMap.put("message","您还未登录");
        }else {
            Article article=new Article();
            article.setAcontent(content);
            article.setAtitle(Tools.replaceString(title));
            article.setUserId(user.getId());
            int num=articleService.addArticle(article,sort);
            if(num>0){
                resultMap.put("code","1");
                resultMap.put("message","文章发布成功，审核中");
            }else {
                resultMap.put("code","2");
                resultMap.put("message","文章发布失败，再试一次");
            }
        }

        return JSONObject.toJSONString(resultMap);
    }
    /*查询所有文章*/
    @RequestMapping("findArticleAll")
    @ResponseBody
    public Object findArticleAll(){
        Map<String, List<Article>> resultMap = new HashMap<>();
        List<Article> article = articleService.findArticleAll(null);
        resultMap.put("article",article);
        return JSONObject.toJSONString(resultMap);
    }
    /*查询用户所有文章*/
    @RequestMapping("findArticleUserAll")
    @ResponseBody
    public Object findArticleUserAll(@RequestParam(value = "uid",required=true)String userId){
        Map<String, List<Article>> resultMap = new HashMap<>();
        if(userId!=null&&userId!=""){
            List<Article> article = articleService.findArticleAll(userId);
            resultMap.put("article",article);
        }
        return JSONObject.toJSONString(resultMap);
    }
    /*查询文章详情*/
    @RequestMapping("findArticleBy")
    @ResponseBody
    public Object findArticleBy(@RequestParam(value = "aid")String aid,@RequestParam(value = "sort",required=false)String sort){
        Map<String, Object> resultMap = new HashMap<>();
        Subject currentUser = SecurityUtils.getSubject();
        /*会话ID*/
        String sessionId= (String) currentUser.getSession().getId();
        User user = (User) currentUser.getPrincipal();
        Article article=articleService.findArticleBy(Integer.parseInt(aid),sort,sessionId);
        if(article!=null){

            resultMap.put("article",article);
            resultMap.put("articleLike","0");
            int articleUserId=article.getUserId();
            if(user==null){
                resultMap.put("attention","2");
            }else {
                int userId=user.getId();
                if(articleUserId==userId){
                    resultMap.put("attention","55");
                }else {
                    /*查询用户之间的关系*/
                    String string=userService.findAttention(articleUserId,userId);
                    resultMap.put("attention",string);
                }
                int likeStatus=articleService.findUserIfLike(article.getAid(),userId);
                resultMap.put("articleLike",likeStatus);

            }
            /*统计文章数量*/
            int userArticleCount=userService.findUserArticleCount(articleUserId);
            /*查询用户的所有访问*/
            int userArticleViews=userService.findUserArticleViews(articleUserId);
            /*查询用户的所有评论*/
            int userArticleComments=userService.findUserArticleComments(articleUserId);
            /*查询用户的所有点赞*/
            int userArticleLikes=userService.findUserArticleLikes(articleUserId);
            /*查询用户的所有关注*/
            int userAttentionCount=userService.findUserAttentionCount(articleUserId);
            resultMap.put("userArticleCount",userArticleCount);
            resultMap.put("userArticleViews",userArticleViews);
            resultMap.put("userArticleComments",userArticleComments);
            resultMap.put("userArticleLikes",userArticleLikes);
            resultMap.put("userAttentionCount",userAttentionCount);
        }
        return JSONObject.toJSONString(resultMap);
    }
    /*查询文章的所有的评论*/
    @RequestMapping("findCommentArticleBy")
    @ResponseBody
    public Object findCommentArticleBy(@RequestParam(value = "aid")Integer aid){
        Map<String, Object> resultMap = new HashMap<>();
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getPrincipal();
        Article article=articleService.findArticleView(aid);
        if(article!=null){
            List<Comment> commentList = articleService.findCommentArticle(aid);
//            resultMap.put("commentList",commentList);
//            System.out.println(commentList);
            List<Map> mapList=new ArrayList<Map>();
            if(user==null){
                /*作为评价是否能显示回复依据，itExist为1表示可显示，2表示不能显示*/
                resultMap.put("itExist","2");
                for (Comment comment:commentList){
                    Map<String, Object> result = new HashMap<>();
                    result.put("comment",comment);
                    result.put("status",0);
                    mapList.add(result);
                }
                resultMap.put("commentList",mapList);
            }else {
                int userId=user.getId();
                resultMap.put("itExist","1");
                for (Comment comment:commentList){
                    Map<String, Object> result = new HashMap<>();
                    result.put("comment",comment);
                    /*判断当前用户是否点赞了评论*/
                    int num=articleService.findUserIfLike(comment.getCommentId(),userId);
                    result.put("status",num);
                    mapList.add(result);
                    System.out.println(num);
                }
                resultMap.put("commentList",mapList);
            }
        }
        return JSONObject.toJSONString(resultMap);
    }
    /*添加评论*/
    @RequestMapping(value = "addCommentArticle",method = RequestMethod.POST)
    @ResponseBody
    public Object addCommentArticle(@RequestParam(value = "aid")Integer aid,@RequestParam(value = "commentContent")String commentContent,@RequestParam(value = "parentId",required = false)String parentId){
        Map<String, Object> resultMap = new HashMap<>();
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getPrincipal();
        if(user==null){
            resultMap.put("code","3");
            resultMap.put("message","您还未登录");
        }else {
            Article article = articleService.findArticleView(aid);
            if (article != null) {
                int acommentcCount = article.getAcommentCount();
                Comment comment = new Comment();
                comment.setArticleId(article.getAid());
                comment.setCommentContent(commentContent);
                comment.setUserId(user.getId());
                if (parentId != null) {
                    comment.setParentCommentId(Integer.parseInt(parentId));
                }
                article.setAcommentCount(++acommentcCount);
                int num = articleService.addCommentArticle(comment, article);
                if (num > 0) {
                    resultMap.put("code", "1");
                    resultMap.put("message", "发表成功");
                } else {
                    resultMap.put("code", "2");
                    resultMap.put("message", "发表失败");
                }
            }else {
                resultMap.put("code", "2");
                resultMap.put("message", "文章不存在，发表失败");
            }
        }
        return JSONObject.toJSONString(resultMap);
    }
    /*文章点赞*/
    @RequestMapping(value = "articleLike",method = RequestMethod.POST)
    @ResponseBody
    public Object articleLike(@RequestParam(value = "aid")Integer aid){
        Map<String, Object> resultMap = new HashMap<>();
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getPrincipal();
        if (user==null) {
            resultMap.put("code","3");
            resultMap.put("message","您还未登录");
        }else {
            Article article=articleService.findArticleView(aid);
            if(article!=null){
                int alikeCount= article.getAlikeCount();
                article.setAlikeCount(++alikeCount);
                int num = articleService.addArticleLike(article,user.getId());
                if (num > 0) {
                    resultMap.put("code", "1");
                    resultMap.put("message", "已点赞");
                } else {
                    resultMap.put("code", "2");
                    resultMap.put("message", "点赞失败");
                }
            }else {
                resultMap.put("code", "2");
                resultMap.put("message", "文章不存在，点赞失败");
            }
        }

        return JSONObject.toJSONString(resultMap);
    }
    /*评论点赞*/
    @RequestMapping(value = "commentLike",method = RequestMethod.POST)
    @ResponseBody
    public Object commentLike(@RequestParam(value = "cid")Integer cid){
        Map<String, Object> resultMap = new HashMap<>();
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getPrincipal();
        if (user==null) {
            resultMap.put("code","3");
            resultMap.put("message","您还未登录");
        }else {
            Comment comment=articleService.findCommentArticleBy(cid);
            if(comment!=null){
                int likeCount= comment.getCommentLikeCount();
                comment.setCommentLikeCount(++likeCount);
                int num = articleService.addCommentLike(comment,user.getId());
                if (num > 0) {
                    resultMap.put("code", "1");
                    resultMap.put("message", "已点赞");
                } else {
                    resultMap.put("code", "2");
                    resultMap.put("message", "点赞失败");
                }
            }else {
                resultMap.put("code", "2");
                resultMap.put("message", "评论不存在，点赞失败");
            }
        }

        return JSONObject.toJSONString(resultMap);
    }



}
