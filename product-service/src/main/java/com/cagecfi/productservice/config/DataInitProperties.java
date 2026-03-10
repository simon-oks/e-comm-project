package com.cagecfi.productservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "product.data-init")
public class DataInitProperties {
    private boolean enabled;
    private String imagesDir;
    private List<String> imageFiles;
}