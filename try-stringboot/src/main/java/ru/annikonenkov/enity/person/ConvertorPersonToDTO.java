package ru.annikonenkov.enity.person;

public class ConvertorPersonToDTO {

    public PersonDTO convertToDTOOnlyPerson(Person person) {
        return new PersonDTO(person.getId(), person.getName(), person.getOld(), null, null);
    }

    public PersonDTO convertToDTOWithPhotos(Person person) {
        return new PersonDTO(person.getId(), person.getName(), person.getOld(), person.getPersonPhotos(), null);
    }

    public PersonDTO convertToDTOWithPets(Person person) {
        return new PersonDTO(person.getId(), person.getName(), person.getOld(), null, person.getPetsLinks());
    }

    public PersonDTO convertToDTOWithPhotosAndPets(Person person) {
        return new PersonDTO(person.getId(), person.getName(), person.getOld(), person.getPersonPhotos(), person.getPetsLinks());
    }
}
