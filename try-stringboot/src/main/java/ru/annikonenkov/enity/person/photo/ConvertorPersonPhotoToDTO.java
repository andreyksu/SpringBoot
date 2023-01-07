package ru.annikonenkov.enity.person.photo;

public class ConvertorPersonPhotoToDTO {

    public PersonPhotoDTO convertToPersonPhotoDTO(PersonPhoto personPhoto) {
        return new PersonPhotoDTO(personPhoto.getId(), personPhoto.getPhotoName(), personPhoto.getSize(), personPhoto.getMime());
    }
}
