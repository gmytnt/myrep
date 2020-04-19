package com.mytnt.service;
import com.mytnt.dao.ArticleMapper;
import com.mytnt.dao.UserMapper;
import com.mytnt.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public List<ArticleSort> findSortAll() {
        return articleMapper.findSortAll();
    }

    @Override
    public List<Article> findArticleAll(String userId,Integer page,Integer limit,String searchName) {
        return articleMapper.findArticleAll(userId,(page-1)*limit,limit,searchName);
    }

    @Override
    public Article findArticleBy(Integer aid,String sortName,String sessionId) {
        Article article=articleMapper.findArticleBy(aid,sortName);
        if(article!=null){
            int articleId=article.getAid();
            int num=articleMapper.findViewLog(sessionId,articleId);
            int aviews=article.getAviews();
            if(num==0){
                article.setAviews(++aviews);
                articleMapper.addViewLog(sessionId,articleId);
                articleMapper.updateArticle(article);
            }

        }
        return article;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addArticle(Article article, String sort) {
        try {
            articleMapper.addArticle(article);
            Integer aid=article.getAid();
            String[] arr=sort.split(",");
            for(int i=0;i<arr.length;i++){
                Integer aSortId= Integer.parseInt(arr[i]);
//                if(articleMapper.findSortById(aSortId)==null){
//                    throw new Exception("没有找到分类");
//                }
                articleMapper.addArticleCorrespondSort(aid,aSortId);
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            //手动提交事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }

    }

    @Override
    public int findArticleCount(String userId,String searchName) {
        return articleMapper.findArticleCount(userId,searchName);
    }

    @Override
    public List<Article> findArticleSort(String sortName, Integer page, Integer limit,String searchName) {
        return articleMapper.findArticleSort(sortName, (page-1)*limit,limit,searchName);
    }

    @Override
    public int findArticleSortCount(String sortName,String searchName) {
        return articleMapper.findArticleSortCount(sortName,searchName);
    }

    @Override
    public int findUserIfLike(Integer articleId, Integer userId) {
        return articleMapper.findUserIfLike(articleId, userId);
    }

    @Override
    public Article findArticleView(Integer articleId) {
        return articleMapper.findArticleView(articleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addCommentArticle(Comment comment,Article article) {
        try {
            articleMapper.addCommentArticle(comment);
            articleMapper.updateArticle(article);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            //手动提交事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }

    @Override
    public List<Comment> findCommentArticle(Integer articleId) {
        return articleMapper.findCommentArticle(articleId);
    }

    @Override
    public Comment findCommentArticleBy(Integer commentId) {
        return articleMapper.findCommentArticleBy(commentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addArticleLike(Article article, Integer userId) {
        try {
            articleMapper.addArticleLike(article.getAid(),userId);
            articleMapper.updateArticle(article);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            //手动提交事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }

    @Override
    public int findCommentIfLike(Integer commentId, Integer userId) {
        return articleMapper.findUserIfLike(commentId,userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addCommentLike(Comment comment , Integer userId) {
        try {
            articleMapper.addCommentLike(comment.getCommentId(),userId);
            articleMapper.updateCommentLike(comment);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            //手动提交事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }
}
