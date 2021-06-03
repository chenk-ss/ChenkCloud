package com.chenk.tencentcloud.controller;

import com.chenk.tencentcloud.pojo.bean.FileBean;
import com.chenk.tencentcloud.service.FileService;
import com.chenk.tencentcloud.util.TencentCOSUploadFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        if (null == file) {
            return "文件为空";
        }
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
        fileService.add(fileBean);

        return "上传成功，访问地址为:" + TencentCOSUploadFileUtil.URL + filePath;
    }
}