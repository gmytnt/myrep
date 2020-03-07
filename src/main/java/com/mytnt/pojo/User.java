package com.mytnt.pojo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class User implements Serializable{
    private Integer id;
    private String username;
    private String password;
    private String telephone;
    private String salt;
    private String createIp;
    private Date createTime;
    private String loginIp;
    private Date loginTime;
    private String status;
    private String email;
    private Integer sex;
    private String qq;
    private String signature;
    private String avatar;
    private Integer missNumber;
    private Date missTime;
    private List<Article> articles;
    private Roles roles;

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getMissNumber() {
        return missNumber;
    }

    public void setMissNumber(Integer missNumber) {
        this.missNumber = missNumber;
    }

    public Date getMissTime() {
        return missTime;
    }

    public void setMissTime(Date missTime) {
        this.missTime = missTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                ", salt='" + salt + '\'' +
                ", createIp='" + createIp + '\'' +
                ", createTime=" + createTime +
                ", loginIp='" + loginIp + '\'' +
                ", loginTime=" + loginTime +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                ", qq='" + qq + '\'' +
                ", signature='" + signature + '\'' +
                ", avatar='" + avatar + '\'' +
                ", missNumber=" + missNumber +
                ", missTime=" + missTime +
                '}';
    }
}
