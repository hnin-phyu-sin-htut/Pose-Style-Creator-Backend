package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AvatarDto {
	
	private Long id;
	private String sourceImageUrl;
	private String avatarImageUrl;
	private LocalDateTime createdAt = LocalDateTime.now();
	
	public AvatarDto(String sourceImageUrl, String avatarImageUrl) {
		super();
		this.sourceImageUrl = sourceImageUrl;
		this.avatarImageUrl = avatarImageUrl;
	} 

}
