package com.chenk.tencentcloud.controller;

import com.chenk.tencentcloud.util.TencentCOSUploadFileUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: chenke
 * @since: 2021/6/3
 */
@RestController
public class UploadFileController {
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file){
        if (null == file) {
            return "文件为空";
        }
        String filePath = TencentCOSUploadFileUtil.uploadfile(file);
        return "上传成功，访问地址为:"+filePath;
    }
}