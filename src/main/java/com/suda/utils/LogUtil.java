package com.suda.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置所有log
 */
public class LogUtil {
	
	private static final Logger errorLog = LoggerFactory.getLogger("error");
	
    private static final Logger infoLog = LoggerFactory.getLogger("info");

    private static final Logger securityLog = LoggerFactory.getLogger("security");

    private static final Logger chargeLog = LoggerFactory.getLogger("charge");
    //运营后台管理员操作日志
    private static final Logger adminLog = LoggerFactory.getLogger("admin");

    public static Logger getInfoLog() {
        return infoLog;
    }

	public static Logger getErrorLog() {
		return errorLog;
	}

    public static Logger getSecurityLog() {
        return securityLog;
    }

    public static Logger getCharge() {
        return chargeLog;
    }

    public static Logger getAdminLog() {
        return adminLog;
    }
}
