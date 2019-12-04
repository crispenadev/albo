package com.albo.domain;

import java.util.Date;
import com.albo.domain.Character;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "character")
public class CharacterResponse {

	@Id
	private long id;
	private Date lastSync;
	private List<Character> characters;

	   public CharacterResponse(long id, Date lastSync) {
   		this.id = id;
   		this.lastSync = lastSync;
   	}

	
	
	
	public long getId() {
		return id;
	}

	public Date getLastSync() {
		return lastSync;
	}

	public void setLastSync(Date lastSync) {
		this.lastSync = lastSync;
	}

	public List<Character> getCharacters() {
		return characters;
	}

	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}

}
