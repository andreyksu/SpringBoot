package ru.annikonenkov.enity.link;

public class ConvertorPersonPetLinkToDTO {

    /**
     * Для Pet конвертирует в DTO ссылки на Person.
     */
    public PersonPetLinkDTO convertToDTOForPets(PersonPetLink personPetLink) {
        return new PersonPetLinkDTO(personPetLink.getPerson());
    }

    /**
     * Для Person конвертирует в DTO ссылки на Pet
     */
    public PersonPetLinkDTO convertToDTOForPerson(PersonPetLink personPetLink) {
        return new PersonPetLinkDTO(personPetLink.getPet());
    }

}
