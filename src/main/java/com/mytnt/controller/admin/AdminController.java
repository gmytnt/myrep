package com.mytnt.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.mytnt.pojo.Article;
import com.mytnt.pojo.Comment;
import com.mytnt.pojo.Roles;
import com.mytnt.pojo.User;
import com.mytnt.service.AdminService;
import org.apache.ibatis.annotations.Param;
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

@Controller
@RequestMapping("/admin/")
public class AdminController {
    @Autowired
    AdminService adminService;
    @RequestMapping("adminView")
    public String adminView(){
        return "admin/admin";
    }

    @RequestMapping("adminFindUser")
    @ResponseBody
    public Object adminFindUser(@RequestParam(value = "page",required = false)Integer page,@RequestParam(value = "limit",required = false)Integer limit
            ,@RequestParam(value = "username",required = false)String  username,@RequestParam(value = "telephone",required = false)String  telephone){
        Map<String, Object> resultMap = new HashMap<>();
        System.out.println(username);
        System.out.println(telephone);
        List<User> userList=adminService.adminFindUser(username,telephone,page,limit);
        for (User user:userList){
            System.out.println(user);
        }
        resultMap.put("data",userList);
        resultMap.put("code",0);
        resultMap.put("msg","成功");
        resultMap.put("count",adminService.adminUserCount(username,telephone));
        return JSONObject.toJSONString(resultMap);
    }
    @RequestMapping("adminFindRoles")
    @ResponseBody
    public Object adminFindRoles(){
        Map<String, Object> resultMap = new HashMap<>();
        List<Roles> rolesList=adminService.adminFindRoles();
        resultMap.put("data",rolesList);
        return JSONObject.toJSONString(resultMap);
    }
    @RequestMapping(value = "adminUpdateUserRoles",method = RequestMethod.POST)
    @ResponseBody
    public Object adminUpdateUserRoles(@RequestParam("userId")Integer userId,@RequestParam("rolesId")Integer rolesId){
        Map<String, Object> resultMap = new HashMap<>();
        if(userId==1){
            resultMap.put("code",2);
            resultMap.put("message","该用户不允许修改");
        }else if(adminService.adminUpdateUserRoles(userId, rolesId)>0){
            resultMap.put("code",1);
            resultMap.put("message","已更新用户角色");
        }else {
            resultMap.put("code",2);
            resultMap.put("message","更新失败");
        }

        return JSONObject.toJSONString(resultMap);
    }

    @RequestMapping("adminArticleList")
    @ResponseBody
    public Object adminArticleList(@RequestParam(value = "page",required = false)int page,@RequestParam(value = "limit",required = false)int limit
            ,@RequestParam(value = "username",required = false)String  username,@RequestParam(value = "title",required = false)String  title
            ,@RequestParam(value = "content",required = false)String  content,@RequestParam(value = "astatus",required = false)String  astatus){
        Map<String, Object> resultMap = new HashMap<>();
        System.out.println(username);
        System.out.println(astatus);
        List<Article> articleList=adminService.adminArticleList(username, title, content,astatus,page, limit);
        resultMap.put("data",articleList);
        resultMap.put("code",0);
        resultMap.put("msg","成功");
        resultMap.put("count",adminService.adminArticleCount(username, title, content,astatus));
        return JSONObject.toJSONString(resultMap);
    }
    @RequestMapping("adminFindArticleBy")
    @ResponseBody
    public Object adminFindArticleBy(@RequestParam(value = "aid",required = false)Integer aid){
        Map<String, Object> resultMap = new HashMap<>();
        Article article=adminService.adminFindArticleBy(aid);
        resultMap.put("article",article);
        return JSONObject.toJSONString(resultMap);
    }

    /*查询文章的所有的评论*/
    @RequestMapping("adminFindCommentArticle")
    @ResponseBody
    public Object adminFindCommentArticle(@RequestParam(value = "aid")Integer aid){
        Map<String, Object> resultMap = new HashMap<>();
        List<Comment> commentList=adminService.adminFindCommentArticle(aid);
        resultMap.put("commentList",commentList);
        return JSONObject.toJSONString(resultMap);
    }
    @RequestMapping(value = "adminUpdateArticleStatus",method = RequestMethod.POST)
    @ResponseBody
    public Object adminUpdateArticleStatus(@RequestParam("astatus")Integer astatus,@RequestParam("aid")Integer aid){
        Map<String, Object> resultMap = new HashMap<>();
       if(adminService.adminUpdateArticleStatus(aid, astatus)>0){
            resultMap.put("code",1);
            resultMap.put("message","文章状态改变");
        }else {
            resultMap.put("code",2);
            resultMap.put("message","更改状态失败");
        }
        return JSONObject.toJSONString(resultMap);
    }
}
