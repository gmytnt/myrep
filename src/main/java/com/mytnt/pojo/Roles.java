package com.mytnt.pojo;

import java.io.Serializable;

/**
 * Created by meiyan on 2020/3/7.
 */
public class Roles implements Serializable {
    private Integer rolesId;
    private String rolesName;

    public Integer getRolesId() {
        return rolesId;
    }

    public void setRolesId(Integer rolesId) {
        this.rolesId = rolesId;
    }

    public String getRolesName() {
        return rolesName;
    }

    public void setRolesName(String rolesName) {
        this.rolesName = rolesName;
    }
}
