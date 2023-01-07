package ru.annikonenkov.enity.person;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.annikonenkov.enity.link.PersonPetLink;
import ru.annikonenkov.enity.link.PersonPetLinkDTO;
import ru.annikonenkov.enity.link.ConvertorPersonPetLinkToDTO;
import ru.annikonenkov.enity.person.photo.PersonPhoto;
import ru.annikonenkov.enity.person.photo.PersonPhotoDTO;
import ru.annikonenkov.service.PersonService;
import ru.annikonenkov.enity.person.photo.ConvertorPersonPhotoToDTO;

public class PersonDTO {

    private static final Logger log = LoggerFactory.getLogger(PersonDTO.class);

    private Integer id;

    private String personName;

    private int personOld;

    private Set<PersonPhoto> personPhotos = new HashSet<>();

    private Set<PersonPetLink> petsLinks = new HashSet<>();
    
    public PersonDTO() {}

    public PersonDTO(int id, String name, int old, Set<PersonPhoto> personPhotos, Set<PersonPetLink> petsLinks) {
        this.id = id;
        this.personName = name;
        this.personOld = old;
        this.personPhotos = personPhotos;
        this.petsLinks = petsLinks;
        log.info("\n Constructor PersonDTO::: id = {}, personName = {}, personOld = {}, \n\n this.personPhotos = {},\n\n  this.petsLinks = {} \n", id, name, old, personPhotos, petsLinks);
    } 

    public Integer getId() {
        return this.id;
    }

    public String getPersonName() {
        return this.personName;
    }

    public int getPersonOld() {
        return this.personOld;
    }

    public Set<PersonPhotoDTO> getPersonPhotosDTO() {
        log.info("\n\n Method::: Set<PersonPhotoDTO> getPersonPhotosDTO() personId = {} \n", id);
        Set<PersonPhotoDTO> personPhotosDTO = new HashSet<>();
        if (this.personPhotos == null || this.personPhotos.size() == 0) {
            log.warn("\n personPhotos is null or size = 0 \n");
            return personPhotosDTO;
        } else {
            log.info("\n personPhotos isn`t null or size != 0 personId = {} \n", id);
        }
        ConvertorPersonPhotoToDTO convertor = new ConvertorPersonPhotoToDTO();
        this.personPhotos.forEach(item -> personPhotosDTO.add(convertor.convertToPersonPhotoDTO(item)));
        return personPhotosDTO;
    }

    public Set<PersonPetLinkDTO> getPetLinkDTO() {
        log.info("\n\n Method::: Set<PersonPetLinkDTO> getPetLinkDTO() personId = {} \n", id);
        Set<PersonPetLinkDTO> petsLinksDTO = new HashSet<>();
        if (this.petsLinks == null || this.petsLinks.size() == 0) {
            log.warn("\n petsLinks is null or size = 0 \n");
            return petsLinksDTO;
        } else {
            log.info("\n petsLinks isn`t null or size != 0 personId = {} \n", id);
        }
        ConvertorPersonPetLinkToDTO convertor = new ConvertorPersonPetLinkToDTO();
        this.petsLinks.forEach(item -> petsLinksDTO.add(convertor.convertToDTOForPerson(item)));
        return petsLinksDTO;
    }

}
