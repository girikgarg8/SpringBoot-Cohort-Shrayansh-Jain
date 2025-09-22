package com.girikgarg.learningspringboot;

import jakarta.servlet.*;

import java.io.IOException;

public class MyFilter2 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("MyFilter2 doFilter started");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("MyFilter2 doFilter finished");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
