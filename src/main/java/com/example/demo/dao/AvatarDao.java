package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Avatar;

public interface AvatarDao extends JpaRepository<Avatar, Long> {
	
	List<Avatar> findByUserId(Long userId);

}
