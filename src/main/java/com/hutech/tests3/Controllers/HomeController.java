package com.hutech.tests3.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("")
    public String HomePage(){
        return "Layout/Home/homepage";
    }
}
