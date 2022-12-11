package ru.annikonenkov.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.annikonenkov.enity.link.PersonPetLink;

@Service
public class PersonPetLinkService {

    @Autowired
    private ru.annikonenkov.repository.PersonPetLinkRepository personPetLinkRepository;

    public List<PersonPetLink> getAll() {
        return personPetLinkRepository.findAll();
    }

    public PersonPetLink save(PersonPetLink personPetLink) {
        return personPetLinkRepository.save(personPetLink);
    }

}
