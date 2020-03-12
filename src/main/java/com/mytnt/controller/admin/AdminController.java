package com.mytnt.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class AdminController {

    @RequestMapping("adminView")
    public String adminView(){
        return "admin/admin";
    }
}
