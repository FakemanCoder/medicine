package com.medicine.sys.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;


@WebFilter(urlPatterns="/api/*", filterName = "loginFilter")
public class LoginFilter implements Filter {

	@Autowired
	private RedisUtils redis;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Object key = req.getSession().getAttribute("loginName");
        System.out.println("filter-->doFilter-->key="+key);
        if (key != null && key.toString() != null) {
        	String string = redis.get(key.toString());
        	if (string == null) {
        		System.out.println("filter重定向");	
            	resp.sendRedirect("/");
    			return;
			}
        	filterChain.doFilter(servletRequest,servletResponse);
        } else {
        	System.out.println("filter重定向");
        	resp.sendRedirect("/");
			return;
        }
		
	}
	
	@Override
	public void destroy() {
		System.out.println("filter-->destroy");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("filter-->init");
		
	}

}
