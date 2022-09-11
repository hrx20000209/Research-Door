package com.example.researchgate.Config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*", filterName = "CorsFilter")
public class CorsFilter implements Filter {
   
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    	HttpServletRequest request = (HttpServletRequest) servletRequest;
    	HttpServletResponse response = (HttpServletResponse) servletResponse;
    	response.setHeader("Access-Control-Allow-Origin", "*");//允许任何请求来源        
    	response.setHeader("Access-Control-Allow-Methods", "*");//允许任何method
    	response.setHeader("Access-Control-Allow-Headers", "*");//允许任何自定义header
    	if (!request.getMethod().equals("OPTIONS")){
    		 filterChain.doFilter(servletRequest, servletResponse);
    	}  
    }
 
}
