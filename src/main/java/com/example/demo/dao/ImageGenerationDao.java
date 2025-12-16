package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ImageGeneration;

public interface ImageGenerationDao extends JpaRepository<ImageGeneration, Long> {
	
	List<ImageGeneration> findByUserId(Long userId);
	
}
