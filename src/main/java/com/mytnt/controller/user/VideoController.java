package com.mytnt.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class VideoController {
    @RequestMapping("video")
    public String video(){
        return "video";
    }
}
