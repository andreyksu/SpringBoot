package ru.annikonenkov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.annikonenkov.enity.photo.PersonPhoto;

@Service
public class PersonPhotoService {

    @Autowired
    private ru.annikonenkov.repository.PersonPhotoRepository personPhotoRepository;
    
    public PersonPhoto getById(Integer id) {
        return personPhotoRepository.getById(id);
    }

    public PersonPhoto save(PersonPhoto personPhoto) {
        return personPhotoRepository.save(personPhoto);
    }

}
