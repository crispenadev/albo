package com.albo.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albo.domain.ColaboratorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
public class ColaboratorController {
	
	
	private static final Logger log = LoggerFactory.getLogger(ColaboratorController.class);


	@Autowired
	private ColaboratorRepository colaboratorRepository;

	@RequestMapping("marvel/colaborators/{nameHero}")
	public ColaboratorResponse colaboratorsByHero(@PathVariable String nameHero)
			throws JsonMappingException, JsonProcessingException {
		ColaboratorResponse cf = colaboratorRepository.findByName(nameHero);

		if(cf == null) {
			throw new DataNotFoundException(nameHero);
		}
		
		return cf;
	}

	public ColaboratorRepository getColaboratorRepository() {
		return colaboratorRepository;
	}

	public void setColaboratorRepository(ColaboratorRepository colaboratorRepository) {
		this.colaboratorRepository = colaboratorRepository;
	}

}
