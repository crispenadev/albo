package com.albo.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comic {
 
	public int id;
	
	public Creator creators;

	

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
