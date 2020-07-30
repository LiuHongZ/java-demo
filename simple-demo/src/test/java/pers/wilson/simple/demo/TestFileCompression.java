package pers.wilson.simple.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author
 * 日期 2020/7/30 13:58
 * 描述 测试文件压缩
 * @version 1.0
 * @since 1.0
 */
public class TestFileCompression {

    public static void main(String[] args) {
        multiFileToZip("D:\\test\\java-zip-test", "D:\\test\\java-zip-test\\10000.zip");
    }

    /**
     * 将多个文件压缩为zip，文件存放至一个文件夹中
     *
     * @param filepath 要被压缩的文件夹
     * @param zipPath  要生成的zip文件全路径
     */
    public static void multiFileToZip(String filepath, String zipPath) {
        // 要被压缩的文件夹
        File file = new File(filepath);
        // 要生成的zip文件全路径
        File zipFile = new File(zipPath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                return;
            }
            try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile))) {
                for (File itemFile : files) {
                    try (InputStream input = new FileInputStream(itemFile)) {

                        // zip包中有名为“java-zip-test”的一级目录
                        // zipOut.putNextEntry(new ZipEntry(file.getName() + File.separator + itemFile.getName()));

                        // zip包中没有一级目录，打开直接是所有文件
                        zipOut.putNextEntry(new ZipEntry(itemFile.getName()));

                        int temp = input.read();
                        while (temp != -1) {
                            zipOut.write(temp);
                            temp = input.read();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
