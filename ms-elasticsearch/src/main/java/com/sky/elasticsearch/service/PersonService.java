package com.sky.elasticsearch.service;

import com.sky.elasticsearch.document.Person;

public interface PersonService {
    Object savePerson(Person person);

    Object findById(String id);
}
