package com.xworkz.configuration;


import com.xworkz.utils.ConverterListToString;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan("com.xworkz")
@EnableWebMvc
public class CommanModuleConfig implements WebMvcConfigurer {
    @Bean
    public ViewResolver viewResolver(){
        System.out.println("View Resolver initiated..");
        return new InternalResourceViewResolver("/",".jsp");
    }

    @Bean
    public ModelMapper modelMapper(ConverterListToString converterListToString) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(converterListToString);

        return modelMapper;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();        // it tells the server to if Dispatcher Servlet not able to map the
        // request then pass it to default servlet to map in static resources.
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.viewResolver(viewResolver());
//    }

    @Bean
    public MultipartResolver multipartResolver() {
        System.out.println("Multipart resolver is created.");
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(10485760); // 10MB
        return multipartResolver;
    }

}
