package com.example.researchgate.Utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntityToMap {
    /**
     * 将一个类用属性名为Key，值为Value的方式存入map
     */
    public static Map<String,Object> object2Map(Object object){
        Map<String,Object> result=new HashMap<>();
        Field[]fields=object.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                result.put(name, field.get(object));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return result;
    }
}
