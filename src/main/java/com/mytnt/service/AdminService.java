package com.mytnt.service;

import com.mytnt.pojo.*;

import java.util.List;

public interface AdminService {
    /*查询所有用户*/
    public List<User> adminFindUser(String username,String telephone,Integer page,Integer limit);
    /*统计用户数量*/
    public int adminUserCount(String username,String telephone);
    /*查询角色*/
    public List<Roles> adminFindRoles();
    /*修改用户角色*/
    public int adminUpdateUserRoles(Integer userId,Integer rolesId);
    /*查询所有文章*/
    public List<Article> adminArticleList(String username,String title,String content,String astatus,Integer page,Integer limit);
    /*统计所有文章数量*/
    public int adminArticleCount(String username,String title,String content,String astatus);
    /*查询文章Id*/
    public Article adminFindArticleBy(Integer aid);
    /*查询当前文章所有评论*/
    public List<Comment> adminFindCommentArticle(Integer articleId);
    /*修改文章状态*/
    public int adminUpdateArticleStatus(Integer aid,Integer astatus);
}
