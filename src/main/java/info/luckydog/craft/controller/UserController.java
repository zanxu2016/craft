package info.luckydog.craft.controller;

import info.luckydog.craft.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 *
 * @author eric
 * @since 2019/6/6
 */
@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        User user = new User();
        user.setId(id);
        user.setName("张三");
        user.setPwd("******");
        user.setAge(20);
        user.setGender(1);
        return user;
    }
}
