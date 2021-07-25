package com.chenk.tencentcloud.controller;

import com.chenk.tencentcloud.pojo.FileDBDTO;
import com.chenk.tencentcloud.pojo.FileInsertDTO;
import com.chenk.tencentcloud.pojo.ResultPage;
import com.chenk.tencentcloud.pojo.bean.FileBean;
import com.chenk.tencentcloud.service.FileService;
import com.chenk.tencentcloud.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author: chenke
 * @since: 2021/6/3
 */
@RestController
@RequestMapping("/")
public class HomeController {

    String DOMAIN = "https://image.taeyeonss.com/";

    @Autowired
    private FileService fileService;

    @GetMapping("/list")
    public ResultPage<List<FileDBDTO>> queryList(@RequestParam("page") int page, @RequestParam("size") int size) {
        return fileService.listFromDB(page, size);
    }

    @RequestMapping(value = "/insertToDB", method = RequestMethod.POST)
    public String insertToDB(@RequestBody FileInsertDTO dto) {
        FileBean fileBean = new FileBean();
        String filePath = dto.getFileName();
        fileBean.setFileName(dto.getFileName());
        fileBean.setUrl(DOMAIN + dto.getUrl());
        Date date = new Date();
        fileBean.setCreateTime(date);
        fileBean.setUpdateTime(date);
        fileBean.setSize(dto.getSize());
        fileBean.setType(filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length()));
        fileBean.setStatus(1L);
        fileBean.setRemark(null);
        if (!TokenUtil.isLogin()) {
            fileBean.setSource("public");
        }
        fileBean.setOriginFileName(dto.getOriginFileName());
        boolean b = fileService.add(fileBean);
        return b ? "上传成功" : "上传失败";
    }

    @GetMapping("/remove")
    public String insertToDB(@RequestParam("fileName") String fileName) {
        if (!TokenUtil.isLogin()) {
            return "您无删除权限";
        }
        boolean b = fileService.removeByFileName(fileName);
        return b ? "删除成功" : "删除失败";
    }

    @GetMapping("/login")
    public String login(@RequestParam("pwd") String pwd) {
        String token = UUID.randomUUID().toString();
        TokenUtil.setDefaultToken(token);
        return Objects.equals(pwd, TokenUtil.DEFAULT_PWD) ? token : null;
    }
}
