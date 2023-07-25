package hello.login.web;

import hello.login.web.argumentresolver.LoginArgumentResolver;
import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/error");

        registry.addInterceptor(new LoginInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login", "/logout", "/members/add",
                                     "/css/**", "/error");
    }

    //    @Bean
    public FilterRegistrationBean<LogFilter> logFilterFilterRegistrationBean() {
        FilterRegistrationBean<LogFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new LogFilter());
        bean.setOrder(1);
        bean.addUrlPatterns("/*");
        return bean;
    }

//    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilterFilterRegistrationBean() {
        FilterRegistrationBean<LoginFilter> bean = new FilterRegistrationBean<>(new LoginFilter());
        bean.setOrder(2);
        bean.addUrlPatterns("/*");
        return bean;
    }

}
