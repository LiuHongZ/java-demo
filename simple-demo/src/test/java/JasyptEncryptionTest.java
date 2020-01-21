import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.util.function.Consumer;

/**
 * @author
 * 日期 2019/12/26 11:59
 * 描述 jasypt加解密
 * @version 1.0
 * @since 1.0
 */
public class JasyptEncryptionTest {

    /**
     * 密钥
     */
    private static final String PASSWORD_KEY = "fjckkitulv73I2pSD3I50JMXoaxZTKJ7";

    public static void main(String[] args) {
        StandardPBEStringEncryptor ss = new StandardPBEStringEncryptor();
        ss.setPassword(PASSWORD_KEY);

        String userName = "root";
        String password = "abc123";

        String encryptUsername = ss.encrypt(userName);
        String decryptUsername = ss.decrypt(encryptUsername);

        String encryptPassword = ss.encrypt(password);
        String decryptPassword = ss.decrypt(encryptPassword);

        Consumer<String> consumer = System.out::println;
        consumer.accept("userName: " + userName);
        consumer.accept("encryptUsername: " + encryptUsername);
        consumer.accept("decryptUsername: " + decryptUsername);
        consumer.accept("");
        consumer.accept("password: " + password);
        consumer.accept("encryptPassword: " + encryptPassword);
        consumer.accept("decryptPassword: " + decryptPassword);
    }
}
