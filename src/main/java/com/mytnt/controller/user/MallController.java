package com.mytnt.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by meiyan on 2020/2/27.
 */
@Controller
@RequestMapping("/")
public class MallController {
    @RequestMapping("mall")
    public String mall(){
        return "mall";
    }
}
