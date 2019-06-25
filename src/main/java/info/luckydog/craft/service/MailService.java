package info.luckydog.craft.service;

import java.io.File;

/**
 * MailService
 *
 * @author eric
 * @since 2019/6/22
 */
public interface MailService {

    /**
     * 发送邮件
     *
     * @param subject   主题
     * @param content   正文，支持 html
     * @param recipient 收件人邮箱
     * @return boolean 发送结果
     */
    boolean sendMail(String subject, String content, String[] recipient);

    /**
     * 发送邮件
     *
     * @param subject   主题
     * @param content   正文，支持 html
     * @param recipient 收件人邮箱
     * @param cc        抄送人人邮箱
     * @return boolean 发送结果
     */
    boolean sendMail(String subject, String content, String[] recipient, String[] cc);

    /**
     * 发送邮件
     *
     * @param subject   主题
     * @param content   正文，支持 html
     * @param files     附件
     * @param recipient 收件人邮箱
     * @return boolean 发送结果
     */
    boolean sendMail(String subject, String content, File[] files, String[] recipient);

    /**
     * 发送邮件
     *
     * @param subject    主题
     * @param content    正文，支持 html
     * @param files      附件
     * @param recipient  收件人邮箱
     * @param contentIds 正文中的cid
     * @return boolean 发送结果
     */
    boolean sendMail(String subject, String content, File[] files, String[] recipient, String[] contentIds);

    /**
     * 发送邮件
     *
     * @param subject    主题
     * @param content    正文，支持 html
     * @param files      附件
     * @param recipient  收件人邮箱
     * @param cc         抄送人人邮箱
     * @param contentIds 正文中的cid
     * @return boolean 发送结果
     */
    boolean sendMail(String subject, String content, File[] files, String[] recipient, String[] cc, String[] contentIds);
}
