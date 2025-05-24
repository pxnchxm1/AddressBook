package com.example.demo.services;

import com.example.demo.models.Person;

import lombok.extern.slf4j.Slf4j;

import com.example.demo.dtos.PersonDTO;
import com.example.demo.exceptionHandlers.AddressBookException;
import com.example.demo.mappers.PersonMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


@Slf4j
@Service
public class PersonService {
    private final List<Person> personList = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);
    private final PersonMapper personMapper;

    public PersonService(PersonMapper personMapper) {
        this.personMapper = personMapper;
        personList.add(new Person(counter.getAndIncrement(), "John Doe", "john@example.com", "1234567890", "123 Main St"));
        personList.add(new Person(counter.getAndIncrement(), "Jane Smith", "jane@example.com", "9876543210", "456 Oak Ave"));
    }

    public List<PersonDTO> getAllPersons() {
    	log.info("Got details of all the persons :)");
        return personMapper.toDTOList(personList);
    }

    public PersonDTO getPersonById(long id) {
        return personList.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(personMapper::toDTO)
                .orElseThrow(() -> new AddressBookException("Person with ID " + id + " not found"));
    }

    public PersonDTO addPerson(PersonDTO dto) {
        Person person = personMapper.toEntity(dto);
        person.setId(counter.getAndIncrement());
        personList.add(person);
        return personMapper.toDTO(person);
    }

    public Optional<PersonDTO> updatePerson(long id, PersonDTO updatedDTO) {
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getId() == id) {
                Person updatedPerson = personMapper.toEntity(updatedDTO);
                updatedPerson.setId(id); // Preserve the original ID
                personList.set(i, updatedPerson);
                return Optional.of(personMapper.toDTO(updatedPerson));
            }
        }
        return Optional.empty();
    }

    public boolean deletePerson(long id) {
        return personList.removeIf(p -> p.getId() == id);
    }
}