package com.albo.test;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.albo.domain.ColaboratorResponse;

@Repository
public interface ColaboratorRepository extends MongoRepository<ColaboratorResponse, Long> {
	
	public ColaboratorResponse findByName(String name);
}
