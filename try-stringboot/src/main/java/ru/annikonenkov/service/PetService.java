package ru.annikonenkov.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.annikonenkov.enity.pet.Pet;

// TODO: Это спринговский аналог DAO?

@Service
public class PetService {

    private static final Logger log = LoggerFactory.getLogger(PetService.class);

    @Autowired
    private ru.annikonenkov.repository.PetRepository petRepository;

    // TODO: Так допустимо получать EntityManager в спринге?
    @PersistenceContext
    private EntityManager entityManager;

    public Pet savePet(Pet pet) {
        Pet savedPet = petRepository.save(pet);
        log.debug("save(...) ---- The new Pet is saved = {}", savedPet.toString());
        return savedPet;
    }

    /*
     * См. класс PersonService - там все пояснения. public void saveExistPet(Pet pet) { log.debug("saveExistPet(...) ---- Pet that will be saved as exist = {} {} ", pet.toString()); entityManager.merge(pet); }
     */

    public List<Pet> getAllPet() {
        return petRepository.findAll();
    }

    public Pet getPetById(Integer id) {
        Pet pet = petRepository.getById(id);
        log.debug("getPetById(...) ---- Pet that has got form DB by ID = {}", pet.toString());
        return pet;
    }

    public Pet getPetWithPerson(Integer id) {
        Pet pet = petRepository.getPetWithPerson(id);
        log.debug("getPetById(...) ---- Pet with Person ONLY that has got form DB by ID = {}", pet.toString());
        return pet;
    }

    public Pet getPetWithRepastAndPerson(Integer id) {
        Pet pet = petRepository.getPetWithRepastAndPerson(id);
        log.debug("getPetById(...) ---- Pet with Person and Repast that has got form DB by ID = {}", pet.toString());
        return pet;
    }

    public Pet getPetWithRepast(Integer id) {
        Pet pet = petRepository.getPetWithRepast(id);
        log.debug("getPetById(...) ---- Pet with Repast ONLY that has got form DB by ID = {}", pet.toString());
        return pet;
    }

    public List<Pet> getPersonMatchedByName(String maskOfName) {
        List<Pet> pets = petRepository.getPersonMatchedByName(maskOfName);
        log.info("getPetById(...) ---- Pet with Repast ONLY that has got form DB by ID = {}", pets.toString());
        return pets;
    }

}
