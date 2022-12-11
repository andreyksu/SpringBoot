package ru.annikonenkov.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.annikonenkov.enity.pet.Pet;

@Service
public class PetService {

    @Autowired
    private ru.annikonenkov.repository.PetRepository petRepository;

    public List<Pet> getAll() {
        return petRepository.findAll();
    }

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

}
