package com.example.demo.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.ImageGenerationDao;
import com.example.demo.dto.AvatarDto;
import com.example.demo.dto.ImageGenerationDto;
import com.example.demo.entity.Avatar;
import com.example.demo.entity.ImageGeneration;

@Service
public class ImageGenerationService {
	
	@Autowired
	private ImageGenerationDao imageGenerationDao;
	
	public ImageGenerationDto generateImage(
            MultipartFile file,
            String prompt
    ) throws Exception {

        // Save file
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path uploadDir = Paths.get("uploads");
        Files.createDirectories(uploadDir);
        Path filePath = uploadDir.resolve(filename);
        Files.write(filePath, file.getBytes());

        ImageGeneration image = new ImageGeneration();
        image.setPrompt(prompt);
        image.setBackgroundImage("/uploads/" + filename);
        image.setUser(null);

        ImageGeneration saved = imageGenerationDao.save(image);

        return new ImageGenerationDto(
                saved.getId(),
                saved.getBackgroundImage(),
                saved.getPrompt()
        );
    }
	
	public AvatarDto generateAvatar(Avatar avatar) {
		return new AvatarDto("sourceImageUrl", "avatarImageUrl");
	}
	
	public List<ImageGeneration> getUserUploadedImages(Long userId){
		return imageGenerationDao.findByUserId(userId);
	}

}
