package com.mytnt.pojo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created by meiyan on 2020/3/2.
 */
public class Article implements Serializable {
    private Integer aid;
    private Integer userId;
    private String atitle;
    private String acontent;
    private Date atime;
    private String ainfo;
    private Integer astatus;
    private Integer aviews;
    private Integer acommentCount;
    private Integer alikeCount;
    private User user;
    private List<ArticleSort> articleSorts;

    @Override
    public String toString() {
        return "Article{" +
                "aid=" + aid +
                ", userId=" + userId +
                ", atitle='" + atitle + '\'' +
                ", acontent='" + acontent + '\'' +
                ", atime=" + atime +
                ", ainfo='" + ainfo + '\'' +
                ", astatus=" + astatus +
                ", aviews=" + aviews +
                ", acommentCount=" + acommentCount +
                ", alikeCount=" + alikeCount +
                ", user=" + user +
                ", articleSorts=" + articleSorts +
                '}';
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAtitle() {
        return atitle;
    }

    public void setAtitle(String atitle) {
        this.atitle = atitle;
    }

    public String getAcontent() {
        return acontent;
    }

    public void setAcontent(String acontent) {
        this.acontent = acontent;
    }

    public Date getAtime() {
        return atime;
    }

    public void setAtime(Date atime) {
        this.atime = atime;
    }

    public String getAinfo() {
        return ainfo;
    }

    public void setAinfo(String ainfo) {
        this.ainfo = ainfo;
    }

    public Integer getAstatus() {
        return astatus;
    }

    public void setAstatus(Integer astatus) {
        this.astatus = astatus;
    }

    public Integer getAviews() {
        return aviews;
    }

    public void setAviews(Integer aviews) {
        this.aviews = aviews;
    }

    public Integer getAcommentCount() {
        return acommentCount;
    }

    public void setAcommentCount(Integer acommentCount) {
        this.acommentCount = acommentCount;
    }

    public Integer getAlikeCount() {
        return alikeCount;
    }

    public void setAlikeCount(Integer alikeCount) {
        this.alikeCount = alikeCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ArticleSort> getArticleSorts() {
        return articleSorts;
    }

    public void setArticleSorts(List<ArticleSort> articleSorts) {
        this.articleSorts = articleSorts;
    }
}
