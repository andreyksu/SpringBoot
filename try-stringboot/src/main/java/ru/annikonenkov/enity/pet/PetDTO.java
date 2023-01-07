package ru.annikonenkov.enity.pet;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.annikonenkov.enity.link.PersonPetLink;
import ru.annikonenkov.enity.link.PersonPetLinkDTO;
import ru.annikonenkov.enity.person.PersonDTO;
import ru.annikonenkov.enity.link.ConvertorPersonPetLinkToDTO;
import ru.annikonenkov.enity.repast.ConvertReapstToDTO;
import ru.annikonenkov.enity.repast.Repast;
import ru.annikonenkov.enity.repast.RepastDTO;

public class PetDTO {

    private static final Logger log = LoggerFactory.getLogger(PetDTO.class);

    private int id;

    private String petsName;

    private int petsOld;

    private Set<PersonPetLink> personLinks = new HashSet<>();

    private Set<Repast> repast = new HashSet<>();

    public PetDTO(int id, String name, int old) {
        log.info("\n PetDTO(int id, String name, int old) \n");
        this.id = id;
        this.petsName = name;
        this.petsOld = old;
    }

    public PetDTO(int id, String name, int old, Set<PersonPetLink> personLinks, Set<Repast> repast) {
        log.info("\n PetDTO(int id, String name, int old, Set<PersonPetLink> personLinks, Set<Repast> repast) \n");
        this.id = id;
        this.petsName = name;
        this.petsOld = old;
        this.personLinks = personLinks;
    }

    public PetDTO(int id, String name, int old, Set<PersonPetLink> personLinks) {
        log.info("\n PetDTO(int id, String name, int old, Set<PersonPetLink> personLinks) \n");
        this.id = id;
        this.petsName = name;
        this.petsOld = old;
        this.personLinks = personLinks;
    }

    public PetDTO(Set<Repast> repast, int id, String name, int old) {
        log.info("\n PetDTO(Set<Repast> repast, int id, String name, int old) \n");
        this.id = id;
        this.petsName = name;
        this.petsOld = old;
        this.repast = repast;
    }

    public int getId() {
        return this.id;
    }

    public String getPetsName() {
        return this.petsName;
    }

    public int getPetsOld() {
        return this.petsOld;
    }

    public Set<PersonPetLinkDTO> getPersonPetLinkDTO() {
        log.info("\n Set<PersonPetLinkDTO> getPersonPetLinkDTO() \n");
        Set<PersonPetLinkDTO> personLinksDTO = new HashSet<>();
        if (personLinks == null || personLinks.size() == 0) {
            log.warn("\n personLinks is null or size = 0 \n");
            log.warn("\n personLinks.size() = {} \n", personLinks.size());
            return personLinksDTO;
        } else {
            log.info("\n personLinks isn`t null or size != 0 \n");
        }
        ConvertorPersonPetLinkToDTO convertor = new ConvertorPersonPetLinkToDTO();
        personLinks.forEach(item -> personLinksDTO.add(convertor.convertToDTOForPets(item)));
        return personLinksDTO;
    }

    public Set<RepastDTO> getRepast() {
        log.info("\n Set<RepastDTO> getRepast() \n");
        Set<RepastDTO> repastDTO = new HashSet<>();
        if (repast == null || repast.size() == 0) {
            log.warn("\n repast is null or size = 0 \n");
            log.warn("\n repast.size() = {} \n", repast.size());
            return repastDTO;
        } else {
            log.info("\n repast isn`t null or size != 0 \n");
        }
        ConvertReapstToDTO convertor = new ConvertReapstToDTO();
        repast.forEach(item -> repastDTO.add(convertor.convertToDTO(item)));
        return repastDTO;
    }

}
