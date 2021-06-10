package com.chenk.tencentcloud.pojo.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: chenke
 * @since: 2021/5/31
 */
@Entity
@Data
@Table(name = "tb_file")
public class FileBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "url")
    private String url;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "size")
    private Long size;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private Long status;

    @Column(name = "remark")
    private String remark;

    @Column(name = "origin_file_name")
    private String originFileName;
}
