package com.xworkz.initializer;

import com.xworkz.configuration.CommanModuleConfig;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class CommanModuleInitializer extends AbstractAnnotationConfigDispatcherServletInitializer implements WebMvcConfigurer {


    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{
                CommanModuleConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{
                "/"
        };
    }


    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();        // it tells the server to if Dispatcher Servlet not able to map the
        // request then pass it to default servlet to map in static resources.
    }

}
