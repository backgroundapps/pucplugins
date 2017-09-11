package com.tecgraf.plugins.server.process.utils;


import com.tecgraf.plugins.common.Functionality;
import com.tecgraf.plugins.common.Plugin;

import java.util.LinkedList;
import java.util.List;

public class Mapper {
    public static List<String> getPluginNames(List<Plugin> objs){
        List<String> list = new LinkedList<>();
        for (Plugin obj : objs) {
            list.add(obj.getName());
        }
        return list;
    }

    public static  List<String>  getFunctionalityNames(List<Functionality> objs) {
        List<String> list = new LinkedList<>();
        for (Functionality obj : objs) {
            list.add(obj.getName());
        }
        return list;
    }
}
