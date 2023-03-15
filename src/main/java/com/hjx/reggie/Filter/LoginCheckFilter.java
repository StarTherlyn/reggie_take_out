package com.hjx.reggie.Filter;

import com.alibaba.fastjson.JSON;
import com.hjx.reggie.common.BaseContext;
import com.hjx.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否完成登陆
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        //获取本次请求的URI
        String requestURI = httpServletRequest.getRequestURI();
        log.info("拦截到请求:{}",requestURI);

        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/login"};

        //判断是否需要处理
        boolean check = check(urls,requestURI);
        //如果不需要处理直接放行
        if(check){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            log.info("本次请求不需要处理");
            return;
        }
        //需要处理，如果已登录，放行
        if(httpServletRequest.getSession().getAttribute("employee")!=null ){

            long empId = (long) httpServletRequest.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(httpServletRequest,httpServletResponse);
            log.info("已登录");
            return;
        }
        if(httpServletRequest.getSession().getAttribute("user")!=null ){

            long empId = (long) httpServletRequest.getSession().getAttribute("user");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(httpServletRequest,httpServletResponse);
            log.info("已登录");
            return;
        }
        //未登录，通过输出流方式往客户端写数据即可
        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("用户未登录");
    }

    /**
     * 检查本次匹配是否需要放行
     * @return
     */
    public boolean check(String[] urls,String requestURI){
        for(String url:urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
