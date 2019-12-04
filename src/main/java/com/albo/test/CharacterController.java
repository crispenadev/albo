package com.albo.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albo.domain.CharacterResponse;




@RestController
public class CharacterController {

	@Autowired
	private CharacterRepository creatorRepository;
	

	 @Value("${urlBase}")
	 private String urlBase;

	
	
	@RequestMapping("marvel/characters/{nameHero}")
	public List<CharacterResponse> characters() {
		return null;

	}



	public CharacterRepository getCreatorRepository() {
		return creatorRepository;
	}



	public void setCreatorRepository(CharacterRepository creatorRepository) {
		this.creatorRepository = creatorRepository;
	}
}
