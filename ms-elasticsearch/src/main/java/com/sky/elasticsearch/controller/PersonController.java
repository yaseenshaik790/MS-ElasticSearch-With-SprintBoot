package com.sky.elasticsearch.controller;

import com.sky.elasticsearch.document.Person;
import com.sky.elasticsearch.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {


    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public void savePerson(@RequestBody Person person){

        personService.savePerson(person);
    }

    @GetMapping("/{id}")
    public Object findById(@PathVariable String id){

        return personService.findById(id);
    }
}
