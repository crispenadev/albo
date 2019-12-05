package com.albo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albo.domain.ColaboratorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
public class ColaboratorController {

	@Autowired
	private ColaboratorRepository colaboratorRepository;

	@RequestMapping("marvel/colaborators/{nameHero}")
	public ColaboratorResponse colaboratorsByHero(@PathVariable String nameHero)
			throws JsonMappingException, JsonProcessingException {
		ColaboratorResponse cf = colaboratorRepository.findByName(nameHero);
		return cf;
	}

	public ColaboratorRepository getColaboratorRepository() {
		return colaboratorRepository;
	}

	public void setColaboratorRepository(ColaboratorRepository colaboratorRepository) {
		this.colaboratorRepository = colaboratorRepository;
	}

}
