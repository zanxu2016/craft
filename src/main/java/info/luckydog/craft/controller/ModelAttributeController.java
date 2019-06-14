package info.luckydog.craft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * ModelAttributeController
 * <p>
 * 请求进入Controller层后，先执行@ModelAttribute注解的方法，并将返回的数据放入Model中。
 * 若数据未定义变量，则key为数据所属类型的首字母小写，value为数据。
 * 若有多个@ModelAttribute注解的方法，则按从上到下的顺序，执行各个方法。
 * 最后执行映射的方法，若该方法参数中有Model实例，则会将上面Model实例传入方法内。
 * <p>
 * 被@ModelAttribute注解的方法，返回的字符串不会被当做页面返回，而会被放入Model中。如 test2 方法
 * 若有请求参数，则@ModelAttribute注解的方法可接收请求参数，只需在@ModelAttribute的value属性中设置参数名称，方法参数内传入该参数即可。如 test3 方法
 *
 * @author eric
 * @since 2019/6/14
 */
@Controller
@RequestMapping("test")
public class ModelAttributeController {

    @ModelAttribute
    public int test1() {
        System.out.println("get into method test1");
        return 110;
    }

    @ModelAttribute
    public String test2() {
        System.out.println("get into method test2");
        return "test2";
    }

    @ModelAttribute(value = "name")
    public String test3(String name) {
        System.out.println("get into method test3");
        System.out.println("name is " + name);
        return name;
    }

    @RequestMapping("index")
    public String test4(@RequestParam("name") String name, Model model) {
        System.out.println("get into method test4");

        System.out.println("param name = " + name);
        Map map = model.asMap();
        System.out.println(map.toString());

        return "index";
    }
}
