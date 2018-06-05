package com.liceyo.elasticsearch.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author liceyo
 * @version 2018/6/5
 */
public class FileUtil {

    /**
     * 读取文件为字符串
     * @param path 文件路径
     * @return 文本
     * @throws FileNotFoundException 文件未找到异常
     */
    public static String read(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return buffer.toString();
    }
}
