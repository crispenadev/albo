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

import com.albo.domain.CharacterResponse;
import com.albo.domain.Colaborator;
import com.albo.domain.Comic;
import com.albo.domain.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.albo.domain.Character;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

@Component
public class SincronizatioService {

	private static final Logger log = LoggerFactory.getLogger(SincronizatioService.class);

	@Value("${urlBase}")
	private String urlBase;

	@Value("${apikey}")
	private String apikey;

	@Value("${hash}")
	private String hash;

	@Value("${characterPath}")
	private String characterPath;

	@Value("${ts}")
	private String ts;

	@Autowired
	private ColaboratorRepository colaboratorRepository;
	
	@Autowired
	private CharacterRepository characterRepository;

	@Scheduled(cron = "0 */50 * ? * *")
	public void saveCreators() throws JsonMappingException, JsonProcessingException {

		String urlCharacters = urlBase + characterPath;
		JSONArray arrayCharacteres = clientToMarvel(urlCharacters);

		for (int i = 0; i < arrayCharacteres.length(); i++) {
			JSONObject object = arrayCharacteres.getJSONObject(i);
			String characterId = object.getString("id");
			ArrayList<String> editors = new ArrayList<String>();
			ArrayList<String> colorists = new ArrayList<String>();
			ArrayList<String> writers = new ArrayList<String>();
			Colaborator c = new Colaborator(Long.valueOf(characterId), new Date());

			String urlComics = urlBase + characterPath + "/";
			urlComics = urlComics + characterId + "/comics";
			JSONArray arrayComics = clientToMarvel(urlComics);

			ObjectMapper mapper = new ObjectMapper();
			Comic[] comics = mapper.readValue(arrayComics.toString(), Comic[].class);

			for (Comic comic : comics) {
				log.info("Numero de creadores por comic" + comic.getCreators().getItems().size());
				List<Item> items = comic.getCreators().getItems();
				for (Item item : items) {
					log.info("Creator: " + item.getName() + "Role: " + item.getRole());
					if (item.getRole().equals("editor")) {
						editors.add(item.getName());
					}
					if (item.getRole().equals("colorist")) {
						colorists.add(item.getName());
					}
					if (item.getRole().equals("writer")) {
						writers.add(item.getName());
					}
				}

			}
			c.setColirist(colorists);
			c.setWriters(writers);
			c.setEditors(editors);
			c.setLastSync(new Date());
			colaboratorRepository.save(c);
		}

	}

	@Scheduled(cron = "0 */1 * ? * *")
	public void saveCharacters() throws JsonMappingException, JsonProcessingException {
		CharacterResponse c = new CharacterResponse(4,new Date());
		List<Character> characters = new ArrayList<Character>();
		
		
		String characterName = "Squirrel Girl";
		List<String> comics = new  ArrayList<String>();
		comics.add("Iron Man");
		
		Character ch = new Character();
		
		characters.add(ch);
		ch.setCharacter(characterName);
		ch.setComics(comics);
		
		c.setCharacters(characters);
		
		characterRepository.save(c);

	}

	public JSONArray clientToMarvel(String url) {

		log.info(url);
		JSONArray arrayCharacteres = Unirest.get(url).header("accept", "application/json").queryString("ts", ts)
				.queryString("apikey", apikey).queryString("hash", hash).asJson().getBody().getObject()
				.getJSONObject("data").getJSONArray("results");
		return arrayCharacteres;
	}

}
