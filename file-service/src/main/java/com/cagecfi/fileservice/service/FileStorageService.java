package com.cagecfi.fileservice.service;

import com.cagecfi.fileservice.config.FileStorageConfig;
import com.cagecfi.fileservice.exceptions.FileNotFoundException;
import com.cagecfi.fileservice.exceptions.FileStorageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final FileStorageConfig config;

    /**
     * Stocke un fichier sur le système de fichiers local.
     * @return Le nom unique du fichier sauvegardé
     */
    public String storeFile(MultipartFile file) {
        // 1. Validation du type MIME
        String contentType = file.getContentType();
        if (contentType == null || !config.getAllowedTypes().contains(contentType)) {
            throw new FileStorageException("Type de fichier non autorisé : " + contentType);
        }

        // 2. Nettoyage du nom original
        String originalFilename = StringUtils.cleanPath(
                file.getOriginalFilename() != null ? file.getOriginalFilename() : "file"
        );

        // 3. Vérification des path traversal attacks
        if (originalFilename.contains("..")) {
            throw new FileStorageException("Nom de fichier invalide : " + originalFilename);
        }

        // 4. Génération d'un nom unique pour éviter les collisions
        String extension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = originalFilename.substring(dotIndex);
        }
        String uniqueFilename = UUID.randomUUID() + extension;

        // 5. Sauvegarde sur le disque
        try {
            Path targetLocation = Paths.get(config.getUploadDir()).resolve(uniqueFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("Fichier sauvegardé : {}", uniqueFilename);
            return uniqueFilename;
        } catch (IOException ex) {
            throw new FileStorageException("Impossible de stocker le fichier : " + originalFilename);
        }
    }

    /**
     * Charge un fichier comme Resource téléchargeable.
     */
    public Resource loadFileAsResource(String filename) {
        try {
            Path filePath = Paths.get(config.getUploadDir()).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("Fichier introuvable : " + filename);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("Fichier introuvable : " + filename);
        }
    }
}