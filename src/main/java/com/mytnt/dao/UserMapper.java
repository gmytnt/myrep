package com.mytnt.dao;

import com.mytnt.pojo.RegisterLog;
import com.mytnt.pojo.User;
import com.mytnt.pojo.UserLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by meiyan on 2020/2/28.
 */
public interface UserMapper {
    /*查询用户是否注册*/
    public User findUserName(@Param("username") String username);
    /*查询手机号是否注册*/
    public User findtelePhone(@Param("telephone") String telephone);
    /*查询邮箱是否存在*/
    public User findEmail(@Param("email") String email);
    /*查询QQ是否存在*/
    public User findQQ(@Param("qq") String qq);
    /*查用户信息*/
    public User findUserId(@Param("userId") Integer userId);
    /*t添加用户*/
    public int addUser(User addUser);
    /*登录时修改用户*/
    public int updateLoginUser(User addUser);
    /*t添加用户角色*/
    public int addUserRoles(@Param("userId") Integer userId);
    /*查询发送次数*/
    public RegisterLog findtelePhoneCount(@Param("telephone") String telephone);/*查询发送次数*/
    public int findRegisterLogIp(@Param("ip") String ip);
    /*添加获取验证码日志*/
    public int addRegisterLog(RegisterLog registerLog);
    /*跟新获取验证码日志*/
    public int updateRegisterLog(RegisterLog registerLog);
    /*由用户id查询所有文章*/
    public User findUserArticle(@Param("id") Integer id);
    /*统计文章数量*/
    public int findUserArticleCount(@Param("id") Integer id);
    /*查询用户的所有访问*/
    public int findUserArticleViews(@Param("id") Integer id);
    /*查询用户的所有评论*/
    public int findUserArticleComments(@Param("id") Integer id);
    /*查询用户的所有点赞*/
    public int findUserArticleLikes(@Param("id") Integer id);
    /*查询用户的粉丝数量*/
    public int findUserAttentionCount(@Param("othersId") Integer othersId);
    /*查询用户的关注的数量*/
    public int findAttentionCount(@Param("userId") Integer userId);
    /*查询用户之间的关系*/
    public String findAttention(@Param("othersId") Integer othersId,@Param("selfId") Integer selfId);
    /*添加用户之间的关系*/
    public int addAttention(@Param("othersId") Integer othersId,@Param("selfId") Integer selfId);
    /*更新用户之间为关注*/
    public int updateAddAttention(@Param("attentionStatus") Integer attentionStatus,@Param("othersId") Integer othersId,@Param("selfId") Integer selfId);
    /*更新用户之间为取消*/
    public int cancleAttention(@Param("attentionStatus") Integer attentionStatus,@Param("othersId") Integer othersId,@Param("selfId") Integer selfId);
    /*查询用户的关注的所有人信息*/
    public List<User> findAttentionUserInfo(@Param("userId") Integer userId);
    /*查询用户的粉丝的所有人信息*/
    public List<User> findFollowersUserInfo(@Param("userId") Integer userId);

    int addUserLog(UserLog userLog);

    int updateMissNumber(@Param("missNumber") Integer missNumber,@Param("telephone")String telephone);

    int updateUsernaeOrEmailOrQQ(@Param("username")String username,@Param("email")String email,@Param("qq")String qq,
                                 @Param("file")String file,@Param("userId")Integer userId);
}
