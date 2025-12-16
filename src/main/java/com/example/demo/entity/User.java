package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_tbl")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String username;
	
	private String password;
	
	@Column(unique = true)
	private String email;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "user_type", columnDefinition = "")
	private UserType userType;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now(); 

	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
	private List<ImageGeneration> images = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
	private List<Avatar> avatars = new ArrayList<>();
	
	public enum UserType{
		USER, ADMIN
	}
	
	public User(String username, String password, String email, UserType userType) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.userType = userType;
	}
	
}
