package com.employees.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class RequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("1 - pre handle.");
        System.out.println("METHOD type:" + request.getMethod());
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Servlet PATH: " + request.getServletPath());
        if(handler instanceof HandlerMethod){
            //can be added different logics
            Class<?> controllerClass = ((HandlerMethod) handler).getBeanType();
            String methodName = ((HandlerMethod) handler).getMethod().getName();
            System.out.println("Controller name: " + controllerClass.getName());
            System.out.println("Method name:" + methodName);

        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("2 - post Handle");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("3 - after Completion.");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
