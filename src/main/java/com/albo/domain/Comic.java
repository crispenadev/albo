package com.albo.domain;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comic {
 
	public int id;
	
	public String title;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Creator creators;
	
	
	public CharacterMarvel getCharacters() {
		return characters;
	}

	public void setCharacters(CharacterMarvel characters) {
		this.characters = characters;
	}

	public CharacterMarvel characters;

	

	public Creator getCreators() {
		return creators;
	}

	public void setCreators(Creator creators) {
		this.creators = creators;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}
