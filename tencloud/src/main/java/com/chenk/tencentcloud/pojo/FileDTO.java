package com.chenk.tencentcloud.pojo;

import lombok.Data;

/**
 * @author: chenke
 * @since: 2021/5/31
 */
@Data
public class FileDTO {
    private String key;
    private String hash;
    private long fsize;
    private String putTime;
    private String mimeType;
    private String endUser;
    private String url;
}
