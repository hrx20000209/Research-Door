package com.example.researchgate.Utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class FileName {
    public static String getResourceBasePath(){
        String pathStr = "http://43.140.248.122:8090" + getFileStorePath();
        return pathStr;
    }

    public static String getFileStorePath(){
        File path = null;
        try{
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e){

        }
        if (path == null || !path.exists()){
            path = new File("");
        }
        String pathStr = path.getAbsolutePath();
        pathStr = pathStr.replace("\\target\\classes", "");
        return pathStr;
    }

    public static void ensureDirectory(String filePath){
        if (filePath == null || filePath.equals("")) return;
        filePath = replaceSeparator(filePath);
        if (filePath.contains("/")){
            filePath = filePath.substring(0, filePath.lastIndexOf("/"));
            File file = new File(filePath);
            if(!file.exists()){
                file.mkdirs();
            }
        }
    }

    public static String replaceSeparator(String str){
        return str.replace("\\", "/").replace("\\\\", "/");
    }
}
