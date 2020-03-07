package com.mytnt.pojo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by meiyan on 2020/2/28.
 */
public class RegisterLog implements Serializable{
    private Integer id;
    private String ipAddress;
    private String telephone;
    private int frequency;
    private Date registerTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    @Override
    public String toString() {
        return "RegisterLog{" +
                "id=" + id +
                ", ipAddress='" + ipAddress + '\'' +
                ", telephone='" + telephone + '\'' +
                ", frequency='" + frequency + '\'' +
                ", registerTime=" + registerTime +
                '}';
    }
}
