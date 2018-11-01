package com.application.catny;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@RestController
@EnableScheduling
@EnableCaching
@MapperScan("com.application.catny.mapper")
public class CatnyApplication {

    @RequestMapping("")
    public ModelAndView s(){
        return new ModelAndView("1");
    }



    @Bean
    public CorsFilter corsFilter()
    {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }



    private CorsConfiguration buildConfig()
    {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

// 允许跨域访问的域名
        corsConfiguration.addAllowedOrigin("*");
// 请求头
        corsConfiguration.addAllowedHeader("*");
// 请求方法
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        corsConfiguration.addAllowedMethod(HttpMethod.POST);
        corsConfiguration.addAllowedMethod(HttpMethod.GET);
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);
// 预检请求的有效期，单位为秒。
        corsConfiguration.setMaxAge(3600L);
// 是否支持安全证书
        corsConfiguration.setAllowCredentials(true);

        return corsConfiguration;
    }
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory config = new MultipartConfigFactory();
        config.setMaxFileSize("2GB");
        config.setMaxRequestSize("2GB");
        return config.createMultipartConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(CatnyApplication.class, args);
    }
}
