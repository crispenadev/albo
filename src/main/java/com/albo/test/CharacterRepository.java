package com.albo.test;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.albo.domain.CharacterResponse;

@Repository
public interface CharacterRepository extends MongoRepository<CharacterResponse, Long> {
	
	public CharacterResponse findByName(String name);

}
