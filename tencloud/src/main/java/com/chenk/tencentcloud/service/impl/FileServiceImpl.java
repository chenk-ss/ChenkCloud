package com.chenk.tencentcloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.chenk.tencentcloud.pojo.FileDBDTO;
import com.chenk.tencentcloud.pojo.ResultPage;
import com.chenk.tencentcloud.pojo.bean.FileBean;
import com.chenk.tencentcloud.repository.FileRepository;
import com.chenk.tencentcloud.service.FileService;
import com.chenk.tencentcloud.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author: chenke
 * @since: 2021/5/31
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public ResultPage<List<FileDBDTO>> listFromDB(int pageNum, int size) {
        Pageable page = PageRequest.of(pageNum, size, Sort.by("createTime").descending());
        FileBean queryFileBean = new FileBean();
//        queryFileBean.setSource("public");
        Example<FileBean> example = Example.of(queryFileBean);
        Page<FileBean> fileBeans = fileRepository.findAll(example, page);
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
            fileDTO.setOriginFileName(fileBean.getOriginFileName());
            fileDTOList.add(fileDTO);
        });
        ResultPage<List<FileDBDTO>> resultPage = new ResultPage();
        resultPage.setData(fileDTOList);
        resultPage.setPage(pageNum);
        resultPage.setSize(fileDTOList.size());
        return resultPage;
    }

    @Override
    public Boolean add(FileBean bean) {
        fileRepository.save(bean);
        return Boolean.TRUE;
    }

    @Override
    public Boolean removeByFileName(String fileName) {
        FileBean bean = new FileBean();
        bean.setFileName(fileName);
        Example<FileBean> example = Example.of(bean);
        Optional<FileBean> one = fileRepository.findOne(example);
        log.info(JSON.toJSONString(one.get()));
        fileRepository.deleteById(one.get().getId());
        return Boolean.TRUE;
    }
}
