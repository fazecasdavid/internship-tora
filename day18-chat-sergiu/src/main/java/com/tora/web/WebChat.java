package com.tora.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebChat {
    @GetMapping("/**")
    public String mapRendering(@RequestParam(name = "toUser", required = false) String user,
                               Model model) {
        return "index";
    }
}
