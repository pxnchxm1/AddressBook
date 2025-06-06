package com.example.demo.controllers;

import com.example.demo.dtos.PersonDTO;
import com.example.demo.services.PersonService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressbook")
public class AddressBookController {
    
	@Autowired
    private final PersonService personService;

    public AddressBookController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        return ResponseEntity.ok(personService.getAllPersons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable long id) {
        PersonDTO person = personService.getPersonById(id);
        return ResponseEntity.ok(person);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> addPerson(@Valid @RequestBody PersonDTO personDTO) {
        return ResponseEntity.ok(personService.addPerson(personDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable long id, @Valid @RequestBody PersonDTO updatedDTO) {
        return personService.updatePerson(id, updatedDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable long id) {
        return personService.deletePerson(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}
