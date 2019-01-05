package com.typicalbot.util;

public class StringUtil {
    public static String firstUpperCase(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1, input.length()).toLowerCase();
    }
}
