package com.xniax.common;

public class NiaStringUtils {

    public static String upperFirst(String str) {
        char[] chars = str.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] = (char) (chars[0] - 32);
        }
        return String.valueOf(chars);
    }
}
