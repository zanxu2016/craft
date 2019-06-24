package info.luckydog.craft.controller;

import com.alibaba.fastjson.JSON;
import info.luckydog.craft.model.dto.UserDTO;
import info.luckydog.craft.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * TransactionController
 *
 * @author eric
 * @since 2019/6/24
 */
@RestController
@RequestMapping("/tx")
@Slf4j
public class TransactionController {

    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/addUser")
    public String addUser(@ModelAttribute UserDTO user) {
        log.info("to add user: {}", JSON.toJSONString(user));

        userService.getAllUsers().stream()
                .filter(Objects::nonNull)
                .forEach(userDB -> log.info("exist user: {}", JSON.toJSONString(userDB)));

        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            log.info("start to add user...");
            userService.addUser(user);
            log.info("end to add user...");
            int num = 1 / 0;// 制造异常
            transactionManager.commit(txStatus);
            log.info("add user successful");
        } catch (Exception e) {
            log.error("ex occurs, caused by {}", e.getMessage());
            transactionManager.rollback(txStatus);
            log.info("add user failed");
            return "failure";
        }

        return "success";
    }
}
