package com.albo.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albo.domain.Colaborator;



@RestController
public class CreatorController {

	@Autowired
	private CreatorRepository creatorRepository;
	

	 @Value("${app.urlBase}")
	 private String urlBase;

	
	
	@RequestMapping("marvel/characters/{nameHero}")
	public List<Colaborator> characters() {
		return null;

	}



	public CreatorRepository getCreatorRepository() {
		return creatorRepository;
	}



	public void setCreatorRepository(CreatorRepository creatorRepository) {
		this.creatorRepository = creatorRepository;
	}
}
