package com.cagecfi.productservice;

import com.cagecfi.productservice.config.DataInitProperties;
import com.cagecfi.productservice.entity.Category;
import com.cagecfi.productservice.entity.Product;
import com.cagecfi.productservice.feign.FileServiceClient;
import com.cagecfi.productservice.repository.CategoryRepository;
import com.cagecfi.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductDataInitializer implements ApplicationRunner {

    private final FileServiceClient fileServiceClient;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final DataInitProperties properties;

    @Override
    public void run(@NonNull ApplicationArguments args) {

        // Initialisation désactivée depuis le .yaml
        if (!properties.isEnabled()) {
            log.info("[DataInit] Initialisation désactivée, on passe.");
            return;
        }

        // Aucun fichier configuré
        if (properties.getImageFiles() == null || properties.getImageFiles().isEmpty()) {
            log.warn("[DataInit] Aucun fichier configuré dans product.data-init.image-files");
            return;
        }

        log.info("[DataInit] Démarrage — {} fichier(s) à traiter", properties.getImageFiles().size());

        for (String imageFilename : properties.getImageFiles()) {
            try {
                processImageFile(imageFilename);
            } catch (Exception e) {
                // On logue l'erreur, mais on continue avec les autres fichiers
                log.error("[DataInit] Échec pour '{}' : {}", imageFilename, e.getMessage(), e);
            }
        }

        log.info("[DataInit] Terminé.");
    }

    private void processImageFile(String imageFilename) throws IOException {

        // 1. Construire le chemin complet vers le fichier local
        Path imagesDirPath = resolveImagesDirPath(properties.getImagesDir());
        Path imagePath = imagesDirPath.resolve(imageFilename);

        if (!Files.exists(imagePath)) {
            log.warn("[DataInit] Fichier introuvable, ignoré : {}", imagePath.toAbsolutePath());
            return;
        }

        // 2. Vérifier si un produit avec ce nom d'image existe déjà (idempotence)
        String productName = buildProductName(imageFilename);
        if (productRepository.existsByName(productName)) {
            log.info("[DataInit] Produit '{}' déjà existant, ignoré.", productName);
            return;
        }

        // 3. Lire le fichier et construire un MultipartFile
        byte[]   fileBytes   = Files.readAllBytes(imagePath);
        String   contentType = Files.probeContentType(imagePath);
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                imageFilename,
                contentType != null ? contentType : "application/octet-stream",
                fileBytes
        );

        // 4. Envoyer le fichier au file-service via Feign
        log.info("[DataInit] Upload de '{}' vers file-service...", imageFilename);
        ResponseEntity<Map<String, String>> response = fileServiceClient.uploadFile(multipartFile);

        if (response.getBody() == null || !response.getStatusCode().is2xxSuccessful()) {
            log.error("[DataInit] Réponse inattendue du file-service pour '{}'", imageFilename);
            return;
        }

        // 5. Récupérer le nom unique retourné par le file-service
        String storedFilename = response.getBody().get("filename");
        String downloadUrl = response.getBody().get("downloadUrl");

        log.info("[DataInit] Fichier uploadé → storedFilename='{}', url='{}'", storedFilename, downloadUrl);

        // 6. Construire et persister le produit
        Category category = getOrCreateCategory(productName);
        Product product = Product.create(
                productName,
                "Description de "+productName,
                BigDecimal
                        .valueOf(ThreadLocalRandom.current().nextDouble(100, 500))
                        .setScale(2, RoundingMode.HALF_UP),
                storedFilename,
                category);

        productRepository.save(product);
        log.info("[DataInit] Produit '{}' enregistré avec l'image '{}'", productName, storedFilename);
    }

    private Path resolveImagesDirPath(String imagesDir) {
        if (imagesDir == null || imagesDir.isBlank()) {
            return Paths.get(".");
        }
        String trimmed = imagesDir.trim();
        if (trimmed.startsWith("file:")) {
            return Paths.get(URI.create(trimmed));
        }
        return Paths.get(trimmed);
    }

    /**
     * Dérive un nom de produit lisible à partir du nom de fichier.
     * Ex: "smartphone_x1.png" → "Smartphone X1"
     */
    private String buildProductName(String filename) {
        // Retirer l'extension
        String nameWithoutExt = filename.contains(".")
                ? filename.substring(0, filename.lastIndexOf('.'))
                : filename;

        // Remplacer _ et - par des espaces, puis mettre en titre
        String[] words = nameWithoutExt.replace("-", "_").split("_");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }

    private Category getOrCreateCategory(String productName){
        String predictedCategoryName = productName.substring(0, productName.length() - 2);
        List<Category> categories = categoryRepository.findByLibelleContainingIgnoreCase(predictedCategoryName);
        if (categories.isEmpty()){
            Category category = Category.create(predictedCategoryName);
            return categoryRepository.save(category);
        }
        return categories.get(0);
    }


}