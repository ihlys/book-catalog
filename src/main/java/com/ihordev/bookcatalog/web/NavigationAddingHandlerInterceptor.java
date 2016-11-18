package com.ihordev.bookcatalog.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ihordev.bookcatalog.util.NavigationPath;
import com.ihordev.bookcatalog.util.SiteNavigation;

@Component
public class NavigationAddingHandlerInterceptor extends HandlerInterceptorAdapter
{

    @Autowired
    private SiteNavigation siteNavigation;

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
            final ModelAndView modelAndView) throws Exception
    {

        if (modelAndView != null)
        {
            List<NavigationPath> navigationPaths = siteNavigation.getNavigationFor(modelAndView.getViewName(), modelAndView.getModel());
            modelAndView.getModelMap().addAttribute("navigationPaths", navigationPaths);
        }
    }
}
