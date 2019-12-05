package com.albo.test;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.albo.domain.Colaborator;

@Repository
public interface ColaboratorRepository extends MongoRepository<Colaborator, Long> {
	
	public Colaborator findByName(String name);
}
