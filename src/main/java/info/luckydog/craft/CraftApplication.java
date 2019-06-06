package info.luckydog.craft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * CraftApplication
 *
 * @author eric
 * @since 2019/6/6
 */
@SpringBootApplication
//扫描自定义的servlet和filter
@ServletComponentScan("info.luckydog.craft.filter")
public class CraftApplication {

    public static void main(String[] args) {
        SpringApplication.run(CraftApplication.class);
    }
}
