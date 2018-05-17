package com.liceyo.elasticsearch.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取配置文件
 * @author liceyo
 * @version 2018/5/14
 */
public class PropertiesUtil {
    private static Logger logger= LogManager.getLogger(PropertiesUtil.class);

    /**
     * 读取配置文件
     * @param path 配置文件路径
     * @return 配置
     */
    public static Properties getProperties( String path) {
        Properties properties =  new Properties();
        try {
            InputStream input = PropertiesUtil.class.getResourceAsStream(path);
            InputStreamReader reader = new InputStreamReader(input, "UTF-8");
            properties.load(reader);
            reader.close();
            input.close();
        } catch (IOException e) {
            logger.error("read properties error,path:"+path,e);
        }
        return properties;
    }

    /**
     * 读取配置文件并转换为map
     * @param path 配置文件路径
     * @return 解析结果
     */
    public static Map<String, String> convertProToMap(String path) {
        Map<String, String> map = new HashMap<>();
        try {
            Properties properties = getProperties(path);
            for (Object key : properties.keySet()) {
                map.put((String) key, (String) properties.get(key));
            }
        } catch (Exception e) {
            logger.error("read properties error,path:"+path,e);
        }
        return map;
    }
}
