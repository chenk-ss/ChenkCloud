package com.chenk.tencentcloud.pojo;

import lombok.Data;

/**
 * @Author chenk
 * @create 2021/6/11 22:47
 */
@Data
public class FileInsertDTO {
    private String fileName;
    private Long size;
    private String originFileName;
    private String url;
}
