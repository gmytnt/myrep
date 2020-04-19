package com.mytnt.service;

import com.mytnt.pojo.Article;
import com.mytnt.pojo.ArticleSort;
import com.mytnt.pojo.Comment;

import java.util.List;

/**
 * Created by meiyan on 2020/2/28.
 */
public interface ArticleService {
    /*查询所有分类*/
    public List<ArticleSort> findSortAll();
    /*查询全部文章*/
    public List<Article> findArticleAll(String userId,Integer page,Integer limit,String searchName);
    /*查询sortId分类*/
    public Article findArticleBy(Integer aid,String sortName,String sessionId);
    /*添加文章*/
    public int addArticle(Article article,String sort);
    /*统计文章数量*/
    public int  findArticleCount(String userId,String searchName);
    /*统计分类文章数量*/
    public List<Article> findArticleSort(String sortName,Integer page,Integer limit,String searchName);
    /*统计分类文章数量*/
    public int  findArticleSortCount(String sortName,String searchName);
    /*由文章和用户id判断是否点赞*/
    public int findUserIfLike(Integer articleId,Integer userId);
    /*判断文章id是否能被查看*/
    public Article findArticleView(Integer articleId);
    /*添加评论*/
    public int addCommentArticle(Comment comment,Article article);
    /*查询当前文章所有评论*/
    public List<Comment> findCommentArticle(Integer articleId);
    /*查询单条评论信息*/
    public Comment findCommentArticleBy(Integer commentId);
    /*点赞*/
    public int addArticleLike(Article article,Integer userId);
    /*由评论和用户id判断是否点赞*/
    public int findCommentIfLike(Integer commentId,Integer userId);
    /*添加评论点赞记录*/
    public int addCommentLike(Comment comment,Integer userId);
}
