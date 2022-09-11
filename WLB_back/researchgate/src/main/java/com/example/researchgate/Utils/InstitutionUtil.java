package com.example.researchgate.Utils;

import java.util.HashMap;
import java.util.Map;

public class InstitutionUtil {
    protected static Map<String,String> map =new HashMap<>();
    //@buaa.edu.cn @ @pku.edu.cn @tsinghua.edu.cn @bit.edu.cn @bupt.edu.cn
    static{
        map.put("@buaa.edu.cn","BUAA");
        map.put("@pku.edu.cn","Peking University");
        map.put("@tsinghua.edu.cn","Tsinghua University");
        map.put("@bit.edu.cn","BIT");
        map.put("@bupt.edu.cn","BUPT");
    }

    public static String getInstitution(String email){
        for(String key : map.keySet()){
            if(email.contains(key)){
                return map.get(key);
            }
        }
        return "Other Institution";
    }
}
