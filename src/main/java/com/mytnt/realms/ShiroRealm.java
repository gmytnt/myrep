package com.mytnt.realms;

import com.mytnt.dao.UserMapper;
import com.mytnt.pojo.Roles;
import com.mytnt.pojo.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by meiyan on 2020/3/1.
 */
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    UserMapper userMapper;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1. 从 PrincipalCollection 中来获取登录用户的信息
        User principal = (User) principalCollection.getPrimaryPrincipal();
//        System.out.println(principal);
//        System.out.println(principal.getRoles());
        List<Roles> rolesList=principal.getRoles();
        //2. 利用登录的用户的信息来用户当前用户的角色或权限(可能需要查询数据库)
        Set<String> roles = new HashSet<>();
        for(Roles role:rolesList){
            if("超级管理员".equals(role.getRolesName())){
                roles.add("superAdmin");
            }else if("管理员".equals(role.getRolesName())){
                roles.add("admin");
            }
        }


        //3. 创建 SimpleAuthorizationInfo, 并设置其 reles 属性.
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);

        //4. 返回 SimpleAuthorizationInfo 对象.
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("doGetAuthenticationInfo");
        //1. 把 AuthenticationToken 转换为 UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        //2. 从 UsernamePasswordToken 中来获取 username
        String telephone = upToken.getUsername();
        System.out.println("doGetAuthenticatifo"+telephone);
        User user=userMapper.findtelePhone(telephone);
        if(user==null){
            throw new UnknownAccountException("用户不存在或者密码不正确!");
        }
        if("5".equals(user.getMissNumber())&&new Date().getTime()-user.getMissTime().getTime()<1000*1*60*60*5){
            throw new LockedAccountException("用户暂时无法登录");
        }
        //6. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为: SimpleAuthenticationInfo
        //以下信息是从数据库中获取的.
        //1). principal: 认证的实体信息. 可以是 username, 也可以是数据表对应的用户的实体类对象.
        Object principal = user;
        //2). credentials: 密码.
        Object credentials = user.getPassword(); //"fc1709d0a95a6be30bc5926fdb7f22f4";

        //3). realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
        String realmName = getName();
        //4). 盐值.
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());

        SimpleAuthenticationInfo info = null; //new SimpleAuthenticationInfo(principal, credentials, realmName);
        info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
        System.out.println(info);
        return info;
    }
}
