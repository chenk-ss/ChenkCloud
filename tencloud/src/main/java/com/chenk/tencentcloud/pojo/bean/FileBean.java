package com.chenk.tencentcloud.pojo.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: chenke
 * @since: 2021/5/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_file")
public class FileBean {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String fileName;

    private String url;

    private Date createTime;

    private Date updateTime;

    private Long size;

    private String type;

    private Long status;

    private String remark;

    private String originFileName;

    private String source;
}
