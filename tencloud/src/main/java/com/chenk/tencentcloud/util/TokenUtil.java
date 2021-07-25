package com.chenk.tencentcloud.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author chenk
 * @create 2021/7/24 21:42
 */
@Slf4j
public class TokenUtil {

    private static final String TOKEN = "Token";

    private static String DEFAULT_TOKEN;

    public static final String DEFAULT_PWD = "123456";

    public static Boolean isLogin() {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(TOKEN);
        log.info("token:{}", token);
        return Objects.nonNull(token) && Objects.equals(token, DEFAULT_TOKEN);
    }

    public static void setDefaultToken(String token) {
        DEFAULT_TOKEN = token;
    }
}
