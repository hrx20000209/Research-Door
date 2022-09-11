package com.example.researchgate.Config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import java.io.IOException;

@Configuration
public class FileUploadConfig {
    private static final String Single_file_size = "10240KB";
    private static final String Total_file_size = "10240KB";

    @Bean
    public MultipartConfigElement multipartConfigElement() throws IOException{
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse(Single_file_size));
        factory.setMaxRequestSize(DataSize.parse(Total_file_size));
        return factory.createMultipartConfig();
    }
}
