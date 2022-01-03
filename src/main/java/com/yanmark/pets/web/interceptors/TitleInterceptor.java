package com.yanmark.pets.web.interceptors;

import com.yanmark.pets.web.annotations.PageTitle;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TitleInterceptor extends HandlerInterceptorAdapter {

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView) {
    String title = "\uD835\uDD7B\uD835\uDD8A\uD835\uDD99\uD835\uDD98";

    if (modelAndView == null) {
      modelAndView = new ModelAndView();
    } else {
      if (handler instanceof HandlerMethod) {
        PageTitle methodAnnotation = ((HandlerMethod) handler).getMethodAnnotation(PageTitle.class);

        if (methodAnnotation != null) {
          modelAndView.addObject("title", title + " - " + methodAnnotation.value());
        }
      }
    }
  }
}
