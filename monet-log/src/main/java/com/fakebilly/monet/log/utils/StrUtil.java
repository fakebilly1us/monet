package com.fakebilly.monet.log.utils;

/**
 * StrUtil
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class StrUtil {

    public static boolean isBlankChar(int c) {
        return Character.isWhitespace(c)
                || Character.isSpaceChar(c)
                || c == '\ufeff'
                || c == '\u202a';
    }

    public static boolean isBlankChar(char c) {
        return isBlankChar((int) c);
    }

    public static boolean isBlank(CharSequence str) {
        int length;
        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            // 只要有一个非空字符即为非空字符串
            if (false == isBlankChar(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence str) {
        return false == isBlank(str);
    }


}
