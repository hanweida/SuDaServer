package com.suda.servlet;


import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 初始化
 */
public class InitServlet extends HttpServlet {

    public void destroy(){
        super.destroy();
    }

	@Override
	public void init(ServletConfig config) throws ServletException {
        super.init(config);
		ServletContext sc = getServletContext();
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);

        //加载物料应用分类
        loadAppLevelDes(wac, sc);
    }

    /**
     * 加载物料应用分类
     * @param wac
     * @param sc
     */
    public void loadAppLevelDes(WebApplicationContext wac, ServletContext sc){

    }
}
