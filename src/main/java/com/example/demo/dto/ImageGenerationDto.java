package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageGenerationDto {

	private Long id;
	private String backgroundImage;
	private String prompt;
	private String sourceImageUrl;
	private LocalDateTime createdAt = LocalDateTime.now();
	
}
