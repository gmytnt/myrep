package com.mytnt.controller.common;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by meiyan on 2020/2/23.
 */
@Controller
@RequestMapping("/")
public class UpDownLoadController {
    @RequestMapping("uploadFile")
    @ResponseBody
    public String method2(@RequestParam MultipartFile file,HttpServletRequest request ) {
        System.out.print("进入方法method2");
        //多个参数的话只要多个@RequestParam即可，注意参数名要和表单里面的属性名一致
        JSONObject json =new JSONObject();
        //获取原始文件名
        String orgiginalFileName = file.getOriginalFilename();
        //获取文件名后缀
        String suffix=orgiginalFileName.substring(orgiginalFileName.lastIndexOf("."));
        //获取当前时间字符串，生成文件名
        SimpleDateFormat smf=new SimpleDateFormat("yyyyMMddHHmmss");
        //为文件名添加后缀，表示文件类型
        String fileName=smf.format(new Date())+suffix;
//        UUID类会生成一个32位的字符串，而且永远不会重复
//        String fileName= UUID.randomUUID().toString()+suffix;
        System.out.print("编号"+UUID.randomUUID().toString());
        //生成当日文件夹
        SimpleDateFormat smfdir=new SimpleDateFormat("yyyyMMdd");
        String dir=smfdir.format(new Date());
        //设置文件保存的路径：当前项目的下的/static/upload/文件夹下
        String path = request.getSession().getServletContext().getRealPath("/static/upload/"+dir+"/");
        File newFile =new File(path+fileName);
        //        判断当前文件父级文件夹是否存在，如果不存在，就创建文件夹
        if (!newFile.getParentFile().exists()) {
            newFile.mkdirs();
        }
        try {
            file.transferTo(newFile);
            String content = file.getContentType();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] photpPath={"/static/upload/"+dir+"/"+fileName};
//        json.put("flag", true);
        json.put("errno", 0);
        json.put("data",photpPath );
        System.out.println(json.toJSONString());
        return json.toJSONString();
    }
}
