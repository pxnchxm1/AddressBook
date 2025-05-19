package com.example.demo.controllers;

import com.example.demo.models.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/addressbook")
public class AddressBookController {

    private List<Person> personList = new ArrayList<>();
    private long counter = 1;

    // GET all persons
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        return ResponseEntity.ok(personList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable long id) {
        for (Person person : personList) {
            if (person.getId() == id) {
                return ResponseEntity.ok(person);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        person.setId(counter++);
        personList.add(person);
        return ResponseEntity.ok(person);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable long id, @RequestBody Person updatedPerson) {
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getId() == id) {
                updatedPerson.setId(id);
                personList.set(i, updatedPerson);
                return ResponseEntity.ok(updatedPerson);
            }
        }
        return ResponseEntity.notFound().build();
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable long id) {
        boolean removed = personList.removeIf(p -> p.getId() == id);
        return removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
