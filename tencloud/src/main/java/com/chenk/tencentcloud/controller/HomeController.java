package com.chenk.tencentcloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: chenke
 * @since: 2021/6/3
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping(value = "/")
    private String index() {
        return "index";
    }

}
