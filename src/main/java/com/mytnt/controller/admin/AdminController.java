package com.mytnt.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.mytnt.pojo.Roles;
import com.mytnt.pojo.User;
import com.mytnt.service.AdminService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    @Autowired
    AdminService adminService;
    @RequestMapping("adminView")
    public String adminView(){
        return "admin/admin";
    }

    @RequestMapping("adminFindUser")
    @ResponseBody
    public Object adminFindUser(@RequestParam(value = "page",required = false)int page,@RequestParam(value = "limit",required = false)int limit
            ,@RequestParam(value = "username",required = false)String  username,@RequestParam(value = "telephone",required = false)String  telephone){
        Map<String, Object> resultMap = new HashMap<>();
        System.out.println(username);
        System.out.println(telephone);
        List<User> userList=adminService.adminFindUser(username,telephone,page,limit);
        for (User user:userList){
            System.out.println(user);
        }
        resultMap.put("data",userList);
        resultMap.put("code",0);
        resultMap.put("msg","成功");
        resultMap.put("count",adminService.adminUserCount());
        return JSONObject.toJSONString(resultMap);
    }
    @RequestMapping("adminFindRoles")
    @ResponseBody
    public Object adminFindRoles(){
        Map<String, Object> resultMap = new HashMap<>();
        List<Roles> rolesList=adminService.adminFindRoles();
        resultMap.put("data",rolesList);
        return JSONObject.toJSONString(resultMap);
    }
    @RequestMapping(value = "adminUpdateUserRoles",method = RequestMethod.POST)
    @ResponseBody
    public Object adminUpdateUserRoles(@RequestParam("userId")Integer userId,@RequestParam("rolesId")Integer rolesId){
        Map<String, Object> resultMap = new HashMap<>();
        if(userId==1){
            resultMap.put("code",2);
            resultMap.put("message","该用户不允许修改");
        }else if(adminService.adminUpdateUserRoles(userId, rolesId)>0){
            resultMap.put("code",1);
            resultMap.put("message","已更新用户角色");
        }else {
            resultMap.put("code",2);
            resultMap.put("message","更新失败");
        }

        return JSONObject.toJSONString(resultMap);
    }
}
