package com.albo.test;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	private ColaboratorRepository repository;

	@RequestMapping("/greeting")
	public List<Colaborator> greeting(@RequestParam(value="name", defaultValue="World") String name) {
		
		ArrayList<String> editors = new ArrayList<String>();
		ArrayList<String> colorists = new ArrayList<String>();
		ArrayList<String> writers = new ArrayList<String>();
	    Colaborator c=new Colaborator(300, new Date());
	    c.setColirist(colorists);
	    c.setWriters(writers);
        c.setEditors(editors);
	    repository.save(c);
	    
		HttpResponse<JsonNode> response = Unirest.get("https://gateway.marvel.com/v1/public/characters")
			      .header("accept", "application/json")
			      .queryString("ts", "1")
			      .queryString("name", "Spider-Man")
			      .queryString("apikey", "fb78df34e0c68917916824f00c64e5b5")
			      .queryString("hash", "41751dd0559ab98466d95e2946a69450")
			      .asJson();

		System.out.println(response.getStatus());
		System.out.println(response.getBody());
		List<Colaborator> cf = repository.findAll();
		return cf ;
	}
}
