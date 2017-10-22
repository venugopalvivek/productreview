package com.intuit.vivek.swagger;

import io.swagger.jaxrs.config.BeanConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class Bootstrap extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setTitle("Mystic");
        beanConfig.setSchemes(new String[]{"https"});
        beanConfig.setHost("localhost");
        beanConfig.setBasePath("/productreview/api");
        beanConfig.setResourcePackage("com.intuit.vivek.rest");
        beanConfig.setScan(true);
    }
}
