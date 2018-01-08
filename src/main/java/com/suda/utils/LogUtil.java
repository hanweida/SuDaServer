package com.suda.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置所有log
 */
public class LogUtil {
	
	private static final Logger errorLog = LoggerFactory.getLogger("error");
	
    private static final Logger infoLog = LoggerFactory.getLogger("info");


    public static Logger getInfoLog() {
        return infoLog;
    }

	public static Logger getErrorLog() {
		return errorLog;
	}
}
