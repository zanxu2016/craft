package info.luckydog.craft.config;

import info.luckydog.craft.filter.HttpLogFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * FilterConfig
 *
 * @author eric
 * @since 2019/6/6
 */
@Component
@Slf4j
public class FilterConfig {

    @Autowired
    private HttpLogFilter httpLogFilter;

    @Bean
    public FilterRegistrationBean httpLogFilterRegistration() {
        log.info("httpLogFilterRegistration start...");
        FilterRegistrationBean<HttpLogFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(httpLogFilter);
        registration.setName("httpLogFilter");
        registration.addUrlPatterns("/user/*");

        Map<String, String> params = new HashMap<>();
        params.put("encoding", "UTF-8");
        params.put("forceEncoding", "true");

        registration.setInitParameters(params);
        registration.setOrder(1);// spring boot 会按照 order 值从小到大顺序依次过滤
        log.info("httpLogFilterRegistration end...");
        return registration;
    }
}
