package com.cagecfi.fileservice.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "file.storage")
public class FileStorageConfig {

    private String uploadDir;
    private List<String> allowedTypes;

    @PostConstruct
    public void init() {
        try {
            // Crée le répertoire d'upload s'il n'existe pas
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Impossible de créer le répertoire d'upload : " + uploadDir, e);
        }
    }
}
