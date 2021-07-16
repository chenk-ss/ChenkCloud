package com.chenk.tencentcloud.pojo;

import lombok.Data;

/**
 * @Author chenk
 * @create 2021/7/4 0:59
 */
@Data
public class ResultPage<T> {
    T data;
    Integer size;
    Integer page;
}
