package com.chenk.tencentcloud.service.impl;

import com.chenk.tencentcloud.pojo.FileDBDTO;
import com.chenk.tencentcloud.pojo.FileDTO;
import com.chenk.tencentcloud.pojo.bean.FileBean;
import com.chenk.tencentcloud.repository.FileRepository;
import com.chenk.tencentcloud.service.FileService;
import com.chenk.tencentcloud.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: chenke
 * @since: 2021/5/31
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public List<FileDBDTO> listFromDB(int pageNum, int size) {
        Pageable page = PageRequest.of(pageNum, size, Sort.by("createTime").descending());
        Page<FileBean> fileBeans = fileRepository.findAll(page);
        List<FileDBDTO> fileDTOList = new ArrayList<>();
        fileBeans.stream().forEach(fileBean -> {
            FileDBDTO fileDTO = new FileDBDTO();
            fileDTO.setId(fileBean.getId());
            fileDTO.setFileName(fileBean.getFileName());
            fileDTO.setUrl(fileBean.getUrl());
            fileDTO.setCreateTime(TimeUtil.dateToStr(fileBean.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            fileDTO.setUpdateTime(TimeUtil.dateToStr(fileBean.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
            fileDTO.setSize(fileBean.getSize());
            fileDTO.setType(fileBean.getType());
            fileDTO.setStatus(fileBean.getStatus());
            fileDTO.setRemark(fileBean.getRemark());
            fileDTOList.add(fileDTO);
        });
        return fileDTOList;
    }

    @Override
    public Boolean add(FileBean bean) {
        fileRepository.save(bean);
        return true;
    }
}
