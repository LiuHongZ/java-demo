package pers.wilson.uploadftp.demo;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.function.Consumer;

public class UploadFtpApplication {

    public static void main(String[] args) {
//        FTP配置
        String host = "192.168.100.100";
        int port = 21;
        String username = "ftpadmin";
        String password = "abc123";
        String serverFilePath = "/data/ftpfile/";
//        本地文件配置
        String localFilePath = "D:\\BaiduNetdiskDownload\\";
        String filename = "Effective Java 中文版（第2版） - Joshua Bloch.pdf";

        File file = new File( localFilePath + "/" + filename);
        uploadFilePasv(host,
                port,
                username,
                password,
                serverFilePath,
                filename,
                file);
    }

    /**
     * Description: 向FTP服务器上传文件，使用被动模式
     *
     * @param host     FTP服务器hostname
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param filePath FTP服务器文件存放路径。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param file    输入流
     * @return 成功返回true，否则返回false
     */
    private static boolean uploadFilePasv(String host,
                                          int port,
                                          String username,
                                          String password,
                                          String filePath,
                                          String filename,
                                          File file) {

        Consumer<String> consumer = System.out::println;
        FTPClient ftp = new FTPClient();

        try (FileInputStream inputStream = new FileInputStream(file)){
            // 连接FTP服务器
            ftp.connect(host, port);
            // 登录，如果采用默认端口，可以使用ftp.connect(host)方式直接连接FTP服务器
            ftp.login(username, password);
            int reply = ftp.getReplyCode();
            consumer.accept("------------------- ReplyCode is: " + reply + " -------------------");
//            True if a reply code is a postive completion response, false if not.
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return false;
            }
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = "";
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) {
                        continue;
                    }
                    tempPath += "/" + dir;
                    //进不去目录，说明该目录不存在
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        //创建目录
                        if (!ftp.makeDirectory(tempPath)) {
                            //如果创建文件目录失败，则返回
                            consumer.accept("------------------- 创建文件目录: " + tempPath + " 失败 -------------------");
                            return false;
                        } else {
                            //目录存在，则直接进入该目录
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
//            设置为被动模式
            ftp.enterLocalPassiveMode();
            //ftp.enterLocalActiveMode();//主动
            //上传文件
            boolean storeFileResult = ftp.storeFile(filename, inputStream);
            consumer.accept("------------------- storeFileResult is: " + storeFileResult + " -------------------");
            ftp.logout();
            return storeFileResult;
        } catch (IOException e) {
            consumer.accept("上传FTP异常：" + e);
            e.printStackTrace();
            return false;
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    consumer.accept("断开FTP异常: " + e);
                    e.printStackTrace();
                }
            }
        }
    }

}
