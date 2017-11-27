package com.suda.web;

import org.springframework.context.ApplicationContext;

import java.util.Locale;

/**
 * spring 上下文
 * Created with IntelliJ IDEA.
 * Class: ContextHolder
 * User: jerry
 * Date: 17-10-19
 * Time: 下午5:20
 * To change this template use File | Settings | File Templates.
 */
public class ContextHolder {

    private final static ContextHolder instance = new ContextHolder();

    private static ApplicationContext ac;

    private static Locale local;

    private ContextHolder() {
    }

    public static ContextHolder getInstance() {
        return instance;
    }

    public synchronized void setApplicationContext(ApplicationContext ac) {
        this.ac = ac;
    }

    public static ApplicationContext getApplicationContext() {
        return ac;
    }

    public static Object getBean(String name) {
        return ContextHolder.getInstance().getApplicationContext().getBean(name);
    }

    public static Locale getLocal() {
        return local;
    }

    public static void setLocal(Locale local) {
        ContextHolder.local = local;
    }
}