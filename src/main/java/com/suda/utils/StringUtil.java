package com.suda.utils;

/**
 * Created by ES-BF-IT-126 on 2017/10/20.
 */
public class StringUtil {
    public static boolean isBlank(String str){
        int strLen;
        if(str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }
    public static boolean isNotBlank(String str){
       return !isBlank(str);
    }
}
