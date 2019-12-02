package com.albo.test;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.albo.domain.Colaborator;

@Repository
public interface CreatorRepository extends MongoRepository<Colaborator, Long> {

}
