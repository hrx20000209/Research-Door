package com.example.researchgate.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "files.upload")
public class FileUploadProperties {
    private List<String> allowTypes;
}
