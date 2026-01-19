package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.ImageGenerationDao;
import com.example.demo.dto.ImageGenerationDto;
import com.example.demo.entity.ImageGeneration;

@Service
public class ImageGenerationService {

    @Autowired
    private ImageGenerationDao imageGenerationDao;

    @Value("${stability.api-key}")
    private String apiKey;

    @Value("${stability.api-url}")
    private String stabilityUrl;
    
    public ImageGenerationDto generateImage(
            MultipartFile file,
            String prompt
    ) throws Exception {

        byte[] fileBytes = file.getBytes();

        String originalName =
                Optional.ofNullable(file.getOriginalFilename())
                        .orElse("upload.png");

        String filename = System.currentTimeMillis() + "_" + originalName;

        // Save background
        Path bgDir = Paths.get("uploads/backgrounds");
        Files.createDirectories(bgDir);
        Files.write(bgDir.resolve(filename), fileBytes);

        // Call Stability with BYTES (not MultipartFile)
        byte[] generatedImage =
                callStabilityAI(fileBytes, originalName, prompt);

        // Save generated
        String generatedFilename = "gen_" + filename;
        Path genDir = Paths.get("uploads/generated");
        Files.createDirectories(genDir);
        Files.write(genDir.resolve(generatedFilename), generatedImage);

        ImageGeneration image = new ImageGeneration();
        image.setPrompt(prompt);
        image.setBackgroundImage("/uploads/backgrounds/" + filename);
        image.setSourceImageUrl("/uploads/generated/" + generatedFilename);

        ImageGeneration saved = imageGenerationDao.save(image);

        return new ImageGenerationDto(
                saved.getId(),
                saved.getBackgroundImage(),
                saved.getPrompt(),
                saved.getSourceImageUrl(),
                saved.getCreatedAt()
        );
    }
    
    private byte[] callStabilityAI(
            byte[] imageBytes,
            String filename,
            String prompt
    ) throws Exception {

        String boundary = "----StabilityBoundary" + System.currentTimeMillis();
        ByteArrayOutputStream body = new ByteArrayOutputStream();

        // Image
        body.write(("--" + boundary + "\r\n").getBytes());
        body.write(("Content-Disposition: form-data; name=\"image\"; filename=\"" +
                filename + "\"\r\n").getBytes());
        body.write("Content-Type: image/png\r\n\r\n".getBytes());
        body.write(imageBytes);
        body.write("\r\n".getBytes());

        // Prompt
        body.write(("--" + boundary + "\r\n").getBytes());
        body.write("Content-Disposition: form-data; name=\"prompt\"\r\n\r\n".getBytes());
        body.write(prompt.getBytes(StandardCharsets.UTF_8));
        body.write("\r\n".getBytes());

        // End
        body.write(("--" + boundary + "--\r\n").getBytes());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(stabilityUrl))
                .header("Authorization", "Bearer " + apiKey)
                .header("Accept", "image/*")
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(body.toByteArray()))
                .build();

        HttpResponse<byte[]> response =
                HttpClient.newHttpClient()
                        .send(request, HttpResponse.BodyHandlers.ofByteArray());
        
        if (response.statusCode() != 200) {
            System.err.println(
                new String(response.body(), StandardCharsets.UTF_8)
            );
            System.err.println("Stability error body:");
            System.err.println(new String(response.body(), StandardCharsets.UTF_8));

            throw new RuntimeException(
                "Stability API error: " + response.statusCode()
            );

        }

        return response.body();
    }

    public List<ImageGeneration> getUserUploadedImages(Long userId) {
        return imageGenerationDao.findByUserId(userId);
    }
}

