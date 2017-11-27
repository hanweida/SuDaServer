package com.suda.listener;

import com.suda.web.ContextHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.util.Locale;

/**
 * 自定义ContextLoaderListener
 */
public class DefaultContextLoaderListener extends ContextLoaderListener {

	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        ContextHolder.getInstance().setApplicationContext(context);
        ContextHolder.getInstance().setLocal(Locale.getDefault());
        ServletContext servletContext = event.getServletContext();
        // 初始化数据源
        WebApplicationContext webContext = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
        //DBConnectUtil.init(webContext);
	}
}
