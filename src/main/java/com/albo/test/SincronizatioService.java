package com.albo.test;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.albo.domain.Colaborator;
import com.albo.domain.Comic;
import com.albo.domain.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

@Component
public class SincronizatioService {

	private static final Logger log = LoggerFactory.getLogger(SincronizatioService.class);
	
	@Value("${app.urlBase}")
	private String urlBase;
	
	@Autowired
	private ColaboratorRepository colaboratorRepository;

	@Scheduled(fixedRate = 15000)
	public void reportCurrentTime() throws JsonMappingException, JsonProcessingException {
       
		String urlCharacters = urlBase + "/characters";
		JSONArray list = Unirest.get(urlCharacters)
				.header("accept", "application/json").queryString("ts", "1")
				.queryString("apikey", "fb78df34e0c68917916824f00c64e5b5")
				.queryString("hash", "41751dd0559ab98466d95e2946a69450").asJson().getBody().getObject()
				.getJSONObject("data").getJSONArray("results");
		for (int i = 0; i < list.length(); i++) {
			JSONObject object = list.getJSONObject(i);
			String characterId = object.getString("id");
			ArrayList<String> editors = new ArrayList<String>();
			ArrayList<String> colorists = new ArrayList<String>();
			ArrayList<String> writers = new ArrayList<String>();
			Colaborator c = new Colaborator(Long.valueOf(characterId), new Date());
			
			
			
			
			
			
			String url = urlBase +"/characters/";
			url = url + characterId + "/comics";
			JSONArray listComics = Unirest.get(url).header("accept", "application/json").queryString("ts", "1")
					.queryString("apikey", "fb78df34e0c68917916824f00c64e5b5")
					.queryString("hash", "41751dd0559ab98466d95e2946a69450").asJson().getBody().getObject()
					.getJSONObject("data").getJSONArray("results");

			ObjectMapper mapper = new ObjectMapper();
			Comic[] comics = mapper.readValue(listComics.toString(), Comic[].class);

			for (Comic comic : comics) {
				log.info("Numero de creadores por comic"+comic.getCreators().getItems().size());
				List<Item> items = comic.getCreators().getItems();
				for (Item item : items) {
					log.info("Creator: "+ item.getName() + "Role: "+item.getRole());
					
					
					if(item.getRole().equals("editor")) {
						editors.add(item.getName());
					}

					if(item.getRole().equals("colorist")) {
						colorists.add(item.getName());
					}

					if(item.getRole().equals("writer")) {
						writers.add(item.getName());
					}
					
					
				}
				
				
			}
			c.setColirist(colorists);
			c.setWriters(writers);
			c.setEditors(editors);
			colaboratorRepository.save(c);
		}

	}
}
