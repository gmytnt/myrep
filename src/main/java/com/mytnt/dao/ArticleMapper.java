package com.mytnt.dao;

import com.mytnt.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by meiyan on 2020/2/28.
 */
public interface ArticleMapper {
    /*查询所有分类*/
    public List<ArticleSort> findSortAll();
    /*查询sortId分类*/
    public ArticleSort findSortById(@Param("sortId")Integer sortId);
    /*查询全部文章*/
    public List<Article> findArticleAll(@Param("userId")String userId);
    /*查询sortId分类*/
    public Article findArticleBy(@Param("aid")Integer aid,@Param("sortName")String sortName);

    /*添加文章*/
    public int addArticle(Article article);
    public int addArticleCorrespondSort(@Param("articleId")Integer articleId,@Param("articleSortId")Integer articleSortId);
    /*由文章和用户id判断是否点赞*/
    public int findUserIfLike(@Param("articleId")Integer articleId,@Param("userId")Integer userId);
    /*判断文章是否能被查看*/
    public Article findArticleView(@Param("articleId")Integer articleId);
    /*添加评论*/
    public int addCommentArticle(Comment comment);
    /*更新文章信息*/
    public int updateArticle(Article article);
    /*查询当前文章所有评论*/
    public List<Comment> findCommentArticle(@Param("articleId")Integer articleId);
    /*查询单条评论信息*/
    public Comment findCommentArticleBy(@Param("commentId")Integer commentId);
    /*查看当前会话阅读日志*/
    public int findViewLog(@Param("sessionId")String sessionId,@Param("articleId")Integer articleId);
    /*添加当前会话阅读日志*/
    public int addViewLog(@Param("sessionId")String sessionId,@Param("articleId")Integer articleId);
    /*添加文章点赞记录*/
    public int addArticleLike(@Param("articleId")Integer articleId,@Param("userId")Integer userId);
    /*由评论和用户id判断是否点赞*/
    public int findCommentIfLike(@Param("commentId")Integer commentId,@Param("userId")Integer userId);
    /*添加评论点赞记录*/
    public int addCommentLike(@Param("commentId")Integer commentId,@Param("userId")Integer userId);
    /*更新评论点赞数量*/
    public int updateCommentLike(Comment comment);

}
