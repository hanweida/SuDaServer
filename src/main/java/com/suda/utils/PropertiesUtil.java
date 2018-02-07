package com.suda.utils;

import com.suda.web.ContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import java.util.Locale;

/**
 * 读取资源文件
 * Created with IntelliJ IDEA.
 * Class: PropertiesUtil
 * User: weida
 */
public class PropertiesUtil {

    /**
     * 带默认值的读取方式
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getProperties(String key, String defaultValue) {
        String value = getProperties(key);
        return (value != null && !value.equals("") ? value : defaultValue);
    }

//    /**
//     * 通过key读取
//     * @param key
//     * @return
//     */
    public static String getProperties(String key) {
        ApplicationContext appContext = ContextHolder.getApplicationContext();
        String value = "";
        try {
            value = appContext.getMessage(key, new Object[0], ContextHolder.getInstance().getLocal());
            return (value != null && !value.equals("") ? value : null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
    * 通过key读取
    * @param key
    * @return
    */
//    public static String getProperties(String key) {
//        MessageSource resources = new ClassPathXmlApplicationContext("spring-base.xml");
//        String value = "";
//        try {
//            value = resources.getMessage(key, new Object[0], Locale.getDefault());
//            return (value != null && !value.equals("") ? value : null);
//        } catch (Exception e) {
//            return null;
//        }
//    }
}
