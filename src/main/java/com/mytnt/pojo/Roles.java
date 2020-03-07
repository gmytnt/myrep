package com.mytnt.pojo;

import java.io.Serializable;

/**
 * Created by meiyan on 2020/3/7.
 */
public class Roles implements Serializable {
    private Integer roleId;
    private String roleName;

    @Override
    public String toString() {
        return "Roles{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
