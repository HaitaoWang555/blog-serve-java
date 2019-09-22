package com.wht.blog.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wht.blog.entity.User;
import com.wht.blog.util.ErrorCode;
import com.wht.blog.util.Method;
import com.wht.blog.util.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author wht
 * @since 2019-09-22 21:05
 */
@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {
    private static final String AUTH_URIS = "/manage";

    private static final String[] IGNORE_URIS = {"/manage/user/login", "/manage/user/logout"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        String ip = Method.getIp();

        log.info("用户访问地址: {}, Http类型: {}, ip地址: {}", url, request.getMethod(), ip);

        if (url.contains(AUTH_URIS)) {
            boolean auth = true;
            //登录拦截忽略url
            for (String param : IGNORE_URIS) {
                if (StringUtils.endsWithIgnoreCase(url, param)) {
                    auth = false;
                }
            }
            //登录拦截
            if (auth) {
                User user = Method.getLoginUser();
                if (null == user) {
                    // 要设置跨域，不然输出信息没有
                    if (request.getHeader(HttpHeaders.ORIGIN) != null) {
                        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader(HttpHeaders.ORIGIN));
                        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, PUT, DELETE");
                        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
                        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "x-requested-with");
                    }
                    PrintWriter out = response.getWriter();
                    ObjectMapper mapper = new ObjectMapper();
                    out.print(mapper.writeValueAsString(RestResponse.fail(ErrorCode.NOT_LOGIN.getCode(), ErrorCode.NOT_LOGIN.getMsg())));
                    out.flush();
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {

    }
}
