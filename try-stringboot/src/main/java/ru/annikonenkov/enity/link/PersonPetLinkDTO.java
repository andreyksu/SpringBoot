package ru.annikonenkov.enity.link;

import ru.annikonenkov.enity.person.Person;
import ru.annikonenkov.enity.person.PersonDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.annikonenkov.enity.person.ConvertorPersonToDTO;
import ru.annikonenkov.enity.pet.ConvertorPetToDTO;
import ru.annikonenkov.enity.pet.Pet;
import ru.annikonenkov.enity.pet.PetDTO;

public class PersonPetLinkDTO {

    private static final Logger log = LoggerFactory.getLogger(PersonPetLinkDTO.class);

    private Person person;

    private Pet pet;

    public PersonPetLinkDTO(Pet pet) {
        log.info("\n Constructor::: PersonPetLinkDTO(Pet pet) \n");
        this.pet = pet;
    }

    public PersonPetLinkDTO(Person person) {
        log.info("\n Constructor::: PersonPetLinkDTO(Person person) \n");
        this.person = person;
    }

    public PersonDTO getPersonDTO() {
        log.info("\n Method::: PersonDTO getPersonDTO() person = {} \n", person);
        if (person == null) {
            log.warn("\n personLinks is null or size = 0 \n");
            return null;
        }
        PersonDTO personDTO = new ConvertorPersonToDTO().convertToDTOOnlyPerson(person);
        return personDTO;
    }

    public PetDTO getPetDTO() {
        log.info("\n Method::: PetDTO getPetDTO() pet = {} \n", pet);
        if (pet == null) {
            log.warn("\n petLinks is null or size = 0 \n");
            return null;
        }
        PetDTO petDTO = new ConvertorPetToDTO().convertToDTOOnlyPet(pet);
        return petDTO;
    }

}
