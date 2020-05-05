package com.mytnt.service;

import com.mytnt.dao.UserMapper;
import com.mytnt.pojo.RegisterLog;
import com.mytnt.pojo.User;
import com.mytnt.pojo.UserLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserName(String username) {
        return userMapper.findUserName(username);
    }

    @Override
    public User findtelePhone(String telephone) {
        return userMapper.findtelePhone(telephone);
    }

    @Override
    public User findEmail(String email) {
        return userMapper.findEmail(email);
    }

    @Override
    public User findQQ(String qq) {
        return userMapper.findQQ(qq);
    }

    @Override
    public User findUserId(Integer userId) {
        return userMapper.findUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addUser(User user) {
        try {
            userMapper.addUser(user);
            userMapper.addUserRoles(user.getId());
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            //手动提交事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }

    }

    @Override
    public int updateLoginUser(User user) {
        return userMapper.updateLoginUser(user);
    }

    @Override
    public RegisterLog findtelePhoneCount(String telephone) {
        return userMapper.findtelePhoneCount(telephone);
    }

    @Override
    public int findRegisterLogIp(String ip) {
        return userMapper.findRegisterLogIp(ip);
    }
    @Override
    public int addRegisterLog(RegisterLog registerLog) {
        return userMapper.addRegisterLog(registerLog);
    }

    @Override
    public int updateRegisterLog(RegisterLog registerLog) {
        return userMapper.updateRegisterLog(registerLog);
    }

    @Override
    public User findUserArticle(Integer uid) {
        return userMapper.findUserArticle(uid);
    }

    @Override
    public int findUserArticleCount(Integer id) {
        return userMapper.findUserArticleCount(id);
    }

    @Override
    public int findUserArticleViews(Integer id) {
        return userMapper.findUserArticleViews(id);
    }

    @Override
    public int findUserArticleComments(Integer id) {
        return userMapper.findUserArticleComments(id);
    }

    @Override
    public int findUserArticleLikes(Integer id) {
        return userMapper.findUserArticleLikes(id);
    }

    @Override
    public int findUserAttentionCount(Integer othersId) {
        return userMapper.findUserAttentionCount(othersId);
    }

    @Override
    public int findAttentionCount(Integer userId) {
        return userMapper.findAttentionCount(userId);
    }

    @Override
    public String findAttention(Integer othersId, Integer selfId) {
        return userMapper.findAttention(othersId,selfId);
    }

    @Override
    public int addAttention(Integer othersId, Integer selfId) {
        String attention = userMapper.findAttention(othersId, selfId);
        if (attention==null||attention==""){
            return userMapper.addAttention(othersId,selfId);
        }else if ("2".equals(attention)){
            return userMapper.updateAddAttention(1,othersId, selfId);
        }else {
            return 0;
        }
    }

    @Override
    public int cancleAttention(Integer othersId, Integer selfId) {
        return userMapper.cancleAttention(2,othersId,selfId);
    }
    @Override
    public List<User> findAttentionUserInfo(Integer userId){
        return userMapper.findAttentionUserInfo(userId);
    }

    @Override
    public List<User> findFollowersUserInfo(Integer userId) {
        return userMapper.findFollowersUserInfo(userId);
    }

    @Override
    public int addUserLog(UserLog userLog) {
        return userMapper.addUserLog(userLog);
    }

    @Override
    public int updateMissNumber(String telephone) {
        Integer missNumber=userMapper.findtelePhone(telephone).getMissNumber();
        return userMapper.updateMissNumber(++missNumber,telephone);
    }

    @Override
    public int updateUsernaeOrEmailOrQQ(String username, String email, String qq,String file,Integer userId) {
        return userMapper.updateUsernaeOrEmailOrQQ(username, email, qq,file,userId);
    }


}
