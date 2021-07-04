package com.chenk.tencentcloud.pojo;

import lombok.Data;

/**
 * @author: chenke
 * @since: 2021/6/1
 */
@Data
public class FileDBDTO {
    private Long id;

    private String fileName;

    private String url;

    private String createTime;

    private String updateTime;

    private Long size;

    private String type;

    private Long status;

    private String remark;

    private String originFileName;
}
