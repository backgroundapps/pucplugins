package com.tecgraf.plugins.server.process.utils;

public class Strings {
    public static boolean notEmpty(String value){
        return value != null && !value.isEmpty();
    }

    public static boolean isEmpty(String value) { return value == null || value.isEmpty(); }
}
