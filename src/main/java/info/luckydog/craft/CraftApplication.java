package info.luckydog.craft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * CraftApplication
 *
 * @author eric
 * @since 2019/6/6
 */
@SpringBootApplication
// mapper 接口类扫描包配置
@MapperScan("info.luckydog.craft.mapper")
//扫描自定义的servlet和filter
@ServletComponentScan("info.luckydog.craft.filter")
@EnableSwagger2
public class CraftApplication {

    public static void main(String[] args) {
        SpringApplication.run(CraftApplication.class);
    }
}
