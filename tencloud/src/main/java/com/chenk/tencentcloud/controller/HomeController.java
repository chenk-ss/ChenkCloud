package com.chenk.tencentcloud.controller;

import com.chenk.tencentcloud.pojo.FileInsertDTO;
import com.chenk.tencentcloud.pojo.bean.FileBean;
import com.chenk.tencentcloud.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @CrossOrigin("*")
    @ResponseBody
    @RequestMapping(value = "/insertToDB", method = RequestMethod.POST)
    public String insertToDB(@RequestBody FileInsertDTO dto) {
        FileBean fileBean = new FileBean();
        String filePath = dto.getFileName();
        fileBean.setFileName(dto.getFileName());
        fileBean.setUrl(dto.getUrl());
        Date date = new Date();
        fileBean.setCreateTime(date);
        fileBean.setUpdateTime(date);
        fileBean.setSize(dto.getSize());
        fileBean.setType(filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length()));
        fileBean.setStatus(1L);
        fileBean.setRemark(null);
        fileBean.setOriginFileName(dto.getOriginFileName());
        boolean b = fileService.add(fileBean);
        return b ? "上传成功" : "上传失败";
    }
}
