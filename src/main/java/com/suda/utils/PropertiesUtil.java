package com.suda.utils;

import com.suda.pojo.MatchInfo;
import com.suda.web.ContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

/**
 * 读取资源文件
 * Created with IntelliJ IDEA.
 * Class: PropertiesUtil
 * User: weida
 */
@Configuration
@PropertySource("classpath:sys/application.properties")
public class PropertiesUtil {
    @Autowired
    private Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer
    propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * 通过实体注解注入
     * @author:ES-BF-IT-126
     * @method:mongoTemplate
     * @date:Date 2018/2/27
     * @params:[]
     * @returns:com.suda.pojo.MatchInfo
     */
    @Bean
    public MatchInfo mongoTemplate() throws Exception {
        String mongodbUrl = env.getProperty("kuwan_url");
        String defaultDb = env.getProperty("kuwan_url");
        return new MatchInfo();

    }

    /**
     * 带默认值的读取方式
     * @param key
     * @param defaultValue
     * @return
     */
    public String getProperties(String key, String defaultValue) {
        String value = getProperties(key);
        return (value != null && !value.equals("") ? value : defaultValue);
    }

    /**
     * 通过key读取
     * @param key
     * @return
     */
//    public static String getProperties(String key) {
//        ApplicationContext appContext = ContextHolder.getApplicationContext();
//        String value = "";
//        try {
//            value = appContext.getMessage(key, new Object[0], ContextHolder.getInstance().getLocal());
//            return (value != null && !value.equals("") ? value : null);
//        } catch (Exception e) {
//            return null;
//        }
//    }

    /**
     * 通过key读取
     * @param key
     * @return
     */
    public  String getProperties(String key) {
        try {
            String value = env.getProperty(key);
            return (value != null && !value.equals("") ? value : null);
        } catch (Exception e) {
            return null;
        }
    }
}
