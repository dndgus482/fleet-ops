package com.bqua.fleetops.common.util;

public class HeredocEscaper {

    public static String escapeForHeredoc(String input) {
        if (input == null) return "";
        return input
                .replace("\\", "\\\\")  // backslash
                .replace("$", "\\$")     // variables
                .replace("`", "\\`");    // backticks
    }
}