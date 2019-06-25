package info.luckydog.craft.controller;

import info.luckydog.craft.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

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

    @Autowired
    private TemplateEngine templateEngine;

    @RequestMapping("/send")
    public String sendEmail(@RequestParam String email) {

        String subject = "send simple email";
        String content = "This is a test email sent from eric.";

        String[] recipient = {email};// 3099306276@qq.com

        boolean success = mailService.sendMail(subject, content, recipient);
        log.info("mail has been sent? " + success);

        return "success";
    }

    @RequestMapping("/sendWithAttachment")
    public String sendEmailWithAttachment(@RequestParam String email) {

        String subject = "send email with attachment";
        String content = "This is a test email sent from eric.";

        String[] recipient = {email};// 3099306276@qq.com

        File[] files = {new File(ClassLoader.getSystemResource("imgs/").getPath() + "white-cloud.jpg")};
        boolean successWithAttachment = mailService.sendMail(subject, content, files, recipient);
        log.info("mail with attachment has been sent? " + successWithAttachment);

        return "success";
    }

    @RequestMapping("/sendHtmlContent")
    public String sendEmailHtmlContent(@RequestParam String email) {

        String subject = "send email with html content";
        String content = "<html><body><img src=\"cid:whiteCloud\" ></body></html>";
        String[] contentIds = {"whiteCloud"};

        String[] recipient = {email};// 3099306276@qq.com/2628641193@qq.com
        File file = new File(ClassLoader.getSystemResource("imgs/").getPath() + "white-cloud.jpg");
        File[] files = {file};

        boolean successWithHtmlStyle = mailService.sendMail(subject, content, files, recipient, contentIds);
        log.info("mail with html style has been sent? " + successWithHtmlStyle);

        return "success";
    }

    @RequestMapping("/sendTemplate")
    public String sendEmailTemplate(@RequestParam String email) {

        String subject = "send email with html content";

        //org.thymeleaf.context.Context;
        Context context = new Context();
        context.setVariable("username", "eric");
        //获取thymeleaf的html模板
        String content = templateEngine.process("email", context);
        log.info("content is {}", content);

        String[] recipient = {email};// 3099306276@qq.com

        boolean successWithAttachment = mailService.sendMail(subject, content, recipient);
        log.info("mail with attachment has been sent? " + successWithAttachment);

        return "success";
    }
}
