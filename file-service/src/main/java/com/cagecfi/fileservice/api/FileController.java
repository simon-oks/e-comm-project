package com.cagecfi.fileservice.api;

import com.cagecfi.fileservice.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(
            @RequestParam("file") MultipartFile file) {

        String filename = fileStorageService.storeFile(file);

        // Construction de l'URL de download
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/files/download/")
                .path(filename)
                .toUriString();

        log.info("Fichier uploadé avec succès : {}", filename);

        return ResponseEntity.ok(Map.of(
                "filename", filename,
                "originalName", file.getOriginalFilename() != null ? file.getOriginalFilename() : filename,
                "contentType", file.getContentType() != null ? file.getContentType() : "unknown",
                "size", String.valueOf(file.getSize()),
                "downloadUrl", fileDownloadUri
        ));
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String filename) {

        Resource resource = fileStorageService.loadFileAsResource(filename);

        // Détermine le Content-Type
        String contentType;
        try {
            contentType = resource.getFile().toPath().getFileName().toString().endsWith(".pdf")
                    ? MediaType.APPLICATION_PDF_VALUE
                    : MediaType.APPLICATION_OCTET_STREAM_VALUE;
        } catch (Exception e) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}