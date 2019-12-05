package com.albo.domain;

import com.albo.domain.Character;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "character")
public class CharacterResponse {

	@Id
	private long id;
    private String name;
	private String lastSync;
	private List<Character> characters;

	   public CharacterResponse(long id, String lastSync) {
   		this.id = id;
   		this.lastSync = lastSync;
   	}

	
	
	
	public long getId() {
		return id;
	}

	public String getLastSync() {
		return lastSync;
	}

	public void setLastSync(String lastSync) {
		this.lastSync = lastSync;
	}

	public List<Character> getCharacters() {
		return characters;
	}

	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}

}
