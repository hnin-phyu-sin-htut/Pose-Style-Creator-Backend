package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ImageGenerationDto;
import com.example.demo.entity.ImageGeneration;
import com.example.demo.service.ImageGenerationService;

@RestController
@RequestMapping("/api/image")
public class ImageGenerationController {
	
	@Autowired
	private ImageGenerationService imageGenerationService;
	
	@PostMapping(value = "/generate-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ImageGenerationDto> generateImage(
	        @RequestParam("file") MultipartFile file,
	        @RequestParam("prompt") String prompt
	) throws Exception {

	    ImageGenerationDto response =
	            imageGenerationService.generateImage(file, prompt);

	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/user-image/{userId}")
	public List<ImageGeneration> getUserUploadedImages(@PathVariable Long userId){
		return imageGenerationService.getUserUploadedImages(userId);
	}

}
