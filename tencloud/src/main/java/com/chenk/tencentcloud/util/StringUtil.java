package com.chenk.tencentcloud.util;

import java.util.UUID;

/**
 * @author: chenke
 * @since: 2021/5/31
 */
public class StringUtil {
    /**
     * @Description: 生成唯一名称
     * @Param: fileName
     * @return: 云服务器fileName
     */
    public static String getRandomFileName(String fileName) {

        int index = fileName.lastIndexOf(".");

        if ((fileName == null || fileName.isEmpty()) || index == -1) {
            throw new IllegalArgumentException();
        }
        // 获取文件后缀
        String suffix = fileName.substring(index);
        // 生成UUID
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 生成上传至云服务器的路径
        String path = TimeUtil.getNow() + "-" + uuid + suffix;
        return path;
    }
}
