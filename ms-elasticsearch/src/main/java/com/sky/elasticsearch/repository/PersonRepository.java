package com.sky.elasticsearch.repository;

import com.sky.elasticsearch.document.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PersonRepository extends ElasticsearchRepository<Person, String> {

}
