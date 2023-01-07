package ru.annikonenkov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.annikonenkov.enity.person.photo.PersonPhoto;

@Service
public class PersonPhotoService {

    @Autowired
    private ru.annikonenkov.repository.PersonPhotoRepository personPhotoRepository;

    public PersonPhoto getById(Integer id) {
        return personPhotoRepository.getById(id);
    }

    public PersonPhoto saveNewPersonPhoto(PersonPhoto personPhoto) {
        return personPhotoRepository.save(personPhoto);
    }

    public void flush() {
        personPhotoRepository.flush();
    }

}
