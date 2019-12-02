package com.albo.test;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albo.domain.Colaborator;
import com.albo.domain.Comic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

@RestController
public class ColaboratorController {

	@Autowired
	private ColaboratorRepository colaboratorRepository;



	@RequestMapping("marvel/colaborators/{nameHero}")
	public List<Colaborator> colaboratorsIronman(@PathVariable String nameHero)
			throws JsonMappingException, JsonProcessingException {
		List<Colaborator> cf = colaboratorRepository.findAll();
		return cf;
	}

	public ColaboratorRepository getColaboratorRepository() {
		return colaboratorRepository;
	}

	public void setColaboratorRepository(ColaboratorRepository colaboratorRepository) {
		this.colaboratorRepository = colaboratorRepository;
	}

}
