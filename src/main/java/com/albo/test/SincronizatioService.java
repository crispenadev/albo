package com.albo.test;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import com.albo.domain.CharacterResponse;
import com.albo.domain.ColaboratorResponse;
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
	
	@Value("${comicPath}")
	private String comicPath;

	@Value("${ts}")
	private String ts;
	
	@Value("${datePattern}")
	private String datePattern;

	@Autowired
	private ColaboratorRepository colaboratorRepository;

	@Autowired
	private CharacterRepository characterRepository;

	@Scheduled(cron = "0 0 0 * * ?")
	public void saveCreators() throws JsonMappingException, JsonProcessingException {

		String urlCharacters = urlBase + characterPath;
		JSONArray arrayCharacteres = clientToMarvel(urlCharacters);

		for (int i = 0; i < arrayCharacteres.length(); i++) {
			JSONObject object = arrayCharacteres.getJSONObject(i);
			String characterId = object.getString("id");
			String nameHero = object.getString("name");
			Set<String> editors = new HashSet<String>();
			Set<String> colorists = new HashSet<String>();
			Set<String> writers = new HashSet<String>();
			ColaboratorResponse c = new ColaboratorResponse(Long.valueOf(characterId), getDateFormat());
			Comic[] comics = getComics(i, arrayCharacteres);

			for (Comic comic : comics) {
				log.info("Number of creators" + comic.getCreators().getItems().size());
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

	@Scheduled(cron = "0 0 1 * * ?")
	public void saveCharacters() throws JsonMappingException, JsonProcessingException {
		String urlCharacters = urlBase + characterPath;
		JSONArray arrayCharacteres = clientToMarvel(urlCharacters);

		for (int i = 0; i < arrayCharacteres.length(); i++) {
			Map<String, List<String>> characterComic = new HashMap<String, List<String>>();
			List<Character> charactes = new ArrayList<Character>();
			JSONObject object = arrayCharacteres.getJSONObject(i);
			String characterId = object.getString("id");
			String nameHero = object.getString("name");
			Comic[] comics = getComics(i, arrayCharacteres);

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

			for (Map.Entry<String, List<String>> entry : characterComic.entrySet()) {
				if(!entry.getKey().equals(nameHero)) {
				Character ch = new Character();
				ch.setCharacter(entry.getKey());
				ch.setComics(entry.getValue());
				charactes.add(ch);
				}
				
			}

			CharacterResponse chR = new CharacterResponse(Long.valueOf(characterId), getDateFormat());
			chR.setCharacters(charactes);
			chR.setName(nameHero);
			characterRepository.save(chR);
		}

	}

	public JSONArray clientToMarvel(String url) {
		
		JSONArray arrayCharacteres = new JSONArray();
		try {
		arrayCharacteres = Unirest.get(url).header("accept", "application/json").queryString("ts", ts)
				.queryString("apikey", apikey).queryString("hash", hash).asJson().getBody().getObject()
				.getJSONObject("data").getJSONArray("results");
		}
		catch(HttpClientErrorException e) {
			e.printStackTrace();
		}
		
		
		return arrayCharacteres;
	}

	public String getDateFormat() {
		String pattern = datePattern;
		String date = "";
		try {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		 date = simpleDateFormat.format(new Date());}
		catch(DateTimeException e) {
			e.printStackTrace();
		}
	
		return date;
	}

	public Comic[] getComics(int index, JSONArray arrayCharacteres) {
		JSONObject object = arrayCharacteres.getJSONObject(index);
		String characterId = object.getString("id");
		String urlComics = urlBase + characterPath + "/";
		urlComics = urlComics + characterId + comicPath;
		JSONArray arrayComics = clientToMarvel(urlComics);
		ObjectMapper mapper = new ObjectMapper();
		Comic[] comics = null;
		try {
			comics = mapper.readValue(arrayComics.toString(), Comic[].class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return comics;
	}
}
