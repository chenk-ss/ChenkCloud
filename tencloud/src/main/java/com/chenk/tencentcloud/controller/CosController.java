package com.chenk.tencentcloud.controller;

import com.chenk.tencentcloud.util.TokenUtil;
import com.tencent.cloud.CosStsClient;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.TreeMap;

/**
 * @author: chenke
 * @since: 2021/6/17
 */
@RestController
@RequestMapping("/cos")
public class CosController {

    String SECRET_ID = "";
    String SECRET_KEY = "";
    String BUCKET_NAME = "";
    String REGION = "";

    @PostMapping("/token")
    public String getToken() {
        Boolean isLogin = TokenUtil.isLogin();
        TreeMap<String, Object> config = new TreeMap<String, Object>();
        if (Objects.nonNull(logoutCredential) && !isLogin) {
            Integer expiredTime = (Integer) logoutCredential.get("expiredTime");
            if (expiredTime > System.currentTimeMillis() / 1000 + 5) {
                return JSONObject.valueToString(logoutCredential);
            }
        } else if (Objects.nonNull(loginCredential) && isLogin) {
            Integer expiredTime = (Integer) loginCredential.get("expiredTime");
            if (expiredTime > System.currentTimeMillis() / 1000 + 5) {
                return JSONObject.valueToString(loginCredential);
            }
        }
        try {
            // 替换为您的 SecretId
            config.put("SecretId", SECRET_ID);
            // 替换为您的 SecretKey
            config.put("SecretKey", SECRET_KEY);

            // 临时密钥有效时长，单位是秒，默认1800秒，目前主账号最长2小时（即7200秒），子账号最长36小时（即129600秒）
            config.put("durationSeconds", 1800);

            // 换成您的 bucket
            config.put("bucket", BUCKET_NAME);
            // 换成 bucket 所在地区
            config.put("region", REGION);

            // 这里改成允许的路径前缀，可以根据自己网站的用户登录态判断允许上传的具体路径，例子：a.jpg 或者 a/* 或者 * 。
            // 如果填写了“*”，将允许用户访问所有资源；除非业务需要，否则请按照最小权限原则授予用户相应的访问权限范围。
            config.put("allowPrefix", "*");

            // 密钥的权限列表。必须在这里指定本次临时密钥所需要的权限。
            // 简单上传、表单上传和分片上传需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
            String[] allowActions;
            if (isLogin) {
                allowActions = new String[]{
                        // 简单上传
                        "name/cos:PutObject",
                        // 表单上传、小程序上传
                        "name/cos:PostObject",
                        // 分片上传
                        "name/cos:InitiateMultipartUpload",
                        "name/cos:ListMultipartUploads",
                        "name/cos:ListParts",
                        "name/cos:UploadPart",
                        "name/cos:CompleteMultipartUpload",
                        "name/cos:DeleteObject"
                };
            } else {
                allowActions = new String[]{
                        // 简单上传
                        "name/cos:PutObject",
                        // 表单上传、小程序上传
                        "name/cos:PostObject",
                        // 分片上传
                        "name/cos:InitiateMultipartUpload",
                        "name/cos:ListMultipartUploads",
                        "name/cos:ListParts",
                        "name/cos:UploadPart",
                        "name/cos:CompleteMultipartUpload"
                };
            }
            config.put("allowActions", allowActions);

            JSONObject credential = CosStsClient.getCredential(config);
            //成功返回临时密钥信息，如下打印密钥信息
            System.out.println(credential);
            if (isLogin) {
                loginCredential = credential;
            } else {
                logoutCredential = credential;
            }
            return JSONObject.valueToString(credential);
        } catch (Exception e) {
            //失败抛出异常
            throw new IllegalArgumentException("no valid secret !");
        }
    }
}
