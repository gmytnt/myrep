package com.mytnt.service;

import com.mytnt.pojo.RegisterLog;
import com.mytnt.pojo.User;

import java.util.List;

/**
 * Created by meiyan on 2020/2/28.
 */
public interface UserService {
    /*查询用户名是否注册*/
    public User findUserName(String username);
    /*查询手机号是否注册*/
    public User findtelePhone(String telephone);
    /*查询手机号是否注册*/
    public User findUserId(Integer userId);
    /*添加用户*/
    public int addUser(User user);
    /*登录时修改用户*/
    public int updateLoginUser(User user);
    /*查询手机号是否发送给验证码*/
    public RegisterLog findtelePhoneCount(String telephone);
    /*查询当前ip发送几个手机号验证码*/
    public int findRegisterLogIp(String ip);
    /*添加注册日志*/
    public int addRegisterLog(RegisterLog registerLog);
    /*更新注册日志*/
    public int updateRegisterLog(RegisterLog registerLog);
    /*由用户id查询所有文章*/
    public User findUserArticle(Integer id);
    /*统计文章数量*/
    public int findUserArticleCount(Integer id);
    /*查询用户的所有访问*/
    public int findUserArticleViews(Integer id);
    /*查询用户的所有评论*/
    public int findUserArticleComments(Integer id);
    /*查询用户的所有点赞*/
    public int findUserArticleLikes(Integer id);
    /*查询用户的粉丝数量*/
    public int findUserAttentionCount(Integer othersId);
    /*查询用户的关注的数量*/
    public int findAttentionCount(Integer userId);
    /*查询用户之间的关系*/
    public String findAttention(Integer othersId,Integer selfId);
    /*添加关注*/
    public int addAttention(Integer othersId,Integer selfId);
    /*取消关注*/
    public int cancleAttention(Integer othersId,Integer selfId);
    /*查询用户的关注的所有人信息*/
    public List<User> findAttentionUserInfo(Integer userId);
    /*查询用户的粉丝的所有人信息*/
    public List<User> findFollowersUserInfo(Integer userId);

}
