package com.mytnt.pojo;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

public class UserLog {
    private Integer id;
    private String loginIp;
    private Integer uid;
    private Date loginTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return "UserLog{" +
                "id=" + id +
                ", loginIp='" + loginIp + '\'' +
                ", uid=" + uid +
                ", loginTime=" + loginTime +
                '}';
    }
}
