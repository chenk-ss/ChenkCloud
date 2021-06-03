package com.chenk.tencentcloud.service;

import com.chenk.tencentcloud.pojo.FileDBDTO;
import com.chenk.tencentcloud.pojo.bean.FileBean;

import java.util.List;

/**
 * @author: chenke
 * @since: 2021/5/31
 */
public interface FileService {

    List<FileDBDTO> listFromDB(int page, int size);

    Boolean add(FileBean bean);
}
