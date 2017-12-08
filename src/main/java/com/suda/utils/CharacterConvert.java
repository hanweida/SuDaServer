package com.suda.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ES-BF-IT-126 on 2017/12/8.
 */
public class CharacterConvert {
    /**
     * Unicode 转中文 \\u516c 转为公牛
     * @author:ES-BF-IT-126
     * @method:decodeUnicode
     * @date:Date 2017/12/8
     * @params:[dataStr]
     * @returns:java.lang.String
     */
    public static String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }

    /**
     * Unicode 转中文 \\u516c 转为公牛
     * @author:ES-BF-IT-126
     * @method:decodeUnicode
     * @date:Date 2017/12/8
     * @params:[dataStr]
     * @returns:java.lang.String
     */
    public static String convert(String utfString){
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while((i=utfString.indexOf("\\u", pos)) != -1){
            sb.append(utfString.substring(pos, i));
            if(i+5 < utfString.length()){
                pos = i+6;
                sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));
            }
        }

        return sb.toString();
    }

    /**
     * Unicode 转中文 \u516c 转为公牛
     * @author:ES-BF-IT-126
     * @method:decodeUnicode
     * @date:Date 2017/12/8
     * @params:[dataStr]
     * @returns:java.lang.String
     */
    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

        Matcher matcher = pattern.matcher(str);

        char ch;

        while (matcher.find()) {

            ch = (char) Integer.parseInt(matcher.group(2), 16);

            str = str.replace(matcher.group(1), ch+"" );

        }
        return str;
    }
}
