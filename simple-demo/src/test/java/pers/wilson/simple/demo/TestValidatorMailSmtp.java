package pers.wilson.simple.demo;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;
import java.util.function.Consumer;

/**
 * @author
 * 日期 2019/12/5 13:58
 * 描述 验证Smtp邮箱服务是否可用
 * @version 1.0
 * @since 1.0
 */
public class TestValidatorMailSmtp {

    public static void main(String[] args) {

        int port = 25;
        String host = "smtp.qq.com";
        String user = "xxxxxxx@qq.com";
        String pwd = "fjdigyzijviodg";

        Consumer<String> consumer = System.out::println;
        Properties props = new Properties();
        // required for gmail
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        // or use getDefaultInstance instance if desired...
        Session session = Session.getInstance(props, null);
        try {
            Transport transport = session.getTransport("smtp");
            transport.connect(host, port, user, pwd);
            transport.close();
            consumer.accept("success");
        } catch (AuthenticationFailedException e) {
            consumer.accept("AuthenticationFailedException - for authentication failures");
            e.printStackTrace();
        } catch (MessagingException e) {
            consumer.accept("for other failures");
            e.printStackTrace();
        } catch (Exception e) {
            consumer.accept("other exception");
            e.printStackTrace();
        }
    }
}
