package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageGenerationDto {

	private Long id;
	private String backgroundImage;
	private String prompt;
	private String sourceImageUrl;
	private LocalDateTime createdAt = LocalDateTime.now();
	
	public ImageGenerationDto(Long id, String backgroundImage, String prompt) {
		super();
		this.id = id;
		this.backgroundImage = backgroundImage;
		this.prompt = prompt;
	}
	
	
}
