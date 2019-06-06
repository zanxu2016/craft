package info.luckydog.craft.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebAppConfig
 *
 * @author eric
 * @since 2019/6/6
 */
//@Configuration
public class WebAppConfig implements WebMvcConfigurer {

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
