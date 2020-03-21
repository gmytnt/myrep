package com.mytnt.service;

import com.mytnt.dao.AdminMapper;
import com.mytnt.dao.ArticleMapper;
import com.mytnt.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Override
    public List<User> adminFindUser(String username,String telephone,Integer page,Integer limit) {
        return adminMapper.adminFindUser(username,telephone,(page-1)*limit,limit);
    }

    @Override
    public int adminUserCount() {
        return adminMapper.adminUserCount();
    }

    @Override
    public List<Roles> adminFindRoles() {
        return adminMapper.adminFindRoles();
    }

    @Override
    public int adminUpdateUserRoles(Integer userId, Integer rolesId) {
        return adminMapper.adminUpdateUserRoles(userId, rolesId);
    }
}
