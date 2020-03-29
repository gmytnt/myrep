package com.mytnt.dao;

import com.mytnt.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台操作
 */
public interface AdminMapper {
    /*查询所有用户*/
    public List<User> adminFindUser(@Param("username")String username,@Param("telephone")String telephone,@Param("page") Integer page,@Param("limit") Integer limit);
    /*统计用户数量*/
    public int adminUserCount(@Param("username")String username,@Param("telephone")String telephone);
    /*查询角色*/
    public List<Roles> adminFindRoles();
    /*修改用户角色*/
    public int adminUpdateUserRoles(@Param("userId")Integer userId,@Param("rolesId")Integer rolesId);
    /*查询所有文章*/
    public List<Article> adminArticleList(@Param("username")String username,@Param("title")String title,@Param("content")String content,@Param("astatus")String astatus,@Param("page") Integer page,@Param("limit") Integer limit);
    /*统计所有文章数量*/
    public int adminArticleCount(@Param("username")String username,@Param("title")String title,@Param("content")String content,@Param("astatus")String astatus);
    /*查询文章Id*/
    public Article adminFindArticleBy(@Param("aid")Integer aid);
    /*查询当前文章所有评论*/
    public List<Comment> adminFindCommentArticle(@Param("articleId")Integer articleId);
    /*修改文章状态*/
    public int adminUpdateArticleStatus(@Param("aid")Integer aid,@Param("astatus")Integer astatus);
 }
