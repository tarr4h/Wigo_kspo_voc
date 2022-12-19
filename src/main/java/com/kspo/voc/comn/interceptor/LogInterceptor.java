package com.kspo.voc.comn.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringBuilder sb = new StringBuilder();

        sb.append("\n[log-interceptor]");
        sb.append("\n ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        sb.append("\n -- requestURI : ").append(request.getRequestURI());
        sb.append("\n -- requestURL : ").append(request.getRequestURL());
        sb.append("\n -- method     : ").append(request.getMethod().toString());
        sb.append("\n -- remote ip  : ").append(request.getRemoteAddr());
        sb.append("\n -- userAgent  : ").append(request.getHeader("User-Agent"));
        sb.append("\n ---------------------------------------------------------------------------------------");
        log.debug(sb.toString());

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("\n -------- {} ----------------------------------------------------------------------------------------------------------------------------------------------------------", request.getRequestURI());
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
