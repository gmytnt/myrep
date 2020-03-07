package com.mytnt.tool;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by meiyan on 2020/2/27.
 */
public class Tools {
    /*验证手机号*/
    public static boolean isMobilePhone(String phone) {
        boolean flag=true;
        String regex = "^1[3|4|5|6|7|8|9][0-9]{9}$";
        if (phone.length() != 11) {
            flag= false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            flag = m.matches();
        }
        return flag;
    }
    /*验证手机号*/
    public static String replaceString(String string) {
        String str=string.replaceAll("<","&lt;");
        str=str.replaceAll(">","&gt;");
        str=str.replaceAll("\"","&quot;");
        return str;
    }
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return "0:0:0:0:0:0:0:1".equals(ipAddress) ? "127.0.0.1" : ipAddress;
    }
    /**
     * MD5加密
     */
    public static String md5Pwd(String password, String saltStr){
            String hashAlgorithmName = "MD5";
            Object credentials = password;
            Object salt = ByteSource.Util.bytes(saltStr);;
            int hashIterations = 1024;
            SimpleHash simpleHash = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
            String pwdstring=  simpleHash.toHex();
            return pwdstring;
    }

}
