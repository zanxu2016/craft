package info.luckydog.craft.service.impl;

import info.luckydog.craft.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;

/**
 * MailServiceImpl
 *
 * @author eric
 * @since 2019/6/22
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("#{applicationProperties.emailFrom}")
    private String emailFrom;

    @Value("#{applicationProperties.name}")
    private String name;

    @Override
    public boolean sendMail(String subject, String content, String[] recipient) {
        return sendMail(subject, content, null, recipient, null, null);
    }

    @Override
    public boolean sendMail(String subject, String content, File[] files, String[] recipient) {
        return sendMail(subject, content, files, recipient, null, null);
    }

    @Override
    public boolean sendMail(String subject, String content, File[] files, String[] recipient, String[] contentIds) {
        return sendMail(subject, content, files, recipient, null, contentIds);
    }

    @Override
    public boolean sendMail(String subject, String content, String[] recipient, String[] cc) {
        return sendMail(subject, content, null, recipient, cc, null);
    }

    @Override
    public boolean sendMail(String subject, String content, File[] files, String[] recipient, String[] cc, String[] contentIds) {
        if (recipient == null || recipient.length <= 0) {
            return false;
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            messageHelper.setFrom(emailFrom, name);
            for (String s : recipient) {
                messageHelper.addTo(s);
            }
            //抄送
            if (null != cc && cc.length > 0) {
                messageHelper.setCc(cc);
            }
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);//true 启用html格式
            if (null != contentIds && contentIds.length > 0) {
                Assert.notNull(files, "文件不能为空");
                Assert.state(contentIds.length == files.length, "文件数量与内容参数cid数量不一致");
                for (int i = 0; i < contentIds.length; i++) {
                    messageHelper.addInline(contentIds[i], files[i]);
                }
            } else {
                if (null != files && files.length > 0) {
                    for (File file : files) {
                        messageHelper.addAttachment(MimeUtility.encodeWord(file.getName()), file);
                    }
                }
            }

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("send mail error", e);
            return false;
        }
        return true;
    }
}
