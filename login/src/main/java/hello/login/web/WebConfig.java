package hello.login.web;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<LogFilter> logFilterFilterRegistrationBean() {
        FilterRegistrationBean<LogFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new LogFilter());
        bean.setOrder(1);
        bean.addUrlPatterns("/*");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilterFilterRegistrationBean() {
        FilterRegistrationBean<LoginFilter> bean = new FilterRegistrationBean<>(new LoginFilter());
        bean.setOrder(2);
        bean.addUrlPatterns("/*");
        return bean;
    }

}
