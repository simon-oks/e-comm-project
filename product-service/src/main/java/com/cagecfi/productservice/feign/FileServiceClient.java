package com.cagecfi.productservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@FeignClient(name = "file-service", path = "/api/files")
public interface FileServiceClient {

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Map<String, String>> uploadFile(@RequestPart("file") MultipartFile file);
}