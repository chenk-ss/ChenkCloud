package com.chenk.tencentcloud.controller;

import com.chenk.tencentcloud.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: chenke
 * @since: 2021/6/3
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private FileService fileService;

    @GetMapping(value = "/")
    private String index() {
        return "web";
    }

    @GetMapping("/list")
    public String queryList(Model m, @RequestParam("page") int page, @RequestParam("size") int size) {
        m.addAttribute("resultList", fileService.listFromDB(page, size));
        return "list";
    }
}
