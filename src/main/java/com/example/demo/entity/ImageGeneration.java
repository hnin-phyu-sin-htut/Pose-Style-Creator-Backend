package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "image_generation_tbl")
public class ImageGeneration {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "background_image")
	private String backgroundImage;
	
	private String prompt;
	
	@Column(name = "source_image_url")
	private String sourceImageUrl;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now(); 
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "imageGeneration", cascade = CascadeType.PERSIST)
	private List<Avatar> avatars = new ArrayList<>();

	public ImageGeneration(Long id, String backgroundImage, String prompt, String sourceImageUrl,
			LocalDateTime createdAt, User user, List<Avatar> avatars) {
		super();
		this.id = id;
		this.backgroundImage = backgroundImage;
		this.prompt = prompt;
		this.sourceImageUrl = sourceImageUrl;
		this.createdAt = createdAt;
		this.user = user;
		this.avatars = avatars;
	}	
	
}
