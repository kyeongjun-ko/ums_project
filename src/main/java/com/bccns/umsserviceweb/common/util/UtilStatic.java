package com.bccns.umsserviceweb.common.util;

import java.util.HashMap;
import java.util.Map;

public class UtilStatic {
    
    public static Map<String, Object> getMap(Object obj) {
        
        java.lang.reflect.Field[] fields = obj.getClass().getDeclaredFields();
        Map<String, Object> map = new HashMap<String, Object>();
        
        for(int i=0 ; i < fields.length ; i++ ) {
                try {
                    if(!fields[i].getName().equals("log") && !fields[i].getName().equals("logger")) {
                        fields[i].setAccessible(true);
                        map.put(fields[i].getName(), fields[i].get(obj));
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return map;
    }

}
