package com.mytnt.service;

import com.mytnt.pojo.*;

import java.util.List;

public interface AdminService {
    /*查询所有用户*/
    public List<User> adminFindUser(String username,String telephone,Integer page,Integer limit);
    /*统计用户数量*/
    public int adminUserCount();
    /*查询角色*/
    public List<Roles> adminFindRoles();
    /*修改用户角色*/
    public int adminUpdateUserRoles(Integer userId,Integer rolesId);
}
