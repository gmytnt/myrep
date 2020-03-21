package com.mytnt.dao;

import com.mytnt.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台操作
 */
public interface AdminMapper {
    /*查询所有用户*/
    public List<User> adminFindUser(@Param("username")String username,@Param("telephone")String telephone,@Param("page") Integer page,@Param("limit") Integer limit);
    /*统计用户数量*/
    public int adminUserCount();
    /*查询角色*/
    public List<Roles> adminFindRoles();
    /*修改用户角色*/
    public int adminUpdateUserRoles(@Param("userId")Integer userId,@Param("rolesId")Integer rolesId);
}
