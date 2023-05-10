package com.sky.elasticsearch.service;

import com.sky.elasticsearch.document.Person;
import com.sky.elasticsearch.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService{

    private PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Object savePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Object findById(String id) {
        return personRepository.findById(id).orElse(new Person("",""));
    }
}
