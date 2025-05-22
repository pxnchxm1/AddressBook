package com.example.demo.mappers;

import com.example.demo.models.Person;
import com.example.demo.dtos.PersonDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonMapper {

    public Person toEntity(PersonDTO dto) {
        Person person = new Person();
        person.setId(dto.getId());
        person.setName(dto.getName());
        person.setEmail(dto.getEmail());
        person.setPhone(dto.getPhone());
        person.setAddress(dto.getAddress());
        return person;
    }

    public PersonDTO toDTO(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setName(person.getName());
        dto.setEmail(person.getEmail());
        dto.setPhone(person.getPhone());
        dto.setAddress(person.getAddress());
        return dto;
    }

    public List<PersonDTO> toDTOList(List<Person> persons) {
        return persons.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}