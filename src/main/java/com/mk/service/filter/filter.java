package com.mk.service.filter;

import javax.servlet.*;
import java.io.IOException;

public class filter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器创建");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    public void destroy() {

    }
}
