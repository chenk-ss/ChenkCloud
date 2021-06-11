package com.chenk.tencentcloud.controller;

import com.chenk.tencentcloud.pojo.FileInsertDTO;
import com.chenk.tencentcloud.pojo.bean.FileBean;
import com.chenk.tencentcloud.service.FileService;
import com.chenk.tencentcloud.util.TencentCOSUploadFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author: chenke
 * @since: 2021/6/3
 */
@Slf4j
@RequestMapping("file")
@RestController
public class UploadFileController {

    @Autowired
    private FileService fileService;

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

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        if (null == file) {
            return "文件为空";
        }
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                String originFileName = file.getOriginalFilename();
                log.info("开始上传：{}", originFileName);
                String filePath = TencentCOSUploadFileUtil.uploadfile(file);
                log.info("上传成功，访问地址为:" + TencentCOSUploadFileUtil.URL + filePath);

                FileBean fileBean = new FileBean();
                fileBean.setFileName(filePath);
                fileBean.setUrl(TencentCOSUploadFileUtil.URL + filePath);
                Date date = new Date();
                fileBean.setCreateTime(date);
                fileBean.setUpdateTime(date);
                fileBean.setSize(file.getSize());
                fileBean.setType(filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length()));
                fileBean.setStatus(1L);
                fileBean.setRemark(null);
                fileBean.setOriginFileName(originFileName);
                fileService.add(fileBean);
            }
        });
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "上传成功";
    }
}