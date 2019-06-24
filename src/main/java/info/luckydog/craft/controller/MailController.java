package info.luckydog.craft.controller;

import info.luckydog.craft.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * MailController
 *
 * @author eric
 * @since 2019/6/22
 */
@RestController
@RequestMapping("/mail")
@Slf4j
public class MailController {

    @Autowired
    private MailService mailService;

    @RequestMapping("/send")
    public String sendEmail(@RequestParam String email) {

        String subject = "test";
        String content = "This is a test email sent from eric.";

        String[] recipient = {email};// 3099306276@qq.com

        boolean success = mailService.sendMail(subject, content, recipient);
        log.info("mail has been sent? " + success);

        return "success";
    }
}
