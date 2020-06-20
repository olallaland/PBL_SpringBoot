package com.example.sp.config;

import com.example.sp.constant.ConfigConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cglib.proxy.Factory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;
import java.io.File;


@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {
    /**
 * 对系统后台进行跨域访问进行配置
 */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")// 1 设置访问源地址
                .allowCredentials(true)// 2 设置访问源请求头
                .allowedMethods("GET", "POST", "DELETE", "PUT") // 3 设置访问源请求方法
                .maxAge(3600);
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        File file = new File("/springboot/");
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        registry.addResourceHandler("/springboot/**").addResourceLocations("file:/springboot/");
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/images/**")
//                .addResourceLocations("file:"+ ConfigConstant.LOCATION + "\\images\\");
//        registry.addResourceHandler("/files/**")
//                .addResourceLocations("file:"+ ConfigConstant.LOCATION + "\\files\\");
//    }

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	//System.out.println(new File("/www/wwwroot/anjian/img/").getAbsolutePath());
    	// /www/wwwroot/anjian/img/
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:" + uploadFolder);
    }

}
