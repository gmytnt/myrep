package com.mytnt.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Constants;
import com.mytnt.pojo.RegisterLog;
import com.mytnt.pojo.User;
import com.mytnt.pojo.UserLog;
import com.mytnt.service.UserService;
import com.mytnt.tool.SendSms;
import com.mytnt.tool.Tools;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import java.text.DateFormat;
import java.util.*;


@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("login")
    public String login(){
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            return "login";
        }else {
            return "redirect:/";
        }
    }

    @RequestMapping("register")
    public String register(){
        return "register";
    }
    @RequestMapping("")
    public String index(){
        return "index";
    }
    @RequestMapping("user")
    public String user(){
        return "user";
    }

    @RequestMapping("findUserTelephone")
    @ResponseBody
    public Object findUserTelephone(@RequestParam("telephone")String telephone){
        Map<String, Object> resultMap = new HashMap<>();
        User user=userService.findtelePhone(telephone);
        if (user==null){
            resultMap.put("code","1");
            resultMap.put("message","当前手机号可以注册");
        }else {
            resultMap.put("code","2");
            resultMap.put("message","该手机号已经被注册了，换其他的手机号吧");
        }
        return JSONObject.toJSONString(resultMap);
    }
    @RequestMapping("findUserName")
    @ResponseBody
    public Object findUserName(@RequestParam("username")String username){
        Map<String, Object> resultMap = new HashMap<>();
        User user=userService.findUserName(username);
        if (user==null){
            resultMap.put("code","1");
            resultMap.put("message","用户名可以使用");
        }else {
            resultMap.put("code","2");
            resultMap.put("message","用户名已经被使用，起个更创意的名字吧");
        }
        return JSONObject.toJSONString(resultMap);
    }

    /*登录*/
    @RequestMapping(value = "dologin",method = RequestMethod.POST)
    @ResponseBody
    public Object doLogin(HttpServletRequest request,@RequestParam(value = "telephone") String telephone,@RequestParam(value = "password") String password){
        Map<String, String> resultMap = new HashMap<String, String>();
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            // 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(telephone, password);
            // rememberme
//            token.setRememberMe(true);
            try {
                // 执行登录.
                currentUser.login(token);
                //修改用户记录
                User user = (User) currentUser.getPrincipal();
                user.setLastIp(user.getLoginIp());
                user.setLastTime(user.getLoginTime());
                user.setLoginIp(Tools.getIpAddr(request));
                user.setLoginTime(new Date());
                user.setMissNumber(0);
                System.out.println(user);
                userService.updateLoginUser(user);
                //添加日志
                UserLog userLog=new UserLog();
                userLog.setLoginIp(Tools.getIpAddr(request));
                userLog.setUid(user.getId());
                userService.addUserLog(userLog);
                resultMap.put("code","1");
                resultMap.put("message","成功登录");
            }catch (LockedAccountException e){
                e.printStackTrace();
                resultMap.put("code","2");
                resultMap.put("message","用户被锁定了,暂时无法登录");
            }catch (UnknownAccountException e){
                e.printStackTrace();
                resultMap.put("code","2");
                resultMap.put("message","用户不存在或者密码错误");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            // 所有认证时异常的父类.
            catch (AuthenticationException e) {
                e.printStackTrace();
                userService.updateMissNumber(telephone);
                resultMap.put("code","2");
                resultMap.put("message","用户不存在或者密码错误");
            }
        }else {
            resultMap.put("code","1");
            resultMap.put("message","您已经登录过了");
        }
        return JSONObject.toJSONString(resultMap);
    }
    /*注册用户*/
    @RequestMapping(value = "doregister",method = RequestMethod.POST)
    @ResponseBody
    public Object doRegister(HttpServletRequest request,String username,
                             String password,String repassword,String telephone,String code){
        Map<String, String> resultMap = new HashMap<String, String>();
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("telephoneCode"));
        //拼接手机验证码
        String teleCode=telephone+code;
//        获取session的手机+验证码
        String recode= String.valueOf(session.getAttribute("telephoneCode"));
        /*判断用户输入的验证码是否正确*/
        if (teleCode==""){
            resultMap.put("code","2");
            resultMap.put("message","请输入短信验证码");
        }else if(teleCode.equalsIgnoreCase(recode)){
            if(telephone==""||!Tools.isMobilePhone(telephone)){
                resultMap.put("code","2");
                resultMap.put("message","请输入手机号");
            }else if(userService.findtelePhone(telephone)!=null){
                resultMap.put("code","2");
                resultMap.put("message","手机号已被注册");
            }else if(username==""){
                resultMap.put("code","2");
                resultMap.put("message","请输入用户名");
            }else if(userService.findUserName(Tools.replaceString(username))!=null){
                resultMap.put("code","2");
                resultMap.put("message","用户名已被注册");
            }else if (password==""||repassword==""){
                resultMap.put("code","2");
                resultMap.put("message","请输入密码");
            }else if (!password.equals(repassword)){
                resultMap.put("code","2");
                resultMap.put("message","两次密码不一致");
            }else {
                // UUID类会生成一个32位的字符串，而且永远不会重复
                String salt= UUID.randomUUID().toString().replaceAll("-","");
                System.out.println(salt);
                String md5Pwd=Tools.md5Pwd(password,salt);
                System.out.println(md5Pwd);
                String ip=Tools.getIpAddr(request);
                User user=new User();
                user.setCreateIp(ip);
                user.setPassword(md5Pwd);
                user.setSalt(salt);
                user.setUsername(username);
                user.setTelephone(telephone);
                user.setAvatar("/static/images/default.jpg");
                if(userService.addUser(user)>0){
                    resultMap.put("code","1");
                    resultMap.put("message","注册成功");
                    session.removeAttribute("telephoneCode");
                }else {
                    resultMap.put("code","2");
                    resultMap.put("message","注册失败");
                }
            }
        }else {
            resultMap.put("code","2");
            resultMap.put("message","验证码不正确");
        }
        return JSONObject.toJSONString(resultMap);
    }


    /*获取手机验证码*/
    @RequestMapping(value = "verification",method = RequestMethod.POST)
    @ResponseBody
    public Object verification(HttpServletRequest request,String telephone, String code){
        final HttpSession session = request.getSession();
        Map<String, String> resultMap = new HashMap<String, String>();
        String recode= String.valueOf(session.getAttribute(Constants.KAPTCHA_SESSION_KEY));
        resultMap.put("code","1");
        if (code==""){
            resultMap.put("code","2");
            resultMap.put("message","请输入验证码");
        }else if(code.equalsIgnoreCase(recode)){
            if(telephone==""||!Tools.isMobilePhone(telephone)){
                resultMap.put("code","2");
                resultMap.put("message","请输入手机号");
            }else if(userService.findtelePhone(telephone)!=null){
                resultMap.put("code","2");
                resultMap.put("message","手机号已被注册");
            }else {
                String ip=Tools.getIpAddr(request);
                System.out.println(userService.findtelePhoneCount(telephone));
                RegisterLog rgl=userService.findtelePhoneCount(telephone);
                Date time=new Date();
                int codeNum = (int)((Math.random()*9+1)*100000);
                System.out.println("手机"+telephone+"验证码"+codeNum);
//                拼接手机验证码
                String teleCodeNum=telephone+codeNum;
                System.out.println("session"+teleCodeNum);
                if(rgl==null){
                    int ipnumber=userService.findRegisterLogIp(ip);
                    /*判断当前ip是否重复输入多个手机号*/
                    if(ipnumber<3){
                        System.out.println(ipnumber+"ip注册数量");
                        RegisterLog registerLog=new RegisterLog();
                        registerLog.setFrequency(1);
                        registerLog.setIpAddress(ip);
                        registerLog.setTelephone(telephone);
//                        String status=SendSms.sendSms(telephone,String.valueOf(codeNum),"5");
//                        System.out.println(status+"返回值");
                        if ("ok".equalsIgnoreCase("ok")){
                            userService.addRegisterLog(registerLog);
                            System.out.println(registerLog);
                            System.out.println("存储短信记录");
                            session.setAttribute("telephoneCode",teleCodeNum);
                            session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
                            resultMap.put("message","短信以发送，注意查收手机验证码");
                        }else {
                            resultMap.put("code","2");
                            resultMap.put("message","短信无法发送，请重新");
                        }
                    }else {
                        resultMap.put("code","2");
                        resultMap.put("message","当前操作过于频繁");
                        System.out.println("当前ip发送多个手机");
                    }
                }else {
                    int frequency=rgl.getFrequency();
                    Date registerTime = rgl.getRegisterTime();
                    /*判断当前手机号是否在两分钟内重新获取密码*/
                    if(time.getTime()-registerTime.getTime()<1000*1*60*2){
                        resultMap.put("code","2");
                        resultMap.put("message","验证码已发送，两分钟后再重新获取");
                        System.out.println("两分钟后再重新获取");
                    }else {
                        /*判断手机号是否多次获取验证码*/
                        if(frequency<3){
                            System.out.println(time.getTime()-registerTime.getTime());
                            System.out.println(60*1*60*1*1000);
                            System.out.println(time.getTime()-registerTime.getTime()<60*1*60*1*1000);
                            if(time.getTime()-registerTime.getTime()<60*1*60*1*1000){
                                rgl.setFrequency(++frequency);
                                System.out.println(rgl.getFrequency());
                            }else {
                                rgl.setFrequency(1);
                                rgl.setIpAddress(ip);
                            }
                            String status=SendSms.sendSms(telephone,String.valueOf(codeNum),"5");
                            if ("ok".equalsIgnoreCase(status)){
                            userService.updateRegisterLog(rgl);
                            session.setAttribute("telephoneCode",teleCodeNum);
                            System.out.println("频率少");
                            System.out.println(rgl);
                            session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
                            resultMap.put("message","短信以发送，注意查收手机验证码");
                            }else {
                            resultMap.put("code","2");
                            resultMap.put("message","短信无法发送，请重新");
                            }

                        }else {
                            /*手机号验证码获取多次时，看是否在限制时间内*/
                            if(time.getTime()-registerTime.getTime()<60*1*60*3*1000){
                                resultMap.put("code","2");
                                resultMap.put("message","当前操作过于频繁");
                                System.out.println("频率多");
                            }else {
                                rgl.setFrequency(1);
                                rgl.setIpAddress(ip);
                                String status=SendSms.sendSms(telephone,String.valueOf(codeNum),"5");
                                if ("ok".equalsIgnoreCase(status)){
                                userService.updateRegisterLog(rgl);
                                session.setAttribute("telephoneCode",teleCodeNum);
                                resultMap.put("message","短信以发送，注意查收手机验证码");
                                session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
                                System.out.println("过时间限制频率");
                                }else {
                                resultMap.put("code","2");
                                resultMap.put("message","短信无法发送，请重新");
                                }

                            }

                        }
                    }

                }

                final Timer timer=new Timer();
                timer.schedule(new TimerTask(){
                    @Override
                    public void run() {
                        session.removeAttribute("telephoneCode");
                        System.out.println("telephoneCode删除成功");
                        timer.cancel();
                    }
                },5*60*1000);
            }

        }else {
            resultMap.put("code","2");
            resultMap.put("message","验证码不正确");
        }
        return JSONObject.toJSONString(resultMap);
    }

    @RequestMapping(value = "attention",method = RequestMethod.POST)
    @ResponseBody
    public Object attention(@RequestParam("othersId")Integer othersId){
        Map<String, String> resultMap = new HashMap<String, String>();
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getPrincipal();
        System.out.println(othersId);
        if (user==null) {
            resultMap.put("code","3");
            resultMap.put("message","您还未登录");
        }else{
            int userId=user.getId();
            if (othersId==userId){
                resultMap.put("code","2");
                resultMap.put("message","关注失败，重试一次");
            }else if(userService.findUserId(othersId)!=null){
                int num=userService.addAttention(othersId,userId);
                if(num>0){
                    resultMap.put("code","1");
                    resultMap.put("message","已关注");
                }else {
                    resultMap.put("code","2");
                    resultMap.put("message","关注失败，重试一次");
                }
            }else {
                resultMap.put("code","2");
                resultMap.put("message","您关注的用户不存在");
            }
        }
        return JSONObject.toJSONString(resultMap);
    }
    /*取消关注*/
    @RequestMapping(value = "unsubscribe",method = RequestMethod.POST)
    @ResponseBody
    public Object unsubscribe(@RequestParam("othersId")Integer othersId){
        Map<String, String> resultMap = new HashMap<String, String>();
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getPrincipal();
        System.out.println(othersId);
        if (user==null) {
            resultMap.put("code","3");
            resultMap.put("message","您还未登录");
        }else{
            int userId=user.getId();
            if (othersId==userId){
                resultMap.put("code","2");
                resultMap.put("message","取消失败，重试一次");
            }else if(userService.findUserId(othersId)!=null){
                int num=userService.cancleAttention(othersId,userId);
                if(num>0){
                    resultMap.put("code","1");
                    resultMap.put("message","已取消关注");
                }else {
                    resultMap.put("code","2");
                    resultMap.put("message","关注失败，重试一次");
                }
            }else {
                resultMap.put("code","2");
                resultMap.put("message","您关注的用户不存在");
            }
        }
        return JSONObject.toJSONString(resultMap);
    }
    /*查询用户信息*/
    @RequestMapping(value = "userInfo")
    @ResponseBody
    public Object userInfo(@RequestParam(value = "uid",required=true)Integer uid){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getPrincipal();
        System.out.println(uid);
        if(uid!=null) {
            User othersUser=userService.findUserId(uid);
            if(othersUser!=null){
                othersUser.setTelephone("");
                othersUser.setSalt("");
                othersUser.setCreateIp("");
                othersUser.setCreateTime(new Date(0));
                othersUser.setLoginIp("");
                othersUser.setLoginTime(new Date(0));
                othersUser.setLastIp("");
                othersUser.setLastTime(new Date(0));
                othersUser.setMissNumber(0);
                othersUser.setMissTime(new Date(0));
                othersUser.setPassword("");
                othersUser.setStatus("");

            }
            resultMap.put("user",othersUser);
            if (user == null) {
                resultMap.put("isLogin", "2");
            } else {
                int userId = user.getId();
                if (uid == userId) {
                    resultMap.put("attention", "55");
                    user.setSalt("");
                    user.setLoginIp("");
                    user.setMissNumber(0);
                    user.setMissTime(new Date(0));
                    user.setPassword("");
                    user.setStatus("");
                    resultMap.put("user",user);
                } else {
                /*查询用户之间的关系*/
                    String string = userService.findAttention(uid, userId);
                    resultMap.put("attention", string);
                }
            }

            /*统计文章数量*/
            int userArticleCount = userService.findUserArticleCount(uid);
            /*查询用户的所有访问*/
            int userArticleViews = userService.findUserArticleViews(uid);
            /*查询用户的所有评论*/
            int userArticleComments = userService.findUserArticleComments(uid);
            /*查询用户的所有点赞*/
            int userArticleLikes = userService.findUserArticleLikes(uid);
            /*查询用户的所有粉丝*/
            int userAttentionCount = userService.findUserAttentionCount(uid);
            /*查询用户的所有关注*/
            int attentionCount = userService.findAttentionCount(uid);
            resultMap.put("userArticleCount", userArticleCount);
            resultMap.put("userArticleViews", userArticleViews);
            resultMap.put("userArticleComments", userArticleComments);
            resultMap.put("userArticleLikes", userArticleLikes);
            resultMap.put("userAttentionCount", userAttentionCount);
            resultMap.put("attentionCount", attentionCount);
        }
        return JSONObject.toJSONString(resultMap);
    }
    /*查询用户关注的所有人信息*/
    @RequestMapping(value = "attentionUserInfo")
    @ResponseBody
    public Object findAttentionUserInfo(@RequestParam(value = "uid",required=true)Integer uid){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getPrincipal();
        System.out.println(uid);
        if(uid!=null) {
            List<Map> mapList=new ArrayList<Map>();
            List<User> othersUserList=userService.findAttentionUserInfo(uid);
            System.out.println(othersUserList);
            if (user == null) {
                for (User useritem:othersUserList){
                    System.out.println(useritem);
                    Map<String, Object> result = new HashMap<>();
                    result.put("user",useritem);
                    result.put("status",2);
                    mapList.add(result);
                }
            } else {
                int userId = user.getId();
                if (uid == userId) {
                    for (User useritem:othersUserList){
                        Map<String, Object> result = new HashMap<>();
                        result.put("user",useritem);
                        result.put("status",1);
                        mapList.add(result);
                    }
                } else {
                /*查询用户之间的关系*/
                    for (User useritem:othersUserList){
                        Map<String, Object> result = new HashMap<>();
                        result.put("user",useritem);
                        String string = userService.findAttention(useritem.getId(), userId);
                        result.put("status",string);
                        if (useritem.getId()==userId){
                            result.put("status",3);
                        }
                        mapList.add(result);
                    }
                }
            }
            resultMap.put("userList",mapList);

        }
        return JSONObject.toJSONString(resultMap);
    }
    /*查询用户关注的所有人信息*/
    @RequestMapping(value = "followersUserInfo")
    @ResponseBody
    public Object followersUserInfo(@RequestParam(value = "uid",required=true)Integer uid){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getPrincipal();
        System.out.println(uid);
        if(uid!=null) {
            List<Map> mapList=new ArrayList<Map>();
            List<User> othersUserList=userService.findFollowersUserInfo(uid);
            System.out.println(othersUserList);
            if (user == null) {
                for (User useritem:othersUserList){
                    System.out.println(useritem);
                    Map<String, Object> result = new HashMap<>();
                    result.put("user",useritem);
                    result.put("status",2);
                    mapList.add(result);
                }
            } else {
                int userId = user.getId();
                if (uid == userId) {
                    for (User useritem:othersUserList){
                        Map<String, Object> result = new HashMap<>();
                        result.put("user",useritem);
                        result.put("status",1);
                        mapList.add(result);
                    }
                } else {
                /*查询用户之间的关系*/
                    for (User useritem:othersUserList){
                        Map<String, Object> result = new HashMap<>();
                        result.put("user",useritem);
                        String string = userService.findAttention(useritem.getId(), userId);
                        result.put("status",string);
                        if (useritem.getId()==userId){
                            result.put("status",3);
                        }
                        mapList.add(result);
                    }
                }
            }
            resultMap.put("userList",mapList);

        }
        return JSONObject.toJSONString(resultMap);
    }
    /*查询用户关注的所有人信息*/
    @RequestMapping(value = "ownInformation")
    @ResponseBody
    public Object ownInformation(){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getPrincipal();
        if (user == null) {
            resultMap.put("user", null);
        } else {
            user.setSalt("");
            user.setLoginIp("");
            user.setMissNumber(0);
            user.setMissTime(new Date(0));
            user.setPassword("");
            user.setStatus("");
            resultMap.put("user",user);
        }
        return JSONObject.toJSONString(resultMap);
    }
}
