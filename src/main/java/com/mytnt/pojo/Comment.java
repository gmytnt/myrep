package com.mytnt.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by meiyan on 2020/3/4.
 */
public class Comment implements Serializable {
    private Integer commentId;
    private Integer userId;
    private Integer articleId;
    private Integer commentLikeCount;
    private Date commentTime;
    private String commentContent;
    private Integer parentCommentId;
    private User user;
//    private List<Comment> comments;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getCommentLikeCount() {
        return commentLikeCount;
    }

    public void setCommentLikeCount(Integer commentLikeCount) {
        this.commentLikeCount = commentLikeCount;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", articleId=" + articleId +
                ", commentLikeCount=" + commentLikeCount +
                ", commentTime=" + commentTime +
                ", commentContent='" + commentContent + '\'' +
                ", parentCommentId=" + parentCommentId +
                '}';
    }
}
