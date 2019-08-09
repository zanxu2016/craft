package info.luckydog.craft.controller;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DemoController
 *
 * @author eric
 * @since 2019/7/29
 */
@RestController
@RequestMapping("demo")
@Slf4j
public class DemoController {

    /**
     * 路径上的参数，会被解析到 @ModelAttribute 注解的实体内
     *
     * @param id  路径参数
     * @param dto 参数接收实体
     * @return String 返回字符串
     */
    @RequestMapping("test/{id}")
    public String get(@PathVariable String id, @ModelAttribute Dto dto) {
        String res = JSON.toJSONString(dto);
        log.info("params: {}", res);
        return res;
    }
}

@Data
@AllArgsConstructor
class Dto {
    private String id;

    private String name;
}
