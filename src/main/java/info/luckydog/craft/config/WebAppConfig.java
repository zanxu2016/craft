package info.luckydog.craft.config;

import info.luckydog.craft.filter.HttpLogFilter;
import info.luckydog.craft.filter.PermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebAppConfig
 *
 * @author eric
 * @since 2019/6/6
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Bean
    public PermissionInterceptor getPermissionInterceptor() {
        return new PermissionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(getPermissionInterceptor())
                .addPathPatterns("/user/**")
        .excludePathPatterns("/user/getUser/**");
    }


    // cors 跨域
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*")
//                .exposedHeaders("TOKEN")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
