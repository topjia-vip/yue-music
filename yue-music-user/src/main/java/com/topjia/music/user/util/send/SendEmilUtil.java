package com.topjia.music.user.util.send;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 验证码发送工具类（异步）
 *
 * @author topjia
 * @date 2020-08-01 10:46
 */
@Component
@Slf4j
public class SendEmilUtil {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Value(value = "${mail.fromMail.addr}")
    private String fromEmail;

    public void sendEmilCaptcha(String toEmilAddr, String captcha) throws Exception {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject("Yue Music用户注册验证码");
            helper.setText("亲爱的用户，您好!：<br><br>  <div style='margin-left: 20px;'>欢迎注册Yue music<br>  注册验证码为：<b style='color:red'>" + captcha + "</b>，验证码有效期为5分钟<br><br><br> </div>若为本人操作，请在规定时间内完成注册<br>  若不是您本人操作，请忽略此邮件，对本次打扰感到抱歉，Sorry~~", true);
            helper.setTo(toEmilAddr);
            helper.setFrom(fromEmail);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new Exception("邮件发送错误");
        }
    }
}
