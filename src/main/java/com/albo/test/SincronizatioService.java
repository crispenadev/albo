package com.albo.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Scheduled(cron = "0 */44 * ? * *")
	public void saveCreators() throws JsonMappingException, JsonProcessingException {

		String urlCharacters = urlBase + characterPath;
		JSONArray arrayCharacteres = clientToMarvel(urlCharacters);

		for (int i = 0; i < arrayCharacteres.length(); i++) {
			JSONObject object = arrayCharacteres.getJSONObject(i);
			String characterId = object.getString("id");
			String nameHero = object.getString("name");
			ArrayList<String> editors = new ArrayList<String>();
			ArrayList<String> colorists = new ArrayList<String>();
			ArrayList<String> writers = new ArrayList<String>();
			
			String pattern = "dd/M/yyyy hh:mm:ss";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());
			
			
			
			Colaborator c = new Colaborator(Long.valueOf(characterId), date);

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
			c.setName(nameHero);
			colaboratorRepository.save(c);
		}

	}

	@Scheduled(cron = "0 */44 * ? * *")
	public void saveCharacters() throws JsonMappingException, JsonProcessingException {
		String urlCharacters = urlBase + characterPath;
		JSONArray arrayCharacteres = clientToMarvel(urlCharacters);

		

		for (int i = 0; i < arrayCharacteres.length(); i++) {
			Map<String, List<String>> characterComic = new HashMap<String, List<String>>();
			
			List<Character> charactes = new ArrayList<Character>();
			JSONObject object = arrayCharacteres.getJSONObject(i);
			String characterId = object.getString("id");
			String nameHero = object.getString("name");
			String urlComics = urlBase + characterPath + "/";
			urlComics = urlComics + characterId + "/comics";
			JSONArray arrayComics = clientToMarvel(urlComics);
			ObjectMapper mapper = new ObjectMapper();
			Comic[] comics = mapper.readValue(arrayComics.toString(), Comic[].class);

			for (Comic comic : comics) {
				log.info("Número de caracteres por comic" + comic.getCharacters().getItems().size());
				List<Item> items = comic.getCharacters().getItems();
				for (Item item : items) {
					log.info("Character: " + item.getName());

					List<String> set = characterComic.get(item.getName());
					if (set == null) {
						set = new ArrayList<String>();
						characterComic.put(item.getName(), set);
					}
					set.add(comic.getTitle());

				}
			}
			
			log.info("Tamaño del mapa" + characterComic.size());

			for (Map.Entry<String, List<String>> entry : characterComic.entrySet()) {
				log.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				
				Character ch = new Character();
				ch.setCharacter(entry.getKey());
				ch.setComics(entry.getValue());
				charactes.add(ch);
				
			}
			
			String pattern = "dd/M/yyyy hh:mm:ss";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());
			
			CharacterResponse chR = new CharacterResponse(Long.valueOf(characterId), date);
			chR.setCharacters(charactes);
            chR.setName(nameHero);
			characterRepository.save(chR);
			
		}
		

	}

	public JSONArray clientToMarvel(String url) {

		log.info(url);
		JSONArray arrayCharacteres = Unirest.get(url).header("accept", "application/json").queryString("ts", ts)
				.queryString("apikey", apikey).queryString("hash", hash).asJson().getBody().getObject()
				.getJSONObject("data").getJSONArray("results");
		return arrayCharacteres;
	}

}
