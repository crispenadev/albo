package com.albo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.albo.domain.CharacterResponse;

@RestController
public class CharacterController {

	@Autowired
	private CharacterRepository characterRepository;

	@RequestMapping("marvel/characters/{nameHero}")
	public CharacterResponse characters(@PathVariable String nameHero){
		CharacterResponse cf = characterRepository.findByName(nameHero);
		return cf;
	}

	public CharacterRepository getCharacterRepository() {
		return characterRepository;
	}

	public void setCharacterRepository(CharacterRepository characterRepository) {
		this.characterRepository = characterRepository;
	}

}
