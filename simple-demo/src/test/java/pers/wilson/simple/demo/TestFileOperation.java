package pers.wilson.simple.demo;

import java.io.File;

/**
 * 测试文件操作
 */
public class TestFileOperation {

    /**
     * 删除文件
     *
     * @param file File
     */
    public static void deleteFile(File file) {
        String fileName = file.getName();
        if (file.delete()) {
            System.out.println("删除文件：{}成功！" + fileName);
        } else {
            System.out.println("删除文件：{}失败..." + fileName);
        }
    }

}
