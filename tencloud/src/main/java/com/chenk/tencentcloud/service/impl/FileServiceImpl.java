package com.chenk.tencentcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenk.tencentcloud.pojo.FileDBDTO;
import com.chenk.tencentcloud.pojo.ResultPage;
import com.chenk.tencentcloud.pojo.bean.FileBean;
import com.chenk.tencentcloud.mapper.FileMapper;
import com.chenk.tencentcloud.service.FileService;
import com.chenk.tencentcloud.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: chenke
 * @since: 2021/5/31
 */
@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileBean> implements FileService {

    @Override
    public ResultPage<List<FileDBDTO>> listFromDB(int pageNum, int size) {
        LambdaQueryWrapper<FileBean> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.orderByDesc(FileBean::getCreateTime);
        IPage<FileBean> page = new Page<>(pageNum, size);
        page = page(page, queryWrapper);
        if (null == page
                || CollectionUtils.isEmpty(page.getRecords())) {
            return new ResultPage<>();
        }
        List<FileDBDTO> fileDTOList = new ArrayList<>();
        page.getRecords().stream().forEach(fileBean -> {
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
        return save(bean);
    }

    @Override
    public Boolean removeByFileName(String fileName) {
        LambdaQueryWrapper<FileBean> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(FileBean::getFileName, fileName);
        return remove(queryWrapper);
    }
}
